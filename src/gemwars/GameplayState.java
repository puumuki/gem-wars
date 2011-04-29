package gemwars;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import gameobjects.map.Map;
import io.MapLoader;
import io.ResourceManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * The Gameplay state is the one where the game itself happens.
 *
 */
public class GameplayState extends BasicGameState {

    int stateID = -1;
    
    Music gamemusic = null;
    
    public GameplayState( int stateID ) 
    {
       this.stateID = stateID;
    }
    
    private Map map;
    
	public void init(GameContainer cont, StateBasedGame state) throws SlickException {
		try {
			map = MapLoader.loadMap(new File("src/resources/maps/e4l1.gem"));
		} catch (IOException e) {
			throw new SlickException("Can't load map file. ", e);
		}
		
		try {
			File file = new File("src/resources/resources.xml"); 
			FileInputStream fileStream = new FileInputStream(file);		
			ResourceManager.getInstance().loadResources(fileStream, false); 
		} catch (Exception e) {
			e.printStackTrace();
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
