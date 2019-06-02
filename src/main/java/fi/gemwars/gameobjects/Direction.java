package fi.gemwars.gameobjects;

import java.awt.Point;

/**
 *
 * Enumeration to indicate the direction where an entity is moving.
 */
public enum Direction {
	STATIONARY(0, 0), LEFT(-1, 0), RIGHT(1, 0), UP(0, -1), DOWN(0, 1);

	private int delta_x;
	private int delta_y;

	private Direction(int delta_x, int delta_y) {
		this.delta_x = delta_x;
		this.delta_y = delta_y;
	}

	public static Point scanDirection(Direction direction) {
		return direction.scanDirection();
	}

	public Point scanDirection() {
		return new Point(delta_x, delta_y);
	}
}
