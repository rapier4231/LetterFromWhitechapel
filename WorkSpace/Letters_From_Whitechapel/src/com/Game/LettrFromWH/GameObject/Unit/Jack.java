package com.Game.LettrFromWH.GameObject.Unit;

import com.Game.LettrFromWH.Component.Ability.JackAbility;
import com.Game.LettrFromWH.Component.Transform.JackTransform;
import com.Game.LettrFromWH.Component.Transform.Transform;
import com.Game.LettrFromWH.DB.DBMng;
import com.Game.LettrFromWH.Game.GameMng;

public class Jack extends Unit {
	
	@Override
	public void init() {
		super.init();
		
	}

	@Override
	public void play() {
		super.play();
	}

	@Override
	public void exit() {
		super.exit();
	}
	
	public void inputComponent() {
		super.inputComponent();
		setComponent("Transform", new JackTransform());
		setComponent("Ability", new JackAbility());
	}

	@Override
	protected void settingMyUnitType() {
		unitType = UnitType.Jack;
	}

	@Override
	public int getMoveCount() {
		return ((Transform)getComponent("Transform")).getMoveCount();
	}
	///////////////////////////

	public void setKill() {
		DBMng.getInstace().setKill(gameField.setKill(((Transform)getComponent("Transform")).getIntPosX(),((Transform)getComponent("Transform")).getIntPosY()));
	}

	public boolean isCanKill() {
		return gameField.isCanKill(getIntPosX(),getIntPosY());
	}

	@Override
	protected void settingOpponentsNode() {
		gameField.setOpponentsNode(true);
	}

	@Override
	public void moveMyUnit(int beginPosX, int beginPosY, int endPosX, int endPosY) {
		gameField.moveMyUnit(beginPosX,beginPosY,endPosX,endPosY,true);
		
	}
	
}
