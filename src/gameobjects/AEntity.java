package gameobjects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * @author Teemuki
 *
 * Base class for all the objects of the game. 
 */
public abstract class AEntity implements Comparable<AEntity>, IGameObject {
	
	/**
	 * The horizontal position of the tile entity (in tiles!)
	 */
	public int positionX;
	
	/**
	 * The vertical position of the tile entity (in tiles!) 
	 */
	public int positionY;
	
	/**
	 * Which way the object is moving or if it is moving at all
	 */
	public Direction direction = Direction.STATIONARY;
	
	/**
	 * How fast the entity is moving. If the value is negative the object is stationary.
	 */
	public double speed;
	
	/**
	 * Layer number means a drawing order.
	 * Entities with smallest numbers are drawn first.
	 */
	public int layer;
	
	/**
	 * How far we've gone from the last position
	 */
	public double distance;
	
	/**
	 * The entity is rendered to the screen when this method is called.
	 * 
	 * @param cont Don't read any input or update physics in this method.
	 * @param grap Use this Graphics object to draw the entity so the drawing order is right.
	 * @throws SlickException if something goes wrong
	 */
	@Override
	public abstract void render(GameContainer cont, 					    
					   			Graphics grap) 
					    		throws SlickException;
	
	/**
	 * In this method an entity's physics are updated, input from user
	 * are handled and so on. 
	 * 
	 * @param cont the container of the game, can give us the input of the player for example
	 * @param delta 
	 * @throws SlickException if something goes wrong
	 */
	@Override
	public abstract void update(GameContainer cont, 
								int delta) throws SlickException ;
	
	@Override
	public int compareTo(AEntity o) {
		
		int compare = 0;
		
		if( o.layer > this.layer )
			compare = 1;
		else if( o.layer < this.layer )
			compare = -1;
		
		return compare;
	}
}
