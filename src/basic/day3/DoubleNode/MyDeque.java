package basic.day3.DoubleNode;


/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2021/12/13 1:34 上午
 * @Description
 */
public class MyDeque<T> {
    private DoubleNode<T> head;
    private DoubleNode<T> tail;
    private int size;

    public void pushHead(T value) {
        DoubleNode<T> curr = new DoubleNode<>(value);
        if (head == null) {
            head = curr;
            tail = curr;
        } else {
            head.last = curr;
            curr.next = head;
            head = curr;
        }
        size++;
    }

    public void pushTail(T value) {
        DoubleNode<T> curr = new DoubleNode<>(value);
        if (head == null) {
            head = curr;
            tail = curr;
        } else {
            tail.next = curr;
            curr.last = tail;
            tail = curr;
        }
        size++;
    }

    public T pollHead() {
        T value = null;
        if (head == null) {
            return value;
        }
        value = head.value;
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.last = null;
        }
        size--;
        return value;
    }

    public T pollTail() {
        T value = null;
        if (tail == null) {
            return value;
        }
        value = tail.value;
        if (tail == head) {
            tail = null;
            head = null;
        } else {
            tail = tail.last;
            tail.next = null;
        }
        size--;
        return value;
    }

    public T peekHead() {
        T value = null;
        if (!isEmpty()) {
            value = head.value;
        }
        return value;
    }

    public T peekTail() {
        T value = null;
        if (!isEmpty()) {
            value = tail.value;
        }
        return value;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public MyDeque() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
}
