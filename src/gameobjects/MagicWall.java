package gameobjects;

import io.ResourceManager;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import gameobjects.map.ItemTypes;

/**
 * A magic wall! It is animated.
 * Some more magicks might be implemented later.
 *
 */
public class MagicWall extends Item {

	private Animation wall;
	
	public MagicWall(ItemTypes itemType) throws SlickException {
		super(itemType);
		
		wall = ResourceManager.fetchAnimation("MAGIC_WALL");
	}	
	
	@Override
	public void render(GameContainer cont, Graphics grap) throws SlickException {
		grap.drawAnimation(wall, positionX * TILE_WIDTH, positionY * TILE_HEIGHT);
	}
	
	public static ItemTypes getTransformedItemType(ItemTypes type) {
		ItemTypes[] gems = {ItemTypes.BLUE_GEM, ItemTypes.RED_GEM, ItemTypes.GREEN_GEM};
		ItemTypes[] boulders = {ItemTypes.DARK_BOULDER, ItemTypes.WHITE_BOULDER};
		if(type == ItemTypes.DARK_BOULDER || type == ItemTypes.WHITE_BOULDER)
			return gems[(int)Math.floor(Math.random()*gems.length)];
		
		return boulders[(int)Math.floor(Math.random()*boulders.length)];
	}

}
