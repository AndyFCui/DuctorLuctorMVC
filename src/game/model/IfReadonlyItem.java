package game.model;

/**
 * Read only interface for item.
 */
public interface IfReadonlyItem extends IfModel {
    /**
     * Get item's name.
     *
     * @return item's name
     */
    String getName();

    /**
     * Get item current room.
     *
     * @return IRoom current room
     */
    IfRoom getCurrentRoom();

    /**
     * The getter of item's damage.
     *
     * @return the value of item's attack
     */
    int getAmountOfDamage();
}
