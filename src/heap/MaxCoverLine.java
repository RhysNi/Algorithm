package heap;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2024/4/19 12:38 AM
 */
public class MaxCoverLine {
    public static void main(String[] args) {
        //随机生成N个线段进行统计
        System.out.println(maxCover(generateLines(100, 0, 200)));
    }

    public static int maxCover(int[][] lineArray) {
        // 将所有线段按照起始索引排序
        Arrays.sort(lineArray, Comparator.comparingInt(a -> a[0]));

        PriorityQueue<Integer> heap = new PriorityQueue<>();
        // 定义重合最多的线段数
        int max = 0;
        // 从最小的线段开始，将小于star位置的元素弹出去，并且将end位置元素存添加到小根堆
        for (int[] line : lineArray) {
            while (!heap.isEmpty() && heap.peek() <= line[0]) {
                heap.poll();
            }
            heap.add(line[1]);
            // 永远返回最大的值
            max = Math.max(max, heap.size());
        }
        return max;
    }


    public static class Line {
        public int start;
        public int end;

        public Line(int s, int e) {
            start = s;
            end = e;
        }
    }

    public static int[][] generateLines(int N, int L, int R) {
        int size = (int) (Math.random() * N) + 1;
        int[][] ans = new int[size][2];
        for (int i = 0; i < size; i++) {
            int a = L + (int) (Math.random() * (R - L + 1));
            int b = L + (int) (Math.random() * (R - L + 1));
            if (a == b) {
                b = a + 1;
            }
            ans[i][0] = Math.min(a, b);
            ans[i][1] = Math.max(a, b);
        }
        return ans;
    }
}
