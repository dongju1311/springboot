use shoppy;
select database();
show tables;
select * from member;
desc member;

-- pwd 사이즈 변경
alter table member modify column pwd varchar(100) not null;
desc member;

-- mysql은 수정, 삭제 시 update mode를 변경
SET SQL_SAFE_UPDATES = 0;

delete from member 
where mdate = '2025-10-17';

delete from member
where id = 'test222';

select pwd from member where id='hong';
select count(*) as pwd from member where id = 'hong';

select * from member;

/***********************************
	상품 테이블 생성 : product
***********************************/
show tables;
-- drop table product;
create table product(
	pid		int		auto_increment primary key,	
	name	varchar(200)	not null,
    price	long,
    info	varchar(200),
    rate	double,
    image	varchar(100),
    imgList json
);
desc product;
select * from product;
insert into product(name, price, info, rate, image, imgList) 
	values
    ('후드티', 15000, '분홍 후드티', 3.2, '1.webp', JSON_Array('1.webp','1.webp','1.webp')),
    ('후드티', 15000, '검정색 후드티', 2.2, '2.webp', JSON_Array('2.webp','2.webp','2.webp')),
    ('원피스', 25000, '원피스', 4, '3.webp', JSON_Array('3.webp','3.webp','3.webp')),
    ('반바지', 12000, '반바지', 3.2, '4.webp', JSON_Array('4.webp','4.webp','4.webp')),
    ('티셔츠', 20000, '티셔츠', 5, '4.webp', JSON_Array('5.webp','5.webp','5.webp')),
    ('스트레치 비스트 드레스', 55000, '스트레치 비스트 드레스', 3, '6.webp', JSON_Array('6.webp','6.webp','6.webp')),
    ('자켓', 115000, '자켓', 3.5, ' 7.webp', JSON_Array('7.webp','7.webp','7.webp'));
    

select * from product ;  
desc product;  

select pid, name, price, info, rate, trim(image) as image, imgList from product
where pid = 1;


/**********************************************
	상품상세정보 테이블 생성 : product_detailinfo
**********************************************/
show tables;
drop table product_detailinfo;
create table product_detailinfo (
	did			int				auto_increment		primary key,
    title_en	varchar(100)	not null,
    title_ko	varchar(100)	not null,
    pid			int				not null,
    list		json,  -- nodeJS(JSON), springboot(String, List<>)
    constraint fk_product_pid	foreign key(pid)
    references product(pid)
    on delete cascade
    on update cascade
);
desc product_detailinfo;
select * from product_detailinfo;

-- mysql에서 json, csv, excel... 데이터 파일을 업로드 하는 경로
show variables like 'secure_file_priv';

-- mac os
-- set global local_infile = 1;

-- CREATE TEMPORARY TABLE tmp_products (doc JSON);

-- -- 관리자 계정으로
-- SET GLOBAL local_infile = 1;
-- SHOW GLOBAL VARIABLES LIKE 'local_infile';  -- ON 인지 확인

-- LOAD DATA LOCAL INFILE '/Users/leekm/Downloads/products.json'
-- INTO TABLE tmp_products
-- FIELDS TERMINATED BY '\t'
-- LINES TERMINATED BY '\n'
-- (@row) SET doc = CAST(@row AS JSON);

-- SET @j = CAST(LOAD_FILE('/Users/leekm/Downloads/products.json') AS CHAR CHA-- RACTER SET utf8mb4);

-- SET @json = LOAD_FILE('/Users/leekm/Downloads/products.json');

-- SELECT LENGTH(@j), JSON_VALID(@j);

-- SHOW VARIABLES LIKE 'secure_file_priv';

-- ********
SET @json = CAST(LOAD_FILE('/usr/local/mysql-files/products.json') AS CHAR CHARACTER SET utf8mb4);

-- JSON이 잘 읽혔는지 확인
SELECT LENGTH(@json) AS len, JSON_VALID(@json) AS is_valid;
-- len > 0, is_valid = 1 이면 OK


-- 실제 삽입
INSERT INTO product_detailinfo (title_en, title_ko, pid, `list`)
SELECT 
    jt.title_en,
    jt.title_ko,
    jt.pid,
    jt.`list`
FROM JSON_TABLE(
    @json,
    '$[*]' COLUMNS (
        pid        INT          PATH '$.pid',
        title_en   VARCHAR(100) PATH '$.detailInfo.title_en',
        title_ko   VARCHAR(100) PATH '$.detailInfo.title_ko',
        `list`     JSON         PATH '$.detailInfo.list'
    )
) AS jt;


--

select * from product_detailinfo;

