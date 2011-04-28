package gameobjects.map;

public enum LayerTypes {
	
	GROUND(0),
	OBJECTS(1),
	SPECIAL(2),
	COLLISION(3);
	
	private final int layerIndex;
	
	LayerTypes (int index) {
		layerIndex = index;
	}
	
	public int layerIndex() {
		return layerIndex;
	}
}
