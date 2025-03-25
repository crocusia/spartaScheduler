package com.example.scheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Task {

    private Long id; //고유 키
    private Long userId; //작성자 id 외래키
    private String content; //할 일
    private String password; //비밀번호
    private Timestamp createdAt; //등록일
    private Timestamp updatedAt; //수정일

    //Insert
    public Task(Long userId, String content, String password){
        this.userId = userId;
        this.content = content;
        this.password = password;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    //할 일 수정
    public void updateContent(String content){
        this.content = content;
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

}
