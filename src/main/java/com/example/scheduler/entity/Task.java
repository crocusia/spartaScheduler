package com.example.scheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class Task {

    private Long taskId; //고유 키
    private Long userId; //작성자 id 외래키
    private String content; //할 일
    private String password; //비밀번호
    private Timestamp createdAt; //등록일
    private Timestamp updatedAt; //수정일

    //저장을 위한 생성자 : taskId는 생성되지 않음
    public Task(Long userId, String content, String password){
        this.userId = userId;
        this.content = content;
        this.password = password;
    }

    //조회를 위한 생성자
    public Task(Long taskId, Long userId, String content, String password){
        this.taskId = taskId;
        this.userId = userId;
        this.content = content;
        this.password = password;
    }

    //할 일 수정
    public void updateContent(String content){
        this.content = content;
    }

    //작성자 id 수정
    public void updateUserId(Long userId){
        this.userId = userId;
    }

}
