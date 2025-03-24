CREATE TABLE users(
                      user_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '작성자 식별자', -- 고유키
                      name VARCHAR(100) NOT NULL COMMENT '작성자명', -- 동명이인 가능
                      email VARCHAR(100) UNIQUE COMMENT '이메일', -- 중복될 수 없음
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '등록일', -- 초기 생성 시, 설정 후 변하지 않음
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일' -- 일정 최근 수정일에 따라 업데이트 될 변수
);

CREATE TABLE  tasks(
                       task_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '일정 식별자', -- 고유키
                       user_id BIGINT NOT NULL COMMENT '작성자 식별자', -- 외래키, 작성자명을 불러올 때 사용함
                       content VARCHAR(200) NOT NULL COMMENT '할 일', -- 최대 200자 제한
                       password CHAR(4) NOT NULL COMMENT '비밀번호', -- 4자리 핀 번호
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '작성일', -- 작성일
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일', -- 수정일, UPDATE 마다 자동으로 시간 갱신됨
                       FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE -- 외래키 / 작성자 삭제 시, 해당 작성자의 일정이 삭제됨
);