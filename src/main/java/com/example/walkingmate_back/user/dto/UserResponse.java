package com.example.walkingmate_back.user.dto;

public class UserResponse {

    public Data data = new Data();

    public static String success = "success";
    public static String fail = "fail";

    public class Data {
        public String userId = "";
        public String message = "";
        public String code = "";
    }

}
