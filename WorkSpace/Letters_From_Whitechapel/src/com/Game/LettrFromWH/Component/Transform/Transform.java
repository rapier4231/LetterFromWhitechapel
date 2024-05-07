package com.Game.LettrFromWH.Component.Transform;

import com.Game.LettrFromWH.Component.Component;
import com.Game.LettrFromWH.FieldMng.FieldMng;
import com.Game.LettrFromWH.GameObject.Unit.Unit;

public class Transform extends Component {

	@Override
	public void init() {
		startPos();
	}

	@Override
	public void play() {

	}

	@Override
	public void exit() {

	}
	
	//////////////////////////////////////////////////
	
	private int posX;
	private int posY;

	private void startPos() {
		switch(((Unit)getGameObject()).getUnitType()) {
			case Jack:
				posX = 0;
				posY = 0;
				break;
			case Police1:
				posX = FieldMng.getInstace().getMapX() - 1;
				posY = FieldMng.getInstace().getMapY() - 1;
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
