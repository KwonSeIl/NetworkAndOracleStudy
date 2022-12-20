package com.sist.change;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.sist.dao.*;
public class FoodModel {
	public void foodListData(HttpServletRequest request)
	{
		// �ڹ� �ڵ� ����
		// 1. ����ڰ� ������ �����͸� �޴´�
		String strPage=request.getParameter("page"); // request,response => ���� ��ü
		if(strPage==null)
			strPage="1"; //default
		int curpage=Integer.parseInt(strPage);
		// 2. ����Ŭ ����(DAO)
		FoodDAO dao=new FoodDAO();
		// 3. �����͸� �޴´�
		ArrayList<FoodVO> list=dao.foodListData(curpage);
		// 4. HTML�� �̿��ؼ� ���
		request.setAttribute("list", list);
	}

}
