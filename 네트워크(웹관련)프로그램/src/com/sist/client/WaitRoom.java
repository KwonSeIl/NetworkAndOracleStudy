package com.sist.client;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
/*
 *    Ŭ������ ���� 
 *    ----------
 *      �߻�Ŭ���� : �̿ϼ��� Ŭ���� => �޸� �Ҵ��� �Ұ��� => ����ڰ� ����� �޾Ƽ� ó�� 
 *      -------- java.io 
 *      public abstract class ClassName
 *      {
 *          ---------------------------------
 *           �ν��Ͻ� ���� ���� 
 *          ---------------------------------
 *           ������ 
 *          ---------------------------------
 *           ������ �޼ҵ� 
 *          ---------------------------------
 *           ���𸸵� �޼ҵ� 
 *           public abstract ������ �޼ҵ�(�Ű�����..);
 *          ---------------------------------
 *      }
 *      �������̽� : �߻�Ŭ������ ���� , �̿ϼ��� Ŭ���� => Ŭ���� ������ ��� �Ѱ��� �̸����� ��� 
 *      -------- java.sql => �����ͺ��̽� ���� (����Ŭ, MySQL,MSSQL,DB2,MariaDB) 
 *      -------- �����û , �Ȼ��û,������û => MSSQL (ȣȯ�� ����)  
 *               ------ ����Ŭ 
 *      public interface InterfaceName
 *      {
 *          ---------------------------
 *           �ν��Ͻ������� ������ �� ���� 
 *           = ����� ������ ������ ���� 
 *           (public static final) int a=10;
 *          ---------------------------
 *           ������ �ȵ� �޼ҵ� 
 *           (public abstract) void display();
 *           -----------------
 *          --------------------------- JDK 1.8�̻� ���� 
 *           ������ �޼ҵ�
 *           (public) default void aaa(){}
 *          ---------------------------
 *           ������ �޼ҵ�
 *           (public) static void aaa(){}
 *          ---------------------------
 *      }
 *      ����Ŭ���� 
 *      = ���Ŭ���� (Ŭ������ ����(����,�޼ҵ�)�� ����) ==> static�� �̿��Ѵ�  ==> ������ , ������ , ��������
 *        class A
 *        {
 *            ������ ������ 
 *            ������ �޼ҵ� 
 *            class B
 *            {
 *                A�� ������ �ִ� ��� �޼ҵ�,������ ����� ����(private��� ����)
 *            }
 *        }
 *       = �͸��� Ŭ���� : ����� ������� ���� ��찡 ���� 
 *                      -------------------------- ������ , ���̹�Ƽ�� => �������̵��� �͸��� Ŭ���� �̿� 
 *                      *** �������̵��� ����� �� �޾ƾ� �����ϴ� ? yes/no
 *         class A
 *         {
 *             B b=new B()
 *             {
 *                 public void disp(){}
 *                 // �߰��� �����ϴ� 
 *             }
 *         }
 *         class B
 *         {
 *             public void disp(){}
 *         }
 *      ����Ŭ���� => ����� ���Ǵ� ���� ���� => java.lang.* (���� Ŭ����) 
 *        public final class ClassName
 *        {
 *        }
 *        => ����� ������ ���� ==> String,System,Math,StringBuffer,Wrapper....
 */
public class WaitRoom extends JPanel{
     JTable table1,table2;
     DefaultTableModel model1,model2;
     JTextArea ta;
     JTextField tf;
     JButton b1,b2,b3,b4,b5,b6;
     JLabel la1,la2;
     public WaitRoom()
     {
    	 // �ʱ�ȭ 
    	 // �͸��� Ŭ���� => ��Ӿ��� �������̵��ÿ� ��� 
    	 String[] col1={"���̸�","����","�ο�"};
    	 String[][] row1=new String[0][3]; // ���ٿ� �����͸� 3�� ���� 
    	 model1=new DefaultTableModel(row1,col1)
    	 {
            // ���� ����
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
    		 
    	 };
    	 table1=new JTable(model1);
    	 JScrollPane js1=new JScrollPane(table1);
    	 
    	 String[] col2={"ID","�̸�","����"};
    	 String[][] row2=new String[0][3]; // ���ٿ� �����͸� 3�� ���� 
    	 model2=new DefaultTableModel(row2,col2) //�͸��� Ŭ���� => ��Ӿ��� �������̵�
    	 {
            // ���� ����
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
    		 
    	 };
    	 table2=new JTable(model2);
    	 JScrollPane js2=new JScrollPane(table2);
    	 
    	 ta=new JTextArea();
    	 ta.setEditable(false);
    	 JScrollPane js3=new JScrollPane(ta);
    	 
    	 tf=new JTextField();
    	 
    	 la1=new JLabel("������ ������");
    	 la2=new JLabel("������ ����� ����");
    	 
    	 b1=new JButton("�游���");
    	 b2=new JButton("�����");
    	 b3=new JButton("����������");
    	 b4=new JButton("��������");
    	 b5=new JButton("1:1 ä��");
    	 b6=new JButton("�����ϱ�");
    	 
    	 // ��ġ
    	 setLayout(null);//CSS
    	 // x,y ,width,height ==> top,bottom,left,right , width,height
    	 la1.setBounds(10, 15, 120, 30);
    	 js1.setBounds(10, 50, 750, 450);
    	 
    	 js3.setBounds(10, 510, 750, 250);
    	 tf.setBounds(10, 765, 750, 30);
    	 
    	 la2.setBounds(770, 15, 350, 30);
    	 js2.setBounds(770, 50, 350, 350);
    	 
    	 JPanel p=new JPanel();
    	 p.add(b1);p.add(b2);p.add(b3);p.add(b4);p.add(b5);p.add(b6);
    	 p.setLayout(new GridLayout(3, 2,5,5));
    	 p.setBounds(770, 410, 350, 100);
    	 
    	 add(la1);add(la2);
    	 add(js1);add(js2);add(js3);
    	 add(tf);add(p);
    	 
     }
     
}

