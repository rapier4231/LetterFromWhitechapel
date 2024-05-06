package com.Game.LettrFromWH.Printer;

public class PrintMng {

	//Singleton
	/////////////////////////////////////////////////////
	
	static PrintMng instance;

	private PrintMng() {}
	
	static {instance = new PrintMng();}
	
	public static PrintMng getInstace() {return instance;}
	
	/////////////////////////////////////////////////////
	
	final String os = System.getProperty("os.name");
	ProcessBuilder cmdClearProcessBuilder = new ProcessBuilder("cmd", "/c", "cls");
	
	public void cmdClear() {
		if(os.contains("Win")) {
			try {
				cmdClearProcessBuilder.inheritIO().start().waitFor();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
		
	public void p(String printStr) {System.out.print(printStr);} 
	public void pl(String printStr) {System.out.println(printStr);} 
	public void cp(String printStr) {cmdClear();System.out.print(printStr);} 
	public void cpl(String printStr) {cmdClear();System.out.println(printStr);}	
}
