package gameobjects.map;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import gameobjects.AEntity;
import gameobjects.Item;

public class Layer extends AEntity {

	private int width = 0;
	
	private int height = 0;
	
	private Item[][] tiles;

	private LayerTypes layerType;
	
	public Layer( int width, int height , LayerTypes type ) {
		
		this.width = width;
		this.height = height;
		
		this.layerType = type;
		
		
		tiles = new Item[width][height];
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {				
				tiles[x][y] = new Item(ItemTypes.EMPTY);				
			}
		}
	}
	
	public void setTile( int posX, int posY, Item tile ) {
		tiles[posX][posY] = tile;
	}
	
	@Override
	public void render(GameContainer cont, Graphics grap) throws SlickException {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				tiles[x][y].render(cont, grap);
			}
		}
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	public int getType() {
		return layerType.layerIndex();
	}

	
	
}