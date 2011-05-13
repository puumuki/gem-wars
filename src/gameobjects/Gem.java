package gameobjects;

import gameobjects.map.ItemTypes;
import io.ResourceManager;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Gem extends Item implements IDynamic {

	private Image gemImage;
	
	private Animation gemDown;
	private Animation gemLeft;
	private Animation gemRight;
	
	public Gem(ItemTypes gemtype) {
		super(gemtype);
		
		if(gemtype == ItemTypes.BLUE_GEM) {
			gemImage = ResourceManager.fetchImage("");
			gemDown = ResourceManager.fetchAnimation("GEM_BLUE_DOWN");
			gemLeft = ResourceManager.fetchAnimation("GEM_BLUE_LEFT");
			for (int i = gemLeft.getFrameCount(); i > 0; i--) {
				gemRight.addFrame(gemLeft.getImage(i-1), gemLeft.getDuration(i-1));
			}
		}
			
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
		
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		
	}
}
