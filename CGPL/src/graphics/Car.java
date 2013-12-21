package graphics;

import java.awt.Color;
import javax.media.opengl.GL2;

class Car
{
    //Center of the object
    private float x, y, z;
    // Color in RGB
    private float r, g, b;
    

    public Car(Color c)
    {
	this.r = c.getRed()/255;
	this.g = c.getGreen()/255;
	this.b = c.getBlue()/255;
    }

    public void setPosition(float x, float y, float z)
    {
	this.x = x;
	this.y = y;
	this.z = z;
    }
    
    public void draw(GL2 gl)
    {
	gl.glTranslatef(x, y, z);
	gl.glBegin(GL2.GL_POLYGON);

	// cyan
	gl.glColor3f(r, g, b);

	// Front-face
	gl.glVertex3f(2, -1, 3);
	gl.glVertex3f(-2, -1, 3);
	gl.glVertex3f(-2, 1, 3);
	gl.glVertex3f(2, 1, 3);
	gl.glEnd();

        gl.glBegin(GL2.GL_POLYGON);

	// Back-face
	gl.glVertex3f(2, -1, -3);
	gl.glVertex3f(-2, -1, -3);
	gl.glVertex3f(-2, 1, -3);
	gl.glVertex3f(2, 1, -3);
	gl.glEnd();

        gl.glBegin(GL2.GL_POLYGON);

	// Left-face
	gl.glVertex3f(-2, -1, 3);
	gl.glVertex3f(-2, -1, -3);
	gl.glVertex3f(-2, 1, -3);
	gl.glVertex3f(-2, 1, 3);
	gl.glEnd();

        gl.glBegin(GL2.GL_POLYGON);

	// Right-face
	gl.glVertex3f(2, -1, 3);
	gl.glVertex3f(2, -1, -3);
	gl.glVertex3f(2, 1, -3);
	gl.glVertex3f(2, 1, 3);
	gl.glEnd();

        gl.glBegin(GL2.GL_POLYGON);

	// Top-face
	gl.glVertex3f(2, 1, 3);
	gl.glVertex3f(-2, 1, 3);
	gl.glVertex3f(-2, 1, -3);
	gl.glVertex3f(2, 1, -3);
	gl.glEnd();

        gl.glBegin(GL2.GL_POLYGON);

	// Bottom-face
	gl.glVertex3f(2, -1, 3);
	gl.glVertex3f(-2, -1, 3);
	gl.glVertex3f(-2, -1, -3);
	gl.glVertex3f(2, -1, -3);

	gl.glEnd();
	gl.glTranslatef(-x, -y, -z);

    }

    void setColor(Color c)
    {
	this.r = c.getRed()/255;
	this.g = c.getGreen()/255;
	this.b = c.getBlue()/255;
    }

}