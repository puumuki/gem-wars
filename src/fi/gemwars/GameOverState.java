package fi.gemwars;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.GradientEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;



/**
 * Game over state
 *
 */
public class GameOverState extends BasicGameState {

	int stateID;
	
	private UnicodeFont font;
	
	public GameOverState(int stateID) {
		this.stateID = stateID;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		java.awt.Font awtFont = new java.awt.Font("Verdana", java.awt.Font.BOLD, 30);
        font = new UnicodeFont(awtFont);
        font.addAsciiGlyphs();       
        java.awt.Color topColor = new java.awt.Color( 0xbbdd00 );
        java.awt.Color bottomColor = new java.awt.Color( 0x99aa00 );
        
        font.getEffects().add(new GradientEffect(topColor, bottomColor, 1f));
        font.loadGlyphs();
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		g.setFont(font);
		String text = "Game Over :(\n\nPress enter."; 
		g.drawString(text, gc.getWidth() / 2 - font.getWidth(text) / 2, gc.getHeight() / 2 - font.getHeight(text));
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		Input input = gc.getInput();
		
		if( input.isKeyPressed(Input.KEY_ENTER) || input.isKeyPressed(Input.KEY_ESCAPE) || input.isKeyPressed(Input.KEY_SPACE))
			game.enterState(Gemwars.MAINMENUSTATE, 								
					new FadeOutTransition(), 
					new FadeInTransition());
			
	}
	
	@Override
	public int getID() {
		return stateID;
	}

}
