create database if not exists warehouse;
use warehouse;

create table client (
  id int auto_increment primary key,
  name varchar(30) not null,
  address varchar(30) not null,
  email varchar(30) not null,
  age int not null
);


create table product (
  id int auto_increment primary key,
  name varchar(30) not null,
  price real not null,
  quantity int not null
);


create table orderr (
  id int auto_increment primary key,
  clientId int not null,
  productId int not null,
  quantity int not null,
  foreign key (clientId) references Client(id) on delete cascade,
  foreign key (productId) references Product(id) on delete cascade
);