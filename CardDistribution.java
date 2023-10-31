import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CardDistribution {

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

  public List<List<String>> checkTrail(
    List<Players> playersList,
    List<List<String>> players,
    String[] ranks,
    int numPlayers
  ) {
    for (int i = 0; i < numPlayers; i++) {
      // System.out.println("Player " + (i + 1) + " Hand: " + players.get(i));

      boolean trail = true;
      // to get the index of first white space to get substring up to that space
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
        player.setName("Player" + i);
        player.setStatus("Trial");
        player.setHand(players.get(i));
        player.setBiggestCard(myHand[2]);
        player.setMiddleCard(myHand[1]);
        player.setSmallestCard(myHand[0]);
        playersList.add(player);
      }
    }
    return null;
  }

  public List<List<String>> checkDoubleRun(
    List<Players> playersList,
    List<List<String>> players,
    String[] ranks,
    int numPlayers
  ) {
    for (int i = 0; i < numPlayers; i++) {
      boolean color = true;

      // to check color
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

      // System.out.println("Rank 1: " + rank1Index);
      // System.out.println("Rank 2: " + rank2Index);
      // System.out.println("Rank 3: " + rank3Index);

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
        player.setName("Player" + i);
        player.setHand(players.get(i));
        player.setStatus("Double Run");
        player.setBiggestCard(myHand[2]);
        player.setMiddleCard(myHand[1]);
        player.setSmallestCard(myHand[0]);
        playersList.add(player);
      }
    }
    return null;
  }

  public List<List<String>> checkRun(
    List<Players> playersList,
    List<List<String>> players,
    String[] ranks,
    int numPlayers
  ) {
    for (int i = 0; i < numPlayers; i++) {
      // sorting cards in hand
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
        //  ||
        // (
        //   Math.abs(rank3Index - rank2Index) == 1 &&
        //   (rank1Index == 0 && rank2Index == 12) ||
        //   (rank1Index == 12 && rank2Index == 0)
        // )
      ) {
        Players player = new Players();
        String playerName = "Player" + i;

        boolean playerExists = playersList
          .stream()
          .anyMatch(person -> person.getName().equals(playerName));

        if (!playerExists) {
          // System.out.println(player.getStatus());
          player.setName("Player" + i);
          player.setHand(players.get(i));
          player.setStatus("Run");
          player.setBiggestCard(myHand[2]);
          player.setMiddleCard(myHand[1]);
          player.setSmallestCard(myHand[0]);
          playersList.add(player);
        }
      }
    }

    return null;
  }

  public List<List<String>> checkColor(
    List<Players> playersList,
    List<List<String>> players,
    String[] ranks,
    int numPlayers
  ) {
    for (int i = 0; i < numPlayers; i++) {
      // System.out.println("Player " + (i + 1) + " Hand: " + players.get(i));

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
        // System.out.println("Color");
        Players player = new Players();
        String playerName = "Player" + i;

        boolean playerExists = playersList
          .stream()
          .anyMatch(person -> person.getName().equals(playerName));

        if (!playerExists) {
          // System.out.println(player.getStatus());
          player.setName("Player" + i);
          player.setHand(players.get(i));
          player.setStatus("Color");
          player.setBiggestCard(myHand[2]);
          player.setMiddleCard(myHand[1]);
          player.setSmallestCard(myHand[0]);
          playersList.add(player);
        }
      }
    }

    return null;
  }

  public List<List<String>> checkPair(
    List<Players> playersList,
    List<List<String>> players,
    String[] ranks,
    int numPlayers
  ) {
    for (int i = 0; i < numPlayers; i++) {
      // System.out.println("Player " + (i + 1) + " Hand: " + players.get(i));

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
        String playerName = "Player" + i;

        boolean playerExists = playersList
          .stream()
          .anyMatch(person -> person.getName().equals(playerName));

        if (!playerExists) {
          player.setName("Player" + i);
          player.setHand(players.get(i));
          player.setStatus("Pair");
          player.setBiggestCard(pairOf);
          player.setMiddleCard(pairOf);
          player.setSmallestCard(thirdCard);
          playersList.add(player);
        }
      }
    }

    return null;
  }

  public List<List<String>> defaultHand(
    List<Players> playersList,
    List<List<String>> players,
    String[] ranks,
    int numPlayers
  ) {
    for (int i = 0; i < numPlayers; i++) {
      // sorting cards in hand
      String[] myHand = new String[3];
      myHand = sortFunc(players.get(i), myHand, ranks);

      Players player = new Players();
      String playerName = "Player" + i;

      boolean playerExists = playersList
        .stream()
        .anyMatch(person -> person.getName().equals(playerName));

      if (!playerExists) {
        player.setName("Player" + i);
        player.setHand(players.get(i));
        player.setStatus("Nothing");
        player.setBiggestCard(myHand[2]);
        player.setMiddleCard(myHand[1]);
        player.setSmallestCard(myHand[0]);
        playersList.add(player);
      }
    }

    return null;
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

    int numPlayers = 4; // Change this to the number of players you have
    int cardsPerPlayer = 3;

    // Create an array to represent the players and their hands
    List<List<String>> players = new ArrayList<>();
    for (int i = 0; i < numPlayers; i++) {
      players.add(new ArrayList<>());
    }

    // Distribute cards to players
    int currentPlayer = 0;
    for (int i = 0; i < numPlayers * cardsPerPlayer; i++) {
      String card = deck.remove(0); // Remove the top card from the deck
      players.get(currentPlayer).add(card); // Add the card to the current player's hand
      currentPlayer = (currentPlayer + 1) % numPlayers; // Move to the next player
    }

    List<List<String>> myPlayers = List.of(
      // List.of("J of Hearts", "J of Diamond", "J of Club"),
      // List.of("10 of Spade", "10 of Diamond", "10 of Club"),
      // List.of("8 of Spade", "8 of Diamond", "8 of Club"),
      // List.of("3 of Spade", "A of Spade", "2 of Spade"),
      // List.of("9 of Spade", "10 of Spade", "J of Spade"),
      // List.of("A of Spade", "K of Spade", "Q of Spade")
      // List.of("A of Club", "K of Club", "Q of Club")
      // List.of("J of Spade", "K of Spade", "Q of Spade")
      // List.of("K of Spade", "J of Spade", "Q of Diamond"),
      // List.of("K of Diamond", "J of Club", "Q of Spade"),
      // List.of("8 of Spade", "9 of Spade", "10 of Diamond"),
      // List.of("A of Spade", "2 of Spade", "3 of Diamond")
      // List.of("A of Spade", "2 of Spade", "3 of Diamond")
      // List.of("A of Club", "2 of Club", "4 of Club"),
      // List.of("5 of Club", "8 of Club", "3 of Club"),
      // List.of("5 of Diamond", "7 of Diamond", "3 of Diamond"),
      // List.of("5 of Diamond", "7 of Diamond", "3 of Diamond")
      // List.of("A of Spade", "2 of Spade", "10 of Spade")
      // List.of("K of Club", "K of Diamond", "10 of Hearts"),
      // List.of("K of Spade", "K of Hearts", "10 of Spade"),
      // List.of("A of Club", "A of Diamond", "6 of Hearts"),
      // List.of("A of Club", "A of Diamond", "5 of Hearts")
      List.of("7 of Hearts", "Q of Spade", "6 of Club"),
      List.of("7 of Club", "J of Spade", "6 of Spade")
    );
    CardDistribution myGame = new CardDistribution();

    List<Players> playersList = new ArrayList<>();
    List<Players> trailPlayersList = new ArrayList<>();
    List<Players> doubleRunPlayersList = new ArrayList<>();
    List<Players> runPlayersList = new ArrayList<>();
    List<Players> colorPlayersList = new ArrayList<>();
    List<Players> pairPlayersList = new ArrayList<>();
    List<Players> nothingPlayersList = new ArrayList<>();

    Game my_game = new Game();

    int playersCount = myPlayers.size();

    myGame.checkTrail(playersList, myPlayers, ranks, playersCount);
    myGame.checkDoubleRun(playersList, myPlayers, ranks, playersCount);
    myGame.checkRun(playersList, myPlayers, ranks, playersCount);
    myGame.checkColor(playersList, myPlayers, ranks, playersCount);
    myGame.checkPair(playersList, myPlayers, ranks, playersCount);
    myGame.defaultHand(playersList, myPlayers, ranks, playersCount);
    System.out.println("-----------------------------");
    // myGame.checkTrail(playersList, players, ranks, numPlayers);
    // myGame.checkDoubleRun(playersList, players, ranks, numPlayers);
    // myGame.checkRun(playersList, players, ranks, numPlayers);
    // myGame.checkColor(playersList, players, ranks, numPlayers);
    // myGame.checkPair(playersList, players, ranks, numPlayers);
    // myGame.defaultHand(playersList, players, ranks, numPlayers);

    // System.out.println("----------------------------------");
    for (Players player : playersList) {
      if (player.getStatus() == "Trial") {
        trailPlayersList.add(player);
        my_game.setTrail(my_game.getTrail() + 1);
      } else if (player.getStatus() == "Double Run") {
        doubleRunPlayersList.add(player);
        my_game.setDoubleRun(my_game.getDoubleRun() + 1);
      } else if (player.getStatus() == "Run") {
        runPlayersList.add(player);
        my_game.setRun(my_game.getRun() + 1);
      } else if (player.getStatus() == "Color") {
        colorPlayersList.add(player);
        my_game.setColor(my_game.getColor() + 1);
      } else if (player.getStatus() == "Pair") {
        pairPlayersList.add(player);
        my_game.setPair(my_game.getPair() + 1);
      } else {
        nothingPlayersList.add(player);
        my_game.setNothing(my_game.getNothing() + 1);
      }
    }

    for (Players player : playersList) {
      if (my_game.getTrail() > 0 && player.getStatus() == "Trial") {
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
          winner.setState("Winner");
          my_game.setWinner(winner);
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

          // Players winner = doubleRunPlayersList.get(0);
          // int rank1 = Arrays.asList(ranks).indexOf(winner.getBiggestCard());
          // int rank2 = Arrays.asList(ranks).indexOf(winner.getMiddleCard());
          // int rank3 = Arrays.asList(ranks).indexOf(winner.getSmallestCard());
          // boolean isFirstElement = true;
          // for (Players doubleRunPlayer : doubleRunPlayersList) {
          //   int rank11 = Arrays
          //     .asList(ranks)
          //     .indexOf(doubleRunPlayer.getBiggestCard());
          //   int rank22 = Arrays
          //     .asList(ranks)
          //     .indexOf(doubleRunPlayer.getMiddleCard());
          //   int rank33 = Arrays
          //     .asList(ranks)
          //     .indexOf(doubleRunPlayer.getSmallestCard());
          //   if (isFirstElement) {
          //     isFirstElement = false;
          //     continue;
          //   } else if (rank11 > rank1) {
          //     rank1 = rank11;
          //     rank2 = rank22;
          //     rank3 = rank33;
          //     winner = doubleRunPlayer;
          //   } else if (rank11 == rank1 && rank22 > rank2) {
          //     rank1 = rank11;
          //     rank2 = rank22;
          //     rank3 = rank33;
          //     winner = doubleRunPlayer;
          //   } else if (rank11 == rank1 && rank22 == rank2 && rank33 > rank3) {
          //     rank1 = rank11;
          //     rank2 = rank22;
          //     rank3 = rank33;
          //     winner = doubleRunPlayer;
          //   } else if (rank11 == rank1 && rank22 == rank2 && rank33 == rank3) {
          //     winner = null;
          //   }
          // }
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
          // Players winner = runPlayersList.get(0);
          // int rank1 = Arrays.asList(ranks).indexOf(winner.getBiggestCard());
          // int rank2 = Arrays.asList(ranks).indexOf(winner.getMiddleCard());
          // int rank3 = Arrays.asList(ranks).indexOf(winner.getSmallestCard());
          // boolean isFirstElement = true;
          // for (Players runPlayer : runPlayersList) {
          //   int rank11 = Arrays
          //     .asList(ranks)
          //     .indexOf(runPlayer.getBiggestCard());
          //   int rank22 = Arrays
          //     .asList(ranks)
          //     .indexOf(runPlayer.getMiddleCard());
          //   int rank33 = Arrays
          //     .asList(ranks)
          //     .indexOf(runPlayer.getSmallestCard());
          //   if (isFirstElement) {
          //     isFirstElement = false;
          //     continue;
          //   } else if (rank11 > rank1) {
          //     rank1 = rank11;
          //     rank2 = rank22;
          //     rank3 = rank33;
          //     winner = runPlayer;
          //   } else if (rank11 == rank1 && rank22 > rank2) {
          //     rank1 = rank11;
          //     rank2 = rank22;
          //     rank3 = rank33;
          //     winner = runPlayer;
          //   } else if (rank11 == rank1 && rank22 == rank2 && rank33 > rank3) {
          //     rank1 = rank11;
          //     rank2 = rank22;
          //     rank3 = rank33;
          //     winner = runPlayer;
          //   } else if (rank11 == rank1 && rank22 == rank2 && rank33 == rank3) {
          //     winner = null;
          //   }
          // }
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
          // Players winner = colorPlayersList.get(0);
          // int rank1 = Arrays.asList(ranks).indexOf(winner.getBiggestCard());
          // int rank2 = Arrays.asList(ranks).indexOf(winner.getMiddleCard());
          // int rank3 = Arrays.asList(ranks).indexOf(winner.getSmallestCard());
          // boolean isFirstElement = true;
          // for (Players colorPlayer : colorPlayersList) {
          //   int rank11 = Arrays
          //     .asList(ranks)
          //     .indexOf(colorPlayer.getBiggestCard());
          //   int rank22 = Arrays
          //     .asList(ranks)
          //     .indexOf(colorPlayer.getMiddleCard());
          //   int rank33 = Arrays
          //     .asList(ranks)
          //     .indexOf(colorPlayer.getSmallestCard());
          //   if (isFirstElement) {
          //     isFirstElement = false;
          //     continue;
          //   } else if (rank11 > rank1) {
          //     rank1 = rank11;
          //     rank2 = rank22;
          //     rank3 = rank33;
          //     winner = colorPlayer;
          //   } else if (rank11 == rank1 && rank22 > rank2) {
          //     rank1 = rank11;
          //     rank2 = rank22;
          //     rank3 = rank33;
          //     winner = colorPlayer;
          //   } else if (rank11 == rank1 && rank22 == rank2 && rank33 > rank3) {
          //     rank1 = rank11;
          //     rank2 = rank22;
          //     rank3 = rank33;
          //     winner = colorPlayer;
          //   } else if (rank11 == rank1 && rank22 == rank2 && rank33 == rank3) {
          //     winner = null;
          //   }
          // }
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
      } else if (my_game.getNothing() > 0 && player.getStatus() == "Nothing") {
        if (my_game.getNothing() == 1) {
          player.setState("Winner");
          my_game.setWinner(player);
          break;
        } else if (my_game.getNothing() > 1) {
          Players winner = myGame.ranking(nothingPlayersList, ranks);
          // Players winner = nothingPlayersList.get(0);
          // int rank1 = Arrays.asList(ranks).indexOf(winner.getBiggestCard());
          // int rank2 = Arrays.asList(ranks).indexOf(winner.getMiddleCard());
          // int rank3 = Arrays.asList(ranks).indexOf(winner.getSmallestCard());
          // boolean isFirstElement = true;
          // for (Players nothingPlayer : nothingPlayersList) {
          //   int rank11 = Arrays
          //     .asList(ranks)
          //     .indexOf(nothingPlayer.getBiggestCard());
          //   int rank22 = Arrays
          //     .asList(ranks)
          //     .indexOf(nothingPlayer.getMiddleCard());
          //   int rank33 = Arrays
          //     .asList(ranks)
          //     .indexOf(nothingPlayer.getSmallestCard());
          //   if (isFirstElement) {
          //     isFirstElement = false;
          //     continue;
          //   } else if (rank11 > rank1) {
          //     rank1 = rank11;
          //     rank2 = rank22;
          //     rank3 = rank33;
          //     winner = nothingPlayer;
          //   } else if (rank11 == rank1 && rank22 > rank2) {
          //     rank1 = rank11;
          //     rank2 = rank22;
          //     rank3 = rank33;
          //     winner = nothingPlayer;
          //   } else if (rank11 == rank1 && rank22 == rank2 && rank33 > rank3) {
          //     rank1 = rank11;
          //     rank2 = rank22;
          //     rank3 = rank33;
          //     winner = nothingPlayer;
          //   } else if (rank11 == rank1 && rank22 == rank2 && rank33 == rank3) {
          //     winner = null;
          //   }
          // }
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
      if (player.getStatus() == "Trial") {
        System.out.println("Trial of: " + player.getBiggestCard());
      } else if (player.getStatus() == "Pair") {
        System.out.println("Pair of: " + player.getBiggestCard());
        System.out.println("Third Card: " + player.getSmallestCard());
      } else if (player.getStatus() == "Nothing") {
        System.out.println("Power of: " + player.getBiggestCard());
        System.out.println("Middle Card: " + player.getMiddleCard());
        System.out.println("Third Card: " + player.getSmallestCard());
      } else {
        System.out.println("Big: " + player.getBiggestCard());
        System.out.println("Middle Card: " + player.getMiddleCard());
        System.out.println("Third Card: " + player.getSmallestCard());
      }
      System.out.println("----------------------------------");
    }

    System.out.println(
      "--------Some Game Stats--------- \nTrail: " +
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


    System.out.println("----------------------------");
    System.out.print("Winner: ");
    if (my_game.getWinner() != null) {
      System.out.println(
        my_game.getWinner().getName() +
        "\nHand: " +
        my_game.getWinner().getHand() +
        "\nStatus: " +
        my_game.getWinner().getStatus()
      );
    } else {
      System.out.println("None, it's draw!!");
    }
  }
}
