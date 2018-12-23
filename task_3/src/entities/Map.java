package entities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Geom.Point3D;

/**
 * 
 * @author Shilo Gilor and Amiel Liberman
 *
 */
public class Map {
	private Point3D left_bottom_corner ; 
	private Point3D right_top_corner ; 
	private BufferedImage backgroundImage;
	/**
	 * 
	 * @param left_bottom_corner is the left bottom corner gps point
	 * @param right_top_corner is the right top corner corner gps point
	 * @param fileName is the file name and directory to the file image
	 */
	public Map(Point3D left_bottom_corner, Point3D right_top_corner, String fileName) {
		super();
		this.left_bottom_corner = left_bottom_corner;
		this.right_top_corner = right_top_corner;
		try {
			this.backgroundImage = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		};
	}
	public Point3D getLeft_bottom_corner() {
		return left_bottom_corner;
	}
	public void setLeft_bottom_corner(Point3D left_bottom_corner) {
		this.left_bottom_corner = left_bottom_corner;
	}
	public Point3D getRight_top_corner() {
		return right_top_corner;
	}
	public void setRight_top_corner(Point3D right_top_corner) {
		this.right_top_corner = right_top_corner;
	}
	public BufferedImage getBackgroundImage() {
		return backgroundImage;
	}
	public void setBackgroundImage(BufferedImage backgroundImage) {
		this.backgroundImage = backgroundImage;
	}
	
	


}
