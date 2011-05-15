package gameobjects;

import gameobjects.map.ItemTypes;

import java.util.List;

/**
 * The bomb box. Not used at the moment.
 *
 */
public class Box extends Item {
	
	public Box() {
		super(ItemTypes.BOMB_BOX);
	}
	
	private List<Item> collectableItems;	
}
