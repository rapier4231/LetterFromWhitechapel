package com.Game.LettrFromWH.DB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.Game.LettrFromWH.Printer.PrintMng;
import com.Game.LettrFromWH.Text.TextStore;
import com.Game.LettrFromWH.User.UserMng;

import oracle.jdbc.OracleType;

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
			
			System.out.println("Now Version Checking...");
			
			//실행
			castmt.execute();
			
			//결과물 가져오기
			version = (String)castmt.getObject(1);
			
		    castmt.close();

		}
		catch (SQLException e) {
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
		try {
			castmt = conn.prepareCall("{ call GetPlayerState(?,?) }");
			
			//모든 인자에 대한 set 및 out 셋팅
			castmt.setInt(1, UserMng.getInstace().getUserId());
			castmt.registerOutParameter(2, OracleType.VARCHAR2);

			//실행
			castmt.execute();

			String str = castmt.getString(2);
			int waitState = Integer.MAX_VALUE;
			if(str.equals("Player not found")){
				waitState = -1;
			}
			else if(str.equals("Multiple players found")){
				System.out.println("매칭 Multiple players found");
			}
			else{
				waitState = Integer.parseInt(str);
			}

			castmt.close();

			return waitState;

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}

	public boolean startMatching(){
		try {
			castmt = conn.prepareCall("{ call InsertIntoPlayWait(?) }");

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
			castmt = conn.prepareCall("{ call UpdatePlayWaitUpdateTime(?) }");

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
			castmt = conn.prepareCall("{ call UpdatePlayerStateTo2(?) }");

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

	public boolean cancelMatching(){
		try {
			castmt = conn.prepareCall("{ call CancelMatch(?,?) }");

			//모든 인자에 대한 set 및 out 셋팅
			castmt.setInt(1, UserMng.getInstace().getUserId());
			castmt.registerOutParameter(2, OracleType.VARCHAR2);

			//실행
			castmt.execute();

			String userError = castmt.getString(2);

			castmt.close();

			if(userError.isEmpty()){
				System.out.println(userError);
				return false;
			}

			return true;

		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
}
