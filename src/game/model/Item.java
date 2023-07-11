package game.model;

/**
 * This is the interface of the Item.
 */
public class Item implements IfItem {
    private String name;
    private int amountOfDamage;
    private IfRoom currentRoom;

    /**
     * Item constructor which can create the Item object.
     *
     * @param name           the name of item
     * @param amountOfDamage the item's attack
     */
    public Item(String name, int amountOfDamage) {
        this.name = name;
        this.amountOfDamage = amountOfDamage;
    }

    public String getName() {
        return name;
    }

    @Override
    public IfRoom getCurrentRoom() {
        return this.currentRoom;
    }

    @Override
    public int getAmountOfDamage() {
        return this.amountOfDamage;
    }

    @Override
    public void setCurrentRoom(IfRoom currentRoom) {
        this.currentRoom = currentRoom;
    }
}
