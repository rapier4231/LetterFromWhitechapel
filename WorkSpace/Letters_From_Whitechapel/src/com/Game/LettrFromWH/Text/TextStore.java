package com.Game.LettrFromWH.Text;

public class TextStore {
	
	//Basic//
	public static String gameName 		= "LetterFromWH";
	public static String systemTalk 	= "System : ";
	public static String userInputTalk 	= "입력 : ";
	public static String yesOrNo = "1.네 2.아니요";
	public static String yes = "1";
	public static String no = "2";
	public static String wrongInput = "잘못 입력하셨습니다.";
	public static String dividingLine = "================================";
	
	//Start//
	public static String hi = "만나서 반갑습니다. 잠시만 기다려주세요.";
	public static String startServerConnection = "서버 연결 시작";
	public static String readServerConnectData = "서버 정보 확인";
	public static String tryServerConnection = "서버 연결 시도";
	public static String successServerConnection = "서버 연결 성공";
	public static String reTryServerConnection = "일정 시간 연결이 되지 않아, 재시도 합니다.";
	
	//client//
	public static String version = "0.0.0";
	public static String versionCheckFalied = "최신 버전을 다운로드 받아주세요.";
	public static String clientVersion = "Client version : ";
	
	//Login//
	public static String qCheckAccount = "계정이 있으십니까?";
	public static String rqInputNickname = "닉네임을 입력해주세요.";
	public static String nonExistNickname = " (이)라는 닉네임이 존재하지 않습니다.";
	public static String qReInputNickname = "다시 입력하시겠습니까?";
	public static String createNickname = "생성하실 닉네임을 입력해주세요. (띄어쓰기는 불가능 합니다.)";
	public static String ExistNickname = " (이)라는 닉네임이 존재합니다.";
	public static String loginSuccess = "님 환영합니다.";
	
	//Main//
	public static String accessNickName = "이름 : ";
	public static String mainMenu = "1.매칭 시작 2.로그 아웃 3.게임 종료";

	//Matching//
	public static String waitForMatching = "매칭 중입니다. 잠시만 기다려 주세요.";
	public static String qAgreeMatching = "매칭에 성공하였습니다. 수락하시겠습니까?";
	public static String waitForOpponent = "상대방을 기다리는 중입니다.";
	public static String reMatch = "상대방이 게임을 수락하지 않았습니다.\n잠시후 매칭이 재시작 됩니다.";
	public static String matchingSuccess = "게임 방을 생성 중입니다. 잠시만 기다려주세요.";
	
	//Game//
	public static String trunTalk = "Turn : ";
	public static String actionTalk = "Action : ";
	public static String GameDividingLine = "=================================================================";

	public static String MoveUserUnit = "당신의 캐릭터를 움직여 주세요.";
	public static String UseJackAbility = "살인을 하시겠습니까? (1.네 2.아니요)";
	public static String JackAbility = "살인 즉시 경찰에게 즉시 위치를 들키게 됩니다";
	public static String UsePoliceAbility = "어떤 능력을 사용하시겠습니까? (1.탐문 2.체포)";
	public static String PoliceAbility = "[탐문 : 잭이 지나간 자리인지 확인] [체포 : 현재 위치에 잭이 있을 경우 체포 성공])";
	public static String WaitMyTurn = "상대방 차례입니다.";
	public static String JackUseKill = "어젯밤, 잭은 살인을 저질렀습니다.";
	public static String JackNotUseKill = "어젯밤, 아무일도 일어나지 않았습니다.";
	public static String PoliceInquiryFailed = "경찰이 잭의 흔적을 찾지 못하였습니다.";
	public static String PoliceInquirySucess = "경찰이 잭을 본 사람과 만났습니다.";
	public static String PoliceArrestFailed = "경찰이 잭을 잡으려 시도했으나, 실패하고 말았습니다.";
	public static String PoliceArrestSucess = "경찰이 잭을 잡는데 성공하였습니다.";
	public static String Inquiry = "1";
	public static String Arrest = "2";
	
	public static String MoveJackUnit(int moveCount){
		return "잭은 움직이지 않거나, " + moveCount + "칸 이내로 이동할 수 있습니다. (입력 예시 : a2, b2 [또는] NoMove)";
	}
	public static String MovePoliceUnit(int moveCount){
		return "경찰은 움직이지 않거나, " + moveCount + "칸 이내로 이동할 수 있습니다. (입력 예시 : a2, b2, b3 [또는] NoMove)";
	}
	
	//Exit//
	public static String Bye = "다음에 다시 뵙기를 기대하겠습니다.";
	
}
