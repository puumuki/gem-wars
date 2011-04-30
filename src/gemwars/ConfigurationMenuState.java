package gemwars;


import java.awt.Font;
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
import org.newdawn.slick.command.KeyControl;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.GradientEffect;
import org.newdawn.slick.font.effects.ShadowEffect;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.RotateTransition;

import com.sun.org.apache.bcel.internal.generic.NEW;


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
			
	private UnicodeFont font;
	
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
		
		//This the way to customize a font.
		java.awt.Font awtFont = new java.awt.Font("Ariel", java.awt.Font.PLAIN, 20);
        font = new UnicodeFont(awtFont);
        font.addAsciiGlyphs();       
        
        java.awt.Color topColor = new java.awt.Color( 0x00000ff );
        java.awt.Color bottomColor = new java.awt.Color( 0xbbff00 );
        
        font.getEffects().add(new GradientEffect(topColor, bottomColor, 1f));
        font.loadGlyphs();
        
                
        
        
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
		g.setFont(font);
		
		for( GearPair pair : gearPairs ) {
			pair.render(cont, g);
		}
		
		g.drawString("To return to the main menu, try to press the 'Escape'-key", 50, 50);
	}
	
	@Override
	public void update(GameContainer cont, StateBasedGame state, int delta)	throws SlickException {
		for( GearPair pair : gearPairs ) {
			pair.update(cont, delta);
		}
		
		Input input = cont.getInput();
		
		if( input.isKeyPressed(Input.KEY_ESCAPE) ) {								
			state.enterState(Gemwars.MAINMENUSTATE, 
							new FadeOutTransition(), 
							new FadeInTransition());
		}			
	}
}
