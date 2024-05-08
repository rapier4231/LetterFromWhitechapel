package com.Game.LettrFromWH.GameObject.Unit;

import com.Game.LettrFromWH.Component.Ability.JackAbility;

public class Jack extends Unit {

	public Jack(UnitType unitType) {
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
		setComponent("Ability", new JackAbility());
	}
	///////////////////////////
	
}
