package xyz.itwill09.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//컨트롤러(Controller) : 클라이언트의 모든 요청을 받아 URL 주소를 분석하여 요청 처리 클래스(Model)의
//메소드를 호출하여 데이타 처리 후 JSP 문서(View)로 스레드를 이동하여 응답되도록 프로그램의 흐름 제어

//컨트롤러 역활을 제공하기 위한 클래스 - 서블릿 클래스
// => 클라이언트의 모든 요청을 받아 처리하는 단일 진입점의 역활을 수행하는 서블릿 - Front Controller Pattern
// => web.xml 파일에서 클래스를 서블릿(웹프로그램)으로 등록하고 클라이언트의 모든 요청을 처리
//할 수 있도록 URL 주소의 패턴을 설정
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//클라이언트의 요청을 처리하기 위해 자동 호출되는 메소드를 오버라이드 선언
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.클라이언트의 요청 URL 주소를 분석하여 요구사항을 반환받아 저장
		String requestURI=request.getRequestURI();
		String contextPath=request.getContextPath();
		String command=requestURI.substring(contextPath.length());
		
		//2.요구사항을 비교하여 요청 처리 클래스(Model)로 객체를 생성하여 객체의 메소드를
		//호출하여 클라이언트 요청에 대한 처리 작업
		
	}
}
















