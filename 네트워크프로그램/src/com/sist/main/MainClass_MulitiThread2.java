package com.sist.main;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
 * 	Button / Menu / JTextField(enter) -> ó���� �� �ִ� ��: ActionListener
 * 	JTree / JTable => Mouse�� ó�� 
 */
public class MainClass_MulitiThread2 extends JFrame implements ActionListener {
	JTextArea my,thread;
	JButton b;
	MyThread3 t;
	// ==> ���� �۾�(�б�/����), �ǽð� => ������: Ajax, VueJS, ReactJS
	public MainClass_MulitiThread2()
	{
		my=new JTextArea();
		JScrollPane js1=new JScrollPane(my);
		thread=new JTextArea();
		thread.setEditable(false); //��Ȱ��ȭ
		JScrollPane js2=new JScrollPane(thread);
		b=new JButton("����");
		 
		setLayout(null); //����� ����
		js1.setBounds(10, 15, 300, 420);
		js2.setBounds(320, 15, 300, 420);
		b.setBounds(10, 450, 100, 30);
		
		// �����쿡 �߰�
		add(js1);add(js2);
		add(b);
		
		setSize(650,520);
		setVisible(true);
		
		b.addActionListener(this);
		
		
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainClass_MulitiThread2();

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==b) // ��ư Ŭ�� �� ó��
		{
			try
			{
				// �Է°� �б�
				String msg=my.getText();
				if(msg.trim().length()<1)
				{
					JOptionPane.showMessageDialog(this, "�����͸� �Է��ϼ���");
					my.requestFocus();
					return;
				}
				
				FileWriter fw=new FileWriter("c:\\java_data\\chat.txt",true);
				fw.write(msg);
				fw.close();
				
				t=new MyThread3(); //�б� => ���  ==> ������� ���(���� �۾� ����)
				t.start();
			}catch(Exception ex) {}
		}
		
	}
	// Ŭ������ Ŭ�������� �����͸� ����
	// ���� �б⸸ ���
	class MyThread3 extends Thread
	{
		public void run() //run()���� �۾� ==> ������ �о textarea�� ���
		{
			try
			{
				FileReader fr=new FileReader("c:\\java_data\\chat.txt");
				int i=0;
				String msg="";
				while((i=fr.read())!=-1)
				{
					msg+=String.valueOf((char)i);
				}
				fr.close();
				
				//�����쿡 ���
				thread.setText(msg);
				
				t.interrupt(); //������ ����
			}catch(Exception ex) {}
		}
	}

}
