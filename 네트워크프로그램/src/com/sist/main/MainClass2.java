package com.sist.main;
// => Thread ��� => ����� �ȵǴ� Ŭ���� ==> ������
/*
 * 	class Window extends JFrame implements Runnable
 * 	{
 * 	}
 * 	
 * 	==> �ΰ��� ���� ���ÿ� ����
 * 		=  IO (�ܹ���) �б�/����
 * 		= Ŭ���̾�Ʈ => ������ ���� / �������� ������ ��
 * 
 */
class MyThread2 implements Runnable // �����忡 ���۸� ����� �ش�
{

	@Override
	public void run() // ���� ���� ����
	{
		// TODO Auto-generated method stub
		try
		{
			for(int i=1;i<=10;i++)
			{
				System.out.println(Thread.currentThread()+":"+i);
				// ���� �����ϰ� �ִ� ������ �̸�
				Thread.sleep(300);
			}
		}catch(Exception ex) {}
		
	}
	
}
public class MainClass2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyThread2 t1=new MyThread2();
		new Thread(t1).start(); //t1�� ������ �ִ� run()�� ȣ��

	}

}
