package com.Game.LettrFromWH.Game;

import com.Game.LettrFromWH.Component.Transform.Transform;

public class GameMng {

    //Singleton
    /////////////////////////////////////////////////////

    static GameMng instance;

    private GameMng() {}

    static {instance = new GameMng();}

    public static GameMng getInstace() {return instance;}

    /////////////////////////////////////////////////////

    private boolean gameProgress = false;

    private int nowTurnNumber = 1;
    private int lastTurnNumber;
    private int turnLimitTimeSeconds;

    public boolean getGameProgress(){return gameProgress;}
    
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

	public int getLastTurn() {
		return lastTurnNumber;
	}

	public int getTurnLimitTimeSeconds() {
		return turnLimitTimeSeconds;
	}
	
	public String getAction() {

		return null;
	}
}
