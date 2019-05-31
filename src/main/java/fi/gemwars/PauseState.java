package fi.gemwars;

import fi.gemwars.gameobjects.Player;
import fi.gemwars.gameobjects.map.Map;
import fi.gemwars.ui.components.menu.BasicMenuItem;
import fi.gemwars.ui.components.menu.Menu;

import java.awt.Point;
import java.util.List;

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

/**
 * PauseState is used when a game play is paused. When the game play is paused, actually we are
 * changing the current game state to the PauseState. 
 */
public class PauseState extends BasicGameState {

	private final String PAUSE_TEXT = "Game Paused";
	
	/**
	 * Unique game state ID
	 */
	private int stateID = -1;
	
	/**
	 * PauseState menu component
	 * Items:
	 * -Resume
	 * -Restart
	 * -Quit
	 */
	private Menu pauseStateMenu;
	
	/**
	 * Menu position
	 */
	private Point menuPosition;
	
	/**
	 * Game screenshot is used as a background image
	 */
	private Image background;
	

	/**
	 * Temponary storage Font / Line color before to this game state is entered.
	 */
	private Color orginalColor;
	
	private UnicodeFont font;
	
	private int textWidth;
	
	private final static int RESUME_BTN_INDEX = 0;
	private final static int RESTART_BTN_INDEX = 1;
	private final static int QUIT_BTN_INDEX = 2;
	
	private final static Color MENU_BACKGROUND_COLOR = new Color(0f, 0f, 0f, 0.28f);
	private final static Color BACKGROUND_FILTER_COLOR = new Color(1.0f, 1.0f, 1.0f, 0.8f);	
	
	private Map currentMap;
	
	public PauseState( int stateID ) {
		this.stateID = stateID;	
	}
	
	private void loadFont() throws SlickException {
		java.awt.Font awtFont = new java.awt.Font("Arial", java.awt.Font.BOLD, 25);
        font = new UnicodeFont(awtFont);
        font.addAsciiGlyphs();
        java.awt.Color topColor = new java.awt.Color(1f,0f,0f,1f);
        java.awt.Color bottomColor = new java.awt.Color(1f,0f,0f,1f);
        font.getEffects().add(new GradientEffect(topColor, bottomColor, 1f));
        font.loadGlyphs();
	}
	
	private void initPauseTextPosition( GameContainer container ) {
		textWidth = font.getWidth(PAUSE_TEXT);
		int textHeight = font.getHeight(PAUSE_TEXT);
		
		int x = (int)( container.getWidth() / 2 - textWidth / 2);
		int y = (int)( container.getHeight() / 2 - textHeight / 2)- 50;
		
        menuPosition = new Point(x, y);
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		orginalColor = container.getGraphics().getColor();
		container.getGraphics().setColor(Color.white);
		
		GameplayState gameState = (GameplayState)game.getState(Gemwars.GAMEPLAYSTATE);
		currentMap = gameState.getCurrentMap();
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game)	throws SlickException {
		super.leave(container, game);		
		container.getGraphics().setColor(orginalColor);
		container.getInput().clearKeyPressedRecord();
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game)	throws SlickException {
		loadFont();
		initPauseTextPosition(container);		
		initPauseMenu();	
	}

	public void initPauseMenu() throws SlickException {
		pauseStateMenu = new Menu( new java.awt.Color( 0xffffff ), new java.awt.Color( 0xffffff ) );
		
		pauseStateMenu.add( "resume", new BasicMenuItem(menuPosition.x-20, menuPosition.y + 20, "Resume"));
		pauseStateMenu.add( "restart", new BasicMenuItem(menuPosition.x-20, menuPosition.y + 40, "Restart level"));
		pauseStateMenu.add( "quit", new BasicMenuItem(menuPosition.x-20, menuPosition.y + 60, "Quit"));			
	}

	
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		
		int menuHeight = 110;
		
		background.draw(0, 0, BACKGROUND_FILTER_COLOR);
		
		g.setColor(MENU_BACKGROUND_COLOR);		
		g.fillRect(menuPosition.x - 30, menuPosition.y-10, textWidth + 60, menuHeight );
		
		font.drawString(menuPosition.x, menuPosition.y, PAUSE_TEXT);
		pauseStateMenu.render(container, g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		Input input = container.getInput();
				
		if( input.isKeyPressed(Input.KEY_ENTER )) {
			int index = pauseStateMenu.getActiveIndex();
			
			switch( index ) {
				case RESUME_BTN_INDEX:
				game.enterState(Gemwars.GAMEPLAYSTATE);
				break;
				case QUIT_BTN_INDEX:
				game.enterState(Gemwars.MAINMENUSTATE);
				break;
				case RESTART_BTN_INDEX:
					
				List<Player> players = currentMap.getPlayers();
				
				for (Player player : players) {
					player.kill();
				}
				
				GameplayState gameState = (GameplayState)game.getState(Gemwars.GAMEPLAYSTATE);
				gameState.reloadMap(container);
				
				game.enterState(Gemwars.GAMEPLAYSTATE);
				
				break;
				default:
				throw new SlickException("We should never be here :|");
			}
		}
		
		pauseStateMenu.update(container, delta);
	}
	
	public void setBackground(Image background) {
		this.background = background;
	}	
	
	@Override
	public int getID() {
		return stateID;
	}
}
