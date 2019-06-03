package com.oracle.casb.common;

/**
 * Created By : abhijsri
 * Date  : 18/05/18
 **/
public class ListNode {
    private int value;
    private ListNode next;

    public ListNode() {
    }

    public ListNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }
}
