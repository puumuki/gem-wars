package gemwars;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameplayState extends BasicGameState {

    int stateID = -1;
    
    public GameplayState( int stateID ) 
    {
       this.stateID = stateID;
    }
    
	public void init(GameContainer cont, StateBasedGame state)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	public void render(GameContainer cont, StateBasedGame state, Graphics graph)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	public void update(GameContainer cont, StateBasedGame state, int delta)
			throws SlickException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getID() {
		return stateID;
	}
}
