# 데이터 베이스 삭제, 생성, 선택
DROP DATABASE IF EXISTS JDBC_board;
CREATE DATABASE JDBC_board;
USE JDBC_board

# article 테이블 생성
CREATE TABLE article (
	id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	`subject` CHAR(100) NOT NULL,
	content TEXT NOT NULL
);

# member 테이블 생성
CREATE TABLE `member` (
	id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	loginId CHAR(50) NOT NULL,
	loginPw CHAR(100) NOT NULL,
	`name` CHAR(50) NOT NULL
);

# 임시 회원
INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'admin',
loginPw = 'admin',
`name` = '관리자';

INSERT INTO `member`
SET regDate = NOW(),
updateDate = NOW(),
loginId = 'user1',
loginPw = 'user1',
`name` = '회원1';

SELECT * FROM `member`;

# 게시물 테이블에 memberId 칼럼 추가
ALTER TABLE article ADD COLUMN memberId INT UNSIGNED NOT NULL AFTER updateDate;

# 게시물 테스트 데이터
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
`subject` = '제목1',
content = '내용1';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
`subject` = '제목2',
content = '내용2';

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
memberId = 2,
`subject` = '제목3',
content = '내용3';