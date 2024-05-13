package com.Game.LettrFromWH.User;

import com.Game.LettrFromWH.DB.DBMng;

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

	private String myNickName = "";

	private String opponentsNickName;

	public void settingOpponentsName(){
		opponentsNickName = DBMng.getInstace().getOpponentsUserNickname();
	}

	public String getMyNickName() {
		if(myNickName.isEmpty()){
			myNickName = DBMng.getInstace().getUserNickname();
			if(myNickName == null){
				myNickName = "Test";
			}
		}
		return myNickName;
	}

	public String getOpponentsNickName(){
		return opponentsNickName;
	}
	public void resetGameInfo() {
		opponentsNickName = "";
	}
}
