package gemwars;

import io.ResourceManager;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import sun.font.FontManager.FamilyDescription;

public class NyanState extends BasicGameState{

	private int stateID = 0;
	
	private Animation nyanAnimated;
	private Music nyanMusic;
	
	public NyanState( int stateID ) {
		this.stateID = stateID;
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
						
		Image frames = ResourceManager.getInstance().getImage("NYAN");
		
		nyanAnimated = new Animation();
		
		int duration = 85;
		int frameWidth = 400;
		
		for (int i = 0; i < frames.getWidth(); i += frameWidth ) {
			Image frame = frames.getSubImage(i, 0, frameWidth, frames.getHeight());
			nyanAnimated.addFrame(frame, duration);			
		}		
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game)
			throws SlickException {

		//nyanMusic.play();
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game)
			throws SlickException {
	
		//nyanMusic.stop();
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		
		nyanAnimated.draw( 0, 0, container.getWidth(), container.getHeight());
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		Input input = container.getInput();
		
		if( input.isKeyDown(Input.KEY_ESCAPE)) {
			
		}
		
		nyanAnimated.update(delta);
	}

	@Override
	public int getID() {
		return stateID;
	}
}
