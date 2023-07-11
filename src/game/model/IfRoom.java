package game.model;


import java.util.List;

/**
 * Interface of Room.
 */
public interface IfRoom extends IfReadonlyRoom {

    /**
     * Add target to this room.
     * The world has only one target, which means only one room has target.
     *
     * @param target ITarget
     */
    void setTarget(IfTarget target);

    /**
     * Remove the target from this room if has.
     */
    void removeTarget();

    /**
     * Add pet to this room.
     * The world has only one pet, which means only one room has pet.
     *
     * @param pet IfPet
     */
    void setPet(IfPet pet);

    /**
     * Remove the pet from this room if has.
     */
    void removePet();


    /**
     * Set items for this room.
     *
     * @param items IItem list
     */
    void setItems(List<IfItem> items);

    /**
     * Add item to this room.
     *
     * @param item IItem
     */
    void addItem(IfItem item);

    /**
     * Remove item from this room.
     *
     * @param item IItem
     */
    void removeItem(IfItem item);

    /**
     * Add players to this room.
     *
     * @param players IPlayer
     */
    void addPlayer(IfPlayer... players);

    /**
     * Remove the player from this room.
     *
     * @param player IPlayer
     */
    void removePlayer(IfPlayer player);
}
