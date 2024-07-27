# 데이터 베이스 삭제, 생성, 선택
DROP DATABASE IF EXISTS JDBC_board;
CREATE DATABASE JDBC_board;
USE JDBC_board

CREATE TABLE article (
	id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	`subject` CHAR(100) NOT NULL,
	content TEXT NOT NULL
);

# 게시물 테스트 데이터
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
`subject` = CONCAT('제목', FLOOR(RAND() * 101)),
content = CONCAT('내용', FLOOR(RAND() * 101));
