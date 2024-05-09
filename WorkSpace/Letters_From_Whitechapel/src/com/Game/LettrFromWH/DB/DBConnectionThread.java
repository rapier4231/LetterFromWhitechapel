package com.Game.LettrFromWH.DB;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.Game.LettrFromWH.MyThread.MyThread;
import com.Game.LettrFromWH.Printer.PrintMng;
import com.Game.LettrFromWH.Text.TextStore;

public class DBConnectionThread extends MyThread {
	private Connection conn;
	
	public Connection getConnection() {return conn;}
	
	@Override
	public void run() {
		super.run();
		
		PrintMng.getInstace().pl(TextStore.dividingLine);
		DBConnection.startConnection();
		
		// 환경설정 파일을 읽어오기 위한 객체 생성
		Properties properties  = new Properties();
		Reader reader;
		try {
			reader = new FileReader("lib/oracle.properties");  // 읽어올 파일 지정
			properties.load(reader);                           // 설정 파일 로딩하기
		} catch (FileNotFoundException e1) {
			System.out.println("예외: 지정한 파일을 찾을수없습니다 :" + e1.getMessage());
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		DBConnection.readData();

		String driverName = properties.getProperty("driver");
		String url = properties.getProperty("url");
		String user = properties.getProperty("user");
		String pwd = properties.getProperty("password");
		
		DBConnection.tryConnection();
		
		try {
			Class.forName(driverName); // oracle driver를 메모리에 로딩시켜줌 //임포트
			conn = DriverManager.getConnection(url, user, pwd);
			DBConnection.successConnection(conn);
		} catch (ClassNotFoundException e) {
			System.out.println("예외: 드라이버로드 실패 :" + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("예외: connection fail :" + e.getMessage());
			e.printStackTrace();
		}

	}
	
}
