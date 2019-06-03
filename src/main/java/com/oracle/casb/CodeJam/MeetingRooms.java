package com.oracle.casb.CodeJam;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Created By : abhijsri
 * Date  : 11/09/18
 **/
public class MeetingRooms {

    public static void main(String[] args) {
        MeetingRooms meetingRooms = new MeetingRooms();
        meetingRooms.testMinRoomsReq();
    }

    private void testMinRoomsReq() {
        int[][] intervals = {{0,10}, {3,7}, {5,10}, {8,20}, {10,15}, {7,8}};
        int rooms = getMinRooms(intervals);
        int rooms1 = getMinRooms1(intervals);
        int room2 = getMinRooms3(intervals);
        System.out.println("Total rooms " + rooms);
    }

    private int getMinRooms3(int[][] intervals) {
        TimePoint[] timePoints = new TimePoint[intervals.length * 2];
        for (int i = 0; i < intervals.length; i++) {
            timePoints[2*i] = new TimePoint(intervals[i][0], true);
            timePoints[2*i+1] = new TimePoint(intervals[i][1], false);
        }
        Arrays.sort(timePoints, (a,b) -> compare(a,b));
        int count = 0;
        int max = 0;
        for (TimePoint t : timePoints) {
            if (t.isStart) {
                count += 1;
            } else {
                count -= 1;
            }
            if (max < count) {
                max = count;
            }

        }
        return max;
    }

    private int compare(TimePoint a, TimePoint b) {
        return a.value == b.value ? (a.isStart ? 1 : -1)  : (a.value - b.value);
    }

    private int getMinRooms1(int[][] intervals) {
        int[] start = new int[intervals.length];
        int[] end = new int[intervals.length];
        int index = 0;
        for (int[] interval : intervals) {
            start[index] = interval[0];
            end[index++] = interval[1];
        }
        Arrays.sort(start);
        Arrays.sort(end);
        return 0;
    }

    private int getMinRooms(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        PriorityQueue<Integer> arranger = new PriorityQueue<>(intervals.length, (a,b) -> a-b);
        arranger.add(intervals[0][1]);
        int size = 1;
        for (int i = 1; i < intervals.length; i++) {
            if (arranger.peek() <= intervals[i][0]) {
                arranger.remove();
            }
            arranger.add(intervals[i][1]);
            if (arranger.size() > size) {
                size = arranger.size();
            }
        }
        return arranger.size();
    }
    class TimePoint {
        int value;
        boolean isStart;

        public TimePoint(int value, boolean isStart) {
            this.value = value;
            this.isStart = isStart;
        }
    }
    class Interval {
        private int start;
        private int end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
        public int getDuration() {
            return end - start + 1;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }
    }
}
