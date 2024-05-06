package com.Game.LettrFromWH.User;

public class UserMng {
	
	//Singleton
	/////////////////////////////////////////////////////
	static UserMng instance;

	private UserMng() {}
	
	static {instance = new UserMng();}
	
	public static UserMng getInstace() {return instance;}
	/////////////////////////////////////////////////////
	
	private int userId = Integer.MAX_VALUE;
	
	public int getUserId() {return userId;}
	public void setUserId(int userId) {this.userId = userId;}
}
