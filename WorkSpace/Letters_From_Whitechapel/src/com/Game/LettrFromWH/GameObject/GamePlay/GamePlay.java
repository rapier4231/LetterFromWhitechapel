package com.Game.LettrFromWH.GameObject.GamePlay;

import com.Game.LettrFromWH.GameObject.GameObject;
import com.Game.LettrFromWH.GameObject.GameRenderer.GameRenderer;
import com.Game.LettrFromWH.Input.InputMng;
import com.Game.LettrFromWH.Scene.SceneMng;

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
	private GameRenderer gameRenderer;
	
	public GameRenderer getGameRenderer() {
		if(gameRenderer == null) {
			this.gameRenderer = (GameRenderer)(SceneMng.getInstace().getCurrentScene().getScene().getGameObject("GameRenderer"));
		}
		
		return gameRenderer;
	}
	
	@Override
	public void init() {
		super.init();
		//waitMyTrun();
	}

	@Override
	public void play() {
		super.play();
		//임시
		myTrun();
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

		getGameRenderer().changeView();
	}
	
	private void move() {
		
	}
	
	private void useAbility() {
		
	}

	private void sendData() {
		
		waitMyTrun();
	}
	
	private void waitMyTrun() {

	}

}
