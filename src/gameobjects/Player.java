package gameobjects;


import gameobjects.map.Map;
import io.ResourceManager;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * @author Teemuki
 * 
 * The Player class holds an implementation of human controller entity.
 */
public class Player extends AEntity {
		
	private static int __staticPlayerNumber = 0;
	
	/**
	 * Player number starting from index 1
	 */
	public int playerNumber;
	
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
		
	boolean areWeMoving = false;
	
	private double distance;

	private Image stationary;
	private Animation walkingRight;
	private Animation walkingLeft;
	private Animation walkingUp;
	private Animation walkingDown;
	private Animation pushLeft;
	private Animation pushRight;
	
	private Map map;
		
	/**
	 * Flag that indicates is the player pushing something.
	 */	
	public boolean pushingStone = false;
	
	public Player() {
		playerNumber = __staticPlayerNumber++;		
		postProcessAnimations();
	}
	
	public Player( int positionX, int positionY ) {
		
		this();
		
		this.positionX = positionX;
		this.positionY = positionY;
		
		//Tweak this if you want to player to go faster
		this.speed = 0.08;
	}
	
	public Player(int posX, int posY, Map map) {
		this(posX, posY);
		this.map = map;
	}
	
	@Override
	public void render(GameContainer cont, Graphics grap) throws SlickException {
		
		int drawX = positionX * Item.TILE_WIDTH;
		int drawY = positionY * Item.TILE_HEIGHT;
		
		if( direction == Direction.UP ) {
			grap.drawAnimation(walkingUp, drawX, (int)(drawY - distance) );
		}
		if( direction == Direction.DOWN ) {
			grap.drawAnimation(walkingDown, drawX, (int) (drawY + distance ));
		}
		if( direction == Direction.LEFT ) {
			grap.drawAnimation(walkingLeft, (int)(drawX - distance), drawY);
		}
		if( direction == Direction.RIGHT ) {
			grap.drawAnimation(walkingRight, (int)(drawX + distance), drawY);			
		}
		if( direction == Direction.STATIONARY ) {
			grap.drawImage(stationary, drawX, drawY);	
		}		
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		Input input = cont.getInput();
		
		//If no any key pressed is going to be stationary		
		if(direction == Direction.STATIONARY ) {
			if( input.isKeyDown(Input.KEY_DOWN )) {
				if (!map.isColliding(positionX, positionY + 1))
					direction = Direction.DOWN;
			}
			
			if( input.isKeyDown(Input.KEY_UP)) {
				if (!map.isColliding(positionX, positionY - 1))
					direction = Direction.UP;
			}
			
			if( input.isKeyDown(Input.KEY_LEFT)) {
				if (!map.isColliding(positionX - 1 , positionY))
					direction = Direction.LEFT;
			}
			
			if( input.isKeyDown(Input.KEY_RIGHT)) {
				if (!map.isColliding(positionX + 1, positionY))
					direction = Direction.RIGHT;
			}
			
		}
		
		if( direction != Direction.STATIONARY 
			&& distance <= Item.TILE_HEIGHT ) {
			
			distance += speed * delta;
		} else {
			if( direction == Direction.LEFT ) {
				positionX--;
			}
			if( direction == Direction.RIGHT ) {
				positionX++;
			}
			if( direction == Direction.UP ) {
				positionY--;
			}
			if( direction == Direction.DOWN ) {
				positionY++;
			}
			
			distance = 0;						
			direction = Direction.STATIONARY;
		}
	}
	
	private void postProcessAnimations() {
		Image image = ResourceManager.getInstance().getImage("UKOT");
		
/*		int duration = 25;
		
		int frameHeight = 56, farmeWidth = 56;
	*/	
		walkingRight = ResourceManager.getInstance().getAnimation("PLAYER_RIGHT");
		walkingLeft = ResourceManager.getInstance().getAnimation("PLAYER_LEFT");
		walkingUp = ResourceManager.getInstance().getAnimation("PLAYER_UP");
		walkingDown = ResourceManager.getInstance().getAnimation("PLAYER_DOWN");
		pushRight = ResourceManager.getInstance().getAnimation("PLAYER_PUSH_RIGHT");
		pushLeft = ResourceManager.getInstance().getAnimation("PLAYER_PUSH_LEFT");
		/*
		for (int i = 0; i < image.getWidth(); i += farmeWidth ) {
			Image sub = image.getSubImage(i, 0, farmeWidth, frameHeight);
			walkingRight.addFrame(sub, duration);
			
			sub = image.getSubImage(i, frameHeight, farmeWidth, frameHeight);
			walkingLeft.addFrame(sub, duration);
			
			sub = image.getSubImage(i, frameHeight*2, farmeWidth, frameHeight);
			walkingDown.addFrame(sub, duration);
			
			sub = image.getSubImage(i, frameHeight*3, farmeWidth, frameHeight);
			walkingUp.addFrame(sub, duration);
			
			sub = image.getSubImage(i, frameHeight*4, farmeWidth, frameHeight);
			pushRight.addFrame(sub, duration);
			
			sub = image.getSubImage(i, frameHeight*5, farmeWidth, frameHeight);
			pushLeft.addFrame(sub, duration);
		}
		*/
		stationary = walkingDown.getImage(0);
	}
	
	public double getDistance() {
		return distance;
	}
}
