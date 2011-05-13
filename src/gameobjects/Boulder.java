package gameobjects;

import gameobjects.map.ItemTypes;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Boulder extends Item implements IDynamic{

	public Boulder() {
		super(ItemTypes.WHITE_BOULDER);
	}
	
	@Override
	public boolean isPushable() {
		return true;
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
