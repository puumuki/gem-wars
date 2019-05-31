package fi.gemwars.gameobjects;

import java.util.ArrayList;

import fi.gemwars.io.ResourceManager;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class DiamondAnimation extends AEntity {

	public static final Color FILTER_COLOR = new Color(1f, 1f, 1f, 0.8f); 
	
	private float scale = 1;
	
	private Animation diamondAnimation;
	
	private static final int DIAMOND_FRAME_WIDTH = 207;
	private static final int DIAMOND_FRAME_HEIGHT = 199;
	
	public DiamondAnimation( int posX, int posY, float scale ) {

		this.positionX = posX;
		this.positionY = posY;
		this.scale = scale;
		
		int duration = 50;
		
		diamondAnimation = new Animation();

		Image image = ResourceManager.fetchImage("MENU_DIAMONDANIMATION");

		ArrayList<Image> frames = new ArrayList<Image>();
		
		//Extracts the frames
		for (int y = 0; y < image.getHeight(); y += DIAMOND_FRAME_HEIGHT) {
			for (int x = 0; x < image.getWidth(); x += DIAMOND_FRAME_WIDTH) {
				Image frame = image.getSubImage(x, y, DIAMOND_FRAME_WIDTH, DIAMOND_FRAME_HEIGHT);				
				frames.add( frame );
			}
		}
		
		//Removes last four empty frames
		for( int i = 0; i< 4; i++) {
			frames.remove(frames.size()-1);
		}
		
		for( Image frame : frames ) {
			diamondAnimation.addFrame(frame, duration);
		}
	}

	@Override
	public void render(GameContainer cont, Graphics grap) throws SlickException {
		diamondAnimation.draw(positionX, positionY, 
							  diamondAnimation.getWidth() * scale, 
							  diamondAnimation.getHeight() * scale, 
							  DiamondAnimation.FILTER_COLOR);	 
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		diamondAnimation.update(delta);		
	}
}
