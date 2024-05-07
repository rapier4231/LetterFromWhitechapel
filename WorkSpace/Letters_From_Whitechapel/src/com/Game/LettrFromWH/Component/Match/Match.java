package com.Game.LettrFromWH.Component.Match;

import com.Game.LettrFromWH.Component.Component;
import com.Game.LettrFromWH.DB.DBMng;
import com.Game.LettrFromWH.Input.InputMng;
import com.Game.LettrFromWH.Printer.PrintMng;
import com.Game.LettrFromWH.Text.TextStore;
import com.Game.LettrFromWH.Time.TimeMng;

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
        TimeMng.getInstace().stopCounting(countingKey);
    }

    /////////////////////////////////////////////////////

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

    public void startGame(){
        stopThread();
    }

    private void waitForMatchingView(){
        PrintMng.getInstace().cpl(TextStore.waitForMatching);
    }

    private void matchCounting(){
        TimeMng.getInstace().startCounting(countingKey,10.f);
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

    }

    private void cancelMatching(){
        DBMng.getInstace().cancelMatching();
        stopThread();
    }

    private void startThread(){
        matchThread.start();
    }

    private void stopThread(){
        matchThread.setRunThread(false);
    }
}
