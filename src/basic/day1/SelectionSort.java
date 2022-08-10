package basic.day1;

import basic.CommonUtils;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2021/12/1 1:37 上午
 * @Description
 */
public class SelectionSort {
    public static void main(String[] args) {
        int[] arr = CommonUtils.ARR;
        CommonUtils.printArray(arr);
        sort(arr);
        CommonUtils.printArray(arr);
    }

    public static void sort(int[] arr) {
        //边界控制
        if (arr != null && arr.length < 2) {
            return;
        }

        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                minIndex = arr[minIndex] < arr[j] ? minIndex : j;
            }
            CommonUtils.swap(arr, i, minIndex);
        }
    }
}
