package gameobjects;

import io.ResourceManager;

import java.util.ArrayList;
import java.util.List;

import gameobjects.map.ItemTypes;

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
	
	public Boulder( ItemTypes boulderType ) throws SlickException {
		super( boulderType );		
		init();
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
		
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		
	}	
}
