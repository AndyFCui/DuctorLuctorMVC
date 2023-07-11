package game.model;

/**
 * concrete class implement game model.
 */
public class GameModel implements IfGameModel {
    private boolean init;
    private IfWorld world;
    private IfPlayer turn;
    private int turnNum;
    private boolean isGameOver;

    /**
     * Constructor of GameModel.
     *
     * @param filePath World specification text file path
     */
    public GameModel(String filePath) {
        this.world = new World(filePath);
        this.init = false;
    }

    /**
     * Constructor of GameModel.
     *
     * @param world IWorld
     */
    public GameModel(IfWorld world) {
        this.world = world;
        this.init = false;
    }

    @Override
    public boolean alreadyInit() {
        return this.init;
    }

    @Override
    public IfWorld getWorld() {
        return this.world;
    }

    @Override
    public int getCurrentTurn() {
        return this.turnNum;
    }

    @Override
    public IfPlayer getTurnPlayer() {
        return this.turn;
    }

    @Override
    public boolean getGameOverStatus() {
        return this.isGameOver;
    }

    @Override
    public IfPlayer nextTurn() {
        int playerIndex = 0;
        if (this.turn == null) {
            // make first player in the list play first
            this.turnNum = 1;
        } else {
            this.turnNum++;
            playerIndex = (this.turnNum - 1) % (this.world.getPlayers().size());
        }
        this.turn = this.world.getPlayers().get(playerIndex);
        return this.turn;
    }

    @Override
    public void setGameOverStatus(boolean status) {
        this.isGameOver = status;
    }

    @Override
    public void setInitStatus(boolean init) {
        this.init = init;
    }

    @Override
    public void setWorld(IfWorld world) {
        this.world = world;
    }
}
