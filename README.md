## 👉🏼 1석3조 뉴스피드 팀 프로젝트

### 🙋‍♀️ 1석3조 뉴스피드 팀 프로젝트 설명
- **### 🙋‍♀️ 1석3조 뉴스피드 팀 프로젝트**는 <u>Spring을 활용하여 뉴스피드를 구현</u>했습니다.
- **팀원 : 조예인, 이민정, 장윤혁, 전서연, 이범서**


## 🛠 목차

1. [👩🏻‍ API 명세](#-API-명세)
2. [👩 ERD](#-ERD)
3. [💥 한계점](#-한계점)
4. [📚 STACKS](#-STACKS)

<br>   

## 👩🏻‍ API 명세

### 로그인
 기능   | Method | URL     | Request | Response                       | 상태코드         
|------|--------|---------|---------|--------------------------------|--------------|
| 로그인  | POST   | /login  | 요청 body | String : 로그인 성공 Header에 JWT 발급 | 200: OK      |

### 회원
 기능               | Method | URL             | Request                | Response | 상태코드    
|------------------|--------|-----------------|------------------------|----------|---------|
| 회원 생성(회원 가입)     | POST   | /users/signup | 요청 body                | 등록 정보    | 201 CREATED |
| 회원 조회         | GET    | /users          | id, nickname Param                    | 다건 응답 정보 | 200: OK |
| 회원 정보 <br/>프로필 사진, 닉네임, 한 줄 소개 수정 | PATCH    | /users/me | 요청 body | 수정 정보    | 200: OK |
| 회원 정보 <br/>비밀번호 수정    | PATCH    | /users/me/password | 요청 body | 단건 응답 정보       | 200: OK |
| 회원 삭제 (회원탈퇴)         | DELETE    | /users | 요청 body         | -        | 200: OK |
| 회원 복구         | PUT    | /users/restore | 요청 body         | 단건 응답 정보        | 200: OK |

### 게시물
 기능           | Method | URL                    | Request              | Response | 상태코드       
|--------------|--------|------------------------|----------------------|----------|------------|
| 게시물 생성       | POST   | /posts            | 요청 body              | 등록 정보    | 201 CREATED |
| 전체 게시물 조회    | GET    | /posts             | 요청 param(페이지 번호, 크기) | 다건 응답 정보 | 200: OK  |
| 선택 게시물 조회    | GET    | /posts/{postId} | path postId          | 단건 응답 정보 | 200: OK  |
| 게시물 수정       | PUT  | /posts/{postId} | path postId, 요청 body | 수정 정보    | 200: OK  |
| 선택 게시물 삭제    | DELETE | /posts/{postId} | path postId          | -        | 200: OK   |
| 선택 게시물 삭제    | PUT | /posts/{postId}/restore | path postId          | 단건 응답 정보 | 200: OK   |
| 친구 게시물 전체 조회 | GET | /posts/friend-post | 요청 param(페이지 번호, 크기) | 다건 응답 정보 | 200: OK   |

### 친구
 기능               | Method | URL            | Request                                             | Response | 상태코드    
|------------------|--------|----------------|-----------------------------------------------------|----------|---------|
| 친구 요청     | POST   | /friends/request | param : receiverId                                  | String: 친구 요청이 전송되었습니다.| 200: OK |
| 친구 수락 or 거절 | PATCH    | /friends/response | param : requesterId,<br> 요청 body : isAcceptOrReject | 친구 수락 성공! or String : 친구 거절 성공 | 200: OK |
| 친구 요청 목록 조회        | GET    | /friends/request-list | -                                                   | 다건 응답 정보 | 200: OK |
| 친구 목록 조회 | GET    | /friends/friend-list | -                                                   | 다건 응답 정보    | 200: OK |
| 친구 전체 삭제    | DELETE    | /friends/delete-all | -                                                   | String : “로그인 사용자의 친구관계 전체 삭제 완료”        | 200: OK |
| 친구 1명 삭제         | DELETE    | /friends/delete	 | param : deleteId                                    | String : “삭제 완료"       | 200: OK |


### 댓글
 기능              | Method | URL                     | Request    | Response | 상태코드      
|-----------------|--------|-------------------------|------------|----------|-----------|
| 댓글 생성(선택 일정)    | POST   | /comments/{postId}  | path postId, 요청 body | 등록 정보    | 201 CREATED |
| 전체 댓글 조회(선택 일정) | GET    | /comments/{postId}  | path postId | 다건 응답 정보 | 200: OK |
| 선택 댓글 수정        | PUT  | /comments/{commentId} | path commentId, 요청 body | 수정 정보    | 200: OK |
| 선택 댓글 삭제        | DELETE | /comments/{commentId} | path commentId | -        | 200: OK |

## 👩 ERD
![Image](https://github.com/user-attachments/assets/e9e7cf5c-97b0-4eb9-8625-a9ff9162042f)

## 💥 한계점

- JWT 사용 시, 회원 삭제를 논리적 삭제(Soft Delete) 방식으로 처리할 경우 발생하는 토큰 기반의 한계점

## 📚 STACKS
<div align=center> 
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> 
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
  <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
</div>

