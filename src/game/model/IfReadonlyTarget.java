package game.model;

/**
 * Read only interface for Target.
 */
public interface IfReadonlyTarget extends IfModel {
    /**
     * Get target's hp.
     *
     * @return HP value
     */
    int getHp();

    /**
     * Get target's name.
     *
     * @return target's name
     */
    String getName();

    /**
     * Get target's pet.
     *
     * @return IPet
     */
    IfPet getPet();

    /**
     * Get current target position.
     *
     * @return roomId
     */
    int getPosition();


    /**
     * Get current target's room.
     *
     * @return room
     */
    IfRoom getCurrentRoom();

}
