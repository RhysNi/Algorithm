package basic.day3.Node;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2021/12/20 10:57 下午
 * @Description 两链表相加
 * @leetcode：https://leetcode.com/problems/add-two-numbers/
 */
public class AddTwoNumbers {
    public ListNode addTwoNumbers(ListNode head1, ListNode head2) {
        int len1 = calcLength(head1);
        int len2 = calcLength(head2);
        ListNode longLink = len1 >= len2 ? head1 : head2;
        ListNode shortLink = longLink == head1 ? head2 : head1;
        ListNode currL = longLink;
        ListNode currS = shortLink;
        ListNode last = null;
        int carry = 0;
        int nodeSum = 0;
        //一阶段：长短链表都有的情况 直接判断短链表是否为NULL得知是否一阶段结束
        while (currS != null) {
            nodeSum = currL.val + currS.val + carry;
            currL.val = nodeSum % 10;
            carry = nodeSum / 10;
            last = currL;
            currL = currL.next;
            currS = currS.next;
        }

        //二阶段，长链表有，短链表无
        while (currL != null) {
            nodeSum = currL.val + carry;
            currL.val = nodeSum % 10;
            carry = nodeSum / 10;
            last = currL;
            currL = currL.next;
        }

        //三阶段：长短链表都无，校验进位信息决定是否补新节点
        if (carry != 0) {
            last.next = new ListNode(1);
        }
        return longLink;
    }

    /**
     * 计算链表长度
     *
     * @param node
     * @return
     */
    public int calcLength(ListNode node) {
        int length = 0;
        while (node != null) {
            length++;
            node = node.next;
        }
        return length;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
