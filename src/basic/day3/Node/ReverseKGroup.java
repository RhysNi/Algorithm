package basic.day3.Node;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2021/12/16 8:34 下午
 * @Description Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode() {}
 * ListNode(int val) { this.val = val; }
 * ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
public class ReverseKGroup<T> {
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode start = head;
        ListNode groupEnd = findGroupEnd(start, k);
        //凑不够一组直接返回当前头节点
        if (groupEnd == null) {
            return head;
        }
        //第一组满足K个
        head = groupEnd;
        reverse(start, groupEnd);
        //上一组的最后一个节点（其实就是上一组的开始节点，经过反转后成为最后一个节点）
        ListNode lastEnd = start;
        //当上一组的最后一个节点不指向Null的时候（如果最后没凑够一组也是会返回Null的）
        while (lastEnd.next != null) {
            //将上一组最后一个节点的下一个节点设置为下一组的开始节点
            start = lastEnd.next;
            groupEnd = findGroupEnd(start, k);
            if (groupEnd == null) {
                return head;
            }
            reverse(start, groupEnd);
            //上一组反转后的最后一个节点指向这一组的结束节点
            lastEnd.next = groupEnd;
            //将下一组的开始节点设置为反转后的最后一个节点
            lastEnd = start;
        }
        return head;
    }

    /**
     * 组内反转
     *
     * @param start
     * @param groupEnd
     * @return
     */
    public void reverse(ListNode start, ListNode groupEnd) {
        ListNode pre = null;
        ListNode curr = start;
        //代名词为：本组反转操作的最后标志位，实际为下一组的开始节点
        ListNode operationEnd = groupEnd.next;
        //如果Curr标志位跳到操作终止节点代表本组反转已经结束
        while (curr != operationEnd) {
            ListNode next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        //本组的开始节点(反转后应该是最后一个节点)，指向下一组的开始节点
        start.next = operationEnd;
    }

    /**
     * 找每组最后一个节点
     *
     * @param start
     * @param k
     * @return
     */
    public ListNode findGroupEnd(ListNode start, int k) {
        while (--k != 0 && start != null) {
            start = start.next;
        }
        return start;
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
