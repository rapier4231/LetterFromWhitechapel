package com.Game.LettrFromWH.Component.Transform;

import java.util.ArrayList;

import com.Game.LettrFromWH.Component.Component;
import com.Game.LettrFromWH.DB.DBMng;
import com.Game.LettrFromWH.Game.GameMng;
import com.Game.LettrFromWH.GameObject.Unit.Unit;


public abstract class Transform extends Component {

	private final int moveCount = DBMng.getInstace().getMoveCount();

	protected String posX;
	protected String posY;
	
	public int getIntPosX() {return posXStringToInt(posX);}
	public int getIntPosY() {return posYStringToInt(posY);}
	public String getStringPosX() {return posX;}
	public String getStringPosY() {return posY;}
	public String getStringPos() {return posX+posY;}
	
	@Override
	public void init() {
		super.init();
		startPos();
	}
	
	protected abstract void startPos();

	public int getMoveCount() {
		return moveCount;
	}

    public boolean setMoveInput(String[] moveInputArray) {
    	
    	if(moveInputArray.length > moveCount) {
    		return false;
    	}
    	
    	ArrayList<String> moveList = new ArrayList<String>();
    	
    	int[] nowTemp = new int[2];
    	int[] preTemp = new int[2];
    	preTemp[0] = getIntPosX();
    	preTemp[1] = getIntPosY();
    	
        for(int i = 0; i < moveInputArray.length; ++i){
        	nowTemp[0] = posXStringToInt(moveInputArray[i].substring(0,1));
        	nowTemp[1] = posYStringToInt(moveInputArray[i].substring(1));
        	
            if(!((Unit)getGameObject()).canMove(preTemp[0],preTemp[1],nowTemp[0],nowTemp[1])){
                return false;
            }
            moveList.add(((Unit)getGameObject()).getNodeName(nowTemp[0],nowTemp[1]));
            preTemp[0] = nowTemp[0];
            preTemp[1] = nowTemp[1];
        }

        GameMng.getInstace().setMoveList(moveList);
        moveMyUnit(nowTemp[0],nowTemp[1]);
        posX = moveList.get(moveList.size() - 1).substring(0, 1);
        posY = moveList.get(moveList.size() - 1).substring(1);
        return true;
    }
    
    private int posXStringToInt(String posX) {
    	return (int)posX.charAt(0) - 65;
    }
    private int posYStringToInt(String posY) {
    	int intPosY = Integer.MAX_VALUE;
    	try {
    		intPosY = Integer.parseInt(posY) - 1;
    	} 
    	catch (NumberFormatException e) {
    	}
    	
    	return intPosY;
    }
    
   private void moveMyUnit(int posX, int posY) {
	   ((Unit)getGameObject()).moveMyUnit(getIntPosX(),getIntPosY(),posX,posY);
   }
}
