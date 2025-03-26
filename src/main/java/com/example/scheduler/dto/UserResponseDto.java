package com.example.scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private Long userId;          //일정 ID
    private String name;      //유저 이름
    private String email;   //이메일 주소
    private String updateAt;  //수정일

    // 날짜 포맷 (YYYY-MM-DD HH:MM)
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public UserResponseDto(Long userId, String name, String email, Timestamp updateAt){
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.updateAt = formatter.format(updateAt);
    }
}