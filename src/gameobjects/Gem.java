package gameobjects;

import gameobjects.map.ItemTypes;
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
		gemDown.draw(positionX * TILE_WIDTH, positionY * TILE_HEIGHT);
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		gemRight.update(delta);
		gemDown.update(delta);
		gemLeft.update(delta);
	}
}
