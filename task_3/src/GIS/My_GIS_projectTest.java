package GIS;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.Test;

class My_GIS_projectTest {

	@Test
	void testMy_GIS_project() {
		try {
			My_GIS_project my_GIS_project = new My_GIS_project(new My_meta_data(new Date().getTime(),null,"7f00ffff"));	
		} catch (IllegalArgumentException e) {  
			fail("Exception was expected for null input");
		}	
		}	

}
