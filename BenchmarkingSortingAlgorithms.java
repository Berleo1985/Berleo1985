package com.CS1103;
import java.util.*;
/* time taken for 1000 random entries
 *  0.008sec for selection sort
 *  0.006sec for arrays sort
 *  time taken for 10000 random entries
 *  0.12sec for selection sort
 *  0.006sec for arrays sort
 *  time taken for 100000 random entries
 *  6.407sec for selection sort
 *  0.033sec for arrays sort
 */
public class BenchmarkingSortingAlgorithms{
    public static void main(String[] args) {
    // Compute benchmarks of two different sorting techniques

    int maxArraySize=1000; // Array Size
    int[] sortingArray1 = new int[maxArraySize]; // First Array
    int[] sortingArray2 = new int[maxArraySize]; // Second Array

// Class Constructor

        for (int i = 0; i < sortingArray1.length; i++) {
// Filling two arrays with the same random numbers.
            sortingArray1[i] = (int) (Integer.MAX_VALUE * Math.random());
            sortingArray2[i] = sortingArray1[i];
        }
        long startTimeArray1 = System.currentTimeMillis(); // computing time forSelectionSort
        selectionSort(sortingArray1); // Sorting Array1 with SelectionSort
        long runTimeArray1 = System.currentTimeMillis() - startTimeArray1; //run the Selection Sort

        long startTimeArray2 = System.currentTimeMillis();//Computing time for Arrays.sort
        Arrays.sort(sortingArray2); //Sorting Array2 with Arrays.sort
        long runTimeArray2 = System.currentTimeMillis() - startTimeArray2; //run the Arrays.sort

        System.out.println("Selection Sort time(sec):"+runTimeArray1/1000.0);
        System.out.println("Arrays Sort time(sec):"+ runTimeArray2/1000.0);
    }
    static void selectionSort(int[] A) {
// Sort A in increasing order, using selection sort
        for (int lastPlace = A.length-1; lastPlace > 0; lastPlace--) {
// Find the largest item among A[0], A[1], ...,
// A[lastPlace], and move it into position lastPlace
// by swapping it with the number that is currently
// in position lastPlace.
            int maxLoc = 0;
// Location of largest item seen so far.
            for (int j = 1; j <= lastPlace; j++) {
                if (A[j] > A[maxLoc]) {
// Since A[j] is bigger than the maximum we have seen
// so far, j is the new location of the maximum value
// we have seen so far.
                    maxLoc = j;
                }
            }
            int temp = A[maxLoc]; // Swap largest item with A[lastPlace].
            A[maxLoc] = A[lastPlace];
            A[lastPlace] = temp;
        } // end of for loop
    }
}

