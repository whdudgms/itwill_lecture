package xyz.itwill07.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

//횡단관심모듈 - Advice 클래스
public class JoinPointAdvice {
	//Around Advice 메소드를 제외한 나머지 Advice 메소드는 일반적으로 반환형을 void로 작성하고
	//매개변수를 작성하지 않거나 JoinPoint 인터페이스로 선언된 매개변수 작성 가능
	// => Advice 클래스의 메소드를 작성 규칙에 맞지 않게 작성할 경우 IllegalArgumentException 발생
	//JoinPoint 객체 : 타겟메소드 관련 정보가 저장된 객체
	// => 스프링 컨테이너가 Advice 클래스의 메소드를 호출할 때 타겟메소드 관련 정보를 JoinPoint
	//객체에 저장하여 매개변수에 전달
	// => Advice 클래스의 메소드에서 타겟메소드 관련 정보가 필요한 경우 매개변수를 선언하여 사용
	
	//Before Advice 메소드
	public void beforeDisplay(JoinPoint joinPoint) {
		//System.out.println("[before]핵심관심코드 실행 전에 삽입되어 실행될 횡단관심코드");
		
		//JoinPoint.getTarget() : 타겟메소드를 호출한 객체(핵심관심모듈)를 반환하는 메소드
		//Object.getClass() : 객체를 생성한 클래스의 Class 객체(Clazz)를 반환하는 메소드
		//Class.getName() : Class 객체에 저장된 클래스의 이름(패키지 포함)을 문자열로 반환하는 메소드
		//System.out.println(joinPoint.getTarget().getClass().getName());
		
		//Class.getSimpleName() : Class 객체에 저장된 클래스의 이름(패키지 미포함)을 문자열로 반환하는 메소드
		//System.out.println(joinPoint.getTarget().getClass().getSimpleName());
		
		//JoinPoint.getSignature() : 타겟메소드의 정보가 저장된 Signature 객체를 반환하는 메소드
		//Signature.getName() : 타겟메소드의 이름을 문자열로 반환하는 메소드
		//System.out.println(joinPoint.getSignature().getName());
		
		//JoinPoint.getArgs() : 타겟메소드의 매개변수에 저장된 모든 값(객체)을 제공받아 Object
		//객체 배열로 반환하는 메소드
		//System.out.println(joinPoint.getArgs());
		
		String className=joinPoint.getTarget().getClass().getSimpleName();
		String methodName=joinPoint.getSignature().getName();
		Object[] params=joinPoint.getArgs();
		
		System.out.print("[before]"+className+" 클래스의 "+methodName+"(");
		for(int i=0;i<params.length;i++) {
			System.out.print(params[i]);
			if(i<params.length-1) {
				System.out.print(", ");
			}
		}
		System.out.println(") 메소드 호출");
	}
	
	//After Advice 메소드
	public void displayMessage(JoinPoint joinPoint) {
		//System.out.println("[after]핵심관심코드 실행 후에 무조건 삽입되어 실행될 횡단관심코드");
		
		Object[] params=joinPoint.getArgs();
		System.out.println("[after]학번이 "+params[0]+"인 학생정보를 삭제 하였습니다.");
	}
	
	//After Returning Advice 메소드에는 JoinPoint 인터페이스의 매개변수 외에 Object 클래스의
	//매개변수 선언 가능
	// => 스프링 컨테이너는 Object 클래스의 매개변수에 타겟메소드의 반환값이 저장되도록 전달
	// => 타겟메소드에서 반환되는 값(객체)의 자료형이 고정되어 있는 경우 Object 클래스 대신
	//반환되는 값(객체)의 자료형으로 매개변수 작성 가능
	// => Spring Bean Configuration File의 AOP 설정에서 after-returning 엘리먼트에 반드시
	//returning 속성을 사용하여 반환값을 저장할 매개변수의 이름을 속성값으로 지정
	// => after-returning 엘리먼트에 returning 속성이 없거나 속성값이 잘못 설정된 경우 IllegalArgumentException 발생
	
	//After Returning Advice 메소드
	public void displayName(Object object) {
		//System.out.println("[after-returning]핵심관심코드가 정상적으로 실행된 후에 삽입되어 실행될 횡단관심코드");

		//instanceof 연산자를 사용하여 매개변수에 저장된 객체의 자료형을 구분하여 처리
		if(object instanceof String) {
			String name=(String)object;//명시적 객체 형변환
			System.out.println("[after-returning]"+name+"님, 안녕하세요.");
		}
	}
	
	//After Throwing Advice 메소드에는 JoinPoint 인터페이스의 매개변수 외에 Exception 클래스의
	//매개변수 선언 가능
	// => 스프링 컨테이너는 Exception 클래스의 매개변수에 타겟메소드의 명령 실행시 발생된 
	//예외(Exception 객체)가 저장되도록 전달
	// => 타겟메소드에서 발생되는 예외가 고정되어 있는 경우 Exception 클래스 대신 자식클래스로
	//선언된 매개변수 작성 가능
	// => Spring Bean Configuration File의 AOP 설정에서 after-throwing 엘리먼트에 반드시
	//throwing 속성을 사용하여 Exception 객체를 저장할 매개변수의 이름을 속성값으로 지정
	// => after-throwing 엘리먼트에 throwing 속성이 없거나 속성값이 잘못 설정된 경우 IllegalArgumentException 발생

	//After Throwing Advice 메소드
	public void exceptionHandle(JoinPoint joinPoint, Exception exception) {
		//System.out.println("[after-throwing]핵심관심코드 실행시 예외가 발생된 경우 삽입되어 실행될 횡단관심코드");
		
		String className=joinPoint.getTarget().getClass().getSimpleName();
		String methodName=joinPoint.getSignature().getName();
		
		System.out.println("[after-throwing]"+className+" 클래스의 "+methodName
			+" 메소드에서 발생된 예외 = "+exception.getMessage());
	}
	
	//Around Advice 메소드는 반환형을 Object 클래스로 작성하고 매개변수의 자료형은 
	//ProceedingJoinPoint 인터페이스로 작성
	// => 타겟메소드의 반환값을 제공받아 반환하기 위해 Object 클래스를 반환형으로 작성
	// => 타겟메소드 관련 정보를 ProceedingJoinPoint 인터페이스의 매개변수로 제공받아 
	//Around Advice 메소드에서 사용
	//ProceedingJoinPoint : 타겟메소드 관련 정보를 저장하기 위한 객체
	// => JoinPoint 객체와 다른점은 타겟메소드를 직접 호출하기 위한 메소드 제공
	
	//Around Advice 메소드
	public Object disply(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("[around]핵심관심코드 실행 전 삽입되어 실행될 횡단관심코드");
		//ProceedingJoinPoint.proceed() : 타겟메소드를 호출하는 메소드 - 핵심관심코드 실행
		// => 타겟메소드를 호출하여 반환되는 결과값을 제공받아 저장
		// => Throwable(Error 클래스와 Exception 클래스의 부모클래스) 객체(예외)가 발생되므로
		//예외를 처리하거나 예외를 전달
		Object object=joinPoint.proceed();
		System.out.println("[around]핵심관심코드 실행 후 삽입되어 실행될 횡단관심코드");
		return object;//타겟메소드를 호출하여 반환된 결과값을 타겟메소드를 호출한 명령으로 반환
	}
}
















