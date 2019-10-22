package service.dto;

// �л��� ������ ������ �����ϱ� ���� DTO(Data Transition Object)
public class StudentDTO {
   private String stuNo = null;			// �й�
   private String stuName = null;		// ����
   private String pwd = null;			// ��ȣ 
   private String stuPhoneNo = null;	// �л� ����ó
   private String year = null;			// �г�
   private String profCode = null;		// �������� �ڵ�
   private String deptCode = null;		// �а� �ڵ�
   private String profName = null;		// �������� ����
   private String dept = null;			// �а���
   
   public String getProfName() 
   {
		return profName;    
   }
   
   public void setProfName(String profName) 
   {
		this.profName = profName;    
   }
   
   public String getDept() 
   {
		return dept;    
   }
   
   public void setDept(String dept) 
   {
		this.dept = dept;    
   }

   public String getStuNo() 
   {
		return this.stuNo;    
   }

   public void setStuNo(String stuNo) 
   {
		this.stuNo = stuNo;    
   }
 
   public String getStuName() 
   {
		return stuName;    
   }

   public void setStuName(String stuName) 
   {
		this.stuName = stuName;    
   }

   public String getPwd() 
   {
		return pwd;    
   }

   public void setPwd(String pwd) 
   {
		this.pwd = pwd;    
   }

   public String getStuPhoneNo() 
   {
		return stuPhoneNo;    
   }

   public void setStuPhoneNo(String stuPhoneNo) 
   {
		this.stuPhoneNo = stuPhoneNo;    
   }

   public String getYear() 
   {
		return year;    
   }

   public void setYear(String year) 
   {
		this.year = year;    
   }

   public String getProfCode() 
   {
		return profCode;    
   }

   public void setProfCode(String profCode) 
   {
		this.profCode = profCode;    
   }

   public String getDeptCode() 
   {
		return deptCode;    
   }

   public void setDeptCode(String deptCode) 
   {
		this.deptCode = deptCode;    
   }
}
