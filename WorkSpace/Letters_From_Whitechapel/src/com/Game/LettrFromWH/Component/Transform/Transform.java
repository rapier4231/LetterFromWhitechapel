package com.Game.LettrFromWH.Component.Transform;

import com.Game.LettrFromWH.Component.Component;
import com.Game.LettrFromWH.DB.DBMng;
import com.Game.LettrFromWH.GameObject.GameField.GameField;
import com.Game.LettrFromWH.GameObject.Unit.Unit.UnitType;
import com.Game.LettrFromWH.Scene.SceneMng;

public class Transform extends Component {

	private final GameField gameField = (GameField)SceneMng.getInstace().getCurrentScene().getScene().getGameObject("GameField");
	
	private final UnitType unitType;
	
	private final int moveCount = DBMng.getInstace().getMoveCount();
	
	private String posX;
	private String posY;
	
	public Transform(UnitType unitType) {
		this.unitType = unitType;
	}

	public String getPos() {
		return posX + posY;
	}
	
	@Override
	public void init() {
		super.init();
		startPos();
	}
	
	private void startPos() {
		switch(unitType) {
			case Jack:
				posX = "a";
				posY = "1";
				break;
			case Police1:
				posX = gameField.getLastPosX();
				posY = gameField.getLastPosY();
				break;
			case Police2:
				break;
			case UnitType_End:
				break;
		}
	}

	public boolean movePos(int x, int y) {
		return true;
	}
}
