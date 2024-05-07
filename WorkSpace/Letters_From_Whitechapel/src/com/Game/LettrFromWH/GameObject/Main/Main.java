package com.Game.LettrFromWH.GameObject.Main;

import com.Game.LettrFromWH.DB.DBMng;
import com.Game.LettrFromWH.GameObject.GameObject;
import com.Game.LettrFromWH.Input.InputMng;
import com.Game.LettrFromWH.Printer.PrintMng;
import com.Game.LettrFromWH.Scene.SceneMng;
import com.Game.LettrFromWH.Scene.SceneMng.SceneType;
import com.Game.LettrFromWH.Text.TextStore;

public class Main extends GameObject{

	@Override
	public void init() {

	}

	@Override
	public void play() {
		mainMenu();
	}

	@Override
	public void exit() {
	
	}

	//////////////////////////////////////////

	private void mainMenu() {
		switch(getSelectMainMenu(false)) {
			case 1:
				SceneMng.getInstace().getCurrentScene().getScene().nextScene(SceneType.MatchingScene);
				break;
			case 2:
				SceneMng.getInstace().getCurrentScene().getScene().nextScene(SceneType.LoginScene);
				break;
			case 3:
				SceneMng.getInstace().getCurrentScene().getScene().nextScene(SceneType.ExitScene);
				break;
			default:
				break;
		}
	}

	private int getSelectMainMenu(boolean wrongInput) {
		PrintMng.getInstace().cpl(TextStore.accessNickName + DBMng.getInstace().getUserNickname());
		PrintMng.getInstace().pl(TextStore.dividingLine);
		if(wrongInput) {
			PrintMng.getInstace().pl(TextStore.systemTalk + TextStore.wrongInput);
			PrintMng.getInstace().cp(TextStore.mainMenu);
		}
		else {
			PrintMng.getInstace().pl(TextStore.mainMenu);			
		}
		PrintMng.getInstace().p(TextStore.userInputTalk);
		return inputSelectMainMenu();
	}

	private int inputSelectMainMenu() {
		String userInput = InputMng.getInstace().Input();
		
		if(userInput.equals("1")) {
			return 1;
		}
		else if(userInput.equals("2")) {
			return 2;
		}
		else if(userInput.equals("3")) {
			return 3;
		}
		else {
			return getSelectMainMenu(true);
		}
	}
}
