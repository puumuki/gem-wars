package gameobjects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * 
 * @author Teemuki
 *
 * Base class for all game objects. 
 */
public abstract class AEntity {
	
	/**
	 * Tells witch tile entity is horizontally
	 */
	public int positionX;
	
	/**
	 * Tells witch tile entity is vertically
	 */
	public int positionY;
	
	/**
	 * Witch way object is moving
	 */
	public Direction direction;
	
	/**
	 * How fast entity is moving. If value is negative object is static.
	 */
	public double speed;
	
	/**
	 * Layer number means a drawing order.
	 * Entities with smallest numbers are drawn first.
	 */
	public int layer;
	
	/**
	 * Entity is rendered to a screen when this method is called.
	 * 
	 * @param cont Don't read any input or update physics in this method.
	 * @param grap Use this Graphics object to draw the entity so the drawing order is right.
	 * @throws SlickException
	 */
	public abstract void render(GameContainer cont, 					    
					   			Graphics grap) 
					    		throws SlickException;
	
	/**
	 * In this method a entity physics are updated. Input from user
	 * are handled and so on. 
	 * 
	 * @param cont
	 * @param delta
	 * @throws SlickException
	 */
	public abstract void update(GameContainer cont, 
								int delta) throws SlickException ;
}
