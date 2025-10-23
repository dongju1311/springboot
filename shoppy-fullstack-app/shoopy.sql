/** 데이터베이스 생성 **/
create database shoppy;

/** 데이터베이스 열기 **/
use shoppy;
select database();

/** 테이블 목록 확인 **/
show tables;

/** member 테이블 생성 **/
create table member(
	id		varchar(50) primary key,
    pwd		varchar(50) not null,
    name 	varchar(20) not null,
    phone 	char(13),
	email 	varchar(50) not null,
    mdate	date
);
show tables;
desc member;

select * from member;
select count(id) from member where id='test';
select count(*) from member where id='test' and pwd='1234';

/** pwd 사이즈 변경 **/
alter table member modify column pwd varchar(100) not null;
/** my sql은 수정 삭제시 update 모드를 변경 **/
set SQL_SAFE_UPDATES = 0;

delete from member where mdate = "2025-10-17";
delete from member where id = 'test222';

select pwd from member where id = 'hong';
select count(*) as pwd from member where id = 'hong';

/*******************************************
	상품 테이블 생성 : product
*******************************************/

create table product(
	pid		int		auto_increment primary key,
    name	varchar(200)	not null,
    price	long,
	info	varchar(200),
    rate	double,
    image	varchar(100),
    imgList	json
);

desc product;
select * from product;
insert into product(name,price,info,rate,image,imgList)
	values
		('후드티',15000,'검정색 후드티',2.2,'2.webp',JSON_ARRAY('2.webp','2.webp','2.webp')),
		('원피스',20000,'원피스',4,'3.webp',JSON_ARRAY('3.webp','3.webp','3.webp')),
        ('반바지',12000,'반바지',3.2,'4.webp',JSON_ARRAY('4.webp','4.webp','4.webp')),
        ('티셔츠',20000,'티셔츠',5,'5.webp',JSON_ARRAY('5.webp','5.webp','5.webp')),
        ('스트레치 비스트 드레스',55000,'스트레치 비스트 드레스',3,'6.webp',JSON_ARRAY('6.webp','6.webp','6.webp')),
        ('자켓',115000,'자켓',3.5,'7.webp',JSON_ARRAY('7.webp','7.webp','7.webp'));

/*******************************************
	상품상세정보 테이블 생성 : product_detailinfo
*******************************************/
drop table product_detailinfo;
select * from product_detailinfo;
create table product_detailinfo(
	did			int		auto_increment		primary key,
    title_en	varchar(100)	not null,
    title_ko	varchar(100) 	not null,
    pid			int				not null,
    list		json,
    constraint	fk_product_pid	foreign key(pid)
    references	product(pid)
    on delete cascade
    on update cascade
);
desc product_detailinfo;
select * from product_detailinfo;
-- mysql에서 json, csv, excel.. 데이터 파일을 업로드 하는 경로
show variables like 'secure_file_priv';
-- products.json 파일의 detailinfo 정보 매핑
insert into product_detailinfo(title_en,title_ko,pid,list)
	select	
    jt.title_en,	
    jt.title_ko,
    jt.pid,
    jt.list
    from
    json_table(
		cast(load_file('C:/ProgramData/MySQL/MYSQL Server 8.0/Uploads/products.json')
			AS CHAR CHARACTER SET utf8mb4),
            '$[*]' COLUMNS (
				title_en	VARCHAR(100)	PATH	'$.detailInfo.title_en',
                title_ko	VARCHAR(100)	PATH	'$.detailInfo.title_ko',
                list		json			PATH	'$.detailInfo.list',
                pid			int				PATH	'$.pid'
                )
    ) as jt;
-- pid : 1에 대한 상품정보와 상세정보 출력
select * from product p, product_detailinfo pd
where p.pid = pd.pid
	and p.pid=1;
select * from product_detailinfo;

