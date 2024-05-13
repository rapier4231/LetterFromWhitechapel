package com.Game.LettrFromWH.Component.Ability;

import com.Game.LettrFromWH.DB.DBMng;
import com.Game.LettrFromWH.Game.GameMng;
import com.Game.LettrFromWH.GameObject.Unit.Police1;
import com.Game.LettrFromWH.Text.TextStore;

public class Police1Ability extends Ability {

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
		
		Police1 police = ((Police1)getGameObject());
		String pos = police.getStringPos();

		if(userInput.equals(TextStore.Inquiry)) {
			if(DBMng.getInstace().inquiryJack(pos)) {
				GameMng.getInstace().setActionTalk(TextStore.PoliceInquirySucess);
			}
			else {
				GameMng.getInstace().setActionTalk(TextStore.PoliceInquiryFailed);
			}
			
			return true;
		}
		else if(userInput.equals(TextStore.Arrest)) {
			if(DBMng.getInstace().arrestJack(pos)) {				
				GameMng.getInstace().setActionTalk(TextStore.PoliceArrestFailed);
			}
			else {
				GameMng.getInstace().setActionTalk(TextStore.PoliceArrestSucess);
			}
		
			return true;
		}
		else {			
			return false;
		}
	}
	
}
