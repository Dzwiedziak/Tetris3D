package Controller;

import Model.*;
import View.GameShower;
import View.GameText;
import View.GameView;
import View.TerminalHandler;
import com.jogamp.opengl.awt.GLCanvas;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class GameController3D {
    Controller controller;
    TetrisMap tetrisMap;
    private final Map<ActionEnum, Function<Void, List<Point3D>>> actionMap;
    JFrame jFrame;
    GameView gameView;
    boolean running = false;
    ActionEnum actionEnum;

    public GameController3D(int height, int width, int depth, Controller controller){
        tetrisMap = new TetrisMap(height, width, depth, PositionStrategy.MIDDLE, PositionStrategy.ZERO, PositionStrategy.MIDDLE);

        actionMap = new HashMap<>();
        initializeActionMap();
        tetrisMap.generateTetromino();
        this.controller = controller;
        this.actionEnum = ActionEnum.NONE;
    }
    private void initializeActionMap() {
        actionMap.put(ActionEnum.MOVE_X_POSITIVE, this::handleMoveXPositive);
        actionMap.put(ActionEnum.MOVE_X_NEGATIVE, this::handleMoveXNegative);
        actionMap.put(ActionEnum.MOVE_Z_POSITIVE, this::handleMoveZPositive);
        actionMap.put(ActionEnum.MOVE_Z_NEGATIVE, this::handleMoveZNegative);
        actionMap.put(ActionEnum.MOVE_Y_SPEEDUP, this::handleMoveYSpeedup);
        actionMap.put(ActionEnum.ROTATION_X_POSITIVE, this::handleRotationXPositive);
        actionMap.put(ActionEnum.ROTATION_X_NEGATIVE, this::handleRotationXNegative);
        actionMap.put(ActionEnum.ROTATION_Y_POSITIVE, this::handleRotationYPositive);
        actionMap.put(ActionEnum.ROTATION_Y_NEGATIVE, this::handleRotationYNegative);
        actionMap.put(ActionEnum.ROTATION_Z_POSITIVE, this::handleRotationZPositive);
        actionMap.put(ActionEnum.ROTATION_Z_NEGATIVE, this::handleRotationZNegative);
        actionMap.put(ActionEnum.NONE, this::handleNone);
    }
    public void tick(){
        tetrisMap.recalculateMassCenter();
        Function<Void, List<Point3D>> action = actionMap.get(actionEnum);
        List<Point3D> point3DList = action.apply(null);
        BlocksList blocksList = new BlocksList(point3DList);

        for(Point3D point3D : blocksList){
            point3D.round();
        }

        if(!tetrisMap.checkCollisionWithBorder(blocksList)){
            if(!tetrisMap.checkCollisionWithStatic(blocksList)){
                tetrisMap.setMovingBlocksList(blocksList);
            }
        }

        List<Point3D> normalMove = handleMoveYSpeedup(null);
        BlocksList blocksMove = new BlocksList(normalMove);

        if(tetrisMap.checkCollisionWithStatic(blocksMove) || tetrisMap.checkCollisionWithBorder(blocksMove)){
            tetrisMap.addMovingToStaticBlocks();
            tetrisMap.removeFull();
            tetrisMap.generateTetromino();

            if(tetrisMap.checkCollisionMovingWithStatic()){
                controller.GameEnded();
            }

        }
        else{
            tetrisMap.setMovingBlocksList(blocksMove);
        }
    }
    private List<Point3D> handleMoveXPositive(Void unused) {
        Vector3D vector3D = new Vector3D(1,0,0);
        return tetrisMap.getMovedTetromino(vector3D);
    }

    private List<Point3D> handleMoveXNegative(Void unused) {
        Vector3D vector3D = new Vector3D(-1,0,0);
        return tetrisMap.getMovedTetromino(vector3D);
    }

    private List<Point3D> handleMoveZPositive(Void unused) {
        Vector3D vector3D = new Vector3D(0,0,1);
        return tetrisMap.getMovedTetromino(vector3D);
    }

    private List<Point3D> handleMoveZNegative(Void unused) {
        Vector3D vector3D = new Vector3D(0,0,-1);
        return tetrisMap.getMovedTetromino(vector3D);
    }

    private List<Point3D> handleMoveYSpeedup(Void unused) {
        Vector3D vector3D = new Vector3D(0,1,0);
        return tetrisMap.getMovedTetromino(vector3D);
    }

    private List<Point3D> handleRotationXPositive(Void unused) {
        EulerAngle eulerAngle = new EulerAngle(90,0,0);
        return tetrisMap.getRotatedTetromino(eulerAngle);
    }

    private List<Point3D> handleRotationXNegative(Void unused) {
        EulerAngle eulerAngle = new EulerAngle(-90,0,0);
        return tetrisMap.getRotatedTetromino(eulerAngle);
    }

    private List<Point3D> handleRotationYPositive(Void unused) {
        EulerAngle eulerAngle = new EulerAngle(0,90,0);
        return tetrisMap.getRotatedTetromino(eulerAngle);
    }

    private List<Point3D> handleRotationYNegative(Void unused) {
        EulerAngle eulerAngle = new EulerAngle(0,-90,0);
        return tetrisMap.getRotatedTetromino(eulerAngle);
    }

    private List<Point3D> handleRotationZPositive(Void unused) {
        EulerAngle eulerAngle = new EulerAngle(0,0,90);
        return tetrisMap.getRotatedTetromino(eulerAngle);
    }

    private List<Point3D> handleRotationZNegative(Void unused) {
        EulerAngle eulerAngle = new EulerAngle(0,0,-90);
        return tetrisMap.getRotatedTetromino(eulerAngle);
    }

    private List<Point3D> handleNone(Void unused) {
        Vector3D vector3D = new Vector3D(0,0,0);
        return tetrisMap.getMovedTetromino(vector3D);
    }

    //TEST


    public List<Point3D> getTetrisMap() {
        List<Point3D> cubes = new ArrayList<>();
        for(Point3D point3D: tetrisMap.getStaticBlocksList()){
            Point3D newPoint = new Point3D(point3D.getX(), point3D.getY(), point3D.getZ());
            newPoint.setProperties(point3D.getProperties());
            cubes.add(newPoint);

        }
        for(Point3D point3D: tetrisMap.getMovingBlocksList()){
            Point3D newPoint = new Point3D(point3D.getX(), point3D.getY(), point3D.getZ());
            newPoint.setProperties(point3D.getProperties());
            cubes.add(newPoint);
        }
        return cubes;
    }

    public void start(GameShower gameShower){
        this.running = true;
        while(running){
            tick();
            gameShower.setCubes(getTetrisMap());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public void stop(){
        running = false;
        if(gameView != null){
            gameView.free();
        }
        gameView = null;
    }
    public void pause(){
        gameView.pause();
    }
    public void resume(){
        gameView.resume();
    }
    public int getScore(){
        int score = (int)tetrisMap.getScore();
        return score;
    }
    public void setActionEnum(ActionEnum actionEnum){
        this.actionEnum = actionEnum;
    }
    public int getHeigth(){
        return tetrisMap.getHeight();
    }
    public int getWidth(){
        return tetrisMap.getWidth();
    }
    public int getDepth(){
        return tetrisMap.getDepth();
    }
}
