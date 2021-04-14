package algorithm;

import java.util.Arrays;

public class SortUtils {

    public static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) return false;
        }
        return true;
    }

    public static int[] copy(int[] arr) {
        return Arrays.copyOf(arr, arr.length);
    }

    public static int[] randomArray(int size, int bound) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int) (Math.random() * bound);
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] data = randomArray(1000, 10000);
        int[] copy1 = copy(data);
        int[] copy2 = copy(data);

        QuickSort.sort(copy1);
        System.out.println("quick: " + isSorted(copy1));

        MergeSort.sort(copy2);
        System.out.println("merge: " + isSorted(copy2));

        int[] heapData = copy(data);
        HeapSort.sort(heapData);
        System.out.println("heap: " + isSorted(heapData));

        int[] insertData = copy(data);
        InsertionSort.sort(insertData);
        System.out.println("insertion: " + isSorted(insertData));

        int[] timData = copy(data);
        TimSort.sort(timData);
        System.out.println("tim: " + isSorted(timData));

        int[] dualData = copy(data);
        DualPivotQuickSort.sort(dualData);
        System.out.println("dual pivot: " + isSorted(dualData));
    }
}
