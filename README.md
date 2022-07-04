# **DDL & Table, Index 정보**  

## **Table**
<img width="623" alt="table" src="https://user-images.githubusercontent.com/32235824/177030435-f579c15a-3f80-4af3-ab9a-6cb6e013bd15.PNG">
  
  ---
  
## **User Account**  
  
  <details>
    <summary>자세히 보기</summary>

create table user_acct (   
   user_id varchar(255) not null,   
    created datetime(6) not null,   
    mileage integer default 0,   
    updated datetime(6),   
    primary key (user_id)   
) engine=InnoDB  
  
<img width="1089" alt="user_acct" src="https://user-images.githubusercontent.com/32235824/177029621-cf30fac7-d09f-40b0-99c1-43fb404c87b8.PNG">  
  
  </details>
  
  ---
## **Review**  

  <details>
    <summary>자세히 보기</summary>

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

  </details>
  
  ---
## **Mileage History**  
  
  <details>
    <summary>자세히 보기</summary>

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

  </details>
  
  ---

# **API 사용 예시(POST)**  

### &#128073; Request URL  
POST /events

## ADD  
  
### &#128073; Request Body
```
{
	"type":"REVIEW",
	"action":"ADD",
	"reviewId":String,
	"content":String,
	"attachedPhotoIds":List<String>,
	"userId":String,
	"placeId":String
}
```

### &#128073; Response Success
```
201 Created
{
	"userId": String,
	"placeId": String,
	"reviewId": String,
	"errorDtl": "Success"
}
```

### &#128073; Response Fail 1
```
500 Internal Server Error
{
	"userId": String,
	"placeId": String,
	"reviewId": String,
	"errorDtl": "Duplicate reviewId"
}
```

### &#128073; Response Fail 2
```
405 Method Not Allowed
{
	"userId": String,
	"placeId": String,
	"reviewId": String,
	"errorDtl": "Already Review for place"
}
```
  
  <details>
    <summary>요청 자세히 보기</summary>


### ㅇ 요청 성공  
<img width="718" alt="ADD_Success" src="https://user-images.githubusercontent.com/32235824/177031667-422cfc2a-6822-41ae-a9d7-21148f7b8e44.PNG">  
  
### ㅇ 요청 실패(중복 리뷰 아이디)
<img width="712" alt="ADD_Error_DupReviewId" src="https://user-images.githubusercontent.com/32235824/177031707-9871a1bb-c579-4041-a0a8-cecddd22f391.PNG">  
  
### ㅇ 요청 실패(한 사용자 한 장소 하나의 리뷰 정책)
<img width="710" alt="ADD_Error_OneReviewPolicy" src="https://user-images.githubusercontent.com/32235824/177031714-0899e619-04a1-42af-8715-5f785df3433d.PNG">  
  
### ㅇ 요청 이후 계좌 정보
<img width="732" alt="ADD_GetAcct" src="https://user-images.githubusercontent.com/32235824/177032416-107af347-395c-4957-a149-224e6b3dc9df.PNG">
  
  </details>
  
  ---
## MOD  
  
### &#128073; Request Body
```
{
	"type":"REVIEW",
	"action":"MOD",
	"reviewId":String,
	"content":String,
	"attachedPhotoIds":List<String>,
	"userId":String,
	"placeId":String
}
```

### &#128073; Response Success
```
201 Created
{
	"userId": String,
	"placeId": String,
	"reviewId": String,
	"errorDtl": "Success"
}
```

### &#128073; Response Fail
```
404 Not Found
{
	"userId": String,
	"placeId": String,
	"reviewId": String,
	"errorDtl": "There are no review to update"
}
```
  
  <details>
    <summary>요청 자세히 보기</summary>

### ㅇ 요청 성공  
<img width="708" alt="MOD_Success" src="https://user-images.githubusercontent.com/32235824/177032715-69ace3b1-19a8-4358-88cc-4df1520d540e.PNG">
  
### ㅇ 요청 실패(존재하지 않는 리뷰 수정)  
<img width="709" alt="MOD_NotExistReview" src="https://user-images.githubusercontent.com/32235824/177032735-18ff24b0-f662-4d21-aa69-48c160c85299.PNG">

### ㅇ 요청 이후 계좌 정보
<img width="725" alt="MOD_history" src="https://user-images.githubusercontent.com/32235824/177032737-db7401a8-358b-4c85-b252-7cc2f52c0658.PNG">
  
  </details>
  

  ---
## DELETE
  
### &#128073; Request Body
```
{
	"type":"REVIEW",
	"action":"DELETE",
	"reviewId":String,
	"content":String,
	"attachedPhotoIds":List<String>,
	"userId":String,
	"placeId":String
}
```

### &#128073; Response Success
```
201 Created
{
	"userId": String,
	"placeId": String,
	"reviewId": String,
	"errorDtl": "Success"
}
```

### &#128073; Response Fail
```
404 Not Found
{
	"userId": String,
	"placeId": String,
	"reviewId": String,
	"errorDtl": "There are no review to delete"
}
```
  
  <details>
    <summary>요청 자세히 보기</summary>

