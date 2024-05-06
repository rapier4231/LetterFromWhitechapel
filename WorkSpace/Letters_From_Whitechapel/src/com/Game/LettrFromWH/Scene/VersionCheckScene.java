package com.Game.LettrFromWH.Scene;

import com.Game.LettrFromWH.GameObject.Version.VersionCheck;

public class VersionCheckScene extends Scene{

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
	public void inputGameObject() {
		setGameObject("VersionCheck", new VersionCheck());
	}
	
	////////////////////////////////////////////////////////////////////////////
	
}
