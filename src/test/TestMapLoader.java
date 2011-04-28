package test;

import java.io.File;
import java.io.IOException;

import io.MapLoader;

import org.junit.Test;
import org.newdawn.slick.SlickException;

public class TestMapLoader {

	private String mapPath = "src/resources/images";
	
	
	@Test
	public void loadMaps() throws IOException, SlickException {
		MapLoader.loadMap(new File("src/resources/maps/e1l1.gem"));
		
		
		
	}
	
}
