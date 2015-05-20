package bean;

/**
 * 专家基本信息Bean,节点
 * */

public class ExpertInfoBean {
	
	/**
	 * 专家id
	 * */
	private int expertId;
	
	/**
	 * 专家姓名
	 * */
	private String expertName;
	
	/**
	 * 专家生卒年
	 * */
	private String birth_death;
	
	/**
	 * 职务
	 * */
	private String job_position;
	
	/**
	 * 参与过的项目
	 * */
	private String project;
	
	/**
	 * 获奖数量
	 * */
	private int count_reward;
	
	/**
	 * 论文数量
	 * */
	private int count_paper;
	
	/**
	 * 专利数量
	 * */
	private int count_patent;
	
	/**
	 * 从业方向
	 * */
	private String employment_direction;
	
	/**
	 * 性别
	 * */
	private String gender;
	
	/**
	 * 现任所在机构
	 * */
	private String org;
	
	
	private String pic_name;
	


	public String getPic_name() {
		return pic_name;
	}

	public void setPic_name(String pic_name) {
		this.pic_name = pic_name;
	}

	public ExpertInfoBean(){
		
	}
	
	public int getExpertId() {
		return expertId;
	}

	public void setExpertId(int expertId) {
		this.expertId = expertId;
	}

	public String getExpertName() {
		return expertName;
	}

	public void setExpertName(String expertName) {
		this.expertName = expertName;
	}

	public String getBirth_death() {
		return birth_death;
	}

	public void setBirth_death(String birth_death) {
		this.birth_death = birth_death;
	}

	public String getJob_position() {
		return job_position;
	}

	public void setJob_position(String job_position) {
		this.job_position = job_position;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public int getCount_reward() {
		return count_reward;
	}

	public void setCount_reward(int count_reward) {
		this.count_reward = count_reward;
	}

	public int getCount_paper() {
		return count_paper;
	}

	public void setCount_paper(int count_paper) {
		this.count_paper = count_paper;
	}

	public int getCount_patent() {
		return count_patent;
	}

	public void setCount_patent(int count_patent) {
		this.count_patent = count_patent;
	}

	public String getEmployment_direction() {
		return employment_direction;
	}

	public void setEmployment_direction(String employment_direction) {
		this.employment_direction = employment_direction;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

}
