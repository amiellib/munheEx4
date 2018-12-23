package Coords;

import Geom.Point3D;
/**
 * This class can do functions on our coordinates 
 * function like adding a vector, or finding the distance between 2 points
 * @author Shilo Gilor and Amiel Liberman
 *
 */
public class MyCoords implements coords_converter
{
	static public int  EARTH_RADIUS = 6371000;

	/**
	 * This function converts from radians to cartesian
	 * @param p is the point to convert
	 * @return the new point after conversion
	 */
	public Point3D convert_radians_to_cartesian(Point3D p)
	{	
		double x = (EARTH_RADIUS+p.z()) * Math.cos(p.x()/180*Math.PI) * Math.cos(p.y()/180*Math.PI);
		double y = (EARTH_RADIUS+p.z()) * Math.cos(p.x()/180*Math.PI) * Math.sin(p.y()/180*Math.PI);
		double z = (EARTH_RADIUS+p.z()) * Math.sin(p.x()/180*Math.PI);
		return new Point3D(x,y,z);
	}
	/**
	 * This function converts from cartesian to radians 
	 * @param p is the point to convert
	 * @return the new point after conversion
	 */
	public Point3D convert_cartesian_to_radians(Point3D p)
	{
		double x = Math.asin(p.z()/EARTH_RADIUS)*180/Math.PI;
		x = (x>180) ? x-360 : x; 
		double y = Math.atan2(p.y(), p.x())*180/Math.PI;
		y = (y>90) ? y-180 : y;
		double z = Math.sqrt(Math.pow(p.x(), 2) + Math.pow(p.y(), 2) + Math.pow(p.z(), 2))-EARTH_RADIUS  ;
		return new Point3D(x,y,z);
	}
	
	/**
	 * This function takes our point and add to it the vector given and readjusts it to the new point
	 * @param gps is my given location
	 * @param local_vector_in_meter is the vector moved by
	 */
	@Override
	public Point3D add(Point3D gps, Point3D local_vector_in_meter) {
		Point3D cartesian= convert_radians_to_cartesian(gps); // convert the point to cartesian for easier calculatins
		cartesian.add(local_vector_in_meter); 
		return convert_cartesian_to_radians(cartesian);
	}

	/**
	 * This function calculates the distance between 2 given points
	 * @param gps0 is my given location
	 * @param gps1 is the location i am calculating the distance to.
	 */
	@Override
	public double distance3d(Point3D gps0, Point3D gps1) 
	{		
		Point3D cartesian0= convert_radians_to_cartesian(gps0); // convert the point to cartesian for easier calculatins
		Point3D cartesian1= convert_radians_to_cartesian(gps1); // convert the point to cartesian for easier calculatins
		double result = cartesian1.distance3D(cartesian0);
		if (result>100000)
		{
			throw new RuntimeException("distance to large, the limit is 100KM, and the distance for these 2 points is " + result); 
		}
		return result ;
	}
	/**
	 * This function calculates the vector between 2 given points
	 * @param gps0 is the given point i am starting from
	 * @param gps1 is the location to where i am calculating the vector to
	 */
	@Override
	public Point3D vector3D(Point3D gps0, Point3D gps1) 
	{
		Point3D cartesian0= convert_radians_to_cartesian(gps0); // convert the point to cartesian for easier calculatins
		Point3D cartesian1= convert_radians_to_cartesian(gps1); // convert the point to cartesian for easier calculatins
		cartesian1.add(-cartesian0.x(), -cartesian0.y(), -cartesian0.z());
		return cartesian1;
	}
	/**
	 * This function calculates 3 variables between 2 points, the azimuth , the elevation , and the distance
	 * @param gps0 is my given location
	 * @param gps1 is the point to which i am doing the calculations to
	 * 	
	 */
	@Override
	public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {
		double[] my_azimuth_elevation_dist= new  double[3];
		my_azimuth_elevation_dist[0] = gps1.north_angle(gps0); // its a given function
		my_azimuth_elevation_dist[2] = distance3d(gps1,gps0); // we just built this function
		my_azimuth_elevation_dist[1] = Math.toDegrees(Math.asin((gps1.z()-gps0.z())/my_azimuth_elevation_dist[2])); // a simple calculation between the height and the hypotenuse 
		return my_azimuth_elevation_dist;
	}

	/**
	 * This function checks if the given point is a valid gps point
	 * @param p is the point to be checked
	 */
	@Override
	public boolean isValid_GPS_Point(Point3D p) 
	{
		return p.x()<-180 && p.x()>180 && p.y()<-90 && p.y()>90 && p.z()<-450;
	}
}
