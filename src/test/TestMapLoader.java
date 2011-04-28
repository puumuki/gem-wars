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
		
		Assert.assertEquals( ItemTypes.METAL_WALL, map.getGroundLayer().getTile(0, 0).itemType);
		Assert.assertEquals( ItemTypes.METAL_WALL, map.getGroundLayer().getTile(1, 0).itemType);
		Assert.assertEquals( ItemTypes.METAL_WALL, map.getGroundLayer().getTile(3, 0).itemType);
		Assert.assertEquals( ItemTypes.METAL_WALL, map.getGroundLayer().getTile(4, 0).itemType);
		Assert.assertEquals( ItemTypes.METAL_WALL, map.getGroundLayer().getTile(5, 0).itemType);		
		Assert.assertEquals( ItemTypes.METAL_WALL, map.getGroundLayer().getTile(6, 0).itemType);
		Assert.assertEquals( ItemTypes.METAL_WALL, map.getGroundLayer().getTile(7, 0).itemType);
		
		Assert.assertEquals( ItemTypes.METAL_WALL, map.getGroundLayer().getTile(22, 10).itemType);
		
		Assert.assertEquals( ItemTypes.GROUND, map.getGroundLayer().getTile(2,2).itemType);
	}
	
	@Test
	public void testCollisionInitialization() {
		Map map = new Map();
		map.createCollision(1000, 1000);
		
		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 1000; j++) {
				Assert.assertFalse(map.isColliding(i, j));
			}
		}
		
	}
}
