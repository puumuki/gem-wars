package fi.gemwars.gameobjects;

import fi.gemwars.gameobjects.map.ItemTypes;
import fi.gemwars.gameobjects.map.LayerTypes;
import fi.gemwars.gameobjects.map.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

/**
 * A physics object such as a gem or a boulder can drop down and roll left/right at the end of its drop.
 * They also kill the player if they fall on him.
 * <p>
 * This class does all the "dirty work" to move the objects.
 */
public abstract class PhysicsObject extends Item implements IDynamic {

	/**
	 * The last direction the object moved
	 */
	private Direction lastDirection;
	
	/**
	 * Link to the map we are in
	 */
	protected Map map;
	
	/**
	 * Creates a new physics object
	 * @param itemType what kind of object
	 * @param map link to the map
	 */
	public PhysicsObject(ItemTypes itemType, Map map) {
		super(itemType);
		this.map = map;
	}

	/**
	 * Checks if the object can fall down 
	 * @return true, if it can
	 */
	private boolean canFallDown() {
		return canDrop(positionX, positionY + 1);
	}
	
	/**
	 * Checks if the object can roll right
	 * @return true, if it can
	 */
	private boolean canRollRight() {
		if(map.getGroundLayer().getTile(positionX + 1, positionY).itemType != ItemTypes.MAGIC_GREY_WALL &&
				canDrop(positionX + 1, positionY + 1) && canDrop(positionX + 1, positionY) && gemOrBoulderUnder())
			return true;
			
		return false;
	}
	
	/**
	 * Checks if the object can roll left
	 * @return true, if it can
	 */
	private boolean canRollLeft() {
		if(map.getGroundLayer().getTile(positionX - 1, positionY).itemType != ItemTypes.MAGIC_GREY_WALL &&
				canDrop(positionX - 1, positionY + 1) && canDrop(positionX - 1, positionY) && gemOrBoulderUnder())
			return true;
			
		return false;
	}
	
	/**
	 * Checks if there is a gem or a boulder under the object 
	 * @return
	 */
	private boolean gemOrBoulderUnder() {
		ItemTypes item = map.getObjectLayer().getTile(positionX, positionY + 1).itemType;
		if (item == ItemTypes.WHITE_BOULDER || item == ItemTypes.DARK_BOULDER || item == ItemTypes.BLUE_GEM
				|| item == ItemTypes.RED_GEM || item == ItemTypes.GREEN_GEM)
			return true;
		return false;
	}
	
	/**
	 * Makes some interesting calculations to find out if the object can drop down to a specific location
	 * @param x X position where the object is trying to go 
	 * @param y Y position where the object is trying to go
	 * @return true, if it can move there, false, if not
	 */
	private boolean canDrop(int x, int y) {
		if (map.getGroundLayer().getTile(x, y).itemType == ItemTypes.GROUND) {
			if (map.getObjectLayer().getTile(x, y).itemType != ItemTypes.EMPTY)
				return false;
			for (Player p : map.getPlayers()) {
				if((p.positionX == x && p.positionY == y)
						|| (p.direction == Direction.LEFT && p.positionX == x+1 && p.positionY == y)
						|| (p.direction == Direction.RIGHT && p.positionX == x-1 && p.positionY == y)
						|| (p.direction == Direction.UP && p.positionX == x && p.positionY == y+1 ))
					return false;
			}
			return true;
		}
		else if (map.getGroundLayer().getTile(x, y).itemType == ItemTypes.MAGIC_GREY_WALL && canDrop(x, y+1))
			return true; // the magic wall needs the block under it to be empty
		return false;
	}

	/**
	 * Makes some neat calculations to move the objects. This is called on every update.
	 */
	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		// object is moving
		if( direction != Direction.STATIONARY 
			&& distance <= Item.TILE_HEIGHT) {
			
			distance += speed * delta;
		}
		// object is not moving or has just finished moving
		else {
			distance = 0;
			lastDirection = direction;
			direction = Direction.STATIONARY;
			
			// check for player and monster deaths if they are under the object when it finishes moving
			for (Player p : map.getPlayers()) {
				if(p.positionX == positionX && p.positionY == positionY+1 && lastDirection != Direction.STATIONARY && p.direction == Direction.STATIONARY)
					p.kill();
			}
			for (Monster m: map.getMonsters()) {
				if(m.positionX == positionX && m.positionY == positionY+1 && lastDirection != Direction.STATIONARY && m.direction == Direction.STATIONARY)
					m.kill();
			}
			
			// can it fall down?
			if (canFallDown()) {
				direction = Direction.DOWN;
				map.getObjectLayer().setTile(positionX, positionY, new Item(ItemTypes.EMPTY));
				positionY++;
				map.getObjectLayer().setTile(positionX, positionY, this);
				
				// are we in a magic wall?
				if (inMagicWall()) {
					ItemTypes newItemType = MagicWall.getTransformedItemType(itemType);
					Item newItem;
					if(newItemType == ItemTypes.BLUE_GEM 
						|| newItemType == ItemTypes.GREEN_GEM 
						|| newItemType == ItemTypes.RED_GEM ) {
						
						newItem = new Gem(newItemType, map);
					}
					else if(newItemType == ItemTypes.DARK_BOULDER 
							|| newItemType == ItemTypes.WHITE_BOULDER) {
						
						newItem = new Boulder(newItemType, map);
					}
					else
						newItem = new Item(newItemType);
					
					newItem.setPos(positionX, positionY);
					newItem.layer = LayerTypes.LAYER_OBJECTS.ordinal();
					map.getObjectLayer().setTile(positionX, positionY, newItem);
					newItem.direction = Direction.DOWN; // these two lines are needed for
					newItem.distance += 0.1;			// the item to appear smoothly under the magic tile
				}
				
			 // if not, can it roll right (there is a gem or a boulder under it)?
			} else if (canRollRight()) {
				direction = Direction.RIGHT;
				map.getObjectLayer().setTile(positionX, positionY, new Item(ItemTypes.EMPTY));
				positionX++;
				map.getObjectLayer().setTile(positionX, positionY, this);
				
			 // if not, can it roll left (there is a gem or a boulder under it)?
			} else if (canRollLeft()) { 
				direction = Direction.LEFT;
				map.getObjectLayer().setTile(positionX, positionY, new Item(ItemTypes.EMPTY));
				positionX--;
				map.getObjectLayer().setTile(positionX, positionY, this);
				
			// just stay put.
			} else {
				direction = Direction.STATIONARY;
			}
		}
		
		
	}
	
	/**
	 * Checks if the object is located in a magic wall
	 * @return true if it is, false if it isn't
	 */
	private boolean inMagicWall() {
		if (map.getGroundLayer().getTile(positionX, positionY - 1).itemType == ItemTypes.MAGIC_GREY_WALL)
			return true;
		
		return false;
	}

	/**
	 * This is needed for checking if a boulder was last pushed
	 * @return direction we moved the last time
	 */
	public Direction getLastDirection() {
		return lastDirection;
	}

}
