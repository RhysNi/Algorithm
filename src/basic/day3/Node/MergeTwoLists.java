package basic.day3.Node;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2021/12/22 8:35 下午
 * @Description 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 * @leetcode：https://leetcode-cn.com/problems/merge-two-sorted-lists/
 */
public class MergeTwoLists {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null || list2 == null) {
            return list1 == null ? list2 : list1;
        }

        //找到最小的作为头节点（升序链表）
        ListNode head = list1.val <= list2.val ? list1 : list2;
        ListNode pre = head;
        ListNode curr1 = head.next;
        ListNode curr2 = head == list1 ? list2 : list1;
        //两链表都有节点
        while (curr1 != null && curr2 != null) {
            if (curr1.val <= curr2.val) {
                pre.next = curr1;
                curr1 = curr1.next;
            } else {
                pre.next = curr2;
                curr2 = curr2.next;
            }
            pre = pre.next;
        }
        //仅剩一个链表有节点
        pre.next = curr1 == null ? curr2 : curr1;
        return head;
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
