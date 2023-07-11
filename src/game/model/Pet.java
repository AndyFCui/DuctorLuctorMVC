package game.model;

/**
 * Pet character.
 */
public class Pet implements IfPet {
    private String name;
    private int position;
    private IfRoom currentRoom;

    /**
     * Pet constructor.
     *
     * @param name     the name of the pet
     * @param position the position of the pet
     */
    public Pet(String name, int position) {
        this.name = name;
        this.position = position;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getPosition() {
        return this.position;
    }

    @Override
    public IfRoom getCurrentRoom() {
        return this.currentRoom;
    }

    @Override
    public void move(IfRoom room) {
        room.setPet(this);
        currentRoom = room;
    }

    @Override
    public void setCurrentRoom(IfRoom currentRoom) {
        this.currentRoom = currentRoom;
    }

}
