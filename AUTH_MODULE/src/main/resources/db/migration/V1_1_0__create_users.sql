DROP TABLE  IF EXISTS app_user_client;
create TABLE app_user_client(
                      id SERIAL primary key ,
                      mail varchar(255),
                      password varchar(255),
                      role varchar(255),
                      status varchar(55)
);
INSERT INTO  app_user_client (id, mail, password,role, status)
VALUES (1, 'test', 'test', 'USER', 'ACTIVE');