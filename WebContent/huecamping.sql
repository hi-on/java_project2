create sequence bigcateseq
increment by 1
start with 1
minvalue 1
maxvalue 10000
nocache;

create table bigcategory( 
	no number not null primary key,	-- 식별값
	name varchar2(50) not null,     -- 대분류명
	step number not null	 		-- 대분류간 순서조정을 위한 값
);
select * from bigcategory;
drop sequence smallcateseq ;
drop table smallcategory cascade constraints;

create sequence smallcateseq
increment by 1
start with 1
minvalue 1
maxvalue 10000
nocache;

create table smallcategory( 
	no number not null primary key,	-- 식별값
	name varchar2(50) not null,     -- 소분류명
	step number not null,			-- 소분류간 순서조정을 위한 값
	lcno number				-- 순서조정을 위한 값
);
--아래의 insert 첫줄은 무조건 넣어줘야 함.
insert into bigcategory values(bigcateseq.nextval, '텐트/타프', 1);
insert into bigcategory values(bigcateseq.nextval, '테이블/선반', 2);
insert into bigcategory values(bigcateseq.nextval, '의자/침대', 3);
insert into bigcategory values(bigcateseq.nextval, '침낭/매트', 4);
insert into bigcategory values(bigcateseq.nextval, '취사용품', 5);
insert into bigcategory values(bigcateseq.nextval, '조명용품', 6);
insert into bigcategory values(bigcateseq.nextval, '캠핑툴', 7);
insert into bigcategory values(bigcateseq.nextval, '일반용품', 8);
insert into bigcategory values(bigcateseq.nextval, '등산/아웃도어', 9);

select * from product;
commit;
--아래의 insert 첫줄은 무조건 넣어줘야 함.
insert into smallcategory values(smallcateseq.nextval, '텐트', 1, 1);
insert into smallcategory values(smallcateseq.nextval, '타프', 2, 1);
insert into smallcategory values(smallcateseq.nextval, '그라운드시트/방수포', 3, 1);
insert into smallcategory values(smallcateseq.nextval, '폴대/팩.스토퍼', 4, 1);
insert into smallcategory values(smallcateseq.nextval, '테이블', 1, 2);
insert into smallcategory values(smallcateseq.nextval, '선반', 2, 2);
insert into smallcategory values(smallcateseq.nextval, '침낭', 1, 3);
insert into smallcategory values(smallcateseq.nextval, '매트', 2, 3);
insert into smallcategory values(smallcateseq.nextval, '돗자리/피크닉매트', 3, 3);


select * from bigcategory order by step asc;
select * from smallcategory order by lcno asc, step asc;

commit;

--getAllCategory()
select lcate.no as lno, lcate.name as lname, lcate.step as lstep, scate.no as sno, scate.name as sname, scate.step as sstep
from (select * from bigcategory order by step asc) lcate left outer join (select * from smallcategory order by lcno asc, step asc) scate
on lcate.no = scate.lcno
order by lstep asc, sstep asc;

commit;

------------------------------------------------------------------------
-- 회원 테이블 생성
drop sequence memseq ;
DROP TABLE member CASCADE CONSTRAINTS;

create sequence memseq   
increment by 1
start with 1
minvalue 1
maxvalue 10000
nocache;

create table member(
	no number not null primary key, -- 사용자 식별번호
	id varchar2(40) UNIQUE not null, -- 아이디
	password varchar2(30) not null, -- 패스워드
	name varchar2(30) not null, -- 이름
	zip varchar2(20), -- 우편번호
	addr1 varchar2(50), -- 주소
	addr2 varchar2(50), -- 상세주소
    mobile1 varchar2(30), -- 핸드폰 앞
	mobile2 varchar2(30), -- 핸드폰 중
	mobile3 varchar2(30), -- 핸드폰 뒷	
    email varchar2(50),
    gender varchar2(10),
    state int default 1 not null,
    memdate date,
    deldate date,
    delcont varchar2(4000)
);

select * from member;


