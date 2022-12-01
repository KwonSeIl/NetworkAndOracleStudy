package com.sist.main;
import java.sql.*;
/*
 * 	�ڹ�
 * 	---
 * 	  ���� => ��Ʈ��ũ / �����ͺ��̽� => �ڹٶ��̺귯��(JDBC) => ���̺귯��(�ܺ� => MyBatis, JPA)
 * 			-----	--------
 * 
 * 			bit > byte(8bit):�ѱ��� > word:�ܾ� > row(record) > table(������ �Ǿ� �ִ�) 
 * 											  ----------	 ---- => 2���� ���� 
 * 												����			 ���� 
 * 			---------------------------------
 * 				id		pwd		name	==> ������(�÷�:����Ŭ) , �ڹٿ����� ������� 
 * 			---------------------------------
 * 				aaa		1234	ȫ�浿		==> �� �� ������: record, row, tuple ==> ��ü(=���̺��ν��Ͻ�) /  Ʃ�� ������ ���� ��: ī��θ�Ƽ 
 * 			---------------------------------
 * 				bbb		1234	�ڹ���
 * 			---------------------------------
 * 				ccc		1234	��û��
 * 			---------------------------------
 * 			 	������
 * 
 * 				emp
 * 				---------------------------------------------------------
 * 					empno	ename	job	mgr	hiredate	sal	comm	deptno
 * 					���		�ϸ�		����	���	�Ի���			�޿�	������		�μ���ȣ
 * 				---------------------------------------------------------
 * 				--- 14��
 * 		
 * 			����Ŭ�� ��ɾ�(SQL)
 * 			---------------
 * 			DQL: SELECT => �����͸� �˻�
 * 				SELECT * | column��...
 * 				FROM table_name
 * 				[
 * 	
 * 				]
 * 				���� => ������ => �Լ� => ���� => ��������
 * 			DML: ������ ���۾��
 * 				INSERT: ������ �߰�(ȸ������, �۾���, ��۴ޱ�)
 * 				UPDATE: ������ ����
 * 				DELECT: ������ ���� 
 * 			-------------------------------------------CURD
 * 			DDL: ������ ���Ǿ��
 * 				=> ������ ���� ���� ���� ==> CREATE
 * 					---------- TABLE, ���� => �������̺�(View)
 * 								Sequence(�ڵ�������ȣ), Index(�˻�, SORT), PL/SQL
 * 				=> ������� ����: ALTER
 * 				=> ������� ����: DROP
 * 				=> �߶󳻱�: TRANCATE
 * 				=> ���̺�� ����: RENAME
 * 			-------------------------------------------DBA
 * 			DCL: ���� ���
 * 				GRANT: ���� �ο�
 * 				REVOKE: ���� ȸ��(����)
 * 				-------------------DBA
 * 			TCL: Ʈ����������(�ϰ�ó��)
 * 				COMMIT: ����(�������� ����)
 * 				ROLLBACK: ���
 * 			---------------------------------- �״�� �ڹٿ��� ����
 * 			=> ��������, �����ͺ��̽� �𵨸�
 * 			
 */
public class MainClass_Oracle {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{
			// ����̹� ��� (thin) => ojdbc8.jar
			Class.forName("oracle.jdbc.driver.OracleDriver"); // mysql => com.mysql.dj.Driver
			// ����Ŭ ����
			String url="jdbc:oracle:thin:@localhost:1521:XE"; // �ڵ� ����
			/*
			 * 	�����ͺ��̽�: ����
			 * 	���̺�: ����
			 * 	�÷�: Ŭ������ �������
			 * 	ROW, RECORD
			 */
			Connection conn=DriverManager.getConnection(url,"hr","happy");
			// conn hr/happy
			// SQL ���� ����
			String sql="SELECT ename,job,hiredate FROM emp";
			PreparedStatement ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			//���� ����� ������ �ִ� 
			// ���� ����� ������ ����
			while(rs.next()) //cursor ��ġ�� ������ �̵� 
			{
				System.out.println(rs.getString(1)+" "
						+rs.getString(2)+" "
						+rs.getDate(3).toString());
			}
		}catch(Exception ex) {ex.printStackTrace();}

	}

}
