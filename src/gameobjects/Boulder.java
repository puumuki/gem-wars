package gameobjects;

import io.ResourceManager;

import gameobjects.map.ItemTypes;
import gameobjects.map.Map;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Renderable;
import org.newdawn.slick.SlickException;

import utils.GemwarsUtils;

/**
 * A boulder that blocks the player and that he can push. Boulders can kill the player if they fall on him.
 *
 */
public class Boulder extends PhysicsObject implements IDynamic {
		
	private Image staticStone;
	
	private Animation falling;
	private Animation movingLeft, movingRight;	
	
	private Renderable currentAnimation;

	private double friction;
	
	private boolean pushed;
	
	/**
	 * Creates a new boulder.
	 * @param boulderType type (or colour) of the boulder. There could be a slight difference between them
	 * (black boulder has more friction?)
	 * @param map link to the map we are on
	 * @throws SlickException if something goes wrong
	 */
	public Boulder( ItemTypes boulderType,  Map map) throws SlickException {
		super( boulderType , map);
		this.map = map;
		init();
		
		speed = 0.20;
		
		// TODO: set friction
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
		//grap.drawString(this.itemType.toString() + "\n" + direction + " "+distance, drawX, drawY);
		//grap.drawString(direction + "\n" + map.getGroundLayer().getTile(positionX, positionY + 1).itemType.toString(), drawX, drawY);

	}
	
	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		if ((super.getLastDirection() == Direction.LEFT || super.getLastDirection() == Direction.RIGHT)
				&& pushed == true){
			pushed = false;
		}
		super.update(cont, delta);
	}
	
	/**
	 * Used to push the boulder in a new location.
	 * @param d the enum'd direction we are pushing the boulder.
	 */
	public void push(Direction d) {
		distance = 0;
		direction = d;
		pushed = true;
		if(direction == Direction.RIGHT) {
			map.getObjectLayer().setTile(positionX, positionY, new Item(ItemTypes.EMPTY));
			positionX++;
			map.getObjectLayer().setTile(positionX, positionY, this);
		}
		else if(direction == Direction.LEFT) {
			map.getObjectLayer().setTile(positionX, positionY, new Item(ItemTypes.EMPTY));
			positionX--;
			map.getObjectLayer().setTile(positionX, positionY, this);
		}

	}
}
