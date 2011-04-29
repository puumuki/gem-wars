package gameobjects;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * @author Teemuki
 * 
 * The Player class holds an implementation of human controller entity.
 */
public class Player extends AEntity {
	
	/**
	 * Lives that the player has left.
	 */
	public int lives;
	
	/**
	 * Player score from the collected gems.
	 */
	public int score;
	
	/**
	 * How many gems the player has collected.
	 */
	public int collectedGemCount;
	
	private Map<Direction, Animation> animations = new HashMap<Direction, Animation>();

	@Override
	public void render(GameContainer cont, Graphics grap) throws SlickException {
		
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		
	}
}
