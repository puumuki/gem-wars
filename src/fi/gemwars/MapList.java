package fi.gemwars;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.font.effects.OutlineEffect;

import fi.gemwars.gameobjects.AEntity;
import fi.gemwars.io.MapLoader;
import fi.gemwars.ui.components.menu.FileMenuItem;
import fi.gemwars.ui.components.menu.Menu;

/**
 * MapList draws nice maps selection list
 */
public class MapList extends AEntity {
	
	/**
	 * Other components that are listening the maps changing
	 */
	private List<ActionListener> eventListeners = new ArrayList<ActionListener>();
	
	/**
	 * All single player maps
	 */
	private List<File> singlePlayerMaps;
	
	private UnicodeFont font;
	
	/**
	 * Basic menu element
	 */
	private Menu menu;
	
	/**
	 * Vertical cap between items, margin
	 */
	private int itemCap;
	
	/**
	 * Last selected map file
	 */
	private File chosenMapFile = null;
	
	/**
	 * Menu background color
	 */
	private Color menuBackgroundColor = new Color(1f, 1f, 1f, 0.8f);
	
	public MapList( int posX, int posY ) throws SlickException {
		        
		this.positionX = posX;
		this.positionY = posY;
		
		itemCap = 20;
		this.hide = true;
		
        findMapFiles();		
		initFont();		
		initMenu();
	}

	private void initMenu() throws SlickException {
		menu = new Menu();
		
		int posX = this.positionX;
		int posY = this.positionY;
		
		for( File file : singlePlayerMaps ) {	
			
			FileMenuItem menuItem = new FileMenuItem(posX, posY, file.getName(), file);			
			menu.add( file.getName(), menuItem);
			
			posY += itemCap;
		}
		
		menu.setFont(font);
	}
	
	public void findMapFiles() {
		File file = new File("src/resources/maps/");
		
		if( file.exists() == false ) {
			file = new File("resources/maps/");
		}
		
		singlePlayerMaps =  MapLoader.loadSinglePlayerMaps(file);
	}
	
	private void initFont() throws SlickException {
		java.awt.Font awtFont = new java.awt.Font("Verdana", java.awt.Font.PLAIN, 16);
        
		font = new UnicodeFont(awtFont);
		
        font.addAsciiGlyphs();
        
        java.awt.Color colorEffect = new java.awt.Color(0, 0, 0);
        
        font.getEffects().add(new ColorEffect(colorEffect));        
        font.loadGlyphs();        
	}
	
	public void addMapChosenEventListener( ActionListener eventListener ) {
		eventListeners.add(eventListener);
	}
	
	/**
	 * Alarms the eventlisteners that user has chosen a new map from list
	 */
	private void fireMapIsChosenEvent( String mapName ) {
		
		for( ActionListener listener : eventListeners ) {
			listener.actionPerformed( new ActionEvent(this, 0, mapName ));
		}
		
		//Indicates that menu is hidden and not drawn an not updated
		this.hide = true;
	}
		

	
	@Override
	public void render(GameContainer cont, Graphics grap) throws SlickException {
		if(!hide) {			
			grap.setColor( menuBackgroundColor );			
			grap.fillRect(positionX - 10, positionY - 10, 200, menu.size() * itemCap + 20);
			
			menu.render(cont, grap);
		}
	}

	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		menu.update(cont, delta);
		
		Input input = cont.getInput(); 
		
		if(input.isKeyPressed(Input.KEY_ESCAPE)) {
			this.hide = true;
		}
		
		if( input.isKeyPressed(Input.KEY_ENTER)) {
			FileMenuItem fileItem =  (FileMenuItem)menu.getMenuItem(menu.getActiveIndex());			
			fireMapIsChosenEvent( fileItem.getFile().getName() );
		}
	}
	
	public int getItemCap() {
		return itemCap;
	}
	
	public void setItemCap(int itemCap) {
		this.itemCap = itemCap;
	}
	
	public File getChosenMapFile() {
		return chosenMapFile;
	}
}