select did, title_en as titleEn, title_ko as titleKo, pid, list from product_detailinfo where pid = 1;
/*******************************************
	상품상세정보 테이블 생성 : product_qna
*******************************************/
drop table product_qna;
create table product_qna(
	qid			int		auto_increment		primary key,
    title		varchar(100) 	not null,
    content		varchar(200),
    is_complete	boolean,
    is_lock		boolean,
    id			varchar(50)		not null,
    pid			int				not null,
    cdate		datetime,
    constraint	fk_product_qna_pid	foreign key(pid) references	product(pid)
    on delete cascade on update cascade,
    constraint fk_member_id foreign key(id) references member(id)
    on delete cascade on update cascade
);
desc product_qna;
select * from product_qna;
-- mysql에서 json, csv, excel.. 데이터 파일을 업로드 하는 경로
show variables like 'secure_file_priv';
-- product_qna.json data insert
insert into product_qna(title,content,is_complete,is_lock,id,pid,cdate)
	select	
    jt.title,	
    jt.content,
    jt.is_complete,
    jt.is_lock,
    jt.id,
    jt.pid,
    jt.cdate
    from
    json_table(
		cast(load_file('C:/ProgramData/MySQL/MYSQL Server 8.0/Uploads/productQnA.json')
			AS CHAR CHARACTER SET utf8mb4),
            '$[*]' COLUMNS (
				title		VARCHAR(100)	PATH	'$.title',
                content		VARCHAR(200)	PATH	'$.content',
                is_complete	BOOLEAN			PATH	'$.isComplete',
                is_lock		BOOLEAN			PATH	'$.isLock',
                id			VARCHAR(50)		PATH	'$.id',
                pid			int				PATH	'$.pid',
                cdate		datetime		PATH	'$.cdate'
                )
    ) as jt;
select * from product_qna;
select qid, title, content, is_complete as isComplete, is_lock as isLock, id, pid, cdate from product_qna where pid = 1;
-- hong 회원이 분홍생 후드티(pid:1) 상품에 쓴 QNA 조회
-- 회원 아이디(id), 회원명(name), 가입날짜(mdate), 상품명(name), 상품가격(price),
-- QnA제목(title), 내용(content), 등록날짜(cdate)
select 
	m.id,
    m.name,
    m.mdate,
    p.name,
    p.pid,
    p.price,
    pq.title,
    pq.content,
    pq.cdate
from member m, product p, product_qna pq
	where m.id = pq.id and p.pid = pq.pid
		and m.id = 'hong' and p.pid = 1;
/*******************************************
	상품 Return/Delivery 테이블 생성 : product_return
*******************************************/
create table product_return(
	rid			int		auto_increment		primary key,
    title		varchar(100) 	not null,
    description	varchar(200),
    list		json
);
show variables like 'secure_file_priv';
-- product_qna.json data insert
insert into product_return(title,description,list)
	select	
    jt.title,	
    jt.description,
    jt.list
    from
    json_table(
		cast(load_file('C:/ProgramData/MySQL/MYSQL Server 8.0/Uploads/productReturn.json')
			AS CHAR CHARACTER SET utf8mb4),
            '$[*]' COLUMNS (
				title		VARCHAR(100)	PATH	'$.title',
                description	VARCHAR(200)	PATH	'$.description',
                list		json			path	'$.list'
                )
    ) as jt;
    desc product_return;
    select * from product_return;

/*******************************************
	장바구니 테이블 생성 : cart
*******************************************/
-- cid, pid, id, size, qty, cdate 

create table cart(
	cid			int		auto_increment		primary key,
    size		char(2) not null,
    qty 		int		not null,
    pid			int		not null,
    id			varchar(50) not null,
    cdate		datetime	not null,
    constraint fk_cart_pid	foreign key(pid) references product(pid)
    on delete cascade  on update cascade,
    constraint fk_cart_id	foreign key(id) references member(id)
    on delete cascade  on update cascade
);
show tables;
desc cart;
select * from cart;
set SQL_SAFE_UPDATES = 0;

delete from cart where qty = 1;

-- pid, size를 이용하여 상품의 존재 check 
-- checkQty = 1 인 경우 cid(⭕) 유효 데이터
-- checkQty = 0 인 경우 cid(❌) 무효 데이터
SELECT 
      ifnull(MAX(cid), 0) AS cid,
      COUNT(*) AS checkQty
    FROM cart
    WHERE pid = 1 AND size = 'xs' AND id = 'test';

select cid, sum(pid=1 and size='xs') as checkQty from cart group by cid order by checkQty desc limit 1;



