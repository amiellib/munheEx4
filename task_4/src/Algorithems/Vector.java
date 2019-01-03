package Algorithems;

import Geom.Point3D;

public class Vector {
	double m,b;

	public Vector(double m, double b) {
		super();
		this.m = m;
		this.b = b;
	}
	public Vector(Point3D start_meters, Point3D end_meters)
	{
		this( (end_meters.y() - start_meters.y())/(end_meters.x() - start_meters.x()), start_meters.y() - (end_meters.y() - start_meters.y())/(end_meters.x() - start_meters.x())*start_meters.x());
	}
	public double give_x_get_y(double x)
	{
		return m*x+b;
	}
	public double give_y_get_x(double y)
	{
		return (m!=0) ? (y-b)/m : 99999;
	}

	public double getM() {
		return m;
	}

	public void setM(double m) {
		this.m = m;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}
	@Override
	public String toString() {
		return "Vector [m=" + m + ", b=" + b + "]";
	}
	

}
