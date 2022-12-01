package com.sist.main;
/*
 * 	Thread(서버 => 반드시 쓰레드를 필요로 한다) 
 * 			프로세스: 전체 프로그램(별도의 프로그램 작성해서 동작) => 쓰레드
 * 			자바: 쓰레드 사용하고 있음
 * 				=> main(쓰레드), gc(쓰레드)
 * 				=> 프로그램의 실제 동작은 쓰레드 
 * 				=> 장점)
 * 					1) CPU의 사용률을 향상할 수 있음
 * 					2) 자원을 효율적으로 사용
 * 					   -- 파일, 클래스 ...
 * 					3) 사용자에 대한 응답성이 향상
 * 					4) 작업을 분리해서 사용 => 소스 간결해짐
 * 	=> 생명주기: 동시에 작업을 수행할 수 있다 -> 한가지 작업만 수행(비행기 동작, 총알 동작) 
 * 	   -----
 * 		생성									대기					동작						일시정지
 * 	 Thread t1=new Thread()				사용할 데이터를 수집			run()					sleep()
 * 	 JVM 역할									t1.start()			=====					wait()
 * 		= Thread 이름 부여											재정의(동작 방법 설정)
 * 		  Thread-0
 * 		  Thread-1
 * 		  setName("")
 * 			=> 이름 변경이 가능
 * 		= 순서를 부여
 * 		  0~10 => 10번이 가장 빠름
 * 		  MIN_PRIORITY(0) => gc()
 * 		  NORM_PRIORITY(5) => 사용자 정의 쓰레드
 * 		  MAX_PRIORITY(10) => main
 * 	  	  setPriority() 사용해 설정 
 * 											쓰레드 제거(종료)=> interrupt(), join
 * 	=> 쓰레드
 * 		1) 비동기: 각자 다르게 동작을, 동시에 수행
 * 		2) 동기: 한개의 쓰레드가 종료 -> 다음 쓰레드가 동작 ==> 메소드 앞에 synchronied
 * 		   --- 은행
 * 				출금 쓰레드, 자동이체, 카드 결제...
 * 	= 생성방법
 * 		= Thread를 상속 ===> 네트워크
 * 			class
 * 			class MyThread etends Thread
 * 			{
 * 				public void run()
 * 				{
 * 					동작에 관련된 소스 코딩
 * 				}
 * 			}
 * 		= Runnable을 구현하는 방식 ===> 윈도우
 * 			인터페이스
 * 			class MyThread implements Runable
 * 			{
 * 				public void run()
 * 				{
 * 					작업 내용
 * 				}
 * 			}
 * 			run()를 호출하지 않는다 ==> start() 호출하면 자동으로 run() 호출 
 * 			void start()
 * 			{
 * 				run()
 * 			}
 */
class MyThread extends Thread
{
	public void run() // 동작 내용 정의
	{
		try
		{
			for(int i=1;i<=10;i++)
			{
				System.out.println(getName()+":"+i);
				Thread.sleep(100); // 1/1000초 단위
			}
		}catch(InterruptedException e) {} // 쓰레드는 CheckedException 가지고 있기 때문에, 반드시 예외처리가 필요함 
	}
}
public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyThread t1=new MyThread();
		t1.setName("홍길동");
		t1.setPriority(Thread.NORM_PRIORITY);
		MyThread t2=new MyThread();
		t2.setName("심청이");
		t2.setPriority(Thread.MIN_PRIORITY);
		MyThread t3=new MyThread();
		t3.setName("이순신");
		t3.setPriority(Thread.MAX_PRIORITY);
		System.out.println("t1-이름"+t1.getName()+", 우선순위:"+t1.getPriority());
		System.out.println("t2-이름"+t2.getName()+", 우선순위:"+t2.getPriority());
		System.out.println("t3-이름"+t3.getName()+", 우선순위:"+t3.getPriority());
		System.out.println("==== 동작 ====");
		t1.start();
		t2.start();
		t3.start();

	}

}
