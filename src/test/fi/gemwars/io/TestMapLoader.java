package fi.gemwars.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import fi.gemwars.gameobjects.Item;
import fi.gemwars.gameobjects.map.ItemTypes;
import fi.gemwars.gameobjects.map.Map;
import fi.gemwars.io.MapLoader;
import fi.gemwars.io.ResourceManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.newdawn.slick.SlickException;

public class TestMapLoader {

	private String RESOURCE_BASE_PATH = "src/main/resources";

	// FIXME: Problems to make this to run, I had problems to initiaze the
	// ResourceManager
	// It had to be initialized inside the "game loop" have to make some kind
	// mocketimock tha
	// replaces the ResourceManager
	public void loadResourceFile() {
		try {
			String path = RESOURCE_BASE_PATH + "/resources.xml";
			InputStream stream = new FileInputStream(new File(path));
			ResourceManager.getInstance().loadResources(stream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Ignore
	public void loadMaps() throws IOException, SlickException {
		Item item = new Item(ItemTypes.BLUE_GEM);

		MapLoader mapLoader = new MapLoader();
		Map map = mapLoader.loadMap(new File(RESOURCE_BASE_PATH + "/maps/e1l1.gem"));

		Assert.assertEquals("Untitled", map.getName());
		Assert.assertEquals("Unknown", map.getCreator());

		Assert.assertEquals(23, map.getWidth());
		Assert.assertEquals(11, map.getHeight());
		Assert.assertEquals(65, map.getTime());
		Assert.assertEquals(9, map.getGemCount());

		Assert.assertEquals(ItemTypes.METAL_WALL, map.getGroundLayer().getTile(0, 0).itemType);
		Assert.assertEquals(ItemTypes.METAL_WALL, map.getGroundLayer().getTile(1, 0).itemType);
		Assert.assertEquals(ItemTypes.METAL_WALL, map.getGroundLayer().getTile(3, 0).itemType);
		Assert.assertEquals(ItemTypes.METAL_WALL, map.getGroundLayer().getTile(4, 0).itemType);
		Assert.assertEquals(ItemTypes.METAL_WALL, map.getGroundLayer().getTile(5, 0).itemType);
		Assert.assertEquals(ItemTypes.METAL_WALL, map.getGroundLayer().getTile(6, 0).itemType);
		Assert.assertEquals(ItemTypes.METAL_WALL, map.getGroundLayer().getTile(7, 0).itemType);

		Assert.assertEquals(ItemTypes.METAL_WALL, map.getGroundLayer().getTile(22, 10).itemType);

		Assert.assertEquals(ItemTypes.BROWN_WALL, map.getGroundLayer().getTile(2, 2).itemType);
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

	@Ignore
	public void testFindingMaps() {
		List<File> files = MapLoader.findAvailableMaps(new File(RESOURCE_BASE_PATH + "maps/"));

		Assert.assertTrue(files.contains(new File(RESOURCE_BASE_PATH + "/maps/e1l1.gem")));
		Assert.assertTrue(files.contains(new File(RESOURCE_BASE_PATH + "/maps/e1l2.gem")));
		Assert.assertTrue(files.contains(new File(RESOURCE_BASE_PATH + "/maps/e1l3.gem")));
		Assert.assertTrue(files.contains(new File(RESOURCE_BASE_PATH + "/maps/e1l4.gem")));
		Assert.assertTrue(files.contains(new File(RESOURCE_BASE_PATH + "/maps/e2l1.gem")));
		Assert.assertTrue(files.contains(new File(RESOURCE_BASE_PATH + "/maps/e2l2.gem")));
		Assert.assertTrue(files.contains(new File(RESOURCE_BASE_PATH + "/maps/e2l3.gem")));
		Assert.assertTrue(files.contains(new File(RESOURCE_BASE_PATH + "/maps/e2l4.gem")));
		Assert.assertTrue(files.contains(new File(RESOURCE_BASE_PATH + "/maps/e3l1.gem")));
		Assert.assertTrue(files.contains(new File(RESOURCE_BASE_PATH + "/maps/e3l2.gem")));
		Assert.assertTrue(files.contains(new File(RESOURCE_BASE_PATH + "/maps/e3l3.gem")));
		Assert.assertTrue(files.contains(new File(RESOURCE_BASE_PATH + "/maps/e3l4.gem")));
		Assert.assertTrue(files.contains(new File(RESOURCE_BASE_PATH + "/maps/e4l1.gem")));
		Assert.assertTrue(files.contains(new File(RESOURCE_BASE_PATH + "/maps/e4l2.gem")));
		Assert.assertTrue(files.contains(new File(RESOURCE_BASE_PATH + "/maps/e4l3.gem")));
		Assert.assertTrue(files.contains(new File(RESOURCE_BASE_PATH + "/maps/e4l4.gem")));
		Assert.assertTrue(files.contains(new File(RESOURCE_BASE_PATH + "/maps/e5l1.gem")));
		Assert.assertTrue(files.contains(new File(RESOURCE_BASE_PATH + "/maps/e5l2.gem")));
		Assert.assertTrue(files.contains(new File(RESOURCE_BASE_PATH + "/maps/e5l3.gem")));
		Assert.assertTrue(files.contains(new File(RESOURCE_BASE_PATH + "/maps/e5l4.gem")));
		Assert.assertTrue(files.contains(new File(RESOURCE_BASE_PATH + "/maps/e6l1.gem")));
		Assert.assertTrue(files.contains(new File(RESOURCE_BASE_PATH + "/maps/race1.gem")));
	}
}
