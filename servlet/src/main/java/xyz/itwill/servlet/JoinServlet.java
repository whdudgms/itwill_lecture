package xyz.itwill.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//입력페이지(join.html)에서 전달된 입력값(회원정보)를 반환받아 클라이언트에게 전달하는 서블릿
@WebServlet("/join.itwill")
public class JoinServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		
		//현재 실행되는 서블릿은 입력페이지(join.html)의 form 태그를 사용하여 [POST] 방식으로 요청
		// => 서블릿을 [GET] 빙식으로 요청한 경우 비정상적인 요청
		//클라이언트가 웹프로그램을 비정상적으로 요청한 경우 클라이언트에게 에러코드를 전달하거나
		//에러페이지(입력페이지)로 이동되도록 처리
		//HttpServletRequest.getMethod() : 클라이언트의 요청방식(GET 또는 POST)을 반환하는 메소드
		if(request.getMethod().equals("GET")) {//클라이언트가 웹프로그램을 [GET] 방식으로 요청한 경우 - 비정상적인 요청
			/*
			//HttpServletResponse.sendError(int sc) : 클라이언트에게 에러코드(4XX or 5XX)를 전달하는 메소드
			//상태코드(Status Code) : 요청에 대한 응답 관련 정보를 제공하기 위한 정수값
			// => 100 : 처리 중, 200 : 처리 완료(응답결과 제공), 300 : 재요청, 400 : 잘못된 요청, 500 : 실행 오류
			// => HttpServletResponse 인터페이스의 상수(Constant)를 이용하여 상태코드 제공
			//response.sendError(400);//response.sendError(405);
			//response.sendError(HttpServletResponse.SC_BAD_REQUEST);//400
			response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);//405
			return;//요청 처리 메소드 종료
			*/
			
			/*
			//HttpServletResponse.sendRedirect(String url) : 클라이언트에게 URL 주소를 전달하는 메소드
			// => 클라이언트에게 처리결과를 파일로 응답하지 않고 301 상태코드와 URL 주소를 전달하여 응답
			//301 상태코드와 URL 주소를 전달받은 클라이언트는 브라우저의 URL 주소를 변경하고  
			//변경된 URL 주소의 웹프로그램을 요청하여 처리결과를 응답받아 출력 - 리다이렉트 이동
			//response.sendRedirect("/servlet/error.html");//에러페이지 이동
			response.sendRedirect("form.html");//입력페이지 이동
			return;
			*/
			
			out.println("<script type='text/javascript'>");
			out.println("alert('비정상적인 접근입니다.');");
			out.println("location.href='form.html';");
			out.println("</script>");
			return;
		}
		
		//웹프로그램 요청시 전달된 값을 반환받아 저장
		//형식)HttpServletRequest.getParameter(String name) : 전달값을 반환하는 메소드
		// => 전달값은 무조건 문자열(String 객체)로 반환
		// => 전달값을 매개변수에 전달된 이름(식별자)로 구분하여 값 반환
		// => 이름(식별자)로 전달된 값이 없는 경우 null 반환
		String id=request.getParameter("id");

		//입력값에 대한 검증 - 생략 가능 : 보안성을 견고하게 구현하기 위해 작성하는 것을 권장
		if(id==null || id.equals("")) {//입력값이 없는 경우 - 비정상적인 요청
			response.sendRedirect("error.html");
			return;
		}
		
		if(!Pattern.matches("^[a-zA-Z]\\w{5,19}$", id)) {//입력형식이 맞지 않은 경우 - 비정상적인 요청
			response.sendRedirect("error.html");
			return;
		}
		
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<meta charset='UTF-8'>");
		out.println("<title>Servlet</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<h1>회원정보확인</h1>");
		out.println("<hr>");
		out.println("<p>아이디 = "+id+"</p>");
		out.println("</body>");
		out.println("</html>");
	}

}






