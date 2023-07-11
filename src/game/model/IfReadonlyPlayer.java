package game.model;

import java.util.List;

/**
 * ReadOnly interface for player.
 */
public interface IfReadonlyPlayer extends IfModel {
    String getName();

    IfRoom getCurrentRoom();

    boolean isRobot();

    int getMaxCarry();

    /**
     * Get description of the player, could be used for printing.
     *
     * @return string
     */
    String getDescription();

    List<IfItem> getCarryItems();
}
