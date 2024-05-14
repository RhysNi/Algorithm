package a_top100.linkedlist;

/**
 * <p>
 * <b>功能描述</b>
 * </p >
 *
 * @author : RhysNi
 * @version : v1.0
 * @date : 2024/5/7 16:30
 * @CopyRight :　<a href="https://blog.csdn.net/weixin_44977377?type=blog">倪倪N</a>
 */
public class PalindromeLinkedList {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(3);
        head.next.next.next.next = new ListNode(2);
        head.next.next.next.next.next = new ListNode(1);

        System.out.println(isPalindrome(head));
    }

    public static boolean isPalindrome(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;

        // 快指针没到链表末尾继续移动
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
            // 快指针移动第二次，当快指针到末尾代表慢指针刚好在中点
            if (fast != null) {
                fast = fast.next;
            }
        }

        // 反转后半段链表
        ListNode reverseList = new ListNode(-1);
        ListNode nextNode = null;
        while (slow != null) {
            // 提前记录慢指针位置下一个节点信息
            ListNode slowNext = slow.next;
            // 将nextNode位置的元素给到slow.next节点
            slow.next = nextNode;
            // 将当前slow节点存到reverseList
            reverseList.next = slow;
            nextNode = slow;
            slow = slowNext;
        }

        // 对比
        reverseList = reverseList.next;
        while (reverseList != null) {
            if (reverseList.val != head.val) {
                return false;
            }
            reverseList = reverseList.next;
            head = head.next;
        }
        return true;
    }


}

class ListNode {
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