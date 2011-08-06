package gemwars;

import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.GradientEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class PauseState extends BasicGameState {

	private Image background;
	
	private int stateID;
	
	private Color orginalColor;
	
	private UnicodeFont font;
	
	private final String PAUSE_TEXT = "Paused, press P or Pause to continue";
	
	private Point textPosition;
	
	public PauseState( int stateID ) {
		this.stateID = stateID;
	}
	
	private void loadFont() throws SlickException {
		java.awt.Font awtFont = new java.awt.Font("Arial", java.awt.Font.PLAIN, 25);
        font = new UnicodeFont(awtFont);
        font.addAsciiGlyphs();
        java.awt.Color topColor = new java.awt.Color(1f,0f,0f,1f);
        java.awt.Color bottomColor = new java.awt.Color(1f,0f,0f,1f);
        font.getEffects().add(new GradientEffect(topColor, bottomColor, 1f));
        font.loadGlyphs();
	}
	
	private void initPauseTextPosition( GameContainer container ) {
		int textWidth = font.getWidth(PAUSE_TEXT);
		int textHeight = font.getHeight(PAUSE_TEXT);
		
		int x = (int)( container.getWidth() / 2 - textWidth / 2);
		int y = (int)( container.getHeight() / 2 - textHeight / 2);
		
        textPosition = new Point(x, y);
	}
	
	
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		orginalColor = container.getGraphics().getColor();
		container.getGraphics().setColor(Color.white);
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game)	throws SlickException {
		super.leave(container, game);		
		container.getGraphics().setColor(orginalColor);
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game)	throws SlickException {
		loadFont();
		initPauseTextPosition(container);
	}

	
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		background.draw(0, 0, new Color(1, 1, 1, 0.75f));
		font.drawString(textPosition.x, textPosition.y, PAUSE_TEXT);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Input input = container.getInput();
		
		if( input.isKeyPressed(Input.KEY_P) || input.isKeyPressed(Input.KEY_PAUSE)) {		
			game.enterState(Gemwars.GAMEPLAYSTATE);
		}
	}

	public void setBackground(Image background) {
		this.background = background;
	}	
	
	@Override
	public int getID() {
		return stateID;
	}

}
