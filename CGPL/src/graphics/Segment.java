/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import java.awt.Color;
import javax.media.opengl.GL2;

class Segment
{

    // (Car.LENGTH + 2)
    public static final float LENGTH = 8;
    // (Car.WIDTH + 2) * 2
    public static final float WIDTH = 12;
    public static final float HEIGHT = 0;
    
    //TODO: wont be final on v2
    // center of the segment
    private final float x, y, z;

    private Car carLeft, carRight;

    private boolean isSource;
    
    private Color color;

    public Segment(float x, float y, float z)
    {
	this.x = x;
	this.y = y;
	this.z = z;
    }

    public Car getCarLeft()
    {
	return carLeft;
    }

    public Car getCarRight()
    {
	return carRight;
    }

    public void setCarLeft(Car carLeft)
    {

	if (carLeft != null)
	{
	    //set car in the correct position
	    carLeft.setCenter(x - Car.LENGTH/2, y + Car.HEIGHT/2, z);
	}
	this.carLeft = carLeft;

    }

    public void setCarRight(Car carRight)
    {
	if (carRight != null)
	{
	    //set car in the correct position
	    carRight.setCenter(x + Car.LENGTH/2, y + Car.HEIGHT/2, z);
	}
	this.carRight = carRight;
    }

    public boolean isCarLeft()
    {
	return carLeft != null;
    }

    public boolean isCarRight()
    {
	return carRight != null;
    }

    public void drawCars(GL2 gl)
    {
	if (isCarLeft())
	{
	    carLeft.draw(gl);
	}
	if (isCarRight())
	{
	    carRight.draw(gl);
	}
    }

    public void drawSegment(GL2 gl)
    {

	gl.glTranslatef(x, y, z);
	gl.glBegin(GL2.GL_POLYGON);

	// white
	gl.glColor3f(1.0f, 1.0f, 1.0f);
	
	gl.glVertex3f(WIDTH/2, 0, LENGTH/2);
	gl.glVertex3f(-WIDTH/2, 0, LENGTH/2);
	gl.glVertex3f(-WIDTH/2, 0, -LENGTH/2);
	gl.glVertex3f(WIDTH/2, 0, -LENGTH/2);

	gl.glEnd();
	gl.glTranslatef(-x, -y, -z);

	if (this.isSource())
	{
	    this.drawSource(gl);
	}

    }

    public void mergeCarRight(Color c)
    {
	this.setCarRight(new Car(c));
    }

    public void mergeCarLeft(Color c)
    {
	this.setCarLeft(new Car(c));
    }

    public Segment copy()
    {

	Segment res = new Segment(x, y, z);
	res.setCarLeft(carLeft);
	res.setCarRight(carRight);
	res.setIsSource(isSource);
	res.setColor(color);
	return res;
    }

    public boolean isSource()
    {
	return isSource;
    }

    public void setIsSource(boolean isSource)
    {
	this.isSource = isSource;
    }

    private void drawSource(GL2 gl)
    {
	
	float r = color.getRed()/255;
	float g = color.getGreen()/255;
	float b = color.getBlue()/255;
	
	//from segment (x) to segment side (x - WIDTH/2) to source center
	float sourceX = x - WIDTH/2 - LENGTH/2;
	float sourceY = y;
	float sourceZ = z;

	gl.glTranslatef(sourceX, sourceY, sourceZ);
	gl.glRotatef(90, 0, 1, 0);
	gl.glBegin(GL2.GL_POLYGON);

	//white
	gl.glColor3f(1.0f, 1.0f, 1.0f);

	//draw half of a segment
	gl.glVertex3f(WIDTH/2/2, 0, LENGTH/2);
	gl.glVertex3f(-WIDTH/2/2, 0, LENGTH/2);
	gl.glVertex3f(-WIDTH/2/2, 0, -LENGTH/2);
	gl.glVertex3f(WIDTH/2/2, 0, -LENGTH/2);

	gl.glEnd();
        
	gl.glRotatef(-90, 0, 1, 0);
	gl.glTranslatef(-sourceX, -sourceY, -sourceZ);

	Car segmentCar = new Car(color);
	segmentCar.setCenter(sourceX, sourceY + Car.HEIGHT/2, sourceZ);
	segmentCar.setAngle(90);
	segmentCar.draw(gl);
	
	

	
	
	
	
	
    }

    public void setColor(Color color)
    {
	this.color = color;
    }
    
    

}
