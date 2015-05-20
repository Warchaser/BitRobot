package servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.SearchLogic;

public class AdminAddExpertInfo extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1586463458712224395L;

	/**
	 * Constructor of the object.
	 */
	public AdminAddExpertInfo() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
//		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
//		out.println("<HTML>");
//		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
//		out.println("  <BODY>");
//		out.print("    This is ");
//		out.print(this.getClass());
//		out.println(", using the GET method");
//		out.println("  </BODY>");
//		out.println("</HTML>");
//		out.flush();
//		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//指定前后台的输入输出字符集均为utf-8
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		//打开session
		HttpSession session = request.getSession();
		
		String expertName = request.getParameter("expert_name");
		String gender = request.getParameter("gender");
		String jobPosition = request.getParameter("job_position");
		String project = request.getParameter("project");
		String employmentDirection = request.getParameter("employment_direction");
		String org = request.getParameter("org");
		
		SearchLogic search = new SearchLogic();
		
		ResultSet rs = search.getResult("select max(Id) from info");
		
		int expertId = 0;
		try {
			rs.next();
			expertId = rs.getInt("max(Id)") + 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "insert into info(Id,expert_name,job_position,project,employment_direction,gender,org,del_sign) values ('" +
				expertId + "', '" +
				expertName + "', '" +
				jobPosition + "', '" +
				project + "', '" +
				employmentDirection + "', '" +
				gender + "', '" +
				org + "', " +
				"1" + ")";
		
		boolean isInsertSuccess = search.execute(sql);
		
		if(isInsertSuccess)
			session.setAttribute("response", "添加成功");
		else
			session.setAttribute("response", "添加失败");
		
		response.sendRedirect("../pages/Response.jsp");
		
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
