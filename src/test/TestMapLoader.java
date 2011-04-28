package test;

import java.io.File;
import java.io.IOException;

import io.MapLoader;

import org.junit.Test;

public class TestMapLoader {

	private String mapPath = "src/resources/images";
	
	
	@Test
	public void loadMaps() throws IOException {
		MapLoader.loadMap(new File("src/resources/maps/e1l1.gem"));
		
		
		
	}
	
}
