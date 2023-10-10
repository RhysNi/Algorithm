package mergesort;

import basic.CommonUtils;

import static basic.CommonUtils.printArray;

/**
 * <p>
 * <b>归并排序</b>
 * </p >
 *
 * @author : RhysNi
 * @version : v1.0
 * @date : 2023/9/18 15:28
 * @CopyRight :　<a href="https://blog.csdn.net/weixin_44977377?type=blog">倪倪N</a>
 */
public class MergeSortMain {
    public static void main(String[] args) {
        int testTime = 500_000;
        int maxSize = 100;
        int maxValue = 100;

        System.out.println("--- MergeSort test start ---");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = CommonUtils.buildRandomArray(maxSize, maxValue);
            int[] arr2 = CommonUtils.copyArray(arr1);

            // 递归法
            MergeSortByRecursion.mergeSort(arr1);
            // 步长法
            MergeSortNonRecursion.mergeSort(arr2);

            if (!CommonUtils.isEqual(arr1, arr2)) {
                System.out.println("MergeSort test error");
                printArray(arr1);
                printArray(arr2);
            }
        }
        System.out.println("--- MergeSort test success ---");
    }

    public static void merge(int[] arr, int startIndex, int mid, int endIndex) {
        // 辅助数组用于最终合并数据，长度为起始索引到结束索引的所有元素的个数
        int[] tempArr = new int[endIndex - startIndex + 1];
        int i = 0;
        int startIndexL = startIndex;
        int startIndexR = mid + 1;

        // 越界判断，保证左右两部分都没有越界
        while (startIndexL <= mid && startIndexR <= endIndex) {
            // 通过指针移动使左右两边的元素一一比较，哪边小就取那边，相等则默认取左边（实际取哪边都一样）
            tempArr[i++] = arr[startIndexL] <= arr[startIndexR] ? arr[startIndexL++] : arr[startIndexR++];
        }

        //当左边越界,右边没越界时，放入右组元素
        while (startIndexR <= endIndex) {
            tempArr[i++] = arr[startIndexR++];
        }
        //当右边越界，左边没越界时，放入左组元素
        while (startIndexL <= mid) {
            tempArr[i++] = arr[startIndexL++];
        }

        // 将最终排序完成的数组传递给原数组
        for (int j = 0; j < tempArr.length; j++) {
            // 从最新的起始索引开始往后
            arr[startIndex + j] = tempArr[j];
        }
    }
}