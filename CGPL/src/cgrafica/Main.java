package cgrafica;
import graphics.Road;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;



/**
 * Create a window with a single canvas component. An instance of
 * {@literal StaticListener} is responsible for handling OpenGL and key events.
 * 
 * @author Pedro Mariano
 */
public class Main extends JFrame implements ActionListener{
	private JMenuItem start = new JMenuItem("Start");
	private JMenuItem pause = new JMenuItem("Pause");
	private JMenuItem exit = new JMenuItem("Exit");
	public static void main(String[] args) throws IOException {
	Main a = new Main();
		a.simulate();
	}
	public Main() {
		final JFrame frameP = new JFrame("Highway Simulation");
		frameP.setSize(1086, 1000);
		frameP.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// center window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension windowSize = frameP.getSize();
		int windowX = Math.max(0, (screenSize.width - windowSize.width) / 2);
		int windowY = Math.max(0, (screenSize.height - windowSize.height) / 2);
		frameP.setLocation(windowX, windowY);
		// end center window
		JButton button1 = new JButton("Open File");
		JButton button2 = new JButton("Simulate");
		JButton button3 = new JButton("Close");
		JLabel label2 = new JLabel("Highway Simulation", SwingConstants.CENTER);
		this.setLayout(null);
		label2.setVerticalAlignment(SwingConstants.TOP);
		label2.setFont(new Font("Arial", 2, 108));
		label2.setForeground(Color.WHITE);
		button1.setBounds(400, 700, 100, 50);
		button2.setBounds(540, 700, 100, 50);
		button3.setBounds(260, 700, 100, 50);
		frameP.setLayout(new BorderLayout());
		frameP.setContentPane(new JLabel(new ImageIcon(
				"C:\\Users\\Rodolfo\\Desktop\\highway.jpg")));
		frameP.setLayout(new BorderLayout());
		frameP.add(button1, BorderLayout.EAST);
		frameP.add(button1);
		frameP.add(button2);
		frameP.add(button3);
		frameP.add(label2);
		frameP.setVisible(true);	
		// event click for open file		 
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				isread = true;
				try {
					browser();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// TODO Auto-generated catch block
			}		
		});
		// close simulator
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		// simulate		
		while(true){
		button2.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {		
				breakes = true;
			}
		});	
		if(breakes)
			break;		
		}
	}
	
	private static boolean isread = false;
	private boolean breakes = false;
	private boolean pauseFont = false;
	public  void simulate() throws IOException {
		if (!isread){
			readFile("estrada");
		}	
		GLProfile glp = GLProfile.getDefault();
		GLCapabilities caps = new GLCapabilities(glp);
		GLCanvas canvas = new GLCanvas(caps);
		JFrame frame = new JFrame(
				"Controlo da câmara e dos parâmetros da projecção");
	
		JMenuBar mb = new JMenuBar();	
		JMenu file = new JMenu("File");
		exit.addActionListener(this);
		start.addActionListener(this);
		pause.addActionListener(this);
		file.add(exit);
		file.add(start);
		file.add(pause);
                mb.add(file);
		frame.setJMenuBar(mb);
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
			road.tick(pauseFont);
			canvas.display();	
			try {
        			TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	static int [][] angulo = new int [10][2];
	static int  segmento;
	private void browser() throws IOException {
		isread = true;
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("choosertitle");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setAcceptAllFileFilterUsed(false);
		int returned = chooser.showOpenDialog(this);
		if (returned == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
                	try {
				//BufferedReader br = new BufferedReader(new FileReader(file));
				readFile(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println("getSelectedFile() : "
					+ chooser.getSelectedFile());			
		} else {
			readFile("estrada");
		}
	}

	private void readFile(File file) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(file));
		String line;
		int i = 0;
		int j = 0;
		segmento = Integer.parseInt(in.readLine());
		while ((line = in.readLine()) != null) {
            j = 0;
            for (String token : line.split("\\s*[ ]\\s*")) {
                angulo[i][j] = Integer.parseInt(token);
                j++;
            }
            i++;
        }
		in.close();
	}

	public static void readFile(String file) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(file));
		String line;
		int i = 0;
		int j = 0;
		segmento = Integer.parseInt(in.readLine());
		while ((line = in.readLine()) != null) {
            j = 0;
            for (String token : line.split("\\s*[ ]\\s*")) {
                angulo[i][j] = Integer.parseInt(token);
                j++;
            }
            i++;
        }
		in.close();
	}
	
	public void actionPerformed(ActionEvent E){
		if(E.getSource() == exit) {
			System.exit(0);
		}
		if(E.getSource() == pause) {
			pauseFont = true;
		}
		if(E.getSource() == start) {
			pauseFont = false;
		}
	}
	public static int getSegmento(){
		return segmento;
	}
}