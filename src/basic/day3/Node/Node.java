package basic.day3.Node;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2021/12/5 9:59 上午
 * @Description 单向链表节点结构
 */
public class Node<T> {
    public T value;
    public Node<T> next;

    public Node(T value) {
        this.value = value;
    }


    //a -> b -> c -> null
    //null <- a <- b <- c
    public Node<T> reverse(Node<T> head) {
        Node<T> pre = null;
        Node<T> next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre=head;
            head=next;
        }
        return pre;
    }
}
