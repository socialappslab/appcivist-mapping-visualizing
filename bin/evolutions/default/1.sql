# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table dataset (
  dataset_id                bigint not null,
  name                      varchar(255),
  api_end_point             varchar(255),
  _hash                     integer,
  tags                      varchar(255),
  datasource_datasource_id  bigint,
  constraint pk_dataset primary key (dataset_id))
;

create table datasource (
  datasource_id             bigint not null,
  name                      varchar(255),
  constraint pk_datasource primary key (datasource_id))
;

create table field (
  field_id                  bigint not null,
  name                      varchar(255),
  api_name                  varchar(255),
  type                      varchar(255),
  _hash                     integer,
  dataset_dataset_id        bigint,
  constraint pk_field primary key (field_id))
;

create table tag (
  tag_id                    bigint not null,
  name                      varchar(255),
  _hash                     integer,
  dataset_dataset_id        bigint,
  constraint pk_tag primary key (tag_id))
;

create sequence dataset_seq;

create sequence datasource_seq;

create sequence field_seq;

create sequence tag_seq;

alter table dataset add constraint fk_dataset_datasource_1 foreign key (datasource_datasource_id) references datasource (datasource_id) on delete restrict on update restrict;
create index ix_dataset_datasource_1 on dataset (datasource_datasource_id);
alter table field add constraint fk_field_dataset_2 foreign key (dataset_dataset_id) references dataset (dataset_id) on delete restrict on update restrict;
create index ix_field_dataset_2 on field (dataset_dataset_id);
alter table tag add constraint fk_tag_dataset_3 foreign key (dataset_dataset_id) references dataset (dataset_id) on delete restrict on update restrict;
create index ix_tag_dataset_3 on tag (dataset_dataset_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists dataset;

drop table if exists datasource;

drop table if exists field;

drop table if exists tag;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists dataset_seq;

drop sequence if exists datasource_seq;

drop sequence if exists field_seq;

drop sequence if exists tag_seq;

