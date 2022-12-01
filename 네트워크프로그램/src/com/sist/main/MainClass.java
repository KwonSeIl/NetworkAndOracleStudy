package com.sist.main;
/*
 * 	Thread(���� => �ݵ�� �����带 �ʿ�� �Ѵ�) 
 * 			���μ���: ��ü ���α׷�(������ ���α׷� �ۼ��ؼ� ����) => ������
 * 			�ڹ�: ������ ����ϰ� ����
 * 				=> main(������), gc(������)
 * 				=> ���α׷��� ���� ������ ������ 
 * 				=> ����)
 * 					1) CPU�� ������ ����� �� ����
 * 					2) �ڿ��� ȿ�������� ���
 * 					   -- ����, Ŭ���� ...
 * 					3) ����ڿ� ���� ���伺�� ���
 * 					4) �۾��� �и��ؼ� ��� => �ҽ� ��������
 * 	=> �����ֱ�: ���ÿ� �۾��� ������ �� �ִ� -> �Ѱ��� �۾��� ����(����� ����, �Ѿ� ����) 
 * 	   -----
 * 		����									���					����						�Ͻ�����
 * 	 Thread t1=new Thread()				����� �����͸� ����			run()					sleep()
 * 	 JVM ����									t1.start()			=====					wait()
 * 		= Thread �̸� �ο�											������(���� ��� ����)
 * 		  Thread-0
 * 		  Thread-1
 * 		  setName("")
 * 			=> �̸� ������ ����
 * 		= ������ �ο�
 * 		  0~10 => 10���� ���� ����
 * 		  MIN_PRIORITY(0) => gc()
 * 		  NORM_PRIORITY(5) => ����� ���� ������
 * 		  MAX_PRIORITY(10) => main
 * 	  	  setPriority() ����� ���� 
 * 											������ ����(����)=> interrupt(), join
 * 	=> ������
 * 		1) �񵿱�: ���� �ٸ��� ������, ���ÿ� ����
 * 		2) ����: �Ѱ��� �����尡 ���� -> ���� �����尡 ���� ==> �޼ҵ� �տ� synchronied
 * 		   --- ����
 * 				��� ������, �ڵ���ü, ī�� ����...
 * 	= �������
 * 		= Thread�� ��� ===> ��Ʈ��ũ
 * 			class
 * 			class MyThread etends Thread
 * 			{
 * 				public void run()
 * 				{
 * 					���ۿ� ���õ� �ҽ� �ڵ�
 * 				}
 * 			}
 * 		= Runnable�� �����ϴ� ��� ===> ������
 * 			�������̽�
 * 			class MyThread implements Runable
 * 			{
 * 				public void run()
 * 				{
 * 					�۾� ����
 * 				}
 * 			}
 * 			run()�� ȣ������ �ʴ´� ==> start() ȣ���ϸ� �ڵ����� run() ȣ�� 
 * 			void start()
 * 			{
 * 				run()
 * 			}
 */
class MyThread extends Thread
{
	public void run() // ���� ���� ����
	{
		try
		{
			for(int i=1;i<=10;i++)
			{
				System.out.println(getName()+":"+i);
				Thread.sleep(100); // 1/1000�� ����
			}
		}catch(InterruptedException e) {} // ������� CheckedException ������ �ֱ� ������, �ݵ�� ����ó���� �ʿ��� 
	}
}
public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyThread t1=new MyThread();
		t1.setName("ȫ�浿");
		t1.setPriority(Thread.NORM_PRIORITY);
		MyThread t2=new MyThread();
		t2.setName("��û��");
		t2.setPriority(Thread.MIN_PRIORITY);
		MyThread t3=new MyThread();
		t3.setName("�̼���");
		t3.setPriority(Thread.MAX_PRIORITY);
		System.out.println("t1-�̸�"+t1.getName()+", �켱����:"+t1.getPriority());
		System.out.println("t2-�̸�"+t2.getName()+", �켱����:"+t2.getPriority());
		System.out.println("t3-�̸�"+t3.getName()+", �켱����:"+t3.getPriority());
		System.out.println("==== ���� ====");
		t1.start();
		t2.start();
		t3.start();

	}

}
