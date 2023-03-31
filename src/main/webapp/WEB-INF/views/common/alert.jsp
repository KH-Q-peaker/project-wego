<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<style>

#modal {	
  display: none;
  position: fixed;
  top: 80px;
  right: 30px;
/*   transform: translate(-50%, -50%); */
  width: 240px;
  height: 70px;
  background-color: rgb(255 144 0 / 80%);
  border-radius: 10px;
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 5px 5px rgba(0, 0, 0, 0.22);
  border: 0;
  z-index: 9999;
  align-items: center;
  justify-content: center;
}

#message {
/*   padding: 20px; */
/*   margin: auto; */
  color: white;
  font-size: 14px;
  text-align: center;
  font-weight: bold;
}
</style>

<script>
  // 모달창 표시 함수
  function showModal() {
    $("#modal").show('fast').css('display', 'flex');
  }

  // 모달창 숨기기 함수
  function hideModal() {
	  $("#modal").hide('fast');
  }

  function setMessage(content) {
	  // 모달창 내용 변경
	  $("#message").text(content);
  }
</script>


</head>
<body>

		<div id="modal">
			<div id="message">
			</div>
		</div>
</body>
</html>
