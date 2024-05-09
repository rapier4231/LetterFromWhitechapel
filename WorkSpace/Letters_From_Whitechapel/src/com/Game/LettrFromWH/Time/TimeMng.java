package com.Game.LettrFromWH.Time;

import java.util.HashMap;
import java.util.Map;

public class TimeMng {

	//Singleton
	/////////////////////////////////////////////////////
	
	static TimeMng instance;

	private TimeMng() {}
	
	static {instance = new TimeMng();}
	
	public static TimeMng getInstace() {return instance;}
	
	/////////////////////////////////////////////////////

    private final Map<String, Boolean> countingMap = new HashMap<>();

	public void delayS(float seconds) {
        try {
            Thread.sleep((long)(seconds * 1000.f));
        } 
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
	
	public void delay(long milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } 
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
	
    public void startCounting(String key, float countSeconds) {
        CountingThread countingThread = new CountingThread(key,countSeconds);
        countingThread.start();
        countingMap.put(key,false);
    }

    public void finishCounting(String key){
        if(countingMap.containsKey(key)){
            countingMap.put(key, true);
        }
    }

    public boolean checkCounting(String key){
        if(countingMap.containsKey(key) && countingMap.get(key)){
            countingMap.remove(key);
            return true;
        }

        return false;
    }

    public void stopCounting(String key){
        countingMap.remove(key);
    }
}
