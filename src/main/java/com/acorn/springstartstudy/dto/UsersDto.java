package com.acorn.springstartstudy.dto;

import lombok.Data;

@Data
public class UsersDto {
    private String uId;
    private String pw;
    private String name;
    private String phone;
    private String imgPath;
    private String email;
    private java.util.Date postTime;
    private String birth; //유닉스시간을 기준으로 하면 1970 이전에 태어난 사람을 저장할 수 없어서...
    private String gender;
    private String address;
    private String detailAddress;
    private String permission;

}
