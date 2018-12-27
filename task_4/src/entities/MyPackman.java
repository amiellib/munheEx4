package entities;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Geom.Point3D;

/**
 * 
 * @author Shilo Gilor and Amiel Liberman
 *
 */
public class MyPackman {


	private int packman_id;
	private Point3D gps;
	private double range;
	private double speed;
	private Image packman_image;

	/**
	 * 
	 * @param packman_id packmans id
	 * @param gps packmans gps location
	 * @param speed packmans speed
	 * @param range packmans range
	 */
	public MyPackman(int packman_id ,Point3D gps, double speed , double range) {
		super();
		this.packman_id = packman_id;
		this.gps = gps;
		this.range = range;
		this.speed = speed;
		try {
			packman_image = ImageIO.read(new File("src/resources/packman.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * packman range will be 1
	 * @param packman_id packmans id
	 * @param gps packmans gps location
	 * @param speed packmans speed
	 */
	public MyPackman(int packman_id , Point3D gps, double speed) {
		this(packman_id , gps,speed,1);
	}
	/**
	 * packman range and speed will be 1
	 * @param packman_id packmans id
	 * @param gps gps location
	 */
	public MyPackman(int packman_id  , Point3D gps) {
		this(packman_id , gps ,1 ,1);
	}
	public MyPackman(MyPackman mypackman) {
		this(mypackman.packman_id , mypackman.gps , mypackman.speed , mypackman.range);
	}
	/**
	 * get packman GPs
	 * @return  Gps
	 */
	public Point3D getGps() {
		return gps;
	}
	/**
	 * get packman speed
	 * @return speed
	 */
	public double getSpeed() {
		return speed;
	}
	/**
	 * get packman range
	 * @return range
	 */
	public double getRange() {
		return range;
	}
	/**
	 *sets packman range
	 * @param range packman range
	 */
	public void setRange(double range) {
		this.range = range;
	}
	/**
	 * get packman_image
	 * @return packman_image
	 */
	public Image getPackman_image() {
		return packman_image;
	}
	/**
	 * sets packman_image
	 * @param packman_image packman image
	 */
	public void setPackman_image(Image packman_image) {
		this.packman_image = packman_image;
	}
	/**
	 * sets packman Gps
	 * @param gps packman Gps
	 */
	public void setGps(Point3D gps) {
		this.gps = gps;
	}
	/**
	 * sets packman speed
	 * @param speed packman speed	
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	/**
	 * get packman_id
	 * @return packman_id
	 */
	public int getPackman_id() {
		return packman_id;
	}
	/**
	 * set packman_id
	 * @param packman_id packman id
	 */
	public void setPackman_id(int packman_id) {
		this.packman_id = packman_id;
	}
	/**
	 * @return string of Game info
	 */
	@Override
	public String toString() {
		return "Packman [packman_id=" + packman_id + ", gps=" + gps + ", range=" + range + ", speed=" + speed
				+ ", packman_image=" + packman_image + "]";
	}
	
	

}


