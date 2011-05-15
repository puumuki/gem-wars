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

public class Gem extends PhysicsObject implements IDynamic {

	private Image gemImage;
	
	private Animation gemDown;
	private Animation gemLeft;
	private Animation gemRight;
		
	public Gem(ItemTypes gemtype, Map map) throws SlickException {
		
		super(gemtype, map);
		
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
		//grap.drawString(direction + "\n" + map.getGroundLayer().getTile(positionX, positionY + 1).itemType.toString(), drawX, drawY);

	}
	
	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		super.update(cont, delta);
		
		/*
		gemRight.update(delta);
		gemDown.update(delta);
		gemLeft.update(delta);
		*/
	}
	
	public int getValue() {
		return this.itemType.ordinal() * 10;
	}

}
