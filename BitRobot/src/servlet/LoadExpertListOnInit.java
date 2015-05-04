package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import util.SearchLogic;
import bean.ExpertInfoBean;


/**
 * 此类是用来获取数据库中专家列表的专家的各种信息，
 * 并放入application
 * */
public class LoadExpertListOnInit extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8935146699676922497L;

	/**
	 * Constructor of the object.
	 */
	public LoadExpertListOnInit() {
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
		
		doPost(request,response);
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

//		response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
//		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
//		out.println("<HTML>");
//		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
//		out.println("  <BODY>");
//		out.print("    This is ");
//		out.print(this.getClass());
//		out.println(", using the POST method");
//		out.println("  </BODY>");
//		out.println("</HTML>");
//		out.flush();
//		out.close();
		
		//指定前后台的输入输出字符集均为utf-8
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		
		ServletContext application = session.getServletContext();
				
		SearchLogic search = new SearchLogic();
		ResultSet rs = search.getResult("select * from info");
		
		if(null == rs){
			response.getWriter().print("0");
		}
		
		Map<String,ExpertInfoBean> map = new HashMap<String,ExpertInfoBean>();
		
		List<String> expertList = new ArrayList<String>();
		
		ExpertInfoBean bean = null;
		
		try {
			while(rs.next()){
				
				bean = new ExpertInfoBean();
				bean.setExpertId(rs.getInt("Id"));
				bean.setExpertName(rs.getString("expert_name"));
				bean.setBirth_death(rs.getString("birth_death"));
				bean.setJob_position(rs.getString("job_position"));
				bean.setProject(rs.getString("project"));
				bean.setCount_reward(rs.getInt("count_reward"));
				bean.setCount_paper(rs.getInt("count_paper"));
				bean.setCount_patent(rs.getInt("count_patent"));
				bean.setEmployment_direction(rs.getString("employment_direction"));
				bean.setGender(rs.getString("gender"));
				bean.setOrg(rs.getString("org"));
				
				map.put(bean.getExpertName(), bean);
				
				expertList.add(bean.getExpertName());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		search.closeBD();
		
		application.setAttribute("listMap", map);
		
		response.getWriter().print(expertList);
		
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
