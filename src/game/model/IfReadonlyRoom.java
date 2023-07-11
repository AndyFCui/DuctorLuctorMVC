package game.model;

import java.awt.*;
import java.util.List;

/**
 * Read only interface for Room.
 */
public interface IfReadonlyRoom extends IfModel {

    /**
     * Get players in this room.
     *
     * @return players list, could be empty, but will never be null.
     */
    List<IfPlayer> getPlayers();

    /**
     * Get room's name.
     *
     * @return room's name
     */
    String getName();

    /**
     * Get target of the room ( if has ).
     *
     * @return target ITarget or null
     */
    IfTarget getTarget();

    /**
     * Get pet of the room ( if has ).
     *
     * @return pet IPet or null
     */
    IfPet getPet();

    boolean hasPet();

    int getRoomId();

    Rectangle getRect();

    List<IfItem> getItems();

    /**
     * Room's description
     *
     * @return String
     */
    String getDescription();
}
