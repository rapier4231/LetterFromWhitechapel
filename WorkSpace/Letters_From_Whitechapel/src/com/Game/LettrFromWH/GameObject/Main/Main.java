package com.Game.LettrFromWH.GameObject.Main;

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
	
	public void mainMenu() {
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
	
	public int getSelectMainMenu(boolean wrongInput) {
		if(wrongInput) {
			PrintMng.getInstace().cpl(TextStore.systemTalk + TextStore.wrongInput);
			PrintMng.getInstace().cp(TextStore.mainMenu);
		}
		else {
			PrintMng.getInstace().cpl(TextStore.mainMenu);			
		}
		PrintMng.getInstace().p(TextStore.userInputTalk);
		return inputSelectMainMenu();
	}
	
	public int inputSelectMainMenu() {
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
