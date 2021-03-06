package fi.gemwars;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.imageout.ImageOut;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.Log;

import fi.gemwars.gameobjects.Monster;
import fi.gemwars.gameobjects.Player;
import fi.gemwars.gameobjects.map.ItemType;
import fi.gemwars.gameobjects.map.Map;
import fi.gemwars.io.MapLoader;
import fi.gemwars.io.Options;
import fi.gemwars.io.ResourceManager;
import fi.gemwars.ui.GameUI;
import fi.gemwars.utils.TimeCounter;
import fi.gemwars.utils.UniqueID;

/**
 * The Gameplay state is the one where the game itself happens.
 *
 */
public class GameplayState extends BasicGameState {

	/**
	 * Unique game state ID
	 */
	private int stateID = -1;

	private Music gamemusic = null;

	/**
	 * Currently selected map
	 */
	private int currentMapIndex = 0;

	private List<File> singlePlayerMaps, availableMaps;

	public List<File> getSinglePlayerMaps() {
		return singlePlayerMaps;
	}

	public GameplayState(int stateID) {
		this.stateID = stateID;
	}

	public int getCurrentMapIndex() {
		return currentMapIndex;
	}

	public void changeMap(File mapFile) {

		int mapIndex = currentMapIndex + 1;

		for (int i = 0; i < singlePlayerMaps.size(); i++) {

			File map = singlePlayerMaps.get(i);

			if (map.equals(mapFile)) {
				mapIndex = i;
				break;
			}
		}

		setCurrentMapIndex(mapIndex);
	}

	public void setCurrentMapIndex(int currentMapIndex) {
		this.currentMapIndex = currentMapIndex - 1;
		isMapChanged = true;
	}

	boolean isMapChanged = false;

	private Map map;

	private GameUI ui;

	private TimeCounter deathTimer = new TimeCounter();

	// goal stuff
	private TimeCounter goalTimer = new TimeCounter();
	private int goaltime = 0;
	private long goalEndTimeStart = 0;
	private int goalBlackingAlpha = 0;
	private Sound bonusSound;

	@Override
	public void init(GameContainer cont, StateBasedGame state) throws SlickException {

		File file = new File(MapList.MAPFOLDER);

		availableMaps = MapLoader.findAvailableMaps(file);
		singlePlayerMaps = MapLoader.loadSinglePlayerMaps(file);

		try {
			MapLoader loader = new MapLoader();
			map = loader.loadMap(singlePlayerMaps.get(currentMapIndex));
		} catch (IndexOutOfBoundsException iobe) {
			throw new SlickException("No maps found. ", iobe);
		} catch (IOException e) {
			throw new SlickException("Can't load map file. ", e);
		}

		bonusSound = ResourceManager.fetchSound("GAME_BONUS");
		gamemusic = ResourceManager.getInstance().getMusic("GAME_MUSIC");

		ui = new GameUI(cont, state);
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);

		gamemusic.setVolume(0);
		gamemusic.fade(1000, Options.getInstance().getMusicVolume(), false);
		gamemusic.loop((float) 1.0, Options.getInstance().getMusicVolume());

		map.enter(container);
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {

		super.leave(container, game);
		gamemusic.fade(500, 0, true);
		// gamemusic.stop();
	}

	@Override
	public void render(GameContainer cont, StateBasedGame state, Graphics graph) throws SlickException {

		map.render(cont, graph);

		if (goalTimer.isActive()) {
			graph.setColor(new Color(0, 0, 0, goalBlackingAlpha));
			graph.fillRect(0, 0, cont.getWidth(), cont.getHeight());
		}

		drawUI(cont, graph);
	}

	@Override
	public void update(GameContainer cont, StateBasedGame state, int delta) throws SlickException {

		map.update(cont, delta);

		Input input = cont.getInput();

		// Are we doing the goal animation?
		if (goalTimer.isActive()) {
			if (goaltime > 0) {
				for (Player p : map.getPlayers()) {
					// increment the player's score by 100 points for every second that was left on
					// the clock
					p.addScore(100);
				}
				goaltime--;
				if (!bonusSound.playing())
					bonusSound.play();

				if (goaltime == 0)
					goalEndTimeStart = goalTimer.timeElapsedInMilliseconds();
			}
			// slight delay before level change
			else {
				if (goalTimer.timeElapsedInMilliseconds() >= goalEndTimeStart + 1500) {
					isMapChanged = true;
				} else if (goalBlackingAlpha < 255) {
					goalBlackingAlpha += delta * 0.5;
					if (goalBlackingAlpha > 255)
						goalBlackingAlpha = 255;
				}
			}

		}
		// normal gameplay
		else {
			checkDeaths(cont, state, delta);
			checkGoal(cont, state, delta);
		}

		// Uncomment this to debug game on runtime
		// debugHelper( input );

		if (input.isKeyPressed(Input.KEY_R)) {
			map.resetMap();
		}

		if (isMapChanged) {

			currentMapIndex++;
			if (currentMapIndex < 0) {
				currentMapIndex = singlePlayerMaps.size() - 1;
			}

			else if (currentMapIndex >= singlePlayerMaps.size()) {
				currentMapIndex = 0;
			}

			try {
				MapLoader loader = new MapLoader();
				map = loader.loadMap(singlePlayerMaps.get(currentMapIndex), map.getPlayers());

				map.enter(cont);
				isMapChanged = false;
				resetGoal();
			} catch (SlickException e) {
				Log.error(e);
			}
		}

		// Screen Capture
		if (input.isKeyPressed(Input.KEY_F10)) {
			takeScreenShot(cont);
		}

		if (input.isKeyPressed(Input.KEY_P) || input.isKeyPressed(Input.KEY_PAUSE)
				|| input.isKeyPressed(Input.KEY_ESCAPE)) {
			pauseGame(cont, state);
		}
	}

