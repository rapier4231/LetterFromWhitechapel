package com.Game.LettrFromWH.Game;

import com.Game.LettrFromWH.GameObject.Unit.Unit.UnitType;

public class GameMng {

    //Singleton
    /////////////////////////////////////////////////////

    static GameMng instance;

    private GameMng() {}

    static {instance = new GameMng();}

    public static GameMng getInstace() {return instance;}

    /////////////////////////////////////////////////////

    private boolean gameProgress = false;
    
    private UnitType nowUnitType = UnitType.UnitType_End;

    public boolean getGameProgress(){return gameProgress;}
    
    public void beginGame() {
    	gameProgress = true;
    }

    public void endGame(){gameProgress = false;}
}
