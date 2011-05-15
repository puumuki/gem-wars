package gemwars.ui.components;

import gameobjects.IGameObject;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;

public class MenuItem implements Comparable<MenuItem>, IGameObject {
	
	/**
	 * This absolute position that is relative to game screen horizontally.
	 */
	public int positionX;
	
	/**
	 * This absolute position that is relative to game screen vertically. 
	 */
	public int positionY;
	
	private String text;

	private float value = 0;
	private float step = 0.0005f;
	private float maxValue = 1;
	private float minValue = 0;
	
	private boolean isActive;
	
	private int index;
	
	private UnicodeFont font;

	public MenuItem( int x, int y, String text ) {
		this.text = text;
		
		this.positionX = x;
		this.positionY = y;
		
		java.awt.Font awtFont = new java.awt.Font("Ariel", java.awt.Font.PLAIN, 20);
        font = new UnicodeFont(awtFont);        
	}	
	
	public void setFont(UnicodeFont font) {
		this.font = font;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public boolean getActive() {
		return isActive;
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	@Override
	public void update(GameContainer cont, int delta) throws SlickException {
		Input input = cont.getInput();
		
		if( isActive ) {
			
			if( input.isKeyDown(Input.KEY_LEFT) && value > minValue) {
				value -= step * delta;								
			}
			
			if( input.isKeyDown(Input.KEY_RIGHT) && value < maxValue ) {
				System.out.println( text );
				
				value += step * delta;
			}
			
			if( value > maxValue ) {
				value = maxValue;
			}
			
			if( value < minValue ) {
				value = minValue;
			}
		}
	}

	@Override
	public void render(GameContainer cont, Graphics g) throws SlickException {		
		
		String textValue = text + " " + Math.round(value * 100) + "%";
		
		if( isActive ) {
			font.drawString(positionX, positionY, ">> " +  textValue);
		}
		else {
			font.drawString(positionX, positionY, textValue  );
		}
	}
	

	@Override
	public int compareTo(MenuItem item) {
		
		if(item.getIndex() > index ) {
			return 1;
		}
		else if( item.getIndex() < index ) {
			return -1;
		}
		
		return 0;
	}
}
