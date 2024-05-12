package com.Game.LettrFromWH.Game;

import java.util.ArrayList;

import com.Game.LettrFromWH.DB.DBConnection;
import com.Game.LettrFromWH.Printer.PrintMng;
import com.Game.LettrFromWH.Scene.SceneMng;
import com.Game.LettrFromWH.Scene.SceneMng.SceneType;
import com.Game.LettrFromWH.Text.TextStore;
import com.Game.LettrFromWH.Time.TimeMng;

public class GameApp {

    public void gameStart(){
    	//printLogo();
        PrintMng.getInstace().cpl(TextStore.hi);
        DBConnection.connect();
        DBConnectNullChecking();
        SceneMng.getInstace().begin();
        GameMng.getInstace().beginGame();
    }

    public void gamePlay() {
        while (GameMng.getInstace().getGameProgress()) {
            SceneMng.getInstace().getCurrentScene().getScene().play();
            TimeMng.getInstace().delay(GameMng.getInstace().getDelayTime());
        }
    }

    public void gameEnd(){
    	GameMng.getInstace().endGame();
        SceneMng.getInstace().end();
    }
    
    //////////////////////////////////////////////////////
    
    public void DBConnectNullChecking() {
    	PrintMng.getInstace().changeLine();
        if(DBConnection.getConnection() == null) {
        	System.out.println("Failed to create Oracle Jdbc Driver");
        }
        else {
        	System.out.println("Successfully created Oracle Jdbc Driver");
        }
    }
    
    public void printLogo() {
    	    	
    	ArrayList<String> logoStringList = new ArrayList<String>();
    	logoStringList.add("=================================");
    	logoStringList.add("|| Letter                      ||");
    	logoStringList.add("||        From                 ||");
    	logoStringList.add("||                White        ||");
    	logoStringList.add("||                      Chapel ||");
    	logoStringList.add("=================================");
    	
    	
    	PrintMng.getInstace().rightAppearPrint(logoStringList);
    	TimeMng.getInstace().delayS(1);
    }
}