-----------------------------------------------------
drop table productQnA;
--상품문의 게시판 
create table productQnA(
 board_no number(38) primary key --게시물 번호
 ,board_name varchar2(30) not null --글쓴이
 ,board_title varchar2(200) not null --글제목
 ,board_pwd varchar2(20) not null --비번
 ,board_cont varchar2(4000) not null --글내용
 ,board_hit number(38) default 0 --조회수, default 0 제약조건을 주면 해당 제약조건이 설정된 컬럼에 굳이 레
 --코드를 저장하지 않아도 기본값 0이 저장됨.
 ,board_ref number(38)  --원본글과 답변글을 묶어주는 그룹번호 역할
 ,board_step number(38) --원본글이면 0,첫번째 답변글이면 1,두번째 답변글이면 2 => 원본글과 답변글을 구분하는 번
 --호값이면서 몇번째 답변글인가를 알려준다.
 ,board_level number(38) --답변글 정렬순서
 ,board_date date 
);

select * from productQnA order by board_no desc;

--product_QnA 시퀀스 생성
drop sequence productQnA_no_seq;
create sequence productQnA_no_seq
start with 1
increment by 1
nocache;

commit;

--------------------------------------------------------------
--상품문의 게시판 댓글

drop table ReplyproductQnA;
create table ReplyproductQnA(
 reply_no number(38) primary key --댓글 번호
 ,board_no number(38) references productQnA(board_no) on delete cascade --게시글 번호
 ,reply_name varchar2(30) not null --댓글 작성자
 ,reply_title varchar2(200) not null --댓글 제목
 ,reply_pwd varchar2(20) not null --비번
 ,reply_cont varchar2(4000) not null --댓글 내용
 ,reply_hit number(38) default 0 --조회수
 ,reply_ref number(38)  --
 ,reply_step number(38) --
 ,reply_level number(38) --
 ,reply_date date -- 댓글 작성 날짜
);

--ReplyproductQnA 시퀀스 생성
create sequence ReplyproductQnA_no_seq
start with 1
increment by 1
nocache;

commit;
select * from ReplyproductQnA;

--------------------------------------------------------------

-- 상품 테이블

drop sequence prodseq ;
drop table product cascade constraints;

create sequence prodseq
increment by 1
start with 1
minvalue 1
maxvalue 10000
nocache;

create table product( 
	no number not null primary key,	-- 식별값 
	lcno number not null references bigcategory(no) on delete cascade, --대분류
	scno number references smallcategory(no) on delete cascade, -- 소분류
	name varchar2(100) not null, -- 상품이름
	oriprice number, --정가
	discprice number, --할인가
	info varchar2(400), -- 상품설명
	mainImgN varchar2(30), -- 상품메인이미지
	detailImgN1 varchar2(30), -- 설명이미지1
	detailImgN2 varchar2(30), -- 설명이미지2
	detailImgN3 varchar2(30), -- 설명이미지3
	detailImgN4 varchar2(30) -- 설명이미지4
);

select * from product;

--getAllProduct()
select pro.NO, pro.LCNAME, scate.name as scname, pro.NAME, pro.ORIPRICE, pro.DISCPRICE, pro.INFO, pro.MAINIMGN, pro.DETAILIMGN1, pro.DETAILIMGN2, pro.DETAILIMGN3, pro.DETAILIMGN4 
from smallcategory scate inner join (select pro.NO, lcate.NAME as lcname, pro.SCNO, pro.NAME, pro.ORIPRICE, pro.DISCPRICE, pro.INFO, pro.MAINIMGN, pro.DETAILIMGN1, pro.DETAILIMGN2, pro.DETAILIMGN3, pro.DETAILIMGN4
from bigcategory lcate inner join product pro
on lcate.no = pro.lcno) pro
on scate.no = pro.scno;


-----------------------------------------------------------------------
-- 사이즈별 재고 테이블

drop sequence stockseq ;
drop table stock;

create sequence stockseq
increment by 1
start with 1
minvalue 1
maxvalue 10000
nocache;

