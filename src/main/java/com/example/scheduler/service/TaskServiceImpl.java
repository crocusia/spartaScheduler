package com.example.scheduler.service;

import com.example.scheduler.dto.*;
import com.example.scheduler.entity.Task;
import com.example.scheduler.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskServiceImpl(TaskRepository taskRepository, UserService userService) {
        this.taskRepository = taskRepository;
        this.userService = userService;
    }

    //생성 - CreateDto로 받은 내용으로 저장
    @Override
    public TaskResponseDto saveTask(TaskCreateRequestDto createDto) {
        // 요청받은 데이터로 Task 객체 생성
        Task task = new Task(createDto.getUserId(), createDto.getContent(), createDto.getPassword());
        // Repository에 저장
        TaskDto taskDto = taskRepository.saveTask(task);
        // 유저 Id 기반 유저 조회
        String name = userService.findUserName(createDto.getUserId());
        return new TaskResponseDto(name, taskDto);
    }

    //조회 - 조건을 만족하는 일정 전체 -> 얘만 조인으로 리턴하자
    @Override
    public List<TaskResponseDto> findTasks(Long userId, String updatedAt){
        return taskRepository.findTasks(userId, updatedAt);
    }

    //조회 - id를 통한 단일 일정 조회
    @Override
    public TaskResponseDto findTaskById(Long id){
        TaskDto taskDto = taskRepository.findTaskByIdOrElseThrow(id);
        // 유저 Id 기반 유저 조회
        String name = userService.findUserName(taskDto.getUserId());
        return new TaskResponseDto(name, taskDto);
    }

    //수정 - userId 또는 content 수정
    @Transactional
    @Override
    public TaskResponseDto updateTask(Long id, TaskUpdateRequestDto updateDto){
        //기존 일정 조회
        Task task = taskRepository.findTaskByIdWithPwd(id);
        //비밀번호 비교
        if (!task.getPassword().equals(updateDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호 입력 오류");
        }
        //작성자 Id 수정
        if (updateDto.getUserId() != null){
            //유저레포지토리에서 조회 후 반환된 결과에 따라 반영 - 실제 존재하는가?
            if(userService.checkUserExist(updateDto.getUserId())){
                //유저아이디로 해당 일정을 작성한 유저만 변경
                //유저 이름은 유저 레포지토리에서 별도로 변경, 이건 일정을 수행해야 하는 유저를 변경하는 기능
                task.updateUserId(updateDto.getUserId());
            }
        }
        //할 일 수정
        if(updateDto.getContent() != null && !updateDto.getContent().isEmpty()){
            task.updateContent(updateDto.getContent());
        }
        //수정된 일정 저장
        TaskDto taskDto = taskRepository.updateTask(task);
        // 유저 Id 기반 유저 조회
        String name = userService.findUserName(taskDto.getUserId());
        return new TaskResponseDto(name, taskDto);
    }

    @Override
    public void deleteTask(Long id, TaskDeleteRequestDto deleteDto) {
        //기존 일정 조회
        Task task = taskRepository.findTaskByIdWithPwd(id);
        //조회한 일정의 비밀번호와 넘겨 받은 비밀번호 비교
        if (!task.getPassword().equals(deleteDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호 입력 오류");
        }
        // 일정 삭제
        int deletedRow = taskRepository.deleteTask(id);
        // 삭제된 row가 0개 라면
        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }
}
