package gemwars;

import java.io.File;
import java.io.IOException;

import gameobjects.map.Map;
import io.MapLoader;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The Gameplay state is the one where the game itself happens.
 *
 */
public class GameplayState extends BasicGameState {

    int stateID = -1;
    
    public GameplayState( int stateID ) 
    {
       this.stateID = stateID;
    }
    
    private Map map;
    
	public void init(GameContainer cont, StateBasedGame state) throws SlickException {
		try {
			map = MapLoader.loadMap(new File("src/resources/maps/e1l1.gem"));
		} catch (IOException e) {
			throw new SlickException("Can't load map file. ", e);
		}		
	}

	public void render(GameContainer cont, StateBasedGame state, Graphics graph)
			throws SlickException {

		map.render(cont, graph);
	}

	public void update(GameContainer cont, StateBasedGame state, int delta)
			throws SlickException {

	}

	@Override
	public int getID() {
		return stateID;
	}
}
