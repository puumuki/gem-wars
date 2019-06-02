package fi.gemwars.gameobjects.map;

/**
 * Enum for the four different layer types used in the game, from the lowest
 * layer to the highest one.
 * 
 * The collision layer is treated separately in the game though.
 *
 */

public enum LayerType {
	LAYER_GROUND(0), LAYER_OBJECTS(1), LAYER_SPECIAL(2), LAYER_COLLISION(3);

	private final int layerIndex;

	LayerType(int index) {
		layerIndex = index;
	}

	public int layerIndex() {
		return layerIndex;
	}
}
