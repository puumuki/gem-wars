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

import gameobjects.Item;
import gameobjects.map.ItemTypes;
import gameobjects.map.Layer;
import gameobjects.map.LayerTypes;
import gameobjects.map.Map;

public class MapLoader {
	
	public static Map loadMap( File file ) throws IOException, SlickException {
		
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
				map.setTime(time);
				
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
									
									Item tile = new Item(ItemTypes.getType(item));
									
									tile.positionX = x;
									tile.positionY = y;
									
									l.setTile(x, y, tile);

									start = end+1;
								}
							}
						}
						
						//TODO: Throw SlickException :)
						// else throw slickexception?
					}
				}
				
			}
		
			map.initPlayers();
			
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
