package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import util.SearchLogic;
import bean.ExpertInfoBean;
import bean.RelationshipBean;

/**
 * 获取专家的关系图用的数据的servlet
 * */

public class GetRelationshipServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5492901695561709870L;

	/**
	 * Constructor of the object.
	 */
	public GetRelationshipServlet() {
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
		
		//获取application中的键值对应的对象
		HttpSession session = request.getSession();
		
		ServletContext application = session.getServletContext();
		
		Map<String,RelationshipBean> relationMap = new HashMap<String,RelationshipBean>();
		
		List<RelationshipBean> listJson = new ArrayList<RelationshipBean>(); 
		
//		if(null == application.getAttribute("relationShipList")){
//			relationMap = new HashMap<String,RelationshipBean>();
//		}
//		else{
//			relationMap = (Map<String, RelationshipBean>) application.getAttribute("relationShipList");
//		}
		
		String expertName = request.getParameter("expertName").trim();
		
		int expertId = 0;
		
		Map<String,ExpertInfoBean> map = new HashMap<String,ExpertInfoBean>();
		
		map = (Map<String, ExpertInfoBean>) application.getAttribute("listMap");
		
		ExpertInfoBean bean = new ExpertInfoBean();
		
		bean = map.get(expertName);
		
		expertId = bean.getExpertId();
		//////////////////////////////////////////////////////////
		SearchLogic search = new SearchLogic();
		ResultSet rs = search.getResult("select * from relationship where expert_id like " 
						+ expertId + " limit " + 10);
		
		RelationshipBean relationshipBean = null;
		
		try {
			while(rs.next()){
				
				relationshipBean = new RelationshipBean();
				
				relationshipBean.setExpertId(expertId);
				relationshipBean.setExpertName(expertName);
				relationshipBean.setName(rs.getString("name"));
				relationshipBean.setRelationshipType(rs.getString("relationship_type"));
				relationshipBean.setR_beizhu(rs.getString("r_beizhu"));
				relationshipBean.setNewType(rs.getString("newtype"));
				
				relationMap.put(expertName, relationshipBean);
				
				listJson.add(relationshipBean);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		search.closeBD();
		
		application.setAttribute("relationShipList", map);
		
		JSONArray json = JSONArray.fromObject(listJson);
		
		response.getWriter().print(json.toString());

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
