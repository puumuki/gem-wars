package fi.gemwars.gameobjects.map;

/**
 * Enum for the different item types available to create the levels.
 *
 */
public enum ItemTypes {

	EMPTY(0), 
 	RED_GEM(1),
 	SAND(2),
 	YELLOW_WALL(3),
 	BLUE_GEM(4),
 	WHITE_BOULDER(5),
 	GROUND(6),
 	GREEN_GEM(7),
 	DARK_BOULDER(8),
	BUTTON(9),
 	MAGIC_GREY_WALL(10),
 	METAL_WALL(11),
 	RED_WALL(12),
 	GOAL_TILE(13),
 	BOMB_BOX(14),
 	BROWN_WALL(15),
 	START(16),
 	GOAL(17),
	MONSTER(18),
 	UNUSED(19);
	
	private final int textureIndex;
	
	ItemTypes(int index) {
		this.textureIndex = index;
	}

	public int textureIndex() {
		return textureIndex;
	}
	
	public static ItemTypes getType( int typeNumber ) {
		ItemTypes type = ItemTypes.EMPTY;
		
		for (ItemTypes element : values()) {
			if( element.ordinal() == typeNumber ) {
				type = element;
				break;
			}
		}
		
		return type;
	}
	
	public static boolean isGem( ItemTypes type ) {
		if( type == ItemTypes.RED_GEM 
			|| type == ItemTypes.GREEN_GEM 
			|| type == ItemTypes.BLUE_GEM) {
			return true;
		}
		
		return false;
	}
}
