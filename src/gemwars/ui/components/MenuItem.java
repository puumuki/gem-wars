package gemwars.ui.components;

import org.newdawn.slick.Renderable;

public class MenuItem implements Comparable<MenuItem> {
	
	/**
	 * This absolute position that is relative to game screen horizontally.
	 */
	public int positionX;
	
	/**
	 * This absolute position that is relative to game screen vertically. 
	 */
	public int positionY;
	
	private String text;

	private int index;

	public MenuItem( int x, int y, String text ) {
		this.text = text;
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
