import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import game.controller.GuiGameController;
import game.controller.IfGameController;
import game.controller.TerminalGameController;
import game.model.GameModel;
import game.model.IfGameModel;
import game.view.GuiGameView;
import game.view.TerminalGameView;


public class Driver {
    public static void main(String[] args) {
        // Create an instance of the game model.
        IfGameModel gameModel = new GameModel("./res/mansion.txt");
        // Create an instance of the terminal view.
        TerminalGameView terminalView = getTerminalView4Test();
        // Bind game model and terminal view to terminal game controller.
        IfGameController terminalController = new TerminalGameController(gameModel, terminalView);

        // Create an instance of gui game controller
        GuiGameView guiView = new GuiGameView(gameModel);
        // Bind game model and gui view to gui game controller,
        // this game model is same to the model of the terminal game.
        IfGameController guiGameController = new GuiGameController(gameModel, guiView);

        // startGame
        try {
            terminalController.playGame();
            guiGameController.playGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static TerminalGameView getTerminalView4Test() {
        // Readable in = new InputStreamReader(System.in);
        // Appendable out = System.out;
        Reader in = new StringReader("50\n4\np1\n1\n1\nfalse\np2\n2\n2\nfalse\nr1\n5\n1\ntrue\n"
                + "r2\n6\n0\ntrue");

//        StringBuffer out = new StringBuffer();
        Appendable out = System.out;

        return new TerminalGameView(in, out);
    }

}
