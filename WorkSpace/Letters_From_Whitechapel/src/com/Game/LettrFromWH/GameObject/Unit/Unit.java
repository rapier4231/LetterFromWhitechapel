package com.Game.LettrFromWH.GameObject.Unit;

import com.Game.LettrFromWH.Component.Transform.Transform;
import com.Game.LettrFromWH.GameObject.GameObject;

public class Unit extends GameObject {

	public Unit(UnitType unitType) {
		this.unitType = unitType;
	}
	
	////////////////////////////
	
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
	
	@Override
	public void inputComponent() {
		super.inputComponent();
		setComponent("Transform", new Transform(unitType));
	}
	
	///////////////////////////

	public enum UnitType{
		Jack,
		Police1,
		Police2,
		UnitType_End
	}
	
	private final UnitType unitType;
	
}
