package cn.choleece.bing.ums.entity;

import java.util.Date;

/**
 * 在线用户
 * @author choleece
 *
 */
public class SysOnlineUser {

	private String id;

	private String userName;

	private String jobNum;
	private String ip;

	private Date lastDate;

	private Date startDate;

	/**
	 * 以分钟为单位
	 */
	private Long timeOut;

	private String sessionId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getJobNum() {
		return jobNum;
	}

	public void setJobNum(String jobNum) {
		this.jobNum = jobNum;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Long getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Long timeOut) {
		this.timeOut = timeOut;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public String toString() {
		return "SysOnlineUser{" +
				"id='" + id + '\'' +
				", userName='" + userName + '\'' +
				", jobNum='" + jobNum + '\'' +
				", ip='" + ip + '\'' +
				", lastDate=" + lastDate +
				", startDate=" + startDate +
				", timeOut=" + timeOut +
				", sessionId='" + sessionId + '\'' +
				'}';
	}
}
