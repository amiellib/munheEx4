package Algorithems;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import Coords.MyCoords;
import Geom.Point3D;
import algo.*;
import entities.*;

/**
 * This class is the main algorithms for calculating many things
 * @author Shilo Gilor and Amiel Liberman
 *
 */
public class Algorithms 
{
	private static ArrayList<Edge> box_edges;
	private static List<Vertex> box_nodes;
	private static ArrayList<Point3D> box_cornors;
	private MyCoords cord=new MyCoords(); // to be able to do points algorithms
	private double ORIGIN_LON , ORIGIN_LAT , CORNER_LON , CORNER_LAT , TOTAL_DISTANCE_X ,TOTAL_DISTANCE_Y ,TOTAL_DISTANCE_ANGEL_LON ,TOTAL_DISTANCE_ANGEL_LAT;
	private Random randomNum = new Random();
	/**
	 * This constructor initializes all the fundamental data
	 * @param map the map that we are doing all the algorithms
	 */
	public Algorithms(Map map)
	{
		super();
		ORIGIN_LON = map.getLeft_bottom_corner().y();
		CORNER_LAT = map.getLeft_bottom_corner().x();
		CORNER_LON = map.getRight_top_corner().y();
		ORIGIN_LAT = map.getRight_top_corner().x();
		Point3D ORIGIN = new Point3D(ORIGIN_LAT, ORIGIN_LON , 0 );
		TOTAL_DISTANCE_X = cord.distance3d(ORIGIN, new Point3D(ORIGIN_LAT , CORNER_LON ,0));
		TOTAL_DISTANCE_Y = cord.distance3d(ORIGIN, new Point3D(CORNER_LAT , ORIGIN_LON , 0));
		TOTAL_DISTANCE_ANGEL_LON = CORNER_LON - ORIGIN_LON;
		TOTAL_DISTANCE_ANGEL_LAT = CORNER_LAT - ORIGIN_LAT;
	}
	/**
	 * 
	 * @param my_string_list is the string list of objects
	 * @param speed is the game speed
	 * @return a fully loaded game
	 */
	public Game create_game(ArrayList<String> my_string_list , double speed)
	{
		Game temp_game = new Game(speed);
		for (String entity : my_string_list)
		{
			String[] values = entity.split(",");
			switch(values[0])
			{
			case "M": temp_game.setMypackman(new MyPackman(Integer.parseInt(values[1]) , new Point3D(Double.parseDouble(values[2]) ,Double.parseDouble(values[3]) , Double.parseDouble(values[4])), Double.parseDouble(values[5]) , Double.parseDouble(values[6]) ) );
			break;
			case "P": temp_game.getPackman_list().add(new Packman(Integer.parseInt(values[1]) , new Point3D(Double.parseDouble(values[2]) ,Double.parseDouble(values[3]) , Double.parseDouble(values[4])), Double.parseDouble(values[5]) , Double.parseDouble(values[6]) ) );
			break;
			case "G": temp_game.getGhost_list().add(new Ghost(Integer.parseInt(values[1]) , new Point3D(Double.parseDouble(values[2]) ,Double.parseDouble(values[3]) , Double.parseDouble(values[4])), Double.parseDouble(values[5]) , Double.parseDouble(values[6]) ) );
			break;
			case "F": temp_game.getFruit_list().add(new Fruit(Integer.parseInt(values[1]) , new Point3D(Double.parseDouble(values[2]) ,Double.parseDouble(values[3]) , Double.parseDouble(values[4])), Double.parseDouble(values[5]) ) );
			break;
			case "B": temp_game.getBox_list().add(new Box(Integer.parseInt(values[1]) , new Point3D(Double.parseDouble(values[2]) ,Double.parseDouble(values[3]) , Double.parseDouble(values[4])),new Point3D(Double.parseDouble(values[5]) ,Double.parseDouble(values[6]) , Double.parseDouble(values[7])) , Double.parseDouble(values[8])) );
			break;
			default : continue;
			}
		}
		return temp_game;
	}

