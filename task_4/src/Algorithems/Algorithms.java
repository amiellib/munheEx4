package Algorithems;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import Coords.MyCoords;
import Geom.Point3D;
import entities.*;

/**
 * This class is the main algorithms for calculating many things
 * @author Shilo Gilor and Amiel Liberman
 *
 */
public class Algorithms 
{
	private MyCoords cord=new MyCoords(); // to be able to do points algorithms
	private double ORIGIN_LON , ORIGIN_LAT , CORNER_LON , CORNER_LAT , TOTAL_DISTANCE_X ,TOTAL_DISTANCE_Y ,TOTAL_DISTANCE_ANGEL_LON ,TOTAL_DISTANCE_ANGEL_LAT;
	private Random randomNum = new Random();
	private double step_value = 1.5 , distance_from_corner =5.0;
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

	public double get_box_X_percentage(Point3D lon1 , Point3D lon2)
	{
		return (convert_gps_to_meters(new Point3D(ORIGIN_LON ,lon1.y() , 0)).x() - convert_gps_to_meters(new Point3D(ORIGIN_LON ,lon2.y() , 0)).x()) / TOTAL_DISTANCE_X;
	}
	public double get_box_Y_percentage(Point3D lat1 , Point3D lat2)
	{
		return (convert_gps_to_meters(new Point3D(lat1.x() ,ORIGIN_LAT , 0)).y() - convert_gps_to_meters(new Point3D(lat2.x() ,ORIGIN_LAT , 0)).y()) / TOTAL_DISTANCE_Y;
	}

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

	public Point3D get_vector(Point3D mypackman ,Point3D mouse)
	{
		Point3D meters_start = convert_gps_to_meters(mypackman);
		Point3D meters_end = convert_gps_to_meters(mouse);
		Point3D vect = new Point3D(meters_end.x() - meters_start.x() , meters_end.y() - meters_start.y() , meters_end.z() - meters_start.z());
		double t = (Math.sqrt(vect.x()*vect.x() + vect.y()*vect.y() + vect.z()*vect.z()))/10;
		return new Point3D (vect.x() /t , vect.y()/t , vect.z()/t);
	}
	public boolean does_hit_block(Point3D start , Point3D end , Box box , String direction)
	{
		Point3D meters_start = convert_gps_to_meters(start);
		Point3D meters_end = convert_gps_to_meters(end);
		Point3D left_bottom = convert_gps_to_meters(box.getGps1());
		Point3D right_top = convert_gps_to_meters(box.getGps2());
		Vector vector = new Vector(meters_start , meters_end);
		double x_value , y_value;
		switch (direction)
		{
			case "top" : 
				x_value = vector.give_y_get_x(right_top.y());
				if (x_value>left_bottom.x()-step_value && x_value<right_top.x()+step_value && x_value<Math.max(meters_start.x(), meters_end.x()) && x_value>Math.min(meters_start.x(), meters_end.x()))
				{
					return true;
				}
				break;
			case "bottom" :
				x_value = vector.give_y_get_x(left_bottom.y());
				if (x_value>left_bottom.x()-step_value && x_value<right_top.x()+step_value && x_value<Math.max(meters_start.x(), meters_end.x()) && x_value>Math.min(meters_start.x(), meters_end.x()))
				{
					return true;
				}
				break;
			case "left" :
				y_value = vector.give_x_get_y(left_bottom.x());
				if (y_value<left_bottom.y()+step_value && y_value>right_top.y()-step_value && y_value<Math.max(meters_start.y(), meters_end.y()) && y_value>Math.min(meters_start.y(), meters_end.y()))
				{
					return true;
				}
				break;
			case "right" :
				y_value = vector.give_x_get_y(right_top.x());
				if (y_value<left_bottom.y()+step_value && y_value>right_top.y()-step_value && y_value<Math.max(meters_start.y(), meters_end.y()) && y_value>Math.min(meters_start.y(), meters_end.y()))
				{
					return true;
				}
				break;
			default : ;
			
		}
		return false;
	}
	
	
	/*
		Point3D vect = new Point3D(meters_end.x() - meters_start.x() , meters_end.y() - meters_start.y() , meters_end.z() - meters_start.z());
		Point3D temp_point;
		double dist = Math.sqrt(vect.x()*vect.x() + vect.y()*vect.y() + vect.z()*vect.z());
		for (int i=0;i<dist;i++)
		{
			temp_point = new Point3D(meters_start.x() +vect.x()/dist*i ,meters_start.y() +vect.y()/dist*i ,meters_start.z() +vect.z()/dist*i);
			switch (direction)
			{
				case "top" : 
					if (temp_point.x()>left_bottom.x()-step_value && temp_point.x()<right_top.x()+step_value && temp_point.y()<right_top.y()+step_value && temp_point.y()>right_top.y()-step_value)
						return true;
					break;
				case "bottom" :
					if (temp_point.x()>left_bottom.x()-step_value && temp_point.x()<right_top.x()+step_value && temp_point.y()<left_bottom.y()+step_value && temp_point.y()>left_bottom.y()-step_value)
						return true;
					break;
				case "left" :
					if (temp_point.x()>left_bottom.x()-step_value && temp_point.x()<left_bottom.x()+step_value && temp_point.y()<left_bottom.y()+step_value && temp_point.y()>right_top.y()-step_value)
						return true;
					break;
				case "right" :
					if (temp_point.x()>right_top.x()-step_value && temp_point.x()<right_top.x()+step_value && temp_point.y()<left_bottom.y()+step_value && temp_point.y()>right_top.y()-step_value)
						return true;
					break;
				default : continue;
				
			}
		}
		return false;
	}*/
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
	public Point3D move_mypackman(MyPackman mypackman ,Point3D vect)
	{
		Point3D meters_start = convert_gps_to_meters(mypackman.getGps());	
		return convert_meters_to_gps(new Point3D(meters_start.x() + vect.x() , meters_start.y() + vect.y() , meters_start.z() + vect.z()));
	}

