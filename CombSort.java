import java.util.*;

public class CombSort {

    public static void sort(int[] array, int size) {
        /* 
        This function sorts a list in-place using CombSort11. 
        It works exactly like BubbleSort except that instead of
        looking at i and i+1 when iterating, it looks at i and i+gap.
        This helps reposition small values stuck at the end of the array.
        */

        int gap = size; // The distance between elements being compared
        boolean swapped = true; 
        int i; // Our index

        // keep looping until you make
        // a complete pass without swapping
        while (gap > 1 || swapped) {

            // 1.3 is the shrink factor.
            // I found it to be the fastest.
            // A gap can not be smaller than 1,
            // hence the "if (gap > 1)"
            if (gap > 1) {
                gap = (int)(gap / 1.3);
            }

            // This is why it is CombSort11 
            // instead of CombSort. I find
            // doing this increases the speed
            // by a few milliseconds.
            if (gap == 9 || gap == 10) {
                gap = 11;
            }

            i = 0;
            swapped = false;

            // Loop through comparing numbers a gap-length apart.
            // If the first number is bigger than the second, swap them.
            while (i + gap < size) {
                if (array[i] > array[i+gap]) {
                    swap(array, i, i+gap);
                    swapped = true;
                }
                i++;
            }
        } 
    }

    public static void swap(int[] list, int i, int j) {
        /* This method simply takes an array
        and swaps its values at index i and j */

        int temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }

    public static String input(String s) {
        /* input() simplifies the process of asking for input
           and getting input at the same time.
           Takes string s, prints it.
           Gets users input, returns it. */

        System.out.print(s);
        Scanner scan = new Scanner(System.in);
        return scan.next();
    }

    public static void main(String[] args) {
        /* Ask the user how many numbers of what range
        should be sorted by the algorithm and if they want to see the array */

        int ammount = Integer.parseInt( input("How many numbers shall be generated? ") );
        int min = Integer.parseInt( input("Min value of numbers: ") );
        int max = Integer.parseInt( input("Max value of numbers: ") );
        boolean show_array = input("Would you like to see the sorted and unsorted list?(y/n) ").equals("y");
        
        int[] numbers = new int[ammount];

        // This generates the randomly populated array using
        // numbers between min and max
        Random rand = new Random();
        for (int n=0; n < ammount; n++) {
            numbers[n] = rand.nextInt(max - min + 1) + min;
        }

        if (show_array) {
            System.out.println("__UNSORTED ARRAY__");
            System.out.println(Arrays.toString(numbers));
        }

        sort(numbers, ammount);

        if (show_array) {
            System.out.println("__SORTED ARRAY__");
            System.out.println(Arrays.toString(numbers));
        }

    }
} 