package com.sist.main;
import java.util.*;
import com.sist.dao.*;
public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// ����Ŭ ����
		EmpDAO dao=EmpDAO.newInstance(); //�̱��� ��ü ����
		/*ArrayList<EmpVO> list=dao.empListData();
		for(EmpVO vo:list)
		{
			System.out.println(vo.getEmpno()+" "
					+vo.getEname()+" "
					+vo.getJob()+" "
					+vo.getHiredate().toString()+" "
					+vo.getSal()+" "
					+vo.getDvo().getDname()+" "
					+vo.getDvo().getLoc()+" "
					+vo.getSvo().getGrade());
		}*/
		Scanner scan=new Scanner(System.in);
		/*System.out.print("�̸� �Է�:");
		String ename=scan.next();
		
		ArrayList<EmpVO> list=dao.empFindData(ename);
		for(EmpVO vo:list)
		{
			System.out.println(vo.getEmpno()+" "+vo.getEname()+" "+vo.getJob()+" "
					+vo.getHiredate().toString()+" "
					+vo.getSal());
		}*/
		//EmpVO vo=dao.empSubQueryData(ename.toUpperCase()); //�빮�� ��ȯ
		System.out.print("�ο� �Է�:");
		int num=scan.nextInt();
		ArrayList<EmpVO> list=dao.empInlineView(num);
		for(EmpVO vo:list)
		{
			System.out.println(vo.getEmpno()+" "+vo.getEname()+" "+vo.getJob()+" "
					+vo.getHiredate().toString()+" "
					+vo.getSal());
		}
		//System.out.println("���:"+vo.getEmpno());
		//System.out.println("�̸�:"+vo.getEname());
		//System.out.println("����:"+vo.getJob());
		//System.out.println("�Ի���:"+vo.getHiredate());
		///System.out.println("�޿�:"+vo.getSal());
		//System.out.println("�μ���:"+vo.getDvo().getDname());
		//System.out.println("�ٹ���:"+vo.getDvo().getLoc());
	}

}
