package com.Game.LettrFromWH.MyThread;

public abstract class MyThread extends Thread{

    public void delayS(float seconds){
        try {
            Thread.sleep((long)(seconds * 1000.f));
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
