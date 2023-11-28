package mergesort.cases;

import basic.CommonUtils;

import static basic.CommonUtils.printArray;

/**
 * <p>
 * <b>功能描述</b>
 * </p >
 *
 * @author : RhysNi
 * @version : v1.0
 * @date : 2023/11/28 11:07
 * @CopyRight :　<a href="https://blog.csdn.net/weixin_44977377?type=blog">倪倪N</a>
 */
public class CountRangeSumCase {
    public static void main(String[] args) {
        int testTime = 500_000;
        int maxSize = 100;
        int maxValue = 100;

        System.out.println("--- DoubleSumCase test start ---");


        for (int i = 0; i < testTime; i++) {
            int[] arr1 = new int[]{5, 8, 3, 7, 2, 4};

//            int[] arr1 = CommonUtils.buildRandomArray(maxSize, maxValue);
            int[] arr2 = CommonUtils.copyArray(arr1);

            int calcu = calcu(arr1,-2,5);

            int calcuByMergeSort = calcuByMergeSort(arr2);

            if (calcu != calcuByMergeSort) {
                System.out.println("DoubleSumCase test error");
                printArray(arr1);
                printArray(arr2);
                return;
            }
        }
        System.out.println("--- DoubleSumCase test success ---");
    }

    private static int calcu(int[] arr, int lower, int upper) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        // 定义前缀和数组
        int[] preSumArr = new int[arr.length];

        // 第一个元素没有累加元素，即为本身
        preSumArr[0] = arr[0];
        // 计算第二个元素开始的前缀和
        for (int i = 1; i < arr.length; i++) {
            preSumArr[i] = arr[i] + preSumArr[i - 1];
        }

        return countCalcu(preSumArr, 0, preSumArr.length - 1, lower, upper);
    }

    private static int countCalcu(int[] preSumArr, int L, int R, int lower, int upper) {
        if (L == R) {
            return 0;
        }

        int M = (L + (R - L) >> 1);
        return proces(preSumArr, L, M, lower, upper) + proces(preSumArr, M + 1, R, lower, upper) + merge(preSumArr, L, M, R, lower, upper);
    }

    private static int merge(int[] preSumArr, int l, int m, int r, int lower, int upper) {
        return 0;
    }

    private static int proces(int[] preSumArr, int l, int m, int lower, int upper) {
        return 0;
    }

    private static int calcuByMergeSort(int[] arr) {
        return 0;
    }
}
