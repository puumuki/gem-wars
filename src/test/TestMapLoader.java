package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;



import gameobjects.Item;
import gameobjects.map.ItemTypes;
import gameobjects.map.Map;

import io.MapLoader;
import io.ResourceManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.newdawn.slick.SlickException;


public class TestMapLoader {

	private String mapPath = "src/resources/images";
	

	
	@Test
	public void loadMaps() throws IOException, SlickException {
		Item item = new Item( ItemTypes.BLUE_GEM );
		
		Map map = MapLoader.loadMap(new File("src/resources/maps/e1l1.gem"));
		
		Assert.assertEquals("Untitled", map.getName());
		Assert.assertEquals("Unknown", map.getCreator());
		
		Assert.assertEquals(23, map.getWidth());
		Assert.assertEquals(11, map.getHeight());
		Assert.assertEquals(65, map.getTime());
		Assert.assertEquals(9, map.getGemCount());				
	}	
}
