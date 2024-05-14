package com.Game.LettrFromWH.GameObject.GamePlay;

import com.Game.LettrFromWH.DB.DBMng;
import com.Game.LettrFromWH.MyThread.MyThread;

public class GamePlayWaitThread extends MyThread {

    public GamePlayWaitThread(GamePlay gamePlay){
        this.gamePlay = gamePlay;
    }

    private final GamePlay gamePlay;

    private boolean endGame = false;
    
    @Override
    public void run() {
        super.run();
        while(gamePlay.getRunThread()){
            if(checkMyTurn()){
                gamePlay.startMyTurn(endGame);
            }
            delayS(0.3f);
        }
    }

    private boolean checkMyTurn() {
    	if(DBMng.getInstace().endGame()) {
    		endGame = true;
    		return true;
    	}
    	
        return DBMng.getInstace().getMyTurn();
    }
}
