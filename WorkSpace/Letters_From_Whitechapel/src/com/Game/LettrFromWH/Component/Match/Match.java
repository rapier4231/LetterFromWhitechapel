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
        CancelMatching,
        QSuccessMatching,
        MatchingSuccess,
        ReMatch,
        MatchingState_End
    }

    private MatchingState matchingState = MatchingState.MatchingState_End;

    private boolean isChangeMatchingState = false;
    
    private final String countingKey = "Match";

    private final MatchThread matchThread = new MatchThread(this, countingKey);

    private boolean runThread = false;
    
    public boolean getRunThread() {return runThread;}
    
    public void changeMatchingState(MatchingState matchingState) {
    	if(this.matchingState == matchingState) {
    		return;
    	}
    	this.matchingState = matchingState;
    	isChangeMatchingState = true;
    }
    
    private void startThread(){
    	runThread = true;
    	matchThread.start();
    }
    
    private void stopThread(){
    	runThread = false;
    	safeThreadEnd();
    }
    
	private void safeThreadEnd() {
		while(matchThread.isAlive()) {
			TimeMng.getInstace().delayS(0.3f);
		}
	}
    
	public void qSuccessMatching() {
		stopThread();
//    	if(DBMng.getInstace().qSuccessMatching()) {
//    		changeMatchingState(MatchingState.MatchingSuccess);
//    		((Matching)getGameObject()).mathingSuccess();
//    	}
//    	else {
//    		changeMatchingState(MatchingState.ReMatch);
//    	}
		
		//임시
		changeMatchingState(MatchingState.MatchingSuccess);
	}
	
    @Override
    public void init() {
    	super.init();
    	//초기화
    	DBMng.getInstace().cancelMatching();
        startThread();
    }
    
    public void play() {
    	super.play();
    	checkChangeMatchingState();
    }

    @Override
    public void exit() {
    	super.exit();
        stopThread();
        TimeMng.getInstace().stopCounting(countingKey);
    }
       
    public void checkChangeMatchingState(){
        if(!isChangeMatchingState){
            return;
        }
        
        isChangeMatchingState = false;

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
            case CancelMatching :
            	cancelMatching();
                break;
            case QSuccessMatching :
            	qSuccessMatching();
                break;
            case MatchingSuccess :
        		((Matching)getGameObject()).mathingSuccess();
                break;
            case ReMatch:
                reMatchView();
                break;
            case MatchingState_End :
                break;
            default :
                break;
        }

    }
    
    public void cancelMatching(){
    	stopThread();
    	safeThreadEnd();
        DBMng.getInstace().cancelMatching();
        ((Matching)getGameObject()).mathingCancel();
    }

    private void waitForMatchingView(){
        PrintMng.getInstace().cpl(TextStore.waitForMatching);
    }

    private void matchCounting(){
        //TimeMng.getInstace().startCounting(countingKey,DBMng.getInstace().getMatchingWaitTime());
        TimeMng.getInstace().startCounting(countingKey,3);
    }

    private void qAgreeMatchingView(){
        PrintMng.getInstace().cpl(TextStore.qAgreeMatching);
        PrintMng.getInstace().pl(TextStore.yesOrNo);
        PrintMng.getInstace().p(TextStore.userInputTalk);

//        String userInput = InputMng.getInstace().Input();
//
//        if(userInput.equals(TextStore.yes)){
//            agreeMatching();
//        }
//        else if(userInput.equals(TextStore.no)){
//            cancelMatching();
//        }
//        else{
//            qAgreeMatchingView();
//        }
    }

    private void waitForOpponentView(){
        PrintMng.getInstace().cpl(TextStore.waitForOpponent);
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
