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
	
	private int width;
	private int height;
	
	private String name;
	private String creator;
	private int gemCount;
	private int time;
	
	private boolean[][] collisionLayer;
	
	private Layer groundLayer;
	private Layer specialLayer;
	private Layer objectLayer;
	
	@Override
	public void render(GameContainer cont, Graphics grap) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public void setGemCount(int gems) {
		this.gemCount = gems;
	}
	
	public int getGemCount() {
		return gemCount;
	}

	public void createCollision(int layerWidth, int layerHeight) {
		this.collisionLayer = new boolean[layerHeight][layerWidth];
		
	}

	public void setCollision(int x, int y, boolean b) {
		collisionLayer[y][x] = b;
	}

	public boolean setLayer(Layer l) {
		if (l.getType() == LayerTypes.LAYER_GROUND.ordinal()) {
			groundLayer = l;
			return true;
		}
		if (l.getType() == LayerTypes.LAYER_OBJECTS.ordinal()) {
			objectLayer = l;
			return true;
		}
		if (l.getType() == LayerTypes.LAYER_SPECIAL.ordinal()) {
			specialLayer = l;
			return true;
		}
		return false;
		
	}
}
