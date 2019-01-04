package Algorithems;

import Geom.Point3D;
/**
 * 
 * @author Shilo Gilor and Amiel Liberman
 *
 */
public class Vector {
	double m,b;

	/**
	 * 
	 * @param m is the angle the vector goes
	 * @param b is the bias it starts from
	 */
	public Vector(double m, double b) {
		super();
		this.m = m;
		this.b = b;
	}
	/**
	 * 
	 * @param start_meters getting 2 meters points
	 * @param end_meters getting 2 meters points
	 */
	public Vector(Point3D start_meters, Point3D end_meters)
	{
		this( (end_meters.y() - start_meters.y())/(end_meters.x() - start_meters.x()), start_meters.y() - (end_meters.y() - start_meters.y())/(end_meters.x() - start_meters.x())*start_meters.x());
	}
	/**
	 * 
	 * @param x get x to calculate the y
	 * @return the y value
	 */
	public double give_x_get_y(double x)
	{
		return m*x+b;
	}
	/**
	 * 
	 * @param y get y to calculate x
	 * @return the x value
	 */
	public double give_y_get_x(double y)
	{
		return (m!=0) ? (y-b)/m : 99999;
	}

	/**
	 * getter
	 * @return m
	 */
	public double getM() {
		return m;
	}
	/**
	 * 
	 * @param m m 
	 */
	public void setM(double m) {
		this.m = m;
	}
	/**
	 * 
	 * @return b
	 */
	public double getB() {
		return b;
	}
	/**
	 * 
	 * @param b b
	 */
	public void setB(double b) {
		this.b = b;
	}
	/**
	 * to string
	 */
	@Override
	public String toString() {
		return "Vector [m=" + m + ", b=" + b + "]";
	}


}
