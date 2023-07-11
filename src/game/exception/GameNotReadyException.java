package game.exception;

public class GameNotReadyException extends Exception {
    private static final long serialVersionUID = 984987829144373643L;

    public GameNotReadyException(String message) {
        super(message);
    }
}
