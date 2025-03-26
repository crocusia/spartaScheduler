package com.example.scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCreateRequestDto {
    private String name;        //이름
    private String email;     //이메일 주소
    private String password;    //비밀번호
}
