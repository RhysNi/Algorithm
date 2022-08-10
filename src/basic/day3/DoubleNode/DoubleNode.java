package basic.day3.DoubleNode;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2021/12/5 10:35 上午
 * @Description
 */
public class DoubleNode<T> {
    public T value;
    public DoubleNode<T> last;
    public DoubleNode<T> next;

    public DoubleNode(T value) {
        this.value = value;
    }
}
