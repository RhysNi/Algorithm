package heap;

import basic.CommonUtils;

import static basic.CommonUtils.swap;
import static heap.Heap.MaxHeap.heapIfy;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2024/4/18 1:08 AM
 */
public class HeapSort {
    public static void main(String[] args) {
        int[] ints = CommonUtils.buildRandomArray(10, 30);
        heapSort(ints);
        for (int i : ints) {
            System.out.print(i + "\t");
        }
    }

    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        // O(N*logN):从上往下建堆是基于节点层数的
//            for (int i = 0; i < arr.length; i++) {
//                MaxHeap.heapInsert(arr, i);
//            }

        // O(N)
        for (int i = arr.length - 1; i >= 0; i--) {
            heapIfy(arr, i, arr.length);
        }

        int heapSize = arr.length;
        swap(arr, 0, --heapSize);

        // O(N*logN)
        while (heapSize > 0) { // O(N)
            heapIfy(arr, 0, heapSize); // O(logN)
            swap(arr, 0, --heapSize); // O(1)
        }
    }
}
