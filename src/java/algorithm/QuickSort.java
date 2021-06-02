package algorithm;

public class QuickSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int left, int right) {
        if (arr[left] > arr[right]) swap(arr, left, right);
        if (left + 1 >= right) return;
        int lt = left + 1, gt = right - 1;
        int i = left + 1;
        int p = arr[left], q = arr[right];
        while (i <= gt) {
            if (arr[i] < p) swap(arr, i++, lt++);
            else if (arr[i] > q) swap(arr, i, gt--);
            else i++;
        }
        swap(arr, left, --lt);
        swap(arr, right, ++gt);
        quickSort(arr, left, lt - 1);
        quickSort(arr, lt + 1, gt - 1);
        quickSort(arr, gt + 1, right);
    }

    private static int partition(int[] arr, int left, int right) {
        int pivot = arr[right];
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, right);
        return i + 1;
    }

    public static void sort(int[] arr, int left, int right) {
        if (left >= right) return;
        int pivot = partition(arr, left, right);
        sort(arr, left, pivot - 1);
        sort(arr, pivot + 1, right);
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
