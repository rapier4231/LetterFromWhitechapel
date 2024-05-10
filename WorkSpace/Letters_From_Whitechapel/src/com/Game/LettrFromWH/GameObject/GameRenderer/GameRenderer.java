package com.Game.LettrFromWH.GameObject.GameRenderer;

import java.util.ArrayList;

import com.Game.LettrFromWH.Game.GameMng;
import com.Game.LettrFromWH.GameObject.GameObject;
import com.Game.LettrFromWH.GameObject.GameField.GameField;
import com.Game.LettrFromWH.Printer.PrintMng;
import com.Game.LettrFromWH.Scene.Scene;
import com.Game.LettrFromWH.Scene.SceneMng;
import com.Game.LettrFromWH.Text.TextStore;
import com.Game.LettrFromWH.Time.TimeMng;
import com.Game.LettrFromWH.GameObject.GamePlay.GamePlay;

public class GameRenderer extends GameObject {
	
	private boolean viewChanging = false;
	private GameField gameField;
	private GamePlay gamePlay;
	
	public GameField getGameField() {
		if(gameField == null) {
			gameField = (GameField)SceneMng.getInstace().getCurrentScene().getScene().getGameObject("GameField");
		}
		return gameField;
	}
	
	public GamePlay getGamePlay() {
		if(gamePlay == null) {
			gamePlay = (GamePlay)SceneMng.getInstace().getCurrentScene().getScene().getGameObject("GamePlay");
		}
		return gamePlay;
	}
	
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
		ArrayList<String> gamePrintList = new ArrayList<String>();
		
		convertAndAddGameField(getGameField().getFieldArray(), gamePrintList);
		
		gamePrintList.add(TextStore.dividingLine);
		
		addPrintListTurn(gamePrintList);
		addPrintListOpponentAction(gamePrintList);
		addPrintListSystemMsg(gamePrintList);
		
		PrintMng.getInstace().diagonalAppearPrint(gamePrintList);
		
		TimeMng.getInstace().delayS(3);
		
		printinput();
	} 

	private void convertAndAddGameField(String[][] fieldArray, ArrayList<String> gamePrintList) {

		int colLength = fieldArray.length - 1;
		int rowLength = fieldArray[0].length - 1;

		String rowStr;
		
		for(int i = 0; i <= colLength; ++i) {
			rowStr = "";
			
			if(i%2 == 0) {
				rowStr += i;
				rowStr += "\t";
			}
			else {
				rowStr += " ";
				rowStr += "\t";
			}
			
			for(int j = 0; j <= rowLength; ++j) {
				rowStr += fieldArray[i][j];
				rowStr += "\t";
			}
			
			rowStr += i;
			gamePrintList.add(rowStr);
		}

		//Last row
		rowStr = " \t";
		char a = 'a';
		for(int i = 0; i <= rowLength; i += 2) {
			rowStr += (a + i);
			rowStr += "\t \t";
		}
	}

	private void addPrintListTurn(ArrayList<String> gamePrintList) {
		gamePrintList.add(TextStore.trunTalk + 
				GameMng.getInstace().getTurn() + " / " +
				GameMng.getInstace().getLastTurn() +
				" [" + turnOwnerName() + "]");
	}
	
	private String turnOwnerName() {
		return "";
	}
	
	private void addPrintListOpponentAction(ArrayList<String> gamePrintList) {
		//gamePrintList.add(TextStore.actionTalk + GameMng.getInstace().getAction());
		gamePrintList.add(TextStore.actionTalk + "상대가 뭘 했을 깜?");
	}
	
	private void addPrintListSystemMsg(ArrayList<String> gamePrintList) {
		gamePrintList.add(TextStore.systemTalk +" 시스템이 뭘 말할 까~?");
		gamePrintList.add(TextStore.systemTalk +" 서브 시스템이 뭘 말할 까~?");
	}
	
	private void printinput() {
		PrintMng.getInstace().p(TextStore.userInputTalk);
		getGamePlay().userInput();
	}
}
