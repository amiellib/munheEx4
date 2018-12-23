package GIS;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class My_meta_dataTest {

	@Test
	void testMy_meta_data() {
		
		try {
			My_meta_data my_meta_data = new My_meta_data(0, null, null);	
		} catch (IllegalArgumentException e) {  
			fail("Exception was expected for bad input");
		}	}

}
