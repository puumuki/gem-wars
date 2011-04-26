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
	
	public MainMenuState(int stateID) {
		this.stateID = stateID;
	}
	
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		background = new Image("data/menutaus.bmp");
		
		Image menuOptions = new Image("data/menu1.bmp");
		
		newGameOption = menuOptions.getSubImage(0, 0, 151, 201);
		multiplayerOption = menuOptions.getSubImage(152, 0, 151, 201);
		optionsOption = menuOptions.getSubImage(304, 0, 151, 201);
		exitOption = menuOptions.getSubImage(456, 0, 151, 201);
		
        Font font = new Font("Arial", Font.BOLD, 20);
        fontti = new UnicodeFont(font);

	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		background.draw(0,0);

		newGameOption.draw(menuX, menuY);
		multiplayerOption.draw(menuX, menuY);
		optionsOption.draw(menuX, menuY);
		exitOption.draw(menuX, menuY);
		
		fontti.drawString(250, 250, "UlTimAaTtinnen"); // miksei tämä piirry?
		
	}

	public void update(GameContainer gc, StateBasedGame sb, int delta)
			throws SlickException {
		Input input = gc.getInput();
		
	}

	@Override
	public int getID() {
		return stateID;
	}

}
