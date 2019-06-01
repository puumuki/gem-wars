package fi.gemwars;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.OutlineEffect;

import fi.gemwars.gameobjects.AEntity;

public class AboutText extends AEntity {

	final String text = "GemWars is a game with a long development\n" + "history comparable to Duke Nukem Forever.\n\n"
			+ "This Java version is the work of\n" + "Miika Hämynen and Teemu Puukko.\n\n"
			+ "The following people have had a part in\nGemWars' development in the past:\n"
			+ "Jarkko Laine, Lauri Laine, Joona Nuutinen,\nMatti Manninen, Skyler York, Antti Kanninen\n\n"
			+ "We thank them for the pioneering work.";

	private UnicodeFont font;

	public AboutText(int positionX, int positionY) throws SlickException {
		this.positionX = positionX;
		this.positionY = positionY;
		this.font = initFont();
		this.hide = true;
	}

	public void drawCredits(GameContainer gc, Graphics g) throws SlickException {
		font.drawString(245, 170, text);
	}

	public UnicodeFont initFont() throws SlickException {
		java.awt.Font awtFont = new java.awt.Font("Verdana", java.awt.Font.PLAIN, 15);
		UnicodeFont font = new UnicodeFont(awtFont);
		font.addAsciiGlyphs();
		font.getEffects().add(new ColorEffect(new java.awt.Color(0, 0, 0)));
		font.getEffects().add(new OutlineEffect(1, new java.awt.Color(0, 0, 0, 150)));
		font.loadGlyphs();
		return font;
	}

	@Override
	public void render(GameContainer cont, Graphics grap) throws SlickException {
		if (!hide) {
			font.drawString(positionX, positionY, text);
		}
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {

		Input input = cont.getInput();

		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			this.hide = true;
		}
	}
}
