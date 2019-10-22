package service.dto;

// 학생과 관련한 정보를 저장하기 위한 DTO(Data Transition Object)
public class StudentDTO {
   private String stuNo = null;			// 학번
   private String stuName = null;		// 성명
   private String pwd = null;			// 암호 
   private String stuPhoneNo = null;	// 학생 연락처
   private String year = null;			// 학년
   private String profCode = null;		// 지도교수 코드
   private String deptCode = null;		// 학과 코드
   private String profName = null;		// 지도교수 성명
   private String dept = null;			// 학과명
   
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
