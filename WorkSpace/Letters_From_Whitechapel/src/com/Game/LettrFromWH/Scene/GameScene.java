package com.Game.LettrFromWH.Scene;

import com.Game.LettrFromWH.GameObject.GameField.GameField;
import com.Game.LettrFromWH.GameObject.GamePlay.GamePlay;
import com.Game.LettrFromWH.GameObject.GameRenderer.GameRenderer;

public class GameScene extends Scene{

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
    
    @Override
    public void inputGameObject() {
    	setGameObject("GameField", new GameField());
    	setGameObject("GamePlay", new GamePlay());
    	setGameObject("GameRenderer", new GameRenderer());
    	
    }
}
