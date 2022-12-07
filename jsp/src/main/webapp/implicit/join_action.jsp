<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- 입력페이지(join_form.jsp)에서 전달된 회원정보를 반환받아 클라이언트에게 전달하는 JSP 문서 - 처리(출력)페이지 --%>
<%
	//비정상적인 요청에 대한 처리 - 클라이언트에게 에러코드 전달하거나 에러페이지(입력페이지)로
	//이동하기 위한 URL 주소 전달
	//request.getMethod() : 클라이언트의 요청방식(GET or POST)을 반환하는 메소드
	if(request.getMethod().equals("GET")) {//JSP 문서를 [GET]방식으로 요청한 경우 - 비정상적인 요청
		//response.sendError(int sc) : 클라이언트에게 에러코드(4XX or 5XX)를 전달하는 메소드
		// => 응답 관련 상태코드는 HttpServletResponse 인터페이스의 상수(Constant)로 제공
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);//에러코드 405 전달
		return;
	}

	//[POST] 방식으로 요청하여 전달된 값에 대한 캐릭터셋 변경
	//request.setCharacterEncoding(String encoding) : 리퀘스트 메세지 몸체부에 저장된 값에 
	//대한 문자형태(CharacterSet)를 변경하는 메소드 - 기본값 : ISO-8859-1
	request.setCharacterEncoding("utf-8");
	
	//전달값을 반환받아 저장
	//request.getParameter(String name) : 전달값을 문자열(String 객체)로 반환하는 메소드
	// => 입력태그의 name 속성값 또는 질의문자열(QueryString)의 이름으로 전달값 구분
	// => 매개변수의 이름으로 전달된 값이 없는 경우 null 반환
	String id=request.getParameter("id");
	String pass=request.getParameter("pass");
	String name=request.getParameter("name");
	String addr=request.getParameter("addr");
	String gender=request.getParameter("gender");
	String job=request.getParameter("job");
	//request.getParameterValues(String name) : 같은 이름으로 전달되는 모든 값을 문자열
	//(String 객체) 배열로 반환하는 메소드
	String[] hobby=request.getParameterValues("hobby");
	String profile=request.getParameter("profile");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP</title>
</head>
<body>
	<h1>회원정보확인</h1>
	<hr>
	<p>아이디 = <%=id %></p>
	<p>비밀번호 = <%=pass %></p>
	<p>이름 = <%=name %></p>
	<p>주소 = <%=addr %></p>
	<p>성별 = <%=gender %></p>
	<p>직업 = <%=job %></p>
	<% if(hobby==null)  { %>
		<p>취미 = 미선택</p>
	<% } else { %>
		<p>취미 =
		<% for(String temp:hobby) { %>
			<%=temp %>&nbsp;
		<% } %>
	<% } %>
	<%-- 엔터(Enter)를 br 태그로 변환하여 출력 처리 --%>
	<p>자기소개 = <br><%=profile.replace("\n", "<br>")%></p>
</body>
</html>