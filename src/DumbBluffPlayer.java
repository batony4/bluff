import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

/**
 * Idiot
 */
public class DumbBluffPlayer implements BluffPlayer {

  @Override
  public Map.Entry<Integer, Integer> doMove(int myValue, List<Map.Entry<Integer, Integer>> previousMoves) {
    return new AbstractMap.SimpleEntry<Integer, Integer>(1, 0);
  }
}
