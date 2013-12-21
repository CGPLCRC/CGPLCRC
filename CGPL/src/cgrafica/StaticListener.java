/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgrafica;

import graphics.Road;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;


public class StaticListener
	implements
	GLEventListener,
	KeyListener,
	MouseMotionListener
{

    private static final double radConversion = Math.PI / 180;
    /**
     * Use a perspective or a parallel projection.
     */
    protected boolean perspectiveProjection = true;
    protected float left = -10;
    protected float right = 10;
    protected float top = 10;
    protected float bottom = -10;
    protected float far = 95;
    protected float near = 1;
    /**
     * Camera coordinates.
     */
    protected float[] eye = new float[]
    {
	0, -20, 20
    };
    double zRotationAngle = 0;
    /**
     * Coordinates of where the camera is pointing.
     */
    protected float[] center = new float[]
    {
	0, 0, 0
    };
    /**
     * Up vector used when setting the camera properties.
     */
    protected float[] up = new float[]
    {
	0, 1, 0
    };
    /**
     * The OpenGL AWT component that this listener is attached to.
     */
    protected final GLCanvas canvas;
    private float speed = 0.5f;
    private int width;
    private int height;
    private Road road;

    StaticListener(GLCanvas canvas, Road road)
    {
	this.canvas = canvas;
	this.road = road;
	this.status();
    }

    @Override
    public void init(GLAutoDrawable glad)
    {
	//TODO: DepthBuffer não funciona a 100%
	
	 GL2 gl = glad.getGL().getGL2();
	 gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	 gl.glClearDepth(1.0f);
	 gl.glEnable(GL.GL_DEPTH_TEST);
	 gl.glDepthFunc(GL.GL_LEQUAL);
	 

	System.out.println("GLEventListener.init(GLAutoDrawable)");
    }

    @Override
    public void dispose(GLAutoDrawable glad)
    {
	System.out.println("GLEventListener.dispose(GLAutoDrawable)");
    }

    @Override
    public void display(GLAutoDrawable drawable)
    {
	GL2 gl = drawable.getGL().getGL2();
	//TODO: DepthBuffer não funciona a 100%	
	gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
	//gl.glClear(GL.GL_COLOR_BUFFER_BIT);
	gl.glLoadIdentity();

	GLU glu = GLU.createGLU(gl);
	glu.gluLookAt(
		this.eye[0], this.eye[1], this.eye[2],
		this.center[0], this.center[1], this.center[2],
		this.up[0], this.up[1], this.up[2]
	);

	/*//draw xyz axis
	 gl.glColor3f(0, 1, 0);
	 gl.glBegin(GL.GL_LINES);
	 gl.glVertex3d(0, -10, 0);
	 gl.glVertex3d(0, 10, 0);
	 gl.glVertex3d(-10, 0, 0);
	 gl.glVertex3d(10, 0, 0);
	 gl.glVertex3d(0, 0, -10);
	 gl.glVertex3d(0, 0, 10);
	 gl.glEnd();
	 */
	
	road.draw(gl);
	gl.glFlush();

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
    {
	this.height = height;
	this.width = width;
	GL2 gl = drawable.getGL().getGL2();
	gl.glViewport(0, 0, width, height);
	gl.glMatrixMode(GL2.GL_PROJECTION);
	gl.glLoadIdentity();
	if (this.perspectiveProjection)
	{

	    /*gl.glFrustum (
	     left,   right,
	     bottom, top,
	     near,   far
	     );*/
	    GLU glu = GLU.createGLU(gl);
	    glu.gluPerspective(60, width / height, 1, 120);
	}
	else
	{
	    gl.glOrtho(
		    left, right,
		    bottom, top,
		    near, far
	    );
	}
	gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    @Override
    public void keyTyped(KeyEvent ke)
    {
	/*
	switch (ke.getKeyChar())
	{
	    case ' ':
		this.perspectiveProjection = !this.perspectiveProjection;
		break;
	    default:
		return;
	}
	//this.canvas.setBounds(0, 0, width, height);
	this.canvas.display();
	this.status();
	*/
    }

    private double getUpdatedRadius()
    {
	return Math.sqrt((double) (eye[0] * eye[0] + eye[2] * eye[2]));
    }

    @Override
    public void keyPressed(KeyEvent ke)
    {
	
	float[] nextPoints = new float[4];
	switch (ke.getKeyChar())
	{
	    case 'w':

		nextPoints = moveForward(eye[0], eye[2], center[0], center[2]);
		this.eye[0] = nextPoints[0];
		this.eye[2] = nextPoints[1];
		this.center[0] = nextPoints[2];
		this.center[2] = nextPoints[3];

		/*
		 this.eye [2] += (float)(Math.cos(zRotationAngle*radConversion) );
		 this.eye [0] += (float)(Math.sin(zRotationAngle*radConversion) );
		 this.center [2] = this.eye [2] + (float)(Math.cos(zRotationAngle*radConversion) );
		 this.center [0] = this.eye [0] + (float)(Math.sin(zRotationAngle*radConversion) );
		*/ 
                //Fechar comentário
		break;
	    case 's':

		nextPoints = moveBackwards(eye[0], eye[2], center[0], center[2]);
		this.eye[0] = nextPoints[0];
		this.eye[2] = nextPoints[1];
		this.center[0] = nextPoints[2];
		this.center[2] = nextPoints[3];
		/*
		 this.eye [2] -= (float)(Math.cos(zRotationAngle*radConversion) );
		 this.eye [0] -= (float)(Math.sin(zRotationAngle*radConversion) );
		 this.center[2] = this.eye [2] + (float)(Math.cos(zRotationAngle*radConversion) );
		 this.center[0] = this.eye [0] + (float)(Math.sin(zRotationAngle*radConversion) );
		
                *///Fechar comentário
                
		break;
	    case 'a':
		/*
		 this.zRotationAngle -= 1;
		 this.center[2] = this.eye [2] + (float)(Math.cos(zRotationAngle*radConversion) );
		 this.center[0] = this.eye [0] + (float)(Math.sin(zRotationAngle*radConversion) );
		 //Fechar comentário
                */
		this.center[0] -= speed;
		break;
	    case 'd':
		/*
		 this.zRotationAngle += 1;
		 this.eye[2] = this.center [2] + (float)(Math.cos(zRotationAngle*radConversion) * getUpdatedRadius());
		 this.eye[0] = this.center [0] + (float)(Math.sin(zRotationAngle*radConversion) * getUpdatedRadius());
		 //Fechar comentário
		
                */
                center[0] += speed;
		break;
	    default:
		return;
	}
	//this.canvas.setBounds(0, 0, width, height);

	this.canvas.display();
	this.status();
	
    }

    @Override
    public void keyReleased(KeyEvent ke)
    {
    }

    /**
     * Print the properties of the projection parameters and of the camera.
     */
    protected void status()
    {
	System.out.println("\n\n");
	System.out.println(this.perspectiveProjection ? "Perspective" : "Parallel");
	System.out.format("Left Right: %5.1f .. %5.1f\n", this.left, this.right);
	System.out.format("Top Bottom: %5.1f .. %5.1f\n", this.top, this.bottom);
	System.out.format("  Near Far: %5.1f .. %5.1f\n", this.near, this.far);
	System.out.format("   Eye:  ( %5.1f , %5.1f , %5.1f )\n", this.eye[0], this.eye[1], this.eye[2]);
	System.out.format("Center:  ( %5.1f , %5.1f , %5.1f )\n", this.center[0], this.center[1], this.center[2]);
	System.out.format("    Up:  ( %5.1f , %5.1f , %5.1f )\n", this.up[0], this.up[1], this.up[2]);
	System.out.format(" angle: %3f\n", this.zRotationAngle);
    }

    @Override
    public void mouseDragged(MouseEvent me)
    {
    }

    @Override
    public void mouseMoved(MouseEvent me)
    {
    }

    public float[] moveForward(float cameraX, float cameraY, float centerX, float centerY)
    {

	return Utils.move(cameraX, cameraY, centerX, centerY, true, speed);
    }

    public float[] moveBackwards(float cameraX, float cameraY, float centerX, float centerY)
    {

	return Utils.move(cameraX, cameraY, centerX, centerY, false, speed);
    }

}
