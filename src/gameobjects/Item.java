package gameobjects;

import io.ResourceManager;
import io.Resources;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import gameobjects.map.ItemTypes;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;




public class Item extends AEntity {
	
	private static Map<ItemTypes, Image> itemTextures = new HashMap<ItemTypes, Image>();  
	
	/**
	 * Tile number from left starting from index 0
	 * Textures are loaded from windb.bmp
	 */
	public ItemTypes itemType;
	
	/**
	 * Filter color
	 */
	public static final Color filter = new Color(0xff00ff00);
	
	private static final int TILE_WIDTH = 56;
	private static final int TILE_HEIGHT = 56;
			
	public Item( ItemTypes itemType ) {
		this.itemType = itemType;
		
		if( itemTextures.size() == 0 ) {
			ResourceManager manager = ResourceManager.getInstance();	
			Image textures = manager.getImage( Resources.ITEM_TEXTURES.name() );
									
			
			
			for( ItemTypes type : ItemTypes.values() ) {				
				Image texture = textures.getSubImage(TILE_WIDTH * (type.ordinal()), 0, 
													 TILE_WIDTH , TILE_HEIGHT);						
				itemTextures.put(type, texture) ;
			}
		}
	}
	
	@Override
	public void render(GameContainer cont, Graphics grap) throws SlickException {
		itemTextures.get(itemType).draw(positionX, positionY, filter);
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		//Don't do anything
	}
}
