package gemwars;

import java.util.ArrayList;

import io.ResourceManager;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

public class DiamondAnimation extends Animation {

	public static final Color FILTER_COLOR = new Color(1f, 1f, 1f, 0.8f); 
	
	public DiamondAnimation() {

		int duration = 50;

		int width = 207;
		int height = 199;

		Image image = ResourceManager.fetchImage("MENU_DIAMONDANIMATION");

		ArrayList<Image> frames = new ArrayList<Image>();
		
		//Extracts the frames
		for (int y = 0; y < image.getHeight(); y += height) {
			for (int x = 0; x < image.getWidth(); x += width) {
				Image frame = image.getSubImage(x, y, width, height);				
				frames.add( frame );
			}
		}
		
		//Removes last four empty frames
		for( int i = 0; i< 4; i++) {
			frames.remove(frames.size()-1);
		}
		
		for( Image frame : frames ) {
			addFrame(frame, duration);
		}
	}
}
