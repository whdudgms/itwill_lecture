package xyz.itiwll.el;

//EL 함수를 사용할 경우 호출될 메소드가 선언된 클래스
public class HelloEL {
	//EL 함수를 사용할 경우 호출될 메소드 - 반드시 정적 메소드로 선언
	public static String hello(String name) {
		return name+"님, 안녕하세요.";
	}
}
