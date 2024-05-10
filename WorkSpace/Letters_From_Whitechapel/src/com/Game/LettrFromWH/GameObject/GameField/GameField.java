package com.Game.LettrFromWH.GameObject.GameField;

import java.sql.ResultSet;

import com.Game.LettrFromWH.DB.DBMng;
import com.Game.LettrFromWH.GameObject.GameObject;

public class GameField extends GameObject {

	@Override
	public void init() {
		super.init();
		createField();
	}

	@Override
	public void play() {
		super.play();
	}

	@Override
	public void exit() {
		super.exit();
	}
	
	///////////////////////////
	
    private String[][] fieldArray;
    
    public String[][] getFieldArray(){
    	return fieldArray;
    }
    
    public void createField() {
    	//DBMng.getInstace().settingField(this);
    	
    	//임시
    	fieldArray = new String[7][7];
    	for(int i = 0; i < 7; ++i) {
    		for(int j = 0; j < 7; ++j) {
    			fieldArray[i][j] = "ㅡ";
    		}
    	}

    	for(int i = 0; i < 7; i += 2) {
    		for(int j = 0; j < 7; j += 2) {
    			fieldArray[i][j] = "◯";
    		}
    	}
    	
    }
    
	public void setField(ResultSet field) {

	}

	public String getLastPosX() {
		return null;
	}
	public String getLastPosY() {
		return null;
	}

}
