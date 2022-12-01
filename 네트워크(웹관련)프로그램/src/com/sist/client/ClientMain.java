package com.sist.client;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import com.sist.dao.*;
public class ClientMain extends JFrame implements ActionListener {
	CardLayout card=new CardLayout();
	Login login=new Login();
	WaitRoom wr=new WaitRoom();
	MemberDAO dao=new MemberDAO();
	public ClientMain()
	{
		setLayout(card);
		add("LOGIN",login);
		add("WR", wr);
		setSize(1300, 850);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE); // X ��ư Ŭ�� �� �޸� ����
		login.b1.addActionListener(this);
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
				String pwd=login.tf2.getText();
				if(pwd.trim().length()<1)
				{
					JOptionPane.showMessageDialog(this, "����� �Է��ϼ���");
					login.tf2.requestFocus();
					return;
				}
				
				String result=dao.isLogin(id, pwd); // �α��� ó�� 
				if(result.equals("NOID"))
				{
					JOptionPane.showMessageDialog(this, "ID�� �������� �ʽ��ϴ�");
					login.tf1.setText("");
					login.tf2.setText("");
				}
				else if(result.equals("NOPWD"))
				{
					JOptionPane.showMessageDialog(this, "��й�ȣ�� Ʋ���ϴ�");
					login.tf2.setText("");
				}
				else
				{
					JOptionPane.showMessageDialog(this, result+"�� �α��εǾ����ϴ�");
				}
			}
		}
	}
