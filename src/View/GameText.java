package View;

import Controller.ControllerTxt;
import Controller.GameController3D;
import Model.ActionEnum;
import Model.Point3D;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;

import java.util.List;

public class GameText extends GameShower implements Runnable{
    TerminalHandler terminalHandler;
    ControllerTxt controllerTxt;

    public void setChangingView(boolean changingView) {
        this.changingView = changingView;
    }

    boolean changingView = false;
    GameController3D gameController3D;
    public GameText(TerminalHandler terminalHandler, ControllerTxt controllerTxt, GameController3D gameController3D){
        this.terminalHandler = terminalHandler;
        this.gameController3D = gameController3D;
        this.controllerTxt = controllerTxt;
    }
    public void KeyStrokeReaction(KeyStroke keyStroke){
        if(keyStroke == null){
            gameController3D.setActionEnum(ActionEnum.NONE);
        }
        else if(keyStroke.getKeyType().toString().equals("Escape")){
            changingView = true;
            controllerTxt.ChangeTargetView(TargetView.Score);
            gameController3D.stop();
        }
        else if(keyStroke.getKeyType().toString().equals("ArrowLeft")){
            gameController3D.setActionEnum(ActionEnum.MOVE_X_NEGATIVE);
        }
        else if(keyStroke.getKeyType().toString().equals("ArrowRight")){
            gameController3D.setActionEnum(ActionEnum.MOVE_X_POSITIVE);
        }

    }
    @Override
    public void setCubes(List<Point3D> cubes){
        terminalHandler.Clear();
        for(Point3D cube : cubes){
            UpdateCube((int)cube.getX(), (int)cube.getY(),true);
        }
    }
    public void UpdateCube(int x, int y, boolean isThere){
        terminalHandler.PutString(x,y," ", TextColor.ANSI.WHITE, TextColor.ANSI.GREEN);
    }

    @Override
    public void run() {
        while(!changingView){
            KeyStroke keyStroke = terminalHandler.GetInput();
            KeyStrokeReaction(keyStroke);

            try {
                Thread.sleep(35);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
