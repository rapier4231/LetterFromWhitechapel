package com.Game.LettrFromWH.Game;

import com.Game.LettrFromWH.DB.DBConnection;
import com.Game.LettrFromWH.Printer.PrintMng;
import com.Game.LettrFromWH.Scene.SceneMng;
import com.Game.LettrFromWH.Text.TextStore;

public class GameApp {

    public void gameStart(){
        PrintMng.getInstace().cpl(TextStore.hi);
        DBConnection.connect();
        DBConnectNullChecking();
        SceneMng.getInstace().begin();
        GameMng.getInstace().beginGame();
    }

    public void gamePlay() {
        while (GameMng.getInstace().getGameProgress()) {
            SceneMng.getInstace().getCurrentScene().getScene().play();
        }
    }

    public void gameEnd(){
    	GameMng.getInstace().endGame();
        SceneMng.getInstace().end();
    }
    
    //////////////////////////////////////////////////////
    
    public void DBConnectNullChecking() {
        if(DBConnection.getConnection() == null) {
        	System.out.println("Failed to create Oracle Jdbc Driver");
        }
        else {
        	System.out.println("Successfully created Oracle Jdbc Driver");
        }
    }
}
