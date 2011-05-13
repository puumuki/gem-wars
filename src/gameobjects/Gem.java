package gameobjects;

import gameobjects.map.ItemTypes;
import io.ResourceManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Gem extends Item implements IDynamic {

	private Image boulderImage;
	
	public Gem() {
		super(ItemTypes.BLUE_GEM);
		boulderImage = ResourceManager.fetchImage("");
	}
	
	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	public boolean isPhysicsEffected() {
		return true;
	}

	@Override
	public void render(GameContainer cont, Graphics grap) throws SlickException {
		
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		
	}
}
