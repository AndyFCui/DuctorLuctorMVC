package game.exception;

/**
 * Reach player's max carry
 */
public class CarryItemFullException extends Exception {
    private static final long serialVersionUID = -6183033016909258394L;

    public CarryItemFullException() {
    }

    public CarryItemFullException(String message) {
        super(message);
    }
}
