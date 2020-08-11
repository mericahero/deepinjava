package com.meric.deepinjava.resume.leetcode.easy;

public class A002addTwoNumbers {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode temp1 = l1, temp2 = l2, curr = head;
        int carry = 0;
        while (temp1 != null || temp2 != null) {
            int x = (temp1 != null) ? temp1.val : 0;
            int y = (temp2 != null) ? temp2.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;
            curr.next = new ListNode(sum % 10);
            curr = curr.next;
            if (temp1 != null) temp1 = temp1.next;
            if (temp2 != null) temp2 = temp2.next;
        }
        if (carry > 0) {
            curr.next = new ListNode(carry);
        }
        return head.next;
    }

    public static void main(String[] args) {
        int[] a=new int[]{2,4,3};
        int[] b=new int[]{5,6,4};
        ListNode l1 = null;
        ListNode l2 = null;


        for(int i=0;i<a.length;++i){
            ListNode newNode = new ListNode(a[i]);
            if(l1==null){
                l1=newNode;
                continue;
            }
            ListNode temp = l1;
            while (temp.next!=null){
                temp=temp.next;
            }
            temp.next=newNode;
        }


        for(int i=0;i<b.length;++i){
            ListNode newNode = new ListNode(b[i]);
            if(l2==null){
                l2=newNode;
                continue;
            }
            ListNode temp = l2;
            while (temp.next!=null){
                temp=temp.next;
            }
            temp.next=newNode;
        }


        ListNode result = new A002addTwoNumbers().addTwoNumbers(l1,l2);
    }
}
