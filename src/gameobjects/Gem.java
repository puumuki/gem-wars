package gameobjects;

import gameobjects.map.ItemTypes;
import gameobjects.map.Map;
import io.ResourceManager;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import utils.GemwarsUtils;

public class Gem extends Item implements IDynamic {

	private Image gemImage;
	
	private Animation gemDown;
	private Animation gemLeft;
	private Animation gemRight;
	
	private double distance;
	private Direction lastDirection;
	
	private Map map;
	
	public Gem(ItemTypes gemtype) throws SlickException {
		super(gemtype);
		
		if(gemtype == ItemTypes.BLUE_GEM) {
			gemDown = ResourceManager.fetchAnimation("GEM_BLUE_DOWN");
			gemRight = ResourceManager.fetchAnimation("GEM_BLUE_RIGHT");
			gemLeft = GemwarsUtils.reverseAnimation(gemRight);
		}
		else if(gemtype == ItemTypes.GREEN_GEM) {
			gemDown = ResourceManager.fetchAnimation("GEM_GREEN_DOWN");
			gemRight = ResourceManager.fetchAnimation("GEM_GREEN_RIGHT");
			gemLeft = GemwarsUtils.reverseAnimation(gemRight);
		}
		else if(gemtype == ItemTypes.RED_GEM) {
			gemDown = ResourceManager.fetchAnimation("GEM_RED_DOWN");
			gemRight = ResourceManager.fetchAnimation("GEM_RED_RIGHT");
			gemLeft = GemwarsUtils.reverseAnimation(gemRight);
		}
		else
			throw new SlickException("Incorrect gem type");

		gemImage = gemDown.getImage(0);
		
		speed = 0.1;
	}
	
	public Gem(ItemTypes gemtype, Map map) throws SlickException {
		this(gemtype);
		this.map = map;
	}
	
	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	public boolean isPhysicsAffected() {
		return true;
	}

	@Override
	public void render(GameContainer cont, Graphics grap) throws SlickException {
		int drawX = positionX * Item.TILE_WIDTH;
		int drawY = positionY * Item.TILE_HEIGHT;
		
		if (direction == Direction.STATIONARY) {
			gemDown.draw(drawX, drawY);
		}
		if( direction == Direction.DOWN ) {
			gemDown.draw(drawX, (int) (drawY - Item.TILE_HEIGHT + distance ));
		}
		if( direction == Direction.LEFT ) {
			gemLeft.draw((int)(drawX + Item.TILE_WIDTH - distance), drawY);
		}
		if( direction == Direction.RIGHT ) {
			gemRight.draw((int)(drawX - Item.TILE_WIDTH + distance), drawY);
		}
		
		// to debug, uncomment:
		//grap.drawString(positionX + "," + positionY + " = " + drawX + "," + drawY + "\n" + direction + " "+distance, drawX, drawY);
		grap.drawString(direction + "\n" + map.getGroundLayer().getTile(positionX, positionY + 1).itemType.toString(), drawX, drawY);

	}
	
	public boolean isFalling() {
		if(canDrop(positionX, positionY + 1))
			return true;
			
		return false;
	}
	
	public boolean isRollingRight() {
		if(canDrop(positionX + 1, positionY + 1) && canDrop(positionX + 1, positionY))
			return true;
			
		return false;
	}
	public boolean isRollingLeft() {
		if(canDrop(positionX - 1, positionY + 1) && canDrop(positionX - 1, positionY))
			return true;
			
		return false;
	}
	
	public boolean canDrop(int x, int y) {
		if (map.getGroundLayer().getTile(x, y).itemType == ItemTypes.GROUND) {
			if (map.getObjectLayer().getTile(x, y).itemType != ItemTypes.EMPTY)
				return false;
			for (Player p : map.getPlayers()) {
				if(p.positionX == x && p.positionY == y)
					return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		if( direction != Direction.STATIONARY 
			&& distance <= Item.TILE_HEIGHT) {
			
			distance += speed * delta;
		}
		else {
			distance = 0;
			lastDirection = direction;
			direction = Direction.STATIONARY;
			if (isFalling()) {
				direction = Direction.DOWN;
				map.getObjectLayer().setTile(positionX, positionY, new Item(ItemTypes.EMPTY));
				positionY++;
				map.getObjectLayer().setTile(positionX, positionY, this);
				
			} else if (lastDirection == Direction.DOWN && isRollingRight()) {
				direction = Direction.RIGHT;
				map.getObjectLayer().setTile(positionX, positionY, new Item(ItemTypes.EMPTY));
				positionX++;
				map.getObjectLayer().setTile(positionX, positionY, this);
			} else if (lastDirection == Direction.DOWN && isRollingLeft()) {
				direction = Direction.LEFT;
				map.getObjectLayer().setTile(positionX, positionY, new Item(ItemTypes.EMPTY));
				positionX--;
				map.getObjectLayer().setTile(positionX, positionY, this);
			} else {
				direction = Direction.STATIONARY;
			}
		}
		
		
		gemRight.update(delta);
		gemDown.update(delta);
		gemLeft.update(delta);
	}
	
	public int getValue() {
		return this.itemType.ordinal() * 10;
	}
}
