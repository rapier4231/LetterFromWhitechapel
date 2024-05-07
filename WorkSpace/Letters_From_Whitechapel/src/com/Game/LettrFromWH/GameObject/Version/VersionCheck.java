package com.Game.LettrFromWH.GameObject.Version;

import com.Game.LettrFromWH.DB.DBMng;
import com.Game.LettrFromWH.Game.GameMng;
import com.Game.LettrFromWH.GameObject.GameObject;
import com.Game.LettrFromWH.Scene.SceneMng;
import com.Game.LettrFromWH.Text.TextStore;

public class VersionCheck extends GameObject {

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void play() {
		if(!DBMng.getInstace().checkClientVersion()) {
			System.out.println(TextStore.versionCheckFalied);
			GameMng.getInstace().endGame();
		}
		SceneMng.getInstace().getCurrentScene().getScene().nextScene(SceneMng.SceneType.LoginScene);
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}

}
