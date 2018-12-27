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
public class Ghost {

	private int ghost_id;
	private Point3D gps;
	private double range;
	private double speed;
	private Image ghost_image;

	/**
	 * 
	 * @param packman_id packmans id
	 * @param gps packmans gps location
	 * @param speed packmans speed
	 * @param range packmans range
	 */
	public Ghost(int ghost_id ,Point3D gps, double speed , double range) {
		super();
		this.ghost_id = ghost_id;
		this.gps = gps;
		this.range = range;
		this.speed = speed;
		try {
			ghost_image = ImageIO.read(new File("src/resources/packman.png"));
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
	public Ghost(int ghost_id , Point3D gps, double speed) {
		this(ghost_id , gps,speed,1);
	}
	/**
	 * packman range and speed will be 1
	 * @param packman_id packmans id
	 * @param gps gps location
	 */
	public Ghost(int ghost_id  , Point3D gps) {
		this(ghost_id , gps ,1 ,1);
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
		return ghost_image;
	}
	/**
	 * sets packman_image
	 * @param packman_image packman image
	 */
	public void setPackman_image(Image ghost_image) {
		this.ghost_image = ghost_image;
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
		return ghost_id;
	}
	/**
	 * set packman_id
	 * @param packman_id packman id
	 */
	public void setGhost_id(int ghost_id) {
		this.ghost_id = ghost_id;
	}
	/**
	 * @return string of Game info
	 */
	@Override
	public String toString() {
		return "Packman [packman_id=" + ghost_id + ", gps=" + gps + ", range=" + range + ", speed=" + speed
				+ ", packman_image=" + ghost_image + "]";
	}
	
	

}
