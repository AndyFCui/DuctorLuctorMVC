package game.view.gui;

import game.model.IfModel;

/**
 * Interface for mouse listener.
 */
public interface IfEventListener {
    /**
     * Handle click event.
     *
     * @param view  the view of the gui
     * @param model the model of the gui
     */
    void handleClick(IGuiView view, IfModel model);
}
