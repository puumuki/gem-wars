package fi.gemwars.event;

/**
 * Listener interface that defines Gewmars game event listener.
 * Implements the observer pattern.
 */
public interface GemwarsListener {
	public void gemwarsEventPerformed( GemwarsEvent event );
}
