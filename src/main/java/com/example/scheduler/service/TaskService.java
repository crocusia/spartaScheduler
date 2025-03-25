package com.example.scheduler.service;

import com.example.scheduler.dto.TaskCreateRequestDto;
import com.example.scheduler.dto.TaskResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface TaskService {
    //생성 - CreateDto로 받은 내용으로 저장
    TaskResponseDto saveTask(TaskCreateRequestDto createDto);
    //조회 - 조건을 만족하는 일정 전체
    List<TaskResponseDto> findTasks(Long userId, String updatedAt);
    //조회 - id를 통한 단일 일정 조회
    TaskResponseDto findTaskById(Long id);
}
