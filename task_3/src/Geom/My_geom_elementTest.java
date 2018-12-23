package Geom;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class My_geom_elementTest {

	@Test
	void testMy_geom_elementPoint3D() {
		try
		{
			Point3D point = new Point3D(1,2,3);
			My_geom_element geom_test_one=new My_geom_element(point);
		}catch(IllegalArgumentException e)
		{
			fail("geom didnt work");
		}	}

	@Test
	void testMy_geom_elementDoubleDoubleDouble() {
		try
		{
			My_geom_element geom_test_one=new My_geom_element(3,3,3);
		}catch(IllegalArgumentException e)
		{
			fail("geom didnt work");
		}
	}

	@Test
	void testDistance3D() {
		My_geom_element geom_test_one=new My_geom_element(3,3,3);
		Point3D gps_test_two= new Point3D(2.6,2.2,1.9);
		double distance=geom_test_one.distance3D(gps_test_two);	
		assertTrue(distance>=99359.6&&distance<=99359.7);
		}

	@Test
	void testDistance2D() {
		My_geom_element geom_test_one=new My_geom_element(2,2,2);
		Point3D gps_test_two= new Point3D(2.6,2.2,1.9);
		double distance=geom_test_one.distance2D(gps_test_two);
		assertTrue(distance>=70319.8&&distance<=70319.9);
		}

}
