package quicksort;

import basic.CommonUtils;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2023/9/20 3:10 AM
 */
public class QuickSortBasicKnowledge {
    public static void main(String[] args) {
        int[] arr = {2, 5, 7, 9, 3, 5, 8, 1, 3};
        partitionNum(arr);
        CommonUtils.printArray(arr);

        int[] arr2 = {2, 5, 7, 9, 3, 5, 8, 1, 3};
        partitionNumAdvanced(arr2);
        CommonUtils.printArray(arr2);
    }

    public static void partitionNum(int[] arr) {
        // 记录小于右边元素的指针位置
        int lessRightIndex = -1;

        // 当前数所在的指针位置
        int currIndex = 0;

        // 数组最右侧元素位置(区分值)
        int mostRightIndex = arr.length - 1;

        while (currIndex < arr.length) {
            // 从数组第一个元素开始挨个和后面元素进行对比，只要出现`<=区分值`的元素，
            // 即将当前指定所在位置的元素和`上一次小于区分值元素所在位置的下一个元素`进行元素交换
            if (arr[currIndex] <= arr[mostRightIndex]) {
                CommonUtils.swap(arr, ++lessRightIndex, currIndex++);
            } else {
                // 当前指针所在位置的元素比区分值大，直接将当前指针移动到下一个元素上进行下一次对比
                currIndex++;
            }
        }

    }

    public static void partitionNumAdvanced(int[] arr) {

        // 当前数所在的指针位置
        int currIndex = 0;

        // 数组最右侧元素位置(区分值)
        int mostRightIndex = arr.length - 1;

        // 记录小于于区分值区域元素的指针位置
        int lessIndex = -1;

        // 记录大于区分值区域元素的指针位置
        int greaterIndex = mostRightIndex;

        // 当两边区域的交换指针没有相遇代表还没结束对比
        while (currIndex < greaterIndex) {
            // 当前位置元素小于区分值
            if (arr[currIndex] < arr[mostRightIndex]) {
                // 小于区域的直接交换位置即可
                CommonUtils.swap(arr, ++lessIndex, currIndex++);
            } else if (arr[currIndex] > arr[mostRightIndex]) {
                // 大于区域的指针从右往左移动，记录交换元素的指针位置，当前指针不动，任然指向原来位置
                // 因为交换过来的元素不知道是大是小，还需要进一步对比
                CommonUtils.swap(arr, --greaterIndex, currIndex);
            } else {
                // 因为我们要保证中间区域是等于分区值的区域元素有序，因此不用交换数据，需要交换元素的是大于和小于区分值的区域
                currIndex++;
            }
        }
        // 由于最后一个分区值一直没有动，因此还需要将这个值与`大于分区值区域的第一个元素（就是greaterIndex最后记录的位置）`做元素交换才能保证中间区域有序
        CommonUtils.swap(arr, greaterIndex, mostRightIndex);
    }
}
