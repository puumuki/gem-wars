package fi.gemwars.gameobjects;


import java.awt.Point;

import fi.gemwars.gameobjects.map.Map;
import fi.gemwars.io.Options;
import fi.gemwars.io.ResourceManager;
import fi.gemwars.utils.TimeCounter;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;


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
	public int lives = INITIAL_LIVE_COUNT;
	
	public static final int INITIAL_LIVE_COUNT = 3;
	
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
	private Image tempImage;
	
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
	private Player() {
		playerNumber = __staticPlayerNumber++;		
		postProcessAnimations();
		initSounds();
		this.speed = 0.20;
	}
	
	public Player( int positionX, int positionY ) throws SlickException {		
		this();		
		initPosition(positionX, positionY);
	}
	
	public Player(int posX, int posY, Map map) throws SlickException {
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
				grap.drawAnimation(walkingUp, drawX, Math.round(drawY - distance) );
			}
			if( direction == Direction.DOWN ) {
				grap.drawAnimation(walkingDown, drawX, Math.round(drawY + distance ));
			}
			if( direction == Direction.LEFT ) {
				if (pushingStone)
					grap.drawAnimation(pushLeft, Math.round(drawX - distance), drawY);
				else
					grap.drawAnimation(walkingLeft, Math.round(drawX - distance), drawY);
			}
			if( direction == Direction.RIGHT ) {
				if (pushingStone)
					grap.drawAnimation(pushRight, Math.round(drawX + distance), drawY);
				else
					grap.drawAnimation(walkingRight, Math.round(drawX + distance), drawY);			
			}
			if( direction == Direction.STATIONARY ) {
				grap.drawImage(tempImage, drawX, drawY);	
			}
		}
		
		// to debug, uncomment
		//grap.drawString(positionX + "," + positionY + " = " + drawX + "," + drawY + "\n" + direction + " "+distance, drawX, drawY);
		//grap.drawString(positionX + "," + positionY + " = " + drawX + "," + drawY + "\n" + direction + " "+distance+"\n"+pushTimer.timeElapsedInMilliseconds(), drawX, drawY);
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		
		Input input = cont.getInput();
		if (dead == false && map.getGoalAnimation() == false)
		{

			collectDiamonds();
			dig();
			
			// Press K to kill the player
			if (input.isKeyDown(Input.KEY_K)) {
				kill();
			}
			
			
			
			if( direction != Direction.STATIONARY 
				&& distance <= Item.TILE_HEIGHT ) {
				if(pushTimer.isActive()) {
					if(pushTimer.timeElapsedInMilliseconds() > 500) {
						pushTimer.stop();
						pushTimer.reset();
						if (direction == Direction.LEFT)
							map.pushBoulder(positionX - 1, positionY, direction);
						if (direction == Direction.RIGHT)
							map.pushBoulder(positionX + 1, positionY, direction);
					}
				}
				else
					distance += speed * delta;
			} else {
				if( direction == Direction.LEFT ) {
					positionX--;
					tempImage = walkingLeft.getCurrentFrame();
				}
				if( direction == Direction.RIGHT ) {
					positionX++;
					tempImage = walkingRight.getCurrentFrame();
				}
				if( direction == Direction.UP ) {
					positionY--;
					tempImage = walkingUp.getCurrentFrame();
				}
				if( direction == Direction.DOWN ) {
					positionY++;
					tempImage = walkingDown.getCurrentFrame();
				}
				
				if (distance != 0) {
					pushingStone = false;
					distance = distance - Item.TILE_HEIGHT;
				}
				
				// direction = Direction.STATIONARY;

				if( input.isKeyDown(Input.KEY_DOWN ) ) {
					if (!map.isColliding(positionX, positionY + 1)) {
						direction = Direction.DOWN;
						distance += speed * delta; 
					}
					else {
						distance = 0;
						direction = Direction.STATIONARY;
					}
				}
				
				else if( input.isKeyDown(Input.KEY_UP)) {
					if (!map.isColliding(positionX, positionY - 1)) {
						direction = Direction.UP;
						distance += speed * delta;
					}
					else {
						distance = 0;
						direction = Direction.STATIONARY;
					}
				}
				
				else if( input.isKeyDown(Input.KEY_LEFT)) {
					if (!map.isColliding(positionX - 1 , positionY)) {
						direction = Direction.LEFT;
						distance += speed * delta;
					}
					else if (map.canPush(positionX - 1, positionY, Direction.LEFT)) {
						if(pushTimer.isActive() == false) {
							pushingStone = true;
							direction = Direction.LEFT;
							pushTimer.start();
						}
						
					}
					else {
						distance = 0;
						direction = Direction.STATIONARY;
					}
				}
				
				else if( input.isKeyDown(Input.KEY_RIGHT)) {
					if (!map.isColliding(positionX + 1, positionY)) {
						direction = Direction.RIGHT;
						distance += speed * delta;
					}
					else if (map.canPush(positionX + 1, positionY, Direction.RIGHT)) {
						if(pushTimer.isActive() == false) {
							pushingStone = true;
							direction = Direction.RIGHT;
							pushTimer.start();
						}
					}
					else {
						distance = 0;
						direction = Direction.STATIONARY;
					}
				}
				
				else {
					distance = 0;
					direction = Direction.STATIONARY;
				}
				
				
				if (direction == Direction.STATIONARY)
				{
					pushingStone = false;
					distance = 0;
					tempImage = stationary;
				}
				
				
			}
			if ((!input.isKeyDown(Input.KEY_LEFT) && pushTimer.isActive() && direction == Direction.LEFT)
					|| (!input.isKeyDown(Input.KEY_RIGHT) && pushTimer.isActive() && direction == Direction.RIGHT)) {
				pushTimer.stop();
				pushTimer.reset();
				pushingStone = false;
				direction = Direction.STATIONARY;
				distance = 0;
			}
			
		}
		else {
			// if the player is dead or in the goal, do nothing
		}
	}
	
	/**
	 * dig a sand block
	 */
	private void dig() {
		Point point = Direction.scanDirection(direction);		
		map.destroySand(positionX + point.x, positionY + point.y);		
	}
	

	public void addScore( int amount ) {
		
		int oldScore = score;
						
		this.score += amount;
		
		if( this.score > nextExtraLifeStep( oldScore ) ) {
			lives++;
		}
	}
	
	public static int nextExtraLifeStep( int scores ) {
		return 101;
	}
	
	public void setScore( int score ) {
		this.score = score;
	}
	
	/**
	 * collect a gem
	 */
	private void collectDiamonds() {						
		Point point = Direction.scanDirection(direction);
		
		boolean isCollected = map.isTileContainingGem(positionX + point.x, positionY + point.y);
		if( isCollected
				&& map.getObjectLayer().getTile(positionX + point.x, positionY + point.y).direction == Direction.STATIONARY) {
			addScore( ((Gem)map.getObjectLayer().getTile(positionX + point.x, positionY + point.y)).getValue());
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
		tempImage = stationary;
		
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
		if (dead == false)
		{
			lives--;
			dead = true;
		}
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
