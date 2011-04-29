package gemwars;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


import gameobjects.GearPair;
import gemwars.ui.components.*;

/**
 * OptionState is a game state where user can change setting like key setting, and sound music volume.
 * 
 * @author TeeMuki
 */
public class ConfigurationMenuState extends BasicGameState  {

	private int stateID;
			
	private ArrayList<GearPair> gearPairs = new ArrayList<GearPair>();
	
	public ConfigurationMenuState( int id ) {
		this.stateID = id;
	}
	
	@Override
	public void init(GameContainer cont, StateBasedGame state) throws SlickException {		
		gearPairs.add( new GearPair( 30f, new Vector2f(100,100), 0.5f));
		gearPairs.add( new GearPair( 10f, new Vector2f(50,50), 0.3f));
		gearPairs.add( new GearPair( 15f, new Vector2f(400,350), 0.1f));
		gearPairs.add( new GearPair( 20f, new Vector2f(380,200), 0.6f));
		gearPairs.add( new GearPair( 12f, new Vector2f(380,500), 1f));
		gearPairs.add( new GearPair( 5f, new Vector2f(500,600), 0.7f));
		gearPairs.add( new GearPair( 14f, new Vector2f(780,100), 0.3f));
		gearPairs.add( new GearPair( 25f, new Vector2f(680,200), 0.2f));			
	}		

	/**
	 * Game state id
	 */
	public int getID() {
		return stateID;
	};
	
	/**
	 * This is called when this game state are entered
	 */
	@Override
	public void enter(GameContainer container, StateBasedGame game)	throws SlickException {
		super.enter(container, game);
	}
	
	@Override
	public void render(GameContainer cont, StateBasedGame state, Graphics g) throws SlickException {
		
		g.setBackground(Color.white);	
		
		for( GearPair pair : gearPairs ) {
			pair.render(cont, g);
		}
	}
	
	@Override
	public void update(GameContainer cont, StateBasedGame state, int delta)	throws SlickException {
		for( GearPair pair : gearPairs ) {
			pair.update(cont, delta);
		}
	}
}
