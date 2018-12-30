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
	 * @param ghost_id ghosts id
	 * @param gps ghosts gps location
	 * @param speed ghosts speed
	 * @param range ghosts range
	 */
	public Ghost(int ghost_id ,Point3D gps, double speed , double range) {
		super();
		this.ghost_id = ghost_id;
		this.gps = gps;
		this.range = range;
		this.speed = speed;
		try {
			ghost_image = ImageIO.read(new File("src/resources/ghost_1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ghost range will be 1
	 * @param ghost_id ghosts id
	 * @param gps ghosts gps location
	 * @param speed ghosts speed
	 */
	public Ghost(int ghost_id , Point3D gps, double speed) {
		this(ghost_id , gps,speed,1);
	}
	/**
	 * ghost range and speed will be 1
	 * @param ghost_id ghosts id
	 * @param gps gps location
	 */
	public Ghost(int ghost_id  , Point3D gps) {
		this(ghost_id , gps ,1 ,1);
	}
	/**
	 * get ghost GPs
	 * @return  Gps
	 */
	public Point3D getGps() {
		return gps;
	}
	/**
	 * get ghost speed
	 * @return speed
	 */
	public double getSpeed() {
		return speed;
	}
	/**
	 * get ghost range
	 * @return range
	 */
	public double getRange() {
		return range;
	}
	/**
	 *sets ghost range
	 * @param range ghost range
	 */
	public void setRange(double range) {
		this.range = range;
	}
	/**
	 * get ghost_image
	 * @return ghost_image
	 */
	public Image getGhost_image() {
		return ghost_image;
	}
	/**
	 * sets ghost_image
	 * @param ghost_image ghost image
	 */
	public void setGhost_image(Image ghost_image) {
		this.ghost_image = ghost_image;
	}
	/**
	 * sets ghost Gps
	 * @param gps ghost Gps
	 */
	public void setGps(Point3D gps) {
		this.gps = gps;
	}
	/**
	 * sets ghost speed
	 * @param speed ghost speed	
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	/**
	 * get ghost_id
	 * @return ghost_id
	 */
	public int getGhost_id() {
		return ghost_id;
	}
	/**
	 * set ghost_id
	 * @param ghost_id ghost id
	 */
	public void setGhost_id(int ghost_id) {
		this.ghost_id = ghost_id;
	}
	/**
	 * @return string of Game info
	 */
	@Override
	public String toString() {
		return "ghost [ghost_id=" + ghost_id + ", gps=" + gps + ", range=" + range + ", speed=" + speed
				+ ", ghost_image=" + ghost_image + "]";
	}
	
	

}
