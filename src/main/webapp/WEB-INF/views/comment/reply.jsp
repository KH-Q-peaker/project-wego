<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.zerock.wego.domain.CommentViewVO" %>
<%-- <%@page import= "java.util.List" %> --%>
<%-- <%@page import= "java.util.ArrayList"%> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%-- <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> --%>
<%-- <c:set var="path" value="${pageContext.request.contextPath}"/> --%>

<script type="text/javascript" src="/resources/js/comment.js" defer ></script>
<script type="text/javascript" src="/resources/js/delete.js" defer ></script>
<script type="text/javascript" src="/resources/js/report.js" defer ></script>

	
				<div class="comments mention">
					<input type="hidden" id="commentId" value="${comment.commentId }"/>
					<input type="hidden" id="mentionId" value="${comment.mentionId }"/>
					<img class="cmtuserPic" src="${comment.userPic }"/>
					<div class="cmtuser">${comment.nickname }</div>
					<div class="cmtdate">
						<fmt:formatDate pattern="MM-dd HH:mm" value="${comment.createdDt}"></fmt:formatDate>
					</div>
					<div class="btns">
						<input type="hidden" id= "commentId" name="commentId" value="${comment.commentId}">
						<c:if test="${comment.userId == sessionScope.__AUTH__.userId}"> <!--  이거 조건 바꿔야된다!!!! -->
						<input type="button" class="modifycmt" name="modifycmt" value="수정" /> 
						<input type="button" class="deletecmt" name="deletecmt" value="삭제" /> 
						</c:if>
					<input type="button" class="reportcmt" name="reportcmt" value="신고" />
				</div>
					<div class="comment">${comment.contents}</div>
				<div class="updatebtn">
					<input type="button" name="updatecls" value="취소" />
					<input type="button" name="updatecmt" value="수정" />
				</div>
			</div>
