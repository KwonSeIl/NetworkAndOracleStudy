package com.sist.main;
/*
 * 			-----------|---------- => 동기화(싱글 쓰레드) ==> 단점(A가 멈추면 B는 수행할 수 없음)
 * 														=> 데드락 / 스타베이션 
 * 														=> 이더넷(단점: 순서가 없음)
 * 				A			B
 * 			
 * 	
 * 			---------------------- => 비동기화(멀티쓰레드)
 * 			 A	 A  	A	  A
 * 				B	B		B	B..
 */
public class MainClass_single {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long start=System.currentTimeMillis();
		
		for(int i=0;i<100;i++)
		{
			System.out.print("★");
		}
		long end=System.currentTimeMillis();
		System.out.println("소요시간:"+(end-start));
		
		for(int i=0;i<100;i++)
		{
			System.out.print("☆");
		}
		end=System.currentTimeMillis();
		System.out.println("소요시간:"+(end-start));
	}

}
