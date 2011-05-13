package utils;

import org.newdawn.slick.Animation;

public class GemwarsUtils {
	
	/**
	 * Helps to reverse an animation (gem moving to the right -> gem moving to the left)
	 * @param a animation that has to be reversed
	 * @return the new, reversed animation
	 */
	public static Animation reverseAnimation(Animation a) {
		Animation newAnimation = new Animation();
		for (int i = a.getFrameCount()-1; i >= 0; i--) {
			newAnimation.addFrame(a.getImage(i), a.getDuration(i));
		}
		return newAnimation;
	}	
}
