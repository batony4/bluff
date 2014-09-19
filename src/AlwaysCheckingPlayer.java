import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

/**
 * Not lies
 * Not believes
 */
public class AlwaysCheckingPlayer implements BluffPlayer {

  @Override
  public Map.Entry<Integer, Integer> doMove(int myValue, List<Map.Entry<Integer, Integer>> previousMoves) {
    if (previousMoves.size() == 0) {
      return new AbstractMap.SimpleEntry<Integer, Integer>(1, myValue);
    } else {
      int prevCount = previousMoves.get(previousMoves.size() - 1).getKey();
      int prevValue = previousMoves.get(previousMoves.size() - 1).getValue();

      if (prevCount >= 2) {
        return null; // it's better to check
      }

      // prevCount is 1

      if (myValue == 0) {
        // i have '*'
        return new AbstractMap.SimpleEntry<Integer, Integer>(2, prevValue); // double opponent's move -- check is a guaranteed lose
      } else {
        return null;
      }
    }
  }
}
