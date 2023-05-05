<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
  <head>
    <meta charset="UTF-8" />
    <title>소셜로그인페이지</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    
    <link rel="shortcut icon" href="/resources/ico/favicon.ico" type="image/x-icon" />
    <link rel="icon" href="/resources/ico/favicon.ico" type="image/x-icon" />
    
    <link rel="stylesheet" href="/resources/css/login.css" />
  </head>
  <body>
    <div class="container">
      <h1>LOGIN</h1>
      <div class="wrap">
        <a href="/login/naver" class="naver"></a>
        <a href="/login/kakao" class="kakao"></a>
        <a href="/login/google" class="google"></a>
      </div>
      <details>
      <summary>TEST</summary>
      <div class="login-wrapper">
        <form method="get" action="/login/tester" id="login-form">
            <input type="text" name="id" placeholder="id"><br>
            <input type="password" name="pw" placeholder="pw"><br>
            <input type="submit" value="Login">
        </form>
    </div>
    </details>
      
    </div>
  </body>
  
    
  
</html>
