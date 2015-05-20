package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.SearchLogic;
import bean.ExpertInfoBean;

public class AdminAddExpertReward extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3641233646102088755L;

	/**
	 * Constructor of the object.
	 */
	public AdminAddExpertReward() {
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
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
		
		//获取application中的键值对应的对象
		HttpSession session = request.getSession();
		ServletContext application = session.getServletContext();
		
		Map<String,ExpertInfoBean> map = new HashMap<String,ExpertInfoBean>();
		map = (Map<String, ExpertInfoBean>) application.getAttribute("listMap");
		
		String expertName = request.getParameter("expertOptions");
		String date = request.getParameter("date");
		String rewardName = request.getParameter("reward_name");
		String org = request.getParameter("org");
		String coPartner = request.getParameter("co_partner");
		int expertId = map.get(expertName).getExpertId();
		
		SearchLogic search = new SearchLogic();
		
		String sqlReward = "insert into reward_detail (reward_name,expert_id,reward_date,reward_org,co_partner) values('" + 
							rewardName + "', " + 
							expertId + ",'" + 
							date + "','" + 
							org + "','" + 
							coPartner + "')";
		
		boolean isInsertRewardSuccess = search.execute(sqlReward);
		
		String sqlCount = "select count(expert_id) from reward_detail where expert_id like " + expertId;
		
		ResultSet rs = search.getResult(sqlCount);
		
		int count = 0;
		try {
			rs.next();
			count = rs.getInt("count(expert_id)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sqlUpdateInfo = "update info set count_reward=" + count + " where Id = " + expertId;
		
		boolean isUpdateInfoSuccess = search.execute(sqlUpdateInfo);
		
		if(isInsertRewardSuccess && count != 0 && isUpdateInfoSuccess){
			session.setAttribute("response", "添加成功");
		}
		else{
			session.setAttribute("response", "添加失败");
		}
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
