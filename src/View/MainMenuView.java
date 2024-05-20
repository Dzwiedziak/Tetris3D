package View;

import Controller.ControllerGraphics;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuView extends JLabelView{
    ControllerGraphics controllerGraphics;
    public MainMenuView(JFrame jFrame, ControllerGraphics controllerGraphics){
        super(jFrame,"resources/tetris.jpg");
        addButton("resources/play.png", 170, 275, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerGraphics.toStartGame = true;
                //controller.StartGame();
            }
        });
        addButton("resources/leaderboard.png", 140, 340, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerGraphics.ShowLeaderBoard();
            }
        });
        addButton("resources/exit.png", 170, 400, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerGraphics.Exit();
            }
        });
        addButton("resources/info.png", 450, 425, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controllerGraphics.ShowHelp();
            }
        });
        addTextLabel(180, 220, controllerGraphics.nickname, new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    controllerGraphics.nickname = e.getDocument().getText(0,e.getDocument().getLength());
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    controllerGraphics.nickname = e.getDocument().getText(0,e.getDocument().getLength());
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    controllerGraphics.nickname = e.getDocument().getText(0,e.getDocument().getLength());
                } catch (BadLocationException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        setVisible(true);
    }
}
