## Web Application의 구조   
### ⬛️ Model1   
* **JSP page에서** presentation logic, business logic, 입출력 데이터 처리, 실행 흐름제어 등을 **모두 구현**   

**◼️ 단점**  
* 복잡한 application의 경우 개발 및 유지보수의 어려움 발생  
* 프로그램 개발자와 웹 디자이너 사이의 작업의 분리가 어려움  

### ⬛️ Model2 (MVC pattern / MVC architecture)  
* **Model** : 입력 값 검증, business logic 수행 및 data 저장 관리(DB연동) 수행  
* **View** : UI 및 presentation(사용자 요청 처리 결과에 대한 출력) logic 구현  
* **Controller** : 사용자와 Model, View 사이의 실행 흐름 제어
 (사용자의 요청 수신, Model의 기능 선택 및 호출, View 선택 및 결과 전송 등)  

#### ⬛️ 모델(Model)  
- business logic 구현  
- 데이터 처리  
Database, file system, legacy system 등과의 연동 수행  

![image](https://user-images.githubusercontent.com/50273050/68199048-9bac9400-0000-11ea-92bb-1321f3af87b6.png)  

**◼️ Domain class**  
* Value Object(VO), **Data Transfer Object(DTO)** 를 정의  
* Application에서 사용되는 **데이터의 표현 및 전달**을 위한 객체  
* Database에 저장되는 객체는 database의 table과 유사한 구조를 가짐  
* 속성에 대한 **getter & setter** methods 포함  

```java
package model;

// 사용자 관리를 위해 필요한 도메인 클래스. USERINFO 테이블과 대응됨

public class User {
	private String userId;
	private String password;
	private String name;
	private String email;
	private String phone;

	public User() { }		// 기본 생성자
	
	public User(String userId, String password, String name, String email, String phone) {
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
		this.phone = phone;
	}

	public void update(User updateUser) {
        this.password = updateUser.password;
        this.name = updateUser.name;
        this.email = updateUser.email;
        this.phone = updateUser.phone;
    }
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	// ... 중간 생략

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/* 비밀번호 검사 */
	public boolean matchPassword(String password) {
		if (password == null) {
			return false;
		}
		return this.password.equals(password);
	}
	
	public boolean isSameUser(String userid) {
        return this.userId.equals(userid);
    }

	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", email=" + email + ", phone="
				+ phone + "]";
	}	
}
```

**◼️ Business Object(BO) class**  
* **Business logic**을 구현하는 클래스  
(Business logic이 간단할 경우, BO 대신 Manager class 등에서 구현 가능)  

**◼️ Data Access Object(DAO) class**  
* **Database**나 기존 legacy system과 **연동**하여 **데이터 처리 및 관리 수행**  
* **JDBC API** 등을 사용하여 구현

```java
package model.dao;
// import 생략

/*
 사용자 관리를 위해 데이터베이스 작업을 전담하는 DAO 클래스
 USERINFO 테이블에 사용자 정보를 추가, 수정, 삭제, 검색 수행 
*/

public class UserDAO {
	private JDBCUtil jdbcUtil = null;
	
	public UserDAO() {			
		jdbcUtil = new JDBCUtil();	// JDBCUtil 객체 생성
	}
	
	// 사용자 관리 테이블에 새로운 사용자 생성.
	public int create(User user) throws SQLException {
		String sql = "INSERT INTO USERINFO (userId, name, password, email, phone) "
					+ "VALUES (?, ?, ?, ?, ?)";		
		Object[] param = new Object[] {user.getUserId(), user.getPassword(), 
				user.getName(), user.getEmail(), user.getPhone()};				
		jdbcUtil.setSqlAndParameters(sql, param);	// JDBCUtil 에 insert문과 매개 변수 설정
						
		try {				
			int result = jdbcUtil.executeUpdate();	// insert 문 실행
			return result;
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {		
			jdbcUtil.commit();
			jdbcUtil.close();	// resource 반환
		}		
		return 0;			
	}

	// .. 수정 및 삭제 생략

	// 주어진 사용자 ID에 해당하는 사용자 정보를 데이터베이스에서 찾아 User 도메인 클래스에 저장하여 반환.
	public User findUser(String userId) throws SQLException {
        String sql = "SELECT password, name, email, phone "
        			+ "FROM USERINFO "
        			+ "WHERE userid=? ";              
		jdbcUtil.setSqlAndParameters(sql, new Object[] {userId});	// JDBCUtil에 query문과 매개 변수 설정

		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query 실행
			if (rs.next()) {						// 학생 정보 발견
				User user = new User(		// User 객체를 생성하여 학생 정보를 저장
					userId,
					rs.getString("password"),
					rs.getString("name"),
					rs.getString("email"),
					rs.getString("phone"));
				return user;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 반환
		}
		return null;
	}

	/**
	 * 전체 사용자 정보를 검색하여 List에 저장 및 반환
	 */
	public List<User> findUserList() throws SQLException {
        String sql = "SELECT userId, password, name, email, phone " 
        		   + "FROM USERINFO "
        		   + "ORDER BY userId";
		jdbcUtil.setSqlAndParameters(sql, null);		// JDBCUtil에 query문 설정
					
		try {
			ResultSet rs = jdbcUtil.executeQuery();			// query 실행			
			List<User> userList = new ArrayList<User>();	// User들의 리스트 생성
			while (rs.next()) {
				User user = new User(			// User 객체를 생성하여 현재 행의 정보를 저장
					rs.getString("userId"),
					rs.getString("password"),
					rs.getString("name"),
					rs.getString("email"),
					rs.getString("phone"));	
				userList.add(user);				// List에 User 객체 저장
			}		
			return userList;					
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 반환
		}
		return null;
	}
	
	/**
	 * 전체 사용자 정보를 검색한 후 현재 페이지와 페이지당 출력할 사용자 수를 이용하여
	 * 해당하는 사용자 정보만을 List에 저장하여 반환.
	 * ... 생략
	 */

	// 주어진 사용자 ID에 해당하는 사용자가 존재하는지 검사 
	public boolean existingUser(String userId) throws SQLException {
		String sql = "SELECT count(*) FROM USERINFO WHERE userid=?";      
		jdbcUtil.setSqlAndParameters(sql, new Object[] {userId});	// JDBCUtil에 query문과 매개 변수 설정

		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query 실행
			if (rs.next()) {
				int count = rs.getInt(1);
				return (count == 1 ? true : false);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 반환
		}
		return false;
	}
}
```

**◼️ Manager(Facade) class**  
* JSP(Model1) 또는 Controller(Model2)에서 **모델에 접근하기 위해 사용하는 인터페이스(API)** 를 제공하는 Facade class
외부에서 모델에 접근할 때 반드시 manager object를 이용하도록 함  
* Manager object는 domain object를 통해 business object와 DAO에 데이터를 전달하며, business logic 및 데이터 처리 결과를 JSP 또는 Controller에 전달하는 역할을 수행  
* **Manager class에서 간단한 business logic을 직접 구현 가능**  

```java
package model.service;

import java.sql.SQLException;
import java.util.List;

import model.User;
import model.dao.UserDAO;

/**
 * 사용자 관리 API를 사용하는 개발자들이 직접 접근하게 되는 클래스.
 * UserDAO를 이용하여 데이터베이스에 데이터 조작 작업이 가능하도록 하며,
 * 데이터베이스의 데이터들을 이용하여 비지니스 로직을 수행하는 역할을 한다.
 * 비지니스 로직이 복잡한 경우에는 비지니스 로직만을 전담하는 클래스를 
 * 별도로 둘 수 있다.
 */
public class UserManager {
	private static UserManager userMan = new UserManager();
	private UserDAO userDAO;
	private UserAnalysis userAanlysis;

	private UserManager() {
		try {
			userDAO = new UserDAO();
			userAanlysis = new UserAnalysis(userDAO);
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
	
	public static UserManager getInstance() {
		return userMan;
	}
	
	public int create(User user) throws SQLException, ExistingUserException {
		if (userDAO.existingUser(user.getUserId()) == true) {
			throw new ExistingUserException(user.getUserId() + "는 존재하는 아이디입니다.");
		}
		return userDAO.create(user);
	}

	public int update(User user) throws SQLException {
		return userDAO.update(user);
	}	

	public int remove(String userId) throws SQLException {
		return userDAO.remove(userId);
	}

	public User findUser(String userId)
		throws SQLException, UserNotFoundException {
		User user = userDAO.findUser(userId);
		
		if (user == null) {
			throw new UserNotFoundException(userId + "는 존재하지 않는 아이디입니다.");
		}		
		return user;
	}

	public List<User> findUserList() throws SQLException {
			return userDAO.findUserList();
	}
	
	public List<User> findUserList(int currentPage, int countPerPage)
		throws SQLException {
		return userDAO.findUserList(currentPage, countPerPage);
	}

	public boolean login(String userId, String password)
		throws SQLException, UserNotFoundException, PasswordMismatchException {
		User user = findUser(userId);

		if (!user.matchPassword(password)) {
			throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
		}
		return true;
	}

	public List<User> makeFriends(String userId) throws Exception {
		return userAanlysis.recommendFriends(userId);
	}
	
	public UserDAO getUserDAO() {
		return this.userDAO;
	}
}
```

#### ⬛️ MVC 구조 \- request 처리 과정  
1. 모든 사용자 요청은 먼저 **Controller(Servlet)** 로 전달  
1. **Controller** 는 사용자 요청에 대한 처리를 **Model에 위임(parameter 전달)**  
1. **Model** 은 요청 처리를 완료한 후 **Controller에게 결과를 반환함**  
1. **Controller** 는 사용자에게 보여줄 **View를 호출함(적절한 View 선택 & Model로부터 반환된 결과를 View에 전달)**  
1. **View** 는 요청 처리 결과 화면을 생성하여 **사용자에게 전송함(response)**  

![image](https://user-images.githubusercontent.com/50273050/68201273-cd275e80-0004-11ea-9358-b5337bd03535.png)  

#### ⬛️ Controller   

**◼️ DispatcherServlet class** (Front Controller)  
- 애플리케이션의 초기 진입점으로 설정  
![image](https://user-images.githubusercontent.com/50273050/68203376-fba73880-0008-11ea-8be1-c737f2d8191c.png)  

```java
package controller;
// import 생략

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@WebServlet(name = "userSevlet", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    
    private RequestMapping rm;

    @Override
    public void init() throws ServletException {
        rm = new RequestMapping();
        rm.initMapping();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) 
    	throws ServletException, IOException {
    	logger.debug("Method : {}, Request URI : {}, ServletPath : {}", 
    			request.getMethod(), request.getRequestURI(), request.getServletPath());
    	String contextPath = request.getContextPath();
    	String servletPath = request.getServletPath();
    	
    	// URL 중 servletPath에 대응되는 controller를 구함
        Controller controller = rm.findController(servletPath);
        try {
        	// controller를 통해 request 처리 후, 이동할 uri를 반환 받음
            String uri = controller.execute(request, response);
            
 			// 반환된 uri에 따라 forwarding 또는 redirection 여부를 결정하고 이동 
            if (uri.startsWith("redirect:")) {	
            	// redirection 지시
            	String targetUri = contextPath + uri.substring("redirect:".length());
            	response.sendRedirect(targetUri);	// redirect to url            
            }
            else {
            	// forwarding 수행
            	RequestDispatcher rd = request.getRequestDispatcher(uri);
                rd.forward(request, response);		// forward to the view page
            }                   
        } catch (Exception e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }
}
```

**◼️ RequestMapping**  
```java
package controller;
// import 생략

public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    
    // 각 요청 uri에 대한 controller 객체를 저장할 HashMap 생성
    private Map<String, Controller> mappings = new HashMap<String, Controller>();

    public void initMapping() {
    	// 각 uri에 대응되는 controller 객체를 생성 및 저장
        mappings.put("/", new ForwardController("index.jsp"));
        mappings.put("/user/login/form", new ForwardController("/user/loginForm.jsp"));
        mappings.put("/user/login", new LoginController());
        mappings.put("/user/logout", new LogoutController());
        mappings.put("/user/list", new ListUserController());
        mappings.put("/user/view", new ViewUserController());
        mappings.put("/user/register/form", new ForwardController("/user/registerForm.jsp"));
        mappings.put("/user/register", new RegisterUserController());
        mappings.put("/user/update/form", new UpdateUserFormController());
        mappings.put("/user/update", new UpdateUserController());
        mappings.put("/user/delete", new DeleteUserController());
        
        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String uri) {	
    	// 주어진 uri에 대응되는 controller 객체를 찾아 반환
        return mappings.get(uri);
    }
}
```

**◼️ Controller interface**   
- **모든 Controller들이 구현해야 할 execute() method를 선언한 인터페이스**    
- **반환 문자열** : 사용자의 request를 처리한 후 이동할 view나 redirection 주소  
 "redirect:"로 시작할 경우 \-> redirection 지시  
&nbsp;&nbsp;&nbsp; 그렇지 않으면 \-> view page(JSP)로 forwarding 수행 
```java
package controller; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;

public interface Controller { 
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception; 
}
```

**◼️ ForwardController**   
- 미리 정해진 URI로 forwarding을 실행하기 위한 controller  
(로그인 화면과 같이 정적인 from을 정의한 페이지로 바로 이동)     
```java
public class ForwardController implements Controller { 
	private String forwardUrl;
	public ForwardController(String forwardUrl) { 
		if (forwardUrl == null) { 
			throw new NullPointerException("forwardUrl is null. 이동할 URL을 입력하세요."); 
		} 
		this.forwardUrl = forwardUrl; 
	}
	@Override 
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception { 
		return forwardUrl; 
	}
}
```

![image](https://user-images.githubusercontent.com/50273050/68203856-1cbc5900-000a-11ea-8f03-bc4122155c6b.png)  
![image](https://user-images.githubusercontent.com/50273050/68203897-3198ec80-000a-11ea-87a3-d30a4b0201b6.png)  

<hr>

### ⬛️ Quiz
**Q1. Model1 구조와 Model2 구조의 장단점을 서술하시오.**

**Q2. MVC architecture의 세 가지 요소를 쓰고, 각각의 기능에 대해 설명하시오.**

**Q3. 아래 설명에 맞는 클래스의 이름과 MVC의 세 가지 요소 중 어느 요소에 해당되는지 쓰시오.** 
1) 애플리케이션의 초기 진입점으로 클라이언트의 request를 받는 클래스
2) 모델에 접근하기 위해 사용하는 인터페이스(API)를 제공하는 클래스
3) 각 요청 uri에 대응되는 controller 객체를 찾아 반환하는 클래스

**Q4. url-pattern "/" 의 의미에 대해 서술하시오.**

**Q5. MVC pattern의 각 구성요소의 역할을 설명하고, MVC pattern을 쓰지 않은 Model 1과 같은 구조는 어떨 때 부적합한지 서술하시오.**  
① Model :  
② View :  
③ Controller :  
④ 단점 :  

**Q6. Model을 구성하는 Class들의 기능을 서술하시오.**  
① Domain class :  
② BO class :  
③ DAO class :  
④ Manager(façade) class :  

**Q7. DispatcherServlet class(Front Controller) 코드의 빈칸을 채우시오.**  
![image](https://user-images.githubusercontent.com/50273050/68281167-682e4000-00ba-11ea-9aa2-b4118777fc49.png)  

**Q8. request.getAttribute()와 request.getParameter()의 차이점은?**  

<br>

**A1.**  
Model1 구조는 대부분의 일을 JSP가 모두 처리해야 하지만, Model2(MVC) 구조는 작업들을 분리하여 개발 가능하도록 구성되어 있다. Model1 구조는 프로그램 개발자와 웹 디자이너 사이의 작업의 분리가 어려웠으나, MVC 구조는 분리하여 개발 가능하도록 했기 때문에 개발 및 유지보수의 효율성이 높다. 그러나 MVC구조는 Controller 부분에 속하는 클래스들을 별도로 정의해야 하고, request에 대한 mapping은 애플리케이션에 종속적이므로 재사용이 어렵다.

**A2.**  
* Model :  business logic 수행 및 data 저장 관리 수행   
* View : UI 및 presentation logic 구현   
* Controller : Model과 View 사이의 실행 흐름 제어   
 (사용자의 요청 수신, Model의 기능 선택 및 호출, View 선택 및 결과 전송 등)   

**A3.**  
1) DispatcherServlet / Controller  
2) Manager / Model  
3) RequestMapping / Controller  

