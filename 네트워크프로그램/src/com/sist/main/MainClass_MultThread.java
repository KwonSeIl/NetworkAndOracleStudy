package com.sist.main;
class MultiThread extends Thread
{
	private String data="";
	public MultiThread(String data)
	{
		this.data=data;
	}
	// ������ ����
	public void run()
	{
		try
		{
			for(int i=0;i<100;i++)
			{
				System.out.print(data);
				Thread.sleep(300);
			}
		}catch(Exception ex) {}
	}
}
public class MainClass_MultThread {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MultiThread t1=new MultiThread("��");
		MultiThread t2=new MultiThread("��");
		// ���� ����
		t1.start();
		t2.start();
	}

}
