package com.example.scheduler.service;

import com.example.scheduler.dto.*;
import com.example.scheduler.entity.Task;
import com.example.scheduler.entity.User;
import com.example.scheduler.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //생성 - CreateDto로 받은 내용으로 저장
    @Override
    public UserResponseDto saveUser(UserCreateRequestDto createDto){
        User user = new User(createDto.getName(), createDto.getEmail(), createDto.getPassword());
        return userRepository.saveUser(user);
    }

    //조회 - 조건을 만족하는 유저
    @Override
    public UserResponseDto findUserById(Long id){
        return userRepository.findUserByIdOrElseThrow(id);
    }
    //조회 - 유저 이름 조회
    @Override
    public String findUserName(Long id){
        return userRepository.findUserName(id);
    }
    //조회 - 유저 존재 여부 확인
    @Override
    public boolean checkUserExist(Long id){
        return userRepository.checkUserExist(id);
    }

    //수정 - 유저 이름 수정
    @Transactional
    @Override
    public UserResponseDto updateUser(Long id, UserUpdateRequestDto updateDto){
        //기존 유저 조회
        User user = userRepository.findUserWithPwd(id);
        //비밀번호 비교
        if (!user.getPassword().equals(updateDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호 입력 오류");
        }
        //작성자 이름 수정
        return userRepository.updateUser(id, updateDto.getName());
    }

    //삭제 - 유저 삭제
    @Override
    public void deleteUser(Long id, UserDeleteRequestDto deleteDto){
        //기존 일정 조회
        User user = userRepository.findUserWithPwd(id);
        //조회한 일정의 비밀번호와 넘겨 받은 비밀번호 비교
        if (!user.getPassword().equals(deleteDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호 입력 오류");
        }
        // 일정 삭제
        int deletedRow = userRepository.deleteUser(id);
        // 삭제된 row가 0개 라면
        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }
}