	public Point3D get_start_location(Game game)
	{
		return game.getFruit_list().get(randomNum.nextInt(game.getFruit_list().size()-1)).getGps();
	}

	public Point3D get_closesed_fruit(Game game)
	{
		Point3D me = convert_gps_to_meters(game.getMypackman().getGps());
	
		double temp_distance;
		int shortest_fruit_id = 0;
		double min_value =cord.distance3d(game.getMypackman().getGps(), game.getFruit_list().get(0).getGps());
		for (int j = 1; j<game.getFruit_list().size();j++)
		{
			temp_distance = cord.distance3d(game.getMypackman().getGps(), game.getFruit_list().get(j).getGps());
			if(min_value>temp_distance && temp_distance>0.8)
			{
				min_value = temp_distance;
				shortest_fruit_id = j;
			}
		}
		Point3D to_return = game.getFruit_list().get(shortest_fruit_id).getGps();
		for (Box box : game.getBox_list())
		{
			Point3D left_bottom = convert_gps_to_meters(box.getGps1());
			Point3D right_top = convert_gps_to_meters(box.getGps2());

			boolean top = does_hit_block (game.getMypackman().getGps() ,game.getFruit_list().get(shortest_fruit_id).getGps() , box , "top");
			boolean bottom = does_hit_block (game.getMypackman().getGps() ,game.getFruit_list().get(shortest_fruit_id).getGps() , box , "bottom");
			boolean left = does_hit_block (game.getMypackman().getGps() ,game.getFruit_list().get(shortest_fruit_id).getGps() , box , "left");
			boolean right = does_hit_block (game.getMypackman().getGps() ,game.getFruit_list().get(shortest_fruit_id).getGps() , box , "right");
	//		System.out.println("top - " + top + " , bottom - " + bottom + " , left - " + left + " , right - " + right);
			if (top && bottom && right)
			{
				System.out.println("right corner 1");
				to_return = convert_meters_to_gps(new Point3D(me.x()+10 ,(left_bottom.y()+right_top.y())/2 ,left_bottom.z()));
				break;
			}
			else if (top && bottom && left)
			{
				System.out.println("right corner 2");
				to_return = convert_meters_to_gps(new Point3D(me.x()-10 , (left_bottom.y()+right_top.y())/2 ,left_bottom.z()));
				break;
			}
			else if (left && right && bottom )
			{
				System.out.println("bottom corner 1");

				to_return = convert_meters_to_gps(new Point3D((left_bottom.x()+right_top.x())/2 , me.y()+10 ,left_bottom.z()));
				break;
			}
			else if (left && right && top )
			{
				System.out.println("bottom corner 2");

				to_return = convert_meters_to_gps(new Point3D((left_bottom.x()+right_top.x())/2 , me.y()-10 ,left_bottom.z()));
				break;
			}
			else if (bottom && left)
			{
				System.out.println("left bottom");
				to_return = convert_meters_to_gps(new Point3D(left_bottom.x()-distance_from_corner , left_bottom.y()+distance_from_corner ,left_bottom.z()));
				break;
			}
			else if (bottom && right)
			{
				System.out.println("bottom right");
				to_return = convert_meters_to_gps(new Point3D(right_top.x()+distance_from_corner , left_bottom.y()+distance_from_corner ,left_bottom.z()));
				break;
			}
			else if (left && top )
			{
				System.out.println("left top");

				to_return = convert_meters_to_gps(new Point3D(left_bottom.x()-distance_from_corner , right_top.y()-distance_from_corner ,left_bottom.z()));
				break;
			}
			else if (right && top )
			{
				System.out.println("right top");
				to_return = convert_meters_to_gps(new Point3D(right_top.x()+distance_from_corner , right_top.y()-distance_from_corner ,left_bottom.z()));
				break;
			}
			else if(right && left)
			{
				if ((left_bottom.y()+right_top.y())/2<me.y()) // go down
				{
					System.out.println("left right go down");
					if (me.x()>right_top.x())
					{
						to_return = convert_meters_to_gps(new Point3D (right_top.x()+distance_from_corner , left_bottom.y()+distance_from_corner , me.z()));
						break;
					}
					else
					{
						to_return = convert_meters_to_gps(new Point3D (left_bottom.x()-distance_from_corner , left_bottom.y()+distance_from_corner , me.z()));
						break;
					}
				}
				else // go up
				{
					System.out.println("left right go up");
					if (me.x()>right_top.x())
					{
						to_return = convert_meters_to_gps(new Point3D (right_top.x()+distance_from_corner , right_top.y()-distance_from_corner , me.z()));
						break;
					}
					else
					{
						to_return = convert_meters_to_gps(new Point3D (left_bottom.x()-distance_from_corner , right_top.y()-distance_from_corner , me.z()));
						break;
					}		
				}
			}
			else if (top && bottom)
			{
				if ((left_bottom.x() + right_top.x())/2 > me.x()) // go left
				{
					System.out.println("top bottom go left");
					if (me.y()>right_top.y())
					{
						to_return = convert_meters_to_gps(new Point3D (left_bottom.x()-distance_from_corner , left_bottom.y()+distance_from_corner , me.z()));
						break;
					}
					else
					{
						to_return = convert_meters_to_gps(new Point3D (left_bottom.x()-distance_from_corner , right_top.y()-distance_from_corner , me.z()));
						break;
					}
				}
				else // go right
				{
					System.out.println("top bottom go right");
					if (me.y()>right_top.y())
					{
						to_return = convert_meters_to_gps(new Point3D (right_top.x()+distance_from_corner , left_bottom.y()+distance_from_corner , me.z()));
						break;
					}
					else
					{
						to_return = convert_meters_to_gps(new Point3D (right_top.x()+distance_from_corner , right_top.y()-distance_from_corner , me.z()));
						break;
					}				
				}
			}
		}
		return to_return;
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
}
