#DDL & Table, Index Info

##User Account
create table user_acct (
   user_id varchar(255) not null,
    created datetime(6) not null,
    mileage integer default 0,
    updated datetime(6),
    primary key (user_id)
) engine=InnoDB

<img width="1091" alt="user_acct" src="https://user-images.githubusercontent.com/32235824/177021225-af34743f-c72d-40f0-a092-418f726411ba.PNG">

##Review
create table review (
   review_id varchar(255) not null,
    attached_photo_ids varchar(255),
    content NVARCHAR(4000) not null,
    created datetime(6) not null,
    place_id varchar(255),
    updated datetime(6),
    use_flag CHAR(1) default 'Y',
    user_id varchar(255),
    primary key (review_id)
) engine=InnoDB
create index i_reviewuserid on review (review_id, user_id)
create index i_placeid on review (place_id)

<img width="1110" alt="review" src="https://user-images.githubusercontent.com/32235824/177021309-359d0c18-7da2-4906-ac01-eaacef680f43.PNG">

##Mileage History
create table mileage_hist (
   id bigint not null,
    action varchar(255),
    bonus_flag CHAR(1) default 'N',
    created datetime(6) not null,
    mileage integer,
    mileage_change integer,
    place_id varchar(255),
    review_id varchar(255),
    use_flag CHAR(1) default 'Y',
    user_id varchar(255),
    primary key (id)
) engine=InnoDB
create index i_userplacereview on mileage_hist (user_id, place_id, review_id)
create index i_placereview on mileage_hist (place_id, review_id)
create index i_userplace on mileage_hist (user_id, place_id)

<img width="1165" alt="mileage_hist" src="https://user-images.githubusercontent.com/32235824/177021330-9170000d-a89d-4cf6-bbdf-1eeae9d692e1.PNG">