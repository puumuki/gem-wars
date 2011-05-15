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

public class Boulder extends PhysicsObject implements IDynamic {
		
	private Image staticStone;
	
	private Animation falling;
	private Animation movingLeft, movingRight;	
	
	private Renderable currentAnimation;

	
	private Map map;
	
	public Boulder( ItemTypes boulderType,  Map map) throws SlickException {
		super( boulderType , map);
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
	
	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		super.update(cont, delta);
		
	}
}
