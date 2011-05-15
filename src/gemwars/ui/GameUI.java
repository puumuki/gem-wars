package gemwars.ui;

import io.ResourceManager;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.GradientEffect;
import org.newdawn.slick.state.StateBasedGame;

public class GameUI {

	private Animation clock;
	private Animation coin;
	private Animation gem;
	private Animation head;
	
	private UnicodeFont font;
	
	
	public GameUI(GameContainer cont, StateBasedGame state) throws SlickException {
		init(cont, state);
	}
	
	@SuppressWarnings("unchecked")
	public void init(GameContainer cont, StateBasedGame state) throws SlickException {
		clock = ResourceManager.fetchAnimation("UI_CLOCK");
		coin = ResourceManager.fetchAnimation("UI_COIN");
		gem = ResourceManager.fetchAnimation("UI_GEM");
		head = ResourceManager.fetchAnimation("UI_HEAD");
		
		
		java.awt.Font awtFont = new java.awt.Font("Verdana", java.awt.Font.BOLD, 30);
        font = new UnicodeFont(awtFont);
        font.addAsciiGlyphs();       
        java.awt.Color topColor = new java.awt.Color( 0xbbdd00 );
        java.awt.Color bottomColor = new java.awt.Color( 0x99aa00 );
        
        font.getEffects().add(new GradientEffect(topColor, bottomColor, 1f));
        font.loadGlyphs();
        
	}
	
	
	public void render(GameContainer cont, Graphics grap, int time, int points, int gemCount, int gemsNeeded, int lives) throws SlickException {
		grap.setFont(font);
		int margin = 5;
		
		grap.drawAnimation(coin, margin, margin);
		grap.drawString("x "+points, margin + coin.getWidth() + margin * 2, margin + coin.getHeight() / 2 - font.getLineHeight() / 2);
		
		grap.drawAnimation(clock, cont.getWidth() - clock.getWidth() - 5, 5);
		String timeText = time + " x";
		grap.drawString(timeText, cont.getWidth() - (margin + clock.getWidth() + margin * 2) - font.getWidth(timeText), margin + coin.getHeight() / 2 - font.getLineHeight() / 2);
		
		grap.drawAnimation(gem, margin, cont.getHeight() - gem.getWidth() - margin);
		grap.drawString("x " + gemCount + " / " + gemsNeeded, margin + gem.getWidth() + margin * 2, cont.getHeight() - (margin + gem.getHeight() / 2) - font.getLineHeight() / 2);
		
		grap.drawAnimation(head, cont.getWidth() - head.getWidth() - margin, cont.getHeight() - head.getWidth() - margin);
		String livesText = lives + " x";
		grap.drawString(livesText, cont.getWidth() - (margin + head.getWidth() + margin * 2) - font.getWidth(livesText), cont.getHeight() - (margin + head.getHeight() / 2) - font.getLineHeight() / 2);
	}
	
}
