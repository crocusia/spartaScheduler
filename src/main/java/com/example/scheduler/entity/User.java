package com.example.scheduler.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class User {
    private Long id; //고유 키
    private String name; //이름
    private String email; //이메일
    private Timestamp createdAt; //유저 생성일
    private Timestamp updatedAt; //유저 정보 수정일

    public User(String name, String email){
        this.name = name;
        this.email = email;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    //작성자명 수정
    public void updateName(String name){
        this.name = name;
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }
}
