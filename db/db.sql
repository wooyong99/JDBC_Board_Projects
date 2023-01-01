DROP DATABASE IF EXISTS text_board;
CREATE DATABASE text_board;
USE text_board;

CREATE TABLE article(
	id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	title CHAR(100) NOT NULL,
	`body` TEXT NOT NULL
);

SELECT * FROM article;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = "제목",
`body` = "내용";

CREATE TABLE `member`(
	id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	regDate DATETIME NOT NULL,
	updateDate DATETIME NOT NULL,
	loginId CHAR(50) NOT NULL,
	loginPw CHAR(200) NOT NULL,
	`name` CHAR(100) NOT NULL
);

SELECT * FROM `member`;