package game.view.gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import game.model.IfModel;
import game.model.IfReadonlyItem;
import game.model.IfReadonlyPet;
import game.model.IfReadonlyPlayer;
import game.model.IfReadonlyRoom;
import game.model.IfReadonlyTarget;

public class RoomPanel extends JPanel implements IGuiView {
    private IfReadonlyRoom room;
    private JLabel roomNameLabel;

    // RoomPanel width, not room width
    private int width;
    private int height;

    private List<PlayerView> playerViewList;
    private List<ItemView> itemViewList;
    private TargetView targetView;
    private PetView petView;
//    private Map<IReadonlyPlayer, PlayerView> playersMap;
//    private Map<IReadonlyItem, ItemView> itemsMap;

    public RoomPanel(IfReadonlyRoom room) {
        this.room = room;

        Rectangle rect = room.getRect();
        this.width = rect.width * 50;
        this.height = rect.height * 50;
//        this.setPreferredSize(new Dimension(this.width, this.height));
        this.setBounds((int) (rect.getX() * 50), (int) (rect.getY() * 50), this.width, this.height);
        this.setLayout(new FlowLayout());
        this.setVisible(true);
        this.setBackground(Color.decode("#ede0d4"));

        this.setBorder(BorderFactory.createLineBorder(Color.black));

//        this.refresh();
        initChildView();
    }

    public void lighter() {
        this.setBackground(Color.decode("#ede0d4"));
        this.refresh();
    }

    public void darker() {
//        this.setBackground(Color.decode("#bb9457"));
        this.setBackground(Color.decode("#d5bdaf"));
        this.refresh();
    }


    private void initChildView() {
        // --> room name and id
        roomNameLabel = new JLabel(String.format("%d: %s", room.getRoomId(), room.getName()));
        roomNameLabel.setVisible(true);
        this.add(roomNameLabel);
        // <-- room name and id

        playerViewList = new ArrayList<>();
        itemViewList = new ArrayList<>();

        // --> target
        IfReadonlyTarget target = room.getTarget();
        if (target != null) {
            targetView = new TargetView(target);
            targetView.setVisible(true);
            this.add(targetView);
        }
        // <-- target

        // <-- pet
        IfReadonlyPet pet = room.getPet();
        if (pet != null) {
            petView = new PetView(pet);
            petView.setVisible(true);
            this.add(petView);
        }
        // <-- pet

        // --> item
        for (IfReadonlyItem item : room.getItems()) {
            addItem(item);
        }
        // <-- item

        // --> player
        for (IfReadonlyPlayer playerModel : room.getPlayers()) {
            addPlayer(playerModel);
        }
        // <-- player
    }

    public void addPlayer(PlayerView playerView) {
        playerViewList.add(playerView);
        this.add(playerView);
    }

    public void removePlayer(PlayerView playerView) {
        playerViewList.remove(playerView);
        this.remove(playerView);
    }

    private void addPlayer(IfReadonlyPlayer playerModel) {
        PlayerView playerView = new PlayerView(playerModel);
        playerView.setVisible(true);
        playerViewList.add(playerView);
        this.add(playerView);
    }

    private void addItem(IfReadonlyItem itemModel) {
        ItemView itemView = new ItemView(itemModel);
        itemView.setVisible(true);
        itemViewList.add(itemView);
        this.add(itemView);
    }

    public void removeItem(ItemView itemView) {
        itemViewList.remove(itemView);
        this.remove(itemView);
    }

    public void removeTarget(TargetView targetView) {
        this.targetView = null;
        this.remove(targetView);
    }

    public void setTarget(TargetView targetView) {
        this.targetView = targetView;
        this.add(targetView);
    }

    public void removePet(PetView petView) {
        this.petView = null;
        this.remove(petView);
    }

    public void setPet(PetView petView) {
        this.petView = petView;
        this.add(petView);
    }

    public void refresh() {
        this.revalidate();
        this.repaint();
    }

    @Override
    public IfModel getBindModel() {
        return this.room;
    }

    public void addClickListener(IfEventListener listener) {
        RoomPanel that = this;
        MouseAdapter clickAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                listener.handleClick(that, that.getBindModel());
            }
        };
        this.addMouseListener(clickAdapter);
    }

    public List<PlayerView> getPlayerViewList() {
        return playerViewList;
    }


    public List<ItemView> getItemViewList() {
        return itemViewList;
    }

    /**
     * @return TargetView or null
     */
    public TargetView getTargetView() {
        return this.targetView;
    }

    /**
     * @return TargetView or null
     */
    public PetView getPetView() {
        return this.petView;
    }
}
