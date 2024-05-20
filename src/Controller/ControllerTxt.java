package Controller;

import Model.HighScore;
import Model.HighScoreManager;
import View.*;

import java.util.ArrayList;
import java.util.List;

public class ControllerTxt extends Controller{
    TerminalHandler terminalHandler;
    TargetView targetView;
    String nickname = "";
    public ControllerTxt(){
        super();
        terminalHandler = TerminalHandler.getInstance();
        Thread thread = new Thread(terminalHandler);
        thread.start();
        targetView = TargetView.InputNick;
        InputNick();
    }

    @Override
    public void ShowMainMenu() {
        terminalHandler.Clear();
        MainMenuText mainMenuText = new MainMenuText(terminalHandler, this);
        if(targetView.equals(TargetView.Game)){
            StartGame();
        }
        else if(targetView.equals(TargetView.LeaderBoard)){
            ShowLeaderBoard();
        }
        else if(targetView.equals(TargetView.Exit)){
            GameStopped();
        }
    }
    public void InputNick(){
        terminalHandler.Clear();
        InputNickText inputNickText = new InputNickText(terminalHandler, this);
        this.nickname = inputNickText.getNickname();
        ShowMainMenu();
    }
    @Override
    public void ShowLeaderBoard() {
        terminalHandler.Clear();
        LeaderBoardText leaderBoardText = new LeaderBoardText(terminalHandler,this);
        if(targetView.equals(TargetView.MainMenu)){
            ShowMainMenu();
        }
    }

    @Override
    public void GameStopped() {
        System.exit(0);
    }

    @Override
    public void StartGame() {
        terminalHandler.Clear();
        gameController3D = new GameController3D(10,10, 1, this);
        GameText gameText = new GameText(terminalHandler, (ControllerTxt) this, gameController3D);
        Thread thread = new Thread(gameText);
        thread.start();
        gameController3D.start(gameText);
        if(targetView.equals(TargetView.Score)){
            GameEnded();
        }
    }

    @Override
    public void GameEnded() {
        int score = gameController3D.getScore();
        List<HighScore> scores = new ArrayList<>();
        scores.add(new HighScore(nickname,score));
        HighScoreManager.updateScores(scores);
        terminalHandler.Clear();
        ScoreText scoreText = new ScoreText(terminalHandler, this, score);
        if(targetView.equals(TargetView.Game)){
            StartGame();
        }
        else if(targetView.equals(TargetView.MainMenu)){
            ShowMainMenu();
        }
    }
    public void ChangeTargetView(TargetView targetView){
        this.targetView = targetView;
    }
    public void setNickname(String nickname){
        this.nickname = nickname;
    }
}
