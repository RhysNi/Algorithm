package basic.day1;

import basic.CommonUtils;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2021/12/1 2:26 上午
 * @Description
 */
public class InsertSort {
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

        for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
                CommonUtils.swap(arr, j, j + 1);
            }
        }
    }
}
