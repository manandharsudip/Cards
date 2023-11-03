import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Cards {

  public String[] sortFunc(
    List<String> playersHand,
    String[] myHand,
    String[] ranks
  ) {
    String[] myHandd = new String[3];
    int ind = 0;

    for (String card : playersHand) {
      int index = card.indexOf(" ");
      myHandd[ind] = card.substring(0, index);
      ind++;
    }

    Arrays.sort(
      myHandd,
      (rank1, rank2) -> {
        int inde1 = Arrays.asList(ranks).indexOf(rank1);
        int inde2 = Arrays.asList(ranks).indexOf(rank2);
        return Integer.compare(inde1, inde2);
      }
    );

    return myHandd;
  }

  public Players ranking(List<Players> playersList, String[] ranks) {
    Players winner = playersList.get(0);
    int rank1 = Arrays.asList(ranks).indexOf(winner.getBiggestCard());
    int rank2 = Arrays.asList(ranks).indexOf(winner.getMiddleCard());
    int rank3 = Arrays.asList(ranks).indexOf(winner.getSmallestCard());
    boolean isFirstElement = true;
    for (Players player : playersList) {
      int rank11 = Arrays.asList(ranks).indexOf(player.getBiggestCard());
      int rank22 = Arrays.asList(ranks).indexOf(player.getMiddleCard());
      int rank33 = Arrays.asList(ranks).indexOf(player.getSmallestCard());
      if (isFirstElement) {
        isFirstElement = false;
        continue;
      } else if (rank11 > rank1) {
        rank1 = rank11;
        rank2 = rank22;
        rank3 = rank33;
        winner = player;
      } else if (rank11 == rank1 && rank22 > rank2) {
        rank1 = rank11;
        rank2 = rank22;
        rank3 = rank33;
        winner = player;
      } else if (rank11 == rank1 && rank22 == rank2 && rank33 > rank3) {
        rank1 = rank11;
        rank2 = rank22;
        rank3 = rank33;
        winner = player;
      } else if (rank11 == rank1 && rank22 == rank2 && rank33 == rank3) {
        winner = null;
      }
    }
    return winner;
  }

  public Players setPlayerInfo(
    Players player,
    String name,
    List<String> hand,
    String hand1,
    String hand2,
    String hand3,
    String status
  ) {
    player.setName(name);
    player.setHand(hand);
    player.setBiggestCard(hand1);
    player.setMiddleCard(hand2);
    player.setSmallestCard(hand3);
    player.setStatus(status);
    return player;
  }

  public void checkTrail(
    List<Players> playersList,
    List<Players> trailPlayersList,
    List<List<String>> players,
    Game my_game,
    String[] ranks,
    int numPlayers
  ) {
    for (int i = 0; i < numPlayers; i++) {
      boolean trail = true;
      int index1 = players.get(i).get(0).indexOf(" ");
      String num = players.get(i).get(0).substring(0, index1);

      String[] myHand = new String[3];
      myHand = sortFunc(players.get(i), myHand, ranks);

      for (String card : players.get(i)) {
        int index = card.indexOf(" ");
        if (!card.substring(0, index).equals(num)) {
          trail = false;
        }
      }

      if (trail) {
        Players player = new Players();
        String playerName = "Player " + (i + 1);

        boolean playerExists = playersList
          .stream()
          .anyMatch(person -> person.getName().equals(playerName));

        if (!playerExists) {
          player =
            setPlayerInfo(
              player,
              playerName,
              players.get(i),
              myHand[2],
              myHand[1],
              myHand[0],
              "Trail"
            );
          playersList.add(player);
          trailPlayersList.add(player);
          my_game.setTrail(my_game.getTrail() + 1);
        }
      }
    }
  }

  public void checkDoubleRun(
    List<Players> playersList,
    List<Players> doubleRunPlayersList,
    List<List<String>> players,
    Game my_game,
    String[] ranks,
    int numPlayers
  ) {
    for (int i = 0; i < numPlayers; i++) {
      boolean color = true;

      int index1 = players.get(i).get(0).lastIndexOf(" ");
      String num = players.get(i).get(0).substring(index1 + 1);

      for (String card : players.get(i)) {
        int index = card.lastIndexOf(" ");
        if (!card.substring(index + 1).equals(num)) {
          color = false;
        }
      }

      // to check run
      String[] myHand = new String[3];
      myHand = sortFunc(players.get(i), myHand, ranks);

      int rank1Index = Arrays.asList(ranks).indexOf(myHand[0]);
      int rank2Index = Arrays.asList(ranks).indexOf(myHand[1]);
      int rank3Index = Arrays.asList(ranks).indexOf(myHand[2]);

      // custom logic to check the run in the hand
      if (
        (
          Math.abs(rank2Index - rank1Index) == 1 &&
          Math.abs(rank3Index - rank2Index) == 1 &&
          color
        ) ||
        (
          Math.abs(rank2Index - rank1Index) == 1 &&
          Math.abs(rank3Index - rank1Index) == 12
        ) &&
        color ||
        (
          Math.abs(rank2Index - rank1Index) == 1 &&
          Math.abs(rank3Index - rank2Index) == 11
        ) &&
        color
      ) {
        Players player = new Players();
        String playerName = "Player " + (i + 1);

        boolean playerExists = playersList
          .stream()
          .anyMatch(person -> person.getName().equals(playerName));

        if (!playerExists) {
          player =
            setPlayerInfo(
              player,
              playerName,
              players.get(i),
              myHand[2],
              myHand[1],
              myHand[0],
              "Double Run"
            );
          playersList.add(player);
          doubleRunPlayersList.add(player);
          my_game.setDoubleRun(my_game.getDoubleRun() + 1);
        }
      }
    }
  }

  public void checkRun(
    List<Players> playersList,
    List<Players> runPlayersList,
    List<List<String>> players,
    Game my_game,
    String[] ranks,
    int numPlayers
  ) {
    for (int i = 0; i < numPlayers; i++) {
      String[] myHand = new String[3];
      myHand = sortFunc(players.get(i), myHand, ranks);

      int rank1Index = Arrays.asList(ranks).indexOf(myHand[0]);
      int rank2Index = Arrays.asList(ranks).indexOf(myHand[1]);
      int rank3Index = Arrays.asList(ranks).indexOf(myHand[2]);

      if (
        Math.abs(rank2Index - rank1Index) == 1 &&
        (
          Math.abs(rank3Index - rank2Index) == 1 ||
          Math.abs(rank3Index - rank1Index) == 12 ||
          Math.abs(rank3Index - rank2Index) == 11
        )
      ) {
        Players player = new Players();
        String playerName = "Player " + (i + 1);

        boolean playerExists = playersList
          .stream()
          .anyMatch(person -> person.getName().equals(playerName));

        if (!playerExists) {
          player =
            setPlayerInfo(
              player,
              playerName,
              players.get(i),
              myHand[2],
              myHand[1],
              myHand[0],
              "Run"
            );
          playersList.add(player);
          runPlayersList.add(player);
          my_game.setRun(my_game.getRun() + 1);
        }
      }
    }
  }

  public void checkColor(
    List<Players> playersList,
    List<Players> colorPlayersList,
    List<List<String>> players,
    Game my_game,
    String[] ranks,
    int numPlayers
  ) {
    for (int i = 0; i < numPlayers; i++) {
      boolean color = true;
      int index1 = players.get(i).get(0).lastIndexOf(" ");
      String num = players.get(i).get(0).substring(index1 + 1);

      for (String card : players.get(i)) {
        int index = card.lastIndexOf(" ");
        if (!card.substring(index + 1).equals(num)) {
          color = false;
        }
      }

      // sorting cards in hand
      String[] myHand = new String[3];
      myHand = sortFunc(players.get(i), myHand, ranks);

      if (color) {
        Players player = new Players();
        String playerName = "Player " + (i + 1);

        boolean playerExists = playersList
          .stream()
          .anyMatch(person -> person.getName().equals(playerName));

        if (!playerExists) {
          player =
            setPlayerInfo(
              player,
              playerName,
              players.get(i),
              myHand[2],
              myHand[1],
              myHand[0],
              "Color"
            );
          playersList.add(player);
          colorPlayersList.add(player);
          my_game.setColor(my_game.getColor() + 1);
        }
      }
    }
  }

  public void checkPair(
    List<Players> playersList,
    List<Players> pairPlayersList,
    List<List<String>> players,
    Game my_game,
    String[] ranks,
    int numPlayers
  ) {
    for (int i = 0; i < numPlayers; i++) {
      String num = "";
      String pairOf = "";
      String thirdCard = "";

      int count = 0;

      for (String mCard : players.get(i)) {
        int mIndex = mCard.indexOf(" ");
        num = mCard.substring(0, mIndex);
        for (String card : players.get(i)) {
          int index = card.indexOf(" ");
          if (card.substring(0, index).equals(num)) {
            pairOf = num;
            count++;
          } else {
            thirdCard = card.substring(0, index);
          }
        }
        if (count == 2) {
          break;
        }
        count = 0;
      }

      if (count == 2) {
        Players player = new Players();
        String playerName = "Player " + (i + 1);

        boolean playerExists = playersList
          .stream()
          .anyMatch(person -> person.getName().equals(playerName));

        if (!playerExists) {
          player =
            setPlayerInfo(
              player,
              playerName,
              players.get(i),
              pairOf,
              pairOf,
              thirdCard,
              "Pair"
            );
          playersList.add(player);
          pairPlayersList.add(player);
          my_game.setPair(my_game.getPair() + 1);
        }
      }
    }
  }

  public void defaultHand(
    List<Players> playersList,
    List<Players> nothingPlayersList,
    List<List<String>> players,
    Game my_game,
    String[] ranks,
    int numPlayers
  ) {
    for (int i = 0; i < numPlayers; i++) {
      // sorting cards in hand
      String[] myHand = new String[3];
      myHand = sortFunc(players.get(i), myHand, ranks);

      Players player = new Players();
      String playerName = "Player " + (i + 1);

      boolean playerExists = playersList
        .stream()
        .anyMatch(person -> person.getName().equals(playerName));

      if (!playerExists) {
        player =
          setPlayerInfo(
            player,
            playerName,
            players.get(i),
            myHand[2],
            myHand[1],
            myHand[0],
            "Power"
          );
        playersList.add(player);
        nothingPlayersList.add(player);
        my_game.setNothing(my_game.getNothing() + 1);
      }
    }
  }

  public static void main(String[] args) {
    List<String> deck = new ArrayList<>();
    String[] suits = { "Hearts", "Diamond", "Club", "Spade" };
    String[] ranks = {
      "2",
      "3",
      "4",
      "5",
      "6",
      "7",
      "8",
      "9",
      "10",
      "J",
      "Q",
      "K",
      "A",
    };

    // Create the deck
    for (String suit : suits) {
      for (String rank : ranks) {
        deck.add(rank + " of " + suit);
      }
    }

    // Shuffle the deck
    Collections.shuffle(deck);

    Scanner myObj = new Scanner(System.in);
    System.out.print("Enter Number of players: ");

    int numPlayers = myObj.nextInt();

    // int numPlayers = 4; /
    int cardsPerPlayer = 3;

    if (numPlayers > 0 && numPlayers <= 17) {
      List<List<String>> players = new ArrayList<>();
      for (int i = 0; i < numPlayers; i++) {
        players.add(new ArrayList<>());
      }

      // Cards Distribution
      int currentPlayer = 0;
      for (int i = 0; i < numPlayers * cardsPerPlayer; i++) {
        String card = deck.remove(0);
        players.get(currentPlayer).add(card);
        currentPlayer = (currentPlayer + 1) % numPlayers;
      }

      // List<List<String>> myPlayers = List.of(
      //   List.of("A of Hearts", "A of Spade", "A of Club"),
      //   List.of("J of Hearts", "J of Diamond", "J of Club"),
      //   List.of("10 of Spade", "10 of Diamond", "10 of Club"),
      //   List.of("8 of Spade", "8 of Diamond", "8 of Club"),
      //   List.of("3 of Spade", "A of Spade", "2 of Spade"),
      //   List.of("9 of Spade", "10 of Spade", "J of Spade"),
      //   List.of("A of Spade", "K of Spade", "Q of Spade"),
      //   List.of("A of Club", "K of Club", "Q of Club"),
      //   List.of("J of Spade", "K of Spade", "Q of Spade"),
      //   List.of("K of Spade", "J of Spade", "Q of Diamond"),
      //   List.of("K of Diamond", "J of Club", "Q of Spade"),
      //   List.of("8 of Spade", "9 of Spade", "10 of Diamond"),
      //   List.of("A of Spade", "2 of Spade", "3 of Diamond"),
      //   List.of("A of Spade", "2 of Spade", "3 of Diamond"),
      //   List.of("A of Club", "2 of Club", "4 of Club"),
      //   List.of("5 of Club", "8 of Club", "3 of Club"),
      //   List.of("5 of Diamond", "7 of Diamond", "3 of Diamond"),
      //   List.of("5 of Diamond", "7 of Diamond", "3 of Diamond"),
      //   List.of("A of Spade", "2 of Spade", "10 of Spade"),
      //   List.of("K of Club", "K of Diamond", "10 of Hearts"),
      //   List.of("K of Spade", "K of Hearts", "10 of Spade"),
      //   List.of("A of Club", "A of Diamond", "6 of Hearts"),
      //   List.of("A of Club", "A of Diamond", "5 of Hearts"),
      //   List.of("7 of Hearts", "Q of Spade", "6 of Club"),
      //   List.of("7 of Club", "J of Spade", "6 of Spade")
      // );

      Cards myGame = new Cards();
      Game my_game = new Game();

      List<Players> playersList = new ArrayList<>();
      List<Players> trailPlayersList = new ArrayList<>();
      List<Players> doubleRunPlayersList = new ArrayList<>();
      List<Players> runPlayersList = new ArrayList<>();
      List<Players> colorPlayersList = new ArrayList<>();
      List<Players> pairPlayersList = new ArrayList<>();
      List<Players> nothingPlayersList = new ArrayList<>();

      // int playersCount = myPlayers.size();

      // myGame.checkTrail(playersList, trailPlayersList, myPlayers, my_game, ranks, playersCount);
      // myGame.checkDoubleRun(playersList, doubleRunPlayersList, myPlayers, my_game, ranks, playersCount);
      // myGame.checkRun(playersList, runPlayersList, myPlayers, my_game, ranks, playersCount);
      // myGame.checkColor(playersList, colorPlayersList, myPlayers, my_game, ranks, playersCount);
      // myGame.checkPair(playersList, pairPlayersList, myPlayers, my_game, ranks, playersCount);
      // myGame.defaultHand(playersList, nothingPlayersList, myPlayers, my_game, ranks, playersCount);
      // System.out.println("-----------------------------");
      myGame.checkTrail(
        playersList,
        trailPlayersList,
        players,
        my_game,
        ranks,
        numPlayers
      );
      myGame.checkDoubleRun(
        playersList,
        doubleRunPlayersList,
        players,
        my_game,
        ranks,
        numPlayers
      );
      myGame.checkRun(
        playersList,
        runPlayersList,
        players,
        my_game,
        ranks,
        numPlayers
      );
      myGame.checkColor(
        playersList,
        colorPlayersList,
        players,
        my_game,
        ranks,
        numPlayers
      );
      myGame.checkPair(
        playersList,
        pairPlayersList,
        players,
        my_game,
        ranks,
        numPlayers
      );
      myGame.defaultHand(
        playersList,
        nothingPlayersList,
        players,
        my_game,
        ranks,
        numPlayers
      );

      System.out.println("----------------------------------");

      for (Players player : playersList) {
        if (my_game.getTrail() > 0 && player.getStatus() == "Trail") {
          if (my_game.getTrail() == 1) {
            player.setState("Winner");
            my_game.setWinner(player);
            break;
          } else if (my_game.getTrail() > 1) {
            Players winner = trailPlayersList.get(0);
            int rank1 = Arrays.asList(ranks).indexOf(winner.getBiggestCard());
            for (Players trailPlayer : trailPlayersList) {
              int rank = Arrays
                .asList(ranks)
                .indexOf(trailPlayer.getBiggestCard());
              if (rank > rank1) {
                winner = trailPlayer;
              } else {
                trailPlayer.setState("Lost!!");
              }
            }
            if (winner != null) {
              winner.setState("Winner");
              my_game.setWinner(winner);
            }
            break;
          }
        } else if (
          my_game.getDoubleRun() > 0 && player.getStatus() == "Double Run"
        ) {
          if (my_game.getDoubleRun() == 1) {
            player.setState("Winner");
            my_game.setWinner(player);
            break;
          } else if (my_game.getDoubleRun() > 1) {
            // Ranking the players
            Players winner = myGame.ranking(doubleRunPlayersList, ranks);

            if (winner != null) {
              winner.setState("Winner");
              my_game.setWinner(winner);
            } else {
              System.out.println("Game Draw");
            }
            break;
          }
        } else if (my_game.getRun() > 0 && player.getStatus() == "Run") {
          if (my_game.getRun() == 1) {
            player.setState("Winner");
            my_game.setWinner(player);
            break;
          } else if (my_game.getRun() > 1) {
            Players winner = myGame.ranking(runPlayersList, ranks);
            if (winner != null) {
              winner.setState("Winner");
              my_game.setWinner(winner);
            } else {
              System.out.println("Game Draw");
            }
            break;
          }
        } else if (my_game.getColor() > 0 && player.getStatus() == "Color") {
          if (my_game.getColor() == 1) {
            player.setState("Winner");
            my_game.setWinner(player);
            break;
          } else if (my_game.getColor() > 1) {
            Players winner = myGame.ranking(colorPlayersList, ranks);
            if (winner != null) {
              winner.setState("Winner");
              my_game.setWinner(winner);
            } else {
              System.out.println("Game Draw");
            }
            break;
          }
        } else if (my_game.getPair() > 0 && player.getStatus() == "Pair") {
          if (my_game.getPair() == 1) {
            player.setState("Winner");
            my_game.setWinner(player);
            break;
          } else if (my_game.getPair() > 1) {
            Players winner = pairPlayersList.get(0);
            int rank1 = Arrays.asList(ranks).indexOf(winner.getBiggestCard());
            int rank3 = Arrays.asList(ranks).indexOf(winner.getSmallestCard());
            boolean isFirstElement = true;
            for (Players pairPlayer : pairPlayersList) {
              int rank11 = Arrays
                .asList(ranks)
                .indexOf(pairPlayer.getBiggestCard());
              int rank33 = Arrays
                .asList(ranks)
                .indexOf(pairPlayer.getSmallestCard());
              if (isFirstElement) {
                isFirstElement = false;
                continue;
              } else if (rank11 > rank1) {
                rank1 = rank11;
                rank3 = rank33;
                winner = pairPlayer;
              } else if (rank11 == rank1 && rank33 > rank3) {
                rank1 = rank11;
                rank3 = rank33;
                winner = pairPlayer;
              } else if (rank11 == rank1 && rank33 == rank3) {
                winner = null;
              }
            }
            if (winner != null) {
              winner.setState("Winner");
              my_game.setWinner(winner);
            } else {
              System.out.println("Game Draw");
            }
            break;
          }
        } else if (my_game.getNothing() > 0 && player.getStatus() == "Power") {
          if (my_game.getNothing() == 1) {
            player.setState("Winner");
            my_game.setWinner(player);
            break;
          } else if (my_game.getNothing() > 1) {
            Players winner = myGame.ranking(nothingPlayersList, ranks);
            if (winner != null) {
              winner.setState("Winner");
              my_game.setWinner(winner);
            } else {
              System.out.println("Game Draw");
            }
            break;
          }
        } else {
          player.setState("Lost!!");
        }
      }

      for (Players player : playersList) {
        System.out.println("Name: " + player.getName());
        System.out.println("Hand: " + player.getHand());
        System.out.println("Status: " + player.getStatus());
        System.out.println("State: " + player.getState());
        if (player.getStatus() == "Trail") {
          System.out.println("Trail of: " + player.getBiggestCard());
        } else if (player.getStatus() == "Pair") {
          System.out.println("Pair of: " + player.getBiggestCard());
          System.out.println("Third Card: " + player.getSmallestCard());
        } else if (player.getStatus() == "Power") {
          System.out.println("Power of: " + player.getBiggestCard());
          System.out.println("Middle Card: " + player.getMiddleCard());
          System.out.println("Third Card: " + player.getSmallestCard());
        } else {
          System.out.println("Big Card: " + player.getBiggestCard());
          System.out.println("Middle Card: " + player.getMiddleCard());
          System.out.println("Third Card: " + player.getSmallestCard());
        }
        System.out.println("----------------------------------");
      }

      System.out.println(
        "--------- Some Game Stats -------- \nTrail: " +
        my_game.getTrail() +
        "\nDouble Run: " +
        my_game.getDoubleRun() +
        "\nRun: " +
        my_game.getRun() +
        "\nColor: " +
        my_game.getColor() +
        "\nPair: " +
        my_game.getPair() +
        "\nPower: " +
        my_game.getNothing()
      );

      System.out.println("----------------------------------");
      if (my_game.getWinner() != null) {
        System.out.println(
          "Winner: " +
          my_game.getWinner().getName() +
          "\nHand: " +
          my_game.getWinner().getHand() +
          "\nStatus: " +
          my_game.getWinner().getStatus()
        );
      } else {
        System.out.println("None, it's draw!!");
      }
    } else {
      System.out.println("Players should be greater than 0 and less than 18.");
    }

    myObj.close();
  }
}
