package com.Game.LettrFromWH.GameObject.GameRenderer;

import com.Game.LettrFromWH.Game.GameMng;
import com.Game.LettrFromWH.GameObject.GameObject;
import com.Game.LettrFromWH.GameObject.GameField.GameField;
import com.Game.LettrFromWH.Printer.PrintMng;
import com.Game.LettrFromWH.Scene.SceneMng;
import com.Game.LettrFromWH.Text.TextStore;
import com.Game.LettrFromWH.GameObject.GamePlay.GamePlay;

public class GameRenderer extends GameObject {

	private boolean viewChanging = false;
	private final GameField gameField = (GameField)(SceneMng.getInstace().getCurrentScene().getScene().getGameObject("GameField"));
	private final GamePlay gamePlay = (GamePlay)(SceneMng.getInstace().getCurrentScene().getScene().getGameObject("GamePlay"));
	
	public void changeView() {
		viewChanging = true;
	}
	
	@Override
	public void play() {
		super.play();
		if(viewChanging) {
			viewChanging = false;
			printView();
		}
	}
	
	private void printView() {
		gameField.printField();
		PrintMng.getInstace().pl(TextStore.dividingLine);
		printTurn();
		printOpponentAction();
		printSystemMsg();
		printinput();
	}

	private void printTurn() {
		PrintMng.getInstace().p(TextStore.trunTalk);
		PrintMng.getInstace().pl(GameMng.getInstace().getTurn() + " / " +
				GameMng.getInstace().getLastTurn() +
				" [" + turnOwnerName() + "]"); 
	}
	
	private String turnOwnerName() {
		return "";
	}
	
	private void printOpponentAction() {
		PrintMng.getInstace().p(TextStore.actionTalk);
		PrintMng.getInstace().pl(GameMng.getInstace().getAction());
	}
	
	private void printSystemMsg() {
		PrintMng.getInstace().p(TextStore.systemTalk);
		//서브 시스템 토크도 있음
	}
	
	private void printinput() {
		PrintMng.getInstace().p(TextStore.userInputTalk);
		gamePlay.userInput();
	}
}
