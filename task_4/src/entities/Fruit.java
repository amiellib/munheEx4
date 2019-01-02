package entities;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import Geom.Point3D;

/**
 * 
 * @author Shilo Gilor and Amiel Liberman
 *
 */
public class Fruit 
{
	private int fruit_id;
	private Point3D Gps;
	private double weight;
	private Image fruit_image;
	private String fruits = "src/resources/fruit.png";
	Random randomNum = new Random();
	/**
	 * 
	 * @param fruit_id is the fruit id
	 * @param gps is the gps location
	 * @param weight is the fruit value
	 */
	public Fruit(int fruit_id ,Point3D gps , double weight)
	{
		this.fruit_id =fruit_id;
		this.Gps=new Point3D(gps);
		this.weight = weight;
		try {
			fruit_image = ImageIO.read(new File(fruits));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/**
 *  get fruit Gps
 * @return Gps
 */
	public Point3D getGps() {
		return Gps;
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
		return fruit_id;
	}

	/**
	 * 
	 * @param fruit_id fruit id
	 */
	public void setFruit_id(int fruit_id) {
		this.fruit_id = fruit_id;
	}

	/**
	 * 
	 * @param gps fruit gps
	 */
	public void setGps(Point3D gps) {
		Gps = gps;
	}

	@Override
	/**
	 * @return string of fruit
	 */
	public String toString() {
		return "Fruit [fruit_id=" + fruit_id + ", Gps=" + Gps + ", weight=" + weight + ", fruit_image=" + fruit_image
				+ "]";
	}
	
	
}
