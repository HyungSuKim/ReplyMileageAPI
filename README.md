# **DDL & Table, Index Info**  

## **Table**
<img width="623" alt="table" src="https://user-images.githubusercontent.com/32235824/177030435-f579c15a-3f80-4af3-ab9a-6cb6e013bd15.PNG">
  
## **User Account**  
create table user_acct (   
   user_id varchar(255) not null,   
    created datetime(6) not null,   
    mileage integer default 0,   
    updated datetime(6),   
    primary key (user_id)   
) engine=InnoDB  
  
<img width="1089" alt="user_acct" src="https://user-images.githubusercontent.com/32235824/177029621-cf30fac7-d09f-40b0-99c1-43fb404c87b8.PNG">  
  
  ---
## **Review**  
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
create index i_userid on review (user_id)   
create index i_placeid on review (place_id)   

  
<img width="1079" alt="review" src="https://user-images.githubusercontent.com/32235824/177030439-3024ae33-3f13-4b77-b4df-d95484679ab0.PNG">  
  
  ---
## **Mileage History**  
create table mileage_hist (   
    id bigint not null,   
    action varchar(255),   
    bonus_flag CHAR(1) default 'N',   
    created datetime(6) not null,   
    mileage integer,   
    mileage_change integer,   
    place_id varchar(255),   
    use_flag CHAR(1) default 'Y',   
    review_id varchar(255) not null,   
    user_id varchar(255) not null,   
    primary key (id)   
) engine=InnoDB   
alter table mileage_hist    
   add constraint FKqiyixpoqk14oidcq2dccic0a0    
   foreign key (review_id)    
   references review (review_id)   
alter table mileage_hist    
   add constraint FKevurmf3qlrpmsh76ot7wfrrm7    
   foreign key (user_id)   
   references user_acct (user_id)
  
<img width="1226" alt="mileage_hist" src="https://user-images.githubusercontent.com/32235824/177029628-84d1ef81-357b-44d4-be99-c97001810bf1.PNG">