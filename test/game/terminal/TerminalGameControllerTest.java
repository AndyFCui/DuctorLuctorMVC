package game.terminal;

import game.controller.TerminalGameController;
import game.exception.GameNotReadyException;
import game.mock.MockPlayer;
import game.mock.MockRoom;
import game.mock.MockWorld;
import game.model.IfPlayer;
import game.model.IfRoom;
import game.model.IfWorld;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TerminalGameControllerTest {
  private IfWorld world;

  @Test()
  public void testInitGameSetting() throws IOException, GameNotReadyException {
    /**
     * TestData:
     * max turns:           4
     * number of player:    2
     * name of player1:     p1
     * position of player1: 4
     * max items can carry: 2
     * robot: false
     * name of player2:     p2
     * position of player1: 7
     * max items can carry: 3
     * robot: true
     */
    StringBuffer out = new StringBuffer();
    Reader in = new StringReader("4\n2\np1\n4\n2\nfalse\np2\n7\n3\ntrue\n");
    TerminalGameController controller = new TerminalGameController(in, out, world);

    controller.initGameSetting();

    assertEquals(4, world.getMaxTurns());
    assertEquals(2, world.getPlayers().size());

    List<IfPlayer> players = world.getPlayers();

    // Convert IPlayer to mockPlayer in order to compare the tested data.
    // MockPlayer rewrite hashcode,toString and equals methods, which will
    // only compare 4 fields values.
    List<MockPlayer> actualPlayers = new ArrayList<>();
    for (IfPlayer p : players) {
      MockPlayer mockPlayer = new MockPlayer(p);
      actualPlayers.add(mockPlayer);
    }

    List<MockPlayer> exceptedPlayers = new ArrayList<>();
    MockPlayer player1 = new MockPlayer("p1", 4, 2, false);
    MockPlayer player2 = new MockPlayer("p2", 7, 3, true);
    exceptedPlayers.add(player1);
    exceptedPlayers.add(player2);

    assertThat(actualPlayers, is(exceptedPlayers));
  }

  @Before
  public void initWorld() {
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

    this.world = new MockWorld(rooms);
  }

}
