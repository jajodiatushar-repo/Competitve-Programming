package com.leetcode;

import java.util.*;

public class SubstringWithConcatenationOfAllWords {

/*
            Main Concept
            ------------

            The most important observation is:

            1. All words have the SAME length.
            2. Therefore, we should move through the string WORD BY WORD,
               not character by character.

            Example:
            s = "barfoothefoobarman"
            words = ["foo", "bar"]

            wordLength = 3

            If we split the string with gap = 3:

            start from index 0:
            bar | foo | the | foo | bar | man

            start from index 1:
            arf | oot | hef | oob | arm

            start from index 2:
            rfo | oth | efo | oba | rma

            Notice something important:

            Starting from index 3 again gives:
            foo | the | foo | bar | man

            which is basically the SAME pattern as index 0.

            So we only need to try:
            0 to wordLength - 1 starting positions.

            That means:
            instead of checking every character independently,
            we process the string in groups based on wordLength.

            -------------------------------------------------------

            Sliding Window Idea
            -------------------

            For each offset (0 to wordLength - 1):

            Use:
            - left pointer
            - right pointer
            - current window hashmap

            Move right pointer word by word.

            For every word:

            1. If word is invalid:
               - clear the window
               - move left = right

            2. If word is valid:
               - add it to current window map

            3. If a word count exceeds expected frequency:
               - shrink window from left
               - remove words until counts become valid again

            4. If window contains exactly all words:
               - record left index as answer

            -------------------------------------------------------

            Why This Avoids TLE
            -------------------

            Brute Force:
            - Start from every index
            - Rebuild hashmap every time
            - Re-scan same words repeatedly

            Optimized Sliding Window:
            - Reuse previous computation
            - Every word enters/leaves window at most once

            This reduces complexity close to O(N).

            -------------------------------------------------------

            Key Pattern To Remember
            -----------------------

            Whenever:
            - all tokens/chunks are same size
            - frequency matching is needed

            Think:

            "Sliding Window + HashMap + Fixed Length Jump"
*/


    public static void main(String[] args) {

        String s = "barfoothefoobarman";
        String[] words = {"foo", "bar"};

        SubstringWithConcatenationOfAllWords solution = new SubstringWithConcatenationOfAllWords();
        List<Integer> result = solution.findSubstring(s, words);
        System.out.println(result);
    }

    public List<Integer> findSubstring(String s, String[] words) {

        List<Integer> result = new ArrayList<>();
        int totalLength = s.length();
        int totalWords = words.length;
        int wordLength = 0;
        Map<String, Integer> originalMap = new HashMap<>();

        for (String word : words) {
            wordLength = word.length();
            originalMap.put(word, originalMap.getOrDefault(word, 0) + 1);
        }


        for (int index = 0; index < wordLength; index++) {

            int startIndex = index;
            int endIndex = index;
            Map<String, Integer> tempMap = new HashMap<>();
            int currentMatch = 0;

            while (endIndex + wordLength <= totalLength) {

                String currWord = s.substring(endIndex, endIndex + wordLength);
                endIndex += wordLength;

                if (originalMap.containsKey(currWord)) {

                    tempMap.put(currWord, tempMap.getOrDefault(currWord, 0) + 1);
                    currentMatch++;

                    while (tempMap.get(currWord) > originalMap.get(currWord)) {
                        String firstWord = s.substring(startIndex, startIndex + wordLength);
                        tempMap.put(firstWord, tempMap.get(firstWord) - 1);
                        startIndex += wordLength;
                        currentMatch--;
                    }

                    if (currentMatch == totalWords) {
                        result.add(startIndex);
                    }
                } else {
                    startIndex = endIndex;
                    tempMap.clear();
                    currentMatch = 0;
                }
            }
        }
        return result;
    }
}
