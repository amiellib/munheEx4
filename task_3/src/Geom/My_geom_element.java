package Geom;

import Coords.MyCoords;

/**
 * This class represents a geo-location
 * @author Shilo Gilor and Amiel Liberman
 *
 */
public class My_geom_element implements Geom_element{
	
	private Point3D my_geom;
	
	public My_geom_element(Point3D my_geom) {
		super();
		this.my_geom = my_geom;
	}
	public My_geom_element(double x,double y,double z) 
	{
		this(new Point3D (x,y,z));
	}
	/**
	 * This function calculates the distance between my geo-location and another geo-location 
	 * @param p is the point to where we are calculating the distance
	 */
	@Override
	public double distance3D(Point3D p) {
		MyCoords mycoords = new MyCoords();
		Point3D cartesian0= mycoords.convert_radians_to_cartesian(p);
		Point3D cartesian1= mycoords.convert_radians_to_cartesian(my_geom);
		double result = cartesian1.distance3D(cartesian0);
		if (result>100000)
		{
			throw new RuntimeException("distance to large, the limit is 100KM, and the distance for these 2 points is " + result); 
		}
		return result;
	}
	/**
	 * This function calculates the distance between my geo-location and another geo-location in a 2D plane
	 * @param p is the point to where we are calculating the distance
	 */
	@Override
	public double distance2D(Point3D p) {
		return distance3D(new Point3D(p.x(),p.y(),my_geom.z()));
	}
	public Point3D getMy_geom() {
		return my_geom;
	}
	public void setMy_geom(Point3D geom) {
		MyCoords mycoords = new MyCoords();
		if (mycoords.isValid_GPS_Point(geom))
		{
			this.my_geom = geom;
		}
		else
		{
			throw new RuntimeException("new geom location is not valid"); 
		}
	}
}
