package com.Game.LettrFromWH.GameObject;

import java.util.HashMap;
import java.util.Map;

import com.Game.LettrFromWH.Component.Component;
import com.Game.LettrFromWH.Printer.PrintMng;

public abstract class GameObject {

	Map<String, Component> componentMap = new HashMap<>();
	private boolean isPlaying = true;
	
	public boolean getIsPlaying() {return isPlaying;}
	
	public void init() {
		inputComponent();
		for (Map.Entry<String, Component> entry : this.componentMap.entrySet()) {
			entry.getValue().init();
		}
	}
	
	public  void play(){
		for (Map.Entry<String, Component> entry : this.componentMap.entrySet()) {
			if(entry.getValue().getIsPlaying()){
				entry.getValue().play();
			}
		}
	}
	
	public  void exit(){
		for (Map.Entry<String, Component> entry : this.componentMap.entrySet()) {
			entry.getValue().exit();
		}

		this.componentMap.clear();
	}

	public void inputComponent() {

	}

	public void setComponent(String key, Component component) {
		if(component == null) {
			PrintMng.getInstace().pl("GameObject 넣을 컴포넌트 널입니다.");
			return;
		}

		if(this.componentMap.containsKey(key)) {
			PrintMng.getInstace().pl("GameObject에 넣을 겜 컴포넌트의 키값이 이미 있슴돠.");
			return;
		}
		this.componentMap.put(key, component);
		component.setGameObject(this);
	}

	public Component getComponent(String key) {
		if(!this.componentMap.containsKey(key)) {
			PrintMng.getInstace().pl("GameObject에서 가져올려 하는 객체의 키 값이 없슴돠.");
			return null;
		}

		return this.componentMap.get(key);
	}
	
	public void outPlay() {
		isPlaying = false;
	}
	
	public void inPlay() {
		isPlaying = true;
	}
}
