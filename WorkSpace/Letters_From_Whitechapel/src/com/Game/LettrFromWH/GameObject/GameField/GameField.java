package com.Game.LettrFromWH.GameObject.GameField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.Game.LettrFromWH.DB.DBMng;
import com.Game.LettrFromWH.GameObject.GameObject;

public class GameField extends GameObject {

	@Override
	public void init() {
		super.init();
	}

	@Override
	public void play() {
		super.play();
	}

	@Override
	public void exit() {
		super.exit();
	}
	
	///////////////////////////
	
    private final ArrayList<ArrayList<FieldPiece>> fieldPiece2dArrayList = new ArrayList<>();
    
    public ArrayList<ArrayList<FieldPiece>> getFieldPiece2dArrayList(){
    	return fieldPiece2dArrayList;
    }

    public void createField() {
    	DBMng.getInstace().settingFieldNode(this);
		settingFieldWay();
    }
    
	public void setFieldNode(ResultSet fieldNode) throws SQLException {
		int colCount = DBMng.getInstace().getFieldColCount() * 2 - 1;
		//int rowCount = DBMng.getInstace().getFieldRowCount() * 2 - 1;
		int rowCount = colCount;

		for(int i = 0; i < colCount; ++i){
			fieldPiece2dArrayList.add(new ArrayList<>());
		}

		for(int i = 0; i < colCount; ++i){
			for(int j = 0; j < rowCount; ++j){
				if(i % 2 == 0 && j % 2 == 0){
					if(!fieldNode.next()){
						break;
					}

					fieldPiece2dArrayList.get(i).add(new Node(fieldNode.getString(1),fieldNode.getString(2),
								fieldNode.getString(3),fieldNode.getString(4),fieldNode.getString(5),
								fieldNode.getString(6),fieldNode.getString(7),fieldNode.getString(8),
								fieldNode.getString(9),fieldNode.getString(10)));
				}
				else{
					fieldPiece2dArrayList.get(i).add(new Way());
				}
			}
		}
	}

	private void settingFieldWay(){
		int colCount = fieldPiece2dArrayList.size();
		int rowCount = fieldPiece2dArrayList.get(0).size();

		for(int i = 0; i < colCount; ++i) {
			for (int j = 0; j < rowCount; ++j) {
				if(i % 2 == 0 && j % 2 == 0){
					changeWay(i,j, (Node)fieldPiece2dArrayList.get(i).get(j));
				}
			}
		}
	}

	private void changeWay(int col, int row, Node node){
		if(node.isCanMoveTop()){
			((Way)fieldPiece2dArrayList.get(col + 1).get(row)).setTopBottomWay();
		}
		if(node.isCanMoveBottom()){
			((Way)fieldPiece2dArrayList.get(col - 1).get(row)).setTopBottomWay();
		}
		if(node.isCanMoveLeft()){
			((Way)fieldPiece2dArrayList.get(col).get(row - 1)).setLeftRightWay();
		}
		if(node.isCanMoveRight()){
			((Way)fieldPiece2dArrayList.get(col).get(row+ 1)).setLeftRightWay();
		}
		if(node.isCanMoveLeftTop()){
			((Way)fieldPiece2dArrayList.get(col + 1).get(row - 1)).setLeftTopRightBottom();
		}
		if(node.isCanMoveLeftBottom()){
			((Way)fieldPiece2dArrayList.get(col - 1).get(row - 1)).setLeftBottomRightTop();
		}
		if(node.isCanMoveRightTop()){
			((Way)fieldPiece2dArrayList.get(col + 1).get(row + 1)).setLeftBottomRightTop();
		}
		if(node.isCanMoveRightBottom()){
			((Way)fieldPiece2dArrayList.get(col - 1).get(row + 1)).setLeftTopRightBottom();
		}
	}

	public String getLastPosX() {
		return null;
	}
	public String getLastPosY() {
		return null;
	}

	public boolean canMove(int startPosX, int startPosY, int finishPosX, int finishPosY) {
		if(Math.abs(startPosX - finishPosX ) > 1 || Math.abs(startPosY - finishPosY ) > 1 ){
			return false;
		}

		int LeftRight = 2;
		int TopBottom = 2;

		//왼오
		if(startPosX == finishPosX){
			LeftRight = 0;
		}
		else if(startPosX > finishPosX){
			LeftRight = 1;
		}

		//위아래
		if(startPosY == finishPosY){
			TopBottom = 0;
		}
		else if(startPosY < finishPosY){
			TopBottom = 1;
		}

		//제자리
		if(LeftRight == 0 && TopBottom == 0){

		}
		else if(LeftRight == 0 && TopBottom == 1){

		}
	}
}
