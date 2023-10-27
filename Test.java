public class Test {
    public static void main(String[] args){
        int[] numbers = {1,2,3};
        int big = numbers[0];
        int med = numbers[0];
        int small = numbers[0];

        for (int i = 0; i < numbers.length; i++){
            if (numbers[i]>big){
                big = numbers[i];
            } else if (numbers[i]>med){
                med = numbers[i];
            } else {
                small = numbers[i];
            }
        }

        System.out.println("Big: "+ big + "\nMed: " + med + "\nSmall: "+ small);
    }
}
