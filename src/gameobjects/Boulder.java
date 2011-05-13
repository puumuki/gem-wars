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

public class Boulder extends Item implements IDynamic{
		
	private Animation falling;
	private Animation movingLeft, movingRight;	
	
	private Renderable currentAnimation;
	
	public Boulder( ItemTypes boulderType ) throws SlickException {
		super( boulderType );		
		init();
	}
	
	private void init() throws SlickException {
						
		if( itemType == ItemTypes.WHITE_BOULDER ) {
			Animation falling = ResourceManager.fetchAnimation("ROCK_WHITE_DOWN");
			Animation movingRight = ResourceManager.fetchAnimation("ROCK_WHITE_RIGHT");
		}		
		else if( itemType == ItemTypes.DARK_BOULDER ) {
			
		}
		else {
			throw new SlickException("Invalid item type can boulder be " +
									 "this kinda? ItemType." + itemType.name() );
		}
		
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
