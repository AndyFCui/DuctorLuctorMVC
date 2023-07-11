package game.model;

/**
 * GameModel interface.
 */
public interface IfGameModel extends IfReadonlyGameModel {
    /**
     * Set game init status.
     *
     * @param init status
     */
    void setInitStatus(boolean init);

    /**
     * Set world model.
     *
     * @param world IWorld
     */
    void setWorld(IfWorld world);

    /**
     * Get player of next turn, only this player can make an action.
     * This method should update next turn, which means if you call it twice,
     * you may get a different result.
     *
     * @return player who can make an action.
     */
    IfPlayer nextTurn();

    /**
     * Set game over status.
     * @param status the game status of over ot not.
     */
    void setGameOverStatus(boolean status);
}
