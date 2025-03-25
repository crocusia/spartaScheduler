package com.example.scheduler.repository;

import com.example.scheduler.dto.TaskResponseDto;
import com.example.scheduler.dto.TaskDto;
import com.example.scheduler.entity.Task;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateTaskRepository implements TaskRepository{
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateTaskRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //DB에 Task 저장 및 저장 내용 반환
    @Override
    public TaskDto saveTask(Task task) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("tasks")
                .usingGeneratedKeyColumns("task_id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", task.getUserId());  //작성자 ID
        parameters.put("content", task.getContent());  //할 일
        parameters.put("password", task.getPassword()); //비밀번호
        //일정 고유키 반환
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        //TaskDto로 날짜를 String으로 반환
        return new TaskDto(key.longValue(), task);
    }

    //userId 또는 updateAt에 해당하는 일정 전체 조회
    @Override
    public List<TaskResponseDto> findTasks(Long userId, String updatedAt){
        StringBuilder sql = new StringBuilder(
                "SELECT t.task_id, t.user_id, u.name, t.content, t.updated_at " +
                        "FROM tasks t " +
                        "JOIN users u ON t.user_id = u.user_id "
        );

        // 동적 조건 추가 여부
        List<Object> params = new ArrayList<>();
        boolean hasCondition = false;

        if (userId != null) { //유저 아이디를 입력 받았다면
            sql.append("WHERE t.user_id = ? "); //유저 아이디 조건 추가
            params.add(userId);
            hasCondition = true;
        }

        if (updatedAt != null && !updatedAt.isEmpty()) { //유저 아이디를 입력 받지 않았다면
            if (hasCondition) { //앞에 유저 아이디가 조건으로 추가되어있으면
                sql.append("AND "); //AND 추가
            } else {
                sql.append("WHERE "); //아니면 바로 WHERE 추가 후
            }
            sql.append("t.updated_at >= ? "); //수정일 조건 추가
            params.add(updatedAt);
        }

        sql.append("ORDER BY t.updated_at DESC"); //수정일 기준 내림차순 정렬

        return jdbcTemplate.query(sql.toString(), taskRowMapperV2(), params.toArray());
    }

    //id에 해당하는 일정 선택 조회
    @Override
    public TaskDto findTaskByIdOrElseThrow(Long id){
        List<TaskDto> result = jdbcTemplate.query("SELECT task_id, user_id, content, updated_at FROM tasks WHERE id = ?", taskRowMapper(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    private RowMapper<TaskDto> taskRowMapper(){
        return new RowMapper<TaskDto>() {
            @Override
            public TaskDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new TaskDto(
                        rs.getLong("task_id"),
                        rs.getLong("user_id"),
                        rs.getString("content"),
                        rs.getTimestamp("updated_at").toString()
                );
            }
        };
    }

    private RowMapper<TaskResponseDto> taskRowMapperV2(){
        return new RowMapper<TaskResponseDto>() {
            @Override
            public TaskResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new TaskResponseDto(
                        rs.getLong("task_id"),
                        rs.getString("name"),
                        rs.getString("content"),
                        rs.getTimestamp("updated_at").toString()
                );
            }
        };
    }

}
