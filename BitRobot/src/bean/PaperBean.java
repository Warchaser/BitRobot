package bean;


/**
 *专家论文表 
 * 
 * */
public class PaperBean {
	
	/**
	 * 论文标题
	 * */
	private String paper_name;

	/**
	 * 专家id
	 * */
	private String expert_id;
	
	/**
	 * 专家所属机构
	 * */
	private String expert_org;
	
	/**
	 * 论文关键词
	 * */
	private String guanjianci;
	
	/**
	 * 项目基金
	 * */
	private String fundation;
	
	/**
	 * 论文摘要
	 * */
	private String abs;

	/**
	 * 收录文集
	 * */
	private String parent_context;
	
	/**
	 * 会议名称
	 * */
	private String meeting_name;
	
	/**
	 * 会议日期
	 * */
	private String meeting_date;
	
	/**
	 * 会议地址
	 * */
	private String meeting_address;
	
	/**
	 * 会议机构
	 * */
	private String meeting_org;
	
	/**
	 * 论文链接
	 * */
	private String url;
	
	/**
	 * 论文作者
	 * */
	private String author_cn;

	public PaperBean(){
		
	}
	public String getPaper_name() {
		return paper_name;
	}

	public void setPaper_name(String paper_name) {
		this.paper_name = paper_name;
	}

	public String getExpert_id() {
		return expert_id;
	}

	public void setExpert_id(String expert_id) {
		this.expert_id = expert_id;
	}

	public String getExpert_org() {
		return expert_org;
	}

	public void setExpert_org(String expert_org) {
		this.expert_org = expert_org;
	}

	public String getGuanjianci() {
		return guanjianci;
	}

	public void setGuanjianci(String guanjianci) {
		this.guanjianci = guanjianci;
	}

	public String getFundation() {
		return fundation;
	}

	public void setFundation(String fundation) {
		this.fundation = fundation;
	}

	public String getParent_context() {
		return parent_context;
	}

	public void setParent_context(String parent_context) {
		this.parent_context = parent_context;
	}

	public String getMeeting_name() {
		return meeting_name;
	}

	public void setMeeting_name(String meeting_name) {
		this.meeting_name = meeting_name;
	}

	public String getMeeting_date() {
		return meeting_date;
	}

	public void setMeeting_date(String meeting_date) {
		this.meeting_date = meeting_date;
	}

	public String getMeeting_address() {
		return meeting_address;
	}

	public void setMeeting_address(String meeting_address) {
		this.meeting_address = meeting_address;
	}

	public String getMeeting_org() {
		return meeting_org;
	}

	public void setMeeting_org(String meeting_org) {
		this.meeting_org = meeting_org;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAuthor_cn() {
		return author_cn;
	}

	public void setAuthor_cn(String author_cn) {
		this.author_cn = author_cn;
	}
	
	public String getAbs() {
		return abs;
	}
	public void setAbs(String abs) {
		this.abs = abs;
	}
}
