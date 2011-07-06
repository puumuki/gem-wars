package gameobjects.map;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;

import utils.TimeCounter;

import gameobjects.AEntity;
import gameobjects.Boulder;
import gameobjects.Direction;
import gameobjects.Gem;
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
    
    private boolean goalOpen = false;
    private boolean goalAnimation = false;
    
    private TimeCounter timer = new TimeCounter();
	
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
		
		objectLayer.update(cont, delta);
		
		//FIXME: We are not taking count another player, the camera is centered always to first player		
		Player playerOne = players.get(0);
		
		if (playerOne.direction != Direction.STATIONARY) {
			centerCameraToPlayer(cont, playerOne);
		}
		
		// remove dead monsters
		for (int i = 0; i < monsters.size(); i++) {
			Monster monster = monsters.get(i);
			if(monster.isDead())
				monsters.remove(i);
			monster.update(cont, delta);
		}
		
		//FIXME: We should count gem count from all players here
		if (goalOpen == false && players.get(0).getGems() >= gemCount) {
			openGoal();
		}
		
		// time up?
		if(isTimeUp()) {
			for (Player player : players) {
				if( player.isDead() == false ) {
					player.kill();	
				}			
			}
		}

		for( Player player : players ) {
			player.update(cont, delta);
		}
	}

	public void resetPlayers() {
		//This should be called after all players are dead
		//It could be reset players to starting positions and keep their stuff
		//map should too keep it state unchanged after a player dies.
		
		List<Point>startingPositions = getStartingPositions();
		
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);			
			Point startingPos = startingPositions.get(i);
			
			player.positionX = startingPos.x;
			player.positionY = startingPos.y;
			
			//Player shall come back from heavens to finish the his work
			player.setDead(false);
		}
		
		//Should we reset enemies?
		
		
	}
	
	public boolean isTimeUp() {
		return (time*1000 - timer.timeElapsedInMilliseconds()) <= 0;
	}

	/**
	 * Opens the goal for the player to get in
	 */
	private void openGoal() {
		goalOpen = true;
		List<Point> goals = findItemPositions(specialLayer, ItemTypes.GOAL);
		Point p = goals.get(0);
		setCollision(p.x, p.y, false);
		Item i = new Item(ItemTypes.GOAL_TILE);
		i.positionX = p.x;
		i.positionY = p.y;
		groundLayer.setTile(p.x, p.y, i);
		
	}

	/**
	 * Transforms the camera position according to the player movements
	 * @param cont game container
	 * @param p the player that we follow
	 */
	private void centerCameraToPlayer(GameContainer cont, Player p) {
		int mapMaxX = groundLayer.getWidth() * Item.TILE_WIDTH;
		int mapMaxY = groundLayer.getHeight() * Item.TILE_HEIGHT;

		cameraPositionX = -1 * p.positionX * Item.TILE_WIDTH + cont.getWidth() / 2 - Item.TILE_WIDTH / 2;		
		cameraPositionY = -1 * p.positionY * Item.TILE_HEIGHT + cont.getHeight() / 2 - Item.TILE_HEIGHT / 2;
		
		if (p.direction == Direction.DOWN) {
				cameraPositionY -= p.getDistance();
		}
		if (p.direction == Direction.UP) {
			cameraPositionY += p.getDistance();
		}
		if (p.direction == Direction.LEFT) {
			cameraPositionX += p.getDistance();
		}
		if (p.direction == Direction.RIGHT) {
			cameraPositionX -= p.getDistance();
		}
		
		if (cameraPositionX < -1 * (mapMaxX - cont.getWidth()))
			cameraPositionX = -1 * (mapMaxX - cont.getWidth());
		if (cameraPositionY < -1 * (mapMaxY - cont.getHeight()))
			cameraPositionY = -1 * (mapMaxY - cont.getHeight());
		if (cameraPositionX > 0)
			cameraPositionX = 0;
		if (cameraPositionY > 0)
			cameraPositionY = 0;
		
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
		
		List<Player> newPlayers = new ArrayList<Player>();
		List<Point> starts = getStartingPositions();
		
		for(int i = 0; i < starts.size(); i++) {
			
			Point startPos = starts.get(i);
			
			// set players back
			if(i < players.size()) {
				Player p = players.get(i).copy();
				newPlayers.add(p);
				Log.debug("Recreating player "+i+" to position: " + startPos);
				p.positionX = startPos.x;
				p.positionY = startPos.y;
				p.setMap(this);
				p.collectedGemCount = 0;
				p.direction = Direction.STATIONARY;
				p.distance = 0;
				p.pushingStone = false;
			}
			// more starting points than players
			else {
				Log.debug("Not enough players; creating player "+i+" to position: " + startPos);
				Player player = new Player(startPos.x, startPos.y, this);
				newPlayers.add(player);
			}
		}
		
		
		this.players.clear();
		this.players = newPlayers;
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
	
	public List<Point> findItemPositions( Layer layer, ItemTypes type ) {
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
		if (objectLayer.getTile(x, y).itemType != ItemTypes.BLUE_GEM
				&& objectLayer.getTile(x, y).itemType != ItemTypes.RED_GEM
				&& objectLayer.getTile(x, y).itemType != ItemTypes.GREEN_GEM 
				&& objectLayer.getTile(x, y).itemType != ItemTypes.EMPTY)
			return true;
		else if (x >= 0 && x < collisionLayer[y].length && y >= 0 && y < collisionLayer.length)
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
	
	/**
	 * Check if a tile contains a boulder in the given coordinates.
	 * 
	 * @param x 
	 * @param y 
	 * @return true if the tile has a boulder in it
	 */
	public boolean isTileContainingBoulder(int x, int y ) {
		Item item = objectLayer.getTile(x, y);
		
		if( item.itemType == ItemTypes.WHITE_BOULDER 
			|| item.itemType == ItemTypes.DARK_BOULDER ) {
			
			return true;			
		}
		
		return false;
	}
	
	
	public boolean isTileContainingSand(int x, int y) {
		Item item = groundLayer.getTile(x, y);
		
		if( item.itemType == ItemTypes.SAND ) {
			return true;
		}
		
		return false;
	}
	
	public boolean destroySand(int x, int y) {
		
		if(isTileContainingSand(x, y)) {
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
		for (Player p : players) {
			p.setDead(false);
		}
		
		timer.start();
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

	public void checkMonsterKills(Player player) {
		for (Monster m : monsters) {
			if(m.positionX == player.positionX && m.positionY == player.positionY && player.isDead() == false) {
				player.kill();
				m.kill();
			}
		}
	}

	public void drawDiamondMatrix(int posX, int posY) throws SlickException {
		for (int x = posX - 1; x <= posX + 1; x++) {
			for (int y = posY - 1; y <= posY + 1; y++) {
				if (!isColliding(x, y)) {
					Gem g = new Gem(ItemTypes.RED_GEM, this);
					Item i = new Item(ItemTypes.GROUND);
					g.positionX = x;
					g.positionY = y;
					i.positionX = x;
					i.positionY = y;
					groundLayer.setTile(x, y, i);
					objectLayer.setTile(x, y, g);
				}
			}
		}
		
	}

	public List<Monster> getMonsters() {
		return monsters;
	}

	public boolean isGoalOpen() {
		return goalOpen;
	}
	
	public int getRemainingTime() {
		return time - (int)timer.timeElapsedInSeconds();
	}
	
	public boolean canPush(int posX, int posY, Direction d) {
		if (isTileContainingBoulder(posX, posY) && d == Direction.LEFT && !isMonsterColliding(posX-1, posY))
			return true;
		if (isTileContainingBoulder(posX, posY) && d == Direction.RIGHT && !isMonsterColliding(posX+1, posY))
			return true;
		return false;
	}

	public void moveBoulder(int posX, int posY, Direction d) {
		Item item = objectLayer.getTile(posX, posY);
		if(item.itemType == ItemTypes.DARK_BOULDER || item.itemType == ItemTypes.WHITE_BOULDER) {
			Boulder b = (Boulder)item;
			b.move(d);
		}
	}
	
	public void goalAnimation() {
		timer.stop();
		goalAnimation = true;
	}
	
	public boolean getGoalAnimation() {
		return goalAnimation;
	}
	
	public boolean isAllPlayersDeath() {
		boolean isThereAnyOneLeft = true;
		
		for (Player player : players) {
			if( player.isDead() == false ) {
				isThereAnyOneLeft = false;
			}
		}
		
		return isThereAnyOneLeft;
	}
}
