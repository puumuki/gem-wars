package gameobjects.map;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

import gameobjects.AEntity;
import gameobjects.Direction;
import gameobjects.Item;
import gameobjects.Monster;
import gameobjects.Player;

/**
 * The Map class is used to save all the data the game needs
 * to draw the map.
 * 
 */
public class Map extends AEntity {
	
	private String filename; 
	
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
	
	/**
	 * The player objects are in this list
	 */
    private List<Player> players = new ArrayList<Player>();
    
    private List<Monster> monsters = new ArrayList<Monster>();
    
    
    private double cameraPositionX = 0;
    private double cameraPositionY = 0;
    
	
	@Override
	public void render(GameContainer cont, Graphics graph) throws SlickException {
		

		graph.translate((float)cameraPositionX, 
						(float)cameraPositionY);
		
		groundLayer.render(cont, graph);
		specialLayer.render(cont, graph);
		objectLayer.render(cont, graph);
				
		for( Player player : players ) {
			player.render(cont, graph);
		}
		
		for ( Monster m : monsters ) {
			m.render(cont, graph);
		}

		graph.resetTransform();
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		for( Player player : players ) {
			player.update(cont, delta);
		}
		Player p = players.get(0);
		if (p.direction != Direction.STATIONARY) {
			centerCameraToPlayer(cont, p);
		}
		
		for (Monster m : monsters) {
			m.update(cont, delta);
		}
	}

	private void centerCameraToPlayer(GameContainer cont, Player p) {
		cameraPositionX = -1 * p.positionX * Item.TILE_WIDTH + cont.getWidth() / 2 - Item.TILE_WIDTH / 2;
		cameraPositionY = -1 * p.positionY * Item.TILE_HEIGHT + cont.getHeight() / 2 - Item.TILE_HEIGHT / 2;
		
		if (p.direction == Direction.DOWN)
			cameraPositionY -= p.getDistance();
		if (p.direction == Direction.UP)
			cameraPositionY += p.getDistance();
		if (p.direction == Direction.LEFT)
			cameraPositionX += p.getDistance();
		if (p.direction == Direction.RIGHT)
			cameraPositionX -= p.getDistance();
	}
	
	/**
	 * Find he starting positions from the map and 
	 * creates Player-objects to those starting positions.
	 */
	public void initPlayers() {
		initPlayers(null);
	}
	
	public void initPlayers(List<Player> players) {
		if(players == null)
		{
			for(Point startingPosition : getStartingPositions() ) {
				Log.debug("Creating player to position: " + startingPosition );
				Player player = new Player(startingPosition.x, startingPosition.y, this);
				this.players.add(player);
			}
			return;
		}
		
		for(Player p : players) {
			for(Point startingPosition : getStartingPositions() ) {
				this.players.clear();
				this.players.add(p);
				p.positionX = startingPosition.x;
				p.positionY = startingPosition.y;
			}
		}

	}
	
	/**
	 * Return starting positions from the map. There can be
	 * multiple starting positions in multiplayer maps.
	 * 
	 * @return List<Point> all starting positions
	 */
	public List<Point> getStartingPositions() {
		return findItemPositions(specialLayer, ItemTypes.START );
	}
	
	/**
	 * @return all ending points from map.  
	 */
	public List<Point> findEndingPoint() {
		return findItemPositions( specialLayer, ItemTypes.GOAL ); 
	}
	
	private List<Point> findItemPositions( Layer layer, ItemTypes type ) {
		List<Point> positions = new ArrayList<Point>();
		
		for (int x = 0; x < specialLayer.getWidth(); x++) {
			for (int y = 0; y < specialLayer.getHeight(); y++) {
				if( specialLayer.tiles[x][y].itemType == type) {
					positions.add( new Point(x,y));
				}
			}
		}
		
		return positions;
	}
	

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilename() {
		return filename;
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
		if (x >= 0 && x < collisionLayer[y].length && y >= 0 && y < collisionLayer.length)
			return collisionLayer[y][x];
		return true;
	}
	
	/**
	 * Check is tile containing a gem in the given coordinates.
	 * 
	 * @param x 
	 * @param y 
	 * @return true if tile is containing a gem, false if not.
	 */
	public boolean isTileContainingGem(int x, int y ) {
		Item item = objectLayer.getTile(x, y);
		
		if( item.itemType == ItemTypes.RED_GEM 
			|| item.itemType == ItemTypes.BLUE_GEM 
			|| item.itemType == ItemTypes.GREEN_GEM ) {
		
			return true;			
		}
		
		return false;
	}
	
	
	
	public boolean isTileContainingDirt(int x, int y) {
		Item item = groundLayer.getTile(x, y);
		
		if( item.itemType == ItemTypes.SAND ) {
			return true;
		}
		
		return false;
	}
	
	public boolean destroyDirt(int x, int y) {
		
		if(isTileContainingDirt(x, y)) {
			Item item = new Item(ItemTypes.GROUND);						
			groundLayer.setTile(x, y, item);
			
			return true;
		}
		
		return false;
	}
	
	public void destroyGem(int x, int y) {
		if(isTileContainingGem(x, y)) {
			objectLayer.setTile(x, y, new Item(ItemTypes.EMPTY));
		}
	}
	
	/**
	 * Returns if the specific tile can be walked on by a monster
	 * @param x horizontal position of the tile
	 * @param y vertical position of the tile
	 * @return true, if unwalkable, false, if can be walked on
	 */
	public boolean isMonsterColliding(int x, int y) {
		if (groundLayer.getTile(x, y).itemType != ItemTypes.GROUND)
			return true;
		else if (objectLayer.getTile(x, y).itemType == ItemTypes.BLUE_GEM)
			return true;
		else if (objectLayer.getTile(x, y).itemType == ItemTypes.RED_GEM)
			return true;
		else if (objectLayer.getTile(x, y).itemType == ItemTypes.GREEN_GEM)
			return true;
		else if (objectLayer.getTile(x, y).itemType == ItemTypes.DARK_BOULDER)
			return true;
		else if (objectLayer.getTile(x, y).itemType == ItemTypes.WHITE_BOULDER)
			return true;
		else if (specialLayer.getTile(x, y).itemType != ItemTypes.EMPTY)
			return true;
		else if (x >= 0 && x < collisionLayer[y].length && y >= 0 && y < collisionLayer.length)
			return collisionLayer[y][x];
		return true;
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

	public void enter(GameContainer cont) {
		centerCameraToPlayer(cont, players.get(0));
	}
	
	/**
	 * returns the player for example to draw the score etc.
	 * @param number the player number
	 * @return the player we want
	 */
	public Player getPlayer(int number) {
		return players.get(number);
	}

	public void add(Monster monster) {
		monsters.add(monster);
	}

	public List<Player> getPlayers() {
		return players;
	}

	public boolean hasPlayerDied(Player player) {
		for (Monster m : monsters) {
			if(m.positionX == player.positionX && m.positionY == player.positionY)
				return true;
		}
		return false;
	}
}
