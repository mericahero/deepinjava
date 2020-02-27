package com.meric.deepinjava.resume.fact;

public class ReverseLinkListSolution {
    public static class Node {
        int val;
        Node next;
        Node(int x) { val = x; }

        @Override
        public String toString() {
            return String.valueOf(val) + ", next: "+ next==null? "":String.valueOf(next.val);
        }
    }

    public static class LinkList{
        public Node head;

        public void create(int[] ary){
            Node newNode ;
            Node tail = new Node(0);
            head = tail;
            for(int i=0;i<ary.length;++i){
                newNode = new Node(ary[i]);
                tail.next=newNode;
                tail=newNode;
            }
        }

        public void print(){
            Node node = head.next;
            while(node!=null){
                System.out.print(node.val+" ----->  ");
                node=node.next;
            }
            System.out.println("null");
        }

        public void reverse(){
            if(head==null || head.next==null){
                return;
            }
            Node p = head.next;
            Node q = p.next;
            p.next= null;

            Node temp;
            while (q!=null){
                temp = q.next;
                q.next = p;
                p=q;
                q=temp;
            }

            head.next=p;
        }

        public static void main(String[] args) {
            LinkList list = new LinkList();
            list.create(new int[]{1,2,3,4});
            list.print();
            list.reverse();
            list.print();
        }
    }

}
