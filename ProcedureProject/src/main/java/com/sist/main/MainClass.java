package com.sist.main;
import com.sist.dao.*;
import java.util.*;
public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Scanner scan=new Scanner(System.in);
		System.out.print("�̸� �Է�:");
		String name=scan.next();
		System.out.print("���� �Է�:");
		int kor=scan.nextInt();
		System.out.print("���� �Է�:");
		int eng=scan.nextInt();
		System.out.print("���� �Է�:");
		int math=scan.nextInt();
		
		StudentVO vo=new StudentVO();
		vo.setName(name);
		vo.setKor(kor);
		vo.setEng(eng);
		vo.setMath(math);*/
		
		// DAO ����
		StudentDAO dao=new StudentDAO();
		//dao.studentInsert(vo);
		System.out.println("======== ���� �Ϸ� ========");
		System.out.println("======== �л� ��� ========");
		ArrayList<StudentVO> list=dao.studentListData();
		for(StudentVO vo:list)
		{
			System.out.println(vo.getHakbun()+" "
					+vo.getName()+" "
					+vo.getKor()+" "
					+vo.getEng()+" "
					+vo.getMath());
			
		}
	}

}
