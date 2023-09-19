package basic.quicksort;

import basic.CommonUtils;

import java.util.Stack;

import static basic.CommonUtils.printArray;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2023/9/19 9:59 PM
 */
public class QuickSortByPartition {
    public static void main(String[] args) {

        int testTime = 500_000;
        int maxSize = 100;
        int maxValue = 100;

        System.out.println("--- QuickSort test start ---");
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = CommonUtils.buildRandomArray(maxSize, maxValue);
            int[] arr2 = CommonUtils.copyArray(arr1);

            // 递归法
            quickSort(arr1);
            // 栈操作
            quickSortNonRecursion(arr2);

            if (!CommonUtils.isEqual(arr1, arr2)) {
                System.out.println("QuickSort test error");
                printArray(arr1);
                printArray(arr2);
            }
        }
        System.out.println("--- QuickSort test success ---");

    }

    public static void quickSortNonRecursion(int[] arr) {
        if (arr != null && arr.length >= 2) {
            // 使用栈结构保存任务
            Stack<Job> jobs = new Stack<>();

            // 往栈里压任务
            jobs.push(Job.builder()
                    .leftIndex(0)
                    .rightIndex(arr.length - 1)
                    .build());

            while (!jobs.isEmpty()) {
                // 只要栈中存在任务，则进行分区然后对左右区间进行判断，符合条件则将对应的区间任务压入栈中
                Job job = jobs.pop();
                int[] partition = partition(arr, job.getLeftIndex(), job.getRightIndex());

                // 返回的左边界索引其实就是中间相等元素的左边界，右边界索引则是中间相等元素的右边界
                // 左边界索引 > 当前任务的最左边界索引代表存在`小于分区值`的左区域
                if (partition[0] > job.getLeftIndex()) {
                    jobs.push(Job.builder()
                            .leftIndex(job.getLeftIndex())
                            .rightIndex(partition[0] - 1)
                            .build());
                }
                // 右边界索引 < 当前任务的最右边界索引代表存在`大于分区值`的右区域
                if (partition[1] < job.getRightIndex()) {
                    jobs.push(Job.builder()
                            .leftIndex(partition[1] + 1)
                            .rightIndex(job.getRightIndex())
                            .build());
                }
            }
        }
    }

    public static void quickSort(int[] arr) {
        if (arr != null && arr.length >= 2) {
            // 递归调用分区交换元素
            process(arr, 0, arr.length - 1);
        }
    }


    private static void process(int[] arr, int leftIndex, int rightIndex) {
        if (leftIndex < rightIndex) {
            // 获取每个阶段数组的分区范围
            int[] ints = partition(arr, leftIndex, rightIndex);
            // 递归对左边区间内数组元素进行排序
            process(arr, leftIndex, ints[0] - 1);
            // 递归对右边边区间内数组元素进行排序
            process(arr, ints[1] + 1, rightIndex);
        }
    }

    public static int[] partition(int[] arr, int leftIndex, int rightIndex) {
        // 由于递归调用，因此参数列表中的leftIndex和rightIndex可能每次都是不一样的
        // 当前数所在的指针位置,从分区数组的最左侧开始
        int currIndex = leftIndex;

        // 数组最右侧元素位置(区分值)，不管是对哪个分区进行排序，始终认定有效区间内的最右边元素为区分值
        int mostRightIndex = rightIndex;

        // 记录小于于区分值区域元素的指针位置
        int lessIndex = leftIndex - 1;

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
        return new int[]{++lessIndex, greaterIndex};
    }
}
