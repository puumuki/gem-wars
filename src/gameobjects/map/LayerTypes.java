package gameobjects.map;

public enum LayerTypes {
	
	LAYER_GROUND(0),
	LAYER_OBJECTS(1),
	LAYER_SPECIAL(2),
	LAYER_COLLISION(3);
	
	private final int layerIndex;
	
	LayerTypes (int index) {
		layerIndex = index;
	}
	
	public int layerIndex() {
		return layerIndex;
	}
}
