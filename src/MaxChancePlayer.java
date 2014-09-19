import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

/**
 * Not lies
 * Believes
 */
public class MaxChancePlayer implements BluffPlayer {

  @Override
  public Map.Entry<Integer, Integer> doMove(int myValue, List<Map.Entry<Integer, Integer>> previousMoves) {
    if (previousMoves.size() == 0) {
      return new AbstractMap.SimpleEntry<Integer, Integer>(1, 1);
//      if (myValue == 0) {
//        return new AbstractMap.SimpleEntry<Integer, Integer>(1, 1);
//      } else if (myValue <= 2) {
//        return new AbstractMap.SimpleEntry<Integer, Integer>(1, 4);
//      } else {
//        // 4 or 5
//        return new AbstractMap.SimpleEntry<Integer, Integer>(1, 1);
//      }
    } else {
      int prevCount = previousMoves.get(previousMoves.size() - 1).getKey();
      int prevValue = previousMoves.get(previousMoves.size() - 1).getValue();

      if (prevCount >= 2) {
        if ((myValue == 0) && (previousMoves.size() >= 2)) {
          int prevPrevCount = previousMoves.get(previousMoves.size() - 2).getKey();
          int prevPrevValue = previousMoves.get(previousMoves.size() - 2).getValue();

          if ((prevValue == prevPrevValue) && (prevCount == 2) && (prevPrevCount == 1)) {
            return new AbstractMap.SimpleEntry<Integer, Integer>(2, 0); // try 2 stars, we have a chance to win
          }
        }
        return null; // it's better to check
      }

      // prevCount is 1

      if (myValue == 0) {
        // i have '*'
        return new AbstractMap.SimpleEntry<Integer, Integer>(2, prevValue); // double opponent's move
      } else {
        if (prevValue == 0) {
          return null; // don't believe
//          return new AbstractMap.SimpleEntry<Integer, Integer>(2, myValue); // if opponent has '*', I bet for 2 my dice
        } else {
          if (prevValue < myValue) {
            return new AbstractMap.SimpleEntry<Integer, Integer>(1, myValue); // switch to my dice
          } else if (prevValue == myValue) {
            return new AbstractMap.SimpleEntry<Integer, Integer>(2, myValue); // two dice like mine
          } else {
            // prevValue > myValue
            return null; // do not believe, no other chances to win
          }
        }
      }
    }
  }
}


// TODO если у меня звезда и соперник поставил цифру ниже - ставить пятëрку или двойное значение его цифры?``
// TODO если соперник говорит пятëрку - у него звезда или пятëрка?
// TODO если соперник вторым ходом повторяет мою цифру, а у меня звезда - проверять или поставить две звезды?
