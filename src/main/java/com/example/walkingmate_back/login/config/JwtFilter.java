package com.example.walkingmate_back.login.config;

import com.example.walkingmate_back.login.service.LoginService;
import com.example.walkingmate_back.login.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final LoginService loginService;
    private final String secretKey;
    // 인증받기 위한 내부Filter - 여기를 통해야 들어갈 수 있다.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // request에서 토큰 추출
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization : {}", authorization);
        // Token이 없다면 그냥 반환시킴
        if(authorization == null){
            log.error("[접근불가] authorization이 없습니다.");
            filterChain.doFilter(request, response);
            return;
        }
        // Token 꺼내기
        String token = authorization;
        // Token Expired 되었는지 여부
//        if(JwtUtil.isExpired(token, secretKey)){
//            log.error("token이 만료 되었습니다.");
//            filterChain.doFilter(request, response);
//            return;
//        }
        // UserName Token에서 꺼내기
        String userName = JwtUtil.getUserName(token, secretKey);
        log.info("userName:{}", userName);
        // 권한 부여
        UsernamePasswordAuthenticationToken authenticationToken;
        if(userName.equals("admin")) {
            // id가 admin이면 관리가(ADMIN)권한 부여
            authenticationToken = new UsernamePasswordAuthenticationToken
                    (userName, null, List.of(new SimpleGrantedAuthority("ADMIN")));
        }else {
            // 아니라면 일반 사용자(USER)권한 부여
            authenticationToken = new UsernamePasswordAuthenticationToken
                    (userName, null, List.of(new SimpleGrantedAuthority("USER")));
        }
        log.info("Role : {}", authenticationToken.getAuthorities());
        // Detail을 넣어줍니다.
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));     SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}