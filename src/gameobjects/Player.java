package gameobjects;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * 
 * @author Teemuki
 *
 * Player class holds a implementation of human controller entity.
 */
public class Player extends AEntity {
	
	/**
	 * Lives that the player has left.
	 */
	public int lives;
	
	/**
	 * Player scores from the collected gems.
	 */
	public int scores;
	
	private Map<Direction, Animation> animations = new HashMap<Direction, Animation>();

	@Override
	public void render(GameContainer cont, Graphics grap) throws SlickException {
		
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		
	}
}
