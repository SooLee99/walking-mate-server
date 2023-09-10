package com.example.walkingmate_back.login.domain;

public class LoginResponse {

    public Data data = new Data();

    public static String success = "success";
    public static String fail = "fail";

    public class Data {
        public String userId = "";
        public String message = "";
        public String code = "";
        public String jwt = "";
    }

}
