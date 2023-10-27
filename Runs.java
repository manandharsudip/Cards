import java.util.Arrays;
import java.util.List;

public class Runs {
    public static void main(String[] args) {

        
        String[] ranks = {
            "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"
        };

        
        // Sample hands
        List<String> hand1 = List.of("A", "6", "2");
        List<String> hand2 = List.of("J", "10", "Q");
        List<String> hand3 = List.of("5", "7", "6");

        // Check if each hand is a run
        boolean isRun1 = isConsecutiveRun(hand1, ranks);
        boolean isRun2 = isConsecutiveRun(hand2, ranks);
        boolean isRun3 = isConsecutiveRun(hand3, ranks);

        System.out.println("Hand 1 is a run: " + isRun1);
        System.out.println("Hand 2 is a run: " + isRun2);
        System.out.println("Hand 3 is a run: " + isRun3);
    }

    public static boolean isConsecutiveRun(List<String> hand, String[] ranks) {
        // Sort the hand based on the custom order of ranks

        String[] myHand = hand.toArray(new String[hand.size()]);

        Arrays.sort(myHand, (rank1, rank2) -> {
            int index1 = Arrays.asList(ranks).indexOf(rank1);
            int index2 = Arrays.asList(ranks).indexOf(rank2);
            return Integer.compare(index1, index2);
        });

        // for (int i = 0; i < myHand.length; i++) {
        //     System.out.println("myHand ele: "+ myHand[i]);
        // }
        
        // Check if the sorted myHand is a run
        for (int i = 0; i < myHand.length - 1; i++) {
            int rank1Index = Arrays.asList(ranks).indexOf(myHand[i]);
            int rank2Index = Arrays.asList(ranks).indexOf(myHand[i + 1]);

            // System.out.println("Rank1: "+ rank1Index + " Rank2: " + rank2Index);

            // Check if the ranks are consecutive based on the custom order
            if (Math.abs(rank2Index - rank1Index) == 1 || ((rank1Index == 0 && rank2Index == 12) || (rank1Index == 12 && rank2Index == 0))) {
                return true;
            }
        }
        return false;
    }
}
