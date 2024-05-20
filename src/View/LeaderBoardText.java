package View;

import Controller.ControllerTxt;
import Model.HighScore;
import Model.HighScoreManager;
import Model.Menu;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoardText {
    TerminalHandler terminalHandler;
    ControllerTxt controllerTxt;
    Menu menu;
    boolean changingView = false;
    public LeaderBoardText(TerminalHandler terminalHandler, ControllerTxt controllerTxt){
        this.controllerTxt = controllerTxt;
        List<String> options = new ArrayList<>();
        this.terminalHandler = terminalHandler;
        options.add("Home");
        menu = new Menu(options);
        List<HighScore> highScores = HighScoreManager.getAllScores();
        terminalHandler.PutString(0, 0, "Top 3 Players" , TextColor.ANSI.YELLOW, TextColor.ANSI.BLACK);
        terminalHandler.PutString(0, 2, highScores.get(0).getNickname() + ": " + highScores.get(0).getScore(), TextColor.ANSI.YELLOW, TextColor.ANSI.BLACK);
        terminalHandler.PutString(0, 4, highScores.get(1).getNickname() + ": " + highScores.get(1).getScore(), TextColor.ANSI.YELLOW, TextColor.ANSI.BLACK);
        terminalHandler.PutString(0, 6, highScores.get(2).getNickname() + ": " + highScores.get(2).getScore(), TextColor.ANSI.YELLOW, TextColor.ANSI.BLACK);

        for(int index = 0; index < menu.getSize(); index++){
            RefreshMenu(index, index == menu.getSelected());
        }
        while(!changingView){
            KeyStroke keyStroke = terminalHandler.GetInput();
            if(keyStroke != null){
                KeyStrokeReaction(keyStroke);
            }
            try {
                Thread.sleep(35);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void KeyStrokeReaction(KeyStroke keyStroke){
        if(keyStroke.getKeyType().toString().equals("ArrowDown")){
            RefreshMenu(menu.getSelected(), false);
            menu.nextOption();
            RefreshMenu(menu.getSelected(), true);
        }
        else if(keyStroke.getKeyType().toString().equals("ArrowUp")){
            RefreshMenu(menu.getSelected(), false);
            menu.previousOption();
            RefreshMenu(menu.getSelected(), true);
        }
        else if(keyStroke.getKeyType().toString().equals("Enter")){
            if(menu.selectedEquals("Home")){
                changingView = true;
                controllerTxt.ChangeTargetView(TargetView.MainMenu);
            }
        }
    }
    public void RefreshMenu(int index, boolean selected){
        int shift = 8;
        int line = index * 2;
        if(selected){
            terminalHandler.PutString(0, line + shift, menu.getIndex(index), TextColor.ANSI.RED, TextColor.ANSI.BLACK);
        }
        else{
            terminalHandler.PutString(0, line + shift, menu.getIndex(index), TextColor.ANSI.GREEN, TextColor.ANSI.BLACK);
        }
    }
}
