package com.Game.LettrFromWH.Time;

public class TimeMng {

	//Singleton
	/////////////////////////////////////////////////////
	
	static TimeMng instance;

	private TimeMng() {}
	
	static {instance = new TimeMng();}
	
	public static TimeMng getInstace() {return instance;}
	
	/////////////////////////////////////////////////////
	
	public void delay (int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } 
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
	
}
