package com.example.scheduler.repository;

import com.example.scheduler.dto.TaskResponseDto;
import com.example.scheduler.dto.TaskDto;
import com.example.scheduler.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    //DB에 Task 저장 및 저장 내용 반환
    TaskDto saveTask(Task task);
    //userId 또는 updateAt에 해당하는 일정 전체 조회
    List<TaskResponseDto> findTasks(Long userId, String updatedAt);
    //id에 해당하는 일정 선택 조회
    TaskDto findTaskByIdOrElseThrow(Long id);
}
