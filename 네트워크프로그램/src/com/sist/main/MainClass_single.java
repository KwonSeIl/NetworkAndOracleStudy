package com.sist.main;
/*
 * 			-----------|---------- => ����ȭ(�̱� ������) ==> ����(A�� ���߸� B�� ������ �� ����)
 * 														=> ����� / ��Ÿ���̼� 
 * 														=> �̴���(����: ������ ����)
 * 				A			B
 * 			
 * 	
 * 			---------------------- => �񵿱�ȭ(��Ƽ������)
 * 			 A	 A  	A	  A
 * 				B	B		B	B..
 */
public class MainClass_single {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long start=System.currentTimeMillis();
		
		for(int i=0;i<100;i++)
		{
			System.out.print("��");
		}
		long end=System.currentTimeMillis();
		System.out.println("�ҿ�ð�:"+(end-start));
		
		for(int i=0;i<100;i++)
		{
			System.out.print("��");
		}
		end=System.currentTimeMillis();
		System.out.println("�ҿ�ð�:"+(end-start));
	}

}
