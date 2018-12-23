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

	@Test
	void testEdge_until_eat() {
		Point3D edge_until_eat = algo.edge_until_eat(new Point3D(32.10601712909979,35.202961775700935,0) ,new Point3D(32.107,35.204,0) , 2);
		assertTrue(32.1069 <=edge_until_eat.x()&&32.107 >=edge_until_eat.x()&&35.2039<=edge_until_eat.y()&&35.204>=edge_until_eat.y()&&0==edge_until_eat.z());
	}

	@Test
	void testGet_data_from_csv() {
		try {
			Game game = algo.get_data_from_csv("src/resources/test_import_game.csv");
		} catch (IOException e) {
			fail("game was not loaded");
		}
	}

	@Test
	void testCreate_csv_from_game() {
		
		try {
			Game game = algo.get_data_from_csv("src/resources/test_import_game.csv");
			algo.create_csv_from_game(game ,"src/resources/test_export_game.csv");
		} catch (IOException e) {
			fail("game was not exported");
		}
	}

	@Test
	void testGet_max_path_time() {
		Game game;
		try {
			game = algo.get_data_from_csv("src/resources/test_import_game.csv");
			Path [] paths = algo.TSP(game, 1.0);
			double path_time = algo.get_max_path_time(paths);
			assertTrue( 210<=path_time &&240 >=path_time);
			
		} catch (IOException e) {
			fail("game was not loaded");
		}
	}

	@Test
	void testGet_max_path() {
		Game game;
		try {
			game = algo.get_data_from_csv("src/resources/test_import_game.csv");
			Path [] paths = algo.TSP(game, 1.0);
			int path_time = algo.get_max_path(paths);
			assertTrue( 0==path_time);
			
		} catch (IOException e) {
			fail("game was not loaded");
		}	
	}

	@Test
	void testGet_matrix_min() {
		Game game;
		try {
			game = algo.get_data_from_csv("src/resources/test_import_game.csv");
			MatrixMin mat = algo.get_matrix_min(game);

			assertTrue( 3==mat.array_min[0]);
			
		} catch (IOException e) {
			fail("game was not loaded");
		}	
	}

	@Test
	void testGet_location_by_time() {
		Game game;
		try {
			game = algo.get_data_from_csv("src/resources/test_import_game.csv");
			Path [] paths = algo.TSP(game, 1.0);
			Point3D point_by_time = algo.get_location_by_time(paths[0], 10.0);
			assertTrue(32.104 <=point_by_time.x()&&32.105 >=point_by_time.x()&&35.203<=point_by_time.y()&&35.204>=point_by_time.y()&&1.15<=point_by_time.z() &&1.2>=point_by_time.z());
			
		} catch (IOException e) {
			fail("game was not loaded");
		}	
		
	}

	@Test
	void testExport_kml() {
		Game game;
		try {
			game = algo.get_data_from_csv("src/resources/test_import_game.csv");
			Path [] paths = algo.TSP(game, 1.0);
			algo.export_kml(paths, game, "src/resources/test_export_kml.kml");
			
		} catch (IOException e) {
			fail("kml was not exported");
		}		
	}

}
