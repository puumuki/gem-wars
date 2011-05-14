package gameobjects;

import io.ResourceManager;

import java.util.ArrayList;
import java.util.List;

import gameobjects.map.ItemTypes;
import gameobjects.map.Map;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;

import utils.GemwarsUtils;

public class Boulder extends Item implements IDynamic{
		
	private Image staticStone;
	
	private Animation falling;
	private Animation movingLeft, movingRight;	
	
	private Renderable currentAnimation;

	private double distance;
	private Direction lastDirection;
	
	private Map map;
	
	public Boulder( ItemTypes boulderType,  Map map) throws SlickException {
		super( boulderType );
		this.map = map;
		init();
		
		speed = 0.1;
	}
	
	
	private void init() throws SlickException {
						
		if( itemType == ItemTypes.WHITE_BOULDER ) {			
			falling = ResourceManager.fetchAnimation("ROCK_WHITE_DOWN");
			movingRight = ResourceManager.fetchAnimation("ROCK_WHITE_RIGHT");
			movingLeft = GemwarsUtils.reverseAnimation(movingRight);
			
		}		
		else if( itemType == ItemTypes.DARK_BOULDER ) {
			falling = ResourceManager.fetchAnimation("ROCK_DARK_DOWN");
			movingRight = ResourceManager.fetchAnimation("ROCK_DARK_RIGHT");
			movingLeft = GemwarsUtils.reverseAnimation(movingRight);
		}
		else {
			throw new SlickException("Invalid item type can boulder be " +
									 "this kinda? ItemType." + itemType.name() ); // Excellent error message :D
		}	
		
		staticStone = falling.getImage(0);
	}
	

	@Override
	public boolean isPushable() {
		return true;
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
			staticStone.draw(drawX, drawY);
		}
		if( direction == Direction.DOWN ) {
			falling.draw(drawX, (int) (drawY - Item.TILE_HEIGHT + distance ));
		}
		if( direction == Direction.LEFT ) {
			movingLeft.draw((int)(drawX + Item.TILE_WIDTH - distance), drawY);
		}
		if( direction == Direction.RIGHT ) {
			movingRight.draw((int)(drawX - Item.TILE_WIDTH + distance), drawY);
		}
		
		// to debug, uncomment:
		//grap.drawString(positionX + "," + positionY + " = " + drawX + "," + drawY + "\n" + direction + " "+distance, drawX, drawY);
		//grap.drawString(direction + "\n" + map.getGroundLayer().getTile(positionX, positionY + 1).itemType.toString(), drawX, drawY);

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
				if((p.positionX == x && p.positionY == y)
						|| (p.direction == Direction.LEFT && p.positionX == x+1 && p.positionY == y)
						|| (p.direction == Direction.RIGHT && p.positionX == x-1 && p.positionY == y)
						|| (p.direction == Direction.UP && p.positionX == x && p.positionY == y+1 ))
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
				
				for (Player p : map.getPlayers()) {
					if(p.positionX == positionX && p.positionY == positionY)
						p.kill();
				}
				for (Monster m: map.getMonsters()) {
					if(m.positionX == positionX && m.positionY == positionY)
						m.kill();
				}
				
				
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
		
		
	}
}
