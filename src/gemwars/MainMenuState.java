package gemwars;

import java.awt.Font;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

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
	
	public MainMenuState(int stateID) {
		this.stateID = stateID;
	}
	
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		
		//TODO: Use io.ResourceManager
		background = new Image("src/resources/images/menutaus.bmp");
		
		//TODO: Use io.ResourceManager
		Image menuOptions = new Image("src/resources/images/menu1.bmp");
		
		newGameOption = menuOptions.getSubImage(0, 0, 151, 201);
		multiplayerOption = menuOptions.getSubImage(152, 0, 151, 201);
		optionsOption = menuOptions.getSubImage(304, 0, 151, 201);
		exitOption = menuOptions.getSubImage(456, 0, 151, 201);
		
        Font font = new Font("Arial", Font.BOLD, 20);
        fontti = new UnicodeFont(font);

	}

	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		background.draw(0,0);

		newGameOption.draw(menuX, menuY);
		newGameOption.setAlpha(1);
		multiplayerOption.draw(menuX, menuY);
		newGameOption.setAlpha(1);
		optionsOption.draw(menuX, menuY);
		newGameOption.setAlpha(1);
		exitOption.draw(menuX, menuY);
		newGameOption.setAlpha(1);
		
		//fontti.drawString(250, 250, "UlTimAaTtinnen"); // miksei tämä piirry?
		
	}

	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		Input input = gc.getInput();
				
		switch (selectedMenu) {
		case 0: // new game
			if(input.isKeyPressed(Input.KEY_UP)) {
				exitOption.setAlpha(0);
				selectedMenu = 3;
				newGameOption.setAlpha(1);
			}
			else if(input.isKeyPressed(Input.KEY_DOWN)){
				multiplayerOption.setAlpha(0);
				selectedMenu = 1;
				newGameOption.setAlpha(1);
			}
			break;
		case 1: // multiplayer
			if(input.isKeyPressed(Input.KEY_UP)) {
				newGameOption.setAlpha(0);
				selectedMenu = 1;
				multiplayerOption.setAlpha(1);
			}
			else if(input.isKeyPressed(Input.KEY_DOWN)){
				optionsOption.setAlpha(0);
				selectedMenu = 2;
				multiplayerOption.setAlpha(1);
			}
			break;
		case 2: // options
			if(input.isKeyPressed(Input.KEY_UP)) {
				multiplayerOption.setAlpha(0);
				selectedMenu = 1;
				optionsOption.setAlpha(1);
			}
			else if(input.isKeyPressed(Input.KEY_DOWN)){
				exitOption.setAlpha(0);
				selectedMenu = 3;
				optionsOption.setAlpha(1);
			}
			break;
		case 3: // exit
			if(input.isKeyPressed(Input.KEY_UP)) {
				optionsOption.setAlpha(0);
				selectedMenu = 2;
				exitOption.setAlpha(1);
			}
			else if(input.isKeyPressed(Input.KEY_DOWN)){
				newGameOption.setAlpha(0);
				selectedMenu = 0;
				exitOption.setAlpha(1);
			}
			break;
			
		default:
			break;
		
		}
		
	}

	@Override
	public int getID() {
		return stateID;
	}

}
