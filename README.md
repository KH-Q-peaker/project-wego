# ⛰️ We Go 위고 ⛰️
----   
   ![WegoLogo](https://github.com/uniyunnnn/project-wego-jiyun/assets/103082984/52b8fb54-6ff3-4530-be4d-7927d98de4be)

   ![WegoTitle](https://github.com/uniyunnnn/project-wego-jiyun/assets/103082984/948cc906-f466-400a-b72c-e27987220b39)

>   ## 목차
>   + [기획 배경](#기획-배경)
>   + [프로젝트 소개](#프로젝트-소개)
>   + [프로젝트 기간](#프로젝트-기간)
>   + [팀원 소개, 역할 및 담당기능](#팀원-소개-역할-및-담당기능)
>   + [기능 소개](#기능-소개)
>   + [주요 자료](#주요-자료)
>   + [기술 스택](#기술-스택)
<br/>  

----
## 기획 배경
> 현대 사회에서 사회적 거리두기와 개인의 자아실현에 집중하는 삶이 중요해지면서 등산이 인기 있는 야외 활동으로 부상하고 있습니다. 그러나 등산을 처음 시작하는 사람들이나 길을 잃어버린 등산객들은 지침이 필요한 경우가 많습니다. "Wego"는 이러한 문제를 해결하기 위해 등산 정보 공유와 소통을 중심으로 한 서비스를 제공합니다.

- "Wego"는 등산과 트레킹을 즐기는 사람들을 위한 종합적인 서비스로, 등산로 안내와 주변 정보 제공으로 등산 계획을 도와주고, 커뮤니티를 통해 사용자들끼리 소통과 정보 공유를 할 수 있으며, 챌린지와 랭킹 시스템을 통해 등산에 대한 동기부여를 제공합니다.
![wego기획안첫화면](https://github.com/uniyunnnn/project-wego-jiyun/assets/103082984/8a538c60-402e-4313-8c83-1b355dfee7eb)

[프로젝트 설명서] [Wego기획안.pdf](https://github.com/javahasooingu/project-wego-ingu/blob/main/etc/wego-plan.pdf)
<br/>

---

## 프로젝트 소개
> "Wego"는 등산과 트레킹을 주제로 한 모두를 위한 등산 정보 공유 및 소통 서비스입니다. 이 서비스는 등산을 처음 시작하는 사람들과 등산 경로를 찾는 사람들에게 필요한 정보를 제공하며, 등산 커뮤니티를 통해 등산에 대한 소통과 공유를 가능하게 합니다.

- 등산로 안내: 산림청 공공 데이터를 기반으로 등산로의 위치, 난이도, 경로 등을 제공하여 사용자들이 쉽게 등산 계획을 세울 수 있도록 도와줍니다.

- 주변 정보 제공: 등산로 주변의 화장실, 맛집 등 필요한 정보를 제공하여 사용자들이 편리하게 등산을 즐길 수 있도록 합니다.

- 등산 커뮤니티: 사용자들은 커뮤니티를 통해 등산에 대한 다양한 정보를 공유하고 소통할 수 있습니다. 등산 경험 공유, 새로운 등산로 추천, 장비 팁 등의 활발한 소통이 이루어집니다.

- 챌린지와 랭킹 시스템: 다양한 챌린지와 랭킹 시스템을 도입하여 등산에 대한 동기부여를 제공합니다. 사용자들은 챌린지에 참여하고 랭킹을 올리는 등의 목표를 가지며 등산을 즐길 수 있습니다.

<br/>

---

## 팀원 소개 역할 및 담당기능
>    | 이름        | 역할                     |
>    |:-----------:|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
> |송인구 (Team Leader)|- Project Manager 역할 <br> -- 깃 PR 관리 <br> -- 코드 컨벤션 작성 <br> - 뱃지/랭킹 기능 구현 Frontend / Backend <br> - 로그인 시스템 구현 Backend <br> -- Google, Kakao, Naver 소셜 로그인 <br> -- Interceptor를 통한 로그인 페이지 이동 <br> - 산림청 API Parsing 및 DB 저장 <br> - 산정보 상세 페이지 조회 Backend <br> - AWS EC2, Tomcat9 를 통한 서비스 배포 |
>    |한은혜| - 메인 페이지<br/>-- 산 정보, 모집, 후기 게시판의 각 게시글 랜덤 10개 출력<br/> - 통합 검색 페이지<br/>-- 산 정보, 후기, 모집 게시글에 대한 통합검색<br/>-- 제목과 내용에서 검색 키워드와 일치하는 글 조회<br/> - 등반 후기 게시판<br/>-- 후기 글 작성 및 수정<br/>-- 글 최대 1500자, 이미지 최대 5장 첨부<br/> - 등반 멤버 모집 게시판의 작성 및 수정<br/>-- 모집 글 작성 및 수정<br/>-- 이미지 최대 1장 첨부 <br/> - 좋아요(❤) 기능<br/>-- 회원 한정 좋아요 클릭<br/>-- 산 정보, 후기, 모집 각 게시글의 회원들의 좋아요 수 반영|
>    |이신영| - 산 정보게시판, 모집 게시판, 후기 게시판 메인 Frontend / Backend<br/> - 비동기 무한스크롤 기능<br/> - 비동기 게시글 정렬 <br/> - 검색어 일치하는 검색결과 반환 및 비동기 무한스크롤<br/> - 목록 좋아요반영<br/> - 산상세정보페이지에 좋아요 기능추가 |
>    |김지윤| - 마이페이지 유저페이지 Frontend<br/>- 유저페이지 Backend <br/>- 작성글과 댓글 목록 최신순으로 PageNation 기능<br/>- 알림 Backend<br/>- 알림요소 5가지(댓글,좋아요,대댓글,취소,뱃지획득)이벤트처리<br/>- 알림삭제 및 읽음 상호작용 |
>    |오태경| 산 상세정보 페이지 Frontend |
>    |최새롬|- 알림 Frontend<br/> - 마이페이지 Backend<br/> - 닉네임 유효값 변경 기능<br/> - 프로필 사진 변경 파일첨부<br/> - 유저 선호도 및 취향 기능<br/> - 회원 탈퇴기능<br/> - 좋아요반영 목록 조회<br/> - 내가쓴글 내가쓴 댓글 목록 조회 |
>    |이소진| - 후기글/모집글 상세페이지 조회<br/> - 후기글/모집글 삭제<br/> - 댓글 CRUD<br/> - 신고 접수<br/> - 게시물 블라인드 처리 및 사용자 활동 제한<br/> - 모집글 채팅(WebSocket)<br/> - 모집글 참여 신청/삭제<br/> - 산정보 주변식당 - Kakao-map API|

<br/>

---

## 기능 소개

| 구분                             | 기능                           | 설명                                    |
| ---------------------------------------------------------------- | -------------------------------------------------------------- | --------------------------------------- |
| 회원                             |  카카오, 네이버, 구글 OAuth 활용 한 소셜 로그인<br/> & 로그아웃 | - id, pw, nickname 작성을 통한 회원관리<br/> |
| 메인 게시판                       | 통합 검색, 각 게시판별 게시글 출력                     | - 검색 키워드입력 후 검색 목록 각 게시판별 통합 검색 결과 출력<br/>- 하단 모달창 세미정보 조회<br/> -게시판별별 랜덤 목록 5개씩 출력  |
| 산 정보 게시판                    | 산 정보 목록                 | - 산림청 오픈 API (100대 명산) 활용하여 산 정보 제공<br/>- 산정보게시판 검색(검색결과 없을 시 랜덤산 추천)<br/>- 정렬 기준 좋아요, ㄱㄴㄷ순 |
| 산 정보 게시글                   | 산 상세정보 |- 산 기본정보 (높이, 지역, 소개)<br />\- 100대 명산 선정 이유 또는 산 정보<br />- 산 이미지, 위치주소 <br />- 현재 날씨 ( 온도, 습도, 날씨) OpenWeather API 로 해당 산의 날씨를 시간별 일기 예보와 7일간 일기 예보 표기 <br/>- 카카오 맵 API 활용한 산 주변 맛집 Pin표기|
| 등산 모집 게시판 | 등산 모집 목록                      | \- 모집 사진, 등산 모집 내용, 날짜 등 피드형식으로 표기 <br />- 게시글 좋아요 기능(스크랩형식) <br/> - 작성자 클릭시 작성자 프로필로 이동  |
| 등산 모집 게시글 | 등산 모집 상세 정보                 | \- 모집 사진첨부, 등산 일정 등록(산이름,시간,참여인원수필수), 수정, 삭제, 취소 가능 <br />- 댓글 기능<br />-좋아요기능 <br />- 유저 신고기능   |
| 등산 모집 커뮤니티         | 채팅                           | - 채팅방 생성<br />- 웹소캣으로 실시간 대화 가능                         |
| 등산 후기 게시판          | 등산 후기 목록                      | \- 후기 사진, 후기 내용, 날짜 등 피드형식으로 표기 <br />- 게시글 좋아요 기능(스크랩형식) <br/> - 작성자 클릭시 작성자 프로필로 이동  |
| 등산 후기 게시글        | 등산 후기 상세 정보                 | \- 후기 사진첨부, 후기 산 산이름 필수기재 , 수정, 취소, 삭제 가능 <br />- 댓글 기능<br />- 좋아요 기능 <br />- 유저 신고기능 |
| 랭킹 시스템           | 등산 챌린지                    | \- 전체 및 월별 누적 거리 기준 회원 랭킹 <br />- 제일높왕: 전체 누적 거리 Top3 차트 <br />- 한우물왕: 한곳만 자주간사람  Top3 차트 <br />- 참대장: 모집글 Top3 차트<br />- 후기왕: 후기글 Top3 차트|
| 뱃지 시스템           | 등산 챌린지                    | \- 후기글 작성시 첫 후기글 해당 산뱃지 획득 <br />- 프로필 뱃지 설정 5개 설정가능 |
| 마이 페이지           | 사용자 피드                    | \- 회원정보- 닉네임,프로필 사진 수정 변경가능<br />- 산의 선호도 및 취향입력기능 ( 취향반영 산추천 기능은 미구현) <br />- 자신의 등산일정목록, 좋아요한 글, 내가쓴글과 댓글 목록|
| 유저 페이지          | 다른 유저 피드                 | \- 타회원정보- 닉네임,프로필, 대표 뱃지,유저가 쓴 글과 댓글 확인가능  <br />- 게시글 제목이나 내용 클릭시 해당 글로 이동  <br />- 신고기능 |
| 알림         | 알림                    | \- 나에게 온 알림과 알림의 내용 확인 가능  <br />- 1. 내가 쓴글 에 댓글이 달리거나 내 댓글의 대댓글이 달렸을 경우<br />- 2. 내글에 타인이 좋아요를 눌렀을 시 <br />- 3. 내가 참여한 모집글이 삭제되었을 시엔 긴급<br />- 4. 후기글작성으로 뱃지를 획득했을시 <br />- 해당 알림클릭시 해당 글로 바로 가기 기능 |

<br/>

---

## 주요 자료
-   [NOTION🖋️](https://hellou8363.notion.site/Final-Project-7dfc2482bc6f474cacbeff6998aecb66)
-   [FIGMA🎨](https://www.figma.com/file/4Gfranq8Iqz4pSebwzUc1W/Final-project?type=design&node-id=0%3A1&t=knivKX1pR5HAgIEC-1)
-   [wegoERD🗜](https://github.com/javahasooingu/project-wego-ingu/blob/main/etc/wego-erd.pdf)
-   [wego코드컨밴션🛠](https://github.com/javahasooingu/project-wego-ingu/blob/main/etc/wego-code-convention.pdf)

<br/>

---


## 기술 스택








>    + 사용 언어 및 프레임워크<br>
>        + <img src="https://img.shields.io/badge/JAVA.v17-ff9306?style=flat-square&logo=openJDK&logoColor=FFFFFF"/>
>           <img src="https://img.shields.io/badge/Spring.v5.3-6DB33F?style=flat-square&logo=Spring&logoColor=FFFFFF"/>
>    + 웹 표준 및 프론트<br>
>      + <img src="https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=HTML5&logoColor=FFFFFF"/>
>        <img src="https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=CSS3&logoColor=FFFFFF"/>
>        <img src="https://img.shields.io/badge/javascript-F7DF1E?style=flat-square&logo=javascript&logoColor=ffffff"/>
>        <img src="https://img.shields.io/badge/jquery-0769AD?style=flat-square&logo=jquery&logoColor=FFFFFF"/>
>    + DB<br>
>        + <img src="https://img.shields.io/badge/oracleSQL-F80000?style=flat-square&logo=oracle&logoColor=FFFFFF"/> 
>           <img src="https://img.shields.io/badge/mybatis-fff?style=flat-square&logo=&logoColor=FFFFFF"/>
>           <img src="https://img.shields.io/badge/hickariCP-000?style=flat-square&logo=&logoColor=FFFFFF"/>
>    + Server, 배포<br>
>        + <img src="https://img.shields.io/badge/AmazonEC2-FF9900?style=flat-square&logo=amazonec2&logoColor=ffffff"/>
>           <img src="https://img.shields.io/badge/tomcat.v9.0-F8DC75?style=flat-square&logo=apachetomcat&logoColor=000000"/>
>    + Tool, 버전관리 <br>
>      + <img src="https://img.shields.io/badge/eclipseide-2C2255?style=flat-square&logo=eclipseide&logoColor=fff"/>
>        <img src="https://img.shields.io/badge/VScode-007ACC?style=flat-square&logo=visualstudiocode&logoColor=fff"/>
>        <img src="https://img.shields.io/badge/git-F05032?style=flat-square&logo=git&logoColor=FFFFFF"/>

## 배포 주소
>  + http://3.25.135.222/
> 
>     + Test Login (Login 화면에서 test 클릭)
>     + ID : tester
>     + PW : wegotester1024
<br/>

---

## 프로젝트 기간
>   KH Final Team Project 2022.01.06 ~ 2022.05.09 (18주)
### 회의 일정
| 날짜            | 회의 내용                 |
| --------------- | ------------------------- |
| 2023년 1월 5일  | 서비스 주제 선정          |
| 2023년 1월 6일  | 필요한 최소 기능 우선순위 |
| 2023년 1월 11일 | 요구사항 명세서 작성      |
| 2023년 1월 16일 - 2023년 1월 19일 | 요구사항 명세서 정리 및 검토, QnA, 기능 추가 |
| 2023년 1월 26일 | 개념적 모델링(ERD) 및 API 작성 |
| 2023년 1월 31일 오후 3:30 | ERD 최종 완성 및 강사님 피드백 |
| 2023년 2월 1일  | RDB 논리적 모델링, 테이블 명세서 작성 |
| 2023년 2월 3일  | HTML ID List 작성, 프론트엔드 업무 분할 |
| 2023년 2월 6일  | 깃허브 협업 설정, 요구사항 명세서 수정 |
| 2023년 2월 7일  | 새로운 기능 추가 회의 |
| 2023년 2월 11일 | 프론트엔트(FE) 스토리보드 설계 |
| 2023년 2월 16일 | FE 피그마 중간점검 회의 |
| 2023년 3월 7일  | 개발 진행 상황 점검 회의 |
| 2023년 3월 14일 | ERD 및 RDB 수정, 테이블 명세서 업데이트 |
| 2023년 3월 30일 | FE 최종 검토 및 수정 회의 |
| 2023년 4월 5일 | FE 최종점검회의 |
| 2023년 4월 12일 | 백엔드(BE)업무분할 및 FE 수정 회의 git협업 준비 |
| 2023년 4월 19일 | BE 기능구현 보고 및 git연동 |
| 2023년 4월 20일 | wego코드컨벤션정의 반영 |
| 2023년 4월 26일 | BE 이슈 및 수정 회의 |
| 2023년 5월 1일 | 미구현기능확인 점검 회의 |
| 2023년 5월 8일 | 최종 검토 및 수정 회의 |
<br/>

---

