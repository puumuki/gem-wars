package gameobjects;

import io.ResourceManager;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
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

	private Animation walkingRight;
	private Animation walkingLeft;
	private Animation walkingUp;
	private Animation walkingDown;
	private Animation pushLeft;
	private Animation pushRight;
	
	public Player() {
		postProcessAnimations();
	}
	
	@Override
	public void render(GameContainer cont, Graphics grap) throws SlickException {
		final int startX = 50;
		final int startY = 50;
		
		walkingRight.draw(startX, startY );
		walkingLeft.draw( startX + 56, startY ); 
		walkingUp.draw( startX + 56 *2, startY);
		walkingDown.draw( startX + 56 *3, startY );
		pushLeft.draw( startX + 56 *4, startY );
		pushRight.draw( startX + 56 *5, startY );
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		walkingRight.update(delta);
		walkingLeft.update(delta);
	}
	
	private void postProcessAnimations() {
		Image image = ResourceManager.getInstance().getImage("UKOT");
		
		int duration = 25;
		
		int frameHeight = 56, farmeWidth = 56;
		
		walkingRight = new Animation();
		walkingLeft = new Animation();
		walkingUp = new Animation();
		walkingDown = new Animation();
		pushRight = new Animation();
		pushLeft = new Animation();
		
		for (int i = 0; i < image.getWidth(); i += farmeWidth ) {
			Image sub = image.getSubImage(i, 0, farmeWidth, frameHeight);
			walkingRight.addFrame(sub, duration);
			
			sub = image.getSubImage(i, frameHeight, farmeWidth, frameHeight);
			walkingLeft.addFrame(sub, duration);
			
			sub = image.getSubImage(i, frameHeight*2, farmeWidth, frameHeight);
			walkingUp.addFrame(sub, duration);
			
			sub = image.getSubImage(i, frameHeight*3, farmeWidth, frameHeight);
			walkingDown.addFrame(sub, duration);
			
			sub = image.getSubImage(i, frameHeight*4, farmeWidth, frameHeight);
			pushRight.addFrame(sub, duration);
			
			sub = image.getSubImage(i, frameHeight*5, farmeWidth, frameHeight);
			pushLeft.addFrame(sub, duration);
		}
	}
}
