package gemwars;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;



/**
 * Main menu state for the main menu
 *
 */
public class GameOverState extends BasicGameState {

	int stateID = -1;
	
	public GameOverState(int stateID) {
		this.stateID = stateID;
	}
	
	
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		
	}
	
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		g.drawString("Game Over :(\n\nPress enter.", 100, 100);
	}

	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException {
		Input input = gc.getInput();
		
		if( input.isKeyPressed(Input.KEY_ENTER) || input.isKeyPressed(Input.KEY_ESCAPE) || input.isKeyPressed(Input.KEY_SPACE))
			game.enterState(Gemwars.MAINMENUSTATE, 								
					new FadeOutTransition(), 
					new FadeInTransition());
			
	}
	
	@Override
	public int getID() {
		return stateID;
	}

}
