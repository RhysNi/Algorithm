package basic.day3.Node;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2021/12/9 2:45 上午
 * @Description
 */
public class MyStack<T> {
    private Node<T> head;
    private int size;

    public int size() {
        return this.size;
    }

    public Boolean isEmpty() {
        return size == 0;
    }

    /**
     * 压栈
     * @param value
     */
    public void push(T value) {
        //遵循队列先进后出
        //从头节点开始弹出，符合队列先进后出机制
        Node<T> currNode = new Node<>(value);
        if (head != null) {
            currNode.next = head;
        }
        head = currNode;
        size++;
    }

    /**
     * 弹栈
     * @return
     */
    public T pop() {
        T val = null;
        if (head != null) {
            val = head.value;
            head = head.next;
            size--;
        }
        return val;
    }

    /**
     * 查看栈顶值
     * @return
     */
    public T peek() {
        return head == null ? null : head.value;
    }
}
