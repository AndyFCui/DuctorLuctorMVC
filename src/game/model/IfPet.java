package game.model;

/**
 * Pet interface.
 */
public interface IfPet extends IfReadonlyPet {

    /**
     * Move to new room
     *
     * @param room IfRoom
     */
    void move(IfRoom room);

    /**
     * Set current room of the pet.
     *
     * @param currentRoom IRoom
     */
    void setCurrentRoom(IfRoom currentRoom);
}
