package fi.gemwars.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.ResourceLoader;

import fi.gemwars.gameobjects.Boulder;
import fi.gemwars.gameobjects.Gem;
import fi.gemwars.gameobjects.Item;
import fi.gemwars.gameobjects.MagicWall;
import fi.gemwars.gameobjects.Monster;
import fi.gemwars.gameobjects.Player;
import fi.gemwars.gameobjects.map.ItemTypes;
import fi.gemwars.gameobjects.map.Layer;
import fi.gemwars.gameobjects.map.LayerTypes;
import fi.gemwars.gameobjects.map.Map;

/**
 * MapLoader load Gemwars special level file format.
 */
public class MapLoader {

	/**
	 * Load a specific map.
	 * @param file map file to load
	 * @return the new map.
	 * @throws IOException
	 * @throws SlickException
	 */
	public Map loadMap( File file ) throws IOException, SlickException {
		return loadMap(file, null);
	}

	/**
	 * Load a specific map with player information.
	 * @param file map file to load
	 * @param players links to the players (or null if no players)
	 * @return the new map
	 * @throws IOException
	 * @throws SlickException
	 */
	public Map loadMap(File file, List<Player> players) throws SlickException {
		
		Log.info("Loading level from a file : " + file.getName());

		Map map = null;

		if( file.isFile() == false ) {
			throw new SlickException("File not found ", new FileNotFoundException(file.getAbsolutePath()) );			
		}

		Scanner scanner = null;

		try {

			InputStream in = ResourceLoader.getResourceAsStream(file.toString());
			scanner = new Scanner(in);

			while (scanner.hasNextLine()) {
				
				// read the file one line at a time
				map = new Map();

				readMapBasicInformations(file, map, scanner);			

				// Then layers, one at a time
				for (int i = 0; i < 4; i++) {

					String layerName = scanner.nextLine();
					
					int layerWidth = readLayerSize(scanner);
					int layerHeight = readLayerSize(scanner);

					// a line for the tile filename (not very useful, redundant)
					String tileFilename = scanner.nextLine();

					// then we make a new layer from that information
					if(LayerTypes.valueOf(layerName) == LayerTypes.LAYER_COLLISION) {
						readCollisionLayer(map, scanner, layerWidth, layerHeight);
					}
					else {
						readMapLayer(map, scanner, layerName, layerWidth, layerHeight);
					}
				}//End of for

			}//End of while

			map.initPlayers(players);

		} catch (NoSuchElementException e) {
			throw new SlickException("Error loading map file", e);
		} catch (RuntimeException e) {
			throw new SlickException("Error loading map file", e); 
		} 
		finally {			
			scanner.close();			
		}

		return map;
	}
	
	/**
	 * Read layer size, basic layer size is 60 tile x 60 tile
	 * @param scanner
	 */
	private int readLayerSize( Scanner scanner ) {
		try {
			return Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			return 60;
		}
	}

	private void readMapBasicInformations(File file, Map map, Scanner scanner) {
		// set the filename
		map.setFilename(file.getName());

		// first the map name
		map.setName(scanner.nextLine());

		// creator name
		map.setCreator(scanner.nextLine());

		// gem count
		int gems = 0;
		try {
			gems = Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			gems = 0;
		}
		map.setGemCount(gems);

		// time
		int time = 60;
		try {
			time = Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			time = 60;
		}
		
		map.setTime(time); // TODO: take Options.gameSpeed into account!		
	}

