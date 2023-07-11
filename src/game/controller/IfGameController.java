package game.controller;

public interface IfGameController {

    /**
     * Everything is OK, game begin.
     *
     * @throws Exception Maybe the model has not be initialized or IOException.
     */
    void playGame() throws Exception;

}
