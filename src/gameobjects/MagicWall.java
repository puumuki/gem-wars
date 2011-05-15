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

}
