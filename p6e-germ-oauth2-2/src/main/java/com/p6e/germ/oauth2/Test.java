package com.p6e.germ.oauth2;

/**
 * @author lidashuang
 * @version 1.0
 */
public class Test {
    public static void main(String[] args) {

    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        final ListNode r = new ListNode(0);
        ListNode rr = r;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                rr.next = l1;
                rr = rr.next;
                // 下一个
                l1 = l1.next;
            } else {
                rr.next = l2;
                rr = rr.next;
                l2 = l2.next;
            }
        }
        if (l1 != null) {
            rr.next = l1;
        }
        if (l2 != null) {
            rr.next = l2;
        }
        return r.next;
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}
