package gameobjects;


import java.awt.Point;

import gameobjects.map.Map;
import io.Options;
import io.ResourceManager;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import utils.TimeCounter;

/**
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
	public int lives = 3;
	
	/**
	 * Player score from the collected gems.
	 */
	public int score;
	
	/**
	 * How many gems the player has collected.
	 */
	public int collectedGemCount;
		
	boolean areWeMoving = false;
	

	private Sound gemCollectedSound;
	
	private Image stationary;
	/**
	 * This image is used to prevent stuttering while walking
	 */
	private Image stationaryTemp;
	private Animation walkingRight;
	private Animation walkingLeft;
	private Animation walkingUp;
	private Animation walkingDown;
	private Animation pushLeft;
	private Animation pushRight;
	private Image deadImage;
	
	private Map map;
	
	private boolean dead = false;
		
	/**
	 * Flag that indicates is the player pushing something.
	 */	
	public boolean pushingStone = false;
	
	private TimeCounter pushTimer = new TimeCounter();
	
	/**
	 * Initialises the player. This should not be called directly! 
	 */
	public Player() {
		playerNumber = __staticPlayerNumber++;		
		postProcessAnimations();
		initSounds();
		this.speed = 0.1;
	}
	
	public Player( int positionX, int positionY ) {
		
		this();
		
		this.positionX = positionX;
		this.positionY = positionY;
		
	}
	
	public Player(int posX, int posY, Map map) {
		this(posX, posY);
		this.map = map;
	}
	
	private void initSounds() {
		gemCollectedSound = ResourceManager.fetchSound("GAME_COLLECT");
	}
	
	@Override
	public void render(GameContainer cont, Graphics grap) throws SlickException {
		
		int drawX = positionX * Item.TILE_WIDTH;
		int drawY = positionY * Item.TILE_HEIGHT;
		
		if (isDead() == true) {
			grap.drawImage(deadImage, drawX, drawY);
		}
		else
		{
			if( direction == Direction.UP ) {
				grap.drawAnimation(walkingUp, drawX, (int)(drawY - distance) );
			}
			if( direction == Direction.DOWN ) {
				grap.drawAnimation(walkingDown, drawX, (int) (drawY + distance ));
			}
			if( direction == Direction.LEFT ) {
				if (pushingStone)
					grap.drawAnimation(pushLeft, (int)(drawX - distance), drawY);
				else
					grap.drawAnimation(walkingLeft, (int)(drawX - distance), drawY);
			}
			if( direction == Direction.RIGHT ) {
				if (pushingStone)
					grap.drawAnimation(pushRight, (int)(drawX + distance), drawY);
				else
					grap.drawAnimation(walkingRight, (int)(drawX + distance), drawY);			
			}
			if( direction == Direction.STATIONARY ) {
				grap.drawImage(stationaryTemp, drawX, drawY);	
			}
		}
		
		// to debug, uncomment
		//grap.drawString(positionX + "," + positionY + " = " + drawX + "," + drawY + "\n" + direction + " "+distance, drawX, drawY);
		//grap.drawString(positionX + "," + positionY + " = " + drawX + "," + drawY + "\n" + direction + " "+distance+"\n"+pushTimer.timeElapsedInMilliseconds(), drawX, drawY);
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		
		Input input = cont.getInput();
		if (dead == false)
		{

			collectDiamonds();
			dig();
			
			// Press K to kill the player
			if (input.isKeyDown(Input.KEY_K)) {
				kill();
			}
			
			//If no key is pressed is going to be stationary		
			if(direction == Direction.STATIONARY ) {
				
				pushingStone = false;
				
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
					else if (map.canPush(positionX - 1, positionY, Direction.LEFT)) {
						if(pushTimer.isActive() == false) {
							pushingStone = true;
							direction = Direction.LEFT;
							pushTimer.start();
						}
						
					}
				}
				
				if( input.isKeyDown(Input.KEY_RIGHT)) {
					if (!map.isColliding(positionX + 1, positionY))
						direction = Direction.RIGHT;
					else if (map.canPush(positionX + 1, positionY, Direction.RIGHT)) {
						if(pushTimer.isActive() == false) {
							pushingStone = true;
							direction = Direction.RIGHT;
							pushTimer.start();
						}
					}
				}
				stationaryTemp = stationary;
				
				
			}
			

			
			if ((!input.isKeyDown(Input.KEY_LEFT) && pushTimer.isActive() && direction == Direction.LEFT)
					|| (!input.isKeyDown(Input.KEY_RIGHT) && pushTimer.isActive() && direction == Direction.RIGHT)) {
				pushTimer.stop();
				pushTimer.reset();
				pushingStone = false;
				direction = Direction.STATIONARY;
			}
			
			if( direction != Direction.STATIONARY 
				&& distance <= Item.TILE_HEIGHT ) {
				if(pushTimer.isActive()) {
					if(pushTimer.timeElapsedInMilliseconds() > 500) {
						pushTimer.stop();
						pushTimer.reset();
						if (direction == Direction.LEFT)
							map.moveBoulder(positionX - 1, positionY, direction);
						if (direction == Direction.RIGHT)
							map.moveBoulder(positionX + 1, positionY, direction);
					}
				}
				else
					distance += speed * delta;
			} else {
				if( direction == Direction.LEFT ) {
					positionX--;
					stationaryTemp = walkingLeft.getCurrentFrame();
				}
				if( direction == Direction.RIGHT ) {
					positionX++;
					stationaryTemp = walkingRight.getCurrentFrame();
				}
				if( direction == Direction.UP ) {
					positionY--;
					stationaryTemp = walkingUp.getCurrentFrame();
				}
				if( direction == Direction.DOWN ) {
					positionY++;
					stationaryTemp = walkingDown.getCurrentFrame();
				}
				
				distance = 0;						
				direction = Direction.STATIONARY;

			}
			
		}
		else {
			// if the player is dead, do nothing
		}
	}
	
	/**
	 * dig a sand block
	 */
	private void dig() {
		Point point = Direction.scanDirection(direction);		
		map.destroySand(positionX + point.x, positionY + point.y);		
	}
	

	/**
	 * collect a gem
	 */
	private void collectDiamonds() {						
		Point point = Direction.scanDirection(direction);
		
		boolean isCollected = map.isTileContainingGem(positionX + point.x, positionY + point.y);
		if( isCollected
				&& map.getObjectLayer().getTile(positionX + point.x, positionY + point.y).direction == Direction.STATIONARY) {
			score += ((Gem)map.getObjectLayer().getTile(positionX + point.x, positionY + point.y)).getValue();
			collectedGemCount++;
			gemCollectedSound.play((float)1.0, Options.getInstance().getSoundVolume());
			map.destroyGem(positionX + point.x, positionY + point.y);
		}
		
		
	}
	
	/**
	 * Processes the animations before using the player
	 */
	private void postProcessAnimations() {
		walkingRight = ResourceManager.getInstance().getAnimation("PLAYER_RIGHT");
		walkingLeft = ResourceManager.getInstance().getAnimation("PLAYER_LEFT");
		walkingUp = ResourceManager.getInstance().getAnimation("PLAYER_UP");
		walkingDown = ResourceManager.getInstance().getAnimation("PLAYER_DOWN");
		pushRight = ResourceManager.getInstance().getAnimation("PLAYER_PUSH_RIGHT");
		pushLeft = ResourceManager.getInstance().getAnimation("PLAYER_PUSH_LEFT");

		stationary = walkingDown.getImage(0);
		stationaryTemp = stationary;
		
		deadImage = ResourceManager.fetchImage("ITEM_TEXTURES").getSubImage(0, 0, Item.TILE_WIDTH, Item.TILE_HEIGHT);
	}
	
	/**
	 * Used to move the camera with the player
	 * @return distance traveled when moving
	 */
	public double getDistance() {
		return distance;
	}
	
	/**
	 * Returns the player's score for UI etc.
	 * @return player score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Returns the player's gem count for UI etc.
	 * @return how many gems the player has collected
	 */
	public int getGems() {
		return collectedGemCount;
	}
	
	/**
	 * Returns the player's life count for UI etc.
	 * @return player lives
	 */
	public int getLives() {
		return lives;
	}
	
	/**
	 * Kills the player
	 */
	public void kill() {
		lives--;
		dead = true;
	}

	/**
	 * Sets the player as dead or alive
	 * @param b true, if dead, false, if alive
	 */
	public void setDead(boolean b) {
		dead = b;
	}

	public boolean isDead() {
		return dead;
	}
	
	/**
	 * Makes a copy of the current player (for level changes etc.)
	 * @return a copied player
	 */
	public Player copy() {
		
		Player player = new Player();
		
		player.playerNumber = this.playerNumber;
		player.lives = this.lives;
		//player.distance = this.distance;
		player.positionX = this.positionX;
		player.positionY = this.positionY;
		player.score = this.score;
		player.collectedGemCount = this.collectedGemCount;
		
		return player;
	}
	
	public void setMap(Map map) {
		this.map = map;
	}
}