	private void readMapLayer(Map map, Scanner scanner, String layerName,	int layerWidth, int layerHeight) throws SlickException {

		// other layers
		Layer l = new Layer(layerWidth, layerHeight, LayerTypes.valueOf(layerName));
		
		if (map.setLayer(l)) {

			for (int y = 0; y < layerHeight; y++) {
				StringBuffer line = new StringBuffer(scanner.nextLine());
				int start = 0, end = 0;
				for (int x = 0; x < layerWidth; x++) {
					end = line.indexOf(" ", start);
					if(end == -1)
						end = line.length();

					int item = 0;
					try {
						item = Integer.parseInt(line.substring(start, end));
					} catch (NumberFormatException e) {
						item = 0;
					}

					// Depending of item type we create different objects

					Item tile = null;

					if(item == ItemTypes.BLUE_GEM.ordinal() 
							|| item == ItemTypes.GREEN_GEM.ordinal() 
							|| item == ItemTypes.RED_GEM.ordinal() ) {

						tile = new Gem(ItemTypes.getType(item), map);
					}
					else if(item == ItemTypes.DARK_BOULDER.ordinal() 
							|| item == ItemTypes.WHITE_BOULDER.ordinal() ) {

						tile = new Boulder(ItemTypes.getType(item), map);
					}
					else if (item == ItemTypes.MAGIC_GREY_WALL.ordinal()) {
						tile = new MagicWall(ItemTypes.getType(item));
					}
					else if(item == ItemTypes.MONSTER.ordinal()) {
						map.add(new Monster(x, y, map));
					}
					else {
						tile = new Item(ItemTypes.getType(item));
					}

					if (tile != null) {
						tile.positionX = x;
						tile.positionY = y;

						l.setTile(x, y, tile);
					}

					// if we have a magic wall, we put it also in the special layer
					try {
						if (l.getType() == LayerTypes.LAYER_SPECIAL.ordinal() && map.getGroundLayer().getTile(x, y).itemType == ItemTypes.MAGIC_GREY_WALL) {
							l.setTile(x, y, map.getGroundLayer().getTile(x, y));
							//Log.debug("Setting a new magic wall on teh special layer at " +x+","+y);
						}
					}
					catch (IndexOutOfBoundsException ie) {
						Log.error("No ground layer under magic grey wall at " + x + "," + y);
					}

					start = end+1;
				}
			}
		}
	}

	private void readCollisionLayer(Map map, 
								  Scanner scanner,	
								  int layerWidth, 
								  int layerHeight) {
		
		// Collision layer is a special case
		map.createCollision(layerWidth, layerHeight);

		for (int y = 0; y < layerHeight; y++) {
			StringBuffer line = new StringBuffer(scanner.nextLine());
			int start = 0, end = 0;
			for (int x = 0; x < layerWidth; x++) {
				end = line.indexOf(" ", start);
				if(end == -1)
					end = line.length();

				int collision = 0;
				try {
					collision = Integer.parseInt(line.substring(start, end));
				} catch (NumberFormatException e) {
					collision = 0;
				}

				if(collision == 1) {
					map.setCollision(x, y, true);
				}

				start = end+1;

			}
		}
	}

	/**
	 * Finds all available map files from directory and it sub directories.
	 * Search is made recursively.
	 * 
	 * @param path Directory that where searching commit
	 * @return All files that name ends with ".gem"
	 */
	public static List<File> findAvailableMaps( File path ) {		
		return findAvailableMaps(path, "");
	}

	public static List<File> findAvailableMaps(File path, String pattern) {

		List<File> maps = new ArrayList<File>();

		searchRecursivelyForMapFiles( maps, path, pattern);

		Collections.sort(maps);

		return maps;
	}

	/**
	 * Does the "dirty work" to find map files in a directory (and its subdirectories).
	 * @param maps already known maps
	 * @param path map folder
	 * @param pattern regex pattern for the map filenames
	 */
	private static void searchRecursivelyForMapFiles(List<File> maps, File path, String pattern) {
		for( File file : path.listFiles() ) {

			if( file.isDirectory() ) {
				searchRecursivelyForMapFiles( maps, file, pattern );				
			}

			if( file.isFile() ) {
				String fileName = file.getName();

				if (fileName.matches(pattern)) {
					maps.add(file);
				}
			}
		}
	}

	/**
	 * Loads all single player maps in a certain directory (and recursively)
	 * @param path directory we are in
	 * @return list of maps
	 */
	public static List<File> loadSinglePlayerMaps(File path) {
		List<File> maps = new ArrayList<File>();

		searchRecursivelyForMapFiles( maps, path, "e[0-9]*l[0-9].gem");

		Collections.sort(maps);

		return maps;
	}

	/**
	 * Loads all multiplayer race maps in a certain directory (and recursively)
	 * @param path directory we are looking in
	 * @return list of maps
	 */
	public static List<File> loadMultiplayerRaceMaps(File path) {
		List<File> maps = new ArrayList<File>();

		searchRecursivelyForMapFiles( maps, path, "race[0-9]*.gem");

		Collections.sort(maps);

		return maps;
	}
}