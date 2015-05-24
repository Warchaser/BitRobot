package servlet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import bean.ExpertInfoBean;
import bean.InterviewsBean;
import bean.PaperBean;
import bean.PatentBean;
import bean.QuestionBean;
import bean.SearchBean;
import bean.UpdatesBean;
import util.SearchLogic;

/**
 * SendQuery获取查询的关键字并返回相关信息的servlet
 * */

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
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//指定前后台的输入输出字符集均为utf-8
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		//获取application中的键值对应的对象
		ServletContext application = request.getServletContext();
		
		Map<String,ExpertInfoBean> map = new HashMap<String,ExpertInfoBean>();
		
		map = (Map<String, ExpertInfoBean>) application.getAttribute("listMap");

		JSONObject jsonObject = new JSONObject();
		
//		map 可能为空，因为数据库连接失败或者数据库原因导致map就是一个null	
//		if(null == map){
//			jsonObject.put("sendResult",0);
//			return ;			
//		}
		
		//获取前台输入框中的信息
		String content = request.getParameter("content").trim();
		//定义转换之后的字符串
		String contentTrans = "";
		
		SearchLogic search = new SearchLogic();
		
		//问题引导搜索，固定搜索question 
		String [] queryStrings = {content};
		String [] fields = {"first_key"};
		String indexPath = "D:\\index1\\question";
		
		List<QuestionBean> searchListQuestion = new ArrayList<QuestionBean>();
		
		Date sendTime = new Date();
		
		File file = new File(indexPath);
		
		//不存在目录就创建
		if(!file.exists()) {
			file.mkdir();
        }
		
		//不存在索引就走完整的流程，创建问题索引
		if(file.listFiles().length == 0){
			search.createIndex(search.getResult("select id,first_key,table_name,index_file,second_key,field,bean from question " ),"question", indexPath);
		}
		
		//返回问题分流表
		searchListQuestion = search.searcherQuestionBean(queryStrings, fields, indexPath);
		System.out.println(searchListQuestion.size());
		System.out.println("分流完毕");
		
		//choice  进入二级表搜索判断变量 默认值为1。  1为需要进入   0为不需要
		int choice = 1;

		if(searchListQuestion.size() != 0 &&  !searchListQuestion.get(0).getSecond_key().equals("null") ) {
			try {
			
			//去除返回的二级关键字中分隔符“；”
			String[] second_key = searchListQuestion.get(0).getSecond_key().split(";",0);
			
			//向前台发送返回数据列表   二级关键字或信息列表
			contentTrans += content+"的信息过于笼统,</br>";
			contentTrans += "请选择如下分类:</br>";
			for(int i = 0;i<second_key.length;i++)
				contentTrans += (i + 1)+"、"+second_key[i]+"</br>";
			jsonObject.put("sendResult",1);
			
			jsonObject.put("content", contentTrans);
			
			jsonObject.put("sendTime",(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(sendTime));		
			
			//进入二级表搜索判断变量置0。  1为需要进入   0为不需要
			choice = 0;
			
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		
		
		//进入二级表搜索
		if(choice == 1  ){
			
			//自适应list表定义
			List<?> searchList = null;
			
			//返回的数据表长度大于0且数据首关键字 First_key ！= null时，进入二级类表搜索，否则直接跳过搜索返回查询结果
			if(searchListQuestion.size() > 0 && !searchListQuestion.get(0).getFirst_key().equals("null")){
				String [] queryString = {content};
				String [] field = {searchListQuestion.get(0).getField()};
				indexPath = "D:\\index1\\"+searchListQuestion.get(0).getIndex_file();
				
				file = new File(indexPath);
				//不存在目录就创建
				if(!file.exists()) {
					file.mkdir();
		        }
				
				//不存在索引就走完整的流程，创建问题索引
				if(file.listFiles().length == 0){
					search = new SearchLogic();
					search.createIndex(search.getResult("select * from " + 
					searchListQuestion.get(0).getTable_name()),searchListQuestion.get(0).getTable_name() ,indexPath);
				}
				
				System.out.println(searchListQuestion.get(0).getBean());
				//根据不同的bean类型定义不同类型list
				if(searchListQuestion.get(0).getBean().equals("PaperBean")){
					searchList = new ArrayList<PaperBean>();
					searchList = search.searcherPaperBean(queryString, field, indexPath);
					search.closeBD();
				}else if(searchListQuestion.get(0).getBean().equals("InterviewsBean") ){
					searchList = new ArrayList<InterviewsBean>();
					searchList = search.searcherInterviewsBean(queryString, field, indexPath);
					search.closeBD();
				}else if(searchListQuestion.get(0).getBean().equals("PatentBean") ){
					searchList = new ArrayList<PatentBean>();
					searchList = search.searcherPatentBean(queryString, field, indexPath);
					search.closeBD();
				}else if(searchListQuestion.get(0).getBean().equals("UpdatesBean") ){
					searchList = new ArrayList<UpdatesBean>();
					searchList = search.searcherUpdatesBean(queryString, field, indexPath);
					search.closeBD();
				}
			}
			
			int listSize = 0;
			if(searchList != null)
				listSize = searchList.size();
			
			//获取请求的数量，默认值为5，数量小于5且数据不是行程信息时，返回全部全部数据，数量大于5时，返回前5个数据
			int requestNum = 5;
			
			if(listSize >= requestNum && searchListQuestion.get(0).getBean().equals("UpdatesBean")){
				listSize =  requestNum ;
			}
			
			//专家姓名
			String expertName = "";
	
			try {
				
				//向前台发送专家基本信息
				if(null != map.get(content)){
					expertName = map.get(content).getExpertName();
					contentTrans += "少侠是要问我的信息？</br>";
					contentTrans += "我是" + content + ",";
					contentTrans += map.get(content).getJob_position().replace("；", "</br>") + "。</br>";
					contentTrans += "参与过" + map.get(content).getProject().replace("；", "</br>");
					contentTrans += map.get(content).getEmployment_direction();
					jsonObject.put("sendResult",1);
					
					jsonObject.put("content", contentTrans);
					
					jsonObject.put("sendTime",(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(sendTime));
					
				}
				
				//向前台发送其他信息
				else{
					
					//向前台发送采访信息
					if(searchListQuestion.get(0).getBean() == "InterviewsBean"){
						for(int i = 0;i < listSize;i++){
							contentTrans += ((InterviewsBean) searchList.get(i)).getTitle();
							contentTrans += "</br>";
							contentTrans += ((InterviewsBean) searchList.get(i)).getAbs();
							contentTrans += "</br>";
							contentTrans += "<a href=" + ((InterviewsBean) searchList.get(i)).getUrl() + 
									" target=\"_blank\">" + "点此链接" + "</a></br>";
						}
						jsonObject.put("sendResult",1);
						
						jsonObject.put("content", contentTrans);
						
						jsonObject.put("sendTime",(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(sendTime));
					}
					
					//向前台发送论文信息
					else if(searchListQuestion.get(0).getBean().equals("PaperBean")){
						for(int i = 0;i < listSize;i++){
							contentTrans += ((PaperBean) searchList.get(i)).getPaper_name();
							contentTrans += "</br>";
							contentTrans += ((PaperBean) searchList.get(i)).getAbs();
							contentTrans += "</br>";
							contentTrans += ((PaperBean) searchList.get(i)).getGuanjianci();
							contentTrans += "</br>";
							contentTrans += "<a href=" + ((PaperBean) searchList.get(i)).getUrl() + 
									" target=\"_blank\">" + "点此链接" + "</a></br>";
						}
						jsonObject.put("sendResult",1);
						
						jsonObject.put("content", contentTrans);
						
						jsonObject.put("sendTime",(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(sendTime));
					}
					
					//向前台发送专利信息
					else if(searchListQuestion.get(0).getBean().equals("PatentBean")){
						for(int i = 0;i < listSize;i++){
							contentTrans += ((PatentBean) searchList.get(i)).getPatent_name();
							contentTrans += "</br>";
							contentTrans += ((PatentBean) searchList.get(i)).getPatent_date();
							contentTrans += "</br>";
							contentTrans += ((PatentBean) searchList.get(i)).getPatent_id();
							contentTrans += "</br>";
							contentTrans += "<a href=" + ((PaperBean) searchList.get(i)).getUrl() + 
									" target=\"_blank\">" + "点此链接" + "</a></br>";
						}
						jsonObject.put("sendResult",1);
						
						jsonObject.put("content", contentTrans);
						
						jsonObject.put("sendTime",(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(sendTime));
					}
					
					//向前台发送行程信息
					else if(searchListQuestion.get(0).getBean().equals("UpdatesBean")){
						for(int i = 0;i < listSize;i++){
							contentTrans += ((UpdatesBean) searchList.get(i)).getDate();
							contentTrans += "</br>";
							contentTrans += ((UpdatesBean) searchList.get(i)).getAbs();
							contentTrans += "</br>";
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
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
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

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(req, resp);
	}
}
