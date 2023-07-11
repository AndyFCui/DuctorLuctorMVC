package game.model;

/**
 * This is interface for Item of world.
 */
public interface IfItem extends IfReadonlyItem {

    /**
     * Set item's current room.
     *
     * @param currentRoom current Room
     */
    void setCurrentRoom(IfRoom currentRoom);
}
