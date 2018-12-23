package GIS;

import Geom.Point3D;

public class My_meta_data implements Meta_data{
/**
 * @author Shilo Gilor and Amiel Liberman
 */
	private long UTC;
	private Point3D orientation;
	private String colour;
	/**
	 * 
	 * @param UTC the time of the meta_data
	 * @param orientation is null for now
	 * @param colour color of meta_data
	 */
	public My_meta_data(long UTC, Point3D orientation , String colour) {
		super();
		this.UTC = UTC;
		this.orientation = null;
		this.colour = colour;
	}
	/**this function return the UTC of the meta_data
	 * @return UTC
	 */
	@Override
	public long getUTC() {
		return UTC;
	}
	/**this function return the orientation of the meta_data(now its null)
	 * @return orientation
	 */
	@Override
	public Point3D get_Orientation() {
		return orientation;
	}
	/**this function return the String of the meta_data
	 * @return orientation
	 */
	@Override
	public String toString() {
		return "My_meta_data [UTC=" + UTC + ", orientation=" + orientation + "]";
	}
	/**this function return the colour of the meta_data
	 * @return orientation
	 */
	public String getColour() {
		return colour;
	}

}
