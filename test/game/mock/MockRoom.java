package game.mock;

import game.model.*;

import java.awt.*;
import java.util.List;

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
