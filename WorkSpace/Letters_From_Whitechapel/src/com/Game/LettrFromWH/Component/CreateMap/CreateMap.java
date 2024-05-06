package com.Game.LettrFromWH.Component.CreateMap;

import com.Game.LettrFromWH.Component.Component;
import com.Game.LettrFromWH.Scene.SceneMng;

public class CreateMap extends Component {
    @Override
    public void init() {

    }

    @Override
    public void play() {

    }

    @Override
    public void exit() {

    }

    public void createMap(){

    }

    public void finishCreatingMap(){
        SceneMng.getInstace().changeScene(SceneMng.SceneType.GameScene);
    }
}
