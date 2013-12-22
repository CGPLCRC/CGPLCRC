/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import cgrafica.AlgoritmoCores;
import cgrafica.AlgoritmoCores.OrigemCarro;
import cgrafica.Main;

import java.awt.Color;
import java.util.ArrayList;

import javax.media.opengl.GL2;

import com.jogamp.graph.font.Font;

/**
 *
 * @author cv
 */
public class Road
{

    // available colors for sources
    private static final Color[] colors = new Color[]
    {

	Color.BLUE, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.RED, Color.YELLOW

    };

    private boolean simulationMode;
    private ArrayList<Segment> road;
    private int size;

    private boolean straight;
    private ArrayList<Integer> sources;
    private ArrayList<Integer> emissions;
    private int tick;
    private final AlgoritmoCores alg;
    private GL2 gl;

    public Road()
    {

	sources = new ArrayList<>();
	emissions = new ArrayList<>();

	int inputTamanho = Main.getSegmento();

	sources.add(1);
	sources.add(4);
	sources.add(5);

	emissions.add(2);
	emissions.add(4);
	emissions.add(5);

	straight = true;

		// //////////////////////////////////////////
	// soma de 2 segmentos extremos
	size = inputTamanho + 2;
	road = new ArrayList<>();
	for (int i = 0; i < size; i++)
	{
	    Segment s = new Segment(0, 0, (-8) * i);
	    s.setAngle(0);
	    s.setColor(colors[i % colors.length]);
	    road.add(s);
	}

	for (int i : sources)
	{
	    road.get(i).setIsSource(true);
	    // road.get(i).setColor(colors[i]);
	}

	tick = 0;
	alg = AlgoritmoCores.getInstance();
	simulationMode = false;
    }

    public void toggleSourceAt(int pos, int emissionTime)
    {

	if (road.get(pos).isSource())
	{
	    road.get(pos).setIsSource(false);
	    int index = sources.indexOf(pos);
	    emissions.remove(index);
	    sources.remove(index);
	}
	else
	{
	    road.get(pos).setIsSource(true);
	    sources.add(pos);
	    emissions.add(emissionTime);

	}

    }

    public void tick(boolean pauseFont)
    {
	try
	{
	    this.move();
	    for (int i = 0; i < sources.size(); i++)
	    {
		// is it time for merge?
		if (tick % emissions.get(i) == 0)
		{
		    int sourceIndex = sources.get(i);
		    if (!pauseFont)
		    {
			road.get(sourceIndex).mergeCarLeft(
				colors[sourceIndex % colors.length]);
		    }
		}
	    }
	    this.draw();
	    nextTick();
	} catch (NullPointerException e)
	{
	    // if road is still not ready, do nothing
	}
    }

    private void move()
    {

	OrigemCarro result[];
	ArrayList<Segment> newRoad = this.copyRoad();

	// segmentos extremos nao têm mexidas
	for (int i = road.size() - 2; i >= 1; i--)
	{

	    boolean lb = road.get(i - 1).isCarLeft();
	    boolean lm = road.get(i).isCarLeft();
	    boolean lf = road.get(i + 1).isCarLeft();
	    boolean rb = road.get(i - 1).isCarRight();
	    boolean rm = road.get(i).isCarRight();
	    boolean rf = road.get(i + 1).isCarRight();

	    result = alg.result(lb, lm, lf, rb, rm, rf);
	    // result esquerdo
	    switch (result[0])
	    {
		case BACK:
		    newRoad.get(i).setCarLeft(road.get(i - 1).getCarLeft());
		    break;
		case RIGHT:
		    newRoad.get(i).setCarLeft(road.get(i).getCarRight());
		    break;
		case EMPTY:
		    newRoad.get(i).setCarLeft(null);
		    break;
		case STAY:
		    newRoad.get(i).setCarLeft(road.get(i).getCarLeft());
		    break;
		default:
		    break;
	    }
	    switch (result[1])
	    {
		case BACK:
		    newRoad.get(i).setCarRight(road.get(i - 1).getCarRight());
		    break;
		case LEFT:
		    newRoad.get(i).setCarRight(road.get(i).getCarLeft());
		    break;
		case EMPTY:
		    newRoad.get(i).setCarRight(null);
		    break;
		case STAY:
		    newRoad.get(i).setCarRight(road.get(i).getCarRight());
		    break;
		default:
		    break;
	    }
	}
	this.road = newRoad;
    }

    private ArrayList<Segment> copyRoad()
    {
	ArrayList<Segment> res = new ArrayList<>();
	for (Segment s : road)
	{
	    res.add(s.copy());
	}
	return res;
    }

    public void draw(GL2 gl)
    {
	this.gl = gl;
	draw();
    }

    private void draw()
    {
	// draw all the roads first so the cars show properly
	for (Segment s : road)
	{
	    s.drawSegment(gl);
	}

	// draw all the cars from the front to the back
	for (int i = 0; i < road.size(); i++)
	{
	    road.get(road.size() - 1 - i).drawCars(gl);
	}
    }

    private void nextTick()
    {
	tick++;
    }
}
