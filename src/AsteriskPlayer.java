import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

/**
 * Lies
 * Believes
 */
public class AsteriskPlayer implements BluffPlayer {

  @Override
  public Map.Entry<Integer, Integer> doMove(int myValue, List<Map.Entry<Integer, Integer>> previousMoves) {
    if (previousMoves.size() == 0) {
      if (myValue == 0) {
        // if I have '*', try to start with '1'
        return new AbstractMap.SimpleEntry<Integer, Integer>(1, 1);
      } else {
        // if I have not '*', try '*' as a beginning
        return new AbstractMap.SimpleEntry<Integer, Integer>(1, 0);
      }
    } else {
      int prevCount = previousMoves.get(previousMoves.size() - 1).getKey();
      int prevValue = previousMoves.get(previousMoves.size() - 1).getValue();

      if (prevCount >= 2) {
        return null;
      }

      // prevCount is 1

      if (myValue == 0) {
        return new AbstractMap.SimpleEntry<Integer, Integer>(2, prevValue);
      } else {
        // it was only one move - opponent's
        if (prevValue == 0) {
          // !!! MAYBE RETURN NULL - it seems that opponent lies
          return new AbstractMap.SimpleEntry<Integer, Integer>(2, myValue);
        } else {
          if (myValue == prevValue) {
            return new AbstractMap.SimpleEntry<Integer, Integer>(2, myValue);
          } else {
            return new AbstractMap.SimpleEntry<Integer, Integer>(1, 0);
          }
        }
      }
    }
  }
}
