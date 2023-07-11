package game.view.gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import game.exception.CancelWeaponChooseException;
import game.model.IfItem;
import game.model.IfModel;
import game.model.IfReadonlyPlayer;

public class PlayerView extends JButton implements IGuiView {
    private IfReadonlyPlayer model;

    public PlayerView(IfReadonlyPlayer player) {
        this.model = player;
        this.setText(player.getName());
        this.setForeground(Color.WHITE);
        this.setBackground(Color.decode("#a9d6e5"));
    }

    /**
     * Cannot click, cannot move, not my turn
     */
    public void setInvalid() {
        this.setEnabled(false);
        this.setBackground(Color.decode("#013a63"));
    }

    public void setValid() {
        this.setEnabled(true);
        this.setBackground(Color.decode("#a9d6e5"));
    }

    public void addClickListener(IfEventListener listener) {
        PlayerView that = this;
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
        return model;
    }

    /**
     * Show description of the player
     */
    public void showMyInfoDialog() {
        String desc = this.model.getDescription();
        JOptionPane.showMessageDialog(this, desc, "Player info", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showNoItem2PickDialog() {
        JOptionPane.showMessageDialog(this, "There's no items in this room can be picked up", "Pick items", JOptionPane.ERROR_MESSAGE);
    }

    public void showPetNotInCurrentRoomDialog() {
        JOptionPane.showMessageDialog(this, "Pet is not in current room", "Move pet", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Prompts the user for input in a blocking dialog to choose a weapon.
     *
     * @return null if select default weapon
     * @throws CancelWeaponChooseException select nothing, may want to something else
     */
    public IfItem chooseWeapon() throws CancelWeaponChooseException {
        List<IfItem> carryItems = model.getCarryItems();
        List<String> itemNames = new ArrayList<>();
        Map<String, IfItem> itemsMap = new HashMap<>();

        String defaultWeapon = "Hand(-1HP)";
        itemNames.add(0, defaultWeapon);
        itemsMap.put(defaultWeapon, null);
        for (IfItem item : carryItems) {
            String itemName = String.format("%s(-%dHP)", item.getName(), item.getAmountOfDamage());
            itemNames.add(itemName);
            itemsMap.put(itemName, item);
        }

        String selectItemName = (String) JOptionPane.showInputDialog(
                this,
                "Choose a weapon for attacking:",
                "Choose weapon",
                JOptionPane.PLAIN_MESSAGE,
                null,
                itemNames.toArray(),
                defaultWeapon
        );

        if (selectItemName == null) {
            throw new CancelWeaponChooseException();
        }
        return itemsMap.get(selectItemName);
    }

    /**
     * Show look around information dialog
     *
     * @param description string
     */
    public void showLookAroundInfo(String description) {
        JOptionPane.showMessageDialog(this, description, "Look Around", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Choose which item will be picked up
     *
     * @param currentRoomItems should not be null or empty
     * @return choosed item or null
     */
    public IfItem chooseItems(List<IfItem> currentRoomItems) {
        Map<String, IfItem> itemsMap = new HashMap<>();
        List<String> itemNames = new ArrayList<>();

        for (IfItem item : currentRoomItems) {
            String itemName = item.getName();
            itemNames.add(itemName);
            itemsMap.put(itemName, item);
        }

        String selectItemName = (String) JOptionPane.showInputDialog(
                this,
                "Choose an item to pick:",
                "Choose items",
                JOptionPane.PLAIN_MESSAGE,
                null,
                itemNames.toArray(),
                itemNames.get(0)
        );
        return itemsMap.get(selectItemName);
    }
}
