package game.model;

/**
 * Interface for target.
 */
public interface IfTarget extends IfReadonlyTarget {
    /**
     * Set pet
     *
     * @param pet IfPet
     */
    void setPet(IfPet pet);

    /**
     * Set current room of the target.
     *
     * @param currentRoom IRoom
     */
    void setCurrentRoom(IfRoom currentRoom);

    /**
     * Move to new room
     *
     * @param room IfRoom
     */
    void move(IfRoom room);

    /**
     * Get the attack value after attack.
     *
     * @param damage damage value
     * @return HP after damage, negative if die
     */
    int beAttacked(int damage);
}
