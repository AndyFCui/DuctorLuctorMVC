package game.view;

import java.io.IOException;
import java.util.Scanner;

import game.model.IfCommand;

/**
 * Implement Game view interface.
 */
public class TerminalGameView implements IfGameView {
    private Readable in;
    private Appendable out;
    private Scanner scanner;

    /**
     * Constructor of TerminalGameView.
     *
     * @param in  Readable
     * @param out Appendable
     */
    public TerminalGameView(Readable in, Appendable out) {
        this.in = in;
        this.out = out;
        this.scanner = new Scanner(in);
    }

    public void output(String result) throws IOException {
        out.append(result);
    }

    public String input() {
        return scanner.nextLine();
    }

    @Override
    public IfCommand receiveCommand() {
        String userInput = scanner.nextLine();
        return null;
    }

    @Override
    public void showResult(IfCommand command) {

    }
}
