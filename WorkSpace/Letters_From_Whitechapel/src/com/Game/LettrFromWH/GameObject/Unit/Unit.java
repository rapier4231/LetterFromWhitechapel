package com.Game.LettrFromWH.GameObject.Unit;

import java.util.ArrayList;

import com.Game.LettrFromWH.Component.Ability.Ability;
import com.Game.LettrFromWH.Component.Transform.Transform;
import com.Game.LettrFromWH.Game.GameMng;
import com.Game.LettrFromWH.GameObject.GameObject;
import com.Game.LettrFromWH.GameObject.GameField.GameField;
import com.Game.LettrFromWH.Scene.SceneMng;

public abstract class Unit extends GameObject {

	public enum UnitType{
		Jack,
		Police1,
		Police2,
		UnitType_End
	}

	protected UnitType unitType = UnitType.UnitType_End;

	protected final GameField gameField = (GameField)SceneMng.getInstace().getCurrentScene().getScene().getGameObject("GameField");
	
	@Override
	public void init() {
		super.init();
		settingMyUnitType();
		settingOpponentsNode();
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

	protected abstract void settingMyUnitType();

	public boolean getIsJack(){
		if(unitType == UnitType.Jack){
			return true;
		}
		return false;
	}

	public abstract int getMoveCount();

	public boolean setMove(String userInput){
		
		userInput = userInput.toUpperCase();
		
		if(userInput.equals("NOMOVE")) {
			GameMng.getInstace().setMoveList(new ArrayList<String>());
			return true;
		}
		
		String[] moveInputArray = userInput.split(",");
		
		return ((Transform)getComponent("Transform")).setMoveInput(moveInputArray);
	}

	public boolean setAbility(String userInput) {
		return ((Ability)getComponent("Ability")).setAbilityInput(userInput);
	}

	public boolean canMove(int startPosX, int startPosY, int finishPosX, int finishPosY) {
		return gameField.canMove(startPosX, startPosY, finishPosX, finishPosY);
	}
	
	public int getIntPosX() {
		return ((Transform)getComponent("Transform")).getIntPosX();
	}
	
	public int getIntPosY() {
		return ((Transform)getComponent("Transform")).getIntPosY();
	}
	
	public String getStringPos() {
		return ((Transform)getComponent("Transform")).getStringPos();
	}

	public String getNodeName(int posX, int posY) {
		return gameField.getNodeName(posX,posY);
	}

	public String nowNodeName() {
		return gameField.getNodeName(getIntPosX(),getIntPosY());
	}
	
	protected abstract void settingOpponentsNode();

	public abstract void moveMyUnit(int beginPosX, int beginPosY, int endPosX, int endPosY);
}
