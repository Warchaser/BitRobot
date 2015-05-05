package bean;


/**
 * 关系bean
 * */
public class RelationshipBean {
	
	
	/**
	 * 专家id
	 * */
	private int expertId;

	/**
	 * 专家姓名
	 * */
	private String expertName;
	
	/**
	 * 与专家有关系的人的姓名
	 * */
	private String name;
	
	/**
	 * 关系类型
	 * */
	private String relationshipType;
	
	/**
	 * 关系备注
	 * */
	private String r_beizhu;
	
	public RelationshipBean(){
		
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRelationshipType() {
		return relationshipType;
	}

	public void setRelationshipType(String relationshipType) {
		this.relationshipType = relationshipType;
	}

	public String getR_beizhu() {
		return r_beizhu;
	}

	public void setR_beizhu(String r_beizhu) {
		this.r_beizhu = r_beizhu;
	}

}
