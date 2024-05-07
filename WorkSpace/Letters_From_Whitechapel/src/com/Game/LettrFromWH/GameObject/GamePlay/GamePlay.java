package com.Game.LettrFromWH.GameObject.GamePlay;

import com.Game.LettrFromWH.GameObject.GameObject;

public class GamePlay extends GameObject {

	@Override
	public void init() {
		super.init();
	}

	@Override
	public void play() {
		super.play();
		gameStart();
	}

	@Override
	public void exit() {
		super.exit();
	}
	
	///////////////////////////
	
	public enum GameState{
		opponentsTurn,
		updateField,
		Move,
		UseAbilities,
		SendData,
		GameState_End
	}
	
	private GameState gameState = GameState.GameState_End;
	
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
				break;
			case SendData:
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
	

	private void gameStart(){
		
	}
}
