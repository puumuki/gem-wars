package gemwars;

import gameobjects.Item;
import gameobjects.map.ItemTypes;

import io.Options;
import io.ResourceManager;

import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.UnicodeFont;
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
	
	Image background = null;
	Image newGameOption = null;
	Image multiplayerOption = null;
	Image optionsOption = null;
	Image exitOption = null;
	
	Sound menusound = null;
	Music menumusic = null;
	Music gamemusic = null;
	
	HashMap<String, Animation> menuanimations = new HashMap<String, Animation>();
	
	UnicodeFont fontti = null;
	
	private static int menuX = 38;
	private static int menuY = 240;
	
	private static int menuAnimX = 43;
	private static int menuAnimY = 40;
	
	float alpha = 0;

	int currentSelection = 1;
	int selected = 0;
	
	private Item item;
	
	public MainMenuState(int stateID) {
		this.stateID = stateID;
	}
	
	/**
	 * Initialisation of the main menu (loading resources etc)
	 */
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		
		try {
			File file = new File("src/resources/resources.xml"); 
			FileInputStream fileStream = new FileInputStream(file);		
			ResourceManager.getInstance().loadResources(fileStream, false); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		background = ResourceManager.getInstance().getImage("MENU_BG");
		
		Image menuOptions = ResourceManager.getInstance().getImage("MENU_OPTIONS");
		
		newGameOption = menuOptions.getSubImage(0, 0, 151, 201);
		multiplayerOption = menuOptions.getSubImage(152, 0, 151, 201);
		optionsOption = menuOptions.getSubImage(304, 0, 151, 201);
		exitOption = menuOptions.getSubImage(456, 0, 151, 201);
		
		menusound = ResourceManager.getInstance().getSound("MENU_SOUND");
		menumusic = ResourceManager.getInstance().getMusic("MENU_MUSIC");
		
		menuanimations.put("single", ResourceManager.getInstance().getAnimation("MENUANI_SP"));
		menuanimations.put("multi", ResourceManager.getInstance().getAnimation("MENUANI_MP"));
		menuanimations.put("options", ResourceManager.getInstance().getAnimation("MENUANI_OPTIONS"));
		menuanimations.put("exit", ResourceManager.getInstance().getAnimation("MENUANI_EXIT"));
		
        Font font = new Font("Arial", Font.BOLD, 20);
        fontti = new UnicodeFont(font);
       
        item = new Item(ItemTypes.DARK_BOULDER);
        item.positionX = 200;
        item.positionY = 30;          	
	}
	
	/**
	 * What do we do when we enter this state
	 */
	@Override
	public void enter(GameContainer container, StateBasedGame game)	throws SlickException {
		super.enter(container, game);
		
		menumusic.loop((float)1.0, Options.getInstance().getMusicVolume());
		
		
	}
	
	/**
	 * What do we do when we leave this state
	 */
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
	
		super.leave(container, game);
		
		menumusic.stop();
	}

	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		background.draw(0,0);
		
		
		switch (currentSelection) {
		case 2:
			menuanimations.get("multi").draw(menuAnimX, menuAnimY);
			multiplayerOption.draw(menuX, menuY);
			break;
		case 3:
			menuanimations.get("options").draw(menuAnimX, menuAnimY);
			optionsOption.draw(menuX, menuY);
			break;
		case 4:
			menuanimations.get("exit").draw(menuAnimX, menuAnimY);
			exitOption.draw(menuX, menuY);
			break;
		default:
			currentSelection = 1;
			menuanimations.get("single").draw(menuAnimX, menuAnimY);
			newGameOption.draw(menuX, menuY);
			break;
		}
		
		item.render(gc, g);
	}

	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		
		Input input = gc.getInput();

		if( input.isKeyPressed(Input.KEY_ENTER)) {
			switch (selected) {
			case 1: // single player
				game.enterState(Gemwars.GAMEPLAYSTATE, 								
								new EmptyTransition(), 
								new BlobbyTransition());
				break;
			case 2: // multiplayer
				if( Desktop.isDesktopSupported() ) {
					Desktop desktop = Desktop.getDesktop();
					
					if( desktop.isSupported(Desktop.Action.BROWSE)) {
				        
						URI uri = null;
				        
				        try {
				            uri = new URI( Options.getInstance().getGemwarsFormUrl() );
				            desktop.browse(uri);
				        }
				        catch(IOException ioe) {
				            Log.error("Can't open open gemwars url, oh pleas borwser manually to url: " + uri.toString() );
				        }
				        catch(URISyntaxException use) {
				        	Log.error("Can't open open gemwars url, oh pleas borwser manually to url: " + uri.toString() );
				        }
					}
				}				
				break;
			case 3: // options
				game.enterState(Gemwars.CONFIGURATION_MENU_STATE, 
								new FadeOutTransition(), 
								new FadeInTransition());
				break;
			case 4: // exit			
				gc.exit();
				break;
			default:
				break;
			}
		}
		
		if (input.isKeyPressed(Input.KEY_ESCAPE))
			gc.exit();

	}
	
	@Override
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_UP) {
			currentSelection--;
			if(currentSelection < 1)
				currentSelection = 4;
			menusound.play((float)1.0, Options.getInstance().getSoundVolume());
		}
		if (key == Input.KEY_DOWN) {
			currentSelection++;
			if(currentSelection > 4)
				currentSelection = 1;
			menusound.play((float)1.0, Options.getInstance().getSoundVolume());
		}
		
		if (key == Input.KEY_ENTER) {
			selected = currentSelection;
			menusound.play((float)1.0, Options.getInstance().getSoundVolume());
		}
	}

	@Override
	public int getID() {
		return stateID;
	}

}
