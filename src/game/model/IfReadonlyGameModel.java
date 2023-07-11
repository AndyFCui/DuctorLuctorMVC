package game.model;

/**
 * Readonly for game model.
 */
public interface IfReadonlyGameModel extends IfModel {
    /**
     * Check if this game model has been initialized.
     *
     * @return true or false.
     */
    boolean alreadyInit();

    /**
     * Get world model from this model.
     *
     * @return IWorld
     */
    IfWorld getWorld();

    /**
     * Get current turn num.
     *
     * @return the current turn
     */
    int getCurrentTurn();

    /**
     * Get player of current turn.
     *
     * @return IPlayer
     */
    IfPlayer getTurnPlayer();

    /**
     * Get game over status.
     *
     * @return true or false
     */
    boolean getGameOverStatus();
}
