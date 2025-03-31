package com.example.scheduler.repository;

import com.example.scheduler.dto.UserResponseDto;
import com.example.scheduler.entity.User;

public interface UserRepository {
    //유저 생성
    UserResponseDto saveUser(User user);

    //유저 이름 조회
    UserResponseDto findUserByIdOrElseThrow(Long id);

    //유저 이름만 조회! userId에 해당하는 이름 조회
    String findUserName(Long id);

    //유저 존재 여부 화긴
    boolean checkUserExist(Long id);

    //비밀번호 가져오는 조회
    User findUserWithPwd(Long id);

    //유저 이름 수정
    UserResponseDto updateUser(Long id, String name);

    //유저 삭제
    int deleteUser(Long id);
}
