package bean;


	/**
	 *专家动态表 
	 * 
	 * */
public class UpdatesBean {

	/**
	 * 专家id
	 * */
	private String expert_id;
	
	/**
	 * 动态日期
	 * */
	private String date;
	
	/**
	 * 动态摘要
	 * */
	private String abs;
	
	public UpdatesBean(){
		
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

	public String getAbs() {
		return abs;
	}

	public void setAbs(String abs) {
		this.abs = abs;
	}
}
