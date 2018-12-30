package Map;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
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
	private String[] packmans = {"src/resources/packman_eating_3.png" , "src/resources/packman_eating2.png" ,"src/resources/packman_eating1.png" , "src/resources/packman_eating2.png" };
	private String MyPackman ="";
	private boolean loaded = false;
	//	private int fruit_id = 0 , packman_id =0 , packman_counter =0 , global_time;
	static private Game my_game = new Game();
	private BufferedImage backgroundImage;
	private Algorithms algo; 
	private JMenuBar menuBarstatic;
	private JMenu fileMenu , game_menu ,speed , accuracy , csv;
	private JMenuItem clean_map , slowdown , fast_forwards , exit , run , save , fruit , packman , new_file , open, accuracy_level,my_packman;
	//	private Double packman_speed = 1.0 , packman_range =1.0 , fruit_weight =1.0 , packman_height = 0.0 , fruit_height = 0.0 ,max_path_time = 0.0;
	//	private double accuracy_rate = 1.0;
	private Thread thread;
	//	private int slowest_packman_id_array ,path_counter;
	private JTextField max_time;
	private Point3D mypackman_vector = new Point3D(0,0,0);
	static private Play play1 ;
	private double time =100000.0 , mypackman_angle =0.0;
	ArrayList<String> board_data ;
	public GUI_Map(Map map) throws IOException 
	{
		super("PackMan Map");
		this.algo = new Algorithms(map);
		//		max_time = new JTextField("Max Path Time:" + max_path_time);
		//		max_time.setEditable(false);
		backgroundImage = map.getBackgroundImage();
		menuBarstatic = new JMenuBar(); 

		accuracy= new JMenu("Accuracy");
		fileMenu = new JMenu("File");
		game_menu = new JMenu("game"); 
		speed = new JMenu("Speed"); 
		csv=new JMenu("improt/export");
		menuBarstatic.add(fileMenu); 
		menuBarstatic.add(game_menu); 
		menuBarstatic.add(speed); 
		menuBarstatic.add(csv);
		//	menuBarstatic.add(accuracy);
		//		menuBarstatic.add(max_time);

		//https://stackoverflow.com/questions/13366793/how-do-you-make-menu-item-jmenuitem-shortcut link for key shortcut info
		//		clean_map = new JMenuItem("clean map");
		//		clean_map.setAccelerator(KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		slowdown = new JMenuItem("slow down");
		slowdown.setAccelerator(KeyStroke.getKeyStroke('D', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		fast_forwards = new JMenuItem("fast forwards");
		fast_forwards.setAccelerator(KeyStroke.getKeyStroke('U', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		exit = new JMenuItem("Exit");
		exit.setAccelerator(KeyStroke.getKeyStroke('E', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		run = new JMenuItem("run");
		run.setAccelerator(KeyStroke.getKeyStroke('R', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		//		save = new JMenuItem("save");
		//		save.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		//	fruit = new JMenuItem("fruit");
		//	fruit.setAccelerator(KeyStroke.getKeyStroke('F', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		my_packman = new JMenuItem("my_packman");
		my_packman.setAccelerator(KeyStroke.getKeyStroke('P', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		//	new_file = new JMenuItem("new");
		//	new_file.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		open = new JMenuItem("open");
		open.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));

		accuracy_level = new JMenuItem("accuracy_level");
		accuracy_level.setAccelerator(KeyStroke.getKeyStroke('L', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));

		accuracy.add(accuracy_level);

		speed.add(slowdown);
		speed.addSeparator();
		speed.add(fast_forwards);

		//	game_menu.add(fruit);
		game_menu.add(my_packman);
		//	game_menu.addSeparator();
		//	game_menu.add(custom_fruit_weight);
		//game_menu.add(custom_fruit_height);
		//	game_menu.addSeparator();
		//game_menu.add(custom_packman_speed);
		//	game_menu.add(custom_packman_range);
		//game_menu.add(custom_packman_height);
		//	game_menu.addSeparator();


		//	fileMenu.add(new_file);
		//	fileMenu.addSeparator();
		fileMenu.add(run);
		fileMenu.addSeparator();
		fileMenu.add(exit);

		csv.add(open);
		/*csv.addSeparator();
		csv.add(save);
		csv.addSeparator();
		csv.add(kml);
		 */
		setJMenuBar(menuBarstatic);

		Handler handler = new Handler();
		getContentPane().addMouseListener(handler);
		menuBarstatic.addMouseListener(handler);
		//	fruit.addActionListener(handler);
		my_packman.addActionListener(handler);
		slowdown.addActionListener(handler);
		fast_forwards.addActionListener(handler);
		exit.addActionListener(handler);
		run.addActionListener(handler);
		//	save.addActionListener(handler);
		//	new_file.addActionListener(handler);
		open.addActionListener(handler);

		//		accuracy_level.addActionListener(handler);

	}

	/**
	 * This function repaints the map with the data it has
	 */
	public void paint(Graphics g) 
	{
		if (loaded)
		{
			play1.rotate(mypackman_angle);
			my_game = algo.create_game(play1.getBoard());
/*			String info = play1.getStatistics();
			System.out.println(info);
			board_data = play1.getBoard();
			for(int a=0;a<board_data.size();a++) {
				System.out.println(board_data.get(a));
			}
*/		}

		
		g.drawImage(backgroundImage.getScaledInstance(this.getWidth(),this.getHeight(),backgroundImage.SCALE_SMOOTH), 0, 0, null);

		for(Box box :my_game.getBox_list())
		{
			g.drawImage(box.getBox_image(),(int) (algo.convert_gps_to_pixel(box.getGps2(), getHeight(), getWidth()).x())-5, (int)(algo.convert_gps_to_pixel(box.getGps2(), getHeight(), getWidth()).y())-5, (int) (getWidth()*algo.get_box_X_percentage(box.getGps1(), box.getGps2())), (int) (getHeight()*algo.get_box_Y_percentage(box.getGps1(), box.getGps2())), null);
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

		//		if (run_program)
		//	{
		//			slowest_packman_id_array = algo.get_max_path(paths);
		/*	for(Path path :paths)
			{
				g.setColor(path.getColor());
				for(int i=0;i<path.getLocations().size()-1;i++)
				{
					Point3D start = algo.convert_gps_to_pixel(path.getLocations().get(i), getHeight(), getWidth());
					Point3D end = algo.convert_gps_to_pixel(path.getLocations().get(i+1), getHeight(), getWidth());
					g.drawLine((int)start.x(), (int)start.y(), (int)end.x(),(int) end.y());
				}
			}
			try {
				packman_image_eating_temp = get_packman();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			path_counter=0;
			for(Path path :paths)
			{
				if (path_counter== slowest_packman_id_array)	
				{
					g.drawImage(evil_packman,(int) (algo.convert_gps_to_pixel(algo.get_location_by_time(path, global_time*my_game.getSpeed_rate()), getHeight(), getWidth()).x())-20, (int)(algo.convert_gps_to_pixel(algo.get_location_by_time(path, global_time*my_game.getSpeed_rate()), getHeight(), getWidth()).y())-20,50, 50, null);
				}
				else
				{
					g.drawImage(packman_image_eating_temp,(int) (algo.convert_gps_to_pixel(algo.get_location_by_time(path, global_time*my_game.getSpeed_rate()), getHeight(), getWidth()).x())-5, (int)(algo.convert_gps_to_pixel(algo.get_location_by_time(path, global_time*my_game.getSpeed_rate()), getHeight(), getWidth()).y())-5,30, 30, null);
				}
				path_counter++;
			}
		 */
		//}
		//		max_time.setText("Max Path Time:" + max_path_time);
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
		/*
		{
			Point3D point;
			if (is_fruit)
			{
				point = algo.convert_pixel_to_gps(new Point3D(e.getX()-10,e.getY()+30,fruit_height), getHeight(), getWidth());
				my_game.getFruit_list().add(new Fruit(fruit_id, point , fruit_weight ));
				fruit_id++;
			}
			else if (is_packman)
			{
				point = algo.convert_pixel_to_gps(new Point3D(e.getX()-10,e.getY()+30,packman_height), getHeight(), getWidth());
				my_game.getPackman_list().add(new Packman(packman_id, point , packman_speed ,packman_range ));
				packman_id++;
			}
			repaint();
		}
		 */

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


			/*		if(e.getSource().equals(fruit)) {
				is_fruit = true;
				is_packman = false;
			}
			if(e.getSource()==packman) {
				is_fruit = false;
				is_packman = true;			
			}
			 */
			if(e.getSource()==clean_map) 
			{
				reset_params();
				my_game.getFruit_list().clear();
				my_game.getPackman_list().clear();
				repaint();
			}
			if(e.getSource()==slowdown) 
			{
				my_game.setSpeed_rate(my_game.getSpeed_rate()/2);
			}
			if(e.getSource()==fast_forwards) 
			{
				my_game.setSpeed_rate(my_game.getSpeed_rate()*2);
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
				time =100000.0;
				play1.start(); // default max time is 100 seconds (1000*100 ms).
				//			paths = algo.TSP(my_game ,accuracy_rate);
				//			max_path_time = algo.get_max_path_time(paths);
				loaded =true;
				thread = new Thread() 
				{
					@Override
					public void run() {thread_repainter();}
				};
				thread.start();

			}
			/*
			if(e.getSource()==save) 
			{
				try {
					JFrame parentFrame = new JFrame();
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("Specify a file to save");
					if (fileChooser.showSaveDialog(parentFrame) == JFileChooser.APPROVE_OPTION) {
						algo.create_csv_from_game(my_game, fileChooser.getSelectedFile().toString());
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			 */
			if(e.getSource()==new_file) 
			{

				reset_params();
				my_game.getFruit_list().clear();
				my_game.getPackman_list().clear();
				repaint();
			}
			if(e.getSource()==open) 
			{
				//			reset_params();
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
			}

			/*			if(e.getSource()==kml)
			{
				paths = algo.TSP(my_game ,accuracy_rate);
				System.out.println(kml);
				JFrame parentFrame = new JFrame();
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Specify a file to save");
				if (fileChooser.showSaveDialog(parentFrame) == JFileChooser.APPROVE_OPTION) {
					algo.export_kml(paths, my_game, fileChooser.getSelectedFile().toString());

				}
			}
			if(e.getSource()==custom_packman_range)
			{
				try
				{
					packman_range=Double.parseDouble(JOptionPane.showInputDialog(	"Enter packman range"));
				}catch (NumberFormatException e1)
				{
					packman_range = 1.0;
				}	
			}
			if(e.getSource()==custom_fruit_weight)
			{
				try
				{
					fruit_weight=Double.parseDouble(JOptionPane.showInputDialog(	"Enter fruit weight"));
				}catch (NumberFormatException e1)
				{
					fruit_weight = 1.0;
				}	
			}
			if(e.getSource()==custom_packman_speed)
			{
				try
				{
					packman_speed=Double.parseDouble(JOptionPane.showInputDialog(	"Enter packman speed"));
				}catch (NumberFormatException e1)
				{
					packman_speed = 1.0;
				}
			}
			if(e.getSource()==custom_packman_height)
			{
				try 
				{
					packman_height=Double.parseDouble(JOptionPane.showInputDialog(	"Enter packman height"));
				}catch (NumberFormatException e1)
				{
					packman_height = 0.0;
				}
			}
			if(e.getSource()==custom_fruit_height)
			{
				try
				{
					fruit_height=Double.parseDouble(JOptionPane.showInputDialog(	"Enter fruit height"));
				}catch (NumberFormatException e1 )
				{
					fruit_height = 0.0;
				}
			}
			 */
			if(e.getSource()==my_packman)
			{

				/*
				try
				{
					accuracy_rate=Double.parseDouble(JOptionPane.showInputDialog(	"accuracy level"));
				}catch (NumberFormatException e1 )
				{
					accuracy_rate = 1.0;
				}
				 */
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
					Thread.sleep(1000);
					time-=1000;
				}
				repaint();
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
			//		max_path_time = 0.0;
			//		packman_speed = 1.0;
			//		packman_range =1.0;
			//		fruit_weight =1.0;
			//		packman_height = 0.0;
			//		fruit_height = 0.0;
			//		fruit_id = 0;
			//		packman_id =0;
			//		packman_counter =0;
			//		global_time = 0;
			//		is_packman = false;
			//		is_fruit = false; 
			//		run_program = false;
		}
	}
}