**A4.**  
애플리케이션에 대한 모든 request를 해당 servlet이 받음을 의미한다.  

**A5.**  
① Model : business logic를 구현하고 database, file system등과의 연동을 하며 data 저장/관리를 수행한다.  
② View : UI 및 presentation logic 구현  
③ Controller : Model과 View 사이의 실행 흐름 제어  
④ 단점:  
Model 1은 JSP page에서 presentation logic과 business logic, 입출력 데이터 처리, 실행 흐름 제어 등을 모두 구현하는 구조이다.  
이 구조는 개발 및 유지보수, 재활용에 어려움이 크므로 복잡하고 변경이 많은 application의 경우엔 부적합하다.  

**A6.**  
① Application에서 사용되는 데이터의 표현 및 전달을 위한 객체인 VO 및 DTO를 정의한다.  
속성에 대한 setter & getter methods를 포함한다.  
② Business logic을 구현하는 클래스  
③ Database나 기존 legacy system과 연동하여 데이터 처리 및 관리를 수행한다. JDBC API 등을 사용하여 구현한다.  
④ JSP 또는 Controller에서 모델에 접근하기 위해 사용하는 인터페이스(API)를 제공하는 Façade class이다.  

**A7.**  
① HttpServlet  
② service  
③ execute  
④ sendRedirect  
⑤ getRequestDispatcher  

**A8.**  
getParameter()메서드의 경우 String타입을 반환하는 반면, getAttribute()는 Object 타입을 리턴하기 때문에 주로 빈 객체나 다른 클래스를 받아올때 사용된다. 또한, getParameter()는 웹브라우저에서 전송받은 request영역의 값을 읽어오고 getAttribute()의 경우 setAttribute()속성을 통한 설정이 없으면 무조건 null값을 리턴한다.  


