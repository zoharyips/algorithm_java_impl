package com.zohar.linkedlist;

import com.zohar.linkedlist.ListNode;

public class SingleLinkedList {

    public static void main(String[] args) {
        ListNode one = new ListNode(1);
        ListNode two = new ListNode(2);
        ListNode three = new ListNode(3);
        ListNode four = new ListNode(4);
        one.next = two;
        two.next = three;
        three.next = four;
        swapPairs(one);

    }

    public static ListNode swapPairs(ListNode head) {
        /* 指针操作 */
        if (head == null || head.next == null) {
            return head;
        }
        /* 双指针加临时指针 */
        ListNode fastNode = head.next, slowNode = head, lastNode = null;
        while (fastNode != null) {
            /* 慢节点 next 指针指向二者下一个节点 */
            slowNode.next = fastNode.next;
            /* 快节点 next 指针指向慢节点 */
            fastNode.next = slowNode;
            /* 慢节点前一节点 next 指针指向快节点 */
            if (lastNode != null) {
                lastNode.next = fastNode;
            } else {
                head = fastNode;
            }
            lastNode = slowNode;
            slowNode = slowNode.next;
            if (slowNode == null || slowNode.next == null) {
                return head;
            }
            fastNode = slowNode.next;
        }
        return head;
    }

}
