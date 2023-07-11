package game.mock;

import game.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MockWorld implements IfWorld {
  List<IfRoom> rooms;
  // key: roomId, value: IRoom
  HashMap<Integer, IfRoom> roomMap;
  List<IfPlayer> players;
  private int maxTurns;

  public MockWorld(List<IfRoom> rooms) {
    this.rooms = rooms;
    this.roomMap = new HashMap<>();
    for (IfRoom room : rooms) {
      roomMap.put(room.getRoomId(), room);
    }
    this.players = new ArrayList<>();
  }

  @Override
  public boolean hasInit() {
    return false;
  }

  @Override
  public int getMaxTurns() {
    return this.maxTurns;
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
    return this.players;
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
    return this.rooms.stream().map(IfRoom::getRoomId).collect(Collectors.toList());
  }

  @Override
  public void setName(String name) {

  }

  @Override
  public void initWorld() {
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
}
