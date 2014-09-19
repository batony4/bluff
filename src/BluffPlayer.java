import java.util.List;
import java.util.Map;

public interface BluffPlayer {

  Map.Entry<Integer, Integer> doMove(int myValue, List<Map.Entry<Integer, Integer>> previousMoves);

}
