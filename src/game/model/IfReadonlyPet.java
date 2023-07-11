package game.model;

/**
 * Read only interface for pet.
 */
public interface IfReadonlyPet extends IfModel {
    /**
     * Get pet's name
     *
     * @return name
     */
    String getName();

    /**
     * Get pet's position, namely room id
     *
     * @return room id
     */
    int getPosition();

    /**
     * Get pet current room
     *
     * @return room
     */
    IfRoom getCurrentRoom();
}
