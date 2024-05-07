package com.Game.LettrFromWH.Component;

import com.Game.LettrFromWH.GameObject.GameObject;

public abstract class Component {

	private GameObject gameObject;
	private boolean isPlaying = true;
	
	public boolean getIsPlaying() {return isPlaying;}
	
    public abstract void init();

    public abstract void play();

    public abstract void exit();

	public GameObject getGameObject() {
		return gameObject;
	}

	public void setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}
	
	public void outPlay() {
		isPlaying = false;
	}
	
	public void inPlay() {
		isPlaying = true;
	}

}
