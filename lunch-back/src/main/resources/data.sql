

-- 테이블 새로 만들 시 한번 실행

-- Role 데이터 삽입
insert into roles(role_id, role_description, role_name) value
                      (1, '관리자','admin'),
                      (2, '식당','restaurant'),
                      (3,'소비자','consumer');

insert into member(member_id,member_email,member_name,member_password,member_phone) value(1,'store1@naver.com','김상균','$2a$10$QCFGPPyzISFkwVI8JfwJJO7JlnHZQGHqpBCCYqhIP.Lg5AYLN2vRS','010-7234-0646');
insert into member(member_id,member_email,member_name,member_password,member_phone) value(2,'store2@naver.com','김상균','$2a$10$QCFGPPyzISFkwVI8JfwJJO7JlnHZQGHqpBCCYqhIP.Lg5AYLN2vRS','010-7234-0646');
insert into member(member_id,member_email,member_name,member_password,member_phone) value(3,'store3@naver.com','김상균','$2a$10$QCFGPPyzISFkwVI8JfwJJO7JlnHZQGHqpBCCYqhIP.Lg5AYLN2vRS','010-7234-0646');
insert into member(member_id,member_email,member_name,member_password,member_phone) value(4,'test1@naver.com','김상균','$2a$10$QCFGPPyzISFkwVI8JfwJJO7JlnHZQGHqpBCCYqhIP.Lg5AYLN2vRS','010-7234-0646');
insert into member(member_id,member_email,member_name,member_password,member_phone) value(5,'test2@naver.com','김상균','$2a$10$QCFGPPyzISFkwVI8JfwJJO7JlnHZQGHqpBCCYqhIP.Lg5AYLN2vRS','010-7234-0646');
insert into member(member_id,member_email,member_name,member_password,member_phone) value(6,'test3@naver.com','김상균','$2a$10$QCFGPPyzISFkwVI8JfwJJO7JlnHZQGHqpBCCYqhIP.Lg5AYLN2vRS','010-7234-0646');


insert into member_roles values
                             (1,2);
insert into member_roles values
    (2,2);
insert into member_roles values
    (3,2);
insert into member_roles values
    (4,3);
insert into member_roles values
    (5,3);
insert into member_roles values
    (6,3);


-- store 데이터 삽입
#
insert into store(store_id, business_number, store_description, store_image, store_name, member_member_id) values (1,'111-111','1번 스토어 설명','이미지','1번 스토어 이름',1);
insert into store(store_id, business_number, store_description, store_image, store_name, member_member_id) values (2,'111-111','2번 스토어 설명','이미지','2번 스토어 이름',2);
insert into store(store_id, business_number, store_description, store_image, store_name, member_member_id) values (3,'111-111','3번 스토어 설명','이미지','3번 스토어 이름',3);

-- menu 데이터 삽입
insert into menu(menu_id, calorie, carbs, fat, is_sold_out, menu_description, menu_image, menu_name, menu_price, protein, store_store_id) VALUE (1,10,20,30,0,'1메뉴 설명','이미지.png','메뉴 1','3000',40,1);
insert into menu(menu_id, calorie, carbs, fat, is_sold_out, menu_description, menu_image, menu_name, menu_price, protein, store_store_id) VALUE (2,10,20,30,0,'2메뉴 설명','이미지.png','메뉴 2','4000',40,1);
insert into menu(menu_id, calorie, carbs, fat, is_sold_out, menu_description, menu_image, menu_name, menu_price, protein, store_store_id) VALUE (3,10,20,30,0,'3메뉴 설명','이미지.png','메뉴 3','5000',40,1);

insert into menu(menu_id, calorie, carbs, fat, is_sold_out, menu_description, menu_image, menu_name, menu_price, protein, store_store_id) VALUE (4,20,30,30,0,'4메뉴 설명','이미지.png','메뉴 4','6000',40,2);
insert into menu(menu_id, calorie, carbs, fat, is_sold_out, menu_description, menu_image, menu_name, menu_price, protein, store_store_id) VALUE (5,20,40,30,0,'5메뉴 설명','이미지.png','메뉴 5','7000',40,2);
insert into menu(menu_id, calorie, carbs, fat, is_sold_out, menu_description, menu_image, menu_name, menu_price, protein, store_store_id) VALUE (6,20,50,30,0,'6메뉴 설명','이미지.png','메뉴 6','8000',40,3);


#
#
# DELIMITER $$
#
# DROP PROCEDURE IF EXISTS GenerateOrderData;
# CREATE PROCEDURE GenerateOrderData(IN total_orders INT)
# BEGIN
#     DECLARE i INT DEFAULT 1;
#     DECLARE last_order_id INT;
#     DECLARE random_limit INT;
#
#     WHILE i <= total_orders DO
#             -- order_entity 데이터 삽입
#             INSERT INTO order_entity (member_member_id, store_store_id, order_date, is_pay)
#             VALUES (
#                        FLOOR(1 + (RAND() * 6)), -- member_id는 1~6 사이 랜덤 값
#                        FLOOR(1 + (RAND() * 3)), -- store_id는 1~3 사이 랜덤 값
#                        DATE_ADD(CURDATE(), INTERVAL FLOOR(RAND() * 30) DAY), -- 현재 날짜 기준으로 0~30일 사이 랜덤
#                        IF(RAND() > 0.5, TRUE, FALSE) -- is_pay는 TRUE/FALSE 랜덤 값
#                    );
#
#             -- 최근 생성된 order_entity의 ID 가져오기
#             SET last_order_id = LAST_INSERT_ID();
#
#             -- 랜덤 LIMIT 값을 계산
#             SET random_limit = FLOOR(1 + (RAND() * 5)); -- 1~5 사이 랜덤 값
#
#             -- 각 order_entity에 대해 order_detail 1~5개 랜덤 삽입
#             INSERT INTO order_detail (order_order_id, menu_name, menu_price, quantity)
#             SELECT
#                 last_order_id,
#                 CONCAT('메뉴 ', FLOOR(1 + (RAND() * 6))), -- menu_name
#                 FLOOR(1000 + (RAND() * 10000)), -- menu_price
#                 FLOOR(1 + (RAND() * 5)) -- quantity
#             FROM (SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5) temp
#             LIMIT random_limit; -- 사전에 계산된 변수 사용
#
#             SET i = i + 1;
#         END WHILE;
# END$$
#
# DELIMITER ;
#
# CALL GenerateOrderData(1000); -- 1000개의 주문 데이터 생성