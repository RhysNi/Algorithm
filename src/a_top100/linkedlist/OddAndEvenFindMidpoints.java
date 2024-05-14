package a_top100.linkedlist;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2024/5/15 1:03 AM
 */
public class OddAndEvenFindMidpoints {

    // 输入链表头节点，奇数长度返回中点，偶数长度返回上中点
    // 1 -> 3 -> 5        : 3
    // 2 -> 4 -> 5 -> 6   : 4
    // 都是返回slow节点
    public static Node oddMidpointsAndEvenUpperMidpoints(Node head) {
        // 保证链表参与逻辑的有3个节点或更多
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }

        // 走步 快2慢1
        Node slowNode = head;
        Node fastNode = head.next.next;

        while (fastNode != null && fastNode.next.next != null) {
            slowNode = slowNode.next;
            fastNode = fastNode.next.next;
        }

        return slowNode;
    }

    public static class Node {
        public int value;
        public Node next;

        public Node(int v) {
            value = v;
        }
    }
}
