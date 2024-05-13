package com.Game.LettrFromWH.GameObject.GamePlay;

import com.Game.LettrFromWH.DB.DBMng;
import com.Game.LettrFromWH.Game.GameMng;
import com.Game.LettrFromWH.GameObject.GameObject;
import com.Game.LettrFromWH.GameObject.GameField.GameField;
import com.Game.LettrFromWH.GameObject.GameRenderer.GameRenderer;
import com.Game.LettrFromWH.GameObject.Unit.Jack;
import com.Game.LettrFromWH.GameObject.Unit.Unit;
import com.Game.LettrFromWH.Input.InputMng;
import com.Game.LettrFromWH.Printer.PrintMng;
import com.Game.LettrFromWH.Scene.SceneMng;
import com.Game.LettrFromWH.Scene.SceneMng.SceneType;
import com.Game.LettrFromWH.Text.TextStore;
import com.Game.LettrFromWH.Time.TimeMng;
import com.Game.LettrFromWH.User.UserMng;

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
	
	private GameField gameField;

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
		GameMng.getInstace().settingTotalPlayers();
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

	private GameField getGameField() {
		if(this.gameField == null) {
			this.gameField = (GameField)(SceneMng.getInstace().getCurrentScene().getScene().getGameObject("GameField"));
		}

		return gameField;
	}
	
	private Unit getUnit() {
		if(unit == null) {
			this.unit = (Unit)(SceneMng.getInstace().getCurrentScene().getScene().getGameObject("Unit"));
		}

		return unit;
	}

	private boolean waitMyTurn(){
		if(DBMng.getInstace().endGame()) {
			changeGameTurnState(GameTurnState.GameEnd);
			return false;
		}
		
		runThread = true;
		gamePlayWaitThread.start();
		return true;
	}

	private void checkGameTurnState() {
		if(!changeGameTurnState) {
			return;
		}

		changeGameTurnState = false;
		
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
	}

	public void printInput() {
		if(!getMyTurn()){
			return;
		}
		PrintMng.getInstace().p(TextStore.userInputTalk);
		processUserInput(InputMng.getInstace().Input());
	}

	private void processUserInput(String userInput){
		switch (gameTurnState){
			case Move -> {
				if(getUnit().setMove(userInput)) {
					changeGameTurnState(GameTurnState.UseAbilities);
				}
				else {
					move();
				}
			}
			case UseAbilities -> {
				if(getUnit().setAbility(userInput)) {
					changeGameTurnState(GameTurnState.SendData);
				}
				else {
					useAbility();
				}
			}
			case SendData -> {
			}
			case GameEnd -> {
			}
			case GameTurnState_End -> {
			}
			default -> {
			}
		}
	}

	private void opponentsTurn() {

	}

	private void updateGame() {
		GameMng.getInstace().updateOpponentsNodeAndActionTalk();
		DBMng.getInstace().setKillNode(getGameField());
		
		if(DBMng.getInstace().endGame()) {
			changeGameTurnState(GameTurnState.GameEnd);
			return;
		}
		else {
			GameMng.getInstace().addNowTurnNumber(); //내 차례가 되었으므로 1 증가
			changeGameTurnState(GameTurnState.Move);
		}
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
		if(getUnit().getIsJack()){
			if(isCanKill()) {
				gameSystemTalk = TextStore.UseJackAbility;
				gameSubSystemTalk = TextStore.JackAbility;
			}
			else {
				changeGameTurnState(GameTurnState.SendData);
				return;
			}

		}
		else{
			gameSystemTalk = TextStore.UsePoliceAbility;
			gameSubSystemTalk = TextStore.PoliceAbility;
		}

		getGameRenderer().changeView();
	}

	private boolean isCanKill() {
		return ((Jack)getUnit()).isCanKill();
	}
	
	private void sendData() {
		ArrayList<String> moveList = GameMng.getInstace().getMoveList();
		
		int size = moveList.size();
		
		if(size == 0) {
			DBMng.getInstace().sendTurnData(getUnit().nowNodeName(),GameMng.getInstace().getActionTalk(),true);
		}
		else {
			for(int i = 0; i < size - 1; ++i) {
				DBMng.getInstace().sendTurnData(moveList.get(i),"",false);
			}
			DBMng.getInstace().sendTurnData(moveList.get(size - 1),GameMng.getInstace().getActionTalk(),true);
		}
		
		finishMyTurn();
		
		getGameRenderer().changeView();
	}
	
	public void startGame(boolean isJack) {
		
//		
//		changeGameTurnState(GamePlay.GameTurnState.Move);
//		return;
//		
		if(isJack) {
			changeGameTurnState(GamePlay.GameTurnState.Move);
		}
		else {
			changeGameTurnState(GamePlay.GameTurnState.OpponentsTurn);
			gameSystemTalk = TextStore.WaitMyTurn;
			getGameRenderer().changeView();
		}
	}

	private void finishMyTurn(){
		if(waitMyTurn()) {
			gameSystemTalk = TextStore.WaitMyTurn;
			gameSubSystemTalk = "";
			GameMng.getInstace().addNowTurnNumber();
		}
	}

	private void endGame() {
		gameSystemTalk = "게임이 끝났습니다. 모두 고생하셨습니다.";
		gameSubSystemTalk = "잠시 후, 화면이 전환됩니다.";
		getGameRenderer().changeView();
		TimeMng.getInstace().delayS(2);
		
		ArrayList<String> matchingSuccessPrint = new ArrayList<String>();
		if(DBMng.getInstace().qImWinner()) {
			matchingSuccessPrint.add("'##:::::'##:'####:'##::: ##:'##::: ##:'########:'########::");
			matchingSuccessPrint.add(" ##:'##: ##:. ##:: ###:: ##: ###:: ##: ##.....:: ##.... ##:");
			matchingSuccessPrint.add(" ##: ##: ##:: ##:: ####: ##: ####: ##: ##::::::: ##:::: ##:");
			matchingSuccessPrint.add(" ##: ##: ##:: ##:: ## ## ##: ## ## ##: ######::: ########::");
			matchingSuccessPrint.add(" ##: ##: ##:: ##:: ##. ####: ##. ####: ##...:::: ##.. ##:::");
			matchingSuccessPrint.add(" ##: ##: ##:: ##:: ##:. ###: ##:. ###: ##::::::: ##::. ##::");
			matchingSuccessPrint.add(". ###. ###::'####: ##::. ##: ##::. ##: ########: ##:::. ##:");
			matchingSuccessPrint.add(":...::...:::....::..::::..::..::::..::........::..:::::..::");
		}
		else {
			matchingSuccessPrint.add("'##::::::::'#######:::'######::'########:'########::");
			matchingSuccessPrint.add(" ##:::::::'##.... ##:'##... ##: ##.....:: ##.... ##:");
			matchingSuccessPrint.add(" ##::::::: ##:::: ##: ##:::..:: ##::::::: ##:::: ##:");
			matchingSuccessPrint.add(" ##::::::: ##:::: ##:. ######:: ######::: ########::");
			matchingSuccessPrint.add(" ##::::::: ##:::: ##::..... ##: ##...:::: ##.. ##:::");
			matchingSuccessPrint.add(" ##::::::: ##:::: ##:'##::: ##: ##::::::: ##::. ##::");
			matchingSuccessPrint.add(" ########:. #######::. ######:: ########: ##:::. ##:");
			matchingSuccessPrint.add("........:::.......::::......:::........::..:::::..::");
		}
		
		PrintMng.getInstace().diagonalAppearPrint(matchingSuccessPrint);
		TimeMng.getInstace().delayS(2);
		GameMng.getInstace().resetGameInfo();
		UserMng.getInstace().resetGameInfo();
		SceneMng.getInstace().getCurrentScene().getScene().nextScene(SceneType.MainScene);
	}
}
