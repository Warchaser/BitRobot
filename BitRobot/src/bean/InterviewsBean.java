package bean;


/**
 *百度搜集的表 
 * 
 * */
public class InterviewsBean {
	
	/**
	 * 专家id
	 * */
	private String expert_id;
	
	/**
	 * 采访日期
	 * */
	private String date;
	
	/**
	 * 报道标题
	 * */
	private String title;
	
	/**
	 * 报道机构
	 * */
	private String org;
	
	/**
	 * 报道作者
	 * */
	private String author;
	
	/**
	 * 摘要
	 * */
	private String abs;
	
	/**
	 * 链接
	 * */
	private String url;

	public InterviewsBean(){
		
	}
	
	public String getExpert_id() {
		return expert_id;
	}

	public void setExpert_id(String expert_id) {
		this.expert_id = expert_id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAbs() {
		return abs;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
