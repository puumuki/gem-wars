package gemwars;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gameobjects.Player;
import gameobjects.map.Map;
import io.MapLoader;
import io.Options;
import io.ResourceManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.imageout.ImageOut;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.BlobbyTransition;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.Log;

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
    
    private List<File> availableMaps;
    
    private int currentMapIndex = 0;

    private Map map;
    
	public void init(GameContainer cont, StateBasedGame state) throws SlickException {
		
		availableMaps = MapLoader.findAvailableMaps(new File("src/resources/maps/"));
		
		try {
			map = MapLoader.loadMap(availableMaps.get(currentMapIndex));
		} catch (IOException e) {
			throw new SlickException("Can't load map file. ", e);
		}		

		gamemusic = ResourceManager.getInstance().getMusic("GAME_MUSIC");
		
		System.out.println( "INIIIT");
		
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game)	throws SlickException {
		super.enter(container, game);
		
		gamemusic.loop((float)1.0, Options.getInstance().getMusicVolume());
		
		map.enter(container);
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
	
		super.leave(container, game);
		
		gamemusic.stop();
	}

	public void render(GameContainer cont, StateBasedGame state, Graphics graph)
			throws SlickException {
		
		map.render(cont, graph);
		
	}

	public void update(GameContainer cont, StateBasedGame state, int delta)
			throws SlickException {
		
		map.update(cont, delta);
		
		Input input = cont.getInput();
		
		final double increment = 0.2; 
		
		//If we want a friction to movement we need to change logic here.
		//final double friction = 0.2; 
		
		//By multiplying increment value with delta we keep camera movement
		//speed constant with every platform.
		

		if( input.isKeyDown(Input.KEY_LCONTROL) || input.isKeyDown(Input.KEY_RCONTROL)) {
			// TODO: change it so it does not change the map at simply pressing R/LCONTROL when the camera has been moved 
			
			boolean isMapChanged = false;
			
			if( input.isKeyPressed(Input.KEY_LEFT) ) {
				currentMapIndex--;
				isMapChanged=true;
			}
			
			if( input.isKeyPressed(Input.KEY_RIGHT) ) {
				currentMapIndex++;
				isMapChanged=true;
			}
			
			if( currentMapIndex < 0 ) {
				currentMapIndex = availableMaps.size() - 1;
			}
			
			else if( currentMapIndex >= availableMaps.size() ) {
				currentMapIndex = 0;
			}		

			if( isMapChanged ) {
				try {
					map = MapLoader.loadMap(availableMaps.get(currentMapIndex));
					map.enter(cont);
				} catch (IOException e) {
					Log.error(e);
				}
			}
		}
		
		//Screen Capture
		//TODO: Make this save each capture to separate files ;)
		if (input.isKeyPressed( Input.KEY_F10)) {
			Image target = new Image(cont.getWidth(), cont.getHeight());
			cont.getGraphics().copyArea(target, 0, 0);
			ImageOut.write( target, "screenshot.png", false);
			target.destroy();
		}
		
		if( input.isKeyPressed(Input.KEY_ESCAPE) ) {								
			state.enterState(Gemwars.MAINMENUSTATE, 								
					new EmptyTransition(), 
					new BlobbyTransition());
		}	
	}
	
	

	@Override
	public int getID() {
		return stateID;
	}
}
