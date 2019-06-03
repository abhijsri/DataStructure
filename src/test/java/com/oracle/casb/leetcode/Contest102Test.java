package com.oracle.casb.leetcode;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Contest102Test {


    @Test
    public void testIntToStr() {
        Contest102 contest = new Contest102();
        String actualValue = contest.intToStr(251032);
        String expected = "Two Lakh FiftyOne Thousand ThirtyTwo";
        System.out.printf("Expected %s, Actual %s\n", expected, actualValue);
        assertTrue(expected.equals(actualValue));
    }
}
