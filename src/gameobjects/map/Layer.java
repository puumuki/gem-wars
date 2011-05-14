package gameobjects.map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import gameobjects.AEntity;
import gameobjects.Item;

/**
 * One map layer. There are four different layer types, three of which
 * use this Layer class (the collision layer is done with a boolean[][]
 * inside the Map class).
 *
 */
public class Layer extends AEntity {

	/**
	 * Width of the layer
	 */
	private int width = 0;
	
	/**
	 * Height of the layer
	 */
	private int height = 0;
	
	/**
	 * Layer tiles. One item per tile.
	 */
	public Item[][] tiles;

	/**
	 * Type of the layer, according to the enum LayerTypes
	 */
	private LayerTypes layerType;
	
	/**
	 * Creates a new layer
	 * @param width width of the layer
	 * @param height height of the layer
	 * @param type type of the layer
	 */
	public Layer( int width, int height , LayerTypes type ) {
		
		this.width = width;
		this.height = height;
		
		this.layerType = type;
		
		
		tiles = new Item[width][height];
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {				
				tiles[x][y] = new Item(ItemTypes.EMPTY);				
			}
		}
	}
	
	/**
	 * @param posX horizontal position
	 * Sets a tile into a specific position
	 * @param posY vertical position
	 * @param tile type of the tile
	 */
	public void setTile( int posX, int posY, Item tile ) {
		
		tile.positionX = posX;
		tile.positionY = posY;
		
		tiles[posX][posY] = tile;
	}
	
	@Override
	public void render(GameContainer cont, Graphics grap) throws SlickException {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				tiles[x][y].render(cont, grap);
			}
		}
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				tiles[x][y].update(cont, delta);
			}
		}
	}

	/**
	 * Gives the name of the tile at a specific position of the layer
	 * @param x horizontal position
	 * @param y vertical position
	 * @return Item tile
	 */
	public Item getTile(int x, int y) {
		return tiles[x][y];
	}
	
	/**
	 * Returns the type of the layer
	 * @return layer type
	 */
	public int getType() {
		return layerType.layerIndex();
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
}
