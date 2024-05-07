package com.Game.LettrFromWH.DB;

import java.sql.Connection;
import java.sql.SQLException;

import com.Game.LettrFromWH.Printer.PrintMng;
import com.Game.LettrFromWH.Text.TextStore;
import com.Game.LettrFromWH.Time.TimeMng;

public class DBConnection {

	private DBConnection() {
	}
	private static Connection conn;
	private static final float reConnectSeconds = 3.5f;

	public static void connect(){
		while (conn == null) {
			new DBConnectionThread().start();
			TimeMng.getInstace().delayS(reConnectSeconds);
			if(conn == null) {
				PrintMng.getInstace().pl(TextStore.systemTalk + TextStore.reTryServerConnection);
			}
		}
	}

	public static void startConnection() {
		PrintMng.getInstace().pl(TextStore.systemTalk + TextStore.startServerConnection);
	}

	public static void readData() {
		PrintMng.getInstace().pl(TextStore.systemTalk + TextStore.readServerConnectData);
	}

	public static void tryConnection() {
		PrintMng.getInstace().pl(TextStore.systemTalk + TextStore.tryServerConnection);
	}

	public static void successConnection(Connection conn) {
		if(DBConnection.conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
//				e.printStackTrace();
			}
			return;
		}
		DBConnection.conn = conn;
		PrintMng.getInstace().pl(TextStore.systemTalk + TextStore.successServerConnection);
	}
	
	public static Connection getConnection() {
		return conn;
	}

}
