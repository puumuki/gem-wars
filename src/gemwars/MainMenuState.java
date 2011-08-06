package gemwars;

import gameobjects.Item;
import gameobjects.map.ItemTypes;

import io.MapLoader;
import io.Options;
import io.ResourceManager;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
	
	private boolean inSinglePlayerSubmenu = false;
	private int singlePlayerMenuSelected = 0;
	private List<File> singlePlayerMaps;
	
	public MainMenuState(int stateID) {
		this.stateID = stateID;
	}
	
	
	/**
	 * Initialisation of the main menu (loading resources etc)
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		
		try {
			File file = new File("src/resources/resources.xml"); 
			
			if( file.exists() == false ) {
				file = new File("resources/resources.xml");
			}
			
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
        
        
        // TODO: move this out of here
        File file = new File("src/resources/maps/");
		
		if( file.exists() == false ) {
			file = new File("resources/maps/");
		}
        singlePlayerMaps =  MapLoader.loadSinglePlayerMaps(file);
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
		menumusic.fade(500, 0, true);
		//menumusic.stop();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		background.draw(0,0);
		
		// are we in the singleplayer submenu?
		if (inSinglePlayerSubmenu)
		{
			renderSinglePlayerSubmenu(gc, game, g);
		}
		else
		{
			drawCredits(gc, g);
			
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
		}
		
		item.render(gc, g);
	}

	@SuppressWarnings("unchecked")
	private void renderSinglePlayerSubmenu(GameContainer gc,
			StateBasedGame game, Graphics g) throws SlickException {
		
		menuanimations.get("single").draw(menuAnimX, menuAnimY);
		
		// TODO: this does not work with subfolders yet
		
		java.awt.Font awtFont = new java.awt.Font("Verdana", java.awt.Font.PLAIN, 13);
        UnicodeFont font = new UnicodeFont(awtFont);
        font.addAsciiGlyphs();
        font.getEffects().add(new ColorEffect(new Color(255,255,255)));
        font.getEffects().add(new OutlineEffect(1, new Color(255,255,255,150)));
        font.loadGlyphs();
        StringBuilder text = new StringBuilder();
        int i = 0;
        int start = 0, end = 0;
        int mapCount = singlePlayerMaps.size();
    	if (singlePlayerMenuSelected < 5)
    	{
    		start = 0;
    		end = 10;
    	}
    	else if (singlePlayerMenuSelected < mapCount - 5)
    	{
    		start = singlePlayerMenuSelected - 5;
    		end = singlePlayerMenuSelected + 5;
    	}
    	else
    	{
    		start = mapCount - 11;
    		end = mapCount;
    	}
        for (File map : singlePlayerMaps) {
        	if (i >= start)
        	{
	        	if (singlePlayerMenuSelected == i)
	        		text.append(map.getName() + "\n");
	        	else
	        		text.append(map.getName() + "\n");
        	}
        	i++;
        	if (i > end)
        		break;
        	
        }
		font.drawString(265, 180, text.toString());
		int selectorY = (singlePlayerMenuSelected - start) * font.getLineHeight() + 180;
		font.drawString(245, selectorY, ">");
		
	}


	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		
		Input input = gc.getInput();

		// are we in the singleplayer submenu?
		if (inSinglePlayerSubmenu)
		{
			updateSinglePlayerSubmenu(gc, game, delta);
			
		}
		else
		{
			if( input.isKeyPressed(Input.KEY_ENTER)) {
				switch (selected) {
				case 1: // single player
					/*game.enterState(Gemwars.GAMEPLAYSTATE, 								
									new EmptyTransition(), 
									new BlobbyTransition());*/
					inSinglePlayerSubmenu = true;
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
		

	}
	
	private void updateSinglePlayerSubmenu(GameContainer gc,
			StateBasedGame game, int delta) {
		
		Input input = gc.getInput();
		
		if (input.isKeyPressed(Input.KEY_UP)) {
			if (singlePlayerMenuSelected > 0)
				singlePlayerMenuSelected--;
		}
		if (input.isKeyPressed(Input.KEY_DOWN)) {
			if (singlePlayerMenuSelected < singlePlayerMaps.size() - 1)
				singlePlayerMenuSelected++;
		}
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			((GameplayState)game.getState(Gemwars.GAMEPLAYSTATE)).setCurrentMapIndex(singlePlayerMenuSelected);
			game.enterState(Gemwars.GAMEPLAYSTATE,
					new EmptyTransition(), 
					new BlobbyTransition());
		}
		if (input.isKeyPressed(Input.KEY_ESCAPE) || input.isKeyPressed(Input.KEY_LEFT)) {
			currentSelection = 0;
			inSinglePlayerSubmenu = false;
		}
		
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
	
	@SuppressWarnings("unchecked")
	public void drawCredits(GameContainer gc, Graphics g)
			throws SlickException {
		java.awt.Font awtFont = new java.awt.Font("Verdana", java.awt.Font.PLAIN, 13);
        UnicodeFont font = new UnicodeFont(awtFont);
        font.addAsciiGlyphs();
        font.getEffects().add(new ColorEffect(new Color(255,255,255)));
        font.getEffects().add(new OutlineEffect(1, new Color(255,255,255,150)));
        font.loadGlyphs();
		String text = "GemWars is a game with a long development\n" +
				"history comparable to Duke Nukem Forever.\n\n" +
				"This Java version is the work of\n" +
				"Miika Hämynen and Teemu Puukko.\n\n" +
				"The following people have had a part in\nGemWars' development in the past:\n" +
				"Jarkko Laine, Lauri Laine, Joona Nuutinen,\nMatti Manninen, Skyler York, Antti Kanninen\n\n" +
				"We thank them for the pioneering work.";
		font.drawString(245, 170, text);
	}

	@Override
	public int getID() {
		return stateID;
	}

}
