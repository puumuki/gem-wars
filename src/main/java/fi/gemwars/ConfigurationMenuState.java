package fi.gemwars;

import java.io.File;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.GradientEffect;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

import fi.gemwars.gameobjects.GearPair;
import fi.gemwars.io.Options;
import fi.gemwars.ui.components.menu.BasicMenuItem;
import fi.gemwars.ui.components.menu.BooleanMenuItem;
import fi.gemwars.ui.components.menu.IMenuItem;
import fi.gemwars.ui.components.menu.Menu;
import fi.gemwars.ui.components.menu.PercentMenuItem;

/**
 * OptionState is a game state where user can change setting like key setting,
 * and sound music volume.
 * 
 * @author TeeMuki
 */
public class ConfigurationMenuState extends BasicGameState {

	private int stateID;

	private ArrayList<GearPair> gearPairs = new ArrayList<GearPair>();

	private UnicodeFont font;

	private Menu menu;

	public ConfigurationMenuState(int id) {
		this.stateID = id;
	}

	private static final String SOUND_MENUITEM_TEXT = "Sound volume";
	private static final String MUSIC_MENUITEM_TEXT = "Music volume";
	private static final String FULLSCREEN_MENUITEM_TEXT = "Fullscreen";
	private static final String RETURN_MENUITEM_TEXT = "Done";

	@SuppressWarnings("unchecked")
	@Override
	public void init(GameContainer cont, StateBasedGame state) throws SlickException {

		gearPairs.add(new GearPair(30f, new Vector2f(100, 100), 0.5f));
		gearPairs.add(new GearPair(10f, new Vector2f(50, 50), 0.3f));
		gearPairs.add(new GearPair(15f, new Vector2f(400, 350), 0.1f));
		gearPairs.add(new GearPair(20f, new Vector2f(380, 200), 0.6f));
		gearPairs.add(new GearPair(12f, new Vector2f(380, 500), 1f));
		gearPairs.add(new GearPair(5f, new Vector2f(500, 600), 0.7f));
		gearPairs.add(new GearPair(14f, new Vector2f(780, 100), 0.3f));
		gearPairs.add(new GearPair(25f, new Vector2f(680, 200), 0.2f));

		// This the way to customize a font.
		java.awt.Font awtFont = new java.awt.Font("Arial", java.awt.Font.PLAIN, 20);
		font = new UnicodeFont(awtFont);
		font.addAsciiGlyphs();

		java.awt.Color topColor = new java.awt.Color(0x00000ff);
		java.awt.Color bottomColor = new java.awt.Color(0xbbff00);

		font.getEffects().add(new GradientEffect(topColor, bottomColor, 1f));
		font.loadGlyphs();

		menu = new Menu();

		int posX = 0;

		PercentMenuItem item = new PercentMenuItem(posX, 0, SOUND_MENUITEM_TEXT);
		item.setValue(Options.getInstance().getSoundVolume());
		menu.add("soundvolume", item);

		item = new PercentMenuItem(posX, 25, MUSIC_MENUITEM_TEXT);
		item.setValue(Options.getInstance().getMusicVolume());
		menu.add("musicvolume", item);

		BooleanMenuItem booleanItem = new BooleanMenuItem(posX, 50, FULLSCREEN_MENUITEM_TEXT);
		booleanItem.setValue(Options.getInstance().getFullscreen());

		menu.add("fullscreen", booleanItem);

		menu.add("return", new BasicMenuItem(posX, 75, RETURN_MENUITEM_TEXT));
	}

	/**
	 * Game state id
	 */
	@Override
	public int getID() {
		return stateID;
	};

	/**
	 * This is called when this game state are entered
	 */
	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
	}

	@Override
	public void render(GameContainer cont, StateBasedGame state, Graphics g) throws SlickException {
		g.setBackground(Color.black);
		g.setFont(font);

		for (GearPair pair : gearPairs) {
			pair.render(cont, g);
		}

		int x = (int) cont.getWidth() / 4;
		int y = (int) cont.getWidth() / 4;

		g.translate(x, y);

		menu.render(cont, g);

		g.resetTransform();
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {

		super.leave(container, game);
		container.getInput().clearKeyPressedRecord();
	}

	@Override
	public void update(GameContainer cont, StateBasedGame state, int delta) throws SlickException {
		for (GearPair pair : gearPairs) {
			pair.update(cont, delta);
		}

		menu.update(cont, delta);

		Input input = cont.getInput();

		if (input.isKeyPressed(Input.KEY_ENTER)) {
			IMenuItem item = menu.getMenuItem(menu.getActiveIndex());

			if (item.getText().equals(RETURN_MENUITEM_TEXT)) {
				saveConfigurations(cont);
				state.enterState(Gemwars.MAINMENUSTATE);
			}
		}
	}

	private void saveConfigurations(GameContainer cont) {

		Options options = Options.getInstance();

		for (IMenuItem menuItem : menu) {

			if (menuItem.getText().equals(SOUND_MENUITEM_TEXT)) {
				PercentMenuItem sound = (PercentMenuItem) menuItem;
				options.setSoundVolume((Float) sound.getValue());
			}

			if (menuItem.getText().equals(MUSIC_MENUITEM_TEXT)) {
				PercentMenuItem music = (PercentMenuItem) menuItem;
				options.setMusicVolume((Float) music.getValue());
			}

			if (menuItem.getText().equals(FULLSCREEN_MENUITEM_TEXT)) {
				BooleanMenuItem fullcreen = (BooleanMenuItem) menuItem;
				options.setFullscreen((Boolean) fullcreen.getValue());
			}
		}

		cont.setSoundVolume(options.getSoundVolume());
		cont.setMusicVolume(options.getMusicVolume());

		try {
			cont.setFullscreen(options.getFullscreen());
		} catch (SlickException e) {
			Log.error("Failed to set fullscreen mode -> setting fullscreen mode back to false.", e);
			options.setFullscreen(false);
		}

		try {
			options.save(new File(Options.CONFIGURATION_FILE));
		} catch (SlickException e) {
			Log.error("Problems to save gemwars properties file. ", e);
		}
	}
}
