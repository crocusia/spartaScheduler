package com.example.scheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class User {
    private Long userId; //고유 키
    private String name; //이름
    private String email; //이메일
    private String password; //유저 로그인 비밀번호
    private Timestamp createdAt; //등록일
    private Timestamp updatedAt; //수정일

    //저장을 위한 생성자
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    //조회를 위한 생성자
    public User(Long userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    //작성자명 수정
    public void updateName(String name) {
        this.name = name;
    }

    //비밀번호 비교
    public boolean comparePassword(String inputPassword) {
        if (this.password.equals(inputPassword)) {
            return true;
        }
        return false;
    }
}
