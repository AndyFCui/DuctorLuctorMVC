package game.model;


import java.util.ArrayList;
import java.util.List;

import game.exception.CannotMoveException;
import game.exception.CarryItemFullException;

/**
 * Player information class.
 */
public class Player implements IfPlayer {
    private String name;
    private int position;
    private IfRoom currentRoom;
    private IfWorld world;
    private boolean isRobot;
    private int maxCarry;
    private List<IfItem> carryItems;

    /**
     * Player constructor which can create the player object.
     *
     * @param name     the name of the player.
     * @param room     the room the player position.
     * @param world    the world info for current player
     * @param isRobot  the robot or not of current player.
     * @param maxCarry the player's hold item max
     */
    public Player(String name, IfRoom room, IfWorld world, Boolean isRobot, Integer maxCarry)
            throws IllegalArgumentException {
        if (name == null || room == null || world == null || isRobot == null || maxCarry == null) {
            throw new IllegalArgumentException("name,room,world,isRobot,"
                    + "maxCarry cannot be null for new player!");
        }
        if (("").equals(name)) {
            throw new IllegalArgumentException("name cannot be empty for new player!");
        }
        if (!world.getRooms().contains(room)) {
            throw new IllegalArgumentException("the room is not belong to the world!");
        }

        this.name = name;
        this.currentRoom = room;
        this.world = world;
        this.isRobot = isRobot;
        this.maxCarry = maxCarry;
        this.carryItems = new ArrayList<>();
    }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public IfRoom getCurrentRoom() {
        return this.currentRoom;
    }

    @Override
    public boolean isRobot() {
        return this.isRobot;
    }

    @Override
    public int getMaxCarry() {
        return this.maxCarry;
    }

    @Override
    public String getDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("name: %s, position: %d %s, max carry: %d\n", name, currentRoom.getRoomId(),
                currentRoom.getName(), maxCarry));
        if (carryItems.size() == 0) {
            sb.append("carry no item.\n");
        } else {
            sb.append("carry items: \n");
            List<String> name = new ArrayList<>();
            for (IfItem item : carryItems) {
                name.add(item.getName());
            }
            String names = String.join(",", name);
            sb.append(names).append("\n\n");
        }
        return sb.toString();
    }

    @Override
    public List<IfItem> getCarryItems() {
        return this.carryItems;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setIsRobot(boolean isRobot) {
        this.isRobot = isRobot;
    }

    @Override
    public void setMaxCarry(int maxCarry) {
        this.maxCarry = maxCarry;
    }

    @Override
    public void pickItem(IfItem item) throws CarryItemFullException {
        if (this.maxCarry == 0) {
            carryItems.add(item);
            return;
        } else {
            if (this.carryItems.size() == this.maxCarry) {
                throw new CarryItemFullException(String.format("Player %s cannot carry item any more",
                        this.getName()));
            }
        }
        carryItems.add(item);
        currentRoom.removeItem(item);
    }

    @Override
    public void moveTo(IfRoom room) throws CannotMoveException {
        boolean isNeighbor = this.world.isNeighbor(this.currentRoom, room);
        if (isNeighbor) {
            this.currentRoom.removePlayer(this);
            room.addPlayer(this);
            this.currentRoom = room;
        } else {
            throw new CannotMoveException(String.format("Player %s can not move from %s to %s",
                    this.getName(), this.currentRoom.getName(), room.getName()));
        }
    }

    @Override
    public boolean attack(IfItem item, IfTarget targetModel) {
        int damage;
        // use hand to attack
        if (item == null) {
            damage = 1;
        } else {
            damage = item.getAmountOfDamage();
        }
        int leftHp = targetModel.beAttacked(damage);
        carryItems.remove(item);
        return leftHp <= 0;
    }

    public List<IfRoom> lookAround() {
        List<IfRoom> rooms = new ArrayList<>();
        for (IfRoom room : this.world.getNeighbours(this.currentRoom.getRoomId())) {
            if (room.hasPet()) {
                // todo: pet
            } else {
                rooms.add(room);
            }
        }
        return rooms;
    }
}
