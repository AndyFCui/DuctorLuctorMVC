package game.view.gui;

import javax.swing.*;


/**
 * Singleton.
 */
public class MenuBar extends JMenuBar {
    private static MenuBar menuBar;
    private JMenuItem newGameWithNewFileMenuItem;
    private JMenuItem newGameWithCurrentFileMenuItem;
    private JMenuItem quitGameMenuItem;

    public MenuBar() {
        this.add(createOptionMenu());
        this.setVisible(true);
    }

    private JMenu createOptionMenu() {
        final JMenu menu = new JMenu("Option");
        this.newGameWithNewFileMenuItem = new JMenuItem("New Game with New World Specification");
        this.newGameWithCurrentFileMenuItem = new JMenuItem("New Game with Current "
                + "World Specification");
        this.quitGameMenuItem = new JMenuItem("Quit");

        menu.add(this.newGameWithNewFileMenuItem);
        menu.addSeparator();
        menu.add(this.newGameWithCurrentFileMenuItem);
        menu.addSeparator();
        menu.add(this.quitGameMenuItem);
        return menu;
    }

    public JMenuItem getNewGameWithNewFileMenuItem() {
        return this.newGameWithNewFileMenuItem;
    }

    public JMenuItem getNewGameWithCurrentFileMenuItem() {
        return this.newGameWithCurrentFileMenuItem;
    }

    public JMenuItem getQuitGameMenuItem() {
        return this.quitGameMenuItem;
    }
}
