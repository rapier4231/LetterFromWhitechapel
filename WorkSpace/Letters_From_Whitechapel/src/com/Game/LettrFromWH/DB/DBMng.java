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

	private void checkCastmt() throws SQLException {
		if(castmt == null){
			return;
		}
		if(castmt.isClosed()){
			castmt = null;
			return;
		}

		castmt.close();
		castmt = null;
	}

	//Version//
	public boolean checkClientVersion() {
		PrintMng.getInstace().p("Now Version Checking");
		PrintMng.getInstace().beginDelayPrint(".",1);
		
		String version = "";	
		try {
			checkCastmt();

			castmt = conn.prepareCall("{ call GetActiveGameVersion(?) }");
			
			//모든 인자에 대한 set 및 out 셋팅
			castmt.registerOutParameter(1, OracleType.VARCHAR2);		

			//실행
			castmt.execute();		
			
			//결과물 가져오기
			version = (String)castmt.getObject(1);
			
		    castmt.close();

		}
		catch (SQLException e) {
			PrintMng.getInstace().endDelayPrint();
			e.printStackTrace();
		}
		
		PrintMng.getInstace().endDelayPrint();
		
		if(version.equals(TextStore.version)) {
			return true;
		}
			
		return false;
	}
	
	//Login//
	public boolean checkAccount(String userNickname) {
		
		try {
			checkCastmt();

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
		
		try {checkCastmt();
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
		
		try {checkCastmt();
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

		try {checkCastmt();
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
		
		try {checkCastmt();
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
		try {checkCastmt();
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
			//e.printStackTrace();
		}
		
		return waitState;
	}

	public boolean startMatching(){
		try {checkCastmt();
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
		try {checkCastmt();
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
		try {checkCastmt();
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
		try {checkCastmt();
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
		try {checkCastmt();
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
	
//	public boolean qSuccessMatching() {
//		boolean success = false;
//		try {
//			castmt = conn.prepareCall("{ call ????????????(?,?) }");
//
//			//모든 인자에 대한 set 및 out 셋팅
//			castmt.setInt(1, UserMng.getInstace().getUserId());
//			castmt.registerOutParameter(2, OracleType.VARCHAR2);
//
//			//실행
//			castmt.execute();
//
//			// = castmt.get();
//
//			castmt.close();
//
//		}
//		catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		return success;
//	}

	public String getOpponentsUserNickname() {
		String opponentsUserNickname = "";
		int opponentsUserId = 0;
		try {checkCastmt();
			castmt = conn.prepareCall("{ call GetMatchedPlayerID(?,?) }");

			//모든 인자에 대한 set 및 out 셋팅
			castmt.setInt(1, UserMng.getInstace().getUserId());
			castmt.registerOutParameter(2, OracleType.NUMBER);
			//실행
			castmt.execute();
			opponentsUserId = castmt.getInt(2);
			castmt.close();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		opponentsUserNickname = getUserNickname(opponentsUserId);

		return opponentsUserNickname;
	}

	//GameInfo

	public int getTotalPlayers() {
		int totalPlayers = 0;
		try {checkCastmt();
			castmt = conn.prepareCall("{ call GetCurrentTotalPlayers(?) }");

			//모든 인자에 대한 set 및 out 셋팅
			castmt.registerOutParameter(1, OracleType.NUMBER);

			//실행
			castmt.execute();

			totalPlayers = castmt.getInt(1);

			castmt.close();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return totalPlayers;
	}
	
	public Unit.UnitType  getMyRoll(){
		//return Unit.UnitType.Police1;

		Unit.UnitType  unitType = Unit.UnitType.UnitType_End;

		try {checkCastmt();
			castmt = conn.prepareCall("{ call GetPlayerRole(?, ?) }");

			//모든 인자에 대한 set 및 out 셋팅
			castmt.setInt(1, UserMng.getInstace().getUserId());
			castmt.registerOutParameter(2, OracleType.VARCHAR2);

			//실행
			castmt.execute();

			unitType = Unit.UnitType.values()[Integer.parseInt(castmt.getString(2))];

			castmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return unitType;
	}

	public int getMoveCount() {
		int moveCount = 0;
		try {checkCastmt();
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
		try {checkCastmt();
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
			checkCastmt();
			castmt = conn.prepareCall("{call GetPlayerRoomNodes(?, ?) }");

			castmt.setInt(1, UserMng.getInstace().getUserId());
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
		try {checkCastmt();
			castmt = conn.prepareCall("{ call GetCurrentGameFieldCol(?) }");

			castmt.registerOutParameter(1, OracleTypes.NUMBER);

			castmt.execute();

			colCount = castmt.getInt(1);

			castmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return colCount;
	} 
	
	public void setKill(String setKill) {
		try {checkCastmt();
			castmt = conn.prepareCall("{ call UpdateNodeState(?,?) }");

			//모든 인자에 대한 set 및 out 셋팅
			castmt.setInt(1, UserMng.getInstace().getUserId());
			castmt.setString(2, setKill);
			//실행
			castmt.execute();

			castmt.close();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setKillNode(GameField gameField) {
		try {checkCastmt();
			castmt = conn.prepareCall("{ call GetStateTwoNodes(?, ?) }");

			castmt.setInt(1, UserMng.getInstace().getUserId());
			castmt.registerOutParameter(2, OracleTypes.CURSOR);//crs 받을 것
			
			castmt.execute();
			
			ResultSet rs;
			rs = (ResultSet) castmt.getObject(2);
			gameField.setKillNode(rs);
			
		    rs.close();
		    castmt.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//Turn
	public boolean getMyTurn() {
		boolean myTurn = false;
		try {checkCastmt();
			castmt = conn.prepareCall("{ call GetLastGameTurn(? , ?) }");

			//모든 인자에 대한 set 및 out 셋팅
			castmt.setInt(1, UserMng.getInstace().getUserId());
			castmt.registerOutParameter(2, OracleType.NUMBER);

			//실행
			castmt.execute();

			int turn = castmt.getInt(2);
			int totalPlayers = GameMng.getInstace().getTotalPlayers();
			int myRoll = GameMng.getInstace().getMyRoll();
			if(((turn + totalPlayers * 2) % totalPlayers) == myRoll) {
				myTurn = true;
			}

			castmt.close();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return myTurn;
	}

	public void updateOpponentsNodeAndActionTalk() {

		try {checkCastmt();
			castmt = conn.prepareCall("{ call GetLastGameTurnDetails(?,?,?) }");

			//모든 인자에 대한 set 및 out 셋팅
			castmt.setInt(1, UserMng.getInstace().getUserId());
			castmt.registerOutParameter(2, OracleType.VARCHAR2);
			castmt.registerOutParameter(3, OracleType.VARCHAR2);

			//실행
			castmt.execute();

			GameMng.getInstace().setOpponentsNodeAndActionTalk(castmt.getString(2),castmt.getString(3));

			castmt.close();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void sendTurnData(String moveNodeName, String actionTalk, boolean isSendLast) {
		try {checkCastmt();
			castmt = conn.prepareCall("{ call InsertPlayroomAction(?,?,?,?) }");

			//모든 인자에 대한 set 및 out 셋팅
			castmt.setInt(1, UserMng.getInstace().getUserId());
			castmt.setString(2, moveNodeName);
			if(actionTalk.isEmpty() && isSendLast) {
				castmt.setString(3, TextStore.JackNotUseKill);
			}
			else {
				castmt.setString(3, actionTalk);
			}
			if(isSendLast) {
				castmt.setString(4, "1");
			}
			else {
				castmt.setString(4, "0");
			}
			
			//실행
			castmt.execute();

			castmt.close();

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean inquiryJack(String pos) {
		try {
			castmt = conn.prepareCall("{ call CheckJackPassedNode(?,?,?) }");

			//모든 인자에 대한 set 및 out 셋팅
			castmt.setInt(1, UserMng.getInstace().getUserId());
			castmt.setString(2, pos);
			castmt.registerOutParameter(3, OracleType.NUMBER);
			
			//실행
			castmt.execute();

			int success = castmt.getInt(3);
			
			castmt.close();
			
			if(success == 1) {
				return true;
			}

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public boolean arrestJack(String pos) {
		try {checkCastmt();
			castmt = conn.prepareCall("{ call CheckJackInNode(?,?,?) }");

			//모든 인자에 대한 set 및 out 셋팅
			castmt.setInt(1, UserMng.getInstace().getUserId());
			castmt.setString(2, pos);
			castmt.registerOutParameter(3, OracleType.NUMBER);
			
			//실행
			castmt.execute();

			int success = castmt.getInt(3);
			
			castmt.close();
			
			if(success == 1) {
				return true;
			}

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public boolean endGame() {
		try {checkCastmt();
			castmt = conn.prepareCall("{ call CheckPlayerStateInPlaywait(?,?) }");

			//모든 인자에 대한 set 및 out 셋팅
			castmt.setInt(1, UserMng.getInstace().getUserId());
			castmt.registerOutParameter(2, OracleType.NUMBER);
			
			//실행
			castmt.execute();

			int isPlaying = castmt.getInt(2);
			
			castmt.close();
			
			//게임 중이면 1을 반환한다.
			if(isPlaying == 0) {
				System.out.println("endGame - " + isPlaying);
				return true;
			}

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public boolean qImWinner() {
		try {checkCastmt();
			castmt = conn.prepareCall("{ call CheckPlayerScore(?,?) }");

			//모든 인자에 대한 set 및 out 셋팅
			castmt.setInt(1, UserMng.getInstace().getUserId());
			castmt.registerOutParameter(2, OracleType.NUMBER);
			
			//실행
			castmt.execute();

			int imWin = castmt.getInt(2);
			
			castmt.close();
			
			if(imWin == 1) {
				return true;
			}

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
