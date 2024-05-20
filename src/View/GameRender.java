package View;

import Model.Point3D;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

import java.util.List;

public class GameRender implements GLEventListener {
    private GLU glu = new GLU();
    float x_pos;
    float y_pos;
    float z_pos;

    float x_angle;
    float y_angle;
    float z_angle;

    float x_size;
    float y_size;
    float z_size;

    public void setCubes(List<Point3D> cubes) {
        this.cubes = cubes;
    }

    private List<Point3D> cubes;

    public GameRender(List<Point3D> cubes, float x_anglew, float y_angle, float z_angle, float x_pos, float y_pos, float z_pos, float x_size, float y_size, float z_size){
        super();
        this.cubes = cubes;
        this.x_angle = x_anglew - 90;
        this.y_angle = -y_angle + 180;
        this.z_angle = z_angle - 90;
        this.x_pos = x_pos;
        this.y_pos = - y_pos;
        this.z_pos = z_pos;
        this.x_size = x_size;
        this.y_size = y_size;
        this.z_size = z_size;
    }

    @Override
    public void display( GLAutoDrawable drawable ) {

        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );
        gl.glLoadIdentity();
        gl.glTranslatef( x_pos, y_pos, z_pos );

        // Rotate The Cube On X, Y & Z

        gl.glRotatef(x_angle, 1.0f, 0.0f, 0.0f);

        gl.glRotatef(y_angle, 0.0f, 1.0f, 0.0f);

        gl.glRotatef(z_angle, 0.0f, 0.0f, 1.0f);

        //giving different colors to different sides
        gl.glBegin(GL2.GL_QUADS); // Start Drawing The Cube
        for (Point3D cube : cubes) {
            // Pobranie koloru z obiektu cube i konwersja wartości do zakresu 0-1
            gl.glColor3f((float) cube.getProperties().getColor().getRed() / 255, (float)cube.getProperties().getColor().getGreen() / 255, (float) cube.getProperties().getColor().getBlue() / 255);

            // Pobranie pozycji x, y, z z obiektu cube
            float x = (float) cube.getX();
            float y = (float) cube.getY();
            float z = (float) cube.getZ();

            // Ustawienie wierzchołków dla przedniego górnego kwadratu
            gl.glVertex3f(1.0f + x, 1.0f + y, 0.0f + z); // Top Right Of The Quad (Top)
            gl.glVertex3f(0.0f + x, 1.0f + y, 0.0f + z); // Top Left Of The Quad (Top)
            gl.glVertex3f(0.0f + x, 1.0f + y, 1.0f + z); // Bottom Left Of The Quad (Top)
            gl.glVertex3f(1.0f + x, 1.0f + y, 1.0f + z); // Bottom Right Of The Quad (Top)

            // Ustawienie wierzchołków dla przedniego dolnego kwadratu
            gl.glVertex3f(1.0f + x, 0.0f + y, 1.0f + z); // Top Right Of The Quad
            gl.glVertex3f(0.0f + x, 0.0f + y, 1.0f + z); // Top Left Of The Quad
            gl.glVertex3f(0.0f + x, 0.0f + y, 0.0f + z); // Bottom Left Of The Quad
            gl.glVertex3f(1.0f + x, 0.0f + y, 0.0f + z); // Bottom Right Of The Quad

            // Ustawienie wierzchołków dla przedniej ściany
            gl.glVertex3f(1.0f + x, 1.0f + y, 1.0f + z); // Top Right Of The Quad (Front)
            gl.glVertex3f(0.0f + x, 1.0f + y, 1.0f + z); // Top Left Of The Quad (Front)
            gl.glVertex3f(0.0f + x, 0.0f + y, 1.0f + z); // Bottom Left Of The Quad
            gl.glVertex3f(1.0f + x, 0.0f + y, 1.0f + z); // Bottom Right Of The Quad

            // Ustawienie wierzchołków dla tylnej ściany
            gl.glVertex3f(1.0f + x, 0.0f + y, 0.0f + z); // Bottom Left Of The Quad
            gl.glVertex3f(0.0f + x, 0.0f + y, 0.0f + z); // Bottom Right Of The Quad
            gl.glVertex3f(0.0f + x, 1.0f + y, 0.0f + z); // Top Right Of The Quad (Back)
            gl.glVertex3f(1.0f + x, 1.0f + y, 0.0f + z); // Top Left Of The Quad (Back)

            // Ustawienie wierzchołków dla lewej ściany
            gl.glVertex3f(0.0f + x, 1.0f + y, 1.0f + z); // Top Right Of The Quad (Left)
            gl.glVertex3f(0.0f + x, 1.0f + y, 0.0f + z); // Top Left Of The Quad (Left)
            gl.glVertex3f(0.0f + x, 0.0f + y, 0.0f + z); // Bottom Left Of The Quad
            gl.glVertex3f(0.0f + x, 0.0f + y, 1.0f + z); // Bottom Right Of The Quad

            // Ustawienie wierzchołków dla prawej ściany
            gl.glVertex3f(1.0f + x, 1.0f + y, 0.0f + z); // Top Right Of The Quad (Right)
            gl.glVertex3f(1.0f + x, 1.0f + y, 1.0f + z); // Top Left Of The Quad
            gl.glVertex3f(1.0f + x, 0.0f + y, 1.0f + z); // Bottom Left Of The Quad
            gl.glVertex3f(1.0f + x, 0.0f + y, 0.0f + z); // Bottom Right Of The Quad
        }
        gl.glEnd(); // Done Drawing The Quad
        gl.glBegin(GL2.GL_LINES); // Start Drawing The Cube
        for (Point3D cube : cubes) {
            gl.glColor3f(0, 0, 0);

            // Pobranie pozycji x, y, z z obiektu cube
            float x = (float) cube.getX();
            float y = (float) cube.getY();
            float z = (float) cube.getZ();

            gl.glVertex3f(1.0f + x, 1.0f + y, 0.0f + z);
            gl.glVertex3f(0.0f + x, 1.0f + y, 0.0f + z);
            gl.glVertex3f(0.0f + x, 1.0f + y, 1.0f + z);
            gl.glVertex3f(1.0f + x, 1.0f + y, 1.0f + z);

            gl.glVertex3f(1.0f + x, 0.0f + y, 1.0f + z);
            gl.glVertex3f(0.0f + x, 0.0f + y, 1.0f + z);
            gl.glVertex3f(0.0f + x, 0.0f + y, 0.0f + z);
            gl.glVertex3f(1.0f + x, 0.0f + y, 0.0f + z);

            gl.glVertex3f(1.0f + x, 1.0f + y, 1.0f + z);
            gl.glVertex3f(0.0f + x, 1.0f + y, 1.0f + z);
            gl.glVertex3f(0.0f + x, 0.0f + y, 1.0f + z);
            gl.glVertex3f(1.0f + x, 0.0f + y, 1.0f + z);

            gl.glVertex3f(1.0f + x, 0.0f + y, 0.0f + z);
            gl.glVertex3f(0.0f + x, 0.0f + y, 0.0f + z);
            gl.glVertex3f(0.0f + x, 1.0f + y, 0.0f + z);
            gl.glVertex3f(1.0f + x, 1.0f + y, 0.0f + z);

            gl.glVertex3f(0.0f + x, 1.0f + y, 1.0f + z);
            gl.glVertex3f(0.0f + x, 1.0f + y, 0.0f + z);
            gl.glVertex3f(0.0f + x, 0.0f + y, 0.0f + z);
            gl.glVertex3f(0.0f + x, 0.0f + y, 1.0f + z);

            gl.glVertex3f(0.0f + x, 0.0f + y, 0.0f + z);
            gl.glVertex3f(0.0f + x, 1.0f + y, 0.0f + z);

            gl.glVertex3f(0.0f + x, 0.0f + y, 1.0f + z);
            gl.glVertex3f(0.0f + x, 1.0f + y, 1.0f + z);

            gl.glVertex3f(1.0f + x, 0.0f + y, 0.0f + z);
            gl.glVertex3f(1.0f + x, 1.0f + y, 0.0f + z);

            gl.glVertex3f(1.0f + x, 0.0f + y, 1.0f + z);
            gl.glVertex3f(1.0f + x, 1.0f + y, 1.0f + z);

            gl.glVertex3f(1.0f + x, 1.0f + y, 0.0f + z);
            gl.glVertex3f(1.0f + x, 1.0f + y, 1.0f + z);
            gl.glVertex3f(1.0f + x, 0.0f + y, 1.0f + z);
            gl.glVertex3f(1.0f + x, 0.0f + y, 0.0f + z);
        }

