package com.oracle.casb.leetcode;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SkyLineTest {

    @Test
    public void testSkyLine() {
        SkyLine skyLine = new SkyLine();
        int[][][] expectedList = {{},{}, {}};
        int[][] arg = {{2,9,10},{4,5,6}};
        assertNotEquals(expectedList, skyLine.getSkyline(arg));
    }

    @Test
    public void testMergeSkylines() {
        SkyLine skyLine = new SkyLine();
        int[] first = new int[] {2, 6, 12};
        int[] second = new int[] {3, 8, 10};
        List<int[]> list = skyLine.mergeSkylines(first, second);

        for (int[] sl : list) {
            System.out.printf("Skyline [%s]\n", Arrays.stream(sl).boxed().map(String::valueOf).collect(Collectors.joining(", ")));
        }
    }
}
