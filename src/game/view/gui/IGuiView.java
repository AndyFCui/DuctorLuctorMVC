package game.view.gui;

import game.model.IfModel;

public interface IGuiView {
    /**
     * Refresh view
     */
    void refresh();

    /**
     * Each view should bind a model, this method for getting the bound model
     *
     * @return IModel model
     */
    IfModel getBindModel();
}
