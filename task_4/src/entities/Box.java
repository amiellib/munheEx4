package entities;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import Geom.Point3D;

public class Box {
	private int box_id;
	private Point3D Gps1;
	private Point3D Gps2;
	private double weight;
	private Image box_image;
	private double range;
//	private String[] fruits = {"src/resources/fruit.png" , "src/resources/fruit2.png" ,"src/resources/fruit3.png" ,"src/resources/fruit4.png" ,"src/resources/fruit5.png"};
	Random randomNum = new Random();
	/**
	 * 
	 * @param box_id is the box id
	 * @param gps is the gps location
	 * @param weight is the fruit value
	 */
	public Box(int box_id ,Point3D gps1 , Point3D gps2, double weight , double range )
	{
		this.box_id =box_id;
		this.Gps1=new Point3D(gps1);
		this.Gps2=new Point3D(gps2);
		this.range = range;
		this.weight = weight;
/*		try {
			fruit_image = ImageIO.read(new File(fruits[randomNum.nextInt(fruits.length-1)]));
		} catch (IOException e) {
			e.printStackTrace();
		}
*/	}
/**
 *  get box Gps
 * @return Gps
 */
	public Point3D getGps1() {
		return Gps1;
	}
	public Point3D getGps2() {
		return Gps2;
	}
	/**
	 *  get box image
	 * @return box image
	 */
	public Image getBox_image() {
		return box_image;
	}
	/**
	 *  get box weight
	 * @return weight
	 */
	public double getWeight() {
		return weight;
	}
/**
 * 
 * @param weight the weight
 */
	void setWeight(double weight) {
		this.weight = weight;
	}
	/**
	 * get box id
	 * @return box id
	 */
	public int getBox_id() {
		return box_id;
	}

	/**
	 * 
	 * @param box_id box id
	 */
	public void setbox_id(int box_id) {
		this.box_id = box_id;
	}

	/**
	 * 
	 * @param gps box gps
	 */
	public void setGps1(Point3D gps1) {
		this.Gps1 = gps1;
	}
	
	public void setGps2(Point3D gps2) {
		this.Gps2 = gps2;
	}
	

	public double getRange() {
		return range;
	}
	public void setRange(double range) {
		this.range = range;
	}
	
	
	@Override
	/**
	 * @return string of box
	 */
	public String toString() {
		return "box [box_id=" + box_id + ", Gps1=" + Gps1 +  ", Gps2=" + Gps2 + ", weight=" + weight + ", box_image=" + box_image
				+ "]";
	}
	
	
}


