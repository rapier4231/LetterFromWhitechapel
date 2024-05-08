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
	
    private String FieldArray[][];
    
    public void createField() {
    	DBMng.getInstace().settingField(this);
    }
    
	public void setField(ResultSet field) {

	}

	public String getLastPosX() {
		return null;
	}
	public String getLastPosY() {
		return null;
	}

	public void printField() {
		
	}
}
