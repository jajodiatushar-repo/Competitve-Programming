package com.leetcode;

import java.util.*;

public class PermutationsII {

    /*
        This is similar to the Permutation I,
        Just that you can have duplicates.

        Now, you just need to track that same number is not repeated in the
        current level again using a simple HashSet

     */


    List<List<Integer>> result = new ArrayList<>();
    int length = 0;
    int[] originalArray;


    public List<List<Integer>> permuteUnique(int[] nums) {

        length = nums.length;
        originalArray = nums;

        permute(new ArrayList<>(), 0, nums);

        return result;

    }

    public void permute(List<Integer> currResult, int index, int[] array) {

        if (currResult.size() == array.length) {
            result.add(currResult);
            return;
        }

        Set<Integer> used = new HashSet<>(); // This is the HERO.

        for (int i = index; i < array.length; i++) {
            List<Integer> newArray = new ArrayList<>(currResult);

            if (used.contains(array[i])) {
                continue;
            }
            used.add(array[i]);


            newArray.add(array[i]);
            swap(array, i, index);
            permute(newArray, index + 1, array);
            swap(array, i, index);
        }
    }

    public void swap(int[] array, int first, int second) {
        int temp = array[first];
        array[first] = array[second];
        array[second] = temp;
    }


}
