package game.view.gui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import game.model.IfModel;
import game.model.IfReadonlyTarget;

public class TargetView extends JButton implements IGuiView {
    private IfReadonlyTarget model;

    public TargetView(IfReadonlyTarget model) {
        this.model = model;
        this.setText(String.format("%s HP:%d", model.getName(), model.getHp()));
        this.setForeground(Color.WHITE);
        this.setBackground(Color.RED);
    }

    public void addClickListener(IfEventListener listener) {
        TargetView that = this;
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
        this.setText(String.format("%s HP:%d", model.getName(), model.getHp()));
        this.repaint();
    }

    @Override
    public IfModel getBindModel() {
        return this.model;
    }
}
