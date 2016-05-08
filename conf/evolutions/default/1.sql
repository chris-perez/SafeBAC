# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table drink (
  id                        bigint not null,
  name                      varchar(255),
  abv                       double,
  type                      varchar(255),
  constraint pk_drink primary key (id))
;

create table user (
  id                        bigint not null,
  name                      varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  sex                       varchar(255),
  birth_date                timestamp,
  weight                    integer,
  auth_id                   varchar(255),
  constraint pk_user primary key (id))
;

create table user_to_drink (
  id                        bigint not null,
  user_id                   bigint,
  drink_id                  bigint,
  volume                    double,
  time                      timestamp,
  constraint pk_user_to_drink primary key (id))
;

create table user_to_user (
  id                        bigint not null,
  user1_id                  bigint,
  user2_id                  bigint,
  user1is_visible           boolean,
  user2is_visible           boolean,
  constraint pk_user_to_user primary key (id))
;

create sequence drink_seq;

create sequence user_seq;

create sequence user_to_drink_seq;

create sequence user_to_user_seq;

alter table user_to_drink add constraint fk_user_to_drink_user_1 foreign key (user_id) references user (id) on delete restrict on update restrict;
create index ix_user_to_drink_user_1 on user_to_drink (user_id);
alter table user_to_drink add constraint fk_user_to_drink_drink_2 foreign key (drink_id) references drink (id) on delete restrict on update restrict;
create index ix_user_to_drink_drink_2 on user_to_drink (drink_id);
alter table user_to_user add constraint fk_user_to_user_user1_3 foreign key (user1_id) references user (id) on delete restrict on update restrict;
create index ix_user_to_user_user1_3 on user_to_user (user1_id);
alter table user_to_user add constraint fk_user_to_user_user2_4 foreign key (user2_id) references user (id) on delete restrict on update restrict;
create index ix_user_to_user_user2_4 on user_to_user (user2_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists drink;

drop table if exists user;

drop table if exists user_to_drink;

drop table if exists user_to_user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists drink_seq;

drop sequence if exists user_seq;

drop sequence if exists user_to_drink_seq;

drop sequence if exists user_to_user_seq;

