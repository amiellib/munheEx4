package Algorithems;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import Geom.Point3D;
import entities.*;

class AlgorithemsTest {

	Algorithms algo = new Algorithms(new Map(new Point3D (32.102010 , 35.202155 , 0) , new Point3D (32.106162 , 35.212514 , 0), "src/resources/Ariel1.png"));
	
	@Test
	void testAlgorithems() {
		try
		{
			algo = new Algorithms(new Map(new Point3D (32.102010 , 35.202155 , 0) , new Point3D (32.106162 , 35.212514 , 0), "src/resources/Ariel1.png")); 
		}catch(IllegalArgumentException e)
		{
			fail("map didnt load");
		}	
	}

	@Test
	void testConvert_pixel_to_gps() {
		
		Point3D pixel_to_gps_point = algo.convert_pixel_to_gps(new Point3D(50,50,0), 1433, 642);
		assertTrue(32.106 <=pixel_to_gps_point.x()&&32.107 >=pixel_to_gps_point.x()&&35.2029<=pixel_to_gps_point.y()&&35.203>=pixel_to_gps_point.y()&&0==pixel_to_gps_point.z());
	}

	@Test
	void testConvert_gps_to_pixel() {
		Point3D gps_to_pixel_point = algo.convert_gps_to_pixel(new Point3D(32.10601712909979,35.202961775700935,0), 1433, 642);
		assertTrue(49 <=gps_to_pixel_point.x()&&51 >=gps_to_pixel_point.x()&&49<=gps_to_pixel_point.y()&&51>=gps_to_pixel_point.y()&&0==gps_to_pixel_point.z());
	}

	@Test
	void testConvert_meters_to_gps() {
		Point3D meters_to_gps_point = algo.convert_meters_to_gps(new Point3D(75.98964207552375,16.108909118027164,0));
		assertTrue(32.106 <=meters_to_gps_point.x()&&32.107 >=meters_to_gps_point.x()&&35.202<=meters_to_gps_point.y()&&35.204>=meters_to_gps_point.y()&&0==meters_to_gps_point.z());

	}

	@Test
	void testConvert_gps_to_meters() {
		Point3D gps_to_meters_point = algo.convert_gps_to_meters(new Point3D(32.10601712909979,35.202961775700935,0));
		assertTrue(75 <=gps_to_meters_point.x()&&77 >=gps_to_meters_point.x()&&16<=gps_to_meters_point.y()&&17>=gps_to_meters_point.y()&&0==gps_to_meters_point.z());
	}


}
