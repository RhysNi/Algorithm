package heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2024/4/18 9:04 PM
 */
public class StrengthenHeap<T> {
    private ArrayList<T> heap;
    private HashMap<T, Integer> indexMap;
    private int heapSize;
    private Comparator<T> comparator;

    public StrengthenHeap(Comparator<T> comparator) {
        this.comparator = comparator;
        this.heap = new ArrayList<>();
        this.indexMap = new HashMap<>();
        this.heapSize = 0;
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public int size() {
        return heapSize;
    }


    public boolean contains(T obj) {
        return indexMap.containsKey(obj);
    }

    public T peek() {
        return heap.get(0);
    }

    public void push(T obj) {
        heap.add(obj);
        indexMap.put(obj, heapSize);
        heapInsert(heapSize++);
    }

    public T pop() {
        T t = heap.get(0);
        // 获取顶部节点元素与最后节点护换
        swap(0, heapSize - 1);
        // 从反向查找表删除对应key
        indexMap.remove(t);
        // 从堆中移除最后一个元素
        heap.remove(--heapSize);
        // 重构堆结构，将换到顶部节点的元素下沉
        heapIfy(0);
        return t;
    }

    public void remove(T obj) {
        // 获取最后一个元素
        T t = heap.get(heapSize - 1);
        // 找到这个元素在反向查找表中的位置
        Integer idx = indexMap.get(obj);
        indexMap.remove(obj);
        heap.remove(--heapSize);
        // 处理非最后一个节点的元素
        if (obj != t) {
            // 将最后一个元素换到删除元素的位置顶替
            heap.set(idx, t);
            indexMap.put(t, idx);
            // 重调堆结构
            resign(t);
        }
    }

    public void resign(T t) {
        // 同时调用上升和下沉，必然会执行其中一个
        heapInsert(indexMap.get(t));
        heapIfy(indexMap.get(t));
    }

    private void heapInsert(int idx) {
        // 拿idx节点的值与它的父根进行比较，比父节点大则与父节点交换，依次向上对比，直到不大于父节点的值停止，或者移动到了整个堆的根节点停止
        while (comparator.compare(heap.get(idx), heap.get((idx - 1) / 2)) < 0) {
            swap(idx, (idx - 1) / 2);
            // idx跟随移动到对应节点位置
            idx = (idx - 1) / 2;
        }
    }

    private void swap(int idx, int i) {
        // 元素位置变动后，反向查找表也要随之变动
        T t1 = heap.get(idx);
        T t2 = heap.get(i);
        heap.set(idx, t2);
        heap.set(i, t1);
        indexMap.put(t2, idx);
        indexMap.put(t1, i);
    }

    public void heapIfy(int idx) {
        // 左分支位置
        // idx为0，左分支 0*2+1 = 1;
        // idx为1，左分支 1*2+1 = 3;
        // idx为2，左分支 1*2+1 = 5;
        int left = idx * 2 + 1;
        // 从idx位置不断下沉，当较大的分支没有idx位置的数大的时候，代表已经没有分支了，停止下沉
        while (left < heapSize) {
            // 确保右分支不越界 并且 将左右分支中稍大的一个数拿出来和目标值对比
            int largest = left + 1 < heapSize && comparator.compare(heap.get(left + 1), heap.get(left)) < 0 ? left + 1 : left;
            // 用这个较大节点值去对比父节点值，谁大把谁的下标给到largest
            largest = comparator.compare(heap.get(largest), heap.get(idx)) < 0 ? largest : idx;
            // 代表已经是三个节点中最大的值了，不需要下沉了
            if (largest == idx) {
                break;
            }
            // idx和较大分支的下标互换，将父节点数下沉
            swap(largest, idx);
            idx = largest;
            left = idx * 2 + 1;
        }
    }

    public List<T> getAll() {
        List<T> list = new ArrayList<>();
        for (T t : heap) {
            list.add(t);
        }
        return list;
    }
}
