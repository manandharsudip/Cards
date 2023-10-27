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

  public List<List<String>> checkTrial(
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

      // custom logic to check the run in the hand
      if (
        (
          Math.abs(rank2Index - rank1Index) == 1 &&
          Math.abs(rank3Index - rank2Index) == 1 &&
          color
        ) ||
        (
          Math.abs(rank2Index - rank1Index) == 1 &&
          color &&
          (rank1Index == 0 && rank2Index == 12) ||
          (rank1Index == 12 && rank2Index == 0)
        ) ||
        (
          Math.abs(rank3Index - rank2Index) == 1 &&
          color &&
          (rank1Index == 0 && rank2Index == 12) ||
          (rank1Index == 12 && rank2Index == 0)
        )
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
      // System.out.println("Player " + (i + 1) + " Hand: " + players.get(i));

      String[] myHand = new String[3];
      myHand = sortFunc(players.get(i), myHand, ranks);

      // int ind = 0;
      // for (String card : players.get(i)) {
      //   int index = card.indexOf(" ");
      //   myHand[ind] = card.substring(0, index);
      //   ind++;
      // }

      // Arrays.sort(
      //   myHand,
      //   (rank1, rank2) -> {
      //     int inde1 = Arrays.asList(ranks).indexOf(rank1);
      //     int inde2 = Arrays.asList(ranks).indexOf(rank2);
      //     return Integer.compare(inde1, inde2);
      //   }
      // );

      int rank1Index = Arrays.asList(ranks).indexOf(myHand[0]);
      int rank2Index = Arrays.asList(ranks).indexOf(myHand[1]);
      int rank3Index = Arrays.asList(ranks).indexOf(myHand[2]);

      if (
        (
          Math.abs(rank2Index - rank1Index) == 1 &&
          Math.abs(rank3Index - rank2Index) == 1
        ) ||
        (
          Math.abs(rank2Index - rank1Index) == 1 &&
          (rank1Index == 0 && rank2Index == 12) ||
          (rank1Index == 12 && rank2Index == 0)
        ) ||
        (
          Math.abs(rank3Index - rank2Index) == 1 &&
          (rank1Index == 0 && rank2Index == 12) ||
          (rank1Index == 12 && rank2Index == 0)
        )
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
          playersList.add(player);
        }
      }
    }

    return null;
  }

  public List<List<String>> checkPair(
    List<Players> playersList,
    List<List<String>> players,
    int numPlayers
  ) {
    for (int i = 0; i < numPlayers; i++) {
      // System.out.println("Player " + (i + 1) + " Hand: " + players.get(i));

      int count = 0;
      String pairOf = "";
      int index1 = players.get(i).get(0).indexOf(" ");
      String num = players.get(i).get(0).substring(0, index1);

      for (String card : players.get(i)) {
        int index = card.indexOf(" ");
        if (card.substring(0, index).equals(num)) {
          pairOf = num;
          count++;
        }
        // System.out.println(card.substring(0, index));
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
          playersList.add(player);
        }
        // System.out.println("Pair of: " + pairOf);
      }
    }

    return null;
  }

  public List<List<String>> defaultHand(
    List<Players> playersList,
    List<List<String>> players,
    int numPlayers
  ) {
    for (int i = 0; i < numPlayers; i++) {
      // // System.out.println("Player " + (i + 1) + " Hand: " + players.get(i));

      // int count = 0;
      // String pairOf = "";
      // int index1 = players.get(i).get(0).indexOf(" ");
      // String num = players.get(i).get(0).substring(0, index1);

      // for (String card : players.get(i)) {
      //   int index = card.indexOf(" ");
      //   if (card.substring(0, index).equals(num)) {
      //     pairOf = num;
      //     count++;
      //   }
      // }

      // if (count == 2) {
      Players player = new Players();
      String playerName = "Player" + i;

      boolean playerExists = playersList
        .stream()
        .anyMatch(person -> person.getName().equals(playerName));

      if (!playerExists) {
        player.setName("Player" + i);
        player.setHand(players.get(i));
        player.setStatus("Nothing !!");
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

    // // Display each player's hand
    // for (int i = 0; i < numPlayers; i++) {
    //     System.out.println("Player " + (i + 1) + " Hand: " + players.get(i));
    //     System.out.println("Numbers Only");
    // }

    // Display each player's hand
    // for (int i = 0; i < numPlayers; i++) {
    //     System.out.println("Player " + (i + 1) + " Hand: " + players.get(i));

    //     System.out.println("Numbers");
    //     players.get(i).forEach(card -> {int index = card.indexOf(" "); System.out.println(card.substring(0, index));});
    //     System.out.println("Colors");
    //     players.get(i).forEach(card -> {int index = card.lastIndexOf(" "); System.out.println(card.substring(index+1));});
    // }

    List<List<String>> myPlayers = List.of(
      List.of("10 of Spade", "10 of Diamond", "10 of Club"),
      List.of("1 of Spade", "3 of Spade", "2 of Spade"),
      List.of("1 of Spade", "2 of Spade", "4 of Spade"),
      List.of("K of Spade", "J of Spade", "Q of Club"),
      List.of("7 of Club", "7 of Spade", "6 of Club"),
      List.of("7 of Hearts", "K of Spade", "6 of Club")
    );
    CardDistribution myGame = new CardDistribution();

    List<Players> playersList = new ArrayList<>();

    int playersCount = myPlayers.size();

    myGame.checkTrial(playersList, myPlayers, ranks, playersCount);
    myGame.checkDoubleRun(playersList, myPlayers, ranks, playersCount);
    myGame.checkRun(playersList, myPlayers, ranks, playersCount);
    myGame.checkColor(playersList, myPlayers, playersCount);
    myGame.checkPair(playersList, myPlayers, playersCount);
    myGame.defaultHand(playersList, myPlayers, playersCount);
    // System.out.println("-----------------------------");
    // myGame.checkTrial(playersList, players, numPlayers);
    // myGame.checkDoubleRun(playersList, players, ranks, numPlayers);
    // myGame.checkRun(playersList, players, ranks, numPlayers);
    // myGame.checkColor(playersList, players, numPlayers);
    // myGame.checkPair(playersList, players, numPlayers);
    // myGame.defaultHand(playersList, players, numPlayers);

    System.out.println("----------------------------------");
    for (Players player : playersList) {
      System.out.println("Name: " + player.getName());
      System.out.println("Hand: " + player.getHand());
      System.out.println("Status: " + player.getStatus());
      System.out.println("Big: " + player.getBiggestCard());
      System.out.println("Med: " + player.getMiddleCard());
      System.out.println("Small: " + player.getSmallestCard());
      System.out.println("----------------------------------");
    }
  }
}
