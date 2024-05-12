package com.Game.LettrFromWH.GameObject.Unit;

import com.Game.LettrFromWH.Component.Ability.JackAbility;
import com.Game.LettrFromWH.Component.Transform.JackTransform;
import com.Game.LettrFromWH.Component.Transform.Transform;

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
}
