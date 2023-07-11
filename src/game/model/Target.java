package game.model;

/**
 * NPC character, e.g. "Doctor Lucky"
 */
public class Target implements IfTarget {
    private String name;
    private int health;
    private int position;
    private IfPet pet;
    private IfRoom currentRoom;

    @Override
    public int getHp() {
        return health;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public IfPet getPet() {
        return this.pet;
    }

    @Override
    public void setPet(IfPet pet) {
        this.pet = pet;
    }

    @Override
    public IfRoom getCurrentRoom() {
        return this.currentRoom;
    }

    @Override
    public void setCurrentRoom(IfRoom currentRoom) {
        this.currentRoom = currentRoom;
    }

    @Override
    public void move(IfRoom room) {
        room.setTarget(this);
        this.currentRoom = room;
    }

    @Override
    public int beAttacked(int damage) {
        this.health -= damage;
        return this.health;
    }
}
