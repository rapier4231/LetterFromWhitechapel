package com.Game.LettrFromWH.GameObject.GamePlay;

import com.Game.LettrFromWH.Game.GameMng;
import com.Game.LettrFromWH.GameObject.GameObject;
import com.Game.LettrFromWH.GameObject.GameRenderer.GameRenderer;
import com.Game.LettrFromWH.GameObject.Unit.Unit;
import com.Game.LettrFromWH.Input.InputMng;
import com.Game.LettrFromWH.Printer.PrintMng;
import com.Game.LettrFromWH.Scene.SceneMng;
import com.Game.LettrFromWH.Text.TextStore;

import java.util.ArrayList;

public class GamePlay extends GameObject {

	private String gameSystemTalk = "";
	private String gameSubSystemTalk = "";
	public void addSystemTalk(ArrayList<String> gamePrintList) {
		if(!gameSystemTalk.isEmpty()){
			gamePrintList.add(TextStore.systemTalk + gameSystemTalk);
		}
		if(!gameSubSystemTalk.isEmpty()){
			gamePrintList.add(TextStore.systemTalk + gameSubSystemTalk);
		}
	}

	public boolean getMyTurn() {
		if(gameTurnState == GameTurnState.OpponentsTurn || gameTurnState == GameTurnState.SendData){
			return false;
		}

		return true;
	}

	public enum GameTurnState{
		OpponentsTurn,
		Update,
		Move,
		UseAbilities,
		SendData,
		GameEnd,
		GameTurnState_End
	}

	private GameTurnState gameTurnState = GameTurnState.GameTurnState_End;

	private boolean changeGameTurnState = false;

	private final GamePlayWaitThread gamePlayWaitThread = new GamePlayWaitThread(this);

	private boolean runThread = false;

	private GameRenderer gameRenderer;

	private Unit  unit;

	public boolean getRunThread() { return runThread;}

	public void changeGameTurnState(GameTurnState gameTurnState) {
		if(this.gameTurnState == gameTurnState) {
			return;
		}
		this.gameTurnState = gameTurnState;
		changeGameTurnState = true;
	}

	public void startMyTurn(){
		runThread = false;
		changeGameTurnState(GameTurnState.Update);
	}
	
	@Override
	public void init() {
		super.init();
	}

	@Override
	public void play() {
		if(!changeGameTurnState){
			return;
		}

		super.play();
		checkGameTurnState();
	}

	private GameRenderer getGameRenderer() {
		if(gameRenderer == null) {
			this.gameRenderer = (GameRenderer)(SceneMng.getInstace().getCurrentScene().getScene().getGameObject("GameRenderer"));
		}

		return gameRenderer;
	}

	private Unit getUnit() {
		if(unit == null) {
			this.unit = (Unit)(SceneMng.getInstace().getCurrentScene().getScene().getGameObject("Unit"));
		}

		return unit;
	}

	private void waitMyTurn(){
		runThread = true;
		gamePlayWaitThread.start();
	}

	private void checkGameTurnState() {
		if(!changeGameTurnState) {
			return;
		}

		switch(gameTurnState) {
			case OpponentsTurn:
				opponentsTurn();
				break;
			case Update:
				updateGame();
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
			case GameEnd:
				endGame();
				break;
			case GameTurnState_End:
				break;
			default:
				break;
		}
		changeGameTurnState = false;
	}

	public void printInput() {
		PrintMng.getInstace().p(TextStore.userInputTalk);
		processUserInput(InputMng.getInstace().Input());
	}

	private void processUserInput(String userInput){
		switch (gameTurnState){
			case Move -> {
				getUnit().
			}
			case UseAbilities -> {
			}
			case SendData -> {
			}
			case GameEnd -> {
			}
			case GameTurnState_End -> {
			}
		}
	}

	private void opponentsTurn() {

	}

	private void updateGame() {
		GameMng.getInstace().updateTurnAndActionTalk();

	}
	
	private void move() {
		gameSystemTalk = TextStore.MoveUserUnit;
		if(getUnit().getIsJack()){
			gameSubSystemTalk = TextStore.MoveJackUnit(getUnit().getMoveCount());
		}
		else{
			gameSubSystemTalk = TextStore.MovePoliceUnit(getUnit().getMoveCount());
		}

		getGameRenderer().changeView();
	}
	
	private void useAbility() {
		
	}

	private void sendData() {

		finishMyTurn();
	}

	private void finishMyTurn(){
		//if()마지막 턴이 아니면
		waitMyTurn();
	}

	private void endGame() {

	}
}
