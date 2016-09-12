<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>모달 폼</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<script src="//code.jquery.com/jquery-1.12.4.js"></script>

<link rel="stylesheet" type="text/css" href="css/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="css/jquery-ui.theme.css">
<script src="js/jquery-ui.js"></script>

<script type="text/javascript">

$(function(){
	var dialog,
		form,
		emailRegex = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/, 
		name = $("#name"),
		email = $("#email"),
		password = $("#password"),
		//3개 필드 전부 포함
		allFields = $([]).add(name).add(email).add(password),
		tips = $(".validateTips");
	
	function updateTips(t) {
		tips
			.text(t)
			.addClass("ui-state-highlight");
		setTimeout(function(){
			tips.removeClass("ui-state-highlight", 1500);
		}, 500);
	}
	
	
	
	/* 길이 체크 */
	function checkLength(o, n, min, max) {
		if(o.val().length > max || o.val().length < min) {
			o.addClass("ui-state-error");
			updateTips(n + "은(는) "
					+ min + "에서 " + max + "자까지 입력해 주세요.");
			return false;
		} else {
			return true;
		}
	}
	
	/* 정규 표현식 체크 */
	function checkRegexp(o, regexp, n) {
		if( !(regexp.test(o.val()))) { //테스트
			o.addClass("ui-state-error");
			updateTips(n);
			return false;
		}else {
			return true;
		}
	}
	
	// 순서에 따라 아래 실행안함 >> 코드 분석~
	function addUser() {
		var valid = true;
		allFields.removeClass("ui-state-error");
		
		valid = valid && checkLength(name, "이름", 3, 16);
		valid = valid && checkRegexp(name, /^[가-힣a-z]([가-힣0-9a-z_\s])+$/i,	"성명은 3자에서 16자 이내로 작성해 주세요.");
		
		valid = valid && checkLength(email, "이메일", 6, 80);
		valid = valid && checkRegexp(email, emailRegex, "이메일 형식으로 적어주세요(6~80자 이내)");
		
		valid = valid && checkLength(password, "비밀번호", 5, 16);
		valid = valid && checkRegexp(password,  /^[a-z]([0-9a-z_\s])+$/, "비밀번호는 영문자 소문자, 숫자 5~16까지 허용합니다.");
		
		if (valid) {
			/* = form[0].submit(); */
			$("#frm").submit();			
			/* $("#users tbody").append("<tr>" +
				"<td>" + name.val() + "</td>" +
				"<td>" + email.val() + "</td>" +
				"<td>" + password.val() + "</td>" +
				"</tr>"); */
			dialog.dialog("close");
		}
		return valid;
	}
	
	dialog = $("#dialog-form").dialog({
		autoOpen: false,
		height: 350,
		width: 300,
		modal: true,
		buttons: {
			"회원등록" : addUser,
			"취소" : function(){
				dialog.dialog("close");
			}
		},
		//화면 바깥으로 이동(닫기 X) >> 입력자료 초기화 필요~
		close: function() {
			form[0].reset(); //모든 자료 초기화~!!
			allFields.removeClass("ui-state-error");
		} //close 는 컴파일 시 자바엔진이 자동으로 위로 올린다( + 언디파일드 건다) >> 에러 X
	});
	
	// 동적 처리 위해 on메서드를 먼저 등록해야한다 >> ? 생각~
	form = dialog.find("form").on("submit", function(event){
		/* event.preventDefault(); //이전 기능 막음 */
		/* addUser(); */
	});
	
	$("#create-user").button().on("click", function(){
		dialog.dialog("open");
	});
	
	$( "input" ).checkboxradio();
	
});

</script>
</head>
<body>

<!-- 다이어로그 -->
<div id="dialog-form" title="Create new user">
	<p class="validateTips">입력 해주세요.</p>
	<form id="frm" method="post" action="MemberServlet">
		<input type="hidden" name="command" value="member_write">
		<fieldset>
			<label for="name">이름</label>
			<input type="text" name="name" id="name" placeholder="이름 입력" class="text ui-widget-content ui-corner-all">
			<label for="email">이메일</label>
			<input type="text" name="email" id="email" placeholder="이메일 입력" class="text ui-widget-content ui-corner-all">
			<label for="password">비밀번호</label>
			<input type="password" name="password" id="password" placeholder="비밀번호 입력" class="text ui-widget-content ui-corner-all">
			
			<!-- 포커스 기능 정지 : 엔터키 활성 -->
			<input type="submit" tabindex="-1" style="position: absolute; top: -1000px">
		</fieldset>
	</form>
</div>
<!-- 다이어로그 끝 -->
<div class=cont>
	<div id="user-contain" class="ui-widget">
		<h1>Dialog</h1>
		<table id="users" class="ui-widget ui-widget-content">
			<thead>
				<tr class="ui-widget-header">
					<td>No</td>
					<td>이름 &nbsp;</td>
					<td>이메일</td>
					<td>비밀번호</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="member" items="${memberList }">
				<tr>
					<td>${member.no }</td>
					<td>${member.name }</td>
					<td>${member.email }</td>
					<td>${member.password }</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
<input type="button" value="자료 입력" id="create-user">
</div>

</body>
</html>