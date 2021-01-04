package algorithm;

public class MergeSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        int[] tmp = new int[arr.length];
        mergeSort(arr, tmp, 0, arr.length - 1);
    }

    private static void mergeSort(int[] arr, int[] tmp, int left, int right) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;
        mergeSort(arr, tmp, left, mid);
        mergeSort(arr, tmp, mid + 1, right);
        merge(arr, tmp, left, mid, right);
    }

    private static void merge(int[] arr, int[] tmp, int left, int mid, int right) {
        System.arraycopy(arr, left, tmp, left, right - left + 1);
        int i = left, j = mid + 1, k = left;
        while (i <= mid && j <= right) {
            arr[k++] = tmp[i] <= tmp[j] ? tmp[i++] : tmp[j++];
        }
        while (i <= mid) arr[k++] = tmp[i++];
    }
}
