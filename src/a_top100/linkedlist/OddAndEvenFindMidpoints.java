package a_top100.linkedlist;

import java.util.ArrayList;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2024/5/15 1:03 AM
 */
public class OddAndEvenFindMidpoints {

    // 输入链表头节点，奇数长度返回中点，偶数长度返回上中点
    // 1 -> 3 -> 5        : 3
    // 1 -> 3 -> 5 -> 6   : 3
    // 都是返回slow节点
    public static Node oddMidpointsAndEvenUpperMidpoints(Node head) {
        // 保证链表参与逻辑的有3个节点或更多
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }

        // 从第二个节点开始走 快2慢1
        Node slowNode = head.next;
        Node fastNode = head.next.next;

        while (fastNode.next != null && fastNode.next.next != null) {
            slowNode = slowNode.next;
            fastNode = fastNode.next.next;
        }

        return slowNode;
    }


    // 输入链表头节点，奇数长度返回中点，偶数长度返回下中点
    // 1 -> 3 -> 5        : 3
    // 1 -> 3 -> 5 -> 6   : 5
    // 偶数情况返回slow节点下一个
    public static Node oddMidpointsAndEvenLowerMidpoints(Node head) {
        // 至少保证有两个节点
        if (head == null || head.next == null) {
            return head;
        }

        // 从偶数节点开始，从同一个起点出发，当快指针到达终点， 慢指针刚好处于快指针的后一位，刚好是下中点
        Node slow = head.next;
        Node fast = head.next;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    //输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个
    // 1 -> 3 -> 5        : 1
    // 1 -> 3 -> 5 -> 6   : 1
    // 只关注slow节点即可
    public static Node oddPreMidpointsAndEvenPreUpperMidpoints(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }

        // slow从第一个节点出发，fast从第三个节点出发，如果
        Node slow = head;
        Node fast = head.next.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // slow在1位置，fast在5位置，无法往下走了自然返回slow
        return slow;
    }

    //输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个 = 上中点
    // 1 -> 3 -> 5        : 1
    // 1 -> 3 -> 5 -> 6   : 3
    // 偶数情况返回slow节点下一个
    public static Node oddPreMidpointsAndEvenPreLowerMidpoints(Node head) {
        if (head == null || head.next == null) {
            return null;
        }

        // 偶数直接返回上中点（下中点的前一个）
        if (head.next.next == null) {
            return head;
        }

        Node slow = head;
        Node fast = head.next;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static class Node {
        public int value;
        public Node next;

        public Node(int v) {
            value = v;
        }
    }


    public static Node right1(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get((arr.size() - 1) / 2);
    }

    public static Node right2(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get(arr.size() / 2);
    }

    public static Node right3(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get((arr.size() - 3) / 2);
    }

    public static Node right4(Node head) {
        if (head == null || head.next == null) {
            return null;
        }
        Node cur = head;
        ArrayList<Node> arr = new ArrayList<>();
        while (cur != null) {
            arr.add(cur);
            cur = cur.next;
        }
        return arr.get((arr.size() - 2) / 2);
    }

    public static void main(String[] args) {
        Node test = null;
        test = new Node(0);
        test.next = new Node(1);
        test.next.next = new Node(2);
        test.next.next.next = new Node(3);
        test.next.next.next.next = new Node(4);
        test.next.next.next.next.next = new Node(5);
        test.next.next.next.next.next.next = new Node(6);
        test.next.next.next.next.next.next.next = new Node(7);
        test.next.next.next.next.next.next.next.next = new Node(8);

        Node ans1 = null;
        Node ans2 = null;

        ans1 = oddMidpointsAndEvenUpperMidpoints(test);
        ans2 = right1(test);
        System.out.println(ans1 != null ? ans1.value : "无");
        System.out.println(ans2 != null ? ans2.value : "无");

        ans1 = oddMidpointsAndEvenLowerMidpoints(test);
        ans2 = right2(test);
        System.out.println(ans1 != null ? ans1.value : "无");
        System.out.println(ans2 != null ? ans2.value : "无");

        ans1 = oddPreMidpointsAndEvenPreUpperMidpoints(test);
        ans2 = right3(test);
        System.out.println(ans1 != null ? ans1.value : "无");
        System.out.println(ans2 != null ? ans2.value : "无");

        ans1 = oddPreMidpointsAndEvenPreLowerMidpoints(test);
        ans2 = right4(test);
        System.out.println(ans1 != null ? ans1.value : "无");
        System.out.println(ans2 != null ? ans2.value : "无");

    }
}
