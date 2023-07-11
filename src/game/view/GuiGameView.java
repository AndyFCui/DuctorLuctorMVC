package game.view;

import java.awt.*;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

import game.model.IfModel;
import game.model.IfReadonlyGameModel;
import game.model.IfReadonlyItem;
import game.model.IfReadonlyPlayer;
import game.model.IfRoom;
import game.model.IfWorld;
import game.view.gui.IGuiView;
import game.view.gui.ItemView;
import game.view.gui.MenuBar;
import game.view.gui.PetView;
import game.view.gui.PlayerView;
import game.view.gui.RoomPanel;
import game.view.gui.TargetView;
import game.view.gui.WelcomePanel;
import game.view.gui.WorldPanel;


/**
 * Gui game view implement.
 */
public class GuiGameView extends JFrame implements IGuiView {
    private int mainFrameWidth;
    private int mainFrameHeight;
    private int startX;
    private int startY;
    private WelcomePanel welcomePanel;
    private WorldPanel worldPanel;
    private JLabel statusBar;
    private IfReadonlyGameModel model;
    private JPanel bottomPanel;
    private JLabel actionResult;
    private MenuBar menuBar;

    /**
     * Game gui view set.
     *
     * @param model the model of game.
     */
    public GuiGameView(IfReadonlyGameModel model) {
        this.model = model;
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrameWidth = (int) (dimension.width * 0.7);
        mainFrameHeight = (int) (dimension.height * 0.7);
        startX = (dimension.width - mainFrameWidth) / 2;
        startY = (dimension.height - mainFrameHeight) / 2;

//        WelcomePanel welcomePanel = WelcomePanel.getInstance();
//        MenuBar menuBar = MenuBar.getInstance();

        this.setTitle("Doctor Lucky's Mansion");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(mainFrameWidth, mainFrameHeight);
        this.setLocation(startX, startY);

        menuBar = new MenuBar();
        this.setJMenuBar(menuBar);
        initStatusBar();
        this.add(statusBar, BorderLayout.NORTH);
        initBottomPanel();
        this.add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Used to show current play status.
     */
    private void initStatusBar() {
        statusBar = new JLabel("Welcome to Doctor Lucky's Mansion");
        statusBar.setFont(new Font("Arial", Font.BOLD, 30));
        statusBar.setHorizontalAlignment(SwingConstants.CENTER);
    }

    /**
     * Display message in status bar.
     *
     * @param info message to be displayed
     */
    public void updateStatusBar(String info) {
        statusBar.setText(info);
    }

    private void initBottomPanel() {
        bottomPanel = new JPanel();
        bottomPanel.setVisible(true);
        JLabel mouseHintLabel = new JLabel("MOUSE:  1.click player(blue):show player info; 2.click room:move; 3.click item(green):pick up; 4.click target(red):attack; 5.click pet(yellow):move pet");
        mouseHintLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mouseHintLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        JLabel keyboardHintLabel = new JLabel("KEYBOARD:  A:try to attack target; P:move pet; F:pick item; G:look around");
        keyboardHintLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        keyboardHintLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        actionResult = new JLabel("Action result");
        actionResult.setFont(new Font("Arial", Font.BOLD, 20));
        actionResult.setVisible(true);
        actionResult.setAlignmentX(Component.CENTER_ALIGNMENT);

        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(mouseHintLabel);
        bottomPanel.add(keyboardHintLabel);
        bottomPanel.add(actionResult);
    }

    public MenuBar getMyMenuBar() {
        return this.menuBar;
    }


    /**
     * Display action result in bottom panel.
     *
     * @param text message to be displayed
     */
    public void updateActionResult(String text) {
        actionResult.setText(text);
    }

    /**
     * Player view set invalid.
     *
     * @param playerModel the model of player
     */
    public void makePlayerViewInvalid(List<IfReadonlyPlayer> playerModel) {
        List<PlayerView> playerViewList = worldPanel.getPlayerViews(playerModel);
        for (PlayerView view : playerViewList) {
            view.setInvalid();
        }
    }

    /**
     * Player view set valid.
     *
     * @param playerModel the model of player
     */
    public void makePlayerViewValid(IfReadonlyPlayer playerModel) {
        // only zero or one item in this list
        List<PlayerView> playerViewList = worldPanel.getPlayerViews(
                Collections.singletonList(playerModel));
        if (playerViewList.size() == 0) {
            return;
        }
        playerViewList.get(0).setValid();
    }


    public WorldPanel getWorldView() {
        return this.worldPanel;
    }

    /**
     * RoomPanel list.
     *
     * @return the world panel
     * @throws UnsupportedOperationException if world panel has not been initialized
     */
    public List<RoomPanel> getRoomViews() {
        if (this.worldPanel == null) {
            throw new UnsupportedOperationException("Cannot get room views before world panel "
                    + "has been initialized");
        }
        return this.worldPanel.getRoomViews();
    }

    /**
     * Get player views.
     *
     * @return the player list of view
     * @throws UnsupportedOperationException if world panel has not been initialized
     */
    public List<PlayerView> getPlayerViews() {
        if (this.worldPanel == null) {
            throw new UnsupportedOperationException("Cannot get room views before world "
                    + "panel has been initialized");
        }
        return this.worldPanel.getPlayerViews();
    }

    /**
     * Get player view by model.
     *
     * @param playerModel the model of player
     * @return the player view.
     * @throws UnsupportedOperationException if world panel has not been initialized
     */
    public PlayerView getPlayerView(IfReadonlyPlayer playerModel) {
        if (this.worldPanel == null) {
            throw new UnsupportedOperationException("Cannot get room views before world panel "
                    + "has been initialized");
        }

        List<PlayerView> playerViews = this.worldPanel.getPlayerViews(
                Collections.singletonList(playerModel));
        if (playerViews.size() == 0) {
            throw new IllegalArgumentException("cannot get the player view by this model");
        }
        return playerViews.get(0);
    }

    /**
     * Get item views.
     *
     * @return the list of view.
     * @throws UnsupportedOperationException if world panel has not been initialized
     */
    public List<ItemView> getItemViews() {
        if (this.worldPanel == null) {
            throw new UnsupportedOperationException("Cannot get room views before world "
                    + "panel has been initialized");
        }
        return this.worldPanel.getItemViews();
    }

    public void pickItem(IfReadonlyItem item) {
        this.worldPanel.pickItem(item);
    }


    /**
     * Refresh all.
     */
    @Override
    public void refresh() {
        worldPanel.refresh();
    }

    @Override
    public IfModel getBindModel() {
        return this.model;
    }

    /**
     * GamePanel.
     *
     * @param world the world info.
     */
    public void showGamePanel(IfWorld world) {
        if (this.worldPanel == null) {
            this.worldPanel = new WorldPanel(world);
        }
        if (this.welcomePanel != null) {
            this.remove(this.welcomePanel);
        }

        JScrollPane scrollPane = new JScrollPane(this.worldPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

//        this.add(this.worldPanel);
        this.add(scrollPane, BorderLayout.CENTER);
        this.worldPanel.makeVisible();
        this.setVisible(true);
    }

    /**
     * Show the welcome panel.
     */
    public void showWelcomePanel() {
        if (this.welcomePanel == null) {
            this.welcomePanel = new WelcomePanel();
        }
        if (this.worldPanel != null) {
            this.remove(this.worldPanel);
        }
        this.add(welcomePanel);
        this.welcomePanel.makeVisible();
        this.setVisible(true);
    }

    public void showErrorDialog(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showGameOverDialog(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Game over", JOptionPane.WARNING_MESSAGE);
    }

    public void targetMove(IfRoom to) {
        worldPanel.targetMove(to);
    }

    public void petMove(IfRoom to) {
        worldPanel.petMove(to);
    }

    /**
     * Get target view.
     *
     * @return the target view.
     */
    public TargetView getTargetView() {
        if (this.worldPanel == null) {
            throw new UnsupportedOperationException("Cannot get target view before world panel "
                    + "has been initialized");
        }
        return worldPanel.getTargetView();
    }

    /**
     * Get pet view.
     *
     * @return the pet view.
     */
    public PetView getPetView() {
        if (this.worldPanel == null) {
            throw new UnsupportedOperationException("Cannot get pet view before world panel "
                    + "has been initialized");
        }
        return worldPanel.getPetView();
    }
}
