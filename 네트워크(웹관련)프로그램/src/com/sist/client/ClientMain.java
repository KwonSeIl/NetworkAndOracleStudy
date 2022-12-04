package com.sist.client;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import com.sist.dao.*;
//��Ʈ��ũ ���� ���̺귯�� 
import java.net.*; // ��Ʈ��ũ ���� (����) => Socket
import java.io.*; // ������ ����� => BufferedReader / OutputStream ------ ������ ��û�� ������ ��� 
//                              --------------- �������� ������ ����� �о� �´� 
/*
*      ����Ŭ ���� : OutputStream , BufferedReader : PreparedStatement 
*      �� ���� : OutputStream  =========> HttpServletRequest
*               BufferedReader ========> HttpServletResponse 
*/
import java.util.*;// StringTokenizer : �����͸� �ڸ��� 
public class ClientMain extends JFrame implements ActionListener,Runnable{
	CardLayout card=new CardLayout();
	Login login=new Login();
	WaitRoom wr=new WaitRoom();
	// ��Ʈ��ũ ���� Ŭ���� 
	private Socket s; // ���� ����� 
	private OutputStream out; // ������ ��û���� ������ ===> ��üó�� ==> out.write()
	private BufferedReader in;// �������� ������ ���� �޴� ���� ==> �������̿� ==> in.readLine()
	// �ܾ� ���� ==> StringTokenizer() 
	// 100|id|name|sex ==> ����Ŭ (SELECT..) , ��(���ϸ� ����)
	private String id;
	public ClientMain()
 {
 	setLayout(card);
 	
 	add("LOGIN",login);
 	add("WR",wr);
 	setSize(1150, 850);
 	setVisible(true);
 	setDefaultCloseOperation(EXIT_ON_CLOSE); // X ��ư Ŭ���� �޸� ����
 	login.b1.addActionListener(this);
 	wr.b6.addActionListener(this);// ������
 	wr.tf.addActionListener(this); //ä�� //300
 }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
		}catch(Exception ex){}
     new ClientMain();
	}
	@Override
	//������ ��û
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==login.b1)
		{
			String id=login.tf1.getText();
			if(id.trim().length()<1)
			{
				JOptionPane.showMessageDialog(this, "ID�� �Է��ϼ���");
				login.tf1.requestFocus();
				return;
			}
			String name=login.tf2.getText();
			if(name.trim().length()<1)
			{
				JOptionPane.showMessageDialog(this, "�̸��� �Է��ϼ���");
				login.tf2.requestFocus();
				return;
			}
			String sex="";
			if(login.rb1.isSelected())// ���� ������ư�� ���õǾ�����
			{
				sex="����";
			}
			else
			{
				sex="����";
			}
			// �Էµ� �����͸� ������ ���� => ������ �����͸� �޾Ƽ� ���� => ��Ž� ����� ���ɰ� ����� 
			
			connection(id, name, sex);
		}
		//������
		else if(e.getSource()==wr.b6)
		{
			try
			{
				out.write((900+"|"+id+"\n").getBytes());
			}catch(Exception ex) {}
		}
		//ä�ÿ�û
		else if(e.getSource()==wr.tf)
		{
			String msg=wr.tf.getText(); // ����ڰ� �Է��� ��
			if(msg.trim().length()<1) //�Է��� �ȵ� ���
				return; //�޼ҵ� ����
			
			try
			{
				//ä�� ���ڿ� ����(������ ������ �� out.write())
				out.write((300+"|"+msg+"\n").getBytes()); //��=> �ּҶ� ==> ������
				//?������&������&������...
			}catch(Exception ex) {}
			wr.tf.setText("");
		}
	}
	/*
	 *     Ŭ���̾�Ʈ���� => ��û / ó�� / ��� 
	 *     Ŭ���̾�Ʈ   /  ���� 
	 *     --------    -----
	 *     ��û/���     ��ûó���� �Ѵ� 
	 *     ==> ����Ŭ / �� 
	 *     => Ŭ���̾�Ʈ���� ��û (�α��� , �α׾ƿ� , ��ٱ��� , �����Ѵ�...)
	 *     => �������� Ŭ���̾�Ʈ�� ��û�� �����͸� �޴´� 
	 *        ��) ==> �α��� (id,pwd) , ��ȭ��� (��������ȣ) , ��ȭ �� (�ߺ��� �ȵ� ������)..
	 *        �������� ó���ϴ� ���� : ��� Ŭ���̾�Ʈ�� ������ ����
	 *        => Socket (���ȣȯ)
	 *           ---------------  ī�� (����:C,Ŭ���̾�Ʈ :�ڹ�) 
	 *                                 ----- ------------- 
	 *     
	 */
	// �α��� ó�� �޼ҵ� => ������ ���� 
	public void connection(String id,String name,String sex)
	{
		// ��ȭ�� ���� => ��ȭ�� �Ǵ� Socket => ���� 
		// �α��� ��û => ����� ���� (id,name,sex) => �������� ������� �ִ� ��� => �޾Ƽ� ��� 
		try
		{
			// ����
			s=new Socket("localhost",3355); // localhost => 127.0.0.1 , ���� IP
			// �������� ������ ������ �б�
			in=new BufferedReader(new InputStreamReader(s.getInputStream())); // s�� ����
			// s�� ������ ���� (�������� ��û ����� ���� �޸𸮿� ����) 
			//  InputStreamReader => ���� ��Ʈ�� ==> byte=>char�� ��ȯ 
			// ������ ���� ���� (�ٿ�ε�/���ε�)
			// ������ ������ ������ ���� ���� 
			out=s.getOutputStream();//�߻� Ŭ���� 
			/*
			 *   new�� �̿��ϴ� ��� => �Ϲ� Ŭ���� 
			 *   new���� ���� => �߻�Ŭ����
			 *   -------------------------------------------------------
			 *      *** �߻�Ŭ���� VS �������̽� 
			 *      => �������̽� ����� ���� ��κ��� ���
			 *         -------- �������� �⺻ 
			 *      => ���ó => ���õ� Ŭ������ ��� ��� 
			 *      
			 */
			// �α��� ��û 
			out.write((100+"|"+id+"|"+name+"|"+sex+"\n").getBytes());
		}catch(Exception ex) {}
		// �����κ��� ������� �޾ƿͶ� ==> Thread (�ܹ���) => ������(Ŭ���̾�Ʈ���� ó��) / �б�(������) ==> ���ÿ� ����
		new Thread(this).start();
		
	}
	// �������� ������ �����͸� ����ϴ� ���� => ������ ���� 
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try
		{
			while(true)
			{
				// �������� ������ ���� �޴´� 
				String msg=in.readLine();
				// 100+"|"+id+"|"+name+"|"+sex
				System.out.println("Server���� ������:"+msg);
				StringTokenizer st=new StringTokenizer(msg,"|");// ���Խ� => replaceAll(),split()
				int protocol=Integer.parseInt(st.nextToken());
				// �������� Ŭ���̾�Ʈ ��� => ��� 100,110...
				switch(protocol)
				{
				  case 100://�α��� ó�� 
				  {
					  String[] data= {
							  st.nextToken(), //id
							  st.nextToken(), //name
							  st.nextToken()  //sex
					  };
					  wr.model2.addRow(data);
				  }
				  break;
				  case 110://ȭ�� ���� => (�α���â=>����) 
				  {
					  id=st.nextToken(); //�������� ������ id ����
					  setTitle(id); //������ ���� ��� 
					  card.show(getContentPane(), "WR");  //ȭ���� ����
				  }
				  break;
				  case 990: //������ ���(������ ����)
				  {
					  dispose(); //�޸� ����
					  System.exit(0); // ���α׷� ���� ==> ��� ���(�������� ������ ==> ��ȣ�� ������)
				  }
				  break;
				  case 900: //�����ִ� ���
				  {
					  String mid=st.nextToken();
					  String temp="";
					  for(int i=0;i<wr.model2.getRowCount();i++)
					  {
						  temp=wr.model2.getValueAt(i, 1).toString();
						  if(mid.equals(temp))
						  {
							  wr.model2.removeRow(i);
							  break;
						  }
					  }
				  }
				  break;
				  case 300: //ä�ù��ڿ��� ���� ���
				  {
					  wr.ta.append(st.nextToken()+"\n"); //���� => ���� => �޾Ƽ� ���
				  }
				  break;
				}
			}
		}catch(Exception ex){}
	}

}

