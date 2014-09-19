import java.util.*;

/**
 * The WINNER is: MaxChancePlayer!!!
 */
public class Framework {

  public static void main(String[] args) {

    BluffPlayer myPlayer = new MaxChancePlayer();
    BluffPlayer[] opponents = new BluffPlayer[] {
        new AlwaysCheckingPlayer(),
        new AsteriskPlayer(),
        new DontBelieveInHighValuesPlayer(),
        new DumbBluffPlayer(),
        new MaratLikePlayer(),
        new MaxChancePlayer(),
        new NotBluffingPlayer()
    };

    @SuppressWarnings("unchecked")
    Map.Entry<Integer, Integer>[] results = new Map.Entry[opponents.length];


    for (int i = 0; i < opponents.length; i++) {
      Map.Entry<Integer, Integer> res = playUsingPlayers(new BluffPlayer[] {myPlayer, opponents[i]});
      results[i] = res;
    }

    System.out.println();
    System.out.println("========================================");
    System.out.println();

    for (int i = 0; i < opponents.length; i++) {
      System.out.println("Final result for game " + myPlayer.getClass().getSimpleName() + ":" + opponents[i].getClass().getSimpleName() + " is " + results[i].getKey() + ":" + results[i].getValue());
    }


  }

  private static Map.Entry<Integer, Integer> playUsingPlayers(BluffPlayer[] players) {
    System.out.println("Player 0 is: " + players[0].getClass().getSimpleName());
    System.out.println("Player 1 is: " + players[1].getClass().getSimpleName());

    // init score
    int score0 = 0;
    int score1 = 0;
    int[] wonOnIllegalMoveOfOpponent = new int[2];
    int[] wonOnMyCheck = new int[2];
    int[] wonOnOpponentCheck = new int[2];
    int[][][][][] gameStats = new int[2][2][6][6][2]; // [player][myStart][myDice][opponentDice][0:lose, 1:win]

    int gameIndex = 0;
    int[] dice = new int[2];
    for (dice[0] = 0; dice[0] < 6; dice[0]++) {
      for (dice[1] = 0; dice[1] < 6; dice[1]++) {
        for (int firstToMove = 0; firstToMove <= 1; firstToMove++) {
          System.out.println("========================================");
          System.out.println("Starting game with index #" + gameIndex);
          System.out.println("Player 0 has: " + (dice[0] == 0 ? "*" : dice[0]));
          System.out.println("Player 1 has: " + (dice[1] == 0 ? "*" : dice[1]));
          System.out.println("First to move is: Player " + firstToMove);

          // continue init
          int playerToMove = firstToMove;
          List<Map.Entry<Integer, Integer>> moves = new ArrayList<Map.Entry<Integer, Integer>>();

          boolean finished = false;
          while (!finished) {
            Map.Entry<Integer, Integer> moveResult = players[playerToMove].doMove(dice[playerToMove], moves);

            if (moveResult == null) {
              System.out.println("Player " + playerToMove + " CHECKS");

              // check
              boolean predictionCorrect = checkPredictionCorrect(dice, moves.get(moves.size() - 1));
              if (predictionCorrect) {
                System.out.println("CORRECT! Player " + (1 - playerToMove) + " wins");
                wonOnOpponentCheck[1 - playerToMove]++;
                gameStats[0][1 - firstToMove][dice[0]][dice[1]][playerToMove]++;
                gameStats[1][firstToMove][dice[1]][dice[0]][1 - playerToMove]++;
              } else {
                System.out.println("LIE! Player " + playerToMove + " wins");
                wonOnMyCheck[playerToMove]++;
                gameStats[0][1 - firstToMove][dice[0]][dice[1]][1 - playerToMove]++;
                gameStats[1][firstToMove][dice[1]][dice[0]][playerToMove]++;
              }
              finished = true;
            } else {
              System.out.println("Player " + playerToMove + " moves: " + moveResult.getKey() + " times " + moveResult.getValue());

              // move made
              boolean moveCorrect = checkLastMoveCorrect(moveResult, moves);
              if (!moveCorrect) {
                System.out.println("INCORRECT MOVE! Player " + (1 - playerToMove) + " wins");
                wonOnIllegalMoveOfOpponent[1 - playerToMove]++;
                gameStats[0][1 - firstToMove][dice[0]][dice[1]][1 - playerToMove]++;
                gameStats[1][firstToMove][dice[1]][dice[0]][playerToMove]++;
                finished = true;
              } else {
                moves.add(moveResult);
                playerToMove = 1 - playerToMove;
              }
            }
          }

          System.out.println("New score:");

          score0 = wonOnIllegalMoveOfOpponent[0] + wonOnMyCheck[0] + wonOnOpponentCheck[0];
          System.out.println(" Player 0: " + score0 + " points (" + wonOnIllegalMoveOfOpponent[0] + " on opponent's illegal moves, " + wonOnMyCheck[0] + " on his checks, " + wonOnOpponentCheck[0] + " on opponent's wrong checks");

          score1 = wonOnIllegalMoveOfOpponent[1] + wonOnMyCheck[1] + wonOnOpponentCheck[1];
          System.out.println(" Player 1: " + score1 + " points (" + wonOnIllegalMoveOfOpponent[1] + " on opponent's illegal moves, " + wonOnMyCheck[1] + " on his checks, " + wonOnOpponentCheck[1] + " on opponent's wrong checks");

          System.out.println();
          System.out.println("Win matrix (for player 0): <myStart:win-lose> | <notMyStart:win-lose>");
          System.out.println("\t0\t\t\t1\t\t\t2\t\t\t3\t\t\t4\t\t\t5");
          for (int i = 0; i < 6; i++) {
            System.out.print(i + ": \t");
            for (int j = 0; j < 6; j++) {
              System.out.print(gameStats[0][1][i][j][1] + "-" + gameStats[0][1][i][j][0] + " | " + gameStats[0][0][i][j][1] + "-" + gameStats[0][0][i][j][0] + "\t");
            }
            System.out.println();
          }
        }
      }
    }

    return new AbstractMap.SimpleEntry<Integer, Integer>(score0, score1);
  }

