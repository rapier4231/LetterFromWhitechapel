package com.Game.LettrFromWH.Component.Match;

import com.Game.LettrFromWH.Component.Component;
import com.Game.LettrFromWH.DB.DBMng;
import com.Game.LettrFromWH.Input.InputMng;
import com.Game.LettrFromWH.Printer.PrintMng;
import com.Game.LettrFromWH.Text.TextStore;

public class Match extends Component {
    @Override
    public void init() {
        startThread();
    }

    @Override
    public void play() {

    }

    @Override
    public void exit() {
        stopThread();
    }

    /////////////////////////////////////////////////////

    public enum MatchingState{
        WaitForMatching,
        MatchingAgree,
        WaitForOpponent,
        MatchingSuccess,
        MatchingState_End;
    }

    private MatchingState matchingState = MatchingState.MatchingState_End;
    private final MatchThread matchThread = new MatchThread(this);

    public void changeMatchingState(MatchingState matchingState){
        if(this.matchingState == matchingState){
            return;
        }

        switch (matchingState){
            case WaitForMatching :
                waitForMatchingView();
                break;
            case MatchingAgree :
                qAgreeMatchingView();
                break;
            case WaitForOpponent :
                waitForOpponentView();
                break;
            case MatchingSuccess :
                matchingSuccessView();
                break;
            case MatchingState_End :
                break;
            default :
                break;
        }

        this.matchingState = matchingState;
    }

    public void waitForMatchingView(){
        PrintMng.getInstace().cpl(TextStore.waitForMatching);
    }

    public void qAgreeMatchingView(){
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

    public void waitForOpponentView(){
        PrintMng.getInstace().cpl(TextStore.waitForOpponent);
    }

    public void matchingSuccessView(){
        PrintMng.getInstace().cpl(TextStore.matchingSuccess);
    }

    public void agreeMatching(){
        DBMng.getInstace().agreeMatching();
    }

    public void cancelMatching(){
        DBMng.getInstace().cancelMatching();
        stopThread();
    }

    public void startGame(){
        stopThread();
    }

    public void startThread(){
        matchThread.start();
    }

    public void stopThread(){
        matchThread.setRunThread(false);
    }
}
