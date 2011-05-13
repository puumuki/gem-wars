package utils;

import org.newdawn.slick.Animation;

public class GemwarsUtils {
	public static Animation reverseAnimation( Animation mation ) {			
		Animation reversedAnimation = new Animation();
		
		for (int i = mation.getFrameCount(); i > 0 ; i--) {
			reversedAnimation.addFrame(mation.getImage(i), mation.getDuration(i)); 
		}
		
		return reversedAnimation;
	}
	
}
