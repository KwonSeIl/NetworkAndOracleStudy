package com.sist.client;
import javax.swing.*;
import java.awt.*;
// login.jsp, waitroom.jsp => HTML => ȭ�鿡 �ʿ��� ������(����Ŭ)
public class Login extends JPanel {
	   JTextField tf1,tf2; // ����Ŭ����
	   //JRadioButton rb1,rb2;
	   JButton b1,b2;
	   JLabel la1, la2, la3;
	   public Login()
	   {
	      // �ʱ�ȭ => �޸� �Ҵ�
	      la1=new JLabel("ID");
	      la2=new JLabel("���");
	      //la3=new JLabel("����");
	      tf1=new JTextField();
	      tf2=new JTextField();
	      //rb1=new JRadioButton("����");
	      //rb2=new JRadioButton("����");
	      //ButtonGroup bg=new ButtonGroup();
	      //bg.add(rb1); bg.add(rb2)
	      //rb1.setSelected(true);
	      b1=new JButton("�α���");
	      b2=new JButton("���");
	      
	    //��ġ ==> Layout => �� : CSS
	      setLayout(null); //���� ��ġ
	      la1.setBounds(10,15,50,30);
	      tf1.setBounds(65,15,150,30);
	      
	      la2.setBounds(10,50,50,30);
	      tf2.setBounds(65,50,150,30);
	      
	     // la3.setBounds(10,85,50,30);
	      //rb1.setBounds(65,85,70,30);
	     // rb2.setBounds(140,85,70,30);
	      
	      add(la1); add(tf1);
	      add(la2); add(tf2);
	     // add(la3); add(rb1); add(rb2);
	      
	      JPanel p=new JPanel();
	      p.add(b1);
	      p.add(b2);
	      p.setBounds(10,125,205,35);
	      add(p);
	      

		
	}

}
