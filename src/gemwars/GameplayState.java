package gemwars;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import gameobjects.map.Map;
import io.MapLoader;
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

/**
 * The Gameplay state is the one where the game itself happens.
 *
 */
public class GameplayState extends BasicGameState {

    int stateID = -1;
    
    private double cameraPositionX = 0;
    private double cameraPositionY = 0;
    
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

		graph.translate((float)cameraPositionX, 
						(float)cameraPositionY);
		
		map.render(cont, graph);
		
		graph.resetTransform();
		
	}

	public void update(GameContainer cont, StateBasedGame state, int delta)
			throws SlickException {
		
		Input input = cont.getInput();
		
		final double increment = 0.2; 
		
		//If we want a friction to movement we need to change logic here.
		//final double friction = 0.2; 
		
		//By multiplying increment value with delta we keep camera movement
		//speed constant with every platform.
		
		if( input.isKeyDown(Input.KEY_LEFT) ) {
			cameraPositionX += increment * delta;
		}
		
		if( input.isKeyDown(Input.KEY_RIGHT)) {
			cameraPositionX -= increment * delta;
		}

		if( input.isKeyDown(Input.KEY_UP)) {
			cameraPositionY += increment * delta;
		}
		
		if( input.isKeyDown(Input.KEY_DOWN)) {
			cameraPositionY -= increment * delta;
		}
		
		//Screen Capture
		//TODO: Make this save each capture to separate files ;)
		if (input.isKeyPressed( Input.KEY_F10)) {
			Image target = new Image(cont.getWidth(), cont.getHeight());
			cont.getGraphics().copyArea(target, 0, 0);
			ImageOut.write( target.getFlippedCopy(false, true), "screenshot.png", false);
			target.destroy();
		}
	}
	
	

	@Override
	public int getID() {
		return stateID;
	}
}
