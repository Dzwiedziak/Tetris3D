package View;

import Controller.ControllerTxt;
import Model.Menu;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;

import java.util.ArrayList;
import java.util.List;

public class MainMenuText{
    TerminalHandler terminalHandler;
    ControllerTxt controllerTxt;
    Menu menu;
    boolean changingView = false;
    public MainMenuText(TerminalHandler terminalHandler, ControllerTxt controllerTxt){
        this.controllerTxt = controllerTxt;
        List<String> options = new ArrayList<>();
        this.terminalHandler = terminalHandler;
        options.add("Play");
        options.add("LeaderBoard");
        options.add("Exit");
        menu = new Menu(options);

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
            if(menu.selectedEquals("Play")){
                changingView = true;
                controllerTxt.ChangeTargetView(TargetView.Game);
            }
            else if(menu.selectedEquals("LeaderBoard")){
                changingView = true;
                controllerTxt.ChangeTargetView(TargetView.LeaderBoard);
            }
            else if(menu.selectedEquals("Exit")){
                changingView = true;
                controllerTxt.ChangeTargetView(TargetView.Exit);
            }
        }
    }
    public void RefreshMenu(int index, boolean selected){
        int line = index * 2;
        if(selected){
            terminalHandler.PutString(0, line, menu.getIndex(index), TextColor.ANSI.RED, TextColor.ANSI.BLACK);
        }
        else{
            terminalHandler.PutString(0, line, menu.getIndex(index), TextColor.ANSI.GREEN, TextColor.ANSI.BLACK);
        }
    }

}
