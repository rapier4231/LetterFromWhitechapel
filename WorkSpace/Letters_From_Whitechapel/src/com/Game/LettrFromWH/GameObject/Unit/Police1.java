package com.Game.LettrFromWH.GameObject.Unit;

import com.Game.LettrFromWH.Component.Ability.Police1Ability;

public class Police1 extends Unit {

	public Police1(UnitType unitType) {
		super(unitType);
	}
	
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
		setComponent("Ability", new Police1Ability());
	}
	
	///////////////////////////
	
}
