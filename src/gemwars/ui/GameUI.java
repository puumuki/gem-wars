package gemwars.ui;

import io.ResourceManager;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.GradientEffect;
import org.newdawn.slick.state.StateBasedGame;

/**
 * This class manages the game's user interface.
 * It draws some nice animations and information on the screen while the game is running.
 */
public class GameUI {

	private Animation clock;
	private Animation coin;
	private Animation gem;
	private Animation head;
	
	private UnicodeFont font;
	
	
	public GameUI(GameContainer cont, StateBasedGame state) throws SlickException {
		init(cont, state);
	}
	
	/**
	 * initialise the game ui graphics 
	 * @param cont container
	 * @param state what state are we in
	 * @throws SlickException if something goes wrong
	 */
	public void init(GameContainer cont, StateBasedGame state) throws SlickException {
		clock = ResourceManager.fetchAnimation("UI_CLOCK");
		coin = ResourceManager.fetchAnimation("UI_COIN");
		gem = ResourceManager.fetchAnimation("UI_GEM");
		head = ResourceManager.fetchAnimation("UI_HEAD");
		
		
		java.awt.Font awtFont = new java.awt.Font("Verdana", java.awt.Font.BOLD, 30);
        font = new UnicodeFont(awtFont);
        font.addAsciiGlyphs();
        setFontColors();
        font.loadGlyphs();
        
	}
	
	/**
	 * Render the game ui
	 * @param cont container, where we render
	 * @param grap link to graphics
	 * @param time time left
	 * @param points player points
	 * @param gemCount how many gems the player has
	 * @param gemsNeeded how many gems the player needs
	 * @param lives how many lives the player has
	 * @throws SlickException if something goes wrong
	 */
	public void render(GameContainer cont, Graphics grap, int time, int points, int gemCount, int gemsNeeded, int lives) throws SlickException {
		int margin = 6;
		grap.setFont(font);
		grap.setColor(new Color(0,0,0,(float)0.5));

		// points in top left
		String pointsText = "x "+points;
		grap.fillRoundRect((float)(margin/2.0), (float)(margin/2.0), (float)(coin.getWidth() + font.getWidth(pointsText) + margin * 4), (float)(coin.getHeight() + margin), 4);
		grap.drawAnimation(coin, margin, margin);
		font.drawString(margin + coin.getWidth() + margin * 2, (float)(margin + coin.getHeight() / 2.0 - font.getHeight(pointsText) / 2.0), pointsText);
		
		// time in top right
		String timeText = time + " x";
		float width = clock.getWidth() + font.getWidth(timeText) + margin * 4;
		grap.fillRoundRect((float)(cont.getWidth() - margin/2.0 - width), (float)(margin/2.0), width, (float)(clock.getHeight() + margin), 4);
		grap.drawAnimation(clock, cont.getWidth() - clock.getWidth() - 5, 5);
		font.drawString(cont.getWidth() - (margin + clock.getWidth() + margin * 2) - font.getWidth(timeText), (float)(margin + clock.getHeight() / 2.0 - font.getLineHeight() / 2.0), timeText);
		
		// gem count in bottom left
		String gemText = "x " + gemCount + " / " + gemsNeeded;
		width = head.getWidth() + font.getWidth(gemText) + margin * 4;
		float y = (float)(cont.getHeight() - gem.getHeight() - margin * 1.5);
		grap.fillRoundRect((float)( margin/2.0 ), y, width, (float)(head.getHeight() + margin), 4);
		grap.drawAnimation(gem, margin, cont.getHeight() - gem.getWidth() - margin);
		font.drawString(gem.getWidth() + margin * 3, (float)(cont.getHeight() - (margin + head.getHeight() / 2.0) - font.getHeight(gemText) / 2.0), gemText);
		
		// life count in bottom right
		String livesText = lives + " x";
		width = head.getWidth() + font.getWidth(livesText) + margin * 4;
		y = (float)(cont.getHeight() - head.getHeight() - margin * 1.5);
		grap.fillRoundRect((float)(cont.getWidth() - margin/2.0 - width), y, width, (float)(head.getHeight() + margin), 4);
		grap.drawAnimation(head, cont.getWidth() - head.getWidth() - margin, cont.getHeight() - head.getWidth() - margin);
		font.drawString(cont.getWidth() - (margin + head.getWidth() + margin * 2) - font.getWidth(livesText), (float)(cont.getHeight() - (margin + head.getHeight() / 2.0) - font.getHeight(livesText) / 2.0), livesText);
	}

	
	
	
	@SuppressWarnings("unchecked")
	private void setFontColors() {

        java.awt.Color topColor = new java.awt.Color( 0xf3ed11 );
        java.awt.Color bottomColor = new java.awt.Color( 0xd3cc0c );
        font.getEffects().clear();
        font.getEffects().add(new GradientEffect(topColor, bottomColor, 1f));
	}
}
