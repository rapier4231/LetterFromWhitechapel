package com.Game.LettrFromWH.DB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.Game.LettrFromWH.Game.GameMng;
import com.Game.LettrFromWH.GameObject.GameField.GameField;
import com.Game.LettrFromWH.GameObject.Unit.Unit;
import com.Game.LettrFromWH.Printer.PrintMng;
import com.Game.LettrFromWH.Text.TextStore;
import com.Game.LettrFromWH.User.UserMng;

import oracle.jdbc.OracleType;
import oracle.jdbc.OracleTypes;

public class DBMng {

	//Singleton
	/////////////////////////////////////////////////////
	
	static DBMng instance;

	private DBMng() {}
	
	static {instance = new DBMng();}
	
	public static DBMng getInstace() {return instance;}
	
	/////////////////////////////////////////////////////
	Connection conn = DBConnection.getConnection();
	CallableStatement castmt;
	
	//Version//
	public boolean checkClientVersion() {
		
		String version = "";	
		try {
			
			castmt = conn.prepareCall("{ call GetActiveGameVersion(?) }");
			
			//모든 인자에 대한 set 및 out 셋팅
			castmt.registerOutParameter(1, OracleType.VARCHAR2);
			
			PrintMng.getInstace().pl("Now Version Checking");
			PrintMng.getInstace().beginDelayPrint(".",1);
			
			//실행
			castmt.execute();
			
			PrintMng.getInstace().endDelayPrint();
			
			//결과물 가져오기
			version = (String)castmt.getObject(1);
			
		    castmt.close();

		}
		catch (SQLException e) {
			PrintMng.getInstace().endDelayPrint();
			e.printStackTrace();
		}
		
		if(version.equals(TextStore.version)) {
			return true;
		}
		
		return false;
	}
	
