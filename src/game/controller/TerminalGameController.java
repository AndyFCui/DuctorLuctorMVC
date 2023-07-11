package game.controller;

import java.io.IOException;

import game.exception.GameNotReadyException;
import game.exception.WorldModelParseException;
import game.model.IfGameModel;
import game.model.IfPlayer;
import game.model.IfRoom;
import game.model.IfWorld;
import game.model.Player;
import game.model.World;
import game.view.TerminalGameView;

/**
 * Terminal controller implement.
 */
public class TerminalGameController implements IfGameController {
    private IfGameModel model;
    //    private IGameView view;
    private Readable in;
    private Appendable out;
    private IfWorld world;
    private TerminalGameView view;

    /**
     * Constructor of TerminalGameController.
     *
     * @param model IGameModel
     * @param view  TerminalGameView
     */
    public TerminalGameController(IfGameModel model, TerminalGameView view) {
        this.model = model;
        this.view = view;
        this.world = this.model.getWorld();
    }

    /**
     * Constructor of TerminalGameController.
     *
     * @param model IGameModel
     * @param view  TerminalGameView
     * @param world IWorld
     */
    public TerminalGameController(IfGameModel model, TerminalGameView view, IfWorld world) {
        this.model = model;
        this.view = view;
        this.model.setWorld(world);
        this.world = world;
    }


    /**
     * Constructor of TerminalGameController.
     *
     * @param in    Readable
     * @param out   Appendable
     * @param world IWorld
     * @throws IllegalArgumentException e
     */
    public TerminalGameController(Readable in, Appendable out, IfWorld world)
            throws IllegalArgumentException {
        this.constructorHelper(in, out, world);
    }

    /**
     * Constructor of TerminalGameController.
     *
     * @param in       Readable
     * @param out      Appendable
     * @param filePath Could be mansion.txt
     * @throws IllegalArgumentException e
     */
    public TerminalGameController(Readable in, Appendable out, String filePath)
            throws IllegalArgumentException {
        IfWorld world = new World(filePath);
        try {
            world.initWorld();
        } catch (WorldModelParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        this.constructorHelper(in, out, world);
    }

    /**
     * Set value to field.
     *
     * @param in    Readable
     * @param out   Appendable
     * @param world IWorld
     * @throws IllegalArgumentException e
     */
    private void constructorHelper(Readable in, Appendable out, IfWorld world)
            throws IllegalArgumentException {
        this.in = in;
        this.out = out;
        if (in == null || out == null) {
            throw new IllegalArgumentException("in and out cannot be null");
        }
        if (world == null) {
            throw new IllegalArgumentException("world model cannot be null");
        }
        this.view = new TerminalGameView(this.in, this.out);
        this.world = world;
    }

    /**
     * Init game by asking user for some settings.
     */
    public void initGameSetting() throws IOException, GameNotReadyException {
        // world model should be loaded before game settings
        if (this.world == null) {
            throw new WorldModelParseException("world model has not be initialized");
        }

        this.view.output("Welcome to Doctor Lucky's Mansion\n");
        this.view.output("Please input max turns:\n");

        int maxTurns = Integer.parseInt(this.view.input());
        this.world.setMaxTurns(maxTurns);

        this.view.output("Please input the number of players:\n");
        int playerNum = Integer.parseInt(this.view.input());
        for (int i = 0; i < playerNum; i++) {
            this.view.output(String.format("Add player %d:\n", i + 1));
            this.view.output("Please input the name of player:\n");
            String name = this.view.input();
            this.view.output(String.format("Please input the position of player [%s]:\n",
                    world.getRoomIds()));
            int position = Integer.parseInt(this.view.input());
            this.view.output("Please input the max number of items they can carry(0 is unlimited):\n");
            int maxCarry = Integer.parseInt(this.view.input());
            this.view.output("is Robot(true/false):\n");
            boolean isRobot = Boolean.parseBoolean(this.view.input());

            IfRoom playerRoom = world.getRoomByRoomId(position);
            IfPlayer player = new Player(name, playerRoom, world, isRobot, maxCarry);
            playerRoom.addPlayer(player);

            world.addPlayers(player);
        }
    }

    /**
     * Game start, this method is the controller's main entrance.
     *
     * @throws Exception e
     */
    @Override
    public void playGame() throws Exception {
        if (this.model.alreadyInit()) {
            return;
        }
        if (this.world == null) {
            throw new WorldModelParseException("world model has not be initialized");
        }
        this.world.initWorld();
        this.initGameSetting();
        this.model.setInitStatus(true);

//         make first player in the list play first
//        this.model.setTurn(this.world.getPlayers().get(0));
        this.startRound();
    }

    /**
     * Every players get a turn to make an action until reach the maxTurns or target has been killed.
     */
    private void startRound() throws IOException {

    }

}
