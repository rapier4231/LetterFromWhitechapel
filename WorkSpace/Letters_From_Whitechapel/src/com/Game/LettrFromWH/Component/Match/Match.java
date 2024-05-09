package com.Game.LettrFromWH.Component.Match;

import com.Game.LettrFromWH.Component.Component;
import com.Game.LettrFromWH.DB.DBMng;
import com.Game.LettrFromWH.GameObject.Matching.Matching;
import com.Game.LettrFromWH.Input.InputMng;
import com.Game.LettrFromWH.Printer.PrintMng;
import com.Game.LettrFromWH.Text.TextStore;
import com.Game.LettrFromWH.Time.TimeMng;

public class Match extends Component {
	
    public enum MatchingState{
        WaitForMatching,
        MatchingAgree,
        WaitForOpponent,
        MatchingSuccess,
        ReMatch,
        MatchingState_End
    }

    private MatchingState matchingState = MatchingState.MatchingState_End;

    private final String countingKey = "Match";

    private final MatchThread matchThread = new MatchThread(this, countingKey);

    public void finishMatching(){
        stopThread();
    }
    
	public void qSuccessMatching() {
		finishMatching();
//    	if(DBMng.getInstace().qSuccessMatching()) {
//    		changeMatchingState(MatchingState.MatchingSuccess);
//    		((Matching)getGameObject()).mathingSuccess();
//    	}
//    	else {
//    		changeMatchingState(MatchingState.ReMatch);
//    	}
		
		//임시
		changeMatchingState(MatchingState.MatchingSuccess);
		((Matching)getGameObject()).mathingSuccess();
	}
    
    @Override
    public void init() {
    	super.init();
        startThread();
    }
    
    private void startThread(){
        matchThread.start();
    }

    @Override
    public void exit() {
    	super.exit();
        stopThread();
        TimeMng.getInstace().stopCounting(countingKey);
    }
    
    private void stopThread(){
        matchThread.setRunThread(false);
    }
    
    public void changeMatchingState(MatchingState matchingState){
        if(this.matchingState == matchingState){
            return;
        }

        switch (matchingState){
            case WaitForMatching :
                waitForMatchingView();
                break;
            case MatchingAgree :
                matchCounting();
                qAgreeMatchingView();
                break;
            case WaitForOpponent :
                waitForOpponentView();
                break;
            case MatchingSuccess :
                matchingSuccessView();
                break;
            case ReMatch:
                reMatchView();
                break;
            case MatchingState_End :
                break;
            default :
                break;
        }

        this.matchingState = matchingState;
    }
    
    public void cancelMatching(){
        DBMng.getInstace().cancelMatching();
        stopThread();
        ((Matching)getGameObject()).mathingCancel();
    }

    private void waitForMatchingView(){
        PrintMng.getInstace().cpl(TextStore.waitForMatching);
    }

    private void matchCounting(){
        TimeMng.getInstace().startCounting(countingKey,DBMng.getInstace().getMatchingWaitTime());
    }

    private void qAgreeMatchingView(){
        PrintMng.getInstace().cpl(TextStore.qAgreeMatching);
        PrintMng.getInstace().pl(TextStore.yesOrNo);
        PrintMng.getInstace().p(TextStore.userInputTalk);

        String userInput = InputMng.getInstace().Input();

        if(userInput.equals(TextStore.yes)){
            agreeMatching();
        }
        else if(userInput.equals(TextStore.no)){
            cancelMatching();
        }
        else{
            qAgreeMatchingView();
        }
    }

    private void waitForOpponentView(){
        PrintMng.getInstace().cpl(TextStore.waitForOpponent);
    }

    private void matchingSuccessView(){
        PrintMng.getInstace().cpl(TextStore.matchingSuccess);
    }

    private void agreeMatching(){
        DBMng.getInstace().agreeMatching();
    }
    
    private void reMatchView() {
    	PrintMng.getInstace().cpl(TextStore.reMatch);
    	TimeMng.getInstace().delayS(3);
    	startThread();
    }
}
