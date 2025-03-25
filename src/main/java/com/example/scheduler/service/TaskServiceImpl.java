package com.example.scheduler.service;

import com.example.scheduler.dto.TaskCreateRequestDto;
import com.example.scheduler.dto.TaskDto;
import com.example.scheduler.dto.TaskResponseDto;
import com.example.scheduler.entity.Task;
import com.example.scheduler.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    //생성 - CreateDto로 받은 내용으로 저장
    @Override
    public TaskResponseDto saveTask(TaskCreateRequestDto createDto) {
        // 요청받은 데이터로 Task 객체 생성
        Task task = new Task(createDto.getUserId(), createDto.getContent(), createDto.getPassword());
        // Repository에 저장
        TaskDto taskDto = taskRepository.saveTask(task);
        // 유저 Id 기반 유저 조회
        String name = "조회 중";
        return new TaskResponseDto(name, taskDto);
    }

    //조회 - 조건을 만족하는 일정 전체 -> 얘만 조인으로 리턴하자
    public List<TaskResponseDto> findTasks(Long userId, String updatedAt){
        return taskRepository.findTasks(userId, updatedAt);
    }

    //조회 - id를 통한 단일 일정 조회
    public TaskResponseDto findTaskById(Long id){
        TaskDto taskDto = taskRepository.findTaskByIdOrElseThrow(id);
        // 유저 Id 기반 유저 조회
        String name = "조회 중";
        return new TaskResponseDto(name, taskDto);
    }
}
