package basic.day3.Node;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2021/12/9 12:05 上午
 * @Description
 */
public class MyQueue<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;


    public int size() {
        return this.size;
    }

    public Boolean isEmpty() {
        return size == 0;
    }

    public void offer(T value) {
        //遵循队列先进先出
        //从头节点开始弹出，符合队列先进先出机制
        Node<T> currNode = new Node<>(value);
        if (tail == null) {
            head = currNode;
            tail = currNode;
        } else {
            tail.next = currNode;
            tail = currNode;
        }
        size++;
    }

    public T poll() {
        T value = null;
        if (head != null) {
            //获取当前头结点的值
            value = head.value;
            //让当前头节点的下一个节点成为新的头节点
            head = head.next;
            //每弹出一个值size同步递减
            size--;
        }
        if (head==null){
            //说明没有值了，保持头尾节点一致;
            tail = null;
        }
        return value;
    }

    public T peek() {
        T value = null;
        if (head != null) {
            //获取当前头结点的值
            value = head.value;
        }
        return value;
    }

    public MyQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }
}
