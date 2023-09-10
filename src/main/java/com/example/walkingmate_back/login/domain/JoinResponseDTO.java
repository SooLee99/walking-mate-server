package com.example.walkingmate_back.login.domain;

public class JoinResponseDTO {

    public Data data = new Data();

    public static String success = "success";
    public static String fail = "fail";

    public class Data {

        public String message = "";
        public String code = "";
    }
}