	//Login//
	public boolean checkAccount(String userNickname) {
		
		try {
			castmt = conn.prepareCall("{ call CheckPlayerExists(?, ?) }");
			
			//모든 인자에 대한 set 및 out 셋팅
			castmt.setString(1, userNickname);
			castmt.registerOutParameter(2, OracleType.VARCHAR2);
			
			//실행
			castmt.execute();
			
			//결과물 가져오기
			String Exist = (String)castmt.getObject(2);

			castmt.close();

			if(Exist.equals("true")) {
				return true;
			}
			
			return false;

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public int getUserId(String userNickname) {
		
		try {
			castmt = conn.prepareCall("{ call GetPlayerID(?, ?) }");
			
			//모든 인자에 대한 set 및 out 셋팅
			castmt.setString(1, userNickname);
			castmt.registerOutParameter(2, OracleType.NUMBER);
			
			//실행
			castmt.execute();
			
			//결과물 가져오기
			int userId = castmt.getInt(2);
			
			castmt.close();

			return userId;

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return Integer.MAX_VALUE;
	}
	
	public String getUserNickname() {
		
		try {
			castmt = conn.prepareCall("{ call GetPlayerNickName(?, ?) }");
			
			//모든 인자에 대한 set 및 out 셋팅
			castmt.setInt(1, UserMng.getInstace().getUserId());
			castmt.registerOutParameter(2, OracleType.VARCHAR2);
			
			//실행
			castmt.execute();
			
			//결과물 가져오기
			String userNickname = castmt.getString(2);

			castmt.close();

			return userNickname;

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "";
		
	}

	public String getUserNickname(int id) {

		try {
			castmt = conn.prepareCall("{ call GetPlayerNickName(?, ?) }");

			//모든 인자에 대한 set 및 out 셋팅
			castmt.setInt(1, id);
			castmt.registerOutParameter(2, OracleType.VARCHAR2);

			//실행
			castmt.execute();

			//결과물 가져오기
			String userNickname = castmt.getString(2);

			castmt.close();

			return userNickname;

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return "";

	}
	
	public boolean createAccount(String userNickname) {
		
		try {
			castmt = conn.prepareCall("{ call CreatePlayer(?) }");
			
			//모든 인자에 대한 set 및 out 셋팅
			castmt.setString(1, userNickname);
			
			//실행
			castmt.execute();
			
			castmt.close();

			return true;

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		//중복 검사 후 바로 생성하지만 인터넷 속도 등으로 인해
		//동시에 계정을 만들거나 하면 한쪽은 failed 뜰 수도 있겟다
		return false;
		
	}

	//Matching//
	public int getWaitState() {
		int waitState = -1;
		try {
			castmt = conn.prepareCall("{ call GetPlayerState(?,?) }");
			
			//모든 인자에 대한 set 및 out 셋팅
			castmt.setInt(1, UserMng.getInstace().getUserId());
			castmt.registerOutParameter(2, OracleType.VARCHAR2);

			//실행
			castmt.execute();

			String str = castmt.getString(2);
			
			if(str == null){
				waitState = 0;
			}
			else if(str.equals("-2")){
				System.out.println("매칭 Multiple players found");
			}
			else{
				waitState = Integer.parseInt(str);
			}

			castmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return waitState;
	}

	public boolean startMatching(){
		try {
			castmt = conn.prepareCall("{ call UpdatePlayerStateToOne(?) }");

			//모든 인자에 대한 set 및 out 셋팅
			castmt.setInt(1, UserMng.getInstace().getUserId());

			//실행
			castmt.execute();

			castmt.close();

			return true;

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean updateMatching(){
		try {
			castmt = conn.prepareCall("{ call UpdatePlayerTime(?) }");

			//모든 인자에 대한 set 및 out 셋팅
			castmt.setInt(1, UserMng.getInstace().getUserId());

			//실행
			castmt.execute();

			castmt.close();

			return true;

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean agreeMatching(){
		try {
			castmt = conn.prepareCall("{ call UpdatePlayerStateToThree(?) }");

			//모든 인자에 대한 set 및 out 셋팅
			castmt.setInt(1, UserMng.getInstace().getUserId());

			//실행
			castmt.execute();

			castmt.close();

			return true;

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	public int getMatchingWaitTime() {
		int matchingWaitTime = 0;
		try {
			castmt = conn.prepareCall("{ call GetMatchingWaitTime(?) }");

			//모든 인자에 대한 set 및 out 셋팅
			castmt.registerOutParameter(1, OracleType.NUMBER);

			//실행
			castmt.execute();

			matchingWaitTime = castmt.getInt(1);

			castmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return matchingWaitTime;
	}

	public void cancelMatching(){
		try {
			castmt = conn.prepareCall("{ call UpdatePlayerStateToZero(?) }");

			//모든 인자에 대한 set 및 out 셋팅
			castmt.setInt(1, UserMng.getInstace().getUserId());

			//실행
			castmt.execute();
			
			castmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean qSuccessMatching() {
		boolean success = false;
		try {
			castmt = conn.prepareCall("{ call ????????????(?,?) }");

			//모든 인자에 대한 set 및 out 셋팅
			castmt.setInt(1, UserMng.getInstace().getUserId());
			castmt.registerOutParameter(2, OracleType.VARCHAR2);

			//실행
			castmt.execute();

			// = castmt.get();

			castmt.close();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return success;
	}

	public String getOpponentsUserNickname() {
		String opponentsUserNickname = "";
		try {
			castmt = conn.prepareCall("{ call GetMatchedPlayerID(?,?) }");

			//모든 인자에 대한 set 및 out 셋팅
			castmt.setInt(1, UserMng.getInstace().getUserId());
			castmt.registerOutParameter(2, OracleType.NUMBER);

			//실행
			castmt.execute();

			opponentsUserNickname = getUserNickname(castmt.getInt(2));

			castmt.close();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return opponentsUserNickname;
	}

	//GameInfo
	public Unit.UnitType  getMyRoll(){
		return Unit.UnitType.Jack;

//		Unit.UnitType  unitType = Unit.UnitType.UnitType_End;
//
//		try {
//			castmt = conn.prepareCall("{ call GetPlayerRole(?, ?) }");
//
//			//모든 인자에 대한 set 및 out 셋팅
//			castmt.setInt(1, UserMng.getInstace().getUserId());
//			castmt.registerOutParameter(2, OracleType.VARCHAR2);
//
//			//실행
//			castmt.execute();
//
//			unitType = Unit.UnitType.values()[Integer.parseInt(castmt.getString(2))];
//
//			castmt.close();
//		}
//		catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return unitType;
	}

	public int getMoveCount() {
		int moveCount = 0;
		try {
			castmt = conn.prepareCall("{ call GetPlayerMoveCount(?,?) }");

			//모든 인자에 대한 set 및 out 셋팅
			castmt.setInt(1, UserMng.getInstace().getUserId());
			castmt.registerOutParameter(2, OracleType.NUMBER);

			//실행
			castmt.execute();

			moveCount = castmt.getInt(2);

			castmt.close();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return moveCount;
	}
	
	public void settingTurnInfo() {
		try {
			castmt = conn.prepareCall("{ call GetGameSettings(?, ?) }");
			
			castmt.registerOutParameter(1, OracleTypes.NUMBER);
			castmt.registerOutParameter(2, OracleType.NUMBER);
			castmt.execute();

			GameMng.getInstace().setTurnInfo(Integer.parseInt(castmt.getString(1)),Integer.parseInt(castmt.getString(2)));

		    castmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	//Field
	public ResultSet settingFieldNode(GameField gameField) {
		try {
			castmt = conn.prepareCall("{ call GetPlayerRoomNodes(?, ?) }");

			//castmt.setInt(1, UserMng.getInstace().getUserId());
			castmt.setInt(1, 2);
			castmt.registerOutParameter(2, OracleTypes.CURSOR);//crs 받을 것
			
			castmt.execute();
			
			ResultSet rs;
			rs = (ResultSet) castmt.getObject(2);
			gameField.setFieldNode(rs);
			
		    rs.close();
		    castmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public int getFieldColCount() {
		int colCount = 0;
		try {
			castmt = conn.prepareCall("{ call GetCurrentGameFieldCol(?) }");

			castmt.registerOutParameter(1, OracleTypes.NUMBER);//crs 받을 것

			castmt.execute();

			colCount = castmt.getInt(1);

			castmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return colCount;
	}

	public int getFieldRowCount() {
		int rowCount = 0;
		try {
			castmt = conn.prepareCall("{ call ???(?) }");

			castmt.registerOutParameter(1, OracleTypes.NUMBER);//crs 받을 것

			castmt.execute();

			rowCount = castmt.getInt(1);

			castmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return rowCount;
	}

	//Turn
	public boolean getMyTurn() {
		boolean myTurn = false;
		try {
			castmt = conn.prepareCall("{ call ????????????(?) }");

			//모든 인자에 대한 set 및 out 셋팅
			castmt.registerOutParameter(1, OracleType.VARCHAR2);

			//실행
			castmt.execute();

			myTurn = castmt.getBoolean(1);

			castmt.close();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return myTurn;
	}

	public void updateTurnAndActionTalk() {

		try {
			castmt = conn.prepareCall("{ call ????????????(?,?,?) }");

			//모든 인자에 대한 set 및 out 셋팅
			castmt.setInt(1, UserMng.getInstace().getUserId());
			castmt.registerOutParameter(1, OracleType.NUMBER);
			castmt.registerOutParameter(1, OracleType.VARCHAR2);

			//실행
			castmt.execute();

			GameMng.getInstace().setTurnAndActionTalk(castmt.getInt(2),castmt.getString(3));

			castmt.close();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
