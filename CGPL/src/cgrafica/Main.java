package cgrafica;

import graphics.Road;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;

/**
 * Create a window with a single canvas component. An instance of
 * {@literal StaticListener} is responsible for handling OpenGL and key events.
 *
 * @author Pedro Mariano
 */
public class Main {

    static public void main(String[] args) throws InterruptedException {
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        GLCanvas canvas = new GLCanvas(caps);

        Frame frame = new Frame("Controlo da câmara e dos parâmetros da projecção");
        frame.setSize(300, 300);
        frame.add(canvas);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        Road road = new Road();
        StaticListener listener;

        listener = new StaticListener(canvas, road);
        canvas.addGLEventListener(listener);
        canvas.addKeyListener(listener);


        while (true) {
            road.tick();
            canvas.display();
            TimeUnit.MILLISECONDS.sleep(500);
        }

    }

}
