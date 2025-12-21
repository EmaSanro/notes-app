CREATE DATABASE IF NOT EXISTS notesdb;
USE notesdb;

create table if not exists category (id bigint not null auto_increment, category varchar(255), primary key (id)) engine=InnoDB;
create table if not exists note (created_at date, is_archived bit, updated_at date, id bigint not null auto_increment, content varchar(255), title varchar(255), primary key (id)) engine=InnoDB;
create table if not exists note_categories (categories_id bigint not null, notes_id bigint not null) engine=InnoDB;
alter table category add constraint UKoo4xayr0g0mkbajn7n2m3918u unique (category);
alter table note_categories add constraint FK3w4wktkwxeqsfvjpjjr9pwfr8 foreign key (categories_id) references category (id);
alter table note_categories add constraint FKi8d2tvjumsddwi9cvausg94e0 foreign key (notes_id) references note (id);
