package servlet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import bean.SearchBean;
import dbtools.DBConnection;
import util.SearchLogic;
import util.StringFormat;

public class SendQuery extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9066548647021272430L;

	/**
	 * Constructor of the object.
	 */
	public SendQuery() {
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
		
		//获取前台输入框中的信息
		String content = request.getParameter("content").trim();
		//定义转换之后的字符串
		String contentTrans = "";
		
		SearchLogic search = new SearchLogic();
		
		String [] queryStrings = {content,content + "*"};
		String [] fields = {"title","abs"};
		String indexPath = "D:\\index1";
		
		List<SearchBean> searchList = new ArrayList<SearchBean>();
		
		JSONObject jsonObject = new JSONObject();
		
		Date sendTime = new Date();
		
		File file = new File(indexPath);
		
		//不存在目录就创建
		if(!file.exists()) {
			file.mkdir();
        }
		
		//不存在索引就走完整的流程
		if(file.listFiles().length == 0){
			search.createIndex(search.getResult("select expert_id,date,title,org,author,abs,url from interviews"), indexPath);
		}
		
		searchList = search.searcher(queryStrings, fields, indexPath);
		search.closeBD();
		
		int listSize = searchList.size();
		//获取请求的数量
		int requestNum = 5;
		
		if(listSize <= requestNum){
			requestNum = 0; 
		}

		try {
			if(listSize > 0){
				for(int i = listSize - 1;i >= listSize - requestNum;i--){
					contentTrans += searchList.get(i).getTitle();
					contentTrans += "</br>";
					contentTrans += searchList.get(i).getAbs();
					contentTrans += "</br>";
					contentTrans += "<a href=" + searchList.get(i).getUrl() + " target=\"_blank\">" + "点此链接" + "</a></br>";
				}
				jsonObject.put("sendResult",1);
				
				jsonObject.put("content", contentTrans);
				
				jsonObject.put("sendTime",(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(sendTime));
			}
			else{
				jsonObject.put("sendResult",1);
				
				jsonObject.put("content", "木有查到啊～亲～");
				
				jsonObject.put("sendTime",(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(sendTime));
			}
			
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		response.getWriter().print(jsonObject);
		
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
