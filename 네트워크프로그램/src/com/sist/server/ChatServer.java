package com.sist.server;
import java.io.*;
import java.net.*;
import java.util.*;
/*
 * 	class chatServer
 * 	{
 * 		Client�� ���� ���� ==> IP, PORT
 * 		--------------------------- ���ӽø��� ����(���)
 * 
 * 		---------------------------
 * 		class Client extends Thread ==> �����ڸ��� ����� ����ϴ� ������ �ʿ�
 * 		{
 * 			��Ÿ� ��� ==> ���� Ŭ����
 * 		}
 * 		---------------------------
 * 
 * 	}
 * 	���� Ŭ����: ������, ��Ʈ��ũ => �����ϴ� �����Ͱ� ���� ==> ��� Ŭ������ �̿��ؼ� ����
 * 		= ���Ŭ����
 * 			class A
 * 			{
 * 				class B
 * 				{
 * 					A�� ������ �ִ� ��� �����͸� ������ ����
 * 				}
 * 			}
 * 		= �͸��� Ŭ����: ��Ӿ��� �������̵��� �� �� ��� 
 */
//���ӽÿ� Ŭ���̾�Ʈ ���� ���� ==> ��ȯ ����
public class ChatServer implements Runnable {
	// Ŭ���̾�Ʈ ���� ���� 
	private Vector<Client> waitVc=new Vector<Client>();
	// ���� ����
	private ServerSocket ss; //��ȯ ���� => ���ӽÿ��� ó��
	//������ PORT�� ����, ���� IP
	private final static int PORT=3355;
	// 0~65535
	/*
	 * 	0~1023��: �˷��� ��Ʈ => ������
	 * 		= FTP: 21
	 * 		= SMTP: 25
	 * 		= HTTP: 80
	 * 		= TELNET: 23
	 * 			p => ��������(���)
	 * 	1521: ����Ŭ ���
	 * 	3306: MySQL
	 * 	3000: React
	 * 	8080: ������
	 * 	=================== ����: 20000����
	 * 						ȭ��: 30000����(JMF)
	 */
	public ChatServer()
	{
		try
		{
			//������ �� �� �����ϸ� �ȵ�(�ѹ��� ������ ����) -> ������ ����: PORT�� �����ؼ� ���
			/*
			 * 	����
			 * 		P2P: Client ���α׷� �ȿ� ������ �۵� => ����
			 */
			ss=new ServerSocket(PORT); //����: 50����� ���� => ��Ʈ��ݿ��� ���(�系)
			//new ServerSocket(PORT,100)
			System.out.println("Server Start...");
			// bind(IP,PORT):����, listen():���
		}catch(Exception ex) 
		{
			System.out.println(ex.getMessage());
		}
	}
	public void run() //���ӽÿ� ������ ����
	{
		try
		{
			while(true)
			{
				//���� => Ŭ���̾�Ʈ�� iP
				Socket client=ss.accept(); //Ŭ���̾�Ʈ�� �������� ��쿡�� ȣ��(Ư���޼ҵ�) => ������ Ŭ���̾�Ʈ�� ������ ������ �´� 
							// �߽��� ��ȭ��ȣ Ȯ�� 
							// IP�� PORT�� ������ �ִ� Ŭ����: Socket
				//System.out.println("������ Ŭ���̾�Ʈ IP:"+client.getInetAddress().getHostAddress());
				//System.out.println("������ Ŭ���̾�Ʈ PORT:"+client.getPort());
				//���� => ���� PORT, Ŭ���̾�Ʈ�� �ڵ� PORT
				Client c=new Client(client);
				waitVc.add(c); //����
				c.start();
			}
		}catch(Exception ex) {}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChatServer server=new ChatServer();
		new Thread(server).start();

	}
	// ��� => ����� ��û �ޱ�, ����� ��û ó�� �Ŀ� ���� ==> ��� ���� ==> ���Ӹ��� �����ؾ� �ȴ� => �����ڸ��� ���� ��� ����
	class Client extends Thread
	{
		private Socket s; //Ŭ���̾�Ʈ ���� ����(�����尡 ����ϴ� Ŭ���̾�Ʈ�� ������ ������ ����) 
		private OutputStream out; //Ŭ���̾�Ʈ�� ���� ����
		private BufferedReader in; //Ŭ���̾�Ʈ�κ��� ��û���� ���� ��쿡 ���
		
		public Client(Socket s)
		{
			try
			{
				this.s=s;
				in=new BufferedReader(new InputStreamReader(s.getInputStream()));
				// socket Ŭ���̾�Ʈ�� ��ǻ�� 
				out=s.getOutputStream(); //������ ���� -> Ŭ���̾�Ʈ���� �о� ����
				// �޸𸮿� ���� -> ����� ��ġ�κ��� Ŭ���̾�Ʈ�� �о  => �ŷڼ��� �پ��(TCP)
				
			}catch(Exception ex) {}
		}
		public void run()
		{
			try 
			{
				while(true)
				{
					//Ŭ���̾�Ʈ�κ��� ��û�� �ޱ�
					String msg=in.readLine(); //in => Ŭ���̾�Ʈ�� ������ �� �޸� 
					System.out.println("Client�� ������ ��:"+msg);
					//������ ��� Ŭ���̾�Ʈ�� ������ ����
					for(Client c:waitVc)
					{
						c.out.write((msg+"\n").getBytes());
						//�ݵ�� \n�� �߰��Ѵ� -> �׷��� readLine���� ���� �� ����
					}
				}
			}catch(Exception ex) {}
		}
	}

}
