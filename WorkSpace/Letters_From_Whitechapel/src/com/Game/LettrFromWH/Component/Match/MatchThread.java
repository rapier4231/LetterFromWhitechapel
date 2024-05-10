package com.Game.LettrFromWH.Component.Match;

import com.Game.LettrFromWH.DB.DBMng;
import com.Game.LettrFromWH.MyThread.MyThread;
import com.Game.LettrFromWH.Time.TimeMng;

public class MatchThread extends MyThread {

    public MatchThread(Match match, String countingKey){
        this.match = match;
        this.countingKey = countingKey;
    }

    private final Match match;

    private final String countingKey;
    
    private int state;

    //Thread//////////////////////////////////////////////////

    public void run() {
        while (match.getRunThread()){
        	matchingFunc();
        	delayS(0.3f);
        }
    }

    ///////////////////////////////////////////////////////////

    private void matchingFunc() {
    	//state = DBMng.getInstace().getWaitState();
    	state = 2;
    	
    	if(match == null) {
    		return;
    	}
    	
        switch(state) {
            //Wait Table에 없음
            case 0:
                match.changeMatchingState(Match.MatchingState.WaitForMatching);
                startMatching();
                break;
            //매칭 대기 중
            case 1:
                match.changeMatchingState(Match.MatchingState.WaitForMatching);
                waitMatching();
                break;
            //매칭 되었음. 수락 요청을 내보낼 것.
            case 2:
                match.changeMatchingState(Match.MatchingState.MatchingAgree);
                successMatchingCheking();
                break;
            //상대의 수락 요청을 기다리는 중 (나는 수락 했음)
            case 3:
                match.changeMatchingState(Match.MatchingState.WaitForOpponent);
                successMatchingCheking();
                break;
            default:
                break;
        }
    }

    private void startMatching(){
        DBMng.getInstace().startMatching();
    }

    private void waitMatching(){
        DBMng.getInstace().updateMatching();
    }

    private void successMatchingCheking(){
        if(TimeMng.getInstace().checkCounting(countingKey)){
        	if(state == 2) {
        		//match.changeMatchingState(Match.MatchingState.CancelMatching); 
        		match.changeMatchingState(Match.MatchingState.QSuccessMatching);        		
        	}
        	else if (state == 3) {
        		match.changeMatchingState(Match.MatchingState.QSuccessMatching);        		
        	}
        	else {
        		System.out.println("successMatchingCheking 에러 - 2도 3도 아님");
        	}
        	
        }
    }
}
