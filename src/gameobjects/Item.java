package gameobjects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Item extends AEntity{

	/**
	 * Tile number from left starting from index 0
	 * Textures are loaded from windb.bmp
	 */
	public int itemType;
	
	public Item( int itemType ) {
		this.itemType = itemType;
	}
	
	@Override
	public void render(GameContainer cont, Graphics grap) throws SlickException {
	
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		
	}
}