create table stock( 
	no number not null primary key,	-- 식별값
	pno number references product(no) on delete cascade,
	opname varchar2(20) not null,
	count number
);

commit;

-----------------------------------------------------------------------
-- 장바구니 테이블 생성
drop sequence cartseq ;
drop table cart;

create sequence cartseq   
increment by 1
start with 1
minvalue 1
maxvalue 10000
nocache;

create table cart(
	no number primary key,
	mno number references member(no) on delete cascade, -- 유저번호
	pno number references product(no) on delete cascade, -- 상품 번호 
	opname varchar2(40), -- 사이즈
	qty number not null
);

select * from cart;
commit;
------------------------------------------------------------------------
------------------------------------------------------------------------
-- 주문내역 테이블 생성
drop sequence orderseq ;
drop table orderlist;

create sequence orderseq   
increment by 1
start with 1
minvalue 1
maxvalue 10000
nocache;

create table orderlist(
	no number  primary key,
	mno number references member(no) on delete cascade, -- 유저번호
	pno number references product(no) on delete cascade, -- 상품 번호 
	opname varchar2(40),
	qty number,
	price number,
	time date,    					-- 결제가 이뤄진 시간
	receiver varchar(40), 	-- 받는 사람
	rv_hp1 varchar2(30),
	rv_hp2 varchar2(30),
	rv_hp3 varchar2(30),
	rv_zip varchar2(20),
	rv_addr1 varchar2(50),
	rv_addr2 varchar2(50)
);

commit;

SELECT * FROM orderlist;
--------------------포토후기-----------------------------

create table review(
 board_no number(38) primary key --게시물 번호
 ,board_name varchar2(30) not null  --글쓴이
 ,board_title varchar2(200) not null--글제목
 ,board_pwd varchar2(20) not null --비번
 ,board_cont varchar2(4000) not null --글내용
 ,board_hit number(38) default 0 --조회수, default 0 제약조건을 주면 해당 제약조건이 설정된 컬럼에 굳이 레
 --코드를 저장하지 않아도 기본값 0이 저장됨.
 ,board_ref number(38)  --원본글과 답변글을 묶어주는 그룹번호 역할
 ,board_step number(38) --원본글이면 0,첫번째 답변글이면 1,두번째 답변글이면 2 => 원본글과 답변글을 구분하는 번
 --호값이면서 몇번째 답변글인가를 알려준다.
 ,board_level number(38) --답변글 정렬순서
 ,board_date date 
 ,board_file1 varchar2(150)
);

select * from review order by board_no desc;

drop table review;

drop table review cascade constraints;

--review 시퀀스 생성
create sequence review_no_seq
start with 1
increment by 1
nocache;

select review_no_seq.nextval from dual;
commit;

insert into review (board_no,board_name,board_title,board_pwd,board_cont) values('1','홍길동','1111','1111','1122222');
 select * from review;


 
 
-----------------포토 후기 댓글------------------------
 
 create table reviewReply(
 reply_no number(38) primary key --댓글 번호
 ,board_no number(38) references review(board_no) on delete cascade --게시글 번호
 ,reply_name varchar2(30) not null --댓글 작성자
 ,reply_title varchar2(200) not null --댓글 제목
 ,reply_pwd varchar2(20) not null --비번
 ,reply_cont varchar2(4000) not null --댓글 내용
 ,reply_hit number(38) default 0 --조회수
 ,reply_ref number(38)  
 ,reply_step number(38) 
 ,reply_level number(38) 
 ,reply_date date -- 댓글 작성 날짜
 );

 
 drop table reviewReply;
 
 select * from reviewReply order by board_no desc;   
 
 --reviewReply 시퀀스 생성
create sequence reviewReply_no_seq
start with 1
increment by 1
nocache;

select reviewReply_no_seq.nextval from dual;

commit;

select table_name, constraint_type, constraint_name, r_constraint_name from user_constraints
where table_name in('REVIEW', 'REVIEWREPLY');