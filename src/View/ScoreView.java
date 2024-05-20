package View;

import Controller.ControllerGraphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScoreView extends JLabelView{
    public ScoreView(JFrame jFrame, ControllerGraphics controllerGraphics, int score){
        super(jFrame, "resources/scoreview.png");
        Font smallFont = new Font("Arial", Font.BOLD, 18);
        addText(String.valueOf(score), smallFont, 220, 100, 100, 100, Color.green);

        addButton("resources/home.png", 180, 240, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerGraphics.toGameStopped = true;
            }
        });
        setVisible(true);
    }

}
