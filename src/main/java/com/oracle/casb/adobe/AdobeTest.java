package com.oracle.casb.adobe;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class AdobeTest {

    public static void main(String[] args) {
        AdobeTest at = new AdobeTest();
        //at.testProb1();
        //List<Integer> res = order(3, ImmutableList.of(1), ImmutableList.of(2), 2);
        Long val = pthFactor(1, 1);
        System.out.println(val);
    }

    private void testProb1() {
        int mask = 0x00F;
        int value = 0x2222;
        System.out.println(value & mask);
    }

    static String findNumber(List<Integer> arr, int k) {
        /**if(arr == null || arr.isEmpty()) {
         return "NO";
         }
         Collections.sort(arr);
         return binarySearch(arr, k) ? "YES": "NO";**/
        Optional<Integer> res =  arr.stream().filter(e -> e.intValue() == k).findAny();
        return res.isPresent() ? "YES": "NO";
    }

    public static String arrange(String sentence) {
        // Write your code here
        sentence = sentence.substring(0, sentence.length()-1);
        String[] words = sentence.split("\\s");
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        StringBuilder sb = new StringBuilder();
        words[0] = Character.toUpperCase(words[0].charAt(0)) + ((words[0].length() > 1) ? words[0].substring(1) : "");
        for (String word : words) {
            sb.append(word.toLowerCase());
        }
        sb.append('.');
        return sb.toString();
    }

    public static List<Integer> order(int employeesNodes, List<Integer> employeesFrom, List<Integer> employeesTo, int host) {
        Map<Integer, Integer> empDist = new HashMap<>();
        for (int i = 1; i <= employeesNodes; i++) {
            if (i == host) {
                empDist.put(i, 0);
            } else {
                empDist.put(i, Integer.MAX_VALUE);
            }
        }
        Map<Integer, Set<Integer>> outGoing = new HashMap<>();
        for (int i = 0; i < employeesFrom.size(); i++) {
            outGoing.computeIfAbsent(employeesFrom.get(i), k -> new HashSet<>()).add(employeesTo.get(i));
            outGoing.computeIfAbsent(employeesTo.get(i), k -> new HashSet<>()).add(employeesFrom.get(i));
        }
        Queue<Integer> visited = new LinkedList<>();
        visited.add(host);
        while (!visited.isEmpty()) {
            Integer current = visited.poll();
            int distFromHost = empDist.get(current) +1 ;
            Set<Integer> neighbours = outGoing.get(current);
            if (neighbours == null) {
                continue;
            }
            for (Integer neighbour : neighbours) {
                if (empDist.get(neighbour) > distFromHost) {
                    empDist.put(neighbour, distFromHost);
                    visited.offer(neighbour);
                }
            }
        }
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(empDist.entrySet());
        Collections.sort(list, (a, b) -> a.getValue().compareTo(b.getValue()));
        List<Integer> res = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : list) {
            if (entry.getKey() == host || entry.getValue() == Integer.MAX_VALUE) {
                continue;
            }
            res.add(entry.getKey());
        }
        return res;
    }

    public static String getShiftedString(String s, int leftShifts, int rightShifts) {
        // Write your code here
        if (s.length() <= 1 || leftShifts == rightShifts) {
            return s;
        }
        int len = s.length();
        leftShifts = leftShifts % len;
        rightShifts = rightShifts % len;
        int effectiveShifts = Math.abs(leftShifts - rightShifts);
        if (effectiveShifts == s.length()) {
            return s;
        }
        int shifts = effectiveShifts;
        if (rightShifts > leftShifts) {
            shifts = len - effectiveShifts;
        }
        String pre = s.substring(shifts);
        String post = s.substring(0, shifts);
        return pre + post;
    }

    public static long doubleSize(List<Long> a, long b) {
        // Write your code here
        Collections.sort(a, (l1, l2) -> l1.compareTo(l2));
        for (long curr : a) {
            if (b == curr) {
                b *= 2;
            }
        }
        return b;
    }

    public static long pthFactor(long n, long p) {
        // Write your code here
        List<Long> list = new ArrayList<>();
        for (long i=1; i<=Math.sqrt(n); i++) {
            if (n%i==0) {
                if (n/i == i) {
                    list.add(i);

                } else {
                    list.add(i);
                    list.add(n/i);
                }
            }
        }
        if (list.size() <= p) {
            return 0;
        }
        return list.get(Long.valueOf(p).intValue() - 1);
    }
    public static int minMoves(List<Integer> avg) {
        // Write your code here
        int countaboveAvg = 0;
        for (int average : avg) {
            countaboveAvg += ((average == 0) ? 0 : 1);
        }
        int subarraySize = countaboveAvg;
        int dp[] = new int[avg.size()];
        dp[0] = avg.get(0);
        for (int i = 1; i < avg.size(); i++) {
            dp[i] = dp[i-1] + avg.get(i);
        }
        int abvAvgcount = dp[subarraySize];
        int maxOnes = abvAvgcount;
        for (int i = subarraySize; i < avg.size(); i++) {
            abvAvgcount = dp[i] - dp[i-subarraySize];
            maxOnes = Math.max(maxOnes, abvAvgcount);
        }
        int numofZeros = countaboveAvg - maxOnes;
        return numofZeros;
    }
    static int minSwaps(List<Integer> avg) {
        int n = avg.size();
        int noOfOnes = 0;

// find total number of all in the array
        for (int i = 0; i < n; i++) {
            if (avg.get(i) == 1)
                noOfOnes++;
        }

// length of subarray to check for
        int x = noOfOnes;

        int maxOnes = Integer.MIN_VALUE;

// array to store number of 1's upto
// ith index
        int preCompute[] = new int[n];

// calculate number of 1's upto ith
// index and store in the array preCompute[]
        if (avg.get(0) == 1)
            preCompute[0] = 1;
        for (int i = 1; i < n; i++) {
            if (avg.get(i) == 1) {
                preCompute[i] = preCompute[i - 1] + 1;
            } else
                preCompute[i] = preCompute[i - 1];
        }

// using sliding window technique to find
// max number of ones in subarray of length x
        for (int i = x - 1; i < n; i++) {
            if (i == (x - 1))
                noOfOnes = preCompute[i];
            else
                noOfOnes = preCompute[i] - preCompute[i - x];

            if (maxOnes < noOfOnes)
                maxOnes = noOfOnes;
        }

// calculate number of zeros in subarray
// of length x with maximum number of 1's
        int noOfZeroes = x - maxOnes;

        return noOfZeroes;
    }
}
