package com.Game.LettrFromWH.Driver;

import com.Game.LettrFromWH.Game.GameApp;

public class GameDriver {

	public static void main(String[] args) {

		GameApp gameApp = new GameApp();

		gameApp.gameStart();

		gameApp.gamePlay();

		gameApp.gameEnd();
	}

}