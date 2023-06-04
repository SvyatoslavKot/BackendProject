DROP TABLE  IF EXISTS app_user_client_db;
create TABLE app_user_client_db(
                      id SERIAL primary key ,
                      mail varchar(255),
                      name varchar(255),
                      lastname varchar(255),
                      gender varchar(50),
                      age int,
                      password varchar(255),
                      role varchar(255),
                      status varchar(55)
);
