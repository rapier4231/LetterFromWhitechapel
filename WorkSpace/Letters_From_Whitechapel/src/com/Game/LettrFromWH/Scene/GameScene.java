package com.Game.LettrFromWH.Scene;

import com.Game.LettrFromWH.DB.DBMng;
import com.Game.LettrFromWH.GameObject.GameField.GameField;
import com.Game.LettrFromWH.GameObject.GamePlay.GamePlay;
import com.Game.LettrFromWH.GameObject.GameRenderer.GameRenderer;
import com.Game.LettrFromWH.GameObject.Unit.Jack;
import com.Game.LettrFromWH.GameObject.Unit.Police1;

public class GameScene extends Scene{

    @Override
    public void init() {
        super.init();
        settingGame();
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
        checkMyRoll();
    }

    private void checkMyRoll(){
        switch (DBMng.getInstace().getMyRoll()){
            case Jack :
                setGameObject("Unit", new Jack());
                ((GamePlay)getGameObject("GamePlay")).startGame(true);
         
                break;
            case Police1 :
                setGameObject("Unit", new Police1());
                ((GamePlay)getGameObject("GamePlay")).startGame(false);
                break;
            case Police2 :
                break;
            case UnitType_End :
                break;
            default:
                break;
        }
    }

    private void settingGame(){
        settingTrun();
        ((GameRenderer)getGameObject("GameRenderer")).firstFieldView();
    }

    private void settingTrun() {
        DBMng.getInstace().settingTurnInfo();
    }

}
