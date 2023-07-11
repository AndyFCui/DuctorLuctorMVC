package game.view.gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import game.model.IfModel;
import game.model.IfReadonlyItem;

public class ItemView extends JButton implements IGuiView {
    private IfReadonlyItem model;

    public ItemView(IfReadonlyItem model) {
        this.model = model;
        this.setText(model.getName());
        this.setForeground(Color.WHITE);
        this.setBackground(Color.decode("#007200"));
    }

    public void addClickListener(IfEventListener listener) {
        ItemView that = this;
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
}

