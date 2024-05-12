package com.Game.LettrFromWH.Game;

import com.Game.LettrFromWH.Component.Transform.Transform;
import com.Game.LettrFromWH.DB.DBMng;

public class GameMng {

    //Singleton
    /////////////////////////////////////////////////////

    static GameMng instance;

    private GameMng() {}

    static {instance = new GameMng();}

    public static GameMng getInstace() {return instance;}

    /////////////////////////////////////////////////////

    private boolean gameProgress = false;
	private final float fps = 20;
	private final long delayTime = (long)(1.f/fps*1000.f);

    private int nowTurnNumber = 1;
    private int lastTurnNumber;
    private int turnLimitTimeSeconds;

	private String actionTalk = "";

    public boolean getGameProgress(){return gameProgress;}
    
    public float getFps() {return fps;}
    public long getDelayTime() {return delayTime;}
    
    public void beginGame() {
    	gameProgress = true;
    }

    public void endGame(){gameProgress = false;}

	public void setTurnInfo(int lastTurnNumber, int turnLimitTimeSeconds) {
		this.lastTurnNumber = lastTurnNumber;
		this.turnLimitTimeSeconds = turnLimitTimeSeconds;
		
	}

	public int getTurn() {
		return nowTurnNumber;
	}

	public void plusNowTurn(){++nowTurnNumber;}

	public int getLastTurn() {
		return lastTurnNumber;
	}

	public int getTurnLimitTimeSeconds() {
		return turnLimitTimeSeconds;
	}
	
	public String getActionTalk() {
		return actionTalk;
	}

	public void updateTurnAndActionTalk(){
		DBMng.getInstace().updateTurnAndActionTalk();
	}

	public void setTurnAndActionTalk(int nowTurnNumber , String actionTalk){
		this.nowTurnNumber = nowTurnNumber;
		this.actionTalk = actionTalk;
	}
}