	/**
	 * 
	 * @param lon1 lon of point 1 
	 * @param lon2 lon of point 2
	 * @return the percentage of the screen
	 */
	public double get_box_X_percentage(Point3D lon1 , Point3D lon2)
	{
		return (convert_gps_to_meters(new Point3D(ORIGIN_LON ,lon1.y() , 0)).x() - convert_gps_to_meters(new Point3D(ORIGIN_LON ,lon2.y() , 0)).x()) / TOTAL_DISTANCE_X;
	}
	/**
	 * 
	 * @param lat1 lat of point 1 
	 * @param lat2 lat of point 2
	 * @return the percentage of the screen
	 */
	public double get_box_Y_percentage(Point3D lat1 , Point3D lat2)
	{
		return (convert_gps_to_meters(new Point3D(lat1.x() ,ORIGIN_LAT , 0)).y() - convert_gps_to_meters(new Point3D(lat2.x() ,ORIGIN_LAT , 0)).y()) / TOTAL_DISTANCE_Y;
	}
	/**
	 * 
	 * @param path_of_csv the dir of the file
	 * @return a fully loaded game
	 * @throws IOException exception
	 */
	public Game get_data_from_csv_4(String path_of_csv) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(path_of_csv));
		br.readLine();
		String line = br.readLine();
		ArrayList<String> my_string_list = new ArrayList<String>();
		while (line != null && !line.isEmpty()) {
			if((line.replaceAll(",","")).replaceAll(" ","").isEmpty())
				break;
			my_string_list.add(line);
			line = br.readLine();
		}
		return create_game(my_string_list , 100);
	}
	/**
	 * 
	 * @param start is the start point
	 * @param end is the end point
	 * @param game is the game presented
	 * @return if the path goes through a box
	 */
	public boolean does_hit_any_block(Point3D start , Point3D end ,Game game)
	{
		Point3D meters_start = convert_gps_to_meters(start);
		Point3D meters_end = convert_gps_to_meters(end);
		Vector vector = new Vector(meters_start , meters_end);
		double x_value , y_value;
		for (Box box : game.getBox_list())
		{
			Point3D left_bottom = convert_gps_to_meters(box.getGps1());
			Point3D right_top = convert_gps_to_meters(box.getGps2());
			if ((meters_start.x() + meters_end.x())/2 - left_bottom.x() > 0.001 && (meters_start.x() + meters_end.x())/2 - right_top.x() < -0.001 && (meters_start.y() + meters_end.y())/2 - left_bottom.y() < -0.001 && (meters_start.y() + meters_end.y())/2 - right_top.y() > 0.001) // in box
			{
				return true;
			}
			if(meters_start.y() == meters_end.y() && !((left_bottom.x() - meters_start.x() < 0.001 && left_bottom.x() - meters_end.x() < 0.001) || (right_top.x() - meters_start.x() > -0.001 && right_top.x() - meters_end.x() >-0.001)))
			{
				return true;
			}
			x_value = vector.give_y_get_x(right_top.y());
			if (x_value -left_bottom.x() >0.0001 && x_value -right_top.x() <-0.001 && x_value - Math.max(meters_start.x(), meters_end.x()) < -0.001 && x_value - Math.min(meters_start.x(), meters_end.x()) > 0.001)
			{
				return true;
			}
			x_value = vector.give_y_get_x(left_bottom.y());
			if (x_value -left_bottom.x() >0.0001 && x_value -right_top.x() <-0.001 && x_value - Math.max(meters_start.x(), meters_end.x()) < -0.001 && x_value - Math.min(meters_start.x(), meters_end.x()) > 0.001)
			{
				return true;
			}
			y_value = vector.give_x_get_y(left_bottom.x());
			if (y_value - left_bottom.y() < -0.001 && y_value - right_top.y() > 0.001 && y_value - Math.max(meters_start.y(), meters_end.y()) < -0.001 && y_value - Math.min(meters_start.y(), meters_end.y()) > 0.001)
			{
				return true;
			}
			y_value = vector.give_x_get_y(right_top.x());
			if (y_value - left_bottom.y() < -0.001 && y_value - right_top.y() > 0.001 && y_value - Math.max(meters_start.y(), meters_end.y()) < -0.001 && y_value - Math.min(meters_start.y(), meters_end.y()) > 0.001)
			{
				return true;
			}
		}		
/*		for(Ghost ghost : game.getGhost_list())
		{
			Point3D ghost_location = convert_gps_to_meters(ghost.getGps());
			y_value = vector.give_x_get_y(ghost_location.x());
			if (y_value - ghost_location.y() < 2 && y_value - ghost_location.y() > -2 && y_value - Math.max(meters_start.y(), meters_end.y()) < -0.001 && y_value - Math.min(meters_start.y(), meters_end.y()) > 0.001)
			{
				return true;
			}
		}*/
		return false;
	}
	/**
	 * 
	 * @param mypackman is my packman
	 * @param mouse is the location we want to go
	 * @return the angle needed to turn
	 */
	public double get_angle(MyPackman mypackman ,Point3D mouse)
	{
		Point3D meters_start = convert_gps_to_meters(mypackman.getGps());
		Point3D meters_end = convert_gps_to_meters(mouse);
		Point3D vect = new Point3D(meters_end.x() - meters_start.x() , meters_end.y() - meters_start.y() , meters_end.z() - meters_start.z());
		if (vect.x()>0)
			return 90 + Math.toDegrees(Math.atan(vect.y()/vect.x()));
		else 
			return 270 + Math.toDegrees(Math.atan(vect.y()/vect.x()));
	}
	/**
	 * 
	 * @param game is the game presented
	 * @return a random fruit location
	 */
	public Point3D get_start_location(Game game)
	{
		return game.getFruit_list().get(randomNum.nextInt(game.getFruit_list().size()-1)).getGps();
	}
	/**
	 * This function converts from a pixel point to a gps point
	 * @param pixel current pixel point
	 * @param height total pixel height
	 * @param width total pixel width
	 * @return the gps value of the location
	 */
	public Point3D convert_pixel_to_gps(Point3D pixel , int height, int  width)
	{
		return new Point3D(ORIGIN_LAT + (pixel.y()/height)*(TOTAL_DISTANCE_ANGEL_LAT),ORIGIN_LON+(pixel.x()/width)*(TOTAL_DISTANCE_ANGEL_LON) , pixel.z());
	}
	/**
	 * This function converts from a gps point to a pixel point
	 * @param gps current location
	 * @param height total pixel height
	 * @param width total pixel width
	 * @return the pixel coordinates 
	 */
	public Point3D convert_gps_to_pixel(Point3D gps  , int height, int width )
	{
		return new Point3D(width*(gps.y() - ORIGIN_LON)/(TOTAL_DISTANCE_ANGEL_LON)  ,height*(gps.x() - ORIGIN_LAT)/(TOTAL_DISTANCE_ANGEL_LAT) , gps.z());
	}
	/**
	 * This function converts from a meters point to a gps point
	 * @param meters current meters location
	 * @return gps location
	 */
	public Point3D convert_meters_to_gps(Point3D meters)
	{
		return new Point3D(ORIGIN_LAT + meters.y()/TOTAL_DISTANCE_Y*(TOTAL_DISTANCE_ANGEL_LAT) ,ORIGIN_LON+meters.x()/TOTAL_DISTANCE_X*(TOTAL_DISTANCE_ANGEL_LON) , meters.z());
	}
	/**
	 * This function converts from a gps point to a meters point
	 * @param gps current gps location
	 * @return meters location
	 */
	public Point3D convert_gps_to_meters(Point3D gps)
	{
		return new Point3D(TOTAL_DISTANCE_X*(gps.y() - ORIGIN_LON)/(TOTAL_DISTANCE_ANGEL_LON) ,TOTAL_DISTANCE_Y*(gps.x() - ORIGIN_LAT)/(TOTAL_DISTANCE_ANGEL_LAT) , gps.z());
	}

	/**
	 * This function gets a game and returns a matrix of distances/speed values
	 * @param game the fully loaded game
	 * @return the matrix of time distances and the min value
	 */
	public MatrixMin get_matrix_min(Game game)
	{
		double min_value;
		MatrixMin matrixmin = new MatrixMin(new double [game.getPackman_list().size()][game.getFruit_list().size()],new int [game.getPackman_list().size()]);
		for (int i = 0; i<game.getPackman_list().size();i++)
		{
			min_value =cord.distance3d(game.getPackman_list().get(i).getGps(), game.getFruit_list().get(0).getGps())/game.getPackman_list().get(i).getSpeed();
			matrixmin.matrix[i][0] =  cord.distance3d(game.getPackman_list().get(i).getGps(), game.getFruit_list().get(0).getGps())/game.getPackman_list().get(i).getSpeed();
			matrixmin.array_min[i]=0;		
			for (int j = 1; j<game.getFruit_list().size();j++)
			{
				matrixmin.matrix[i][j] = cord.distance3d(game.getPackman_list().get(i).getGps(), game.getFruit_list().get(j).getGps())/game.getPackman_list().get(i).getSpeed();
				if(min_value>matrixmin.matrix[i][j])
				{
					matrixmin.array_min[i] = j;
					min_value = matrixmin.matrix[i][j];
				}
			}
		}
		return matrixmin;
	}
	/**
	 * 
	 * @param game is the game presented
	 * @return all the cornors points
	 */
	public ArrayList<Point3D> get_all_box_cornors(Game game)
	{
		box_cornors = new ArrayList<Point3D>();
		for (Box box : game.getBox_list())
		{
			box_cornors.add(new Point3D(box.getGps1()));
			box_cornors.add(new Point3D(box.getGps1().x() , box.getGps2().y()));
			box_cornors.add(new Point3D(box.getGps2()));
			box_cornors.add(new Point3D(box.getGps2().x() , box.getGps1().y()));
		}
/*		for (Ghost ghost :game.getGhost_list())
		{
			Point3D temp = convert_gps_to_meters(ghost.getGps());
			box_cornors.add(new Point3D(convert_meters_to_gps(new Point3D(temp.x()+3 , temp.y()+3 , temp.z()))));
			box_cornors.add(new Point3D(convert_meters_to_gps(new Point3D(temp.x()-3 , temp.y()+3 , temp.z()))));
			box_cornors.add(new Point3D(convert_meters_to_gps(new Point3D(temp.x()+3 , temp.y()-3 , temp.z()))));
			box_cornors.add(new Point3D(convert_meters_to_gps(new Point3D(temp.x()-3 , temp.y()-3 , temp.z()))));
		}*/
		return box_cornors;
	}
	/**
	 * This function creates the paths able to go from box edges
	 * @param game is the game presented
	 */
	public void create_box_edges(Game game)
	{
		box_nodes = new ArrayList<Vertex>();
		box_edges = new ArrayList<Edge>();
		ArrayList<Point3D> cornors = new ArrayList<Point3D>();
		cornors.addAll(get_all_box_cornors(game));
		for (int i = 0; i < cornors.size(); i++) {
			Vertex location = new Vertex(Integer.toString(i), Integer.toString(i));
			box_nodes.add(location);
		}
		int counter=0;
		for(int i=0;i<cornors.size()-1;i++)
		{
			for(int j=i+1;j<cornors.size();j++)
			{
				if (!does_hit_any_block(cornors.get(i) , cornors.get(j) , game))
				{
					box_edges.add(new Edge("Edge_"+counter , box_nodes.get(i) , box_nodes.get(j) , convert_gps_to_meters(cornors.get(i)).distance2D(convert_gps_to_meters(cornors.get(j))) ) );
					counter++;
					box_edges.add(new Edge("Edge_"+counter  , box_nodes.get(j) , box_nodes.get(i) , convert_gps_to_meters(cornors.get(i)).distance2D(convert_gps_to_meters(cornors.get(j))) ) );
					counter++;
				}
			}		
		}
	}
	/**
	 * 
	 * @param game is the game presented
	 * @param fruit is the fruit to go to
	 * @return the point of next point to go
	 */
	public Point3D get_next_point(Game game , Fruit fruit)
	{
		int counter=box_edges.size();
		ArrayList<Point3D> cornors = new ArrayList<Point3D>();
		cornors.addAll(box_cornors);
		List<Vertex> nodes = new ArrayList<Vertex>();
		nodes.addAll(box_nodes);
		int node_size = nodes.size();
		nodes.add(new Vertex(Integer.toString(node_size) , Integer.toString(node_size)));
		nodes.add(new Vertex(Integer.toString(node_size+1) , Integer.toString(node_size+1)));
		List<Edge> edges = new ArrayList<Edge>();
		edges.addAll(box_edges);
		Point3D packman_meters =convert_gps_to_meters(game.getMypackman().getGps());
		Point3D fruit_meters =convert_gps_to_meters(fruit.getGps());
		for (int i = 0; i<cornors.size() ; i++)
		{
			if (!does_hit_any_block(cornors.get(i) , game.getMypackman().getGps() , game))
			{
				edges.add(new Edge("Edge_"+counter , box_nodes.get(i) , nodes.get(cornors.size()) , convert_gps_to_meters(cornors.get(i)).distance2D(packman_meters) ) );
				counter++;
				edges.add(new Edge("Edge_"+counter , nodes.get(cornors.size()) , box_nodes.get(i) , convert_gps_to_meters(cornors.get(i)).distance2D(packman_meters) ) );
				counter++;
			}
			if (!does_hit_any_block(cornors.get(i) , fruit.getGps() , game))
			{
				edges.add(new Edge("Edge_"+counter , box_nodes.get(i) , nodes.get(cornors.size()+1) , convert_gps_to_meters(cornors.get(i)).distance2D(fruit_meters) ) );
				counter++;
				edges.add(new Edge("Edge_"+counter , nodes.get(cornors.size()+1) , box_nodes.get(i) , convert_gps_to_meters(cornors.get(i)).distance2D(fruit_meters) ) );
				counter++;
			}
		}
		if (!does_hit_any_block(game.getMypackman().getGps() , fruit.getGps() , game))
		{
			edges.add(new Edge("Edge_"+counter , nodes.get(cornors.size()), nodes.get(cornors.size()+1) , packman_meters.distance2D(fruit_meters) ) );
			counter++;
		}
		Graph graph = new Graph(nodes, edges);
		Algo dijkstra = new Algo(graph);
		dijkstra.execute(nodes.get(cornors.size()));
		LinkedList<Vertex> path = dijkstra.getPath(nodes.get(cornors.size()+1));
		cornors.add(game.getMypackman().getGps());
		cornors.add(fruit.getGps());
		return cornors.get(Integer.parseInt(path.get(1).getName()));
	}
	/**
	 * 
	 * @param game is the game presented
	 * @param fruit is the fruit to go to
	 * @return the distance taken to go to that fruit
	 */
	public double get_shortest_path(Game game , Fruit fruit)
	{
		int counter=box_edges.size();
		ArrayList<Point3D> cornors = new ArrayList<Point3D>();
		cornors.addAll(box_cornors);
		List<Vertex> nodes = new ArrayList<Vertex>();
		nodes.addAll(box_nodes);
		int node_size = nodes.size();
		nodes.add(new Vertex(Integer.toString(node_size) , Integer.toString(node_size)));
		nodes.add(new Vertex(Integer.toString(node_size+1) , Integer.toString(node_size+1)));
		List<Edge> edges = new ArrayList<Edge>();
		edges.addAll(box_edges);
		Point3D packman_meters =convert_gps_to_meters(game.getMypackman().getGps());
		Point3D fruit_meters =convert_gps_to_meters(fruit.getGps());
		for (int i = 0; i<cornors.size() ; i++)
		{
			if (!does_hit_any_block(cornors.get(i) , game.getMypackman().getGps() , game))
			{
				edges.add(new Edge("Edge_"+counter , box_nodes.get(i) , nodes.get(cornors.size()) , convert_gps_to_meters(cornors.get(i)).distance2D(packman_meters) ) );
				counter++;
				edges.add(new Edge("Edge_"+counter , nodes.get(cornors.size()) , box_nodes.get(i) , convert_gps_to_meters(cornors.get(i)).distance2D(packman_meters) ) );
				counter++;
			}
		}
		for (int i = 0; i<cornors.size() ; i++)
		{
			if (!does_hit_any_block(cornors.get(i) , fruit.getGps(), game))
			{
				edges.add(new Edge("Edge_"+counter , box_nodes.get(i) , nodes.get(cornors.size()+1) , convert_gps_to_meters(cornors.get(i)).distance2D(fruit_meters) ) );
				counter++;
				edges.add(new Edge("Edge_"+counter , nodes.get(cornors.size()+1) , box_nodes.get(i) , convert_gps_to_meters(cornors.get(i)).distance2D(fruit_meters) ) );
				counter++;
			}
		}
		if (!does_hit_any_block(game.getMypackman().getGps() , fruit.getGps() , game))
		{
			edges.add(new Edge("Edge_"+counter , nodes.get(cornors.size()), nodes.get(cornors.size()+1) , packman_meters.distance2D(fruit_meters) ) );
			counter++;
		}
		Graph graph = new Graph(nodes, edges);
		Algo dijkstra = new Algo(graph);
		dijkstra.execute(nodes.get(cornors.size()));
		LinkedList<Vertex> path = dijkstra.getPath(nodes.get(cornors.size()+1));
		cornors.add(game.getMypackman().getGps());
		cornors.add(fruit.getGps());
		double total_distance =0.0;
		for (int i=0;i<path.size()-1;i++)
		{
			total_distance += cornors.get(Integer.parseInt(path.get(i).getName())).distance2D(cornors.get(Integer.parseInt(path.get(i+1).getName())));
		}
		return total_distance;
	}
	/**
	 * 
	 * @param game is the game presented
	 * @return the int of the shortest path
	 */
	public int from_all_path_get_shortest(Game game)
	{
		double temp;
		double min_distance = 99999.0;
		int min_location = 0;
		for (int i = 0; i<game.getFruit_list().size();i++)
		{
			temp = get_shortest_path(game ,game.getFruit_list().get(i));
			if (temp< min_distance)
			{
				min_distance = temp;
				min_location = i;
			}
		}
		return min_location;
	}
	/**
	 * this function gets a game and return the next point the packman should go to
	 * @param game is the game presented
	 * @return the next point to go to
	 */
	public Point3D get_next(Game game)
	{
		/*Point3D me = convert_gps_to_meters(game.getMypackman().getGps());
		if (me.x()<300 && me.x()>290 && me.y()<235 && me.y()>223) // single case gets stuck
		{
			return  convert_meters_to_gps(new Point3D(me.x()+50 , me.y()+100 , me.z()));
		}*/
		int fruit_to_go = from_all_path_get_shortest(game);
		return get_next_point(game , game.getFruit_list().get(fruit_to_go));
	}
}