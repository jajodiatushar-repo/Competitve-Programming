package com;

import java.util.Arrays;

public class MaximumNumberOfJumpsToReachTheLastIndex {

    /*

        This is the classic Dynamic Problem, just like a knapsack.
        You can build the solution from one end ( for eg, left side)
        You are in the first position, then you try to find out
        the maximum number of steps you can take to reach another position that
        satisfies the condition.

        main dp thing is that dp[j] = Math.max(dp[j], dp[i] + 1);

     */



    public static void main(String[] args) {

        MaximumNumberOfJumpsToReachTheLastIndex solution = new MaximumNumberOfJumpsToReachTheLastIndex();
        int[] nums = {1, 3, 6, 4, 1, 2};
        int target = 2;
        int result = solution.maximumJumps(nums, target);
        System.out.println(result);
    }

    public int maximumJumps(int[] nums, int target) {

        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MIN_VALUE);
        dp[0] = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (isPossible(nums, target, i, j)) {
                    dp[j] = Math.max(dp[j], 1 + dp[i]);
                }
            }
        }
        return dp[n - 1] < 0 ? -1 : dp[n - 1];
    }

    public boolean isPossible(int[] nums, int target, int i, int j) {
        int value = Math.abs(nums[j] - nums[i]);
        return target >= value;
    }
}
