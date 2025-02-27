--acik hali
--insert into users values('user1','{noop}user1',true);
--insert into users values('user2','{noop}user2',true);
--insert into users values('user3','{noop}user3',true);

insert into users values('user1','{bcrypt}$2a$10$p83RkVn0qu5ZbFMFP42WYuv9vus4IyYt4MpDoKZtsDYGTjRitDOZS',true);
insert into users values('user2','{bcrypt}$2a$10$TFCE7chfv6dgJmHXz0mG4.unwgbDv2dN1yfTK8M0kFfW.XDNIWMX6',true);
insert into users values('user3','{bcrypt}$2a$10$gq.cbDJMLfLCk7pv5RTQ.edRWcKw8Wbt0sSIkHMZqmVv55wLeuMi.',true);






insert into authorities values('user1','ROLE_USER');
insert into authorities values('user2','ROLE_USER');
insert into authorities values('user2','ROLE_EDITOR');
insert into authorities values('user3','ROLE_USER');
insert into authorities values('user3','ROLE_EDITOR');
insert into authorities values('user3','ROLE_ADMIN');


--data.sql ve shema.sql içi bos ise hata alırız.

--insert into t_owner(id,first_name,last_name) values (1,'Omer','Tuna');
--insert into t_owner(id,first_name,last_name) values (2,'Bugra','Oner');


--insert into t_pet(id,name,birth_date,owner_id) values (1,'Komur','2024-11-01',1);
--insert into t_pet(id,name,birth_date,owner_id) values (2,'Toprak','2023-06-06',2);

INSERT INTO t_owner (id,first_name,last_name) VALUES (1, 'Ziya', 'Ferit');
INSERT INTO t_owner (id,first_name,last_name) VALUES (2, 'Beşir', 'Dal');
INSERT INTO t_owner (id,first_name,last_name) VALUES (3, 'Eda', 'Rize');
INSERT INTO t_owner (id,first_name,last_name) VALUES (4, 'Hadi', 'Duru');
INSERT INTO t_owner (id,first_name,last_name) VALUES (5, 'Pınar', 'Mus');
INSERT INTO t_owner (id,first_name,last_name) VALUES (6, 'Çiğdem', 'Su');
INSERT INTO t_owner (id,first_name,last_name) VALUES (7, 'Aslı', 'Zor');
INSERT INTO t_owner (id,first_name,last_name) VALUES (8, 'Murat', 'Eski');
INSERT INTO t_owner (id,first_name,last_name) VALUES (9, 'Davut', 'Saz');
INSERT INTO t_owner (id,first_name,last_name) VALUES (10, 'Kadir', 'Mutlu');

INSERT INTO t_pet (id,name,birth_date,owner_id) VALUES (1, 'Maviş', '2005-09-07', 1);
INSERT INTO t_pet (id,name,birth_date,owner_id) VALUES (2, 'Donetello', '2008-08-06', 1);
INSERT INTO t_pet (id,name,birth_date,owner_id) VALUES (3, 'Karabaş', '2001-04-17', 1);
INSERT INTO t_pet (id,name,birth_date,owner_id) VALUES (4, 'Joe', '2009-03-07', 2);
INSERT INTO t_pet (id,name,birth_date,owner_id) VALUES (5, 'Canavar', '2010-11-30', 2);
INSERT INTO t_pet (id,name,birth_date,owner_id) VALUES (6, 'Tatlım', '2010-01-20', 3);
INSERT INTO t_pet (id,name,birth_date,owner_id) VALUES (7, 'Samanta', '2008-09-04', 5);
INSERT INTO t_pet (id,name,birth_date,owner_id) VALUES (8, 'Boncuk', '2008-09-04', 5);
INSERT INTO t_pet (id,name,birth_date,owner_id) VALUES (9, 'Şanslı', '2007-08-06', 5);
INSERT INTO t_pet (id,name,birth_date,owner_id) VALUES (10, 'Karaburun', '2009-02-24', 6);
INSERT INTO t_pet (id,name,birth_date,owner_id) VALUES (11, 'Twetty', '2000-03-09', 7);
INSERT INTO t_pet (id,name,birth_date,owner_id) VALUES (12, 'Tarçın', '2000-06-24', 8);
INSERT INTO t_pet (id,name,birth_date,owner_id) VALUES (13, 'Sarı', '2002-06-08', 9);

INSERT INTO t_vet (id,first_name,last_name) VALUES (14, 'Ali', 'Güç');
INSERT INTO t_vet (id,first_name,last_name) VALUES (15, 'Bekir', 'Sağ');
INSERT INTO t_vet (id,first_name,last_name) VALUES (16, 'Esma', 'Kul');
