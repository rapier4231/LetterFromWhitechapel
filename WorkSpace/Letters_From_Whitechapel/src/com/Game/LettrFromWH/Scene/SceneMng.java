package com.Game.LettrFromWH.Scene;

public class SceneMng {

	//Singleton
	/////////////////////////////////////////////////////
	static SceneMng instance;

	private SceneMng() {}
	
	static {instance = new SceneMng();}
	
	public static SceneMng getInstace() {return instance;}
	/////////////////////////////////////////////////////
	
	public enum SceneType{
		VersionCheckScene,
		LoginScene,
		MainScene,
		MatchingScene,
		GameScene,
		ExitScene;
		
		private SceneType() {
			this.scene = createScene();
		}
		
		private Scene scene;
		
		private Scene createScene() {
			switch(this.name()) {
				case "VersionCheckScene":
					return new VersionCheckScene();
				case "LoginScene":
					return new LoginScene();
				case "MainScene" :
					return new MainScene();
				case "MatchingScene" :
					return new MatchingScene();
				case "GameScene":
					return new GameScene();
				case "ExitScene" :
					return new ExitScene();
				default:
					return null;
			}
		}
		
	    public Scene getScene() {
	        return scene;
	    }
	} 
	
	private SceneType preScene;
	private SceneType currentScene;
	
	public SceneType getCurrentScene() {return currentScene;}	
	
	public void changeScene(SceneType nextScene) {
		if(nextScene == currentScene) {
			return;
		}
		
		preScene = currentScene;
		currentScene = nextScene;
		if(preScene != null) {
			preScene.getScene().exit();
		}
		currentScene.getScene().init();
	}
	
	public void changepreScene() {
		changeScene(preScene);
	}

	public void begin(){
		changeScene(SceneType.VersionCheckScene);
	}

	public void end(){
		currentScene.getScene().exit();
	}
}