-- INSERT INTO product_detailinfo(title_en, title_ko, pid, `list`)
-- SELECT 
--   jt.title_en,
--   jt.title_ko,
--   jt.pid,
--   jt.`list`
-- FROM tmp_products tp,
-- JSON_TABLE(
--   tp.doc,                         -- (배열이면 '$[*]'; 객체면 경로 조정)
--   '$[*]' COLUMNS (
--     title_en VARCHAR(100) PATH '$.detailInfo.title_en',
--     title_ko VARCHAR(100) PATH '$.detailInfo.title_ko',
--     `list`   JSON         PATH '$.detailInfo.list',
--     pid      INT          PATH '$.pid'
--   )
-- ) AS jt;


-- 

-- products.json 파일의 detailinfo 정보 매핑
-- insert into product_detailinfo(title_en, title_ko, pid, list)
-- select 
-- 	jt.title_en,
--     jt.title_ko,
--     jt.pid,
--     jt.list
-- from
-- 	json_table(
-- 		cast(load_file('C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/products.json') 
-- 				AS CHAR CHARACTER SET utf8mb4 ),
-- 		'$[*]' COLUMNS (
-- 			 title_en   	VARCHAR(100)  PATH '$.detailInfo.title_en',
-- 			 title_ko   	VARCHAR(100)  PATH '$.detailInfo.title_ko',
-- 			 list   	json PATH '$.detailInfo.list',
-- 			 pid		int	 PATH '$.pid'
-- 		   )   
--     ) as jt ;

select * from product_detailinfo;

-- pid: 1에 대한 상품정보와 상세정보 출력 
select *
from product p, product_detailinfo pd
where p.pid = pd.pid and p.pid = 1 ;

select * from product;
desc product_detailinfo;

select did, title_en as titleEn, title_ko as titleKo, pid, list
	from product_detailinfo
    where pid = 1;

select * from member;
select now() from dual;

/**********************************************
	상품 QnA 테이블 생성 : product_qna
**********************************************/
show tables;
desc member;
create table product_qna (
	qid			int				auto_increment		primary key,
    title		varchar(100) 	not null,
    content		varchar(200),
    is_complete	boolean,
    is_lock		boolean,
    id			varchar(50)		not null,
    pid			int				not null,
    cdate		datetime,
    constraint fk_product_qna_pid	foreign key(pid)  references product(pid)
		on delete cascade   on update cascade,
	constraint fk_member_id	foreign key(id)  references member(id)
		on delete cascade   on update cascade
);
desc product_qna;
select * from product_qna;

-- mysql에서 json, csv, excel... 데이터 파일을 업로드 하는 경로
show variables like 'secure_file_priv';

SET @json = CAST(LOAD_FILE('/usr/local/mysql-files/productQnA.json') AS CHAR CHARACTER SET utf8mb4);

-- JSON이 잘 읽혔는지 확인
SELECT LENGTH(@json) AS len, JSON_VALID(@json) AS is_valid;
-- len > 0, is_valid = 1 이면 OK

-- product_qna data insert
insert into product_qna(title, content, is_complete, is_lock, id, pid, cdate)
SELECT 
    jt.title,
    jt.content,
    jt.is_complete,
    jt.is_lock,
    jt.id,
    jt.pid,
    jt.cdate
FROM JSON_TABLE(
    @json,
    '$[*]' COLUMNS (
			 title   		VARCHAR(100)  	PATH '$.title',
			 content   		VARCHAR(200)  	PATH '$.content',
			 is_complete 	boolean 		PATH '$.isComplete',
             is_lock 		boolean 		PATH '$.isLock',
			 id				varchar(50)	 	PATH '$.id',
             pid			int				path '$.pid',
             cdate			datetime		path '$.cdate'
		   ) 
) AS jt;

select * from product_qna;
select * from member;

-- hong 회원이 분홍색 후드티(pid:1) 상품에 쓴 QnA 조회
-- 회원아이디(id), 회원명(name), 가입날짜(mdate), 상품명(name), 상품가격(price), 
-- QnA제목(title), 내용(content), 등록날짜(cdate)
select 
	m.id,
    m.name,
    m.mdate,
    p.pid,
    p.name,
    p.price,
    pq.title,
    pq.content,
    pq.cdate
from member m, product p, product_qna pq
where m.id = pq.id and p.pid = pq.pid
	and m.id = 'hong' and p.pid = 1;
    
    
/*********************************************************************
	상품 Return/Delivery 테이블 생성 : product_return
**********************************************************************/
show tables;
create table product_return (
	rid			int				auto_increment	primary key,
    title		varchar(100)	not null,
    description	varchar(200),	
    list		json      
);
desc product_return;
select * from product_return;

