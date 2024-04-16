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

    public int countRangeSum(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // 定义前缀和数组
        long[] preSumArr = new long[nums.length];

        // 第一个元素没有累加元素，即为本身
        preSumArr[0] = nums[0];
        // 计算第二个元素开始的前缀和
        for (int i = 1; i < nums.length; i++) {
            preSumArr[i] = nums[i] + preSumArr[i - 1];
        }

        return process(preSumArr, 0, preSumArr.length - 1, lower, upper);
    }

    private static int process(long[] preSumArr, int L, int R, int lower, int upper) {
        // 求在原始arr[L...R]中，有多少个子数组累加和在【lower,upper】上
        if (L == R) {
            // 代表从0 ... L找到一个原始子数组
            return preSumArr[L] >= lower && preSumArr[L] <= upper ? 1 : 0;
        }
        int M = L + ((R - L) >> 1);
        return process(preSumArr, L, M, lower, upper) + process(preSumArr, M + 1, R, lower, upper) + merge(preSumArr, L, M, R, lower, upper);
    }

    private static int merge(long[] preSumArr, int L, int M, int R, int lower, int upper) {
        int result = 0;
        // 滑动窗口，winL不能小于 X-upper ,winR 不能大于 X-lower
        int winL = L;
        int winR = L;

        // 计算达标元素个数
        for (int i = M + 1; i <= R; i++) {
            // X-upper
            long min = preSumArr[i] - upper;
            // X-lower
            long max = preSumArr[i] - lower;
            // preSumArr[winR]如果比max值小窗口右边界就往右滑动，但是最多滑到中点位置
            while (winR <= M && preSumArr[winR] <= max) {
                winR++;
            }
            // preSumArr[winL]如果比min值小窗口左边界就往右滑动，但是最多滑到中点位置
            while (winL <= M && preSumArr[winL] < min) {
                winL++;
            }
            // 比如 winL = 0, winR = 3 , 3-0就能得到中间包含几个数，随着指针移动，就能得到整个长度总共有多少元素符合条件
            result += winR - winL;
        }

        // 合并
        // 定义临时空间排序数组元素
        long[] tmpArr = new long[R - L + 1];
        // 定义临时数组起始索引
        int idx = 0;
        // 定义左右组的指针所在位置
        int partLStartIdx = L;
        int partRStartIdx = M + 1;

        // 左右组都没发生越界
        while (partLStartIdx <= M && partRStartIdx <= R) {
            // 拷贝数组并移动指针
            tmpArr[idx++] = preSumArr[partLStartIdx] <= preSumArr[partRStartIdx] ? preSumArr[partLStartIdx++] : preSumArr[partRStartIdx++];
        }

        //右组越界，拷贝左组
        while (partLStartIdx <= M) {
            tmpArr[idx++] = preSumArr[partLStartIdx++];
        }

        // 左组越界，拷贝右组
        while (partRStartIdx <= R) {
            tmpArr[idx++] = preSumArr[partRStartIdx++];
        }

        // 将有序元素替换到原 nums 数组中对应位置上
        for (int i = 0; i < tmpArr.length; i++) {
            preSumArr[L + i] = tmpArr[i];
        }

        return result;
    }
}
