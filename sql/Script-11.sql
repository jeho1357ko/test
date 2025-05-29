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


CREATE TABLE Comments (
  comment_id     NUMBER DEFAULT bbs_comments_comment_id_seq.nextval PRIMARY KEY,
  post_id        NUMBER,  -- 게시글 ID (외래키)
  name           VARCHAR2(30),
  content        CLOB,
  created_time   TIMESTAMP DEFAULT SYSTIMESTAMP,
  CONSTRAINT fk_comments_post FOREIGN KEY (post_id) REFERENCES Post(post_id) ON DELETE CASCADE
);

DROP TABLE COMMENTS ;
DROP SEQUENCE bbs_comments_comment_id_seq;
CREATE SEQUENCE bbs_comments_comment_id_seq START WITH 1 INCREMENT BY 1  maxvalue 99999;

COMMIT;


SELECT * FROM comments;

--등록
INSERT INTO comments (name,content,post_id)
	VALUES ('명제호','이재명 호텔경제론 이거 경제에 대해 너무 무지하네',39);

--목록
SELECT comment_id, name, content, created_time, post_id
FROM comments;

--조회
SELECT comment_id, name, content, created_time
FROM comments
WHERE comment_id = 2;


--수정
UPDATE comments
SET name = '김문수',
	content = '호텔 경제론 말도 안된다 !!',
	created_time = systimestamp
WHERE comment_id = 2;

--삭제
DELETE FROM comments
WHERE comment_id = 1;

COMMIT;

SELECT count(comment_id)
FROM comments;

SELECT * FROM MEMBER;

SELECT * FROM post;


 SELECT comment_id,name,content,created_time,post_id
 FROM Comments
  WHERE POST_ID = :post_id
ORDER BY comment_id asc
   offset (:pageNo-1) * :numOfRows rows
FETCH NEXT :numOfRows ROWs ONLY


--코드
-------
create table code(
    code_id     varchar2(11),       --코드
    decode      varchar2(30),       --코드명
    discript    clob,               --코드설명
    pcode_id    varchar2(11),       --상위코드
    useyn       char(1) default 'Y',            --사용여부 (사용:'Y',미사용:'N')
    cdate       timestamp default systimestamp,         --생성일시
    udate       timestamp default systimestamp          --수정일시
);
--기본키
alter table code add Constraint code_code_id_pk primary key (code_id);

--외래키
alter table code add constraint bbs_pcode_id_fk
    foreign key(pcode_id) references code(code_id);

--제약조건
alter table code modify decode constraint code_decode_nn not null;
alter table code modify useyn constraint code_useyn_nn not null;
alter table code add constraint code_useyn_ck check(useyn in ('Y','N'));

--시퀀스
create sequence code_code_id_seq;

--샘플데이터 of code
--insert into code (code_id,decode,pcode_id,useyn) values ('B01','게시판',null,'Y');
--insert into code (code_id,decode,pcode_id,useyn) values ('B0101','Spring','B01','Y');
--insert into code (code_id,decode,pcode_id,useyn) values ('B0102','Datbase','B01','Y');
--insert into code (code_id,decode,pcode_id,useyn) values ('B0103','Q_A','B01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('M01','회원구분',null,'Y');
insert into code (code_id,decode,pcode_id,useyn) values ('M0101','일반','M01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('M0102','우수','M01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('M01A1','관리자1','M01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('M01A2','관리자2','M01','Y');

insert into code (code_id,decode,pcode_id,useyn) values ('A02','지역',null,'Y');
insert into code (code_id,decode,pcode_id,useyn) values ('A0201','서울','A02','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('A0202','부산','A02','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('A0203','대구','A02','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('A0204','울산','A02','Y');

insert into code (code_id,decode,pcode_id,useyn) values ('H01','취미',null,'Y');
insert into code (code_id,decode,pcode_id,useyn) values ('H0101','등산','H01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('H0102','수영','H01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('H0103','골프','H01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('H0104','독서','H01','Y');

insert into code (code_id,decode,pcode_id,useyn) values ('F01','첨부파일',null,'Y');
insert into code (code_id,decode,pcode_id,useyn) values ('F0101','회원','F01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('F010101','사진','F0101','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('F0102','게시판','F01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('F010201','첨부파일','F0102','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('F0103','상품관리','F01','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('F010301','설명서','F0103','Y');
insert into code (code_id,decode,pcode_id,useyn) values ('F010302','이미지','F0103','Y');


commit;

-------
--회원
-------
create table member (
    member_id   number(10),     --내부 관리 아이디
    email       varchar2(50),   --로긴 아이디
    passwd      varchar2(12),   --로긴 비밀번호
    tel         varchar2(13),   --연락처 ex)010-1234-5678
    nickname    varchar2(30),   --별칭
    gender      varchar2(6),    --성별
    hobby       varchar2(300),  --취미
    region      varchar2(11),   --지역
    gubun       varchar2(11)   default 'M0101', --회원구분 (일반,우수,관리자..)
    pic         blob,            --사진
    cdate       timestamp default systimestamp,         --생성일시
    udate       timestamp default systimestamp          --수정일시
);
--기본키생성
alter table member add Constraint member_member_id_pk primary key (member_id);

--외래키
alter table member add constraint member_region_fk
    foreign key(region) references code(code_id);
alter table member add constraint member_gubun_fk
    foreign key(gubun) references code(code_id);

--제약조건
alter table member modify email constraint member_email_uk unique;
alter table member modify email constraint member_email_nn not null;
alter table member add constraint member_gender_ck check (gender in ('남자','여자'));

--시퀀스
create sequence member_member_id_seq;

--샘플데이터 of member
insert into member (member_id,email,passwd,tel,nickname,gender,hobby,region,gubun)
    values(member_member_id_seq.nextval, 'test1@kh.com', '1234', '010-1111-1111','테스터1','남자','골프,독서','A0201', 'M0101');
insert into member (member_id,email,passwd,tel,nickname,gender,hobby,region,gubun)
    values(member_member_id_seq.nextval, 'test2@kh.com', '1234', '010-1111-1112','테스터2','여자','골프,수영','A0202', 'M0102');
insert into member (member_id,email,passwd,tel,nickname,gender,hobby,region,gubun)
    values(member_member_id_seq.nextval, 'admin1@kh.com', '1234','010-1111-1113','관리자1', '남자','등산,독서','A0203','M01A1');
insert into member (member_id,email,passwd,tel,nickname,gender,hobby,region,gubun)
    values(member_member_id_seq.nextval, 'admin2@kh.com', '1234','010-1111-1114','관리자2', '여자','골프,독서','A0204','M01A2');
select * from member;
commit;

UPDATE member SET passwd = '1234567' WHERE member_id = 4;
