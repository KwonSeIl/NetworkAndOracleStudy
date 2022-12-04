package com.sist.server;
import java.util.*;
import java.io.*;
import java.net.*;
/*
 *   ��(JSP,MVC,Spring,Spring-Boot) 
 *   ------------------------------
 *     �������� (�⺻��,Ŭ����,�迭) 
 *     => ���� (������,���)
 *     ------------------------------------> �ڹٽ�ũ��Ʈ (�ڹ� ����=>for)
 *     => Back(�ڹ�,����Ŭ) , Front(�ڹٽ�ũ��Ʈ) => HTML,CSS(�ۺ���:�����γ�) => �������� ���� 
 *        ---------------------------------    
 *     => Ŭ���� (�Խ��� Ŭ���� , ����Ŭ���� ...)
 *     => ����ó�� 
 *     => �÷��� , ���׸��� 
 *     => ���̺귯�� (String ....) 
 *         
 *   
 */
public class Server implements Runnable{
    // Ŭ���̾�Ʈ ������ �����ϴ� ���� ==> �� ==> HttpSession 
	private Vector<Client> waitVc=new Vector<Client>();
	/*
	 *    �÷��� / ���׸��� 
	 *    ---------------
	 *            �÷��� : ������ ���� , ������ , ǥ��ȭ 
	 *            Collection 
	 *               |
	 *        -------------------------
	 *        |          |            |
	 *      List       Set           Map
	 *      List => �����͸� ��Ƽ� �������� ���� �� ���� (�����ͺ��̽��� �ִ� �����͸� ����) : ArrayList
	 *      Set => �ߺ��� ����  : HashSet
	 *      Map => ����  : HashMap 
	 *      
	 *      ���׸��� <Ŭ������> => Wrapper  <int> => <Integer>
	 *      ------ ���������� ���� (Object=>������ �������� ����) 
	 *      ------ ����ȯ���� �ʴ´� ==> �ҽ��� �����ϴ� ==> ������(� ���������� ÷�ο��� Ȯ��)
	 *      ------ ���ѵ� ���׸��� , �������� ���� ���׸��� 
	 *             ------------  ------------------ <?>
	 *             <? extends Ŭ������> 
	 *             
	 *      ���Խ� / ������̼� / IO(�ٿ�ε�/���ε�)
	 *      ----- ��� ���� 
	 *             -------- MVC => Spring
	 */
	// �������� => ServerSocket���� 
	private ServerSocket ss;
	// �����ȣ => PORT (0~65535)
	private final int PORT=3355;
	// ���� ���� 
	// ���۰� ���ÿ� ���� 
	/*
	 *   ������ 
	 *    => �ʱ�ȭ 
	 *    => ���۰� ���ÿ� ó���ؾߵǴ� ����� �ִ� ��� 
	 *       --------------
	 *         �ڵ��α��� , �����ͺ��̽� ���� , �������� , ��ġ�� ȭ�� UI...
	 */
	public Server()
	{
		try
		{
			ss=new ServerSocket(PORT);//bind():���ϻ���,listen():������=> ȣ��
			System.out.println("Server Start...");
		}catch(Exception ex){}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Server server=new Server();
        new Thread(server).start();
	}
    // ���ӽ� ������ �޾Ƽ� ���� 
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try
		{
			while(true)
			{
				Socket s=ss.accept(); // Ŭ���̾�Ʈ�� ������ ������ ������ ��� �´� (Socket=>IP,PORT)
				// �߽��� ���� 
				// ==> Thread���� �Ѱ��ش� => ����� �����Ѵ� => ��Ƽ ���� , 1:1 => Echo Server
				Client client=new Client(s);//Thread�� ������ ������ �Ѱ��ش� 
				client.start();//��Ž���
			}
		}catch(Exception ex) {}
	}
   
	// ���� Ŭ���̾�Ʈ�� ��� ==> ������ ==> 1:1
	class Client extends Thread
	{
		private String id,name,sex;
		private OutputStream out;// ��� => ������� �����ش�
		private BufferedReader in;// ��û�� �޴´�
		private Socket s;//���� ��� =>������ Ŭ���̾�Ʈ ������ ������ �ִ� 
		// �ʱ�ȭ
		public Client(Socket s)
		{
			try
			{
				this.s=s;
				in=new BufferedReader(new InputStreamReader(s.getInputStream()));
				out=s.getOutputStream();
			}catch(Exception ex) {}
		}
		// ���� ��� ==> �������� �ٸ��� (ä�ü��� , �� ���� , ����Ŭ ����)
		public void run()
		{
			try
			{
				while(true)
				{
					// ����� ��û���� 
					String msg=in.readLine();
					// �α��� ��û 100|id|name|sex
					StringTokenizer st=new StringTokenizer(msg,"|");
					int protocol=Integer.parseInt(st.nextToken());//�����ڸ� �ڸ��� 
					switch(protocol)
					{
					   case 100: // �α����̸� 
					   {
						   id=st.nextToken();
						   name=st.nextToken();
						   sex=st.nextToken();
						   
						   // ���� ó��
						   //1. ������ ��� ������� �α����� ����� ������ ���� 
						   messageAll(100+"|"+id+"|"+name+"|"+sex);
						   //2. waitVc�� ���� 
						   waitVc.add(this);
						   //2-2 �α���â���� => ����â���� ���� 
						   messageTo(110+"|"+id);
						   //3. �α��� ������� => ������ ������ ��ü ���� 
						   for(Client user:waitVc)
						   {
							   messageTo(100+"|"+user.id+"|"+user.name+"|"+user.sex);
						   }
					   }
					   break;
					   // 900+"|"+id ==> id�� �����Ѵ� 
					   case 900: //������� ...
					   {
						   String mid=st.nextToken();
						   messageAll(900+"|"+id);//���̺��� id�� ���� (���� �ִ� ���ó��)
						   // ����
						   for(int i=0;i<waitVc.size();i++)
						   {
							   Client c=waitVc.get(i);
							   if(c.id.equals(mid))
							   {
								   messageTo(990+"|");// �����츦 �����Ѵ� (������ ��� ó��)
								   waitVc.remove(i);// ����� id�� ����� 
								   in.close();
								   out.close(); // ����� �����Ѵ� 
								   break;// ��ȣ => ���Ƿ� ����� ==> ��(���ϸ�)
								   // login.jsp , exit.jsp.....
								   // ���� => ����,���ڿ� ...
							   }
						   }
					   }
					   break;
					   case 300:
					   {
						   messageAll(300+"|["+name+"]"+st.nextToken());
					   }
					   break;
					}
					
				}
			}catch(Exception ex) {}
		}
		// ��ü������ ����
		public synchronized void messageAll(String msg)
		{
		    try
		    {
		    	for(Client client:waitVc)
		    	{
		    		client.messageTo(msg); //  ���� , ä�� 
		    	}
		    }catch(Exception ex) {}
		}
		// ���������� ���� 
		public synchronized void messageTo(String msg)
		{
			try
			{
				out.write((msg+"\n").getBytes());
			}catch(Exception ex) {}
		}
		
	}
}

