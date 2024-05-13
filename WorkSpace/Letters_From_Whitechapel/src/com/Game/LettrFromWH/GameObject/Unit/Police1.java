package com.Game.LettrFromWH.GameObject.Unit;

import com.Game.LettrFromWH.Component.Ability.Police1Ability;
import com.Game.LettrFromWH.Component.Transform.PoliceTransform;
import com.Game.LettrFromWH.Component.Transform.Transform;

public class Police1 extends Unit {

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
		setComponent("Transform", new PoliceTransform());
		setComponent("Ability", new Police1Ability());
	}

	@Override
	protected void settingMyUnitType() {
		unitType = UnitType.Police1;
	}

	@Override
	public int getMoveCount() {
		return ((Transform)getComponent("Transform")).getMoveCount();
	}
	///////////////////////////
	@Override
	protected void settingOpponentsNode() {
		gameField.setOpponentsNode(false);
	}

	@Override
	public void moveMyUnit(int beginPosX, int beginPosY, int endPosX, int endPosY) {
		gameField.moveMyUnit(beginPosX,beginPosY,endPosX,endPosY,false);
	}
}
