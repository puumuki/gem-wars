package fi.gemwars.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import fi.gemwars.gameobjects.map.ItemType;
import fi.gemwars.gameobjects.map.Map;

public class MapLoaderTest {

	private static final String RESOURCE_BASE_PATH = "src/main/resources";
	private static final String TEST_RESOURCE_BASE_PATH = "src/test/resources";

	private MapLoader mapLoader = new MapLoader();

	// FIXME: Problems to make this to run, I had problems to initiaze the
	// ResourceManager
	// It had to be initialized inside the "game loop" have to make some kind
	// mocketimock tha
	// replaces the ResourceManager
	@Before
	public void loadResourceFile() throws Exception {
		String path = RESOURCE_BASE_PATH + "/resources.xml";
		InputStream stream = new FileInputStream(new File(path));
		ResourceManager.getInstance().loadResources(stream);
	}

	@Test
	@Ignore("This fails because it is tied to lwjgl code: needs to be refactored more")
	public void testLoadMap_whenMapIsFound_itIsLoaded() throws Exception {

		Map map = mapLoader.loadMap(new File(TEST_RESOURCE_BASE_PATH + "/maps/e1l1.gem"));

		Assert.assertEquals("Untitled", map.getName());
		Assert.assertEquals("Unknown", map.getCreator());

		Assert.assertEquals(23, map.getWidth());
		Assert.assertEquals(11, map.getHeight());
		Assert.assertEquals(65, map.getTime());
		Assert.assertEquals(9, map.getGemCount());

		Assert.assertEquals(ItemType.METAL_WALL, map.getGroundLayer().getTile(0, 0).itemType);
		Assert.assertEquals(ItemType.METAL_WALL, map.getGroundLayer().getTile(1, 0).itemType);
		Assert.assertEquals(ItemType.METAL_WALL, map.getGroundLayer().getTile(3, 0).itemType);
		Assert.assertEquals(ItemType.METAL_WALL, map.getGroundLayer().getTile(4, 0).itemType);
		Assert.assertEquals(ItemType.METAL_WALL, map.getGroundLayer().getTile(5, 0).itemType);
		Assert.assertEquals(ItemType.METAL_WALL, map.getGroundLayer().getTile(6, 0).itemType);
		Assert.assertEquals(ItemType.METAL_WALL, map.getGroundLayer().getTile(7, 0).itemType);

		Assert.assertEquals(ItemType.METAL_WALL, map.getGroundLayer().getTile(22, 10).itemType);

		Assert.assertEquals(ItemType.BROWN_WALL, map.getGroundLayer().getTile(2, 2).itemType);
	}

	@Test(expected = IllegalArgumentException.class)
	@Ignore("This fails because it is tied to lwjgl code: needs to be refactored more")
	public void testLoadMap_whenMapIsMissing_throwsException() throws Exception {
		mapLoader.loadMap(new File(TEST_RESOURCE_BASE_PATH + "/maps/nonexistant.gem"));
	}

	@Test
	@Ignore
	public void testCollisionInitialization() throws Exception {
		Map map = new Map();
		map.createCollision(1000, 1000);

		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 1000; j++) {
				Assert.assertFalse(map.isColliding(i, j));
			}
		}

	}

	@Test
	@Ignore
	public void testFindingMaps() throws Exception {
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
