package com.Game.LettrFromWH.FieldMng;

public class FieldMng {

	//Singleton
	/////////////////////////////////////////////////////
	
	static FieldMng instance;

	private FieldMng() {}
	
	static {instance = new FieldMng();}
	
	public static FieldMng getInstace() {return instance;}
	
	/////////////////////////////////////////////////////
    
    private int mapX = 0;
    private int mapY = 0;

    public int getMapX() {return mapX;}
    public int getMapY() {return mapY;}
	
	public void createField() {
		
	}
	
}
