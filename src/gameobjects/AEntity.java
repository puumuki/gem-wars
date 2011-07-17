package gameobjects;

import java.awt.Point;

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
	 * Entity starting position
	 */
	public int startPosX;
	
	/**
	 * Entity starting positionY;
	 */
	public int startPosY;
	
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
	 * If this is set to true entity should not be drawn of effect any how to the game play	
	 */
	public boolean hide;
	
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
	
	/**
	 * Reset a entity to the initial positions
	 */
	public void resetToStaringPosition() {	
		this.positionX = startPosX;
		this.positionY = startPosY;
	}
	
	/**
	 * Init positions with given values, this means starting position and current positions.
	 * @throws SlickException 
	 */
	public void initPosition(int x, int y) throws SlickException {
		setPos(x, y);
		startPosX = x;
		startPosY = y;
	}
	
	//TODO: Should this throw an exception when a given parameter is negative?
	public void setPos( int x, int y ) throws SlickException {
		
		if( x < 0 || y < 0 ) {
			throw new SlickException("Position that are trying to be set is an negative, " +
									  "value shoud be positive. X:" + x + " Y:" + y );
		}
		
		positionX = x;
		positionY = y;
	}
	
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
