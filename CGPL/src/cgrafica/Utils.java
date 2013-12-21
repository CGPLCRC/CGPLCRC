/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgrafica;

import javax.media.opengl.GL2;

/**
 *
 * @author cv
 */
public class Utils
{

    private final static float DELTA = 0.001f;

    public static void drawCube(GL2 gl, float side, float x, float y, float z, float r, float g, float b)
    {
	// BACK
	gl.glBegin(GL2.GL_POLYGON);
	gl.glColor3f(r, g, b);
	gl.glVertex3f(x + side / 2, y - side / 2, z + side / 2);
	gl.glVertex3f(x + side / 2, y + side / 2, z + side / 2);
	gl.glVertex3f(x + -side / 2, y + side / 2, z + side / 2);
	gl.glVertex3f(x + -side / 2, y - side / 2, z + side / 2);
	gl.glEnd();

	// FRONT
	gl.glBegin(GL2.GL_POLYGON);
	gl.glColor3f(r, g, b);
	gl.glVertex3f(x - side / 2, y + side / 2, z - side / 2);
	gl.glVertex3f(x - side / 2, y - side / 2, z - side / 2);
	gl.glVertex3f(x - -side / 2, y - side / 2, z - side / 2);
	gl.glVertex3f(x - -side / 2, y + side / 2, z - side / 2);
	gl.glEnd();

	// RIGHT
	gl.glBegin(GL2.GL_POLYGON);
	gl.glColor3f(r, g, b);
	gl.glVertex3f(x + side / 2, y - side / 2, z - side / 2);
	gl.glVertex3f(x + side / 2, y + side / 2, z - side / 2);
	gl.glVertex3f(x + side / 2, y + side / 2, z + side / 2);
	gl.glVertex3f(x + side / 2, y - side / 2, z + side / 2);
	gl.glEnd();

//LEFT
	gl.glBegin(GL2.GL_POLYGON);
	gl.glColor3f(r, g, b);
	gl.glVertex3f(x - side / 2, y - side / 2, z + side / 2);
	gl.glVertex3f(x - side / 2, y + side / 2, z + side / 2);
	gl.glVertex3f(x - side / 2, y + side / 2, z - side / 2);
	gl.glVertex3f(x - side / 2, y - side / 2, z - side / 2);
	gl.glEnd();

// TOP
	gl.glBegin(GL2.GL_POLYGON);
	gl.glColor3f(r, g, b);
	gl.glVertex3f(x + side / 2, y + side / 2, z + side / 2);
	gl.glVertex3f(x + side / 2, y + side / 2, z - side / 2);
	gl.glVertex3f(x - side / 2, y + side / 2, z - side / 2);
	gl.glVertex3f(x - side / 2, y + side / 2, z + side / 2);
	gl.glEnd();

//BOTTOM
	gl.glBegin(GL2.GL_POLYGON);
	gl.glColor3f(r, g, b);
	gl.glVertex3f(x + side / 2, y - side / 2, z - side / 2);
	gl.glVertex3f(x + side / 2, y - side / 2, z + side / 2);
	gl.glVertex3f(x - side / 2, y - side / 2, z + side / 2);
	gl.glVertex3f(x - side / 2, y - side / 2, z - side / 2);
	gl.glEnd();

    }

    

    public static float[] move(float cameraX, float cameraY, 
	    float centerX, float centerY, boolean forward, float speed)
    {
	float nextCameraX;
	float nextCameraY;
	float nextCenterX;
	float nextCenterY;

	// if centerX == cameraX
	if ((centerX - cameraX) < DELTA)
	{
	    nextCameraX = cameraX;
	    nextCenterX = centerX;
	    if (forward)
	    {
		nextCameraY = cameraY - speed;
		nextCenterY = centerY - speed;
	    }
	    else
	    {
		nextCameraY = cameraY + speed;
		nextCenterY = centerY + speed;
	    }

	}
	else
	{
	    if (centerX < cameraX)
	    {
		if (forward)
		{
		    nextCameraX = cameraX - speed;
		    nextCenterX = centerX - speed;
		}
		else
		{
		    nextCameraX = cameraX + speed;
		    nextCenterX = centerX + speed;
		}
	    }
	    else
	    {
		if (forward)
		{
		    nextCameraX = cameraX + speed;
		    nextCenterX = centerX + speed;
		}
		else
		{
		    nextCameraX = cameraX - speed;
		    nextCenterX = centerX - speed;
		}
	    }
	    //y = ax + b
	    float a = (centerY - cameraY) / (centerX - cameraX);

	    float b = centerY - a * centerX;
	    nextCameraY = a * nextCameraX + b;
	    nextCenterY = a * nextCenterX + b;

	}

	return new float[]
	{
	    nextCameraX, nextCameraY, nextCenterX, nextCenterY
	};
    }

}
