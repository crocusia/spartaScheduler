package com.example.scheduler.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Task {
    private Long id; //고유 키
    private User user; //작성자 id 외래키
    private String content; //할 일
    private String password; //비밀번호
    private LocalDateTime updatedAt; //작성, 수정일

    public Task(User user, String content, String password){
        this.user = user;
        this.content = content;
        this.password = password;
        this.updatedAt = new Timestamp(System.currentTimeMillis()).toLocalDateTime();
    }
    //할 일 수정
    public void update(User user){
        this.user = user;
    }
    //작성자명 수정
    public void update(String content){
        this.content = content;
    }
    //할 일, 작성자명 수정
    public void update(User user, String content){
        this.user = user;
        this.content = content;
    }
}
