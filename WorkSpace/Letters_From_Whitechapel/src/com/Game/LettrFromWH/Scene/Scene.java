package com.Game.LettrFromWH.Scene;


import java.util.LinkedHashMap;
import java.util.Map;
import com.Game.LettrFromWH.GameObject.GameObject;
import com.Game.LettrFromWH.Printer.PrintMng;
import com.Game.LettrFromWH.Scene.SceneMng.SceneType;

public abstract class Scene {
	
	Map<String, GameObject> gameObjectMap = new LinkedHashMap<>();
	
	public void init() {

		inputGameObject();
		for (Map.Entry<String, GameObject> entry : gameObjectMap.entrySet()) {
			entry.getValue().init();
		}

	}
	
	public void play() {
		
		 for (Map.Entry<String, GameObject> entry : gameObjectMap.entrySet()) {
			 if(entry.getValue().getIsPlaying()) {
				 entry.getValue().play();
			 }
		 }
		 
	}
	
	public void exit() {
		
		 for (Map.Entry<String, GameObject> entry : gameObjectMap.entrySet()) {
			 entry.getValue().outPlay();
			 entry.getValue().exit();
		 }
		
		 //gameObjectMap.clear();
		 
	}
	
	public void nextScene(SceneType sceneType) {
		SceneMng.getInstace().changeScene(sceneType);
	}
	
	public abstract void inputGameObject();
	
	public void setGameObject(String key, GameObject gameObject) {
		if(gameObject == null) {
			PrintMng.getInstace().pl("Scene에 넣을 겜 오브젝트 널입니다.");
			return;
		}
		
		if(gameObjectMap.containsKey(key)) {
			PrintMng.getInstace().pl("Scene에 넣을 겜 오브젝트의 키값이 이미 있슴돠.");
			return;
		}
		gameObjectMap.put(key, gameObject);
	}
	
	public GameObject getGameObject(String key) {
		if(!gameObjectMap.containsKey(key)) {
			PrintMng.getInstace().pl("Scene에서 가져올려 하는 객체의 키 값이 없슴돠.");
			return null;
		}
		
		return gameObjectMap.get(key);
	}
}
