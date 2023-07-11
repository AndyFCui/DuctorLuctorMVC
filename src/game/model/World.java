package game.model;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import game.exception.WorldModelParseException;

/**
 * This class for implementing the world model for the game frame.
 */
public class World implements IfWorld {

    /***
     * World's name, e.g. "Doctor Lucky's Mansion"
     */
    HashMap<Integer, IfRoom> roomMap;
    private String name;
    private int rows;
    private int columns;
    private IfTarget target;
    private IfPet pet;
    private List<IfRoom> rooms;
    private List<IfPlayer> players;
    private String filePath;
    private int maxTurns;
    // key: roomId, value: IRoom
    private boolean init;

    /**
     * World empty parameter constructor.
     */
    public World() {
        this.target = new Target();
        this.rooms = new ArrayList<>();
        this.roomMap = new HashMap<>();
        this.players = new ArrayList<>();
        this.init = false;
    }

    /**
     * Init world model by file.
     *
     * @param filePath World specification file.
     */
    public World(String filePath) {
        this();
        this.filePath = filePath;
        try {
            this.initWorld();
        } catch (WorldModelParseException e) {
            throw new IllegalArgumentException("filepath invalid");
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    @Override
    public List<IfRoom> getRooms() {
        return this.rooms;
    }

    @Override
    public IfRoom getRoomByRoomId(int roomId) {
        return this.roomMap.get(roomId);
    }

    @Override
    public IfRoom getRoom(IfReadonlyRoom readonlyRoom) {
        return this.getRoomByRoomId(readonlyRoom.getRoomId());
    }

    @Override
    public IfTarget getTarget() {
        return this.target;
    }

    @Override
    public IfPet getPet() {
        return this.pet;
    }

    @Override
    public List<Integer> getRoomIds() {
        return this.rooms.stream().map(IfRoom::getRoomId).collect(Collectors.toList());
    }

    @Override
    public List<IfRoom> getNeighbours(int roomId) {
        List<IfRoom> list = new ArrayList<>();
        IfRoom room = this.roomMap.get(roomId);
        for (IfRoom r : this.getRooms()) {
            if (r == room) {
                continue;
            }
            if (this.isNeighbor(r, room)) {
                list.add(r);
            }
        }
        return list;
    }

    @Override
    public boolean isNeighbor(IfRoom room1, IfRoom room2) {
        if (room1.getRect().getX() == room2.getRect().getX() + room2.getRect().getWidth()
                || room2.getRect().getX() == room1.getRect().getX() + room1.getRect().getWidth()) {
            if ((room1.getRect().getY() <= room2.getRect().getY()
                    && room1.getRect().getY() + room1.getRect().getHeight() >= room2.getRect().getY())
                    || (room2.getRect().getY() <= room1.getRect().getY()
                    && room2.getRect().getY()
                    + room2.getRect().getHeight() >= room1.getRect().getY())) {
                return true;
            }
        }
        if (room1.getRect().getY() == room2.getRect().getY() + room2.getRect().getHeight()
                || room2.getRect().getY() == room1.getRect().getY() + room1.getRect().getHeight()) {
            if ((room1.getRect().getX() <= room2.getRect().getX()
                    && room1.getRect().getX() + room1.getRect().getWidth() >= room2.getRect().getX())
                    || (room2.getRect().getX() <= room1.getRect().getX()
                    && room2.getRect().getX()
                    + room2.getRect().getWidth() >= room1.getRect().getX())) {
                return true;
            }
        }
        return false;
    }

    public void setRooms(List<IfRoom> rooms) {
        this.rooms = rooms;
    }

    @Override
    public boolean hasInit() {
        return this.init;
    }

    @Override
    public int getMaxTurns() {
        return this.maxTurns;
    }

    @Override
    public List<IfPlayer> getPlayers() {
        return this.players;
    }

    @Override
    public void initWorld() throws WorldModelParseException {
        if (this.init) {
            return;
        }
        int roomNum = 0;
        int itemNum = 0;

        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            throw new WorldModelParseException(String.format("read model file %s error", filePath));
        }

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.length() == 0) {
                continue;
            }

            if (i == 0) {
                String[] split = line.split(" ");
                this.rows = Integer.parseInt(split[0]);
                this.columns = Integer.parseInt(split[1]);
                StringBuilder name = new StringBuilder();
                for (int j = 2; j < split.length; j++) {
                    name.append(split[j]).append(" ");
                }
                this.name = name.toString().trim();
            } else if (i == 1) {
                String[] split = line.split(" ");
                Target target = new Target();
                target.setHealth(Integer.parseInt(split[0]));
                StringBuilder name = new StringBuilder();
                for (int j = 1; j < split.length; j++) {
                    name.append(split[j]).append(" ");
                }
                target.setName(name.toString().trim());
                target.setPosition(0);
                this.target = target;
            } else if (i == 2) {
                pet = new Pet(line, 0);
                this.target.setPet(pet);
            } else if (i == 3) {
                roomNum = Integer.parseInt(line);
                int index = 0;
                for (i = i + 1; i < roomNum + 4; i++) {
                    line = lines.get(i).trim();
                    String[] split = line.split(" ");
                    int x = Integer.parseInt(split[0]);
                    int y = Integer.parseInt(split[1]);
                    int width = Integer.parseInt(split[2]) - x;
                    int height = Integer.parseInt(split[3]) - y;
                    Rectangle rect = new Rectangle(x, y, width, height);

                    StringBuilder name = new StringBuilder();
                    for (int j = 4; j < split.length; j++) {
                        name.append(split[j]).append(" ");
                    }
                    IfRoom room = new Room(name.toString().trim(), index++, rect);
                    if (this.target.getPosition() == room.getRoomId()) {
                        target.move(room);
                    }
                    if (pet.getPosition() == room.getRoomId()) {
                        pet.move(room);
                    }
                    this.rooms.add(room);
                }

                line = lines.get(i).trim();
                itemNum = Integer.parseInt(line);
                for (i = i + 1; i < lines.size(); i++) {
                    line = lines.get(i).trim();
                    String[] split = line.split(" ");
                    int homeIndex = Integer.parseInt(split[0]);
                    int amountOfDamage = Integer.parseInt(split[1]);

                    StringBuilder name = new StringBuilder();
                    for (int j = 2; j < split.length; j++) {
                        name.append(split[j]).append(" ");
                    }
                    IfItem it = new Item(name.toString().trim(), amountOfDamage);
                    IfRoom room = this.rooms.get(homeIndex);
                    room.getItems().add(it);
                    it.setCurrentRoom(room);
                }
            }
        }

        for (IfRoom room : rooms) {
            roomMap.put(room.getRoomId(), room);
        }

        this.init = true;
    }

    @Override
    public void setMaxTurns(int maxTurns) {
        this.maxTurns = maxTurns;
    }

    @Override
    public void addPlayers(IfPlayer... players) {
        this.players.addAll(Arrays.asList(players));
    }

    @Override
    public void setTarget(IfTarget target) {
        this.target = target;
    }
}