### ㅇ 요청 성공
<img width="708" alt="DEL_삭제 성공" src="https://user-images.githubusercontent.com/32235824/177032882-46767b4e-e690-4409-8247-3345cf52f720.PNG">
  
### ㅇ 요청 실패(존재하지 않는 리뷰 삭제)
<img width="713" alt="DEL_존재하지 않는 리뷰" src="https://user-images.githubusercontent.com/32235824/177032885-8fa93f11-9060-416d-bcc5-dc89439abdff.PNG">
  
### ㅇ 요청 이후 계좌 정보
<img width="746" alt="DEL_삭제 이후 계좌" src="https://user-images.githubusercontent.com/32235824/177034500-1f09eb2d-54ec-41c7-a480-24bf193c5cf5.PNG">
  
  </details>

  ---

## 삭제 이후 장소 첫 리뷰 마일리지 적용

  <details>
    <summary>요청 자세히 보기</summary>

### ㅇ 요청 성공
<img width="711" alt="삭제 이후 리뷰 작성" src="https://user-images.githubusercontent.com/32235824/177033094-7dbe1716-2c83-4aa6-8602-408d718dd3c6.PNG">
  
### ㅇ 요청 이후 계좌 정보
<img width="727" alt="삭제 이후 리뷰 작성 결과" src="https://user-images.githubusercontent.com/32235824/177033093-a6b48c04-c46a-4233-b434-b3f6f5c5dc6b.PNG">
  
  </details>

  ---

## 첫 리뷰가 있는 경우 일반 마일리지 적용

  <details>
    <summary>요청 자세히 보기</summary>

### ㅇ 요청 성공
<img width="700" alt="첫리뷰 미적용 요청" src="https://user-images.githubusercontent.com/32235824/177033224-7e3abe62-f45a-409e-8a51-98fc7052214a.PNG">
  
### ㅇ 요청 이후 계좌 정보
<img width="726" alt="첫리뷰 미적용 결과" src="https://user-images.githubusercontent.com/32235824/177033222-2d1243f2-ba7f-4103-aae7-3cef150c47c5.PNG">

  </details>

  ---
  
## 사진 삭제 및 추가 관련

  <details>
    <summary>요청 자세히 보기</summary>

### ㅇ 글과 사진이 있는 리뷰에서 사진을 모두 삭제 (-1점)
<img width="713" alt="사진 삭제" src="https://user-images.githubusercontent.com/32235824/177033565-cba7dae5-bd36-438e-9948-f896117cb2b7.PNG">
  
### ㅇ 글만 작성한 리뷰에서 사진을 추가 (+1점)
<img width="712" alt="사진 추가" src="https://user-images.githubusercontent.com/32235824/177033567-cbfabb2b-a0d4-4015-b2d6-3ba4a4fdea65.PNG">

### ㅇ 사진 모두삭제가 아닌 일부 삭제 (0점)
<img width="710" alt="사진 일부 삭제" src="https://user-images.githubusercontent.com/32235824/177033566-363c9a7c-0924-4755-a6a5-7013dfa204f1.PNG">

### ㅇ 요청 이후 계좌 정보
![사진 삭제 계좌 결과](https://user-images.githubusercontent.com/32235824/177033670-49724193-991d-4fbe-932e-efb2233927c4.png)

  </details>

  ---
# **API 사용 예시(GET)**  
## 사용자 계좌 & History 조회  
  
### &#128073; Request URL  
GET /useracct?userId=  

### &#128073; Response Success
```
200 Ok
{
	"userId": String,
	"mileage": Integer,
	"history": [
		{
			"id":Integer,
			"userId":String,
			"placeId":String,
			"reviewId":String,
			"mileage":Integer,
			"mileageChange"Integer,
			"action":String
		}
	]
}
```

  <details>
    <summary>요청 자세히 보기</summary>

<img width="753" alt="get" src="https://user-images.githubusercontent.com/32235824/177033871-0be2d3a2-d9ff-4915-83bd-9d945579b46f.PNG">

  </details>

  --- 

  # **개발 환경 및 실행 방법**
  ## **개발 환경**  
  JAVA 18.0.1.1  
  Spring Boot  
  Lombok  
  Spring Boot JPA  
  MySQL 8.0.29  
  ---

  ## **실행 방법**  
  ### @ IDE(IntelliJ)
  프로젝트 파일을 열어 실행.  
  ### @ Windows
  ReplyMileageAPI-0.0.1-SNAPSHOT.jar 파일이 위치한 경로에서 아래의 명령어를 순차적으로 실행.  
  > \triple_works\ReplyMileageAPI>gradlew.bat  
    \triple_works\ReplyMileageAPI>gradlew build  
    \triple_works\ReplyMileageAPI>cd build  
    \triple_works\ReplyMileageAPI\build>cd libs  
    \triple_works\ReplyMileageAPI\build\libs>java -jar ReplyMileageAPI-0.0.1-SNAPSHOT.jar  
  (종료 Ctrl + C)
