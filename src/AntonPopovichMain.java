import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AntonPopovichMain {

  public static final BluffPlayer PLAYER = new MaxChancePlayer();

  public static void main(String[] args) throws IOException {
    while (true) {
      byte[] buf = new byte[1000];
      int read = System.in.read(buf);

      String inputStr = new String(buf, 0, read).trim();

      String[] parts = inputStr.split(" ");

      int myValue;
      if (parts[0].equals("*")) {
        myValue = 0;
      } else {
        myValue = Integer.parseInt(parts[0]);
      }

      String[] prev = new String[0];
      if (parts.length > 1) {
        prev = parts[1].split(",");
      }
      List<Map.Entry<Integer, Integer>> previousMoves = new ArrayList<Map.Entry<Integer, Integer>>();

      for (int i = 0; i < prev.length; i++) {
        String nextMove = prev[i];

        int count = Integer.parseInt(nextMove.substring(0, 1));
        String valueStr = nextMove.substring(1, 2);
        int value;
        if (valueStr.equals("*")) {
          value = 0;
        } else {
          value = Integer.parseInt(valueStr);
        }

        previousMoves.add(new AbstractMap.SimpleEntry<Integer, Integer>(count, value));
      }

      Map.Entry<Integer, Integer> moveResult = PLAYER.doMove(myValue, previousMoves);
      if (moveResult == null) {
        System.out.println("liar");
      } else {
        System.out.print(moveResult.getKey());
        System.out.println((moveResult.getValue() == 0) ? "*" : moveResult.getValue());
      }
    }
  }
}
