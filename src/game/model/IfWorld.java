package game.model;

import game.exception.WorldModelParseException;

/**
 * World interface.
 */
public interface IfWorld extends IfReadonlyWorld {

    /**
     * Set world name.
     *
     * @param name world's name
     */
    void setName(String name);

    /**
     * Check if room1 and room2 are neighbor.
     *
     * @param room1 IRoom
     * @param room2 IRoom
     * @return true or false
     */
    boolean isNeighbor(IfRoom room1, IfRoom room2);

    /**
     * Could init from mock data, text file or other.
     */
    void initWorld() throws WorldModelParseException;

    void setMaxTurns(int maxTurns);

    void addPlayers(IfPlayer... player);

    void setTarget(IfTarget target);
}
