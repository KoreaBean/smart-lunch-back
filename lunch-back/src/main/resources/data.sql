

-- Role 데이터 삽입
#
insert into roles values
                      (1, '관리자','admin'),
                      (2, '식당','restaurant'),
                      (3,'소비자','consumer');

insert into member(member_id,member_email,member_name,member_password,member_phone) value(1,'test1@naver.com','김상균','$2a$10$QCFGPPyzISFkwVI8JfwJJO7JlnHZQGHqpBCCYqhIP.Lg5AYLN2vRS','010-7234-0646');


insert into member_roles values
                             (1,2);

-- store 데이터 삽입
#
insert into store(store_id, business_number, store_description, store_image, store_name, member_member_id) values (1,'111-111','스토어 설명','이미지','가게 이름',1)

# insert into store value(store_id)
#
