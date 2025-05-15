DROP TABLE post;
DROP SEQUENCE post_id_seq;


CREATE SEQUENCE post_id_seq START WITH 1 INCREMENT BY 1 MAXVALUE 9999;

CREATE TABLE post (
   post_id NUMBER DEFAULT post_id_seq.nextval PRIMARY KEY ,
   title varchar2(60),
   content clob,
   name varchar2(20),
   Date_Created DATE DEFAULT systimestamp,
   Revised_DATE DATE DEFAULT systimestamp
);

SELECT * FROM post; 

DELETE FROM post WHERE post_id = 24;

COMMIT;

INSERT INTO post(title,content,name)
   values('입법부에 항복한 사법부','선거법 취지를 무시한 서울 고등법원 이재명의 판결을 대선후로 미루다. 이것은 입법부의 사법 개입 법은 만인에게 예외 없이 적용되어야한다','명제호');


SELECT post_id , title , content , name ,Date_Created ,Revised_DATE
FROM post
ORDER BY post_id DESC;

UPDATE post
SET title = '대통령이 될자는?',
	content = '이재명이 유력 김문수는 단일화 성공한다면 아직 모른다 이준석씨는 그냥 없다',
	name = '박대기',
	Revised_DATE = systimestamp
WHERE post_id = 2;
