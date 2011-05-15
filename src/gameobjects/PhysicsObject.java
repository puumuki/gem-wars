package gameobjects;

import gameobjects.map.ItemTypes;
import gameobjects.map.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

public abstract class PhysicsObject extends Item implements IDynamic {

	private Direction lastDirection;
	
	private Map map;
	
	public PhysicsObject(ItemTypes itemType, Map map) {
		super(itemType);
		this.map = map;
	}

	/**
	 * Checks if the boulder can fall down 
	 * @return true, if it can
	 */
	public boolean isFalling() {
		if(canDrop(positionX, positionY + 1))
			return true;
			
		return false;
	}
	
	/**
	 * Checks if the boulder can roll right
	 * @return true, if it can
	 */
	public boolean isRollingRight() {
		if(canDrop(positionX + 1, positionY + 1) && canDrop(positionX + 1, positionY))
			return true;
			
		return false;
	}
	
	/**
	 * Checks if the boulder can roll left
	 * @return true, if it can
	 */
	public boolean isRollingLeft() {
		if(canDrop(positionX - 1, positionY + 1) && canDrop(positionX - 1, positionY))
			return true;
			
		return false;
	}
	
	/**
	 * Can the boulder
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean canDrop(int x, int y) {
		if (map.getGroundLayer().getTile(x, y).itemType == ItemTypes.GROUND) {
			if (map.getObjectLayer().getTile(x, y).itemType != ItemTypes.EMPTY)
				return false;
			for (Player p : map.getPlayers()) {
				if(((p.positionX == x && p.positionY == y)
						|| (p.direction == Direction.LEFT && p.positionX == x+1 && p.positionY == y)
						|| (p.direction == Direction.RIGHT && p.positionX == x-1 && p.positionY == y)
						|| (p.direction == Direction.UP && p.positionX == x && p.positionY == y+1 ))
						&& lastDirection != Direction.STATIONARY)
					return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		if( direction != Direction.STATIONARY 
			&& distance <= Item.TILE_HEIGHT) { 					// boulder is moving
			
			distance += speed * delta;
		}
		else { 													// boulder is not moving or has just finished moving
			distance = 0;
			lastDirection = direction;
			direction = Direction.STATIONARY;
		if (isFalling()) { 													// can it fall down?
				direction = Direction.DOWN;
				map.getObjectLayer().setTile(positionX, positionY, new Item(ItemTypes.EMPTY));
				positionY++;
				map.getObjectLayer().setTile(positionX, positionY, this);
				
				for (Player p : map.getPlayers()) {
					if(p.positionX == positionX && p.positionY == positionY)
						p.kill();
				}
				for (Monster m: map.getMonsters()) {
					if(m.positionX == positionX && m.positionY == positionY)
						m.kill();
				}
				
				
			} else if (lastDirection == Direction.DOWN && isRollingRight()) { // if not, can it roll right after falling down?
				direction = Direction.RIGHT;
				map.getObjectLayer().setTile(positionX, positionY, new Item(ItemTypes.EMPTY));
				positionX++;
				map.getObjectLayer().setTile(positionX, positionY, this);
			} else if (lastDirection == Direction.DOWN && isRollingLeft()) { // if not, can it roll left after falling down? 
				direction = Direction.LEFT;
				map.getObjectLayer().setTile(positionX, positionY, new Item(ItemTypes.EMPTY));
				positionX--;
				map.getObjectLayer().setTile(positionX, positionY, this);
			} else { 														// just stay put.
				direction = Direction.STATIONARY;
			}
		}
		
		if (isPushable()) {
			
		}
		
	}

}
