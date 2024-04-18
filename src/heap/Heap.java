package heap;

import basic.CommonUtils;

import java.util.Comparator;
import java.util.PriorityQueue;

import static basic.CommonUtils.swap;


/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2024/4/17 10:17 PM
 */
public class Heap {

    static class MaxHeap {
        private int[] heap;
        private int limit;
        private int heapSize;

        public MaxHeap(int limit) {
            this.limit = limit;
            this.heap = new int[limit];
            this.heapSize = 0;
        }

        public boolean isEmpty() {
            return this.heapSize == 0;
        }

        public boolean isFull() {
            return this.heapSize == this.limit;
        }

        public void push(int val) {
            if (this.heapSize == this.limit) {
                throw new RuntimeException("heap is full!");
            }
            // val放到heap对应位置
            this.heap[this.heapSize] = val;
            // 向上升级
            heapInsert(this.heap, this.heapSize++);
        }

        // 返回最大值，并且删除大根堆中的最大值
        public int pop() {
            int result = this.heap[0];
            // 将最大的数和最后一个位置的数交换，将最大数扔到最后，等待下一次插入就会被覆盖了
            swap(this.heap, 0, --heapSize);
            // 由于最后一个位置的数被换到了根节点，破坏了堆结构，要将这个数和各级分支进行对比下沉到合适的位置
            heapIfy(this.heap, 0, heapSize);
            return result;
        }

        private static void heapInsert(int[] heap, int idx) {
            // 拿idx节点的值与它的父根进行比较，比父节点大则与父节点交换，依次向上对比，直到不大于父节点的值停止，或者移动到了整个堆的根节点停止
            while (heap[idx] > heap[(idx - 1) / 2]) {
                swap(heap, idx, (idx - 1) / 2);
                // idx跟随移动到对应节点位置
                idx = (idx - 1) / 2;
            }
        }

        public static void heapIfy(int[] heap, int idx, int heapSize) {
            // 左分支位置
            // idx为0，左分支 0*2+1 = 1;
            // idx为1，左分支 1*2+1 = 3;
            // idx为2，左分支 1*2+1 = 5;
            int left = idx * 2 + 1;
            // 从idx位置不断下沉，当较大的分支没有idx位置的数大的时候，代表已经没有分支了，停止下沉
            while (left < heapSize) {
                // 确保右分支不越界 并且 将左右分支中稍大的一个数拿出来和目标值对比
                int largest = left + 1 < heapSize && heap[left + 1] > heap[left] ? left + 1 : left;
                // 用这个较大节点值去对比父节点值，谁大把谁的下标给到largest
                largest = heap[largest] > heap[idx] ? largest : idx;
                // 代表已经是三个节点中最大的值了，不需要下沉了
                if (largest == idx) {
                    break;
                }
                // idx和较大分支的下标互换，将父节点数下沉
                swap(heap, largest, idx);
                idx = largest;
                left = idx * 2 + 1;
            }
        }
    }

    static class MinHeap {
        public static void main(String[] args) {
            PriorityQueue<Integer> heap = new PriorityQueue<>();
            heap.add(3);
            heap.add(9);
            heap.add(6);
            System.out.println(heap.peek());

            heap.add(8);
            heap.add(5);
            System.out.println(heap.peek());

            while (!heap.isEmpty()) {
                System.out.println(heap.poll());
            }
        }
    }

    static class MaxHeapByPriorityQueue {
        public static void main(String[] args) {
            PriorityQueue<Integer> maxHeap = new PriorityQueue<>(new MaxHeapComparator());
            maxHeap.add(3);
            maxHeap.add(9);
            maxHeap.add(6);
            System.out.println(maxHeap.peek());

            maxHeap.add(8);
            maxHeap.add(5);
            System.out.println(maxHeap.peek());

            while (!maxHeap.isEmpty()) {
                System.out.println(maxHeap.poll());
            }
        }

        private static class MaxHeapComparator implements Comparator<Integer> {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        }
    }
}
