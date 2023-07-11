package game.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Room space for implementing room class of the world.
 */
public class Room implements IfRoom {
    private String name;
    private int roomId;
    private Rectangle rect;
    private List<IfItem> items;
    private List<IfPlayer> players;
    // Could be null if there's no target in this room.
    private IfTarget target;
    private IfPet pet;
    private boolean hasPet;

    /**
     * The Room constructor.
     *
     * @param name  the name of the Room.
     * @param index the room index of the room.
     * @param rect  the rectangle position of the room.
     */
    public Room(String name, int index, Rectangle rect) {
        this.name = name;
        this.roomId = index;
        this.rect = rect;
        this.items = new ArrayList<>();
        this.players = new ArrayList<>();
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return roomId;
    }

    public void setIndex(int index) {
        this.roomId = index;
    }

    @Override
    public boolean hasPet() {
        return this.hasPet;
    }

    @Override
    public int getRoomId() {
        return this.roomId;
    }

    @Override
    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    @Override
    public List<IfItem> getItems() {
        return items;
    }

    @Override
    public String getDescription() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("room name: %s, room id: %s\n", this.getName(), this.getRoomId()));
        if (this.getItems().size() == 0) {
            sb.append("no item in the room.\n");
        } else {
            sb.append("items laying around the space: ");
            for (IfItem item : this.getItems()) {
                sb.append(item.getName()).append(" ");
            }
            sb.append("\n");
        }
        if (this.getPlayers().size() == 0) {
            sb.append("no player in the room.\n");
        } else {
            sb.append(String.format("%d player(s) was in the room: ", this.getPlayers().size()));
            for (IfPlayer p : this.getPlayers()) {
                sb.append(p.getName()).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public void setItems(List<IfItem> items) {
        this.items = items;
    }

    @Override
    public void addItem(IfItem item) {
        this.items.add(item);
    }

    @Override
    public void removeItem(IfItem item) {
        this.items.remove(item);
    }

    @Override
    public void removePlayer(IfPlayer player) {
        this.players.remove(player);
    }

    @Override
    public void addPlayer(IfPlayer... players) {
        this.players.addAll(Arrays.asList(players));
    }

    @Override
    public List<IfPlayer> getPlayers() {
        return players;
    }

    @Override
    public void removeTarget() {
        if (this.target != null) {
            this.target = null;
        }
    }

    @Override
    public void setPet(IfPet pet) {
        if (pet.getPosition() != this.roomId) {
            return;
        }
        this.pet = pet;
    }

    @Override
    public void removePet() {
        if (this.pet != null) {
            this.pet = null;
        }
    }

    @Override
    public void setTarget(IfTarget target) {
        if (target.getPosition() != this.roomId) {
            return;
        }
        this.target = target;
    }

    @Override
    public IfTarget getTarget() {
        return this.target;
    }

    @Override
    public IfPet getPet() {
        return pet;
    }
}
