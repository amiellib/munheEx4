package Coords;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Geom.Point3D;

class MyCoordsTest {

	@Test
	void testAdd() {
		MyCoords cord=new MyCoords();
		Point3D gps_test_one= new Point3D(20,20,2);
		Point3D gps_test_add=cord.add(gps_test_one, new Point3D(10,10,10));
		assertTrue(20.0001 <=gps_test_add.x()&&20.0002 >=gps_test_add.x()&&20.00000<=gps_test_add.y()&&20.0001>=gps_test_add.y()&&17.4643<=gps_test_add.z()&&17.4644>=gps_test_add.z());
	}

	@Test
	void testDistance3d() {
		MyCoords cord=new MyCoords();
		Point3D gps_test_one=new Point3D(50,50,50);
		gps_test_one=cord.add(gps_test_one, new Point3D(10,10,10));
		assertTrue(50.0006 <=gps_test_one.x()&&50.0007 >=gps_test_one.x()&&49.9999<=gps_test_one.y()&&50>=gps_test_one.y()&&66.7162<=gps_test_one.z()&&66.7163>=gps_test_one.z());

	}

	@Test
	void testVector3D() {
		MyCoords cord=new MyCoords();
		Point3D gps_test_cartesian=cord.vector3D(new Point3D(10,10,10), new Point3D(9.99,9.99,10));
		assertTrue(380.1272 <=gps_test_cartesian.x()&&380.1273 >=gps_test_cartesian.x()&&-1044.9586<=gps_test_cartesian.x()&&-1044.9585>=gps_test_cartesian.y()&&-1095.0749<=gps_test_cartesian.z()&&-1095.0748>=gps_test_cartesian.z());
	}

	@Test
	void testAzimuth_elevation_dist() {
		MyCoords cord=new MyCoords();
		double[] test_point=cord.azimuth_elevation_dist(new Point3D(32.106352,35.205225,650),new Point3D(32.103315,35.209039,670));
		assertTrue(141.4705 <=test_point[0]&&141.4706 >=test_point[0]&&2.3226<=test_point[1]&&2.3227>=test_point[1]&&493.5044<=test_point[2]&&493.5045>=test_point[2]);
	}

	@Test
	void testIsValid_GPS_Point() {
		Point3D	test_point=new Point3D(32.106352,35.205225,650);
		assertFalse(test_point.x()<-180 || test_point.x()>180 || test_point.y()<-90 || test_point.y()>90 || test_point.z()<-450);
	}

}
