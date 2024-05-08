package com.Game.LettrFromWH.GameObject.Matching;

import com.Game.LettrFromWH.Component.Match.Match;
import com.Game.LettrFromWH.GameObject.GameObject;
import com.Game.LettrFromWH.Scene.SceneMng;

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
		setComponent("Match", new Match());
	}

	public void mathingSuccess() {
		SceneMng.getInstace().changeScene(SceneMng.SceneType.GameScene);
	}
	
}
