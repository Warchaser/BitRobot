package bean;

/**
 *问题引导表 
 * 
 * */
public class QuestionBean {

	/**
	 * 第一次关键字
	 * */
	private String first_key;
	
	/**
	 * 细分后的关键字
	 * */
	private String second_key;
	
	/**
	 * 表名
	 * */
	private String table_name;
	
	/**
	 * 索引文件夹
	 * */
	private String index_file;
	
	/**
	 * 索引字段
	 * */
	private String field;
	
	/**
	 * bean名
	 * */
	private String bean;
	
	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getIndex_file() {
		return index_file;
	}

	public void setIndex_file(String index_file) {
		this.index_file = index_file;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getBean() {
		return bean;
	}

	public void setBean(String bean) {
		this.bean = bean;
	}
	public QuestionBean(){
		
	}
	public String getFirst_key() {
		return first_key;
	}

	public void setFirst_key(String first_key) {
		this.first_key = first_key;
	}

	public String getSecond_key() {
		return second_key;
	}

	public void setSecond_key(String second_key) {
		this.second_key = second_key;
	}
}