        gl.glColor3f(0.5f, 0.5f, 0.5f);
        gl.glVertex3f(0, 0 , 0);
        gl.glVertex3f(x_size, 0 , 0);

        gl.glVertex3f(0, 0 , 0);
        gl.glVertex3f(0, y_size , 0);

        gl.glVertex3f(0, 0 , 0);
        gl.glVertex3f(0, 0 , z_size);



        gl.glVertex3f(x_size, y_size , z_size);
        gl.glVertex3f(x_size, y_size , 0);

        gl.glVertex3f(x_size, y_size , 0);
        gl.glVertex3f(0, y_size , 0);

        gl.glVertex3f(x_size, y_size , 0);
        gl.glVertex3f(x_size, 0 , 0);

        gl.glVertex3f(x_size, y_size , z_size);
        gl.glVertex3f(0, y_size , z_size);

        gl.glVertex3f(0, y_size , z_size);
        gl.glVertex3f(0, y_size , 0);

        gl.glVertex3f(0, y_size , z_size);
        gl.glVertex3f(0, 0 , z_size);

        gl.glVertex3f(x_size, y_size , z_size);
        gl.glVertex3f(x_size, 0 , z_size);

        gl.glVertex3f(x_size, 0 , z_size);
        gl.glVertex3f(0, 0 , z_size);

        gl.glVertex3f(x_size, 0 , z_size);
        gl.glVertex3f(x_size, 0 , 0);




        gl.glEnd(); // Done Drawing The Quad
        gl.glFlush();

    }

    @Override
    public void dispose( GLAutoDrawable drawable ) {

    }

    @Override
    public void init( GLAutoDrawable drawable ) {

        final GL2 gl = drawable.getGL().getGL2();
        gl.glShadeModel( GL2.GL_SMOOTH );
        gl.glClearColor( 0f, 0f, 0f, 0f );
        gl.glClearDepth( 1.0f );
        gl.glEnable( GL2.GL_DEPTH_TEST );
        gl.glDepthFunc( GL2.GL_LEQUAL );
        gl.glHint( GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST );
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL2 gl = drawable.getGL().getGL2();
        if (height <= 0)
            height = 1;

        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }
    public void free(){

    }
}
