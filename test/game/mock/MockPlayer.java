package game.mock;

import game.exception.CannotMoveException;
import game.exception.CarryItemFullException;
import game.model.IfItem;
import game.model.IfPlayer;
import game.model.IfRoom;
import game.model.IfTarget;

import java.util.List;
import java.util.Objects;

/**
 * @see game.terminal.TerminalGameControllerTest#testInitGameSetting
 */
public class MockPlayer implements IfPlayer {
  private String name;
  private int position;
  private int maxCarry;
  private boolean isRobot;

  public MockPlayer(String name, int position, int maxCarry, boolean isRobot) {
    this.name = name;
    this.position = position;
    this.maxCarry = maxCarry;
    this.isRobot = isRobot;
  }

  public MockPlayer(IfPlayer player) {
    this(player.getName(), player.getCurrentRoom().getRoomId(), player.getMaxCarry(), player.isRobot());
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.name, this.position, this.maxCarry, this.isRobot);
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MockPlayer that = (MockPlayer) o;
    return position == that.position &&
            maxCarry == that.maxCarry &&
            isRobot == that.isRobot &&
            name.equals(that.name);
  }

  @Override
  public String toString() {
    return "MockPlayer{" +
            "name='" + name + '\'' +
            ", position=" + position +
            ", maxCarry=" + maxCarry +
            ", isRobot=" + isRobot +
            '}';
  }

  @Override
  public void setName(String name) {

  }

  @Override
  public void setIsRobot(boolean isRobot) {

  }

  @Override
  public void setMaxCarry(int maxCarry) {
    this.maxCarry = maxCarry;
  }

  @Override
  public void pickItem(IfItem item) throws CarryItemFullException {

  }

  @Override
  public void moveTo(IfRoom room) throws CannotMoveException {

  }

  @Override
  public boolean attack(IfItem item, IfTarget targetModel) {
    return false;
  }


  @Override
  public String getName() {
    return null;
  }


  @Override
  public IfRoom getCurrentRoom() {
    return null;
  }

  @Override
  public boolean isRobot() {
    return false;
  }

  @Override
  public int getMaxCarry() {
    return this.maxCarry;
  }

  @Override
  public String getDescription() {
    return null;
  }

  @Override
  public List<IfItem> getCarryItems() {
    return null;
  }

}
