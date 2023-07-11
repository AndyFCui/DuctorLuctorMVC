package game.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.exception.CancelWeaponChooseException;
import game.exception.CannotMoveException;
import game.exception.CarryItemFullException;
import game.model.IfGameModel;
import game.model.IfItem;
import game.model.IfModel;
import game.model.IfPet;
import game.model.IfPlayer;
import game.model.IfReadonlyPlayer;
import game.model.IfReadonlyRoom;
import game.model.IfRoom;
import game.model.IfTarget;
import game.model.IfWorld;
import game.view.GuiGameView;
import game.view.gui.IGuiView;
import game.view.gui.IfEventListener;
import game.view.gui.ItemView;
import game.view.gui.MenuBar;
import game.view.gui.PetView;
import game.view.gui.PlayerView;
import game.view.gui.RoomPanel;
import game.view.gui.TargetView;

/**
 * Gui game controller, extends KeyAdapter abstract
 * class just for listening key typed event.
 */
public class GuiGameController extends KeyAdapter implements IfGameController, IfEventListener {
    private IfGameModel model;
    private GuiGameView view;
    private IfWorld world;

    /**
     * Constructor of GuiGameController.
     *
     * @param model IGameModel
     * @param view  IGameView
     */
    public GuiGameController(IfGameModel model, GuiGameView view) {
        this.model = model;
        this.view = view;
        this.world = this.model.getWorld();
        addMenuBarListener();
    }


    @Override
    public void playGame() {
        if (this.model.alreadyInit()) {
            this.view.showGamePanel(this.model.getWorld());
            // --> add listener for room view
            List<RoomPanel> roomViews = this.view.getRoomViews();
            for (RoomPanel roomView : roomViews) {
                roomView.addClickListener(this);
            }
            // <-- add listener for room view
            // --> add listener for player view
            List<PlayerView> playerViews = this.view.getPlayerViews();
            for (PlayerView playerView : playerViews) {
                playerView.addClickListener(this);
            }
            // <-- add listener for player view
            // --> add listener for item view
            List<ItemView> itemViews = this.view.getItemViews();
            for (ItemView itemView : itemViews) {
                itemView.addClickListener(this);
            }
            // <-- add listener for item view

            // --> add listener for target view
            TargetView targetView = this.view.getTargetView();
            targetView.addClickListener(this);
            // <-- add listener for target view

            // --> add listener for pet view
            PetView petView = this.view.getPetView();
            petView.addClickListener(this);
            // <-- add listener for pet view

            // add key listener for world panel
            // JPanel is not focusable by default
            this.view.getWorldView().setFocusable(true);
            this.view.getWorldView().addKeyListener(this);

            this.nextTurn();
        }
    }

    /**
     * Next turn and update status.
     */
    private void nextTurn() {
        // Only this player can make an action
        IfPlayer player = this.model.nextTurn();
        view.makePlayerViewValid(player);

        List<IfPlayer> tempPlayers = this.world.getPlayers();
        List<IfReadonlyPlayer> notMyTurn = new ArrayList<>(tempPlayers);
        notMyTurn.remove(player);
        view.makePlayerViewInvalid(notMyTurn);

        String statusFormat = "%s's turn | TURN %d/%d";
        int maxTurns = world.getMaxTurns();
        int currentTurn = this.model.getCurrentTurn();
        String status = String.format(statusFormat, player.getName(), currentTurn, maxTurns);
        view.updateStatusBar(status);

        view.getWorldView().darkerOtherRooms(player.getCurrentRoom());
        // if current player is a robot, play automatically
        if (player.isRobot()) {
            robotPlay(player);
        }
    }

    private void robotPlay(IfPlayer robot) {
        IfRoom currentRoom = robot.getCurrentRoom();
        List<IfRoom> neighbours = world.getNeighbours(currentRoom.getRoomId());
        List<IfItem> items = currentRoom.getItems();

        boolean thinking = true;
        view.updateActionResult(String.format("robot %s is thinking...", robot.getName()));
        while (thinking) {
            int randomInt = new Random().nextInt(5);
            switch (randomInt) {
                // move
                case 0:
                    IfRoom toRoom = neighbours.get(new Random().nextInt(neighbours.size()));
                    boolean ok = playerMove(robot, toRoom);
                    if (!ok) {
                        continue;
                    }
                    break;
                // pick item
                case 1:
                    if (items.size() == 0) {
                        continue;
                    }
                    ok = pickUp(robot, items.get(new Random().nextInt(items.size())));
                    if (!ok) {
                        continue;
                    }
                    break;
                // look around
                case 2:
                    lookAround(robot);
                    break;
                // move pet
                case 3:
                    if (currentRoom.getPet() == null) {
                        continue;
                    }
                    IfRoom randomNeighbor = neighbours.get(new Random().nextInt(neighbours.size()));
                    ok = movePet(robot, currentRoom.getPet(), randomNeighbor);
                    if (!ok) {
                        continue;
                    }
                    break;
                // attack
                case 4:
                    IfItem weapon = null;
                    if (items.size() != 0) {
                        weapon = items.get(new Random().nextInt(items.size()));
                    }
                    attackTarget(robot, null, weapon);
                    break;
            }
            thinking = false;
        }
    }


