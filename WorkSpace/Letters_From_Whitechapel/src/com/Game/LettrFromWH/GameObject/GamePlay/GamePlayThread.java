package com.Game.LettrFromWH.GameObject.GamePlay;

import com.Game.LettrFromWH.DB.DBMng;
import com.Game.LettrFromWH.MyThread.MyThread;

public class GamePlayThread extends MyThread {
	
	private final GamePlay gamePlay;
	
	public GamePlayThread (GamePlay gamePlay) {
		this.gamePlay = gamePlay;
	}

	@Override
	public void run() {
		super.run();
		delayS(5f);
		while(!DBMng.getInstace().getMyTurn()) {
			delayS(0.5f);
		}
		gamePlay.myTrun();
	}
}
