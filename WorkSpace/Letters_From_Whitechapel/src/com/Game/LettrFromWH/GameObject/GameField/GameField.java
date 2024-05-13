package com.Game.LettrFromWH.GameObject.GameField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.Game.LettrFromWH.DB.DBMng;
import com.Game.LettrFromWH.Game.GameMng;
import com.Game.LettrFromWH.GameObject.GameObject;

public class GameField extends GameObject {

	@Override
	public void init() {
		super.init();
		createField();
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
		settingUnit();
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
	
	private void settingUnit() {
		((Node)fieldPiece2dArrayList.get(0).get(0)).inJack();
		((Node)(fieldPiece2dArrayList.get(fieldPiece2dArrayList.size() - 1).
				get(fieldPiece2dArrayList.get(0).size() - 1))).inPolice1();
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
			return false;
		}
		
		Node finishNode = getViewIndexNode(startPosX,startPosY);
		
		//위
		if(LeftRight == 0 && TopBottom == 1) {
			if(finishNode.isCanMoveTop()) {
				return true;
			}
			
			return false;
		}
		//아래
		else if(LeftRight == 0 && TopBottom == 2) {
			if(finishNode.isCanMoveBottom()) {
				return true;
			}
			
			return false;
		}
		//오
		else if(LeftRight == 2 && TopBottom == 0) {
			if(finishNode.isCanMoveRight()) {
				return true;
			}
			
			return false;
		}
		//왼
		else if(LeftRight == 1 && TopBottom == 0) {
			if(finishNode.isCanMoveLeft()) {
				return true;
			}
			
			return false;
		}
		//왼위
		else if(LeftRight == 1 && TopBottom == 1) {
			if(finishNode.isCanMoveLeftTop()) {
				return true;
			}
			
			return false;
		}
		//오위
		else if(LeftRight == 2 && TopBottom == 1) {
			if(finishNode.isCanMoveRightTop()) {
				return true;
			}
			
			return false;
		}
		//왼아래
		else if(LeftRight == 1 && TopBottom == 2) {
			if(finishNode.isCanMoveLeftBottom()) {
				return true;
			}
			
			return false;
		}
		//오아래
		else if(LeftRight == 2 && TopBottom == 2) {
			if(finishNode.isCanMoveRightBottom()) {
				return true;
			}
			
			return false;
		}

		return false;
	}

	public String setKill(int posX, int posY) {
		return getViewIndexNode(posX,posY).setKill();
	}

	public boolean isCanKill(int posX, int posY) {
		return getViewIndexNode(posX,posY).isCanKill();
	}

	public String getNodeName(int posX, int posY) {
		return getViewIndexNode(posX,posY).getName();
	}

	public void moveOpponents(String opponentsNode, String nowOpponentsNode) {
		
		//경찰일 경우 안 그려준다 잭.
		if(GameMng.getInstace().getMyRoll() == 1) {
			return;
		}

		int beginPosX = ((int)(opponentsNode.substring(0, 1).charAt(0)) - 65);
		int beginPosY = Integer.parseInt(opponentsNode.substring(1)) - 1;
		int endPosX = ((int)(nowOpponentsNode.substring(0, 1).charAt(0)) - 65);
		int endPosY = Integer.parseInt(nowOpponentsNode.substring(1)) - 1;
		
		if(GameMng.getInstace().getMyRoll() == 0) {
			getViewIndexNode(beginPosX,beginPosY).outPolice1();
			getViewIndexNode(endPosX,endPosY).inPolice1();
		}
		
	}

	public void setOpponentsNode(boolean isImJack) {
		if(isImJack) {
			GameMng.getInstace().setOpponentsNode(getRealIndexNode(getLastIndexX(),getLastIndexY()).getName());
		}
		else {
			GameMng.getInstace().setOpponentsNode("A1");
		}
	}

	private int getLastIndexX() {
		return fieldPiece2dArrayList.get(0).size() - 1;
	}
	
	private int getLastIndexY() {
		return fieldPiece2dArrayList.size() - 1;
	}
	
	private Node getRealIndexNode(int viewIndexPosX, int viewIndexPosY) {
		return ((Node)fieldPiece2dArrayList.get(viewIndexPosY).get(viewIndexPosX));
	}
	
	private Node getViewIndexNode(int viewIndexPosX, int viewIndexPosY) {
		return ((Node)fieldPiece2dArrayList.get(viewIndexPosY * 2).get(viewIndexPosX * 2));
	}
	
	public void moveMyUnit(int beginPosX, int beginPosY, int endPosX, int endPosY, boolean isJack) {
		if(isJack) {
			getViewIndexNode(beginPosX,beginPosY).outJack();
			getViewIndexNode(endPosX,endPosY).inJack();
		}
		else {
			getViewIndexNode(beginPosX,beginPosY).outPolice1();
			getViewIndexNode(endPosX,endPosY).inPolice1();
		}
	}

	public void setKillNode(ResultSet rs) throws SQLException{
		while(rs.next()) {
			String killNodeNme = rs.getString(1);
			setKill(killNodeNme.substring(0,1).charAt(0) - 65,Integer.parseInt(killNodeNme.substring(1)) - 1);
		}
		
	}
}
