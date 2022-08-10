package basic.day3.DoubleNode.test;

import basic.day3.DoubleNode.DoubleNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2021/12/7 11:11 下午
 * @Description
 */
public class TestDoubleNode {
    public static void main(String[] args) {
        int len = 50;
        int value = 100;
        int testTime = 100000;
        System.out.println("test begin!");
        for (int i = 0; i < testTime; i++) {
            DoubleNode<Integer> node1 = generateRandomDoubleList(len, value);
            List<Integer> list3 = getDoubleListOriginOrder(node1);
            node1 = reverse(node1);
            if (!checkDoubleListReverse(list3, node1)) {
                System.out.println("Oops1!");
            }

            DoubleNode<Integer> node2 = generateRandomDoubleList(len, value);
            List<Integer> list4 = getDoubleListOriginOrder(node2);
            node2 = reverse(node2);
            if (!checkDoubleListReverse(list4, node2)) {
                System.out.println("Oops2!");
            }

        }
        System.out.println("test finish!");
    }

    public static DoubleNode<Integer> reverse(DoubleNode<Integer> head) {
        DoubleNode<Integer> pre = null;
        DoubleNode<Integer> next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            head.last = next;
            pre = head;
            head = next;
        }
        return pre;
    }

    public static DoubleNode<Integer> testReverseDoubleList(DoubleNode<Integer> head) {
        if (head == null) {
            return null;
        }
        ArrayList<DoubleNode<Integer>> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }
        list.get(0).next = null;
        DoubleNode<Integer> pre = list.get(0);
        int N = list.size();
        for (int i = 1; i < N; i++) {
            DoubleNode<Integer> cur = list.get(i);
            cur.last = null;
            cur.next = pre;
            pre.last = cur;
            pre = cur;
        }
        return list.get(N - 1);
    }

    public static List<Integer> getDoubleListOriginOrder(DoubleNode<Integer> head) {
        List<Integer> ans = new ArrayList<>();
        while (head != null) {
            ans.add(head.value);
            head = head.next;
        }
        return ans;
    }

    public static boolean checkDoubleListReverse(List<Integer> origin, DoubleNode<Integer> head) {
        DoubleNode<Integer> end = null;
        for (int i = origin.size() - 1; i >= 0; i--) {
            if (!origin.get(i).equals(head.value)) {
                return false;
            }
            end = head;
            head = head.next;
        }
        for (int i = 0; i < origin.size(); i++) {
            if (!origin.get(i).equals(end.value)) {
                return false;
            }
            end = end.last;
        }
        return true;
    }

    public static DoubleNode<Integer> generateRandomDoubleList(int len, int value) {
        int size = (int) (Math.random() * (len + 1));
        if (size == 0) {
            return null;
        }
        size--;
        DoubleNode<Integer> head = new DoubleNode((int) (Math.random() * (value + 1)));
        DoubleNode<Integer> pre = head;
        while (size != 0) {
            DoubleNode<Integer> cur = new DoubleNode((int) (Math.random() * (value + 1)));
            pre.next = cur;
            cur.last = pre;
            pre = cur;
            size--;
        }
        return head;
    }
}
