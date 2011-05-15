package io;

import java.io.File;
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

import gameobjects.Boulder;
import gameobjects.Gem;
import gameobjects.Item;
import gameobjects.MagicWall;
import gameobjects.Monster;
import gameobjects.Player;
import gameobjects.map.ItemTypes;
import gameobjects.map.Layer;
import gameobjects.map.LayerTypes;
import gameobjects.map.Map;

/**
 * This can be used to load maps.
 *
 */
public class MapLoader {
	
	/**
	 * Load a specific map.
	 * @param file map file to load
	 * @return the new map.
	 * @throws IOException
	 * @throws SlickException
	 */
	public static Map loadMap( File file ) throws IOException, SlickException {
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
	public static Map loadMap(File file, List<Player> players) throws IOException, SlickException {
		Log.info("Loading level from a file : " + file.getName());
		
		Map map = null;
		
		if( file.isFile() == false ) {
			throw new IOException("Given file is not file.");
		}
		Scanner f = null;
		
		try {
			InputStream in = ResourceLoader.getResourceAsStream(file.toString());
			f = new Scanner(in);
			
			while (f.hasNextLine()) {
				// read the file one line at a time
			
				// we make the new map
				map = new Map();
				
				// set the filename
				map.setFilename(file.getName());
				
				// first the map name
				map.setName(f.nextLine());
				
				// creator name
				map.setCreator(f.nextLine());
				
				// gem count
				int gems = 0;
				try {
					gems = Integer.parseInt(f.nextLine());
				} catch (NumberFormatException e) {
					gems = 0;
				}
				map.setGemCount(gems);
				
				// time
				int time = 60;
				try {
					time = Integer.parseInt(f.nextLine());
				} catch (NumberFormatException e) {
					time = 60;
				}
				map.setTime(time); // TODO: take Options.gameSpeed into account!
				
				// Then layers, one at a time
				for (int i = 0; i < 4; i++) {
					
					// layer name
					String layerName = f.nextLine();
					
					// Layer width
					int layerWidth = 0;
					try {
						layerWidth = Integer.parseInt(f.nextLine());
					} catch (NumberFormatException e) {
						layerWidth = 60;
					}
					
					// layer height
					int layerHeight = 0;
					try {
						layerHeight = Integer.parseInt(f.nextLine());
					} catch (NumberFormatException e) {
						layerHeight = 60;
					}
					
					// a line for the tile filename (not very useful)
					String tileFilename = f.nextLine();
					
					// then we make a new layer from that information
					
					if(LayerTypes.valueOf(layerName) == LayerTypes.LAYER_COLLISION) {
						// Collision layer is a special case
						map.createCollision(layerWidth, layerHeight);
						
						for (int y = 0; y < layerHeight; y++) {
							StringBuffer line = new StringBuffer(f.nextLine());
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
					else
					{
						// other layers
						Layer l = new Layer(layerWidth, layerHeight, LayerTypes.valueOf(layerName));
						if (map.setLayer(l)) {
							
							for (int y = 0; y < layerHeight; y++) {
								StringBuffer line = new StringBuffer(f.nextLine());
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

									start = end+1;
								}
							}
						}
						
						//TODO: Throw SlickException :)
						// else throw slickexception?
					}
				}
				
			}
			
			map.initPlayers(players);
			
		} catch (NoSuchElementException e) {
			throw new SlickException("Error loading map file", e);
		} catch (RuntimeException e) {
			throw new SlickException("Error loading map file", e); 
		} 
		finally {
			if (f != null)
				f.close();
		}
		
		
		
		return map;
		
	}
	
	/**
	 * Finds all available map files from directory and it sub directories.
	 * Search is made recursively.
	 * 
	 * @param path Directory that where searching commit
	 * @return All files that name ends with ".gem"
	 */
	public static List<File> findAvailableMaps( File path ) {		
		
		List<File> maps = new ArrayList<File>();
		
		searchRecursivelyForMapFiles( maps, path );
		
		Collections.sort(maps);
		
		return maps;
	}
	
	/**
	 * Does the "dirty work" to find map files in a directory (and its subdirectories).
	 * @param maps already known maps
	 * @param path map folder
	 */
	private static void searchRecursivelyForMapFiles( List<File>maps, File path ) {
		for( File file : path.listFiles() ) {
			
			if( file.isDirectory() ) {
				searchRecursivelyForMapFiles( maps, file );				
			}
			
			if( file.isFile() ) {
				String fileName = file.getName();
				
				
				//TODO: Should we have some kind validation for the map format?
				if( fileName.endsWith(".gem") ) {
					maps.add(file);
				}
			}
		}
	}
}
