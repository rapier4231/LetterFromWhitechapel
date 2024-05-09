package com.Game.LettrFromWH.GameObject.Matching;

import java.util.ArrayList;

import com.Game.LettrFromWH.Component.Match.Match;
import com.Game.LettrFromWH.GameObject.GameObject;
import com.Game.LettrFromWH.Printer.PrintMng;
import com.Game.LettrFromWH.Scene.SceneMng;
import com.Game.LettrFromWH.Scene.SceneMng.SceneType;
import com.Game.LettrFromWH.Time.TimeMng;

public class Matching extends GameObject {
	
	@Override
	public void init() {
		super.init();
	}

	@Override
	public void play() {
		super.play();
	}

	@Override
	public void exit() {
		super.exit();
	}

	/////////////////////////////////////////////////////

	public void inputComponent() {
		super.inputComponent();
		setComponent("Match", new Match());
	}

	public void mathingCancel() {
		SceneMng.getInstace().changeScene(SceneType.MainScene);
	}
	
	public void mathingSuccess() {
		matchingSuccessView();
		SceneMng.getInstace().changeScene(SceneMng.SceneType.GameScene);
	}
	
	private void matchingSuccessView() {
		ArrayList<String> matchingSuccessPrint = new ArrayList<String>();
		
		matchingSuccessPrint.add("  ######  ####     ####    ##  ##   ######  ");
		matchingSuccessPrint.add("  ##       ##     ##  ##   ##  ##     ##    ");
		matchingSuccessPrint.add("  ##       ##     ##       ##  ##     ##    ");
		matchingSuccessPrint.add("  #####    ##     ## ###   ######     ##    ");
		matchingSuccessPrint.add("  ##       ##     ##  ##   ##  ##     ##    ");
		matchingSuccessPrint.add("  ##       ##     ##  ##   ##  ##     ##    ");
		matchingSuccessPrint.add("  ##      ####     ####    ##  ##     ##    ");
		
		PrintMng.getInstace().diagonalAppearPrint(matchingSuccessPrint);
		
		TimeMng.getInstace().delayS(1);
	}
}
