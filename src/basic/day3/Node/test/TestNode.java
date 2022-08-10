package basic.day3.Node.test;


import basic.day3.Node.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2021/12/7 11:11 下午
 * @Description
 */
public class TestNode {
    public static void main(String[] args) {
        int len = 3;
        int value = 100;
        int testTime = 100000;
        System.out.println("test begin!");
        for (int i = 0; i < testTime; i++) {
            Node<Integer> node1 = generateRandomLinkedList(len, value);
            List<Integer> list1 = getLinkedListOriginOrder(node1);
            node1 = reverse(node1);
            if (!checkLinkedListReverse(list1, node1)) {
                System.out.println("Oops1!");
            }

            Node<Integer> node2 = generateRandomLinkedList(len, value);
            List<Integer> list2 = getLinkedListOriginOrder(node2);
            node2 = testReverseLinkedList(node2);
            if (!checkLinkedListReverse(list2, node2)) {
                System.out.println("Oops2!");
            }
        }
        System.out.println("test finish!");

    }

    //a -> b -> c -> null
    //null <- a <- b <- c
    public static Node<Integer> reverse(Node<Integer> head) {
        Node<Integer> pre = null;
        Node<Integer> next = null;
        while (head != null) {
            //提前记录next节点
            next = head.next;
            //将头结点的下一个节点指向Pre
            head.next = pre;
            //pre移动到当前头节点位置
            pre=head;
            //头结点移动到提前记录的next位置
            head=next;
        }
        //当head为null时说明已经到头了,此时应该返回pre所在位置的节点作为头结点
        return pre;
    }

    public static Node<Integer> testReverseLinkedList(Node<Integer> head) {
        if (head == null) {
            return null;
        }
        ArrayList<Node<Integer>> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }
        list.get(0).next = null;
        int N = list.size();
        for (int i = 1; i < N; i++) {
            list.get(i).next = list.get(i - 1);
        }
        return list.get(N - 1);
    }

    public static Node<Integer> generateRandomLinkedList(int len, int value) {
        int size = (int) (Math.random() * (len + 1));
        if (size == 0) {
            return null;
        }
        size--;
        Node<Integer> head = new Node<>((int) (Math.random() * (value + 1)));
        Node<Integer> pre = head;
        while (size != 0) {
            Node<Integer> cur = new Node<>((int) (Math.random() * (value + 1)));
            pre.next = cur;
            pre = cur;
            size--;
        }
        return head;
    }

    public static List<Integer> getLinkedListOriginOrder(Node<Integer> head) {
        List<Integer> ans = new ArrayList<>();
        while (head != null) {
            ans.add(head.value);
            head = head.next;
        }
        return ans;
    }

    public static boolean checkLinkedListReverse(List<Integer> origin, Node<Integer> head) {
        for (int i = origin.size() - 1; i >= 0; i--) {
            if (!origin.get(i).equals(head.value)) {
                return false;
            }
            head = head.next;
        }
        return true;
    }
}
