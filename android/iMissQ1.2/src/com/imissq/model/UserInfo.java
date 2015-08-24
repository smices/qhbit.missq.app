package com.imissq.model;

public class UserInfo extends BaseBean{

	private int code;
	private Msg msg;
	
	
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Msg getMsg() {
		return msg;
	}

	public void setMsg(Msg msg) {
		this.msg = msg;
	}

	public static class Msg{
		private Base base;

		public Base getBase() {
			return base;
		}

		public void setBase(Base base) {
			this.base = base;
		} 
		
		
	}
	
	public static class Base{
		private int id;
		private String username;
		private String mobile;
		private String nickname;
		private String type;
		private String token;
		private String openid;
		private int gender;
		private String address;
		private String ctime;
		private int valid;
		private int status;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getNickname() {
			return nickname;
		}
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public String getOpenid() {
			return openid;
		}
		public void setOpenid(String openid) {
			this.openid = openid;
		}
		public int getGender() {
			return gender;
		}
		public void setGender(int gender) {
			this.gender = gender;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getCtime() {
			return ctime;
		}
		public void setCtime(String ctime) {
			this.ctime = ctime;
		}
		public int getValid() {
			return valid;
		}
		public void setValid(int valid) {
			this.valid = valid;
		}
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		
		
		
	}
}
