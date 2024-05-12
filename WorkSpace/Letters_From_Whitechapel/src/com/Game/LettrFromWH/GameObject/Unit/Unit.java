package com.Game.LettrFromWH.GameObject.Unit;

import com.Game.LettrFromWH.Component.Transform.Transform;
import com.Game.LettrFromWH.GameObject.GameObject;

public abstract class Unit extends GameObject {

	public enum UnitType{
		Jack,
		Police1,
		Police2,
		UnitType_End
	}

	protected UnitType unitType = UnitType.UnitType_End;

	@Override
	public void init() {
		super.init();
		settingMyUnitType();
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
		String[] moveInputArray = userInput.split(",");
		return ((Transform)getComponent("Transform")).setMoveInput(moveInputArray);
	}
}
