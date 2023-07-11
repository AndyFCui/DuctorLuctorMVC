package game.exception;

/**
 * If the world model could not be parsed correctly
 * or has something wrong so that the game can not be continued.
 */
public class WorldModelParseException extends GameNotReadyException {
    private static final long serialVersionUID = -3378858432427265650L;

    public WorldModelParseException(String message) {
        super(message);
    }
}
