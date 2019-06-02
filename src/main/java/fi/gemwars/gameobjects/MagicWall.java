package fi.gemwars.gameobjects;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import fi.gemwars.gameobjects.map.ItemType;
import fi.gemwars.io.ResourceManager;

/**
 * A magic wall! It is animated. Some more magicks might be implemented later.
 *
 */
public class MagicWall extends Item {

	private Animation wall;

	public MagicWall(ItemType itemType) throws SlickException {
		super(itemType);

		wall = ResourceManager.fetchAnimation("MAGIC_WALL");
	}

	@Override
	public void render(GameContainer cont, Graphics grap) throws SlickException {
		grap.drawAnimation(wall, positionX * TILE_WIDTH, positionY * TILE_HEIGHT);
	}

	public static ItemType getTransformedItemType(ItemType type) {
		ItemType[] gems = { ItemType.BLUE_GEM, ItemType.RED_GEM, ItemType.GREEN_GEM };
		ItemType[] boulders = { ItemType.DARK_BOULDER, ItemType.WHITE_BOULDER };
		if (type == ItemType.DARK_BOULDER || type == ItemType.WHITE_BOULDER)
			return gems[(int) Math.floor(Math.random() * gems.length)];

		return boulders[(int) Math.floor(Math.random() * boulders.length)];
	}

}
