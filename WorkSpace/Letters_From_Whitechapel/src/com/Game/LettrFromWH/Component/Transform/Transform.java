package com.Game.LettrFromWH.Component.Transform;

import com.Game.LettrFromWH.Component.Component;
import com.Game.LettrFromWH.DB.DBMng;
import com.Game.LettrFromWH.GameObject.GameField.GameField;
import com.Game.LettrFromWH.GameObject.Unit.Unit.UnitType;
import com.Game.LettrFromWH.Scene.SceneMng;

public abstract class Transform extends Component {

	protected final GameField gameField = (GameField)SceneMng.getInstace().getCurrentScene().getScene().getGameObject("GameField");
	
	private final int moveCount = DBMng.getInstace().getMoveCount();

	protected int posX;
	protected int posY;

	@Override
	public void init() {
		super.init();
		startPos();
	}
	
	protected abstract void startPos();

	public int getMoveCount() {
		return moveCount;
	}

	public abstract boolean setMoveInput(String[] moveInputArray);
}
