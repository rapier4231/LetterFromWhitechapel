package com.Game.LettrFromWH.GameObject.Matching;

import com.Game.LettrFromWH.Component.CreateMap.CreateMap;
import com.Game.LettrFromWH.Component.Match.Match;
import com.Game.LettrFromWH.GameObject.GameObject;

public class Matching extends GameObject {
	
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

	/////////////////////////////////////////////////////

	public void inputComponent() {
		super.inputComponent();
		setGameObject("Match", new Match());
		setGameObject("CreateMap", new CreateMap());
	}

}
