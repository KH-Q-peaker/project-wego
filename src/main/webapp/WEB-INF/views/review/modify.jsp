<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title>후기 글 수정</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="shortcut icon" href="/resources/ico/favicon.ico"
	type="image/x-icon" />
<meta name="description" content="등산멤버 모집 커뮤니티" />
<link rel="icon" href="/resources/ico/favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="/resources/css/header.css" />
<link rel="stylesheet" href="/resources/css/footer.css" />
<link rel="stylesheet" href="/resources/css/review-register.css" />
<script src="/resources/js/header.js" defer></script>
<script src="/resources/js/review-register.js" defer></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.4.0/jquery-migrate.min.js"></script>
<script>
const fileList = [];
const fileAltList = [];
<c:forEach var="item" items="${fileList}">
	fileList.push("/img/${fn:substring(item.path, 12, 57)}");
	fileAltList.push("${item.fileName}");
</c:forEach>
</script>
</head>
<body>
	<div class="total-wrap">
		<%@include file="/WEB-INF/views/common/header.jsp"%>
		<section>
			<form action="/review/modify" method="post" class="container">
				<input type="hidden" name="sanReviewId"
					value="${review.sanReviewId}">
				<!-- form 필수값 검증 후 값이 없는 경우 알림 -->
				<div class="alert-window">
					<p>
						산이름, 제목, 내용은<br /> 필수사항입니다.
					</p>
					<button type="button">확인</button>
				</div>
				<!-- -------------- alert-end -------------- -->
				<div class="select-mountain">
					<span>산이름</span> <select name="sanName" required>
						<option value="${review.sanName}">${review.sanName}</option>
					</select>
				</div>
				<div class="title">
					<label for="title">제목</label> <input type="text" name="title"
						id="title" placeholder="제목을 입력하세요.(최소 2자 ~ 최대 20자 가능)"
						minlength="2" maxlength="20" value="${review.title}" required />
				</div>
				<div class="detail">
					<div class="photo"></div>
					<div class="add-photo">
						<div class="top">
							<div class="folder-icon"></div>
							<span>Upload a photo</span> <span class="cancel" type="reset"></span>
						</div>
						<div class="drag-and-drop">
							<div class="picture"></div>
							<p>
								여기로 이미지를 드래그하거나<br /> 파일을 업로드 하세요. (최대 20MB)
							</p>
						</div>
						<button type="button">등록</button>
					</div>
					<div contenteditable="true" name="contents" id="contents">${review.contents}</div>
				</div>
				<div class="buttons">
					<button type="button" id="cancle">취소</button>
					<button type="button" id="upload">수정</button>
				</div>
				<div class="check-again">
					<div class="upload">
						<p>글을 수정하시겠습니까?</p>
						<input type="button" value="아니오" /> <input type="submit"
							value="예" />
					</div>
					<div class="unload">
						<p>
							작성중이던 글이 삭제됩니다.<br /> 취소하시겠습니까?
						</p>
						<input type="button" value="아니오" /> <input type="reset" value="예" />
					</div>
				</div>
			</form>
		</section>
	</div>
	<%@include file="/WEB-INF/views/common/footer.jsp"%>
</body>
</html>