package Algorithems;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
	private double accuracy = 1.0;
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


	public Game create_game(ArrayList<String> my_string_list)
	{
		Game temp_game = new Game();
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
				case "B": temp_game.getBox_list().add(new Box(Integer.parseInt(values[1]) , new Point3D(Double.parseDouble(values[2]) ,Double.parseDouble(values[3]) , Double.parseDouble(values[4])),new Point3D(Double.parseDouble(values[5]) ,Double.parseDouble(values[6]) , Double.parseDouble(values[7])) , Double.parseDouble(values[8]) , Double.parseDouble(values[9]) ) );
				break;
				default : continue;
			}
		}
		return temp_game;
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
		return create_game(my_string_list);
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
	 * This function calculates the point of where the packman needs to go to hit the edge of the fruit
	 * @param start starting point 
	 * @param end where needed to go
	 * @param range the packmans radius of eating
	 * @return the point where the packman can stop and eat the fruit
	 */
	public Point3D edge_until_eat(Point3D start , Point3D end , double range)
	{
		if(cord.distance3d(start, end)<=range)
			return start;
		Point3D meters_start = convert_gps_to_meters(start);
		Point3D meters_end = convert_gps_to_meters(end);
		Point3D vect = new Point3D(meters_end.x() - meters_start.x() , meters_end.y() - meters_start.y() , meters_end.z() - meters_start.z());
		double t = (Math.sqrt(vect.x()*vect.x() + vect.y()*vect.y() + vect.z()*vect.z()) - range)/Math.sqrt(vect.x()*vect.x() + vect.y()*vect.y() + vect.z()*vect.z());
		return convert_meters_to_gps(new Point3D(meters_start.x()+vect.x()*t ,meters_start.y()+vect.y()*t , meters_start.z()+vect.z()*t));
	}

	/**
	 * This function gets data from a csv file and creates a Game with all its data 
	 * @param path_of_csv location of the csv file
	 * @return a fully loaded game
	 * @throws IOException exception
	 */
	public Game get_data_from_csv(String path_of_csv) throws IOException
	{
		Game csv_game = new Game();	
		BufferedReader br = new BufferedReader(new FileReader(path_of_csv));
		br.readLine();
		String line = br.readLine();
		while (line != null && !line.isEmpty()) {
			if((line.replaceAll(",","")).replaceAll(" ","").isEmpty())
				break;
			String[] row = line.split(",");
			if (row[0].equals("P"))
			{
				csv_game.getPackman_list().add(new Packman(Integer.parseInt(row[1]) , new Point3D(Double.parseDouble(row[2]) , Double.parseDouble(row[3]) , Double.parseDouble(row[4])) , Double.parseDouble(row[5]) , Double.parseDouble(row[6])));
			}
			else if (row[0].equals("F"))
			{
				csv_game.getFruit_list().add(new Fruit(Integer.parseInt(row[1]) , new Point3D(Double.parseDouble(row[2]) , Double.parseDouble(row[3]) , Double.parseDouble(row[4])) , Double.parseDouble(row[5])));
			}	
			line = br.readLine();
		}
		return csv_game;
	}
	/**
	 * This function creates a csv file from a game 
	 * @param game the fully loaded game to export
	 * @param path_file_name where to save the file
	 * @throws IOException exception
	 */
	public void create_csv_from_game(Game game , String path_file_name) throws IOException
	{
		FileWriter fileWriter = new FileWriter(path_file_name);
		fileWriter.append("Type,id,Lat,Lon,Alt,Speed/Weight,Radius\n");
		for (Packman packman : game.getPackman_list())
		{
			fileWriter.append("P," + packman.getPackman_id() +"," +packman.getGps().x()  + "," + packman.getGps().y() + "," + packman.getGps().z() + "," + packman.getSpeed() +","  +packman.getRange() +"\n");
		}
		for (Fruit fruit : game.getFruit_list())
		{
			fileWriter.append("F," + fruit.getFruit_id() +"," +fruit.getGps().x()  + "," + fruit.getGps().y() + "," + fruit.getGps().z() + "," + fruit.getWeight() +"\n");
		}
		fileWriter.flush();
		fileWriter.close();
	}
	/**
	 * This function gets a list of paths and returns the value of the max time path
	 * @param paths list of paths
	 * @return the time of max path
	 */
	public double get_max_path_time(Path [] paths)
	{
		double max =0;
		for(Path path : paths)
			max = (max<path.get_total_time()) ? path.get_total_time() : max;
			return max;
	}

	/**
	 * This function gets a list of paths and returns the n,ber of path that has the longest time
	 * @param paths list of paths
	 * @return the path that takes the longest to finish his path
	 */
	public int get_max_path(Path [] paths)
	{
		int max_path =0;
		double max_value=paths[0].get_total_time();
		for (int k=1;k<paths.length;k++)
		{
			if(paths[k].get_total_time()>max_value)
			{
				max_path = k;
				max_value = paths[k].get_total_time();
			}
		}
		return max_path;
	}
	/**
	 * This function gets a game and returns a list of paths after sorting by which packman should take which fruits
	 * @param game the fully loaded game 
	 * @param accuracy is the accuracy of which we calcuate shortest time
	 * @return the list of paths for the packmans to go
	 */
	public Path[] TSP(Game game , double  accuracy)
	{
		this.accuracy = accuracy;
		Path[] paths_greedy_free = new Path [game.getPackman_list().size()];
		int counter =0;
		for (Packman this_packman : game.getPackman_list())
		{
			paths_greedy_free[counter] = new Path(this_packman);
			counter++;
		}
		Game temp_game = game.copy();
		double[] time_gone = new double [game.getPackman_list().size()];
		for(int i=0;i<game.getPackman_list().size(); i++)
			time_gone[i]=0;
		for (int i =0;i<game.getFruit_list().size();i++)
		{
			int[] array_min = get_matrix_min(temp_game).array_min; // get a value of packman (array location) and pair it with the min fruit distance (array value)
			double[][] matrix = get_matrix_min(temp_game).matrix;
			double time_min = time_gone[0] +matrix[0][array_min[0]];
			int packman_to_put = 0;
			for(int j=1;j<game.getPackman_list().size();j++) // find the closest packman to fruit distance by time
			{
				if (time_gone[j] + matrix[j][array_min[j]]<time_min)
				{
					packman_to_put = j;
					time_min = time_gone[j] + matrix[j][array_min[j]];
				}
			}			
			time_gone[packman_to_put] += matrix[packman_to_put][array_min[packman_to_put]];
			Point3D fruit_edge = edge_until_eat(paths_greedy_free[packman_to_put].getLocations().get(paths_greedy_free[packman_to_put].getLocations().size()-1) , temp_game.getFruit_list().get(array_min[packman_to_put]).getGps() ,game.getPackman_list().get(packman_to_put).getRange() );			
			paths_greedy_free[packman_to_put].getLocations().add(fruit_edge);
			temp_game.getPackman_list().get(packman_to_put).setGps(fruit_edge);
			temp_game.getFruit_list().remove(array_min[packman_to_put]).getGps();
		}
		for (int i=0;i<game.getFruit_list().size();i++)
			paths_greedy_free = adjustments(paths_greedy_free); //make small random adjustments

		double max_greedy_free=0;
		for(Path path : paths_greedy_free)
		{
			System.out.println(path.get_total_time());
			if(max_greedy_free<path.get_total_time())
			{
				max_greedy_free = path.get_total_time();
			}
		}
		return paths_greedy_free;
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
	 * This function adjusts paths for a faster path
	 * @param paths list of paths
	 * @return the same list of paths after the adjustments
	 */
	public Path[] adjustments(Path[] paths)
	{
		if (paths.length>1)
			paths = adjusments_move_fruits(paths);
		else
			paths[0] = adjustments_swap(paths[0]);
		return paths;
	}

	/**
	 * This function swaps 2 locations in a path
	 * @param path given path
	 * @param first_location first location to swap
	 * @param second_location second location to swap
	 * @return the path after the swap
	 */
	public Path swap(Path path ,int first_location , int second_location)
	{
		Collections.swap(path.getLocations(), first_location, second_location);
		return path;
	}
	/**
	 * This function adjusts the path by swapping between paths it is possible to swap with its own path
	 * @param path path to adjust
	 * @return path after adjusted
	 */
	public Path adjustments_swap(Path path)
	{
		Path path1;
		for (int i =0 ; i<(path.getLocations().size()-2)*(path.getLocations().size()-1)*accuracy/10 ; i++)
		{
			path1 = path.copy();
			for (int j =0 ; j<(path.getLocations().size()-2) ; j++)
			{
				swap(path1 , 1 + randomNum.nextInt(path1.getLocations().size()-1) , 1 + randomNum.nextInt(path1.getLocations().size()-1));
				if (path1.get_total_time()<path.get_total_time())
				{
					path = path1.copy();
					break;
				}
			}
		}
		return path;
	}
	/**
	 * 
	 * @param paths list of paths
	 * @return the list of paths after adjusted
	 */
	public Path[] adjusments_move_fruits(Path [] paths)
	{
		Path path1 , path2;
		int max_path;
		for (int i =0 ; i<paths.length*accuracy ; i++)
		{
			max_path = get_max_path(paths);
			int secondpackman = randomNum.nextInt(paths.length-1);
			path1 = paths[max_path].copy();
			path2 = paths[secondpackman].copy();
			path2.getLocations().add(1, path1.getLocations().remove(1 + randomNum.nextInt(path1.getLocations().size()-1)));
			path2 = adjustments_swap(path2);
			if (path2.get_total_time()<path1.get_total_time() && paths[max_path].get_total_time()>path1.get_total_time())
			{
				paths[max_path] = path1.copy();
				paths[secondpackman] = path2.copy();
			}
		}
		return paths;
	}

	/**
	 * This function gets a path and a time and return the location of where the packman is at that time
	 * @param path the path
	 * @param time how long till given point
	 * @return the location of where the packman is after certain time
	 */
	public Point3D get_location_by_time(Path path ,Double time)
	{
		double time_left = time;
		double temp_time;
		if (time_left == 0)
			return path.getLocations().get(0);
		for (int i =0 ; i<path.getLocations().size()-1;i++)
		{
			temp_time = cord.distance3d(path.getLocations().get(i) ,path.getLocations().get(i+1))/path.getMy_packman().getSpeed();
			if (temp_time<time_left)
			{
				time_left = time_left - temp_time;
			}
			else
			{
				Point3D meters_start = convert_gps_to_meters(path.getLocations().get(i));
				Point3D meters_end = convert_gps_to_meters(path.getLocations().get(i+1));
				Point3D vect = new Point3D(meters_end.x() - meters_start.x() , meters_end.y() - meters_start.y() , meters_end.z() - meters_start.z());
				double t = time_left/temp_time;
				Point3D newvec = new Point3D(vect.x()*t , vect.y()*t ,vect.z()*t);
				return convert_meters_to_gps(new Point3D(meters_start.x()+newvec.x() ,meters_start.y()+newvec.y() , meters_start.z()+newvec.z()));	
			}
		}
		return path.getLocations().get(path.getLocations().size()-1);
	}
	/**
	 * This function exports a kml file of a games paths
	 * @param paths list of paths
	 * @param out_location dir of output file
	 * @param game is the game to export in kml
	 */
	public void export_kml(Path[] paths ,Game game , String out_location)
	{
		LocalDateTime now_start = LocalDateTime.now();
		LocalDateTime temp_time;
		now_start.minusSeconds(10);
		int global_time;
		Point3D temp ;
		String start = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
				"<kml xmlns=\"http://www.opengis.net/kml/2.2\" xmlns:gx=\"http://www.google.com/kml/ext/2.2\">\n" + 
				"<Document>\n" + 
				"<Style id=\"red\"><IconStyle><Icon>\n" + 
				"<href>http://maps.google.com/mapfiles/ms/icons/red-dot.png</href>\n" + 
				"</Icon></IconStyle></Style>\n" + 
				"<Style id=\"yellow\"><IconStyle><Icon>\n" + 
				"<href>http://maps.google.com/mapfiles/ms/icons/yellow-dot.png</href>\n" + 
				"</Icon></IconStyle></Style>\n" + 
				"<Style id=\"green\"><IconStyle><Icon>\n" + 
				"<href>http://maps.google.com/mapfiles/ms/icons/green-dot.png</href>\n" + 
				"</Icon></IconStyle>\n" + 
				"</Style><Style id=\"track_n\">\n" + 
				"      <IconStyle>\n" + 
				"        <scale>.5</scale>\n" + 
				"        <Icon>\n" + 
				"          <href>http://earth.google.com/images/kml-icons/track-directional/track-none.png</href>\n" + 
				"       </Icon>\n" + 
				"      </IconStyle>\n" + 
				"      <LabelStyle>\n" + 
				"        <scale>0</scale>\n" + 
				"      </LabelStyle>\n" + 
				"\n" + 
				"    </Style>\n" + 
				"    <!-- Highlighted track style -->\n" + 
				"    <Style id=\"track_h\">\n" + 
				"      <IconStyle>\n" + 
				"        <scale>1.2</scale>\n" + 
				"        <Icon>\n" + 
				"          <href>http://earth.google.com/images/kml-icons/track-directional/track-none.png</href>\n" + 
				"        </Icon>\n" + 
				"      </IconStyle>\n" + 
				"    </Style>\n" + 
				"    <StyleMap id=\"track\">\n" + 
				"      <Pair>\n" + 
				"        <key>normal</key>\n" + 
				"        <styleUrl>#track_n</styleUrl>\n" + 
				"      </Pair>\n" + 
				"      <Pair>\n" + 
				"        <key>highlight</key>\n" + 
				"        <styleUrl>#track_h</styleUrl>\n" + 
				"      </Pair>\n" + 
				"    </StyleMap>\n" + 
				"    <!-- Normal multiTrack style -->\n" + 
				"    <Style id=\"multiTrack_n\">\n" + 
				"      <IconStyle>\n" + 
				"        <Icon>\n" + 
				"          <href>http://earth.google.com/images/kml-icons/track-directional/track-0.png</href>\n" + 
				"        </Icon>\n" + 
				"      </IconStyle>\n" + 
				"      <LineStyle>\n" + 
				"        <color>99ffac59</color>\n" + 
				"        <width>6</width>\n" + 
				"      </LineStyle>\n" + 
				"\n" + 
				"    </Style>\n" + 
				"    <!-- Highlighted multiTrack style -->\n" + 
				"    <Style id=\"multiTrack_h\">\n" + 
				"      <IconStyle>\n" + 
				"        <scale>1.2</scale>\n" + 
				"        <Icon>\n" + 
				"        </Icon>\n" + 
				"      </IconStyle>\n" + 
				"      <LineStyle>\n" + 
				"        <color>99ffac59</color>\n" + 
				"        <width>8</width>\n" + 
				"      </LineStyle>\n" + 
				"    </Style>\n" + 
				"    <StyleMap id=\"multiTrack\">\n" + 
				"      <Pair>\n" + 
				"        <key>normal</key>\n" + 
				"        <styleUrl>#multiTrack_n</styleUrl>\n" + 
				"      </Pair>\n" + 
				"      <Pair>\n" + 
				"        <key>highlight</key>\n" + 
				"        <styleUrl>#multiTrack_h</styleUrl>\n" + 
				"      </Pair>\n" + 
				"    </StyleMap>\n" + 
				"    <!-- Normal waypoint style -->\n" + 
				"    <Style id=\"waypoint_n\">\n" + 
				"      <IconStyle>\n" + 
				"        <Icon>\n" + 
				"          <href>http://maps.google.com/mapfiles/kml/pal4/icon61.png</href>\n" + 
				"        </Icon>\n" + 
				"      </IconStyle>\n" + 
				"    </Style>\n" + 
				"    <!-- Highlighted waypoint style -->\n" + 
				"    <Style id=\"waypoint_h\">\n" + 
				"      <IconStyle>\n" + 
				"        <scale>1.2</scale>\n" + 
				"        <Icon>\n" + 
				"          <href>http://maps.google.com/mapfiles/kml/pal4/icon61.png</href>\n" + 
				"        </Icon>\n" + 
				"      </IconStyle>\n" + 
				"    </Style>\n" + 
				"    <StyleMap id=\"waypoint\">\n" + 
				"      <Pair>\n" + 
				"        <key>normal</key>\n" + 
				"        <styleUrl>#waypoint_n</styleUrl>\n" + 
				"      </Pair>\n" + 
				"      <Pair>\n" + 
				"        <key>highlight</key>\n" + 
				"        <styleUrl>#waypoint_h</styleUrl>\n" + 
				"      </Pair>\n" + 
				"    </StyleMap>\n" + 
				"    <Style id=\"lineStyle\">\n" + 
				"      <LineStyle>\n" + 
				"        <color>99ffac59</color>\n" + 
				"        <width>6</width>\n" + 
				"      </LineStyle>\n" + 
				"    </Style>null";
		String packmans_kml = "<Folder> <name>packmans</name>";
		for (Packman packman :game.getPackman_list())
		{
			packmans_kml += "<Placemark>\n" + 
					"<styleUrl>#red</styleUrl>\n"
					+ "<ExtendedData>\n" + 
					"      <Data name=\"packman id\">\n" + 
					"        <value>" +packman.getPackman_id() +"</value>\n" + 
					"      </Data>\n" + 
					"      <Data name=\"packman radius\">\n" + 
					"        <value>" + packman.getRange()+"</value>\n" + 
					"      </Data>\n" + 
					"      <Data name=\"packman speed\">\n" + 
					"        <value>" + packman.getSpeed() +"</value>\n" + 
					"      </Data>\n" + 
					"    </ExtendedData>" +

					"<Point>\n" + 
					"<coordinates>" +packman.getGps().y() + " " + packman.getGps().x() + " " +packman.getGps().z() + " " +"</coordinates>\n" + 
					"</Point>\n" + 
					"</Placemark>";
		}
		packmans_kml += "</Folder>";
		String fruits_kml = "<Folder> <name>fruits</name>";
		for (Fruit fruit :game.getFruit_list())
		{
			fruits_kml += "<Placemark>\n" + 
					"<styleUrl>#yellow</styleUrl>\n" + 
					"<ExtendedData>\n" + 
					"      <Data name=\"fruit id\">\n" + 
					"        <value>" +fruit.getFruit_id() +"</value>\n" + 
					"      </Data>\n" + 
					"      <Data name=\"fruit weight\">\n" + 
					"        <value>" + fruit.getWeight() + "</value>\n" + 
					"      </Data>\n" + 

						"    </ExtendedData>"+
						"<Point>\n" + 
						"<coordinates>" +fruit.getGps().y() + " " + fruit.getGps().x() + " " +fruit.getGps().z() + " " +"</coordinates>\n" + 
						"</Point>\n" + 
						"</Placemark>";
		}
		fruits_kml += "</Folder> ";
		String track =" <Folder>\n";
		int counter =0;
		for (Path path : paths)
		{
			temp_time = now_start;
			track += "<Placemark>"+
					"  <name>packman id " + game.getPackman_list().get(counter).getPackman_id()+"</name>\n" + 
					"<styleUrl>#multiTrack</styleUrl><gx:Track>";				
			for (global_time=0;global_time*10<get_max_path_time(paths)+10;global_time++)
			{
				temp_time = temp_time.plusSeconds(10);
				temp =get_location_by_time(path ,global_time*10.0);
				track +="<when>" + temp_time + "</when>\n" + 
						"<gx:coord>" + temp.y() +"  " + temp.x() + "  " + temp.z() +"</gx:coord>\n";
			}
			track+= "</gx:Track>\n" + 
					"</Placemark>";
			counter ++;
		}		
		track +=" </Folder>\n";
		String end = "</Document>\n" + 
				"</kml>";
		String final_string = start + track + fruits_kml + packmans_kml + end;
		Writer fwriter;
		try {
			fwriter = new FileWriter(out_location);
			fwriter.write(final_string);
			fwriter.flush();
			fwriter.close();
		}catch (IOException e1) {
			e1.printStackTrace();
		} 
	}
}




