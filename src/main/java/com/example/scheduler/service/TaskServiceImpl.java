package com.example.scheduler.service;

import com.example.scheduler.dto.TaskCreateRequestDto;
import com.example.scheduler.dto.TaskDto;
import com.example.scheduler.dto.TaskResponseDto;
import com.example.scheduler.entity.Task;
import com.example.scheduler.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskResponseDto saveTask(TaskCreateRequestDto createDto) {
        // 요청받은 데이터로 Task 객체 생성
        Task task = new Task(createDto.getUserId(), createDto.getContent(), createDto.getPassword());
        // Repository에 저장 후, 일정 고유키 반환
        Long taskId = taskRepository.saveTask(task);
        // 유저 Id 기반 유저 조회
        String name = "조회 중";
        //TaskDto 생성
        TaskDto taskDto = new TaskDto(taskId, task);
        return new TaskResponseDto(name, taskDto);
    }

}
