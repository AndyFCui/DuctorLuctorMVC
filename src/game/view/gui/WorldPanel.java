package game.view.gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import game.model.IfModel;
import game.model.IfReadonlyItem;
import game.model.IfReadonlyPlayer;
import game.model.IfReadonlyRoom;
import game.model.IfReadonlyWorld;
import game.model.IfRoom;
import game.model.IfWorld;

public class WorldPanel extends JPanel implements IGuiView {
    private IfReadonlyWorld world;

    private List<RoomPanel> roomViews;
    private List<IfReadonlyRoom> roomModels;
    // key: room model interface, value: concrete RoomPanelClass
    private Map<IfReadonlyRoom, RoomPanel> roomsMap;
    private Map<IfReadonlyItem, ItemView> itemsMap;
    private RoomPanel targetRoom;
    private TargetView targetView;
    private RoomPanel petRoom;
    private PetView petView;

    public WorldPanel(IfWorld world) {
        this.world = world;
        this.roomViews = new ArrayList<>();

        int width = world.getRows() * 50;
        int height = world.getColumns() * 50;

        // This is important! setSize does not work here.
        this.setPreferredSize(new Dimension(width, height));
        this.setLayout(null);

        this.initRooms();
        this.setVisible(true);
    }

    private void initRooms() {
        roomModels = new ArrayList<>();
        roomsMap = new HashMap<>();

        for (IfReadonlyRoom roomModel : world.getRooms()) {
            RoomPanel roomView = new RoomPanel(roomModel);
            roomModels.add(roomModel);
            roomViews.add(roomView);
            roomsMap.put(roomModel, roomView);
            // add to layout
            this.add(roomView);

            if (roomView.getTargetView() != null) {
                targetRoom = roomView;
                targetView = roomView.getTargetView();
            }
            if (roomView.getPetView() != null) {
                petRoom = roomView;
                petView = roomView.getPetView();
            }
        }
    }

    /**
     * Other room will be darker if not current player's room.
     */
    public void darkerOtherRooms(IfReadonlyRoom currentRoom) {
        RoomPanel notDark = roomsMap.get(currentRoom);
        for (RoomPanel roomView : roomViews) {
            if (roomView == notDark) {
                roomView.lighter();
                continue;
            }
            roomView.darker();
        }
    }

    public List<RoomPanel> getRoomViews() {
        return this.roomViews;
    }

    /**
     * Get specific player views from rooms
     *
     * @return playerView list
     */
    public List<PlayerView> getPlayerViews(List<IfReadonlyPlayer> playerModels) {
        List<PlayerView> allPlayerViews = getPlayerViews();
        List<PlayerView> result = new ArrayList<>();

        Map<IfReadonlyPlayer, PlayerView> playersMap = new HashMap<>();
        for (PlayerView pv : allPlayerViews) {
            playersMap.put((IfReadonlyPlayer) pv.getBindModel(), pv);
        }
        for (IfReadonlyPlayer m : playerModels) {
            result.add(playersMap.get(m));
        }
        return result;
    }


    /**
     * Get all player views from rooms
     *
     * @return playerView list
     */
    public List<PlayerView> getPlayerViews() {
        List<PlayerView> result = new ArrayList<>();
        for (RoomPanel roomView : roomViews) {
            List<PlayerView> playerViewList = roomView.getPlayerViewList();
            result.addAll(playerViewList);
        }
        return result;
    }

    /**
     * Get all item views from rooms
     *
     * @return itemView list
     */
    public List<ItemView> getItemViews(List<IfReadonlyItem> itemModels) {
        List<ItemView> allItemViews = getItemViews();
        List<ItemView> result = new ArrayList<>();

        Map<IfReadonlyItem, ItemView> itemsMap = new HashMap<>();
        for (ItemView iv : allItemViews) {
            itemsMap.put((IfReadonlyItem) iv.getBindModel(), iv);
        }
        for (IfReadonlyItem m : itemModels) {
            result.add(itemsMap.get(m));
        }
        return result;
    }

    /**
     * Get all item views from rooms
     *
     * @return itemView list
     */
    public List<ItemView> getItemViews() {
        List<ItemView> result = new ArrayList<>();
        for (RoomPanel roomView : roomViews) {
            List<ItemView> itemViewList = roomView.getItemViewList();
            result.addAll(itemViewList);
        }
        return result;
    }

    public void makeVisible() {
        this.setVisible(true);
    }

    public void movePlayer(IfReadonlyPlayer player, IfReadonlyRoom from, IfReadonlyRoom to) {
        PlayerView playerView = getPlayerViews(Collections.singletonList(player)).get(0);
        RoomPanel fromRoom = roomsMap.get(from);
        fromRoom.removePlayer(playerView);
        fromRoom.refresh();

        RoomPanel toRoom = roomsMap.get(to);
        toRoom.addPlayer(playerView);
        toRoom.refresh();
    }

    public void pickItem(IfReadonlyItem item) {
        ItemView itemView = getItemViews(Collections.singletonList(item)).get(0);
        IfRoom room = item.getCurrentRoom();
        RoomPanel roomPanel = this.roomsMap.get(room);
        roomPanel.removeItem(itemView);
        roomPanel.refresh();
    }

    public void targetMove(IfRoom to) {
        RoomPanel from = targetRoom;
        from.removeTarget(targetView);
        from.refresh();

        RoomPanel toRoomView = roomsMap.get(to);
        toRoomView.setTarget(targetView);
        targetRoom = toRoomView;
        toRoomView.refresh();
    }

    public void petMove(IfRoom to) {
        RoomPanel from = petRoom;
        from.removePet(petView);
        from.refresh();

        RoomPanel toRoomView = roomsMap.get(to);
        toRoomView.setPet(petView);
        petRoom = toRoomView;
        toRoomView.refresh();
    }

    /**
     * Refresh all
     */
    @Override
    public void refresh() {
        this.repaint();
    }

    @Override
    public IfModel getBindModel() {
        return this.world;
    }

    /**
     * Get target view
     *
     * @return target view
     */
    public TargetView getTargetView() {
        return this.targetView;
    }

    /**
     * Get pet view
     *
     * @return PetView
     */
    public PetView getPetView() {
        return this.petView;
    }

    /**
     * Prompts the user for input in a blocking dialog to choose a room to move pet.
     *
     * @return Selected room or null
     */
    public IfRoom showRoomChoose4PetMoveDialog() {
        return petView.showRoomChoose4PetMoveDialog(world);
    }

}
