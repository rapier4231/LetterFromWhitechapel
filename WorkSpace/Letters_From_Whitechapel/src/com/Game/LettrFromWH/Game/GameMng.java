package com.Game.LettrFromWH.Game;

public class GameMng {

    //Singleton
    /////////////////////////////////////////////////////

    static GameMng instance;

    private GameMng() {}

    static {instance = new GameMng();}

    public static GameMng getInstace() {return instance;}

    /////////////////////////////////////////////////////

    private boolean gameProgress = false;

    public boolean getGameProgress(){return gameProgress;}

    public void startGame() {
        gameProgress = true;
    }
}
