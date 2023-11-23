package mergesort.cases;

import basic.CommonUtils;

import static basic.CommonUtils.printArray;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2023/11/24 12:39 AM
 * <p>
 * 同样一个数组，例如[3,5,6,3,7,8,9,2]，实现思路如下：
 * <p>
 * - 将数组拆分为左右两组，一直拆分到不可拆分为止
 * <p>
 * 将`3,5,6,3,7,8,9,2`拆分为两组，左组`3,5,6,3`,右组`7,8,9,2`
 * <p>
 * - 再将`3,5,6,3`拆分为两组，左组`3,5`,右组`6,3`
 * <p>
 * - 再将`3,5`拆分为两组，左组`3`,右组`5`
 * <p>
 * - 以此类推`7,8,9,2`同上
 * <p>
 * 然后两组进行对比
 * <p>
 * - 左组元素比右组元素小则将左组元素拷贝到新的数组，这时会产生小和，反之右组比左组小，固然先拷贝右组元素到新的数组中，但这种情况是不存在小和问题的，我们要求的是每一个元素左边比自身小的元素累加和。
 * <p>
 * - 以`[3,5,6,3]`为例：
 * - 左组`3`,右组`5`，对比得到 `3<5`，这时会将`3`拷贝到新的数组中，然后左组指针移动到下一个元素发现元素越界，再拷贝`5`,同时也产生了`小和问题`
 * - 根据数组下标计算可得`右组有一个元素比3大`，固小和产生一个`1`，本轮`sum`=1
 * <p>
 * - 以`[6,3]`为例：
 * - 左组6，右组3，对比得到`6>3`,这是会将右组`3`拷贝到新的数组中，然后移动右组指针到下一个元素发现元素越界，再将拷贝`6`，此时没有小和产生
 * - 以`[3,5]|[3,6]`为例：
 * - 左组`3`与右组`3`对比，两元素相等，先拷贝右边，右组指针指向下一个元素，再拷贝左边，不存在小和
 * - 左组`3`与右组`6`对比，`3<6`,先拷贝`3`，移动左组指针到下一个元素`5`上，产生小和 一个`3`,本轮`sum=3`
 * - 左组`5`与右组`6`对比，`5<6`,先拷贝`5`，指针移动到下一个元素，发生越界，再拷贝右组`6`,这里存在小和，一个`5`,本轮`sum=5`
 * - 最终得到`[3,3,5,6]`
 * - `右组`[7,8,9,2]`以此类推得到`[2,7,8,9]
 * - 最后再将[3,3,5,6]和`[2,7,8,9]`进行对比得到最终有序数组`[2,3,3,5,6,7,8,9]`
 * - 再以上过程中也肯定出现了对应的小和，最终结果见代码实现
 */
public class SmallSumCase {
    public static void main(String[] args) {
        int testTime = 500_000;
        int maxSize = 100;
        int maxValue = 100;

        System.out.println("--- SmallSumCase test start ---");


        for (int i = 0; i < testTime; i++) {
            int[] arr1 = CommonUtils.buildRandomArray(maxSize, maxValue);
            int[] arr2 = CommonUtils.copyArray(arr1);

            if (calcu(arr1) != calcuByMergeSort(arr2)) {
                System.out.println("SmallSumCase test error");
                printArray(arr1);
                printArray(arr2);
            }
        }
        System.out.println("--- SmallSumCase test success ---");
    }


    private static int calcu(int[] sumArr) {
        // 不存在小和
        if (sumArr == null || sumArr.length < 2) {
            return 0;
        }

        // 初始化小和
        int smallSum = 0;

        // 每个元素挨个和右边元素进行对比
        for (int i = 0; i < sumArr.length; i++) {
            for (int j = 0; j < i; j++) {
                // 对小的元素进行累加和
                smallSum += sumArr[j] < sumArr[i] ? sumArr[j] : 0;
            }
        }
        return smallSum;
    }

    private static int calcuByMergeSort(int[] arr2) {
        int Left = 0;
        int Right = arr2.length - 1;

        return process(arr2, Left, Right);
    }


    private static int process(int[] sumArr, int startIndex, int endIndex) {
        // 开始到结束只存在一个数，不存在小和问题，不做累加操作
        if (startIndex == endIndex) {
            return 0;
        }

        // 取中位数
        int mid = startIndex + ((endIndex - startIndex) >> 1);

        // 左组区间划分
        int processLeftResult = process(sumArr, startIndex, mid);

        // 右组区间划分
        int processRightResult = process(sumArr, mid + 1, endIndex);

        // 归并排序并返回小和
        int mergeResult = merge(sumArr, startIndex, mid, endIndex);

        // 由于三个阶段都有可能产生小和，因此需要将三个阶段的小和进行累加
        return processLeftResult + processRightResult + mergeResult;
    }

    private static int merge(int[] sumArr, int startIndex, int mid, int endIndex) {
        // 定义缓存数组
        int[] tmpArr = new int[endIndex - startIndex + 1];
        // 定义缓存数组启始索引
        int tmpArrIdx = 0;

        // 定义左右组的指针所在位置
        int LMergeStartIdx = startIndex;
        int RMergeStartIdx = mid + 1;

        // 定义小和初始值
        int smallResult = 0;

        // 左右组都没发生越界
        while (LMergeStartIdx <= mid && RMergeStartIdx <= endIndex) {
            // 因为走到这里，无论什么样的数组 左右两边都已经经过层层排序都是是有序的了
            // 所以我们只需要考虑右组有多少个元素是比左组当前的元素大，就会产生多少个对应左组元素的小和
            // 那么我们只需要知道右组最后一个元素到当前右组指针位置这中间存在多少个元素即可
            // 产生小和就累加小和，没有小和产生则累加0即可
            smallResult += sumArr[LMergeStartIdx] < sumArr[RMergeStartIdx] ? (endIndex - RMergeStartIdx + 1) * sumArr[LMergeStartIdx] : 0;

            // 拷贝数组并移动指针
            tmpArr[tmpArrIdx++] = sumArr[LMergeStartIdx] < sumArr[RMergeStartIdx] ? sumArr[LMergeStartIdx++] : sumArr[RMergeStartIdx++];
        }

        // 右组越界，拷贝左组
        while (LMergeStartIdx <= mid) {
            tmpArr[tmpArrIdx++] = sumArr[LMergeStartIdx++];
        }

        // 左组越界，拷贝右组
        while (RMergeStartIdx <= endIndex) {
            tmpArr[tmpArrIdx++] = sumArr[RMergeStartIdx++];
        }

        // 将有序元素替换到原 sumArr 数组中对应位置上
        for (int i = 0; i < tmpArr.length; i++) {
            sumArr[startIndex + i] = tmpArr[i];
        }

        return smallResult;
    }
}
