# JavaFxHibernate

```sql
create database test;
use test;

create table customer(customer_id int primary key not null auto_increment, customer_name varchar(50) not null, customer_surname varchar(50) not null, date_joined date not null);
create table bot(bot_id int primary key not null auto_increment,bot_name varchar(50),bot_functions varchar(50),bot_owner_id int,foreign key(bot_owner_id) references customer(customer_id) on update cascade);
create table channel(channel_id int primary key not null auto_increment,channel_name varchar(50),channel_description varchar(50),owner_id int,foreign key(owner_id) references customer(customer_id) on update cascade);

select * from customer;
select * from bot;
select * from channel;

drop table bot;
drop table customer;
drop table channel;

drop database test;
```
