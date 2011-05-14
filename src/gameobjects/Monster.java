package gameobjects;

import gameobjects.map.Map;
import io.ResourceManager;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * Enemies of the player. Stupid ants that move according to 
 * a certain pattern so they can be predicted.
 * 
 * Monsters "hug" the wall on their left. If they encounter a wall, they turn left until they can get out of the situation.
 *
 */
public class Monster extends AEntity {

	private Animation walkingRight;
	private Animation walkingLeft;
	private Animation walkingUp;
	private Animation walkingDown;
	
	private Direction direction = Direction.STATIONARY;
	
	private double distance;
	
	private int positionX;
	private int positionY;
	
	private Map map;
	
	
	public Monster(int x, int y, Map map) {
		walkingRight = ResourceManager.fetchAnimation("MONSTER_RIGHT");
		walkingLeft = ResourceManager.fetchAnimation("MONSTER_LEFT");
		walkingUp = ResourceManager.fetchAnimation("MONSTER_UP");
		walkingDown = ResourceManager.fetchAnimation("MONSTER_DOWN");
		
		positionX = x;
		positionY = y;
		
		this.map = map;
		
		this.speed = 0.08;
	}
	
	@Override
	public void render(GameContainer cont, Graphics grap) throws SlickException {
		//walkingRight.draw(positionX * Item.TILE_WIDTH, positionY * Item.TILE_HEIGHT);
		
		int drawX = positionX * Item.TILE_WIDTH;
		int drawY = positionY * Item.TILE_HEIGHT;
		
		if( direction == Direction.UP ) {
			walkingUp.draw(drawX, (int)(drawY + Item.TILE_HEIGHT - distance) );
		}
		if( direction == Direction.DOWN ) {
			walkingDown.draw(drawX, (int) (drawY - Item.TILE_HEIGHT + distance ));
		}
		if( direction == Direction.LEFT ) {
			walkingLeft.draw((int)(drawX + Item.TILE_WIDTH - distance), drawY);
		}
		if( direction == Direction.RIGHT ) {
			walkingRight.draw((int)(drawX - Item.TILE_WIDTH + distance), drawY);
		}

		// to debug, uncomment:
		//grap.drawString(positionX + "," + positionY + " = " + drawX + "," + drawY + "\n" + direction + " "+distance, drawX, drawY);
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		if( direction != Direction.STATIONARY 
			&& distance <= Item.TILE_HEIGHT) {
			
			distance += speed * delta;
		}
		else {
			distance = 0;
			changeDirection();
		}
		if(direction == Direction.STATIONARY) // corrects a bug on start
			direction = Direction.RIGHT;


		
		/*
		walkingRight.update(delta);
		walkingLeft.update(delta);
		walkingUp.update(delta);
		walkingDown.update(delta);
		*/
	}
	
	private void changeDirection() throws SlickException {
		if( direction == Direction.RIGHT ) {
			if (!map.isMonsterColliding(positionX, positionY - 1)) { // can it turn left
				positionY--;
				direction = Direction.UP;
				return;
			}
			else if (!map.isMonsterColliding(positionX + 1, positionY)) // can it move forwards
				positionX++;
			else
				direction = Direction.UP; // giving up, testing another direction
		}
		
		if( direction == Direction.UP ) {
			if (!map.isMonsterColliding(positionX - 1, positionY)) { // can it turn left
				positionX--;
				direction = Direction.LEFT;
				return;
			}
			else if (!map.isMonsterColliding(positionX, positionY - 1)) // can it move forwards
				positionY--;
			else
				direction = Direction.LEFT; // giving up, testing another direction
		}
		
		if( direction == Direction.LEFT ) {
			if (!map.isMonsterColliding(positionX, positionY + 1)) { // can it turn left
				positionY++;
				direction = Direction.DOWN;
				return;
			}
			else if (!map.isMonsterColliding(positionX - 1 , positionY)) // can it move forwards
				positionX--;
			else
				direction = Direction.DOWN; // giving up, testing another direction
		}
		
		if( direction == Direction.DOWN ) {
			if (!map.isMonsterColliding(positionX + 1, positionY)) { // can it turn left
				positionX++;
				direction = Direction.RIGHT;
				return;
			}
			else if (!map.isMonsterColliding(positionX, positionY + 1)) // can it move forwards
				positionY++;
			else
				direction = Direction.RIGHT; // giving up, testing another direction
		}
	}
}
