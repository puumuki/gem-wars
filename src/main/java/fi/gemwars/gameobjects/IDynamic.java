package fi.gemwars.gameobjects;

public interface IDynamic {
	/**
	 * Is this object pushable
	 * 
	 * @return true if it is
	 */
	public boolean isPushable();

	/**
	 * Is this object affected by physics (=gravity)
	 * 
	 * @return true if it is
	 */
	public boolean isPhysicsAffected();
}
