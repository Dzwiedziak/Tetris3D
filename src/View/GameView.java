package View;

import Controller.Controller;
import Controller.GameController3D;
import Model.ActionEnum;
import Model.GameAction;
import Model.Point3D;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class GameView extends GameShower implements Runnable{
    float x_pos = 0;
    float y_pos = 0;
    float z_pos = -15;

    float x_angle = 90;
    float y_angle = 0;
    float z_angle = 90;


    private JFrame jFrame;
    private GameRender gameRender;
    private boolean running = true;
    private boolean pause = false;

    public void setGameAction(GameAction gameAction) {
        this.gameAction = gameAction;
    }

    private GameAction gameAction = GameAction.none;
    private List<Point3D> cubes;
    final FPSAnimator animator;

    public GLCanvas getGlCanvas() {
        return glCanvas;
    }

    GLCanvas glCanvas;



    public GameView(JFrame jFrame, List<Point3D> cubes, float x_size, float y_size, float z_size, Controller controllerGraphics, GameController3D gameController3D){
        this.jFrame = jFrame;
        this.cubes = cubes;
        final GLProfile profile = GLProfile.get( GLProfile.GL2 );
        GLCapabilities capabilities = new GLCapabilities( profile );
        final GLCanvas glcanvas = new GLCanvas( capabilities );

        this.glCanvas = glcanvas;

        gameRender = new GameRender(cubes, x_angle, y_angle, z_angle, x_pos, y_pos, z_pos, x_size, y_size, z_size);
        glcanvas.addGLEventListener( gameRender );
        glcanvas.setSize( 400, 400 );
        jFrame.getContentPane().add( glcanvas );
        jFrame.setSize( jFrame.getContentPane().getPreferredSize());

        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_UP:
                        setGameAction(GameAction.forward);
                        break;
                    case KeyEvent.VK_DOWN:
                        setGameAction(GameAction.backward);
                        break;
                    case KeyEvent.VK_LEFT:
                        setGameAction(GameAction.left);
                        break;
                    case KeyEvent.VK_RIGHT:
                        setGameAction(GameAction.right);
                        break;
                    case KeyEvent.VK_SPACE:
                        setGameAction(GameAction.up);
                        break;
                    case KeyEvent.VK_SHIFT:
                        setGameAction(GameAction.down);
                        break;
                    case KeyEvent.VK_ESCAPE:
                        controllerGraphics.GameEnded();
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                setGameAction(GameAction.none);
            }
        };
        glcanvas.addKeyListener(keyListener);
        KeyListener keyListenerTetris = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_A:
                        gameController3D.setActionEnum(ActionEnum.MOVE_Z_POSITIVE);
                        break;
                    case KeyEvent.VK_S:
                        gameController3D.setActionEnum(ActionEnum.MOVE_X_NEGATIVE);
                        break;
                    case KeyEvent.VK_D:
                        gameController3D.setActionEnum(ActionEnum.MOVE_Z_NEGATIVE);
                        break;
                    case KeyEvent.VK_W:
                        gameController3D.setActionEnum(ActionEnum.MOVE_X_POSITIVE);
                        break;
                    case KeyEvent.VK_Y:
                        gameController3D.setActionEnum(ActionEnum.ROTATION_X_POSITIVE);
                        break;
                    case KeyEvent.VK_U:
                        gameController3D.setActionEnum(ActionEnum.ROTATION_X_NEGATIVE);
                        break;
                    case KeyEvent.VK_K:
                        gameController3D.setActionEnum(ActionEnum.ROTATION_Y_NEGATIVE);
                        break;
                    case KeyEvent.VK_I:
                        gameController3D.setActionEnum(ActionEnum.ROTATION_Y_POSITIVE);
                        break;
                    case KeyEvent.VK_H:
                        gameController3D.setActionEnum(ActionEnum.ROTATION_Z_POSITIVE);
                        break;
                    case KeyEvent.VK_J:
                        gameController3D.setActionEnum(ActionEnum.ROTATION_Z_NEGATIVE);
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                gameController3D.setActionEnum(ActionEnum.NONE);
            }
        };
        glcanvas.addKeyListener(keyListenerTetris);
        DragListener dragListener = new DragListener() {
            @Override
            public void reaction(int distance_x, int distance_y) {
                y_angle += (double)distance_x/2.5;
                x_angle += (double)distance_y/2.5;
            }
        };
        glcanvas.addMouseMotionListener(dragListener);
        glcanvas.addMouseListener(dragListener);

        jFrame.pack();
        jFrame.setVisible( true );
        animator = new FPSAnimator(glcanvas, 1000,true);
    }
    public void setCubes (List<Point3D> cubes){
        this.cubes = cubes;
    }

    @Override
    public void run() {
        animator.start();
        while(running){
            if(gameAction.equals(GameAction.forward)){
                z_pos += 0.1;
            }
            else if(gameAction.equals(GameAction.backward)){
                z_pos -= 0.1;
            }
            else if(gameAction.equals(GameAction.left)){
                x_pos += 0.1;
            }
            else if(gameAction.equals(GameAction.right)){
                x_pos -= 0.1;
            }
            else if(gameAction.equals(GameAction.up)){
                y_pos += -0.1;
            }
            else if(gameAction.equals(GameAction.down)){
                y_pos -= -0.1;
            }
            gameRender.x_pos = x_pos;
            gameRender.y_pos = y_pos;
            gameRender.z_pos = z_pos;

            gameRender.x_angle = x_angle;
            gameRender.y_angle = y_angle;
            gameRender.z_angle = z_angle;

            gameRender.setCubes(cubes);

            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            while(pause){
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    public void free(){
        running = false;
        animator.stop();
        jFrame.getContentPane().removeAll();
        gameRender.free();
        gameRender = null;
    }
    public void pause(){
        pause = true;
    }
    public void resume(){
        pause = false;
    }

}
