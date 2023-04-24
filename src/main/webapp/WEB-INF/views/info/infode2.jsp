<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>등산로 정보 페이지</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link rel="shortcut icon" href="/resources/ico/favicon.ico" type="image/x-icon" />
<link rel="icon" href="/resources/ico/favicon.ico" type="image/x-icon" />

<link rel="stylesheet" href="/resources/css/header.css" />
<link rel="stylesheet" href="/resources/css/final2.css" />
<link rel="stylesheet" href="/resources/css/footer.css" />
<link rel="stylesheet" href="/resources/css/main.css" />

<script src="/resources/js/header.js" defer></script>
<script src="/resources/js/main.js" defer></script>
<script src="/resources/js/favorite.js" defer></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.4.0/jquery-migrate.min.js"></script>
</head>
<body>
	<div class="total-wrap">
		<%@include file="/WEB-INF/views/common/header.jsp"%>
		
		<section>
		
		
		<div class="container">
           
            <div class="profile">
                <img class="profile-image" src="./img/profile.png" alt=""/>
                
            </div>
            
            <div class="moname">
                <p class="mimoname">산이름: ${sanInfoId.sanName}</p>
            </div>
            <div class="weather">
              <img class="weather-image" src="./svg/sun.svg">
            </div>
            <div class="moaddress">
                <p class="mimoaddress">주소: ${sanInfoId.address }</p>
            </div>
            <div class="like">
                <img class="heart" src="./svg/heart.svg">
            </div>
            <div class="num">
              <p class="likenum">1000++</p>
              </div>

            <div class="content">
                <img  class="contentimg" src="">
                <input type="button"
                    id="mozip"
                    onclick="mozip()"
                    value='등산모집'
                    >

            </div>
            <div class="hugi">
                <img  class="hugiimg" src="">

                <input type="button"
                    id="hugis"
                    onclick="hugi()"
                    value="등산후기"
                    >

           </div>

        
        </div>


      
  </div>



  <div id="content-section">
    <ul class="content-header-menu">
      <li class="content-header-menu-item" id="item-point">
        <a href="./fianl1.html">소개</a>
      </li>
      <li class="content-header-menu-item">
        <a href="./final2.html">등산로 정보</a>
      </li>
      <li class="content-header-menu-item">
        <a href="./fianl3.html">날씨</a>
      </li>
      <li class="content-header-menu-item active">
        <a href="./fianl4.html">주변맛집</a>
      </li>
    </ul>
  </div>

  <!-- 내용물  -->
  <div class="cotents">
    <div class="content1">
      <h2 class="im">${sanInfoId.sanName}-등산로 정보</h2>
      <img class="san" src="./img/hansan.jfif">
      <p class="sanin">${sanInfoId.img}</p>
      <p class="sanin">//난이도(고도): 3</p>
      <p class="sanin">산 위도:${sanInfoId.lon}</p>
      <p class="sanin">산 경도:${sanInfoId.lat}</p>
      

     
    </div>

    

</div>



		
		
		
			<a href="#" class="scrollToTop">
			<img src="/resources/svg/top.svg" />
			</a> 
			<a href="#" class="chat"> <img src="/resources/svg/chat.svg" />
			</a>
		</section>
	</div>
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
	
	
	<script>
	
	${"#hugis"}.click(function() {
		self.location = "/review/";
	});


${"#mozip"}.click(function() {
		self.location = "/party/";
	});

	
	</script>
</body>
</html>