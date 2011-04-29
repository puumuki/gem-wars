package gameobjects.map;

import java.util.List;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import gameobjects.AEntity;

/**
 * The Map class is used to save all the data the game needs
 * to draw the map.
 * 
 */
public class Map extends AEntity {

	/**
	 * ???
	 */
	private List<AEntity>gameObjects;
	
	// TODO: are these needed?
	private static final int MAX_WIDTH = 1024;
	private static final int MAX_HEIGHT = 1024;
	
	/**
	 * Width of the map. Taken from the collision layer.
	 */
	private int width;
	/**
	 * Height of the map. Taken from the collision layer.
	 */
	private int height;
	
	/**
	 * Name of the map
	 */
	private String name;
	
	/**
	 * Name of the creator of the map
	 */
	private String creator;
	
	/**
	 * Gems needed to finish the level
	 */
	private int gemCount;
	
	/**
	 * Time available to finish the level
	 */
	private int time;
	
	/**
	 * The collision layer is saved in this array.
	 * 
	 * boolean[vertical][horizontal] = true or false
	 */
	private boolean[][] collisionLayer;
	
	/**
	 * The ground layer
	 */
	private Layer groundLayer;
	/**
	 * The special layer
	 */
	private Layer specialLayer;
	/**
	 * The object layer
	 */
	private Layer objectLayer;
	
	@Override
	public void render(GameContainer cont, Graphics grap) throws SlickException {
		groundLayer.render(cont, grap);
		specialLayer.render(cont, grap);
		objectLayer.render(cont, grap);
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public void setGemCount(int gems) {
		this.gemCount = gems;
	}
	
	public int getGemCount() {
		return gemCount;
	}

	/**
	 * Creates a new collision layer according to the parameters
	 * @param layerWidth width of the collision layer
	 * @param layerHeight height of the collision layer
	 */
	public void createCollision(int layerWidth, int layerHeight) {
		this.collisionLayer = new boolean[layerHeight][layerWidth];
		setWidth(layerWidth);
		setHeight(layerHeight);
	}

	/**
	 * Sets the collision of a specific tile
	 * @param x horizontal position of the tile to set
	 * @param y vertical position of the tile to set
	 * @param b true, if the player cannot walk on the tile, false, if he can 
	 */
	public void setCollision(int x, int y, boolean b) {
		collisionLayer[y][x] = b;
	}
	
	/**
	 * Returns if the specific tile can be walked on
	 * @param x horizontal position of the tile
	 * @param y vertical position of the tile
	 * @return true, if unwalkable, false, if can be walked on
	 */
	public boolean isColliding(int x, int y) {
		return collisionLayer[y][x];
	}

	/**
	 * Sets a new layer to the map
	 * @param l layer to set
	 * @return true, if the layer was set correctly, false, if there was a problem
	 */
	public boolean setLayer(Layer l) {
		if (l.getType() == LayerTypes.LAYER_GROUND.ordinal()) {
			groundLayer = l;
			return true;
		}
		if (l.getType() == LayerTypes.LAYER_OBJECTS.ordinal()) {
			objectLayer = l;
			return true;
		}
		if (l.getType() == LayerTypes.LAYER_SPECIAL.ordinal()) {
			specialLayer = l;
			return true;
		}
		return false;
		
	}
	
	public Layer getGroundLayer() {
		return groundLayer;
	}
	
	public Layer getSpecialLayer() {
		return specialLayer;
	}
	
	public Layer getObjectLayer() {
		return objectLayer;
	}
}
