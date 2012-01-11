package fi.gemwars.gameobjects;

import java.awt.Point;

/**
 *
 * Enumeration to indicate the direction where an entity is moving.
 */
public enum Direction {
	STATIONARY, LEFT, 
	RIGHT, UP, DOWN;
	
	public static Point scanDirection( Direction direction ) {
		
		Point point = new Point();
		
		point.x = 0;
		point.y = 0;
				
		if( direction == Direction.LEFT ) {		
			point.x = -1;
		}
		if( direction == Direction.RIGHT ) {
			point.x = 1;
		}
		if( direction == Direction.UP ) {
			point.y = -1;
		}
		if( direction == Direction.DOWN ) {
			point.y = 1;
		}
		
		return point;
	}
}
