package game.model;

import java.util.List;

/**
 * Read only interface for world.
 */
public interface IfReadonlyWorld extends IfModel {
    /**
     * If world has been init.
     *
     * @return boolean
     */
    boolean hasInit();

    int getMaxTurns();

    /**
     * Get rows.
     *
     * @return rows
     */
    int getRows();

    /**
     * Get columns.
     *
     * @return columns
     */
    int getColumns();

    /**
     * Get world name.
     *
     * @return world's name.
     */
    String getName();

    /**
     * Get all players in the world.
     *
     * @return All players in the world.
     */
    List<IfPlayer> getPlayers();

    List<IfRoom> getRooms();

    IfRoom getRoomByRoomId(int roomId);

    /**
     * It' common used for retrieving "writable" IRoom from "read only" IRoom.
     *
     * @param readonlyRoom IReadonlyRoom
     * @return "writable" IRoom
     */
    IfRoom getRoom(IfReadonlyRoom readonlyRoom);

    /**
     * Get target of the world.
     *
     * @return Target
     */
    IfTarget getTarget();

    /**
     * Get pet of the world.
     *
     * @return Pet
     */
    IfPet getPet();

    /**
     * Could be used for setting player position.
     *
     * @return All rooms'id in this world
     */
    List<Integer> getRoomIds();

    /**
     * Get the neighbours of current position.
     *
     * @param roomId the room index of position.
     * @return the neighbours of Room list
     */
    List<IfRoom> getNeighbours(int roomId);

}
