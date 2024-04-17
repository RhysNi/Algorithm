package heap;

import basic.CommonUtils;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2024/4/18 2:23 AM
 */
public class SortArrayDistanceLessK {
    public static void main(String[] args) {
        int[] ints = Arrays.stream(CommonUtils.buildRandomArray(10, 30)).distinct().toArray();
        ints = randomArrayNoMoveMoreK(ints, 5);

        sortArrayDistanceLessK(ints, 5);
        for (int anInt : ints) {
            System.out.print(anInt + "\t");
        }

    }

    public static void sortArrayDistanceLessK(int[] arr, int k) {
        if (k == 0) {
            return;
        }

        // 小根堆
        PriorityQueue<Integer> heap = new PriorityQueue<>();

        int index = 0;
        // 只有0 ... k-1位置的数才可能移动到0位置
        for (; index <= Math.min(arr.length - 1, k - 1); index++) {
            // 把0...k-1所有元素放到小根堆
            heap.add(arr[index]);
        }

        int i = 0;
        // 把K-1以后得数挨个从0位置开始往后放，放一个弹一个最小值出去
        for (; index < arr.length; i++, index++) {
            heap.add(arr[index]);
            arr[i] = heap.poll();
        }

        while (!heap.isEmpty()) {
            arr[i++] = heap.poll();
        }
    }

    public static int[] randomArrayNoMoveMoreK(int[] arr, int K) {
        // 先排个序
        Arrays.sort(arr);
        // 然后开始随意交换，但是保证每个数距离不超过K
        // swap[i] == true, 表示i位置已经参与过交换
        // swap[i] == false, 表示i位置没有参与过交换
        boolean[] isSwap = new boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int j = Math.min(i + (int) (Math.random() * (K + 1)), arr.length - 1);
            if (!isSwap[i] && !isSwap[j]) {
                isSwap[i] = true;
                isSwap[j] = true;
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }
        return arr;
    }
}
