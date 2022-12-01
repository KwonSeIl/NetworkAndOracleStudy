package com.sist.client;
import java.util.*; //StringTokenizer
import java.io.*; //OutputStream, BufferedReader
import java.net.*; //Socket
import javax.swing.*; //JFrame
import java.awt.*;
import java.awt.event.*;
public class ChatClient extends JFrame implements ActionListener{
	JTextArea ta;
	JTextField tf;
	JButton b1,b2;
	private String name;
	//��Ʈ��ũ ���� ���̺귯��
	private Socket s; //������
	private OutputStream out; //������ ����
	private BufferedReader in; //�����κ��� �� �б�
	public ChatClient()
	{
		ta=new JTextArea();
		JScrollPane js=new JScrollPane(ta);
		ta.setEditable(false);
		tf=new JTextField(30);
		tf.setEnabled(false);
		b1=new JButton("����");
		b2=new JButton("����");
		
		JPanel p=new JPanel();
		p.add(tf);
		p.add(b1);
		p.add(b2);
		
		add("Center",js);
		add("South",p);
		setSize(520,600);
		setVisible(true);
		
		b1.addActionListener(this);
		b2.addActionListener(this); //CallBack => �ý��ۿ� ���ؼ� �ڵ����� ȣ��(javaScript)
		tf.addActionListener(this); //enter
		// CallBack => ��ǥ���� �޼ҵ� 
		// button => Ŭ��, mouseClick()
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ChatClient();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b1)
		{
			name=JOptionPane.showInputDialog("�̸� �Է�:");
			//��������
			try
			{
				s=new Socket("localhost",3355); //���� => ������ ��ȭ �ɱ�
				in=new BufferedReader(new InputStreamReader(s.getInputStream())); //�����κ��� ���� �޴´�
				out=s.getOutputStream();
			}catch(Exception ex) {}
			b1.setEnabled(false);
			tf.setEnabled(true);
			tf.requestFocus();
		}
		if(e.getSource()==b2)
		{
			dispose(); //������ �޸� ȸ��
			System.exit(0); //���α׷� ����
		}
		if(e.getSource()==tf)
		{
			// �Է��� ���ڿ� �б�
			String msg=tf.getText();
			if(msg.trim().length()<1) //�Է��� �ȵ� ����
				return;
			// �Է°��� ������ ����
			try
			{
				out.write(("["+name+"]"+msg+"\n").getBytes());
			}catch(Exception ex) {}
			//ta.append(msg+"\n");
			//�����κ��� ������ ������
			new Thread().start();
			tf.setText("");
		}
		
	}
	//�������� ������ �����͸� ���
	public void run() {
		try
		{
			while(true)
			{
				String msg=in.readLine(); //�������� ������ �����͸� �޴´�
				ta.append(msg+"\n");
			}
		}catch(Exception ex) {}
	}

}
