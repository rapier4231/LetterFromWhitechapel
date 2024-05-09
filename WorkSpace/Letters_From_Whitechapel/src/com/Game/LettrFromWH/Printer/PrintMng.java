package com.Game.LettrFromWH.Printer;

import java.util.ArrayList;

import com.Game.LettrFromWH.Time.TimeMng;

public class PrintMng{

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
	public void changeLine() {p("\n");}
	
	/////////////////////////////////////////////////////////
	
	private final float fps = 20;
	private final long delayTime = (long)(1.f/fps*1000.f);
	
	private enum PrintType{
		RightAppear,
		DiagonalAppear,
		PrintType_End
	}
	
	private PrintType printType = PrintType.PrintType_End;
	
	private ArrayList<String> printStringList;
	private int printStringListSize;
	private int printStringListMaxLength;
	private String maxEmptyStr;
	
	public void rightAppearPrint(ArrayList<String> printStringList) {
		this.printStringList = printStringList;
		printType = printType.RightAppear;
		startEffect();
	}
	
	public void diagonalAppearPrint(ArrayList<String> printStringList) {
		this.printStringList = printStringList;
		printType = printType.DiagonalAppear;
		startEffect();
	}

	public void startEffect() {
		checkArrayList();
		
		switch(printType) {
			case RightAppear:
				rightAppear();
				break;
			case DiagonalAppear:
				settingMaxEmpty();
				diagonalAppear();
				break;
			case PrintType_End:
				break;
			default :
				break;
		}
		
	}
	
	private void checkArrayList() {
		printStringListSize = printStringList.size();
		printStringListMaxLength = 0;
		for(int i = 0; i < printStringListSize; ++i) {
			if(printStringListMaxLength < printStringList.get(i).length())
			{
				printStringListMaxLength = printStringList.get(i).length();
			}
		}
	}
	
	private void rightAppear() {
		int appearIndex = 0;
		String temp;
		while(++appearIndex <= printStringListMaxLength) {
			cmdClear();
			for(int i = 0; i < printStringListSize; ++i) {
				temp = printStringList.get(i);
				if(temp.length() > appearIndex) {
					pl(temp.substring(0,appearIndex));
				}
				else {
					pl(temp);
				}
			}
			
			TimeMng.getInstace().delay(delayTime);
		}
	}
	
	private void diagonalAppear() {

		ArrayList<int[]> printIndexList = createDiagonalAppearPrintIndexList();
		int count = printStringListMaxLength / 2 + printStringListSize + 1;
		int appearIndex = 0;
		//세로가 더 길 경우의 조건은 잡아주지 않았음. -> 그럴 일이 없을 것 같아서.
		while(++appearIndex <= count) {
			calculateDiagonalAppearLeft(printIndexList, appearIndex);
			calculateDiagonalAppearRight(printIndexList, appearIndex);
			
			printDiagonalAppear(printIndexList);
			TimeMng.getInstace().delay(delayTime);
		}
	}
	
	private void settingMaxEmpty() {
		maxEmptyStr = "";
		for(int i = 0; i < printStringListMaxLength; ++i) {
			maxEmptyStr += " ";
		}
	}

	private ArrayList<int[]> createDiagonalAppearPrintIndexList(){
		ArrayList<int[]> printIndexList = new ArrayList<int[]>();
		
		for(int i = 0; i < printStringListSize; ++i) {
			printIndexList.add(new int[4]);
		}
		
		return printIndexList;
	}
	
	private void calculateDiagonalAppearLeft(ArrayList<int[]> printIndexList, int appearIndex) {
		int[] temp;
		for(int i = 0; i < printStringListSize; ++i) {
			temp = printIndexList.get(i);
			temp[0] = 0;
			temp[1] = appearIndex - i;
		}
	}
	
	private void calculateDiagonalAppearRight(ArrayList<int[]> printIndexList, int appearIndex) {
		int[] temp;
		for(int i = 0; i < printStringListSize; ++i) {
			temp = printIndexList.get(i);
			//temp[2] = printStringList.get(i).length() - 1 + printStringListSize - i - 1 - appearIndex;
			temp[2] = printStringList.get(i).length() + printStringListSize - (2 + i + appearIndex);
			temp[3] = printStringList.get(i).length() - 1;
		}
	}
	
	
	private void printDiagonalAppear(ArrayList<int[]> printIndexList) {
		printCheckIndexDiagonalAppear(printIndexList);
		
		String strTemp;
		int[] arrTemp;
		
		cmdClear();
		for(int i = 0; i < printStringListSize; ++i) {
			strTemp = printStringList.get(i);
			arrTemp = printIndexList.get(i);
			pl(strTemp.substring(arrTemp[0], arrTemp[1]) + maxEmptyStr.substring(arrTemp[1], arrTemp[2]) + strTemp.substring(arrTemp[2], arrTemp[3]));
		}
	}
	
	private void printCheckIndexDiagonalAppear(ArrayList<int[]> printIndexList) {
		int[] temp;
		for(int i = 0; i < printStringListSize; ++i) {
			temp = printIndexList.get(i);
			if(temp[0] < 0) {
				temp[0] = 0;
			}
			if(temp[1] < 0) {
				temp[1] = 0;
			}
			if(temp[1] > temp[2]) {
				temp[1] = 0;
				temp[2] = 0;
			}
			if(temp[3] + 1 > printStringList.get(i).length()) {
				temp[3] =  printStringList.get(i).length() - 1;
			}
			if(temp[2] > temp[3]) {
				temp[2] = temp[3];
			}
		}
	}
	///////////////////////////////////////////////////////////////
	
	private DelayPrintThread delayPrintThread;
	
	public void beginDelayPrint(String delayPrintStr, float delayTimeS) {
		delayPrintThread = new DelayPrintThread(delayPrintStr,delayTimeS);
		delayPrintThread.start();
	}

	public void endDelayPrint() {
		if(delayPrintThread != null){			
			delayPrintThread.cancel();
			delayPrintThread = null;
		}
	}
}