-- json 파일 형식은 [ { ~~} ], 배열로 감싼 형식
SET @json = CAST(LOAD_FILE('/usr/local/mysql-files/productReturn.json') AS CHAR CHARACTER SET utf8mb4);


-- JSON이 잘 읽혔는지 확인
SELECT LENGTH(@json) AS len, JSON_VALID(@json) AS is_valid;

-- json_table을 이용하여 데이터 추가
insert into product_return(title, description, `list`)
select 
	jt.title,
    jt.description,
    jt.`list`
FROM JSON_TABLE(
    @json,
    '$[*]' COLUMNS (
			 title   		VARCHAR(100)  	PATH '$.title',
			 description   	VARCHAR(200)  	PATH '$.description',
			 `list` 		json	 		PATH '$.list'
		   ) 
) AS jt;

desc product_return;
select rid, title, description, list from product_return;

/*********************************************************************
	장바구니 테이블 생성 : cart
**********************************************************************/
-- cid, pid, id, size, qty, cdate
create table cart(
	cid			int 	auto_increment		primary key,
    size		char(2)	not null,
    qty			int		not null,
    pid			int		not null,
    id			varchar(50)	not null,
    cdate		datetime 	not null,
    constraint fk_cart_pid	foreign key(pid) references product(pid) 
	on delete cascade		on update cascade,
	constraint fk_cart_id	foreign key(id) references member(id) 
	on delete cascade		on update cascade        
);

show tables;
desc cart;

select now() from dual;
select * from cart;

-- mysql은 수정, 삭제 시 update mode를 변경
SET SQL_SAFE_UPDATES = 0;

select * from cart;
delete from cart where cid in (1,2);
select * from cart;

-- pid, size를 이용하여 상품의 존재 check 
-- checkQty = 1 인 경우 cid(⭕) 유효 데이터
-- checkQty = 0 인 경우 cid(❌) 무효 데이터
SELECT cid, sum(pid=1 AND size='xs') AS checkQty FROM cart GROUP BY cid
order by checkQty desc
limit 1;

select * from cart;

select count(*)
from (
SELECT cid, sum(pid=1 AND size='xs' and id='test') AS checkQty
                	FROM cart
                	GROUP BY cid, id
                	order by checkQty desc
                	limit 1 ) t;
                    
                    
-- 
(
    SELECT 
        cid, 
        (SELECT COUNT(*) FROM cart WHERE pid=1 AND size='xs' AND id='test') AS checkQty
    FROM 
        cart 
    WHERE 
        pid=1 AND size='xs' AND id='test'
    LIMIT 1 -- 실제 cid가 여러 개일 경우 하나만 가져옴
)
UNION ALL
(
    SELECT 
        NULL AS cid, 
        0 AS checkQty
)
ORDER BY 
    checkQty DESC
LIMIT 1; -- 두 행 중 checkQty가 더 큰(즉, 데이터가 있는) 행을 우선 선택

-- 
SELECT 
    MAX(checkQty) AS checkQty,
    MAX(cid) AS cid
FROM (
    SELECT cid, COUNT(*) AS checkQty
    FROM cart
    WHERE pid = 1 AND size = 'xs' AND id = 'test'
    UNION ALL
    SELECT NULL, 0
) t;

--
SELECT 
    MAX(t.cid) AS cid,
    MAX(t.checkQty) AS checkQty
FROM (
    SELECT cid, COUNT(*) AS checkQty
    FROM cart
    WHERE pid = 1 AND size = 'xs' AND id = 'test'
    UNION ALL
    SELECT NULL AS cid, 0 AS checkQty
) AS t;
--

SELECT
  (SELECT cid
     FROM cart
     WHERE pid = 1 AND size = 'xs' AND id = 'test'
     LIMIT 1) AS cid,
  COUNT(*) AS checkQty
FROM cart
WHERE pid = 1 AND size = 'xs' AND id = 'test';



select * from cart;

--
SELECT 
      ifnull(MAX(cid), 0) AS cid,
      COUNT(*) AS checkQty
    FROM cart
    WHERE pid = 1 AND size = 'xs' AND id = 'test';
    
-- 
 select  m.id,
                        p.pid,
                        p.name,
                        p.image,
                           p.price,
                           c.size,
                           c.qty,
                           c.cid,
                           (select sum(c.qty * p.price)
                            from cart c
                            inner join product p on c.pid = p.pid
                            where c.id = 'test') as totalPrice
                   from member m, product p, cart c
                   where m.id = c.id
                    and p.pid = c.pid
                    and m.id = 'test' ;
				
