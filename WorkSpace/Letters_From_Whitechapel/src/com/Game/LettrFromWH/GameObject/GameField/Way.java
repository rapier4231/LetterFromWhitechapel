package com.Game.LettrFromWH.GameObject.GameField;

public class Way extends FieldPiece{

    private String way = "";

    public void setTopBottomWay(){
        way = "│";
    }

    public void setLeftRightWay(){
        way = "ㅡ";
    }

    public void setLeftBottomRightTop(){
        if(way.equals("X")){
            return;
        }

        if(way.equals("\\")){
            way = "X";
        }
        else {
            way = "/";
        }
    }

    public void setLeftTopRightBottom(){
        if(way.equals("X")){
            return;
        }

        if(way.equals("/")){
            way = "X";
        }
        else{
            way = "\\";
        }
    }

    @Override
    public String toString(){
        return way;
    }

}