	public void takeScreenShot(GameContainer cont) throws SlickException {
		File file = null;

		do {
			file = new File("gemwars_" + UniqueID.nextUniqueID() + ".png");
		} while (file.exists());

		Image target = new Image(cont.getWidth(), cont.getHeight());
		cont.getGraphics().copyArea(target, 0, 0);
		ImageOut.write(target, "screenshot.png", false);
		target.destroy();
	}

	/**
	 * Little helper for runtime debuging
	 * 
	 * @param input
	 */
	private void debugHelper(Input input) {

		if (input.isKeyPressed(Input.KEY_0)) {
			for (Monster m : map.getMonsters())
				m.kill();
		}

		// final double increment = 0.2;

		// If we want a friction to movement we need to change logic here.
		// final double friction = 0.2;

		// By multiplying increment value with delta we keep camera movement
		// speed constant with every platform.

		// For debugging only
		if (input.isKeyDown(Input.KEY_LCONTROL) || input.isKeyDown(Input.KEY_RCONTROL)) {

			if (input.isKeyPressed(Input.KEY_LEFT)) {
				currentMapIndex--;
				isMapChanged = true;
			}

			if (input.isKeyPressed(Input.KEY_RIGHT)) {
				currentMapIndex++;
				isMapChanged = true;
			}
		}
	}

	public void pauseGame(GameContainer cont, StateBasedGame state) throws SlickException {
		Image background = new Image(cont.getWidth(), cont.getHeight());
		cont.getGraphics().copyArea(background, 0, 0);

		map.enableTimer(false);

		PauseState pausedState = (PauseState) state.getState(Gemwars.PAUSE_GAME_STATE);

		pausedState.setBackground(background.copy());

		state.enterState(Gemwars.PAUSE_GAME_STATE);
	}

	private void resetGoal() {

		goalTimer.stop();
		goalTimer.reset();
		goalEndTimeStart = 0;
		goalBlackingAlpha = 0;

	}

	/**
	 * Checks for monster and player deaths and proceeds accordingly.
	 * 
	 * @param cont  game container
	 * @param state game state
	 * @param delta
	 * @throws SlickException
	 */
	private void checkDeaths(GameContainer cont, StateBasedGame state, int delta) throws SlickException {

		// are there dead monsters?
		for (Monster m : map.getMonsters()) {
			if (m.isDead()) {
				Log.debug("Removing dead monster at [" + m.positionX + "," + m.positionY + "]");
				map.drawDiamondMatrix(m.positionX, m.positionY);
			}
		}

		// player checks
		for (Player p : map.getPlayers()) {

			// if a monster kills a player, we do it here
			map.checkMonsterKills(p);

			// has the player died in some manner?
			if (p.isDead()) {
				if (deathTimer.isActive() == false) {
					map.drawDiamondMatrix(p.positionX, p.positionY);

					deathTimer.start();
				} else if (deathTimer.timeElapsedInMilliseconds() > 1500) {

					deathTimer.stop();
					deathTimer.reset();

					if (map.isAllPlayersDeath()) {
						state.enterState(Gemwars.GAMEOVERSTATE, new FadeOutTransition(), new FadeInTransition());
					} else {
						reloadMap(cont);
					}
				}
			}
		}
	}

	/**
	 * Reload map after player dies or player want to replay the game.
	 * 
	 * @param cont
	 * @throws SlickException
	 */
	public void reloadMap(GameContainer cont) throws SlickException {
		MapLoader loader = new MapLoader();
		Map tempMap = loader.loadMap(singlePlayerMaps.get(currentMapIndex), map.getPlayers());
		this.map = tempMap;
		tempMap.enter(cont);
	}

	/**
	 * Checks if a player has entered the goal
	 * 
	 * @param cont  game container
	 * @param state the state we are in
	 * @param delta
	 */
	private void checkGoal(GameContainer cont, StateBasedGame state, int delta) {
		for (Player p : map.getPlayers()) {
			// is the player in the goal?
			if (map.isGoalOpen()) {
				List<Point> goals = map.findItemPositions(map.getSpecialLayer(), ItemType.GOAL);
				Point point = goals.get(0);
				if (p.positionX == point.x && p.positionY == point.y) {

					map.goalAnimation();

					goalTimer.start();
					goaltime = map.getRemainingTime();

				}
			}
		}
	}

	private void drawUI(GameContainer cont, Graphics graph) throws SlickException {
		if (goalTimer.isActive()) {
			ui.render(cont, graph, goaltime, map.getPlayer(0).getScore(), map.getPlayer(0).getGems(), map.getGemCount(),
					map.getPlayer(0).getLives());
		} else {
			ui.render(cont, graph, map.getRemainingTime(), map.getPlayer(0).getScore(), map.getPlayer(0).getGems(),
					map.getGemCount(), map.getPlayer(0).getLives());
		}
	}

	@Override
	public int getID() {
		return stateID;
	}

	public Map getCurrentMap() {
		return map;
	}
}