  private static boolean checkPredictionCorrect(int[] dice, Map.Entry<Integer, Integer> lastMove) {
    int[] diceCount = new int[6];
    for (int next : dice) {
      diceCount[next]++;
      if (next == 0) {
        for (int i = 1; i <= 5; i++) {
          diceCount[i]++;
        }
      }
    }

    boolean result = diceCount[(lastMove.getValue())] >= lastMove.getKey();
    System.out.println("Real count of " + lastMove.getValue() + " is " + diceCount[(lastMove.getValue())] + ", checked result is " + lastMove.getKey() + ". It is " + (result ? "CORRECT" : "LIE"));

    return result;
  }

  private static boolean checkLastMoveCorrect(Map.Entry<Integer, Integer> moveResult, List<Map.Entry<Integer, Integer>> previousMoves) {
    if (moveResult.getKey() <= 0) {
      return false;
    }

    if (previousMoves.size() == 0) {
      return true;
    }

    Map.Entry<Integer, Integer> previousMove = previousMoves.get(previousMoves.size() - 1);

    if (moveResult.getValue() == 0) {
      if (previousMove.getValue() == 0) {
        return moveResult.getKey() > previousMove.getKey();
      } else {
        return moveResult.getKey() * 2 > previousMove.getKey();
      }
    } else {
      if (previousMove.getValue() == 0) {
        return moveResult.getKey() >= previousMove.getKey() * 2;
      } else {
        if (moveResult.getValue().compareTo(previousMove.getValue()) > 0) {
          return moveResult.getKey() >= previousMove.getKey();
        } else {
          return moveResult.getKey() > previousMove.getKey();
        }
      }
    }
  }
}
