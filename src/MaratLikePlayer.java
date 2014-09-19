import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

/**
 * Lies
 * Believes
 */
public class MaratLikePlayer implements BluffPlayer {

  @Override
  public Map.Entry<Integer, Integer> doMove(int myValue, List<Map.Entry<Integer, Integer>> previousMoves) {
    if (previousMoves.size() == 0) {
      return new AbstractMap.SimpleEntry<Integer, Integer>(1, 5);
    } else {
      int prevCount = previousMoves.get(previousMoves.size() - 1).getKey();
      int prevValue = previousMoves.get(previousMoves.size() - 1).getValue();

      if (prevCount >= 2) {
        return null; // it's better to check
      }

      // prevCount is 1

      if (myValue == 0) {
        // i have '*'
        return new AbstractMap.SimpleEntry<Integer, Integer>(2, prevValue); // double opponent's move
      } else {
        if (prevValue == 0) {
          return new AbstractMap.SimpleEntry<Integer, Integer>(2, myValue); // if opponent says '*', I bet for 2 my dice
        } else {
          if (prevValue < 5) {
            return new AbstractMap.SimpleEntry<Integer, Integer>(1, 5); // bet 5
          } else {
            if (myValue == 5) {
              return new AbstractMap.SimpleEntry<Integer, Integer>(2, 5); // bet double 5
            } else {
              return null;
            }
          }
        }
      }
    }
  }
}
