package com.blackwhite.adt;

public class UserADT {

	private int id;
	private String username;
	private String avatar;
	private int sex;
	
	public UserADT(int id, String username, String avatar, int sex) {
		this.id = id;
		this.username = username;
		this.avatar = avatar;
		this.sex = sex;
	}
	
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
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	
	
}
