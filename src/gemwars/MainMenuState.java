package gemwars;

import gemwars.ui.components.menu.FileMenuItem;
import gemwars.ui.components.menu.ImageMenuItem;
import gemwars.ui.components.menu.Menu;

import io.MapLoader;
import io.Options;
import io.ResourceManager;

import java.awt.Color;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;


import java.util.HashMap;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.OutlineEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.BlobbyTransition;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.Log;

/**
 * Main menu state for the main menu
 *
 */
public class MainMenuState extends BasicGameState {

	int stateID = -1;
	
	private Image background = null;
	private Image title;
	
	private Music menumusic = null;
		
	private HashMap<String, Animation> menuanimations = new HashMap<String, Animation>();
			
	private UnicodeFont mapSelectionFont;
	
	private Menu mainmenu;
	
	private Animation diamandAni;
	
	private boolean changeMap;
	
	/**
	 * Component that shows all selectable single player maps
	 */
	private MapList maplist;
	
	public MainMenuState(int stateID) {
		this.stateID = stateID;
	}
	
	/**
	 * Initialization of the main menu (loading resources etc)
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		
		try {
			File file = new File("src/resources/resources.xml"); 
			
			if( file.exists() == false ) {
				file = new File("resources/resources.xml");
			}						
			
			if( file.exists() == false ) {
				ClassLoader classLoader = getClass().getClassLoader();
				file = new File(classLoader.getResource("/resources/resources.xml").getFile());
			}								
			
			FileInputStream fileStream = new FileInputStream(file);		
			ResourceManager.getInstance().loadResources(fileStream, false); 
		} catch (Exception e) {
			e.printStackTrace();
		}		                 
        

        mapSelectionFont = initMapChoosingFont();
        
        menumusic = ResourceManager.fetchMusic("MENU_MUSIC");        
        background = ResourceManager.fetchImage("MENU_BG");
        title = ResourceManager.fetchImage("MENU_TITLE");
        
        mainmenu = new Menu();        
        
        mainmenu.positionX = 50;
        mainmenu.positionY = 50;        
        
        int verticalOffset = 75;
        Image menuImage = ResourceManager.fetchImage("MENU_NEWGAME");
        mainmenu.add( "newgame", new ImageMenuItem( new Point(mainmenu.positionX, 
        										   mainmenu.positionY + verticalOffset ) ,
        										   menuImage));  

        
        menuImage = ResourceManager.fetchImage("MENU_OPTIONS");
        mainmenu.add( "options", new ImageMenuItem( new Point(mainmenu.positionX, 
        										   mainmenu.positionY + verticalOffset * 2),
        										   menuImage));
        
        menuImage = ResourceManager.fetchImage("MENU_ABOUT");
        mainmenu.add( "about", new ImageMenuItem( new Point(mainmenu.positionX, 
        										   mainmenu.positionY + verticalOffset *3),
        										   menuImage));
        
        menuImage = ResourceManager.fetchImage("MENU_QUIT");
        mainmenu.add( "quit", new ImageMenuItem( new Point(mainmenu.positionX, 
        										   mainmenu.positionY  + verticalOffset *4),
        										   menuImage));   
        
        

        diamandAni = new DiamondAnimation();         
        maplist = new MapList( 150, 20);
        maplist.addMapChosenEventListener( new MapSelectionListener() );
	}
	
	/**
	 * What do we do when we enter this state
	 */
	@Override
	public void enter(GameContainer container, StateBasedGame game)	throws SlickException {
		super.enter(container, game);
		
		menumusic.setVolume(0);
		menumusic.fade(1000, Options.getInstance().getMusicVolume(), false);
		menumusic.loop((float)1.0, Options.getInstance().getMusicVolume());
	}
	
	/**
	 * What do we do when we leave this state
	 */
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		super.leave(container, game);		
		container.getInput().clearKeyPressedRecord();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		
		g.setColor(org.newdawn.slick.Color.white);
		g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
		
		background.draw(0,0);
		title.draw(  title.getWidth() / 4, 50 );
		mainmenu.render(gc, g);
		diamandAni.draw( gc.getWidth() - diamandAni.getWidth() - 10,
						 gc.getHeight() - diamandAni.getHeight() - 10);
		
		maplist.render(gc, g);
	}

	public UnicodeFont initMapChoosingFont() throws SlickException {
		java.awt.Font awtFont = new java.awt.Font("Verdana", java.awt.Font.PLAIN, 13);
        UnicodeFont font = new UnicodeFont(awtFont);
        font.addAsciiGlyphs();
        font.getEffects().add(new ColorEffect(new Color(255,255,255)));
        font.getEffects().add(new OutlineEffect(1, new Color(255,255,255,150)));
        font.loadGlyphs();
		return font;
	}


	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		
		diamandAni.update(delta);
		
		//Only one menu can be updated at time. 
		//Keyboard inputs can be checked reliable only one time with calling method Input.isKeyPressed();
		if( maplist.hide == false ) {
			maplist.update(gc, delta);
		}
		
		else {
			mainmenu.update(gc, delta);						
			handleMainMenuInputs(gc, game);
		}
	}

	public void handleMainMenuInputs(GameContainer gc, StateBasedGame game) {
		Input input = gc.getInput();			
		
		if(input.isKeyPressed(Input.KEY_ENTER)) {
			if( mainmenu.isActiveIndex("newgame") ) {
				maplist.hide = false;
			}
			
			if( mainmenu.isActiveIndex("options") ) {
				game.enterState( Gemwars.CONFIGURATION_MENU_STATE);
			}
			
			if( mainmenu.isActiveIndex("about") ) {
				
			}

			if( mainmenu.isActiveIndex("quit") ) {
				gc.exit();
			}
		}
		
		if( changeMap ) {			
			GameplayState playState = (GameplayState)game.getState(Gemwars.GAMEPLAYSTATE);
			playState.changeMap( maplist.getChosenMapFile() );
			changeMap = false;			
			game.enterState(Gemwars.GAMEPLAYSTATE);
		}
	}
		
	@SuppressWarnings("unchecked")
	public void drawCredits(GameContainer gc, Graphics g) throws SlickException {
		
		String text = "GemWars is a game with a long development\n" +
				"history comparable to Duke Nukem Forever.\n\n" +
				"This Java version is the work of\n" +
				"Miika Hämynen and Teemu Puukko.\n\n" +
				"The following people have had a part in\nGemWars' development in the past:\n" +
				"Jarkko Laine, Lauri Laine, Joona Nuutinen,\nMatti Manninen, Skyler York, Antti Kanninen\n\n" +
				"We thank them for the pioneering work.";
		
		mapSelectionFont.drawString(245, 170, text);
	}

	@Override
	public int getID() {
		return stateID;
	}
	
	/**
	 * MapSelectionListener listens the MapList-menu component it is performed
	 * when a map is chosen from the list.
	 */
	class MapSelectionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			changeMap = true;
		}		
	}
	
}