select * from cart;

/*********************************************************************
	장바구니 리스트 VIEW  생성 : view_cartlist
**********************************************************************/
create view view_cartlist
as
select  m.id,
		m.name as mname,
		m.phone,
		m.email,
		p.pid,
		p.name,
		p.info,
		p.image,
	   p.price,
	   c.size,
	   c.qty,
	   c.cid,
       t.totalPrice
   from member m, product p, cart c,
          (select c.id, sum(c.qty * p.price) as totalPrice
			from cart c
			inner join product p on c.pid = p.pid
			group by c.id) as t
   where m.id = c.id
	and p.pid = c.pid
    and c.id = t.id
; 

select id, mname, phone, email, pid, name, info, image, price, size, qty, cid, totalPrice 
from view_cartlist
where id='hong';


/*********************************************************************
	고객센터 테이블 생성 : support
**********************************************************************/
create table support(
	sid			int		auto_increment	primary key,    
    title		varchar(100)	not null,
    content		varchar(200),
    stype		varchar(30)	 not null,
    hits		int,
    rdate		datetime
);

show tables;

/* mac os
-- json 파일 형식은 [ { ~~} ], 배열로 감싼 형식
SET @json = CAST(LOAD_FILE('/usr/local/mysql-files/support_list.json') AS CHAR CHARACTER SET utf8mb4);


-- JSON이 잘 읽혔는지 확인
SELECT LENGTH(@json) AS len, JSON_VALID(@json) AS is_valid;


insert into support(title, stype, hits, rdate)
select 
	jt.title,
    jt.stype,
    jt.hits,
    jt.rdate
from
	json_table(
		@json,
		'$[*]' COLUMNS (
			 title   	VARCHAR(100)  PATH '$.title',
			 stype   	VARCHAR(30)  PATH '$.type',
			 hits   	int PATH '$.hits',
			 rdate		datetime	 PATH '$.rdate'
		   )   
    ) as jt ;
    */


-- windows
insert into support(title, stype, hits, rdate)
select 
	jt.title,
    jt.stype,
    jt.hits,
    jt.rdate
from
	json_table(
		cast(load_file('C:/ProgramData/MySQL/MySQL Server 8.0/Uploads/support_list.json') 
				AS CHAR CHARACTER SET utf8mb4 ),
		'$[*]' COLUMNS (
			 title   	VARCHAR(100)  PATH '$.title',
			 stype   	VARCHAR(30)  PATH '$.type',
			 hits   	int PATH '$.hits',
			 rdate		datetime	 PATH '$.rdate'
		   )   
    ) as jt ;

select * from support;
select distinct stype from support;
desc support;

select sid, title, stype, hits, rdate from support;


/*********************************************************************
	주문 테이블 : orders
**********************************************************************/
use shoppy;
select database();
select * from member;
desc member;
drop table orders;
create table orders (
  oid         		int 			auto_increment	primary key,
  order_code		varchar(40)		not null	unique,		-- 카카오 partner_order_id로 사용
  member_id	      		varchar(50)    	not null,				-- 회원 아이디
  status        	enum('대기중','결제중','결제완료','취소','환불','만료')
					not null default	'대기중',
  shipping_fee     	int				not null 	default 0,	-- 배송비
  discount_amount  	int				not null 	default 0,	-- 할인금액
  total_amount     	int				not null,  				-- 결제요청 금액(= 카카오 amount.total)

  -- 수취/배송 스냅샷
  receiver_name    	varchar(50),
  receiver_phone   	varchar(50),
  zipcode          	varchar(20),
  address1         	varchar(255),
  address2         	varchar(255),
  memo             	varchar(255),
  odate				datetime,
  
  constraint fk_orders_member foreign key(member_id)	references member(id)
		on delete cascade	on update cascade
);

show tables;
desc orders;
select * from product;

/*********************************************************************
	주문 상세 테이블 : order_detail
**********************************************************************/
create table order_detail (
	odid			int				auto_increment		primary key,
	order_code		varchar(40)		not null,	
    pid				int				not null,
    pname			varchar(50),
    size			char(2),
    qty				int,
    pid_total_price	decimal,		-- 상품 토탈가격
    discount		decimal,		-- 할인 금액
	
    constraint fk_order_order_detail foreign key(order_code)	references orders(order_code)
		on delete cascade   on update cascade,
	constraint fk_product_order_detail foreign key(pid)	references product(pid)
		on delete cascade  on update cascade
);

show tables;
desc order_detail;




    
    
    





