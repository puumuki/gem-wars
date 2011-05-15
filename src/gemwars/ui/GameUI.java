package gemwars.ui;

import io.ResourceManager;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class GameUI {

	private Animation clock;
	private Animation coin;
	private Animation gem;
	private Animation head;
	
	
	public GameUI(GameContainer cont, StateBasedGame state) throws SlickException {
		init(cont, state);
	}
	
	public void init(GameContainer cont, StateBasedGame state) throws SlickException {
		clock = ResourceManager.fetchAnimation("UI_CLOCK");
		coin = ResourceManager.fetchAnimation("UI_COIN");
		gem = ResourceManager.fetchAnimation("UI_GEM");
		head = ResourceManager.fetchAnimation("UI_HEAD");
		
	}
	
	
	public void render(GameContainer cont, Graphics grap, int time, int points, int gemCount, int gemsNeeded, int lives) throws SlickException {

		int margin = 5;
		
		grap.drawAnimation(coin, margin, margin);
		grap.drawString(""+points, margin + coin.getWidth() + margin * 2, margin + coin.getHeight() / 2);
		
		grap.drawAnimation(clock, cont.getWidth() - clock.getWidth() - 5, 5);
		grap.drawString(""+time, cont.getWidth() - (margin + clock.getWidth() + margin * 2) - 50, margin + coin.getHeight() / 2);
		
		grap.drawAnimation(gem, margin, cont.getHeight() - gem.getWidth() - margin);
		grap.drawString(gemCount + " / " + gemsNeeded, margin + gem.getWidth() + margin * 2, cont.getHeight() - (margin + gem.getHeight() / 2));
		
		grap.drawAnimation(head, cont.getWidth() - head.getWidth() - margin, cont.getHeight() - head.getWidth() - margin);
		grap.drawString(""+lives, cont.getWidth() - (margin + head.getWidth() + margin * 2) - 50, cont.getHeight() - (margin + head.getHeight() / 2));
	}
	
}
