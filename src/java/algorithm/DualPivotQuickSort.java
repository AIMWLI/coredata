package algorithm;

public class DualPivotQuickSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        dualPivot(arr, 0, arr.length - 1);
    }

    private static void dualPivot(int[] arr, int left, int right) {
        if (left >= right) return;
        if (arr[left] > arr[right]) swap(arr, left, right);
        int lt = left + 1, gt = right - 1;
        int i = left + 1;
        int p = arr[left], q = arr[right];
        while (i <= gt) {
            if (arr[i] < p) {
                swap(arr, i++, lt++);
            } else if (arr[i] > q) {
                swap(arr, i, gt--);
            } else {
                i++;
            }
        }
        swap(arr, left, --lt);
        swap(arr, right, ++gt);
        dualPivot(arr, left, lt - 1);
        dualPivot(arr, lt + 1, gt - 1);
        dualPivot(arr, gt + 1, right);
    }

    public static void sort(int[] arr, int left, int right) {
        if (arr == null || arr.length <= 1) return;
        dualPivot(arr, left, right);
    }

    public static void sort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
