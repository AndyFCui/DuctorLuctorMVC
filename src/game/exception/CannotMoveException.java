package game.exception;

public class CannotMoveException extends Exception {
    private static final long serialVersionUID = 2242669572376999193L;

    public CannotMoveException() {
    }

    public CannotMoveException(String message) {
        super(message);
    }
}
