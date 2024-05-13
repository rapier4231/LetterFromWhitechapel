package com.Game.LettrFromWH.Component.Ability;

import com.Game.LettrFromWH.Game.GameMng;
import com.Game.LettrFromWH.GameObject.Unit.Jack;
import com.Game.LettrFromWH.Text.TextStore;

public class JackAbility extends Ability {

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void play() {
		// TODO Auto-generated method stub

	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public boolean setAbilityInput(String userInput) {
		
		if(userInput.equals(TextStore.yes)) {
			((Jack)getGameObject()).setKill();
			GameMng.getInstace().setActionTalk(TextStore.JackUseKill);
			return true;
		}
		else if(userInput.equals(TextStore.no)) {
			GameMng.getInstace().setActionTalk(TextStore.JackNotUseKill);
			return true;
		}
		
		return false;
	}

}
