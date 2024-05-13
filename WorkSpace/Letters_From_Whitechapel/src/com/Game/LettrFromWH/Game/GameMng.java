package com.Game.LettrFromWH.Game;

import java.util.ArrayList;

import com.Game.LettrFromWH.Component.Transform.Transform;
import com.Game.LettrFromWH.DB.DBMng;
import com.Game.LettrFromWH.GameObject.GameField.GameField;
import com.Game.LettrFromWH.Scene.SceneMng;

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

    private String opponentsNode;
    
	private String actionTalk = "";

	private ArrayList<String> moveList;

	private int myRoll = 0;
	private int totalPlayers = 0;
	
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

	public void setActionTalk(String actionTalk) {
		this.actionTalk = actionTalk;
	}
	
	public void setOpponentsNode(String opponentsNode) {
		this.opponentsNode = opponentsNode;
	}
	
	public void updateOpponentsNodeAndActionTalk(){
		DBMng.getInstace().updateOpponentsNodeAndActionTalk();
	}

	public void setOpponentsNodeAndActionTalk(String nowOpponentsNode , String actionTalk){
		
		this.actionTalk = actionTalk;

		if(nowOpponentsNode == null) {
			return;
		}
		
		if(!this.opponentsNode.equals(nowOpponentsNode)) {
			((GameField)(SceneMng.getInstace().getCurrentScene().getScene().
					getGameObject("GameField"))).moveOpponents(this.opponentsNode , nowOpponentsNode);
		}
		
		this.opponentsNode = nowOpponentsNode;
	}

	public void setMoveList(ArrayList<String> moveList) {
		this.moveList = moveList;
	}

	public ArrayList<String> getMoveList() {
		return moveList;
	}
	
	public void setMyRoll(int myRoll) {
		this.myRoll = myRoll;
	}
	
	public int getMyRoll() {return myRoll;}
	
	public void settingTotalPlayers() {
		this.totalPlayers = DBMng.getInstace().getTotalPlayers();
	}
	
	public int getTotalPlayers() {return totalPlayers;}

	public void addNowTurnNumber() {
		++ nowTurnNumber;
	}

	public boolean endTurn() {
		return nowTurnNumber > lastTurnNumber ? true : false;
	}

	public void resetGameInfo() {
		nowTurnNumber = 1;
	    lastTurnNumber = 0;
	    turnLimitTimeSeconds = 0;
	    opponentsNode = "";
		actionTalk = "";
		moveList.clear();;
		myRoll = 0;
		totalPlayers = 0;
	}
}
