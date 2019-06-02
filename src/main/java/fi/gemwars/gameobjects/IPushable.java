package fi.gemwars.gameobjects;

public interface IPushable extends IDynamic {
    @Override
    public default boolean isPushable() {
        return false;
    }

    /**
     * Pushes the object in the wanted direction
     * 
     * @param direction The direction we want to push it to
     */
    public void push(Direction direction);
}