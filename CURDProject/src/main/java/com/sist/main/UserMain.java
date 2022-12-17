package com.sist.main;
import java.util.*;
import com.sist.dao.*;
public class UserMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan=new Scanner(System.in);
		StudentVO vo=new StudentVO();
		System.out.print("�̸� �Է�:");
		vo.setName(scan.next());
		System.out.print("���� �Է�:");
		vo.setKor(scan.nextInt());
		System.out.print("���� �Է�:");
		vo.setEng(scan.nextInt());
		System.out.print("���� �Է�:");
		vo.setMath(scan.nextInt());
		
		StudentDAO dao=new StudentDAO();
		dao.studentInsert(vo);
		System.out.println("���� �Ϸ�!!");
		
		ArrayList<StudentVO> list=dao.studentListData();
		for(StudentVO svo:list)
		{
			System.out.println(svo.getHakbun()+" "
					+svo.getName()+" "
					+svo.getKor()+" "
					+svo.getEng()+" "
					+svo.getMath()+" "
					+svo.getTotal()+" "
					+svo.getAvg());
		}

	}

}
