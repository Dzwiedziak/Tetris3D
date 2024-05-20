package Controller;

import Model.ActionEnum;
import Model.HighScore;
import Model.HighScoreManager;
import Model.Point3D;
import View.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ControllerGraphics extends Controller{
    public JFrame jFrame;
    public boolean toStartGame = false;
    public boolean toGameStopped = false;

    public ControllerGraphics(){
        super();
        jFrame = new JFrame();
        jFrame.setSize(500,500);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainMenuView mainMenuView = new MainMenuView(jFrame, this);
        while(true){
            if(toStartGame){
                toStartGame = false;
                StartGame();
            }
            if(toGameStopped){
                toGameStopped = false;
                GameStopped();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public void GameEnded(){
        int score = gameController3D.getScore();

        List<HighScore> newHighScores = new ArrayList<>();
        newHighScores.add(new HighScore(nickname, score));
        HighScoreManager.updateScores(newHighScores);

        gameController3D.stop();
        jFrame.dispose();
        System.gc();
        jFrame = new JFrame();
        jFrame.setSize(500,500);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ScoreView scoreView = new ScoreView(jFrame, this, score);
    }
    public void StartGame(){
        jFrame.dispose();
        System.gc();
        jFrame = new JFrame();
        jFrame.setSize(500,500);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameController3D = new GameController3D(10,5,5, this);
        List<Point3D> cubes = gameController3D.getTetrisMap();

        GameView gameView = new GameView(jFrame, cubes, gameController3D.getWidth(), gameController3D.getHeigth(), gameController3D.getDepth(), this, gameController3D);
        Thread gameViewThread = new Thread(gameView);
        gameViewThread.start();

        gameController3D.start(gameView);
    }
    public void GameStopped(){
        jFrame.getContentPane().removeAll();
        MainMenuView mainMenuView = new MainMenuView(jFrame, this);
    }
    public void ShowLeaderBoard(){
        jFrame.getContentPane().removeAll();
        LeaderBoardView leaderBoardView = new LeaderBoardView(jFrame, this);
    }
    public void ShowHelp(){
        jFrame.getContentPane().removeAll();
        HelpView helpView = new HelpView(jFrame, this);
    }
    public void ShowMainMenu(){
        jFrame.getContentPane().removeAll();
        MainMenuView mainMenuView = new MainMenuView(jFrame, this);
    }
}
