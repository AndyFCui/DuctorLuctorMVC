package game.view;

import game.model.IfCommand;

/**
 * The interface of Game view.
 */
public interface IfGameView {

    IfCommand receiveCommand();

    void showResult(IfCommand command);
}
