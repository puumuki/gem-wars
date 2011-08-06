package gemwars.ui.components.menu;

import io.ResourceManager;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.GradientEffect;
import org.newdawn.slick.font.effects.OutlineEffect;
import gameobjects.IGameObject;

public class Menu implements IGameObject, Iterable<IMenuItem> {

	public int positionX;
	
	public int positionY;

	private int activeIndex = 0;
	
	private List<IMenuItem> menuitems = new ArrayList<IMenuItem>();
	
	private UnicodeFont font;
	
	private Sound sound;
	
	private String selector = ">>";
	
	public Menu() throws SlickException {	
		initFont();
		sound = ResourceManager.getInstance().getSound("MENU_SOUND");
	}

	private void initFont() throws SlickException {
		java.awt.Font awtFont = new java.awt.Font("Ariel", java.awt.Font.PLAIN, 30);
        font = new UnicodeFont(awtFont);
        font.addAsciiGlyphs();       
        
        java.awt.Color topColor = new java.awt.Color( 0xeeee00 );
        java.awt.Color bottomColor = new java.awt.Color( 0xbbff00 );
        
        OutlineEffect outlineEffect = new OutlineEffect(5, Color.black);
                
        font.getEffects().add(new GradientEffect(topColor, bottomColor, 1f));
        
        font.loadGlyphs();
	}
	
	public void setSelector(String selector) {
		this.selector = selector;
	}
	
	public void add( IMenuItem item ) {
		this.menuitems.add(item);		
		item.setFont(font);
		Collections.sort(menuitems);
	}	
	
	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		for( IMenuItem item : menuitems ) {
			item.update(cont, delta);
		}
		
		Input input = cont.getInput();
		
		menuitems.get(activeIndex).setActive(false);
		
		if( input.isKeyPressed(Input.KEY_DOWN)){
			if( activeIndex < menuitems.size() - 1 ) {
				activeIndex++;
			} else {
				activeIndex = 0;
			}
			
			sound.play();
		}
		
		if( input.isKeyPressed(Input.KEY_UP)) {
			if( activeIndex > 0) {
				activeIndex--;
			} else {
				activeIndex = menuitems.size() - 1;
			}
			
			sound.play();
		}
		
		menuitems.get(activeIndex).setActive(true);		
	}

	public int size() {
		return menuitems.size();
	}
	
	public int getActiveIndex() {
		return activeIndex;
	}
	
	public IMenuItem getMenuItem( int index ) {
		return menuitems.get(index);
	}
	
	@Override
	public void render(GameContainer cont, Graphics g) throws SlickException {		
		for( IMenuItem item : menuitems ) {
			item.render(cont, g);
		}
	}

	@Override
	public Iterator<IMenuItem> iterator() {
		return menuitems.iterator();
	}	
}
