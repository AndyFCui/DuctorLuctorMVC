package game;

import game.model.*;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class PlayerTest {

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor() {
    Player player = new Player(null, null, null, null, null);
  }

  @Test
  public void testLookAround() {
    List<IfRoom> rooms = new ArrayList<>();

    MockRoom room4 = new MockRoom("Drawing Room", 4, 6, 6, 20, 10);
    MockRoom room7 = new MockRoom("Hedge Maze", 7, 9, 10, 17, 18);
    MockRoom room9 = new MockRoom("Lancaster Room", 9, 5, 18, 13, 24);
    MockRoom room10 = new MockRoom("Library", 10, 13, 18, 21, 24);
    MockRoom room3 = new MockRoom("Dining Hall", 3, 0, 8, 6, 14);
    rooms.add(room4);
    rooms.add(room7);
    rooms.add(room9);
    rooms.add(room10);
    rooms.add(room3);

    MockWorld mockWorld = new MockWorld(rooms);
    Player p1 = new Player("p1", room7, mockWorld, false, 0);
    List<IfRoom> room7Around = p1.lookAround();

    List<IfRoom> expected = Arrays.asList(room4, room9, room10);

    assertThat(room7Around, is(expected));
  }


  // --> Mock
  //      --> MockRoom
  public class MockRoom implements IfRoom {
    int roomId;
    Rectangle rectangle;

    public MockRoom(String name, int roomId, int x1, int y1,
                    int x2, int y2) {
      this.roomId = roomId;
      this.rectangle = new Rectangle(x1, y1, x2 - x1, y2 - y1);
    }

    @Override
    public List<IfPlayer> getPlayers() {
      return null;
    }

    @Override
    public String getName() {
      return null;
    }

    @Override
    public IfTarget getTarget() {
      return null;
    }

    @Override
    public IfPet getPet() {
      return null;
    }

    @Override
    public boolean hasPet() {
      return false;
    }

    @Override
    public int getRoomId() {
      return this.roomId;
    }

    @Override
    public Rectangle getRect() {
      return this.rectangle;
    }

    @Override
    public List<IfItem> getItems() {
      return null;
    }

    @Override
    public String getDescription() {
      return null;
    }

    @Override
    public void setTarget(IfTarget target) {

    }

    @Override
    public void removeTarget() {

    }

    @Override
    public void setPet(IfPet pet) {

    }

    @Override
    public void removePet() {

    }

    @Override
    public void setItems(List<IfItem> items) {

    }

    @Override
    public void addItem(IfItem item) {

    }

    @Override
    public void removeItem(IfItem item) {

    }

    @Override
    public void addPlayer(IfPlayer... players) {

    }

    @Override
    public void removePlayer(IfPlayer player) {

    }

  }
  //      <-- MockRoom

  //      --> MockWorld
  public class MockWorld implements IfWorld {
    List<IfRoom> rooms;
    // key: roomId, value: IRoom
    HashMap<Integer, IfRoom> roomMap;

    public MockWorld(List<IfRoom> rooms) {
      this.rooms = rooms;
      this.roomMap = new HashMap<>();
      for (IfRoom room : rooms) {
        roomMap.put(room.getRoomId(), room);
      }
    }

    @Override
    public boolean hasInit() {
      return false;
    }

    @Override
    public int getMaxTurns() {
      return 0;
    }

    @Override
    public int getRows() {
      return 0;
    }

    @Override
    public int getColumns() {
      return 0;
    }

    @Override
    public String getName() {
      return null;
    }

    @Override
    public List<IfPlayer> getPlayers() {
      return null;
    }

    @Override
    public List<IfRoom> getRooms() {
      return this.rooms;
    }

    @Override
    public IfRoom getRoomByRoomId(int roomId) {
      return null;
    }

    @Override
    public IfRoom getRoom(IfReadonlyRoom readonlyRoom) {
      return null;
    }

    @Override
    public IfTarget getTarget() {
      return null;
    }

    @Override
    public IfPet getPet() {
      return null;
    }

    @Override
    public List<Integer> getRoomIds() {
      return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public void initWorld() {
    }

    @Override
    public void setMaxTurns(int maxTurns) {

    }

    @Override
    public void addPlayers(IfPlayer... player) {

    }

    @Override
    public void setTarget(IfTarget target) {

    }

    @Override
    public List<IfRoom> getNeighbours(int roomId) {
      List<IfRoom> list = new ArrayList<>();
      IfRoom room = this.roomMap.get(roomId);

      for (IfRoom r : this.getRooms()) {
        if (r == room) {
          continue;
        }
        if (isNeighbor(r, room)) {
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
    //      <-- MockWorld
    // <-- Mock
  }
}
