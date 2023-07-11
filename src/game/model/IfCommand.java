package game.model;

/**
 * Interface command which made by user.
 * User could send command by using terminal input or gui click or something else.
 */
public interface IfCommand {
    void execute(IfGameModel model);
}
