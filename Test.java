import java.util.List;

public class Test {

  public static void main(String[] args) {
    List<String> players = List.of("10", "K", "K");

    String num = "";
    String pairOf = "";
    String thirdCard = "";

    int count = 0;

    for (String mCard : players) {
      num = mCard;
      for (String card : players) {
        if (card.equals(num)) {
          pairOf = num;
          count++;
          System.out.println(count);
        } else {
          thirdCard = card;
        }
      }
      if (count == 2) {
        break;
      }
      count = 0;
    }

    if (count == 2) {
      System.out.println("Pair: " + pairOf + "\nThird: " + thirdCard);
    } else {
      System.out.println("No Pair!!");
    }
  }
}
