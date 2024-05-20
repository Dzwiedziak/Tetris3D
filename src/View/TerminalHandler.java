package View;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;

import java.awt.*;
import java.io.IOException;

public class TerminalHandler implements Runnable{
    private static TerminalHandler instance;
    private Terminal terminal;
    private Screen screen;
    private TextGraphics textGraphics;
    private KeyStroke keyStroke;
    public static TerminalHandler getInstance() {
        if (instance == null) {
            instance = new TerminalHandler();
        }
        return instance;
    }
    private TerminalHandler(){
        Font myFont = new Font("Monospaced", Font.PLAIN, 16);
        TerminalSize terminalSize = new TerminalSize(20, 20);

        AWTTerminalFontConfiguration myFontConfiguration = AWTTerminalFontConfiguration.newInstance(myFont);
        DefaultTerminalFactory dtf = new DefaultTerminalFactory();
        dtf.setForceAWTOverSwing(true);
        dtf.setTerminalEmulatorFontConfiguration(myFontConfiguration);
        dtf.setInitialTerminalSize(terminalSize);
        try {
            terminal = dtf.createTerminal();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            screen = new TerminalScreen(terminal);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            screen.startScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        screen.setCursorPosition(null);
        textGraphics = screen.newTextGraphics();
    }
    public void PutString(int x, int y, String string, TextColor textColorFG, TextColor textColorBG){
        textGraphics.setBackgroundColor(textColorBG);
        textGraphics.setForegroundColor(textColorFG);
        textGraphics.putString(x,y,string);
        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void Clear(){
        screen.clear();
        try {
            screen.refresh();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void InputReader(){
        while(true) {
            keyStroke = null;
            try {
                keyStroke = screen.readInput();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public KeyStroke GetInput(){
        if(keyStroke == null){
            return null;
        }
        return keyStroke;
    }

    @Override
    public void run() {
        InputReader();
    }
}
