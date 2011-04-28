package gameobjects;

import io.ResourceManager;

import java.util.HashMap;
import java.util.Map;

import gameobjects.map.ItemTypes;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;




public class Item extends AEntity {
	
	private static Map<ItemTypes, Image> itemTextures = new HashMap<ItemTypes, Image>();  
	
	/**
	 * Tile number from left starting from index 0
	 * Textures are loaded from windb.bmp
	 */
	public ItemTypes itemType;
	
	private static final int TILE_WIDTH = 64;
	private static final int TILE_HEIGHT = 64;
	
	static {
		ResourceManager manager = ResourceManager.getInstance();	
		Image textures = manager.getImage("items");
		
		for( ItemTypes type : ItemTypes.values() ) {
			itemTextures.put(type, textures.getSubImage(0, TILE_WIDTH * (type.ordinal() - 1) , 
														   TILE_WIDTH, TILE_HEIGHT));
		}
	}
	
	public Item( ItemTypes itemType ) {
		this.itemType = itemType;
	}
	
	@Override
	public void render(GameContainer cont, Graphics grap) throws SlickException {
		itemTextures.get(itemType).draw(positionX, positionY);
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		//Don't do anything
	}
}
