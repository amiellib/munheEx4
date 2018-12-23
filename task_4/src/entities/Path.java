package entities;

import java.awt.Color;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import Algorithems.Algorithms;
import Coords.MyCoords;
import Geom.Point3D;

/**
 * 
 * @author Shilo Gilor and Amiel Liberman
 *
 */
public class Path {

	Random randomNum = new Random();
	private Packman my_packman;
	private double time = 0.0;
	private Color color;
	private ArrayList<Point3D> locations = new ArrayList<Point3D>();
	MyCoords cord = new MyCoords();
/**
 * build a path for a packman
 * @param my_packman is a packman
 */
	public Path(Packman my_packman) {
		super();
		this.my_packman = my_packman;
		color =  new Color(randomNum.nextFloat(), randomNum.nextFloat(), randomNum.nextFloat());
		locations.add(my_packman.getGps());		
	}
	/**
	 * get locations of the path
	 * @return locations
	 */
	public ArrayList<Point3D> getLocations() {
		return locations;
	}
	/**
	 * set locations of the path
	 * @param locations Point3D
	 */
	public void setLocations(ArrayList<Point3D> locations) {
		this.locations = locations;
	}
	
	/**
	 * get color of path
	 * @return color
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * set color of path
	 * @param color a color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	/**
	 * total time of path
	 * @return time of path
	 */
	public double get_total_time()
	{
		return get_total_distance()/my_packman.getSpeed();
	}
	/**
	 * get total distance of path
	 * @return total_distance
	 */
	public double get_total_distance()
	{
		double total_distance = 0.0;
		Iterator<Point3D> iter = locations.iterator();
		Point3D current_location = iter.next();
		while (iter.hasNext())
		{
			Point3D temp_location = iter.next();
			total_distance += cord.distance3d(current_location,temp_location);
			current_location = temp_location;
		}
		return total_distance;
	}
	/**
	 * copy a path
	 * @return path_copy
	 */
	public Path copy()
	{
		Path temp_path = new Path(my_packman);
		int a = 0;
		for (Point3D location : locations)
		{
			if (a!=0)
				temp_path.locations.add(new Point3D(location.x(),location.y(),location.z()));
			a=1;
		}
		return temp_path;
		
	}
	/**
	 * get path packman 
	 * @return my_packman a packman
	 */
	public Packman getMy_packman() {
		return my_packman;
	}
	/**
	 * sets path packman
	 * @param my_packman a packman
	 */
	public void setMy_packman(Packman my_packman) {
		this.my_packman = my_packman;
	}
	@Override
	/**
	 * string of the path
	 * return path in a string
	 */
	public String toString() {
		return "Path [algorithems=" + ", my_packman=" + my_packman + ", time=" + time + ", locations="
				+ locations + "]";
	}
	

	
}
