package bean;

import java.util.Date;

public class QueryMessageBean {
	/**
	 * id
	 * */
	private int iId;

	/**
	 * 发送id
	 * */
	private int iSendId;

	/**
	 * 接受id
	 * */
	private int iReceiveId;
	
	/**
	 * 查询或发送内容
	 * */
	private String strContent;
	
	/**
	 * 发送者
	 * */
	private String strSender;
	
	/**
	 * 发送时间
	 * */
	private Date dateSendTime;
	
	/**
	 * 空构造
	 * */
	public QueryMessageBean(){
		
	}
	
	/**
	 * 有餐构造
	 * */
	public QueryMessageBean(int sendId, int receiverId, String content, Date sendTime){
		this.iSendId = sendId;
		this.iReceiveId = receiverId;
		this.strContent = content;
		this.dateSendTime = sendTime;
	}
	
	/**********Setters&Getters************/
	
	public int getiId() {
		return iId;
	}

	public void setiId(int iId) {
		this.iId = iId;
	}

	public int getiSendId() {
		return iSendId;
	}

	public void setiSendId(int iSendId) {
		this.iSendId = iSendId;
	}

	public int getiReceiveId() {
		return iReceiveId;
	}

	public void setiReceiveId(int iReceiveId) {
		this.iReceiveId = iReceiveId;
	}

	public String getStrContent() {
		return strContent;
	}

	public void setStrContent(String strContent) {
		this.strContent = strContent;
	}

	public String getStrSender() {
		return strSender;
	}

	public void setStrSender(String strSender) {
		this.strSender = strSender;
	}

	public Date getDateSendTime() {
		return dateSendTime;
	}

	public void setDateSendTime(Date dateSendTime) {
		this.dateSendTime = dateSendTime;
	}

}
