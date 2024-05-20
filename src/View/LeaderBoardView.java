package View;

import Controller.ControllerGraphics;
import Model.HighScore;
import Model.HighScoreManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LeaderBoardView extends JLabelView{
    ControllerGraphics controllerGraphics;
    public LeaderBoardView(JFrame jFrame, ControllerGraphics controllerGraphics){
        super(jFrame, "resources/leaderboardview.jpg");
        this.controllerGraphics = controllerGraphics;
        List<HighScore> highScores = HighScoreManager.getAllScores();
        Font bigFont = new Font("Arial", Font.BOLD, 24);
        Font smallFont = new Font("Arial", Font.BOLD, 18);
        addText(highScores.get(1).getNickname(), smallFont, 95, 290, 100, 100, Color.green);
        addText(highScores.get(0).getNickname(), bigFont, 200, 160, 100, 100, Color.green);
        addText(highScores.get(2).getNickname(), smallFont, 320, 290, 100, 100, Color.green);

        addText(String.valueOf(highScores.get(1).getScore()), smallFont, 115, 325, 100, 100, Color.green);
        addText(String.valueOf(highScores.get(0).getScore()), smallFont, 225, 200, 100, 100, Color.green);
        addText(String.valueOf(highScores.get(2).getScore()), smallFont, 340, 325, 100, 100, Color.green);

        addButton("resources/back.png", 190, 380, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerGraphics.ShowMainMenu();
            }
        });
        setVisible(true);
    }



}
