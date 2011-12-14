package gemwars;

import gemwars.ui.components.menu.ImageMenuItem;
import gemwars.ui.components.menu.Menu;

import io.Options;
import io.ResourceManager;


import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;


import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Main menu state for the main menu
 */
public class MainMenuState extends BasicGameState {

	private int stateID = -1;
	
	private Image background = null;
	
	/**
	 * Title text "GemWars"
	 */
	private Image title;
	
	/**
	 * The classic never ending Gemwars loop
	 */
	private Music menumusic = null;
	
	private HashMap<String, Animation> menuanimations = new HashMap<String, Animation>();
				
	/**
	 * AboutText contains story on the Gemwars
	 */
	private AboutText aboutTextEntity;
	
	/**
	 * Menu containing ( new game, options, about, quit )
	 */
	private Menu mainmenu;
	
	/**
	 * Animation bottom right
	 */
	private Animation diamandAni;
	
	/**
	 * If this flag is true map the game state is changed to GamePlayStat, 
	 * it triggeded by map selection menu listener.
	 */
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
        
		aboutTextEntity = new AboutText(30, 140);
		
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
		
		background.draw(0,0 );
		title.draw(  title.getWidth() / 4, 50 );
		mainmenu.render(gc, g);
		diamandAni.draw( gc.getWidth() - diamandAni.getWidth() - 10,
						 gc.getHeight() - diamandAni.getHeight() - 10, 
						 DiamondAnimation.FILTER_COLOR);
		
		aboutTextEntity.render(gc, g);
		
		maplist.render(gc, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		
		diamandAni.update(delta);
		
		if( aboutTextEntity.hide == false ) {
			aboutTextEntity.update(gc, delta);			
		} else {
			mainmenu.hide = false;
		}
		
		//Only one menu can be updated at time. 
		//Keyboard inputs can be checked reliable only one time with calling method Input.isKeyPressed();
		if( maplist.hide == false ) {
			maplist.update(gc, delta);
		}		
		else {
			mainmenu.update(gc, delta);						
			handleMainMenuInputs(gc, game);
		}		
		
		if( changeMap ) {			
			GameplayState playState = (GameplayState)game.getState(Gemwars.GAMEPLAYSTATE);
			playState.changeMap( maplist.getChosenMapFile() );
			changeMap = false;			
			game.enterState(Gemwars.GAMEPLAYSTATE);
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
				aboutTextEntity.hide = false;
				mainmenu.hide = true;			
			}

			if( mainmenu.isActiveIndex("quit") ) {
				gc.exit();
			}
		}
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
