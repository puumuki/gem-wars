package fi.gemwars.gameobjects;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import fi.gemwars.gameobjects.map.ItemType;
import fi.gemwars.gameobjects.map.Map;
import fi.gemwars.io.ResourceManager;
import fi.gemwars.utils.GemwarsUtils;

/**
 * A gem that the player can collect. Gems can kill the player if they fall on
 * him.
 *
 */
public class Gem extends PhysicsObject implements IDynamic {

	private Image gemImage;

	private Animation gemDown;
	private Animation gemLeft;
	private Animation gemRight;

	/**
	 * Creates a new gem.
	 * 
	 * @param gemtype type (or colour) of the gem
	 * @param map     link to the map we are on
	 * @throws SlickException if something goes wrong
	 */
	public Gem(ItemType gemtype, Map map) throws SlickException {

		super(gemtype, map);

		if (gemtype == ItemType.BLUE_GEM) {
			gemDown = ResourceManager.fetchAnimation("GEM_BLUE_DOWN");
			gemRight = ResourceManager.fetchAnimation("GEM_BLUE_RIGHT");
			gemLeft = GemwarsUtils.reverseAnimation(gemRight);
		} else if (gemtype == ItemType.GREEN_GEM) {
			gemDown = ResourceManager.fetchAnimation("GEM_GREEN_DOWN");
			gemRight = ResourceManager.fetchAnimation("GEM_GREEN_RIGHT");
			gemLeft = GemwarsUtils.reverseAnimation(gemRight);
		} else if (gemtype == ItemType.RED_GEM) {
			gemDown = ResourceManager.fetchAnimation("GEM_RED_DOWN");
			gemRight = ResourceManager.fetchAnimation("GEM_RED_RIGHT");
			gemLeft = GemwarsUtils.reverseAnimation(gemRight);
		} else
			throw new SlickException("Incorrect gem type: " + gemtype.toString());

		gemImage = gemDown.getImage(0);

		speed = 0.20;
	}

	@Override
	public boolean isPushable() {
		return false;
	}

	@Override
	public boolean isPhysicsAffected() {
		return true;
	}

	@Override
	public void render(GameContainer cont, Graphics grap) throws SlickException {
		int drawX = positionX * Item.TILE_WIDTH;
		int drawY = positionY * Item.TILE_HEIGHT;

		if (direction == Direction.STATIONARY) {
			gemDown.draw(drawX, drawY);
		}
		if (direction == Direction.DOWN) {
			gemDown.draw(drawX, (int) (drawY - Item.TILE_HEIGHT + distance));
		}
		if (direction == Direction.LEFT) {
			gemLeft.draw((int) (drawX + Item.TILE_WIDTH - distance), drawY);
		}
		if (direction == Direction.RIGHT) {
			gemRight.draw((int) (drawX - Item.TILE_WIDTH + distance), drawY);
		}

		// to debug, uncomment:
		// grap.drawString(positionX + "," + positionY + " = " + drawX + "," + drawY +
		// "\n" + direction + " "+distance, drawX, drawY);
		// grap.drawString(direction + "\n" + map.getGroundLayer().getTile(positionX,
		// positionY + 1).itemType.toString(), drawX, drawY);

	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		super.update(cont, delta);
	}

	/**
	 * Value of the gem. Depends on the colour.
	 * 
	 * @return value according to a specific algorithm ;)
	 */
	public int getValue() {
		return this.itemType.ordinal() * 10;
	}

}
