package com.Game.LettrFromWH.Scene;

import com.Game.LettrFromWH.Game.GameMng;
import com.Game.LettrFromWH.Printer.PrintMng;
import com.Game.LettrFromWH.Text.TextStore;

public class ExitScene extends Scene {

	@Override
	public void init() {
		super.init();
	}

	@Override
	public void play() {
		super.play();
		PrintMng.getInstace().cpl(TextStore.Bye);
		GameMng.getInstace().endGame();
	}

	@Override
	public void exit() {
		super.exit();
	}

	
	@Override
	public void inputGameObject() {

	}

}
