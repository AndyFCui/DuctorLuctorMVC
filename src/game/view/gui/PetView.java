package game.view.gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import game.model.IfModel;
import game.model.IfReadonlyPet;
import game.model.IfReadonlyWorld;
import game.model.IfRoom;

public class PetView extends JButton implements IGuiView {
    private IfReadonlyPet model;

    public PetView(IfReadonlyPet model) {
        this.model = model;
        this.setText(model.getName());
        this.setForeground(Color.WHITE);
        this.setBackground(Color.decode("#ffbf69"));
    }

    public void addClickListener(IfEventListener listener) {
        PetView that = this;
        MouseAdapter clickAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                listener.handleClick(that, that.getBindModel());
            }
        };
        this.addMouseListener(clickAdapter);
    }

    @Override
    public void refresh() {
        this.repaint();
    }

    @Override
    public IfModel getBindModel() {
        return this.model;
    }

    /**
     * Prompts the user for input in a blocking dialog to choose a room to move pet.
     *
     * @return Selected room or null
     */
    public IfRoom showRoomChoose4PetMoveDialog(IfReadonlyWorld world) {
        IfRoom currentRoom = world.getPet().getCurrentRoom();
        List<IfRoom> neighbours = world.getNeighbours(currentRoom.getRoomId());
        List<String> roomNames = new ArrayList<>();
        Map<String, IfRoom> roomsMap = new HashMap<>();

        for (IfRoom room : neighbours) {
            String roomName = String.format("%d:%s", room.getRoomId(), room.getName());
            roomNames.add(roomName);
            roomsMap.put(roomName, room);
        }

        String selectRoomName = (String) JOptionPane.showInputDialog(
                this,
                "Choose a room to move:",
                "Move pet",
                JOptionPane.PLAIN_MESSAGE,
                null,
                roomNames.toArray(),
                roomNames.get(0)
        );

        return roomsMap.get(selectRoomName);
    }

}
