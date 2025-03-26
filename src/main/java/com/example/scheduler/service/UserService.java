package com.example.scheduler.service;

import com.example.scheduler.dto.UserCreateRequestDto;
import com.example.scheduler.dto.UserDeleteRequestDto;
import com.example.scheduler.dto.UserResponseDto;
import com.example.scheduler.dto.UserUpdateRequestDto;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {
    //생성 - CreateDto로 받은 내용으로 저장
    UserResponseDto saveUser(UserCreateRequestDto createDto);

    //조회 - 조건을 만족하는 유저
    UserResponseDto findUserById(Long id);
    //조회 - 유저 이름 조회
    String findUserName(Long id);
    //조회 - 유저 존재 여부 확인
    boolean checkUserExist(Long id);
    //수정 - 유저 이름 수정
    UserResponseDto updateUser(Long id, UserUpdateRequestDto updateDto);
    //삭제 - 유저 삭제
    void deleteUser(Long id, UserDeleteRequestDto deleteDto);
}
