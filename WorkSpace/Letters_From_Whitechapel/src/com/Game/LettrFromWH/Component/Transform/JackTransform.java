package com.Game.LettrFromWH.Component.Transform;

public class JackTransform extends Transform{
    @Override
    protected void startPos() {
        posX = 0;
        posY = 0;
    }

    @Override
    public boolean setMoveInput(String[] moveInputArray) {

        for(int i = 0; i < moveInputArray.length; ++i){
            if(!gameField.canMove(posX,posY,
                    Integer.parseInt(moveInputArray[i].substring(0,1)) ,
                    Integer.parseInt(moveInputArray[i].substring(1)))){
                return false;
            }
        }

        return true;
    }
}
