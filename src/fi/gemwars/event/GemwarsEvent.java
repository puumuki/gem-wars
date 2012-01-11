package fi.gemwars.event;

public class GemwarsEvent {
	
	private GemwarsEventType type;

	public GemwarsEvent(GemwarsEventType type) {
		super();
		this.type = type;
	}

	public GemwarsEventType getType() {
		return type;
	}
}
