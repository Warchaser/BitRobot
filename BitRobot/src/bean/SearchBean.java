package bean;

/**
 * 搜索用的bean
 * */

public class SearchBean {
	
	/**
	 * 文章标题
	 * */
	private String title;
	
	/**
	 * 文章摘要
	 * */
	private String abs;
	
	/**
	 * 文章链接
	 * */
	private String url;
	
	/**
	 * 发布日期
	 * */
	private String date;
	
	/**
	 * 专家姓名
	 * */
	private String expert_nmae;
	
	/**
	 * 专家所在机构
	 * */
	private String expert_org;

	public SearchBean(){
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getExpert_nmae() {
		return expert_nmae;
	}

	public void setExpert_nmae(String expert_nmae) {
		this.expert_nmae = expert_nmae;
	}

	public String getExpert_org() {
		return expert_org;
	}

	public void setExpert_org(String expert_org) {
		this.expert_org = expert_org;
	}
}
