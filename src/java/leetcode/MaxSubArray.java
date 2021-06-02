package leetcode;

public class MaxSubArray {

    public int maxSubArray(int[] nums) {
        int max = nums[0];
        int cur = nums[0];
        for (int i = 1; i < nums.length; i++) {
            cur = Math.max(nums[i], cur + nums[i]);
            max = Math.max(max, cur);
        }
        return max;
    }

    public int maxSubArrayDivide(int[] nums) {
        return divide(nums, 0, nums.length - 1);
    }

    private int divide(int[] nums, int l, int r) {
        if (l == r) return nums[l];
        int mid = l + (r - l) / 2;
        int left = divide(nums, l, mid);
        int right = divide(nums, mid + 1, r);
        int cross = crossSum(nums, l, mid, r);
        return Math.max(Math.max(left, right), cross);
    }

    private int crossSum(int[] nums, int l, int mid, int r) {
        int leftSum = Integer.MIN_VALUE, sum = 0;
        for (int i = mid; i >= l; i--) {
            sum += nums[i];
            leftSum = Math.max(leftSum, sum);
        }
        int rightSum = Integer.MIN_VALUE;
        sum = 0;
        for (int i = mid + 1; i <= r; i++) {
            sum += nums[i];
            rightSum = Math.max(rightSum, sum);
        }
        return leftSum + rightSum;
    }

    public int maxSubArrayKadane(int[] nums) {
        int max = nums[0], cur = nums[0];
        for (int i = 1; i < nums.length; i++) {
            cur = Math.max(nums[i], cur + nums[i]);
            max = Math.max(max, cur);
        }
        return max;
    }
}
