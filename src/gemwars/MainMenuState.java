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
import org.newdawn.slick.SlickException;
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
	
	UnicodeFont fontti = null;
	
	private static int menuX = 38;
	private static int menuY = 240;
	
	float alpha = 0;

	int selectedMenu = 0;
	
	private Item item;
	
	public MainMenuState(int stateID) {
		this.stateID = stateID;
	}
	
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		
		try {
			File file = new File("src/resources/resources.xml"); 
			FileInputStream fileStream = new FileInputStream(file);		
			ResourceManager.getInstance().loadResources(fileStream, true); 
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		background = ResourceManager.getInstance().getImage("MENU_BG");
		
		Image menuOptions = ResourceManager.getInstance().getImage("MENU_OPTIONS");
		
		newGameOption = menuOptions.getSubImage(0, 0, 151, 201);
		multiplayerOption = menuOptions.getSubImage(152, 0, 151, 201);
		optionsOption = menuOptions.getSubImage(304, 0, 151, 201);
		exitOption = menuOptions.getSubImage(456, 0, 151, 201);
		
        Font font = new Font("Arial", Font.BOLD, 20);
        fontti = new UnicodeFont(font);
       
        item = new Item(ItemTypes.DARK_BOULDER);
        item.positionX = 200;
        item.positionY = 30;
        
    	
	}

	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		background.draw(0,0);

		newGameOption.draw(menuX, menuY);
		multiplayerOption.draw(menuX, menuY);
		optionsOption.draw(menuX, menuY);
		exitOption.draw(menuX, menuY);
		
		//fontti.drawString(250, 250, "UlTimAaTtinnen"); // miksei tämä piirry?
		item.render(gc, g);
	}

	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		Input input = gc.getInput();
		
		
		 
		
	}
	
	

	@Override
	public int getID() {
		return stateID;
	}

}
