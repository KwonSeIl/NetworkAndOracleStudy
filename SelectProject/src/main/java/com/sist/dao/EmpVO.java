package com.sist.dao;
/*
 * EMPNO    NOT NULL NUMBER(4)    ==> int
	ENAME             VARCHAR2(10) ==>String
	JOB               VARCHAR2(9)  ==> String
	MGR               NUMBER(4)    ==>int
	HIREDATE          DATE         ==>Date
	SAL               NUMBER(7,2)  ==>int/double
	COMM              NUMBER(7,2)  ==>int/double
	DEPTNO            NUMBER(2) 	==> int
 */
import java.util.*;
// DESC table; => ������ Ȯ���ϰ� ����� ==> �÷���� �������� ��ġ��Ų��(���ϴ�)
// VO => Value Object ==> ����Ŭ���� ������ ���� �����͸� ������ �������� �����
public class EmpVO {
	private int empno,mgr,sal,comm,deptno;
	private String ename,job;
	private Date hiredate;
	private DeptVO dvo=new DeptVO(); // JOIN => �ڹ�:����Ŭ����
	private SalGradeVO svo=new SalGradeVO(); // JOIN =>�ڹ�:����Ŭ����
	
	public SalGradeVO getSvo() {
		return svo;
	}
	public void setSvo(SalGradeVO svo) {
		this.svo = svo;
	}
	public DeptVO getDvo() {
		return dvo;
	}
	public void setDvo(DeptVO dvo) {
		this.dvo = dvo;
	}
	public int getEmpno() {
		return empno;
	}
	public void setEmpno(int empno) {
		this.empno = empno;
	}
	public int getMgr() {
		return mgr;
	}
	public void setMgr(int mgr) {
		this.mgr = mgr;
	}
	public int getSal() {
		return sal;
	}
	public void setSal(int sal) {
		this.sal = sal;
	}
	public int getComm() {
		return comm;
	}
	public void setComm(int comm) {
		this.comm = comm;
	}
	public int getDeptno() {
		return deptno;
	}
	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Date getHiredate() {
		return hiredate;
	}
	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}
	

}
