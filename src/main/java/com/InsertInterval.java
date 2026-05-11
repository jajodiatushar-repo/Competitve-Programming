package com;

import java.util.ArrayList;
import java.util.List;

import static com.interviewbit.utils.ArrayUtils.printArray;

public class InsertInterval {


    /*
        The idea is see, the new interval can fall in the three places.

        1. New Interval may end before the start of current interval
        2. New Interval may over-lab with existing intervals
        3. New Interval may start after the end of current interval


        For the first you can simply do, (read it like this, new interval is starting
            after the end of current interval)
        if (newInterval[0] > intervals[currentIndex][1])

        For the second you can do, (read it like this, current interval is starting before
            end of new interval)
        if(newInterval[1] >= intervals[currentIndex][0])

        in this case you need to merge the newInterval and the currentInterval using
        min and max approach,
     */



    public static void main(String[] args) {

        InsertInterval solution = new InsertInterval();

        int[][] intervals = {{3, 5}, {12, 15}};
        int[] interval = {6, 6};

        int[][] result = solution.insert(intervals, interval);
        printArray(result);
    }

    /*
        This is my code that passed all the test cases
     */

    public int[][] insert(int[][] intervals, int[] newInterval) {

        int n = intervals.length;
        List<int[]> result = new ArrayList<>();

        int startIndex = 0;

        while (startIndex < n && intervals[startIndex][1] < newInterval[0]) {
            result.add(intervals[startIndex]);
            startIndex++;
        }

        if (startIndex == n) {
            result.add(newInterval);
            return result.toArray(new int[0][0]);
        }

        if (newInterval[1] < intervals[startIndex][0]) {
            int[][] newIntervals = new int[n + 1][2];

            for (int i = 0; i < startIndex; i++) {
                newIntervals[i] = intervals[i];
            }
            newIntervals[startIndex] = newInterval;
            for (int i = startIndex; i < n; i++) {
                newIntervals[i + 1] = intervals[i];
            }

            intervals = newIntervals;
            n = n + 1;
        }

        if (intervals[startIndex][0] <= newInterval[0]) {
            // Do nothing.
        } else if (intervals[startIndex][0] > newInterval[0]) {
            intervals[startIndex][0] = newInterval[0];
        }

        if (intervals[startIndex][1] >= newInterval[1]) {
            // Do nothing.
        } else if (intervals[startIndex][1] < newInterval[1]) {
            intervals[startIndex][1] = newInterval[1];
        }

        int nextIndex = startIndex + 1;

        while (nextIndex < n) {

            int[] curr = intervals[startIndex];
            int[] next = intervals[nextIndex];

            if (curr[1] >= next[0]) {
                if (curr[1] >= next[1]) {
                    // Do nothing and keep skipping
                } else {
                    curr[1] = next[1];
                    nextIndex++;
                    break;
                }
            } else {
                break;
            }
            nextIndex++;
        }

        result.add(intervals[startIndex]);

        while (nextIndex < n) {
            result.add(intervals[nextIndex++]);
        }

        return result.toArray(new int[0][0]);

    }

    /*
        This is the code of the leetCode solution
     */

    public int[][] insertOptimised(int[][] intervals, int[] newInterval) {

        int n = intervals.length;
        List<int[]> result = new ArrayList<>();

        int startIndex = 0;

        while (startIndex < n && intervals[startIndex][1] < newInterval[0]) {
            result.add(intervals[startIndex]);
            startIndex++;
        }

        while (startIndex < n && newInterval[1] >= intervals[startIndex][0]) {
            newInterval[0] = Math.min(intervals[startIndex][0], newInterval[0]);
            newInterval[1] = Math.max(intervals[startIndex][1], newInterval[1]);
            startIndex++;
        }

        result.add(newInterval);

        while (startIndex < n) {
            result.add(intervals[startIndex++]);
        }

        return result.toArray(new int[0][0]);

    }
}


