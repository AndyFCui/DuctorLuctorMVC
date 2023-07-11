package game.model;

import game.exception.CannotMoveException;
import game.exception.CarryItemFullException;

/**
 * This is the Interface of the Player.
 */
public interface IfPlayer extends IfReadonlyPlayer {

    void setName(String name);

    void setIsRobot(boolean isRobot);

    void setMaxCarry(int maxCarry);

    /**
     * Player pick item.
     *
     * @param item item
     */
    void pickItem(IfItem item) throws CarryItemFullException;

    /**
     * Move current player to other room.
     *
     * @param room IRoom
     * @throws CannotMoveException move fail
     */
    void moveTo(IfRoom room) throws CannotMoveException;

    /**
     * Attack.
     *
     * @param item        weapon, could be null if attack by hand
     * @param targetModel ITarget
     * @return target die or not
     */
    boolean attack(IfItem item, IfTarget targetModel);
}
