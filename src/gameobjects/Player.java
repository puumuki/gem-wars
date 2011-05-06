package gameobjects;

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
		this.speed = 0.04;
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
	
		grap.drawString( "" + distance, 200, 200 );
		grap.drawString( direction.name(), 200, 230 );		
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		Input input = cont.getInput();
		
		//If no any key pressed is going to be stationary		
		if(direction == Direction.STATIONARY ) {
			if( input.isKeyDown(Input.KEY_DOWN )) {
				direction = Direction.DOWN;
			}
			
			if( input.isKeyDown(Input.KEY_UP)) {
				direction = Direction.UP;
			}
			
			if( input.isKeyDown(Input.KEY_LEFT)) {
				direction = Direction.LEFT;
			}
			
			if( input.isKeyDown(Input.KEY_RIGHT)) {
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
			walkingDown.addFrame(sub, duration);
			
			sub = image.getSubImage(i, frameHeight*3, farmeWidth, frameHeight);
			walkingUp.addFrame(sub, duration);
			
			sub = image.getSubImage(i, frameHeight*4, farmeWidth, frameHeight);
			pushRight.addFrame(sub, duration);
			
			sub = image.getSubImage(i, frameHeight*5, farmeWidth, frameHeight);
			pushLeft.addFrame(sub, duration);
		}
		
		stationary = walkingDown.getImage(0);
	}
}
