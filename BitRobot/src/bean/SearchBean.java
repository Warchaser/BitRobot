package bean;

public class SearchBean {
	private String title;

	private String abs;
	
	private String url;
	
	private String date;
	
	private String expert_nmae;
	
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
