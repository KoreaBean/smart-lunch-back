# # -- member 데이터 삽입
# # insert into member values
# #                        (1,'test@naver.com','김상균','3','01072340646');
# #
-- Role 데이터 삽입
insert into roles values
                      (1, '관리자','ROLE_admin'),
                      (2, '식당','ROLE_restaurant'),
                      (3,'소비자','ROLE_consumer');

# # insert into member_roles values
# #                              (1,1);
#
-- store 데이터 삽입
insert into store values
                      (1,111-111,'test','test가게',1);
