package com.Game.LettrFromWH.GameObject.GamePlay;

import com.Game.LettrFromWH.GameObject.GameObject;
import com.Game.LettrFromWH.Input.InputMng;

public class GamePlay extends GameObject {

	public enum GameState{
		opponentsTurn,
		updateField,
		Move,
		UseAbilities,
		SendData,
		GameState_End
	}
	
	private GameState gameState = GameState.GameState_End;
	private GamePlayThread gamePlayThread;

	@Override
	public void init() {
		super.init();
		waitMyTrun();
	}
	
	public void myTrun() {
		changeGameState(GameState.updateField);
	}

	public void userInput() {
		InputMng.getInstace().Input();
	}
	
	public void changeGameState(GameState gameState) {
		if(this.gameState == gameState) {
			return;
		}
		
		switch(gameState) {
			case opponentsTurn:
				opponentsTurn();
				break;
			case updateField:
				updateField();
				break;
			case Move:
				move();
				break;
			case UseAbilities:
				useAbility();
				break;
			case SendData:
				sendData();
				break;
			case GameState_End:
				break;
			default:
				break;
		}
	}
	
	private void opponentsTurn() {
		
	}
	
	private void updateField() {
		
	}
	
	private void move() {
		
	}
	
	private void useAbility() {
		
	}

	private void sendData() {
		
		waitMyTrun();
	}
	
	private void waitMyTrun() {
		gamePlayThread = new GamePlayThread(this);
		gamePlayThread.start();
	}

}
