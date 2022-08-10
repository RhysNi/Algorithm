package basic.day1;

import basic.CommonUtils;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2021/12/1 1:57 上午
 * @Description
 */
public class BubbleSort {
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

        for (int i = arr.length - 1; i > 0; i--) {
            int maxIndex = i;
            for (int j = i - 1; j >= 0; j--) {
                maxIndex = arr[maxIndex] > arr[j] ? maxIndex : j;
            }

//            for (int j = 0; j < i; j++) {
//                maxIndex = arr[maxIndex] > arr[j] ? maxIndex : j;
//            }
            CommonUtils.swap(arr, i, maxIndex);
        }
    }
}
