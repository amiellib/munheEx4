package Map;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import Algorithems.Algorithms;
import Geom.Point3D;
import Robot.Play;
import entities.*;
import entities.Box;

/**
 * 
 * @author Shilo Gilor and Amiel Liberman
 *
 */
public class GUI_Map  extends JFrame 
{
	private boolean loaded = false , automate = false;
	static private Game my_game = new Game();
	private BufferedImage backgroundImage;
	private Algorithms algo; 
	private JMenuBar menuBarstatic;
	private JMenu fileMenu , game_menu ,speed  , csv , auto ;
	private JMenuItem clean_map , slowdown ,automated, fast_forwards , exit , run , new_file , open, accuracy_level,my_packman;
	private Thread thread;
	static private Play play1 ;
	private double time =100000.0 , mypackman_angle =0.0;
	ArrayList<String> board_data ;
	JTextField info;
	private String data = "";
	public GUI_Map(Map map) throws IOException 
	{
		super("PackMan Map");
		this.algo = new Algorithms(map);
		backgroundImage = map.getBackgroundImage();
		menuBarstatic = new JMenuBar(); 
		info = new JTextField(data);
		info.setEditable(false);
		auto = new JMenu("Auto");
		fileMenu = new JMenu("File");
		game_menu = new JMenu("game"); 
		speed = new JMenu("Speed"); 
		csv=new JMenu("improt");
		menuBarstatic.add(fileMenu); 
		menuBarstatic.add(game_menu); 
		menuBarstatic.add(speed); 
		menuBarstatic.add(csv);
		menuBarstatic.add(auto);
		menuBarstatic.add(info);
		slowdown = new JMenuItem("slow down");
		slowdown.setAccelerator(KeyStroke.getKeyStroke('D', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		fast_forwards = new JMenuItem("fast forwards");
		fast_forwards.setAccelerator(KeyStroke.getKeyStroke('U', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		exit = new JMenuItem("Exit");
		exit.setAccelerator(KeyStroke.getKeyStroke('E', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		run = new JMenuItem("run");
		run.setAccelerator(KeyStroke.getKeyStroke('R', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		my_packman = new JMenuItem("my_packman");
		my_packman.setAccelerator(KeyStroke.getKeyStroke('P', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		open = new JMenuItem("open");
		open.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		automated = new JMenuItem("automated");
		accuracy_level = new JMenuItem("accuracy_level");
		accuracy_level.setAccelerator(KeyStroke.getKeyStroke('L', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));


		speed.add(slowdown);
		speed.addSeparator();
		speed.add(fast_forwards);

		game_menu.add(my_packman);
	
		fileMenu.add(run);
		fileMenu.addSeparator();
		fileMenu.add(exit);
		auto.add(automated);

		csv.add(open);
		
		setJMenuBar(menuBarstatic);

		Handler handler = new Handler();
		getContentPane().addMouseListener(handler);
		menuBarstatic.addMouseListener(handler);
		my_packman.addActionListener(handler);
		slowdown.addActionListener(handler);
		fast_forwards.addActionListener(handler);
		exit.addActionListener(handler);
		run.addActionListener(handler);
		open.addActionListener(handler);
		automated.addActionListener(handler);


	}

	/**
	 * This function repaints the map with the data it has
	 */
	public void paint(Graphics g) 
	{
		if (loaded)
		{
			if (automate && my_game.getFruit_list().size()>0)
			{			
				mypackman_angle = algo.get_angle(my_game.getMypackman(),  algo.get_next_point(my_game));
			}
			play1.rotate(mypackman_angle);
			my_game = algo.create_game(play1.getBoard() ,my_game.getSpeed_rate());
		}
		
		g.drawImage(backgroundImage.getScaledInstance(this.getWidth(),this.getHeight(),backgroundImage.SCALE_SMOOTH), 0, 0, null);

		for(Box box :my_game.getBox_list())
		{
			g.drawImage(box.getBox_image(),(int) (algo.convert_gps_to_pixel(box.getGps2(), getHeight(), getWidth()).x()), (int)(algo.convert_gps_to_pixel(box.getGps2(), getHeight(), getWidth()).y()), (int) (getWidth()*algo.get_box_X_percentage(box.getGps1(), box.getGps2())), (int) (getHeight()*algo.get_box_Y_percentage(box.getGps1(), box.getGps2())), null);
		}
		for (Fruit fruit : my_game.getFruit_list())
		{
			g.drawImage(fruit.getFruit_image(),(int) (algo.convert_gps_to_pixel(fruit.getGps(), getHeight(), getWidth()).x())-5, (int)(algo.convert_gps_to_pixel(fruit.getGps(), getHeight(), getWidth()).y())-5,10, 10, null);
		}
		for(Packman packman :my_game.getPackman_list())
		{
			g.drawImage(packman.getPackman_image(),(int) (algo.convert_gps_to_pixel(packman.getGps(), getHeight(), getWidth()).x())-5, (int)(algo.convert_gps_to_pixel(packman.getGps(), getHeight(), getWidth()).y())-5,10, 10, null);
		}
		for(Ghost ghost :my_game.getGhost_list())
		{
			g.drawImage(ghost.getGhost_image(),(int) (algo.convert_gps_to_pixel(ghost.getGps(), getHeight(), getWidth()).x())-5, (int)(algo.convert_gps_to_pixel(ghost.getGps(), getHeight(), getWidth()).y())-5,10, 10, null);
		}
		if (my_game.getMypackman()!=null)
			g.drawImage(my_game.getMypackman().getPackman_image(),(int) (algo.convert_gps_to_pixel(my_game.getMypackman().getGps(), getHeight(), getWidth()).x())-5, (int)(algo.convert_gps_to_pixel(my_game.getMypackman().getGps(), getHeight(), getWidth()).y())-5,10, 10, null);

		info.setText(data);
		menuBarstatic.repaint();
	}

	/**
	 * 
	 * @author Shilo Gilor and Amiel Liberman
	 *
	 */

	public class Handler implements MouseListener , ActionListener {
		@Override
		public void mouseClicked(MouseEvent e)
		{
			System.out.println(algo.convert_gps_to_meters(algo.convert_pixel_to_gps(new Point3D(e.getX(),e.getY()+40,0.0), getHeight(), getWidth()) ));
			if (my_game.getMypackman()==null)
			{
				my_game.setMypackman(new MyPackman(0, algo.convert_pixel_to_gps(new Point3D(e.getX(),e.getY()+40,0.0), getHeight(), getWidth()) , 20 , 1));
				my_game.getMypackman().setGps(algo.convert_pixel_to_gps(new Point3D(e.getX(),e.getY()+40,0.0), getHeight(), getWidth()));
				play1.setInitLocation(my_game.getMypackman().getGps().x(),my_game.getMypackman().getGps().y());
				repaint();
			}
			else
			{
				mypackman_angle =algo.get_angle(my_game.getMypackman(),  algo.convert_pixel_to_gps(new Point3D(e.getX(),e.getY()+40,0.0) ,getHeight(), getWidth()));
			}
		}
		

		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}

		/**
		 * This is the action done function
		 */
		@Override
		public void actionPerformed(ActionEvent e) 
		{


		
			if(e.getSource()==automated) 
			{
				if (my_game.getMypackman()==null)
				{
					my_game.setMypackman(new MyPackman(0, algo.get_start_location(my_game) , 20 , 1));
					play1.setInitLocation(my_game.getMypackman().getGps().x()+0.00001,my_game.getMypackman().getGps().y()+0.00001);
				}
				automate=true;
				repaint();
			}
			if(e.getSource()==clean_map) 
			{
				reset_params();
				my_game.getFruit_list().clear();
				my_game.getPackman_list().clear();
				repaint();
			}
			if(e.getSource()==slowdown) 
			{
				my_game.setSpeed_rate(my_game.getSpeed_rate()-100);
			}
			if(e.getSource()==fast_forwards) 
			{
				my_game.setSpeed_rate(my_game.getSpeed_rate()+100);
			}
			if(e.getSource()==exit) 
			{
				System.exit(0);
			}
			if(e.getSource()==run) 
			{
				if (thread!=null )
					thread.stop();
				repaint();
				play1.start(); 
				loaded =true;
				thread = new Thread() 
				{
					@Override
					public void run() {thread_repainter();}
				};
				thread.start();

			}
			if(e.getSource()==new_file) 
			{

				reset_params();
				my_game.setMypackman(null);
				repaint();
			}
			if(e.getSource()==open) 
			{
				my_game.setMypackman(null);
				try 
				{
					JFrame parentFrame = new JFrame();
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Specify a file to open");
					if (fileChooser.showOpenDialog(parentFrame) == JFileChooser.APPROVE_OPTION) {
						my_game = algo.get_data_from_csv_4(fileChooser.getSelectedFile().toString());
						play1 = new Play(fileChooser.getSelectedFile().toString());
						play1.setIDs(01110100, 01110010, 01111001);
						repaint();
					}
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
				algo.create_box_edges(my_game);
			}
		}
			

		/**
		 * This is the new thread that repaints the movement of the packman
		 */
		public void thread_repainter()  
		{
			try
			{
				while(time>=0 && my_game.getFruit_list().size()>0)
				{		
					
					repaint();
					Thread.sleep((int)(100000/my_game.getSpeed_rate()));
					time-=100;
					data = play1.getStatistics();
				}
				repaint();
				System.out.println(play1.getStatistics());
				play1.stop();
				loaded = false;
				// end game clear
			}catch (InterruptedException e) {
				e.printStackTrace();
			}


		}
		public Algorithms getAlgo() {
			return algo;
		}	
		/**
		 * This function resets the data in the map
		 */
		public void reset_params()
		{
			if (thread!=null )
				thread.stop();
			
		}
	}
}