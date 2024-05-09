package com.Game.LettrFromWH.Printer;

import com.Game.LettrFromWH.MyThread.MyThread;

public class DelayPrintThread extends MyThread {

	public DelayPrintThread(String print, float delayTimeS) {
		this.print = print;
		this.delayTimeS = delayTimeS;
	}
	
	private final String print;
	private final float delayTimeS;
	private boolean ing = true;
	public void cancel() {
		ing = false;
	}
	
	@Override
	public void run() {
		super.run();
		while(ing) {
			delayS(delayTimeS);
			if(ing) {				
				PrintMng.getInstace().p(print);
			}
		}
	}
	
}
