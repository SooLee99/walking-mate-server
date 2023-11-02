package com.example.walkingmate_back.login.config;

import com.example.walkingmate_back.login.service.LoginService;
import com.example.walkingmate_back.login.util.JwtUtil;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorization : {}", authorization);

        if(authorization == null){
            log.error("[접근불가] authorization이 없습니다.");
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorization;

        // TODO
        //
//        if(JwtUtil.isExpired(token, secretKey)){
//            log.error("token이 만료 되었습니다.");
//            filterChain.doFilter(request, response);
//            return;
//        }

        // Token에서 UserName추출
        String userName = JwtUtil.getUserName(token, secretKey);
        log.info("userName:{}", userName);

        UsernamePasswordAuthenticationToken authenticationToken;
        if(userName.equals("admin")) {
            // 관리자(ADMIN)권한 부여
            authenticationToken = new UsernamePasswordAuthenticationToken
                    (userName, null, List.of(new SimpleGrantedAuthority("ADMIN")));
        }else {
            // 일반 사용자(USER)권한 부여
            authenticationToken = new UsernamePasswordAuthenticationToken
                    (userName, null, List.of(new SimpleGrantedAuthority("USER")));
        }
        log.info("Role : {}", authenticationToken.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}