package basic;

import java.util.Random;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2021/12/1 1:57 上午
 * @Description 公共工具类
 */
public class CommonUtils {
    public static final int[] ARR = {1, 4, 6, 2, 7, 2, 9, 8, 1, 0};

    /**
     * 替换数值
     *
     * @param arr 原数组
     * @param i   被替换元素
     * @param j   替换元素
     */
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 打印数组
     *
     * @param arr 数组
     */
    public static void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            System.out.print(i < arr.length - 1 ? "," : "");
        }
        System.out.print("]\n");
    }

    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static int[] buildRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[maxSize];
        for (int i = 0; i < maxSize; i++) {
            arr[i] = new Random().nextInt(maxValue);
        }
        return arr;
    }

    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    private CommonUtils() {
    }

    public static int[] copyArrayRange(int[] arr, int rightIndex) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[rightIndex];
        for (int i = 0; i < rightIndex; i++) {
            res[i] = arr[i];
        }
        return res;
    }
}
