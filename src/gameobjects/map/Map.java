package gameobjects.map;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.ResourceLoader;

import gameobjects.AEntity;

public class Map extends AEntity {

	private List<AEntity>gameObjects;
	
	private static final int MAX_WIDTH = 1024;
	private static final int MAX_HEIGHT = 1024;
	
	private String name;
	private String creator;
	private int gemCount;
	private int time;
	
	public void loadMap(String mapName) throws SlickException {
		Scanner f = null;
		
		try {
			InputStream in = ResourceLoader.getResourceAsStream("resources/maps/" + mapName);
			f = new Scanner(in);
			
			while (f.hasNextLine()) {
				// read the file one line at a time
				
				// first the map name
				name = f.nextLine();
				
				// creator name
				creator = f.nextLine();
				
				// gem count
				int gems = 0;
				try {
					gems = Integer.parseInt(f.nextLine());
				} catch (NumberFormatException e) {
					gems = 0;
				}
				gemCount = gems;
				
				// time
				int time = 60;
				try {
					time = Integer.parseInt(f.nextLine());
				} catch (NumberFormatException e) {
					time = 60;
				}
				this.time = time;
				
				// TODO: Then layers, one at a time
				// also: what to do, if map is incomplete? 
			}
			
			
		} catch (RuntimeException e) {
			throw new SlickException("Error loading map file", e); 
		}
		finally {
			if (f != null)
				f.close();
		}
		
	}
	
	@Override
	public void render(GameContainer cont, Graphics grap) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
	}
	
	
}
