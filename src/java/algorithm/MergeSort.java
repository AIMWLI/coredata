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

    public static void sort(int[] arr, int left, int right) {
        if (arr == null || arr.length <= 1) return;
        int[] tmp = new int[arr.length];
        mergeSort(arr, tmp, left, right);
    }

    public static int inversions(int[] arr) {
        int[] tmp = new int[arr.length];
        return countInv(arr, tmp, 0, arr.length - 1);
    }

    private static int countInv(int[] arr, int[] tmp, int left, int right) {
        if (left >= right) return 0;
        int mid = left + (right - left) / 2;
        int inv = countInv(arr, tmp, left, mid) + countInv(arr, tmp, mid + 1, right);
        System.arraycopy(arr, left, tmp, left, right - left + 1);
        int i = left, j = mid + 1, k = left;
        while (i <= mid && j <= right) {
            if (tmp[i] <= tmp[j]) arr[k++] = tmp[i++];
            else { arr[k++] = tmp[j++]; inv += mid - i + 1; }
        }
        while (i <= mid) arr[k++] = tmp[i++];
        return inv;
    }
}

// utility method
public static void run() {}
