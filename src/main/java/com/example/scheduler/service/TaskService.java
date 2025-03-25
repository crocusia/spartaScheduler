package com.example.scheduler.service;

import com.example.scheduler.dto.TaskCreateRequestDto;
import com.example.scheduler.dto.TaskResponseDto;

public interface TaskService {
    //생성 - CreateDto로 받은 내용으로 저장
    TaskResponseDto saveTask(TaskCreateRequestDto createDto);
}
