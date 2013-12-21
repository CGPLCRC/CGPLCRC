package graphics;

import java.awt.Color;
import javax.media.opengl.GL2;

class Car
{
    public static final float LENGTH = 6;
    public static final float WIDTH = 4;
    public static final float HEIGHT = 2;
    
    //Center of the object
    private float x, y, z;
    private float angle = 0;
    // Color in RGB
    private float r, g, b;
    

    public Car(Color c)
    {
	this.r = c.getRed()/255;
	this.g = c.getGreen()/255;
	this.b = c.getBlue()/255;
    }

    public void setCenter(float x, float y, float z)
    {
	this.x = x;
	this.y = y;
	this.z = z;
    }
    
    public void setAngle(float angle)
    {
	this.angle = angle;
    }

    public float getAngle()
    {
	return angle;
    }
    
    
    public void draw(GL2 gl)
    {
	gl.glTranslatef(x, y, z);
	gl.glRotatef(angle, 0, 1, 0);
	gl.glBegin(GL2.GL_POLYGON);

	gl.glColor3f(r, g, b);

	// Front-face
	gl.glVertex3f(WIDTH/2, -HEIGHT/2, LENGTH/2);
	gl.glVertex3f(-WIDTH/2, -HEIGHT/2, LENGTH/2);
	gl.glVertex3f(-WIDTH/2, HEIGHT/2, LENGTH/2);
	gl.glVertex3f(WIDTH/2, HEIGHT/2, LENGTH/2);
	gl.glEnd();

        gl.glBegin(GL2.GL_POLYGON);

	// Back-face
	gl.glVertex3f(WIDTH/2, -HEIGHT/2, -LENGTH/2);
	gl.glVertex3f(-WIDTH/2, -HEIGHT/2, -LENGTH/2);
	gl.glVertex3f(-WIDTH/2, HEIGHT/2, -LENGTH/2);
	gl.glVertex3f(WIDTH/2, HEIGHT/2, -LENGTH/2);
	gl.glEnd();

        gl.glBegin(GL2.GL_POLYGON);

	// Left-face
	gl.glVertex3f(-WIDTH/2, -HEIGHT/2, LENGTH/2);
	gl.glVertex3f(-WIDTH/2, -HEIGHT/2, -LENGTH/2);
	gl.glVertex3f(-WIDTH/2, HEIGHT/2, -LENGTH/2);
	gl.glVertex3f(-WIDTH/2, HEIGHT/2, LENGTH/2);
	gl.glEnd();

        gl.glBegin(GL2.GL_POLYGON);

	// Right-face
	gl.glVertex3f(WIDTH/2, -HEIGHT/2, LENGTH/2);
	gl.glVertex3f(WIDTH/2, -HEIGHT/2, -LENGTH/2);
	gl.glVertex3f(WIDTH/2, HEIGHT/2, -LENGTH/2);
	gl.glVertex3f(WIDTH/2, HEIGHT/2, LENGTH/2);
	gl.glEnd();

        gl.glBegin(GL2.GL_POLYGON);

	// Top-face
	gl.glVertex3f(WIDTH/2, HEIGHT/2, LENGTH/2);
	gl.glVertex3f(-WIDTH/2, HEIGHT/2, LENGTH/2);
	gl.glVertex3f(-WIDTH/2, HEIGHT/2, -LENGTH/2);
	gl.glVertex3f(WIDTH/2, HEIGHT/2, -LENGTH/2);
	gl.glEnd();

        gl.glBegin(GL2.GL_POLYGON);

	// Bottom-face
	gl.glVertex3f(WIDTH/2, -HEIGHT/2, LENGTH/2);
	gl.glVertex3f(-WIDTH/2, -HEIGHT/2, LENGTH/2);
	gl.glVertex3f(-WIDTH/2, -HEIGHT/2, -LENGTH/2);
	gl.glVertex3f(WIDTH/2, -HEIGHT/2, -LENGTH/2);

	gl.glEnd();
	gl.glRotatef(-angle, 0, 1, 0);
	gl.glTranslatef(-x, -y, -z);
	

    }

    void setColor(Color c)
    {
	this.r = c.getRed()/255;
	this.g = c.getGreen()/255;
	this.b = c.getBlue()/255;
    }

}