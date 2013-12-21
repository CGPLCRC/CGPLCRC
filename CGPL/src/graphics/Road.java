/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics;

import cgrafica.AlgoritmoCores;
import cgrafica.AlgoritmoCores.OrigemCarro;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.media.opengl.GL2;

/**
 *
 * @author cv
 */
public class Road {

    private static final int FRAMES_PER_SECOND = 5;
    //available colors for sources
    private static final Color[] colors = new Color[]{
        Color.BLUE,
        //LIGHTSALMON
        new Color(255, 160, 122),
        Color.MAGENTA,
        Color.DARK_GRAY,
        Color.GREEN,
        Color.CYAN,
        Color.ORANGE,
        Color.PINK,
        Color.RED,
        //AQUAMARINE
        new Color(127, 255, 212)
    };

    private ArrayList<Segment> road;
    private int size;
    private boolean straight;
    private ArrayList<Integer> sources;
    private ArrayList<Integer> emissions;
    private int tick;
    private final AlgoritmoCores alg;
    private GL2 gl;

    public Road() {

        int inputTamanho = 10;

        //soma de 2 segmentos extremos
        size = inputTamanho + 2;
        road = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Segment s = new Segment(0, 0, (-8) * i);
            road.add(s);
        }
        straight = true;
        sources = new ArrayList<>();
        sources.add(1);
        sources.add(4);
        sources.add(5);
        for (int i : sources) {
            road.get(i).setIsSource(true);
            road.get(i).setColor(colors[i]);
        }
        emissions = new ArrayList<>();
        emissions.add(2);
        emissions.add(4);
        emissions.add(5);
        tick = 0;
        alg = AlgoritmoCores.getInstance();
    }

    public void tick() {
        try {
            this.move();
            for (int i = 0; i < sources.size(); i++) {
                // is it time for merge?
                if (tick % emissions.get(i) == 0) {
                    int sourceIndex = sources.get(i);
                    road.get(sourceIndex).mergeCarLeft(colors[sourceIndex]);
                }
            }
            this.draw();
            nextTick();
        } catch (NullPointerException e) {
            //if road is still not ready, do nothing
        }
    }

    private void move() {

        OrigemCarro result[];
        ArrayList<Segment> newRoad = this.copyRoad();

        //segmentos extremos nao têm mexidas
        for (int i = 1; i < road.size() - 1; i++) {

            boolean lb = road.get(i - 1).isCarLeft();
            boolean lm = road.get(i).isCarLeft();
            boolean lf = road.get(i + 1).isCarLeft();
            boolean rb = road.get(i - 1).isCarRight();
            boolean rm = road.get(i).isCarRight();
            boolean rf = road.get(i + 1).isCarRight();

            result = alg.result(lb, lm, lf, rb, rm, rf);
            // result esquerdo
            switch (result[0]) {
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

            switch (result[1]) {
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

    private ArrayList<Segment> copyRoad() {

        ArrayList<Segment> res = new ArrayList<>();

        for (Segment s : road) {
            res.add(s.copy());
        }
        return res;

    }

    public void draw(GL2 gl) {
        this.gl = gl;
        draw();
    }

    private void draw() {
        //draw all the roads first so the cars show properly
        for (Segment s : road) {
            s.drawRoad(gl);
        }

        // draw all the cars from the front to the back
        for (int i = 0; i < road.size(); i++) {
            road.get(road.size() - 1 - i).drawCars(gl);
        }

    }

    private void nextTick() {
        tick++;
    }

}