    /**
     * Whatever a player (include robot) make a successful action.
     * this method should be called
     */
    private void playerActFinished(String resultDescription) {
        if (world.getMaxTurns() == model.getCurrentTurn()) {
            gameOver(null);
            return;
        }
        targetMoveRandomly();
        petMoveRandomly();
        this.view.getWorldView().setFocusable(true);
        this.view.updateActionResult(resultDescription);
        nextTurn();
    }

    private void targetMoveRandomly() {
        List<IfRoom> neighbours = world.getNeighbours(world.getTarget().getCurrentRoom().getRoomId());
        IfRoom randomRoom = neighbours.get(new Random().nextInt(neighbours.size()));
        // target move
        world.getTarget().move(randomRoom);
        this.view.targetMove(randomRoom);
    }

    private void petMoveRandomly() {
        List<IfRoom> neighbours = world.getNeighbours(world.getPet().getCurrentRoom().getRoomId());
        IfRoom randomRoom = neighbours.get(new Random().nextInt(neighbours.size()));
        // pet move
        world.getPet().move(randomRoom);
        this.view.petMove(randomRoom);
    }

    @Override
    public void handleClick(IGuiView tempView, IfModel tempModel) {
        if (model.getGameOverStatus()) {
            view.showGameOverDialog("Game is over, please start a new");
            return;
        }
        IfPlayer turnPlayer = this.model.getTurnPlayer();

        // click room, player move
        if (tempView instanceof RoomPanel) {
            RoomPanel clickedRoomView = (RoomPanel) tempView;
            IfRoom clickedRoom = this.world.getRoom((IfReadonlyRoom) clickedRoomView.getBindModel());
            playerMove(turnPlayer, clickedRoom);
        }

        // click player
        if (tempView instanceof PlayerView) {
            PlayerView playerView = (PlayerView) tempView;
            IfPlayer playerModel = (IfPlayer) tempModel;
            System.out.println(String.format("%s clicked", playerModel.getName()));
            if (turnPlayer != playerModel) {
                return;
            }
            playerView.showMyInfoDialog();
        }

        // click item
        if (tempView instanceof ItemView) {
            IfItem itemModel = (IfItem) tempModel;
            if (turnPlayer.getCurrentRoom() != itemModel.getCurrentRoom()) {
                return;
            }
            pickUp(turnPlayer, itemModel);
        }

        // click target
        if (tempView instanceof TargetView) {
            IfTarget targetModel = (IfTarget) tempModel;
            // attack target
            if (turnPlayer.getCurrentRoom() != targetModel.getCurrentRoom()) {
                return;
            }
            attackTarget(turnPlayer, targetModel, null);
        }

        // click pet
        if (tempView instanceof PetView) {
            IfPet petModel = (IfPet) tempModel;
            if (turnPlayer.getCurrentRoom() != petModel.getCurrentRoom()) {
                return;
            }
            movePet(turnPlayer, petModel, null);
        }
    }

    /**
     * Listen and handle key event
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {
        IfPlayer turnPlayer = model.getTurnPlayer();
        switch (e.getKeyChar()) {
            case 'A':
            case 'a':
                System.out.println("try attack");
                attackTarget(turnPlayer, null, null);
                break;
            case 'P':
            case 'p':
                System.out.println("try move pet");
                movePet(turnPlayer, null, null);
                break;
            case 'F':
            case 'f':
                System.out.println("try pick item");
                pickUp(turnPlayer, null);
                break;
            case 'G':
            case 'g':
                System.out.println("try look around");
                lookAround(turnPlayer);
                break;
            default:
                break;
        }
    }

    /**
     * Move player tom toRoom
     *
     * @param player IfPlayer
     * @param toRoom IfRoom
     * @return finished action or not
     */
    private boolean playerMove(IfPlayer player, IfRoom toRoom) {
        IfRoom currentRoom = player.getCurrentRoom();
        try {
            player.moveTo(toRoom);
            this.view.getWorldView().movePlayer(player, currentRoom, toRoom);
            String xx = player.isRobot() ? "robot" : "player";
            String result = String.format("%s %s move from %s to %s", xx,
                    player.getName(), currentRoom.getName(), toRoom.getName());
            System.out.println(result);
            playerActFinished(result);
            return true;
        } catch (CannotMoveException e) {
            this.view.showErrorDialog(e.getMessage());
        }
        return false;
    }

