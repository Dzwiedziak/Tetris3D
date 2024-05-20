package View;

import Controller.ControllerGraphics;
import Model.HighScore;
import Model.HighScoreManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class HelpView extends JLabelView{
    ControllerGraphics controllerGraphics;
    public HelpView(JFrame jFrame, ControllerGraphics controllerGraphics){
        super(jFrame, "resources/mouse.png");
        this.controllerGraphics = controllerGraphics;

        addButton("resources/back.png", 350, 380, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerGraphics.ShowMainMenu();
            }
        });
        setVisible(true);
    }



}
