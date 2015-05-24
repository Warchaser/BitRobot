package bean;


/**
 *专家专利表 
 * 
 * */
public class PatentBean {

	/**
	 * 专利名
	 * */
	private String patent_name;
	
	/**
	 * 专家id
	 * */
	private String expert_id;
	
	/**
	 * 专利申请日期
	 * */
	private String patent_date;
	
	/**
	 * 专利所有人
	 * */
	private String patent_owner;
	
	/**
	 * 专利号
	 * */
	private String patent_id;
	
	public PatentBean(){
		
	}
	
	
	public String getPatent_name() {
		return patent_name;
	}

	public void setPatent_name(String patent_name) {
		this.patent_name = patent_name;
	}

	public String getExpert_id() {
		return expert_id;
	}

	public void setExpert_id(String expert_id) {
		this.expert_id = expert_id;
	}

	public String getPatent_date() {
		return patent_date;
	}

	public void setPatent_date(String patent_date) {
		this.patent_date = patent_date;
	}

	public String getPatent_owner() {
		return patent_owner;
	}

	public void setPatent_owner(String patent_owner) {
		this.patent_owner = patent_owner;
	}

	public String getPatent_id() {
		return patent_id;
	}

	public void setPatent_id(String patent_id) {
		this.patent_id = patent_id;
	}
}
