package View;

import Controller.ControllerTxt;
import Model.Menu;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;

import java.util.ArrayList;
import java.util.List;

public class InputNickText{
    TerminalHandler terminalHandler;
    ControllerTxt controllerTxt;
    Menu menu;
    boolean changingView = false;

    public String getNickname() {
        return nickname;
    }

    String nickname = "";
    public InputNickText(TerminalHandler terminalHandler, ControllerTxt controllerTxt){
        this.controllerTxt = controllerTxt;
        List<String> options = new ArrayList<>();
        this.terminalHandler = terminalHandler;
        options.add("Accept");
        menu = new Menu(options);
        RefreshNickname();

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
            if(menu.selectedEquals("Accept")){
                changingView = true;
                controllerTxt.ChangeTargetView(TargetView.MainMenu);
            }
        }
        else if(keyStroke.getKeyType().toString().equals("Character")){
            nickname = nickname + keyStroke.getCharacter();
            RefreshNickname();
        }
        else if(keyStroke.getKeyType().toString().equals("Backspace")){
            if(!nickname.isEmpty()){
                nickname = nickname.substring(0,nickname.length()-1);
                RefreshNickname();
            }
        }
    }
    public void RefreshMenu(int index, boolean selected){
        int shift = 3;
        int line = index * 2;
        if(selected){
            terminalHandler.PutString(0, line + shift, menu.getIndex(index), TextColor.ANSI.RED, TextColor.ANSI.BLACK);
        }
        else{
            terminalHandler.PutString(0, line + shift, menu.getIndex(index), TextColor.ANSI.GREEN, TextColor.ANSI.BLACK);
        }
    }
    public void RefreshNickname(){
        terminalHandler.PutString(0, 0, createSpaceString(nickname.length() + 1), TextColor.ANSI.WHITE, TextColor.ANSI.BLACK);
        terminalHandler.PutString(0, 0, nickname, TextColor.ANSI.WHITE, TextColor.ANSI.BLACK);
        terminalHandler.PutString(nickname.length(), 0, "_", TextColor.ANSI.WHITE, TextColor.ANSI.BLACK);
    }
    public String createSpaceString(int length) {
        StringBuilder spaces = new StringBuilder();
        for (int i = 0; i < length; i++) {
            spaces.append(" ");
        }
        return spaces.toString();
    }
}
