package fi.gemwars.event;

/**
 * GemwarsEvent is created when a something happens, like a gem is collected.
 * All classes that implements GemwarsEventListener interface are informed happening event
 * by instantiating GemwarsEvent object and passing it to eventListener. It is a part of Gemwars
 * logic that implements the observer pattern.
 */
public class GemwarsEvent {
	
	/**
	 * Gemwars event type
	 */
	private GemwarsEventType type;

	public GemwarsEvent(GemwarsEventType type) {
		this.type = type;
	}

	public GemwarsEventType getType() {
		return type;
	}
}
