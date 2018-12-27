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
	private Image fruit_image;
	private double range;
//	private String[] fruits = {"src/resources/fruit.png" , "src/resources/fruit2.png" ,"src/resources/fruit3.png" ,"src/resources/fruit4.png" ,"src/resources/fruit5.png"};
	Random randomNum = new Random();
	/**
	 * 
	 * @param fruit_id is the fruit id
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
 *  get fruit Gps
 * @return Gps
 */
	public Point3D getGps1() {
		return Gps1;
	}
	public Point3D getGps2() {
		return Gps2;
	}
	/**
	 *  get fruit image
	 * @return fruit image
	 */
	public Image getFruit_image() {
		return fruit_image;
	}
	/**
	 *  get fruit weight
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
	 * get friut id
	 * @return fruit id
	 */
	public int getFruit_id() {
		return box_id;
	}

	/**
	 * 
	 * @param fruit_id fruit id
	 */
	public void setFruit_id(int box_id) {
		this.box_id = box_id;
	}

	/**
	 * 
	 * @param gps fruit gps
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
	 * @return string of fruit
	 */
	public String toString() {
		return "Fruit [fruit_id=" + box_id + ", Gps1=" + Gps1 +  ", Gps2=" + Gps2 + ", weight=" + weight + ", fruit_image=" + fruit_image
				+ "]";
	}
	
	
}


