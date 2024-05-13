package com.Game.LettrFromWH.GameObject.GameRenderer;

import java.util.ArrayList;

import com.Game.LettrFromWH.Game.GameMng;
import com.Game.LettrFromWH.GameObject.GameField.FieldPiece;
import com.Game.LettrFromWH.GameObject.GameField.Node;
import com.Game.LettrFromWH.GameObject.GameObject;
import com.Game.LettrFromWH.GameObject.GameField.GameField;
import com.Game.LettrFromWH.Input.InputMng;
import com.Game.LettrFromWH.Printer.PrintMng;
import com.Game.LettrFromWH.Scene.Scene;
import com.Game.LettrFromWH.Scene.SceneMng;
import com.Game.LettrFromWH.Text.TextStore;
import com.Game.LettrFromWH.Time.TimeMng;
import com.Game.LettrFromWH.GameObject.GamePlay.GamePlay;
import com.Game.LettrFromWH.User.UserMng;

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

	public void firstFieldView(){
		PrintMng.getInstace().topAppearPrint(settingPrintArray(true));
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
		ArrayList<String> gamePrintList = settingPrintArray(false);

		PrintMng.getInstace().cmdClear();
        for (String s : gamePrintList) {
            PrintMng.getInstace().pl(s);
        }

		getGamePlay().printInput();
	}

	private ArrayList<String> settingPrintArray(boolean firstView){
		ArrayList<String> gamePrintList = new ArrayList<String>();

		convertAndAddGameField(getGameField().getFieldPiece2dArrayList(), gamePrintList);

		gamePrintList.add(TextStore.GameDividingLine);

		addPrintListTurn(gamePrintList);

		if(!firstView) {
			addPrintListOpponentAction(gamePrintList);
		}

		addPrintListSystemMsg(gamePrintList);

		return gamePrintList;
	}

	private void convertAndAddGameField(ArrayList<ArrayList<FieldPiece>> fieldPiece2dArrayList, ArrayList<String> gamePrintList) {
		StringBuilder rowStr;
		int sqrt = fieldPiece2dArrayList.size();

		rowStr = new StringBuilder();
		for(int i = sqrt - 1; i > -1; --i){
			rowStr.setLength(0);
			if(i % 2 == 0) {
				rowStr.append((int)(i * 0.51f) + 1);
			}
			else {
				rowStr.append(" ");
			}
			rowStr.append("\t");
			for (int j = 0; j < sqrt; ++j){
				rowStr.append(fieldPiece2dArrayList.get(i).get(j).toString());
				rowStr.append("\t");
			}
			gamePrintList.add(rowStr.toString());
		}

		//Last row
		rowStr.setLength(0);
		rowStr.append(" \t");
		char a = 'a';
		for(int i = 0; i <= sqrt; i += 2) {
			rowStr.append((char)((int)a + (i / 2)));
			rowStr.append("\t \t");
		}
		gamePrintList.add(rowStr.toString());

	}

	private void addPrintListTurn(ArrayList<String> gamePrintList) {
		gamePrintList.add(TextStore.trunTalk + 
				GameMng.getInstace().getTurn() + " / " +
				GameMng.getInstace().getLastTurn() +
				" [" + turnOwnerName() + "]");
	}
	
	private String turnOwnerName() {
		if(getGamePlay().getMyTurn()){
			return UserMng.getInstace().getMyNickName();
		}

		return UserMng.getInstace().getOpponentsNickName();
	}
	
	private void addPrintListOpponentAction(ArrayList<String> gamePrintList) {
		String actionTalk = GameMng.getInstace().getActionTalk();
		if(actionTalk.isEmpty()) {
			return;
		}
		gamePrintList.add(TextStore.actionTalk + actionTalk);
	}
	
	private void addPrintListSystemMsg(ArrayList<String> gamePrintList) {
		getGamePlay().addSystemTalk(gamePrintList);
	}
}
