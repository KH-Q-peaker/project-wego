<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<div class="mypage-container">
	<!-- form 필수값 검증 후 값이 없는 경우 알림 -->
	<!-- <form action="#" method="post"> -->
	<div class="alert-window">
		<p>
			닉네임은 <br /> 필수사항 입니다.
		</p>
		<button type="button">확인</button>
	</div>

	<!--  닉네임 변경  -->
	<div class="nickname-box">
		<div class="nick-header">
			<span>사용할 닉네임을 입력해주세요</span>
		</div>

		<div class="nickname-input">
			<input id="nickname" type="text" name="nickname" placeholder="등산쟁이"
				value="${vo.nickname}" oninput="testtest()"> <span
				class="ion"><ion-icon name="checkmark-circle-outline"
					id="ok-sign"></ion-icon> <ion-icon name="close-circle-outline"
					id="no-sign"></ion-icon></span>
		</div>
		<div class="bottom">
			<span id="isRightText"></span>
		</div>
		<button type="button" class="nickname-send">변경</button>
		<div>
			<a href="#" class="closebutton2"><ion-icon name="close-outline"></ion-icon></a>
		</div>
	</div>
	<script type="module"
		src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
	<script nomodule
		src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>

	<!--</form> -->
	<!--  프로필 메인  -->
	<form action="/profile/image" id="imageForm" method="post"
		enctype="multipart/form-data">
		<div class="profile">
			<div class="profile-image">
				<img id="profileImage" src="/img${UserPicName}" alt="">
			</div>

			<div class="add-profile-image">
				<div class="top">
					<div class="folder-icon"></div>
					<span>Upload a photo</span> <span class="cancel" type="reset"></span>
				</div>
				<div class="drag-and-drop form-group file-upload"
					required="required">
					<div class="picture"></div>

					<div class="form-control" data-message="">
						<input type="file" name="part" id="document_file"
							accept=".jpg, .jpeg, .png">
						<p id="fileText"></p>
					</div>

				</div>
				<button type="button" id="partButton">변경</button>
			</div>
	</form>


	<div class="main-wrap">
		<div class="profile-image-button">
			<img src="/resources/svg/setting.svg" alt="프로필사진변경" />
		</div>

		<!--  사진 변경 옆라인 -->
		<div class="name-badge-setting">
			<div class="div-name-setting">
				<span class="nickname">${vo.nickname}</span> <a class="name-setting"
					href="#"><img src="/resources/img/nickname.png" alt="" /></a>
			</div>
			<!--뱃지모음라인===== -->
			<div class="badge-collection">
				<div class="badge-header">
					<a class="badge-setting" href="/badge/${vo.userId}"><span>Badge Collection</span><img
							src="/resources/svg/badgeset.png" alt="" /></a>
					</span>
				</div>

				<!--적용 -->
				<div class="badge-item1" id="1003">
					<div class="sanBadge"></div>
				</div>
				<div class="badge-item2" id="1">
					<div class="sanBadge"></div>
				</div>
				<div class="badge-item3" id="4">
					<div class="sanBadge"></div>
				</div>
				<div class="badge-item4" id="5">
					<div class="sanBadge"></div>
				</div>
				<div class="badge-item5" id="93">
					<div class="sanBadge"></div>
				</div>
			</div>
		</div>
	</div>
</div>
