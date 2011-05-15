package gemwars.ui.components;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.GradientEffect;
import org.newdawn.slick.font.effects.OutlineEffect;
import gameobjects.IGameObject;

public class Menu implements IGameObject {

	public int positionX;
	
	public int positionY;

	private int activeIndex = 0;
	
	private List<MenuItem> menuitems = new ArrayList<MenuItem>();
	
	private UnicodeFont font;
	
	public Menu() throws SlickException {	
		initFont();        
	}

	private void initFont() throws SlickException {
		java.awt.Font awtFont = new java.awt.Font("Ariel", java.awt.Font.PLAIN, 30);
        font = new UnicodeFont(awtFont);
        font.addAsciiGlyphs();       
        
        java.awt.Color topColor = new java.awt.Color( 0xeeee00 );
        java.awt.Color bottomColor = new java.awt.Color( 0xbbff00 );
        
        OutlineEffect outlineEffect = new OutlineEffect(5, Color.black);
        
        font.getEffects().add(outlineEffect);
        font.getEffects().add(new GradientEffect(topColor, bottomColor, 1f));
        font.loadGlyphs();
	}
	
	public void add( MenuItem item ) {
		this.menuitems.add(item);		
		item.setFont(font);
		Collections.sort(menuitems);
	}	
	
	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		for( MenuItem item : menuitems ) {
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
		}
		
		if( input.isKeyPressed(Input.KEY_UP)) {
			if( activeIndex > 0) {
				activeIndex--;
			} else {
				activeIndex = menuitems.size() - 1;
			}			
		}
		
		menuitems.get(activeIndex).setActive(true);		
	}

	@Override
	public void render(GameContainer cont, Graphics g) throws SlickException {		
		for( MenuItem item : menuitems ) {
			item.render(cont, g);
		}
	}	
}
