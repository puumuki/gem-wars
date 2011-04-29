package gemwars;

import gameobjects.Item;
import gameobjects.map.ItemTypes;

import io.ResourceManager;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;

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
	
	UnicodeFont fontti = null;
	
	private static int menuX = 38;
	private static int menuY = 240;
	
	float alpha = 0;

	int currentSelection = 1;
	int selected = 0;
	
	private Item item;
	
	public MainMenuState(int stateID) {
		this.stateID = stateID;
	}
	
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
		gamemusic = ResourceManager.getInstance().getMusic("GAME_MUSIC");
		menumusic.loop();
		
        Font font = new Font("Arial", Font.BOLD, 20);
        fontti = new UnicodeFont(font);
       
        item = new Item(ItemTypes.DARK_BOULDER);
        item.positionX = 200;
        item.positionY = 30;
        
    	
	}

	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		background.draw(0,0);
		
		switch (currentSelection) {
		case 2:
			multiplayerOption.draw(menuX, menuY);
			break;
		case 3:
			optionsOption.draw(menuX, menuY);
			break;
		case 4:
			exitOption.draw(menuX, menuY);
			break;
		default:
			currentSelection = 1;
			newGameOption.draw(menuX, menuY);
			break;
		}
		
		item.render(gc, g);
	}

	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		//Input input = gc.getInput();

		switch (selected) {
		case 1: // single player
			menusound.play();
			menumusic.stop();
			gamemusic.loop();
			game.enterState(Gemwars.GAMEPLAYSTATE);
			break;
		case 2: // multiplayer
			break;
		case 3: // options
			break;
		case 4: // exit
			System.exit(0);
			break;
		default:
			break;
		}

	}
	
	@Override
	public void keyPressed(int key, char c) {
		if (key == Input.KEY_UP) {
			currentSelection--;
			if(currentSelection < 1)
				currentSelection = 4;
		}
		if (key == Input.KEY_DOWN) {
			currentSelection++;
			if(currentSelection > 4)
				currentSelection = 1;
		}
		
		if (key == Input.KEY_ENTER) {
			selected = currentSelection;
		}
	}

	@Override
	public int getID() {
		return stateID;
	}

}
