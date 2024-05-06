package com.Game.LettrFromWH.GameObject.Login;

import com.Game.LettrFromWH.DB.DBMng;
import com.Game.LettrFromWH.GameObject.GameObject;
import com.Game.LettrFromWH.Input.InputMng;
import com.Game.LettrFromWH.Printer.PrintMng;
import com.Game.LettrFromWH.Scene.SceneMng;
import com.Game.LettrFromWH.Text.TextStore;
import com.Game.LettrFromWH.User.UserMng;

public class Login extends GameObject {

	@Override
	public void init() {
		super.init();
	}

	@Override
	public void play() {
		super.play();
		userLogin();
		SceneMng.getInstace().getCurrentScene().getScene().nextScene(SceneMng.SceneType.MainScene);
	}

	@Override
	public void exit() {
		super.exit();
	}
	
	///////////////////////////

	public void userLogin() {
		while (true) {
			if (loginFunc()) {
				break;
			}
		}
	}

	public boolean loginFunc() {
		if (questionHaveAccount()) {
			return loginUserAccount();
		} else {
			return createAccount();
		}
	}

	// 계정이 있다고 유저가 선택하면 true, 아니면 false
	public boolean questionHaveAccount() {
		PrintMng.getInstace().cpl(TextStore.systemTalk + TextStore.qCheckAccount);
		PrintMng.getInstace().pl(TextStore.yesOrNo);
		PrintMng.getInstace().p(TextStore.userInputTalk);

		String userInput = InputMng.getInstace().Input();
		
		if (userInput.equals(TextStore.yes)) {
			return true;
		} else if (userInput.equals(TextStore.no)) {
			return false;
		} else {
			return questionHaveAccount();
		}
	}

	// 유저가 입력한 닉네임이 있으면 유저 아이디를 저장하고 true
	// 없으면 다시 입력 할 것인지 물어봄(reInputNickname)
	// 입력 다시 안한다 하면 false 반환 - 초기로 돌아갈 것 (로그인 함수 다시)
	public boolean loginUserAccount() {
		PrintMng.getInstace().cpl(TextStore.systemTalk + TextStore.rqInputNickname);
		PrintMng.getInstace().p(TextStore.userInputTalk);

		String userInput = InputMng.getInstace().Input();

		if (checkAccount(userInput)) {
			settingUserId(userInput);
			loginSuccess();
			return true;
		}

		return reInputNickname(userInput);
	}

	// 닉네임이 틀렸을 때, 다시 입력할 것인지 물어봄
	// Yes면 loginUserAccount, 아니면 false반환
	public boolean reInputNickname(String nonExistNickname) {
		PrintMng.getInstace().cpl(TextStore.systemTalk + nonExistNickname + TextStore.nonExistNickname);
		PrintMng.getInstace().pl(TextStore.qReInputNickname);
		PrintMng.getInstace().pl(TextStore.yesOrNo);
		PrintMng.getInstace().p(TextStore.userInputTalk);

		String userInput = InputMng.getInstace().Input();

		if (userInput.equals(TextStore.yes)) {
			return loginUserAccount();
		} else if (userInput.equals(TextStore.no)) {
			return false;
		} else {
			return reInputNickname(nonExistNickname);
		}
	}

	// 닉네임 잇으면 true 없으면 fasle
	public boolean checkAccount(String userNickname) {
		if (DBMng.getInstace().checkAccount(userNickname)) {
			return true;
		}

		return false;
	}

	// 닉네임 주면 유저 id 반환
	public void settingUserId(String userNickname) {
		UserMng.getInstace().setUserId(DBMng.getInstace().getUserId(userNickname));
	}

	// 닉네임 받아서 중복 체크 후, 없으면 생성 및 로그인, 있으면 안내 후 재 생성
	public boolean createAccount() {
		PrintMng.getInstace().cpl(TextStore.systemTalk + TextStore.createNickname);
		PrintMng.getInstace().p(TextStore.userInputTalk);

		String userInput = InputMng.getInstace().Input();

		if (checkAccount(userInput)) {
			return createAccount(userInput + TextStore.ExistNickname);
		} else {
			createAccountDB(userInput);
			settingUserId(userInput);
			loginSuccess();
			return true;
		}
	}

	// 아이디 중복이여서 안내 후 생성 요청 -> 빠져나가는 기능은 없음.
	public boolean createAccount(String preTalk) {
		PrintMng.getInstace().cpl(TextStore.systemTalk + preTalk);
		PrintMng.getInstace().pl(TextStore.systemTalk + TextStore.createNickname);
		PrintMng.getInstace().p(TextStore.userInputTalk);

		String userInput = InputMng.getInstace().Input();

		if (checkAccount(userInput)) {
			return createAccount(userInput + TextStore.ExistNickname);
		} else {
			createAccountDB(userInput);
			settingUserId(userInput);
			loginSuccess();
			return true;
		}
	}

	// 계정 생성
	public void createAccountDB(String nickName) {
		DBMng.getInstace().createAccount(nickName);
	}

	// 로그인 성공 메세지
	public void loginSuccess() {
		PrintMng.getInstace().cpl(TextStore.systemTalk + DBMng.getInstace().getUserNickname() + TextStore.loginSuccess);
	}

}
