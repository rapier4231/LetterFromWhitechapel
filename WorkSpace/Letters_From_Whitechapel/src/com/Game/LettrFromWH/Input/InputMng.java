package com.Game.LettrFromWH.Input;

import java.util.Scanner;

public class InputMng {

	//Singleton
	/////////////////////////////////////////////////////
	
	static InputMng instance;

	private InputMng() {}
	
	static {instance = new InputMng();}
	
	public static InputMng getInstace() {return instance;}
	
	/////////////////////////////////////////////////////
	//이클립스 기본 EUC-KR
	private static final Scanner scanner = new Scanner(System.in,"EUC-KR");
	//인텔리 제이 기본 UTF-8
	//private static final Scanner scanner = new Scanner(System.in,"UTF-8");

	public String Input() {
		String userInput = "";

		 try {
	            while (userInput.isEmpty()) {
	                if (scanner.hasNextLine()) {
	                    userInput = scanner.nextLine();
	                    userInput = userInput.replaceAll(" ", ""); 
	                }

	                Thread.sleep(100);
	            }

	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

		return userInput;
	}
	
}
