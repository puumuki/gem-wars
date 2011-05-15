package gemwars;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.List;

import gameobjects.Monster;
import gameobjects.Player;
import gameobjects.map.ItemTypes;
import gameobjects.map.Map;
import gemwars.ui.GameUI;
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
	boolean isMapChanged = false;

    private Map map;
    
    private GameUI ui;
    
	@Override
	public void init(GameContainer cont, StateBasedGame state) throws SlickException {
		
		File file = new File("src/resources/maps/");
		
		if( file.exists() == false ) {
			file = new File("resources/maps/");
		}
		
		availableMaps = MapLoader.findAvailableMaps(file);
		
		try {
			map = MapLoader.loadMap(availableMaps.get(currentMapIndex));
		} catch (IOException e) {
			throw new SlickException("Can't load map file. ", e);
		}		

		gamemusic = ResourceManager.getInstance().getMusic("GAME_MUSIC");
		
		ui = new GameUI(cont, state);
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game)	throws SlickException {
		super.enter(container, game);

		gamemusic.setVolume(0);
		gamemusic.fade(1000, Options.getInstance().getMusicVolume(), false);
		gamemusic.loop((float)1.0, Options.getInstance().getMusicVolume());
		
		map.enter(container);
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
	
		super.leave(container, game);
		gamemusic.fade(500, 0, true);
		//gamemusic.stop();
	}

	@Override
	public void render(GameContainer cont, StateBasedGame state, Graphics graph)
			throws SlickException {
		
		map.render(cont, graph);
		
		ui.render(cont, graph, map.getRemainingTime(), map.getPlayer(0).getScore(), map.getPlayer(0).getGems(),  map.getGemCount(), map.getPlayer(0).getLives());
		
		/*
		graph.drawString("SCORE: " + map.getPlayer(0).getScore(), 500, 0);
		graph.drawString(" GEMS: " + map.getPlayer(0).getGems() + "/" + map.getGemCount(), 500, 15);
		graph.drawString("LIVES: " + map.getPlayer(0).getLives(), 500, 30);
		*/
	}

	@Override
	public void update(GameContainer cont, StateBasedGame state, int delta)
			throws SlickException {
		
		map.update(cont, delta);
		

		Input input = cont.getInput();
		

		// FIXME: for debug only
		if (input.isKeyPressed(Input.KEY_0)) {
			for (Monster m : map.getMonsters())
				m.kill();
		}
		
		checkDeaths(cont, state, delta);

		checkGoal(cont, state, delta);
		
		
		
		//final double increment = 0.2; 
		
		//If we want a friction to movement we need to change logic here.
		//final double friction = 0.2; 
		
		//By multiplying increment value with delta we keep camera movement
		//speed constant with every platform.
		

		if( input.isKeyDown(Input.KEY_LCONTROL) || input.isKeyDown(Input.KEY_RCONTROL)) {
			// TODO: change it so it does not change the map at simply pressing R/LCONTROL when the camera has been moved 
			
			
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
		}
		if( isMapChanged ) {
			
			
			try {
				map = MapLoader.loadMap(availableMaps.get(currentMapIndex), map.getPlayers());
				
				map.enter(cont);
				isMapChanged = false;
			} catch (IOException e) {
				Log.error(e);
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
	
	private void checkDeaths(GameContainer cont, StateBasedGame state, int delta) throws SlickException {
		// are there dead monsters?
		for (Monster m : map.getMonsters()) {
			if (m.isDead()) {
				map.drawDiamondMatrix(m.positionX, m.positionY);
			}
		}
		
		// has the player died?
		for (Player p : map.getPlayers()) {
			if (map.hasMonsterKilled(p)) // checks for monster killing 
				p.kill();
			
			if (p.isDead()) // has the player died in some manner?
			{
				map.drawDiamondMatrix(p.positionX, p.positionY);
				
				// TODO: pause here
				
				if (map.getPlayer(0).lives <= 0) {
					
					state.enterState(Gemwars.GAMEOVERSTATE, 								
							new EmptyTransition(), 
							new BlobbyTransition());
				}
				else
				{
					try {
						
						Map tempMap = MapLoader.loadMap(availableMaps.get(currentMapIndex), map.getPlayers());
						this.map = tempMap;

						tempMap.enter(cont);
					} catch (IOException e) {
						throw new SlickException(e.getMessage());
					}
				}
			}
		}
	}
	
	private void checkGoal(GameContainer cont, StateBasedGame state, int delta) {
		for (Player p : map.getPlayers()) {
			// is the player in the goal?
			if (map.isGoalOpen()) {
				List<Point> goals = map.findItemPositions(map.getSpecialLayer(), ItemTypes.GOAL);
				Point point = goals.get(0);
				if (p.positionX == point.x && p.positionY == point.y) {
					// TODO: show level ending screen and change level
					currentMapIndex++;
					isMapChanged = true;
				}
			}
		}
	}

	@Override
	public int getID() {
		return stateID;
	}
}