    /**
     * Try to attack target.
     *
     * @param target null: Should check if target in current room, then determine to attack or not.
     *               not null: Attack directly.
     */
    private void attackTarget(IfPlayer player, IfTarget target, IfItem item) {
        String result;
        String xx = player.isRobot() ? "robot" : "player";

        if (target == null) {
            target = player.getCurrentRoom().getTarget();
            // target is not in the same room with current player
            if (target == null) {
                result = String.format("%s %s try to attack target, but target is not in current room", xx, player.getName());
                System.out.println(result);
                playerActFinished(result);
                return;
            }
        }

        // Player and pet or other player in the same room, cannot attack
        IfRoom currentRoom = player.getCurrentRoom();
        if (currentRoom.getPet() != null || currentRoom.getPlayers().size() > 1) {
            result = String.format("%s %s try to attack target, but seen by pet or other player, attack failed", xx, player.getName());
            System.out.println(result);
            playerActFinished(result);
            return;
        }

        PlayerView playerView = this.view.getPlayerView(player);
        result = String.format("%s %s attacked target successful", xx, player.getName());
        try {
            if (!player.isRobot()) {
                item = playerView.chooseWeapon();
            }
            boolean die = player.attack(item, target);
            this.view.getTargetView().refresh();
            System.out.println(result);
            if (die) {
                gameOver(player);
                return;
            }
            // not die
        } catch (CancelWeaponChooseException e) {
            return;
        }
        playerActFinished(result);
    }


    /**
     * Pick up items in this room
     *
     * @param player    IfPlayer
     * @param itemModel null:     Should check current's room and let player make a choice;
     *                  not null: Pick up directly.
     */
    private boolean pickUp(IfPlayer player, IfItem itemModel) {
        if (itemModel == null) {
            List<IfItem> currentRoomItems = player.getCurrentRoom().getItems();
            PlayerView playerView = this.view.getPlayerView(player);
            if (currentRoomItems.size() == 0) {
                playerView.showNoItem2PickDialog();
                return false;
            }
            // let player make a choose
            IfItem selectItem = playerView.chooseItems(currentRoomItems);
            if (selectItem == null) {
                System.out.println("cancel choose items");
                return false;
            }
            itemModel = selectItem;
        }

        try {
            player.pickItem(itemModel);
            this.view.pickItem(itemModel);
            String xx = player.isRobot() ? "robot" : "player";
            String result = String.format("%s %s picked %s", xx, player.getName(), itemModel.getName());
            System.out.println(result);
            playerActFinished(result);
            return true;
        } catch (CarryItemFullException e) {
            if (player.isRobot()) {
                return false;
            }
            this.view.showErrorDialog(e.getMessage());
        }
        return false;
    }

    /**
     * Move pet to another room.
     *
     * @param pet    null: Should check if pet in current room, then determine to move or not.
     *               not null: Move directly.
     * @param toRoom null: Should make player choose one
     */
    private boolean movePet(IfPlayer player, IfPet pet, IfRoom toRoom) {
        IfRoom currentRoom = player.getCurrentRoom();

        if (pet == null) {
            pet = currentRoom.getPet();
            if (pet == null) {
                this.view.getPlayerView(player).showPetNotInCurrentRoomDialog();
                return false;
            }
        }

        if (toRoom == null) {
            toRoom = view.getWorldView().showRoomChoose4PetMoveDialog();
        }
        IfRoom fromRoom = world.getPet().getCurrentRoom();
        if (toRoom == fromRoom || toRoom == null) {
            return false;
        }
        // move pet
        world.getPet().move(toRoom);
        this.view.petMove(toRoom);
        String xx = player.isRobot() ? "robot" : "player";
        String result = String.format("%s %s moved pet from %s to %s", xx, this.model.getTurnPlayer().getName(), fromRoom.getName(), toRoom.getName());
        System.out.println(result);
        playerActFinished(result);
        return true;
    }


    /**
     * Look around will cost one turn
     * Player's action look around the current neighbour.
     */
    private void lookAround(IfPlayer player) {
        IfRoom currentRoom = player.getCurrentRoom();
        if (!player.isRobot()) {
            StringBuilder sb = new StringBuilder();
            sb.append("*********** current room ***********\n");
            sb.append(currentRoom.getDescription());

            sb.append("*********** neighboring room ***********\n");
            for (IfRoom room : world.getNeighbours(currentRoom.getRoomId())) {
                sb.append(String.format("------ neighboring room %d ------\n", room.getRoomId()));
                if (room.getPet() != null) {
                    sb.append("Pet in this room, invisible.\n");
                } else {
                    sb.append(room.getDescription());
                }
            }
            sb.append("\n");
            view.getPlayerView(player).showLookAroundInfo(sb.toString());
        }

        String xx = player.isRobot() ? "robot" : "player";
        String result = String.format("%s %s has looked around", xx, player.getName());
        System.out.println(result);
        playerActFinished(result);
    }

    /**
     * game over of game.
     *
     * @param winner who win the game, could be null which means no one win
     */
    private void gameOver(IfPlayer winner) {
        String result;
        if (winner == null) {
            result = "Reached max turns, nobody won, game over";
        } else {
            result = String.format("%s is the winner of the game!", winner.getName());
        }
        this.view.updateActionResult(result);
        view.showGameOverDialog(result);
        model.setGameOverStatus(true);
    }

    private void addMenuBarListener() {
        MenuBar menuBar = view.getMyMenuBar();
        menuBar.getNewGameWithNewFileMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("fix me");
            }
        });
        menuBar.getNewGameWithCurrentFileMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("fix me");
            }
        });
        menuBar.getQuitGameMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
