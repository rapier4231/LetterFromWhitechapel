package com.Game.LettrFromWH.Time;

import com.Game.LettrFromWH.MyThread.MyThread;

public class CountingThread extends MyThread {

    private final String key;
    private final float countSeconds;
    public CountingThread(String key, float countSeconds){
        this.key = key;
        this.countSeconds = countSeconds;
    }

    @Override
    public void run() {
        super.run();
        delayS(countSeconds);
        TimeMng.getInstace().finishCounting(key);
    }
}
