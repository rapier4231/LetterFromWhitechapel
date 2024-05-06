package com.Game.LettrFromWH.Component.Match;

import com.Game.LettrFromWH.DB.DBMng;

public class MatchThread extends Thread {

    public MatchThread(Match match){
        this.match = match;
    }

    private final Match match;

    private boolean runThread = true;

    public void setRunThread(boolean runThread){
        this.runThread = runThread;
    }

    //Thread//////////////////////////////////////////////////

    public void run() {
        while (runThread){
            matchingFunc();
        };
    }

    ///////////////////////////////////////////////////////////

    public void matchingFunc() {
        switch(DBMng.getInstace().getWaitState()) {
            //Wait Table에 없음
            case -1:
                match.changeMatchingState(Match.MatchingState.WaitForMatching);
                startMatching();
                break;
            //매칭 대기 중
            case 0:
                match.changeMatchingState(Match.MatchingState.WaitForMatching);
                waitMatching();
                break;
            //매칭 되었음. 수락 요청을 내보낼 것.
            case 1:
                match.changeMatchingState(Match.MatchingState.MatchingAgree);
                break;
            //상대의 수락 요청을 기다리는 중 (나는 수락 했음)
            case 2:
                match.changeMatchingState(Match.MatchingState.WaitForOpponent);
                break;
            //상대도 수락했으니, 게임을 시작할 것. (게임 방에 내가 있음)
            case 3:
                match.changeMatchingState(Match.MatchingState.MatchingSuccess);
                startGame();
                runThread = false;
                break;
            default:
                runThread = false;
                break;
        }
    }

    public void startMatching(){
        DBMng.getInstace().startMatching();
    }

    public void waitMatching(){
        DBMng.getInstace().updateMatching();
    }

    public void startGame(){
        match.startGame();
    }

}
