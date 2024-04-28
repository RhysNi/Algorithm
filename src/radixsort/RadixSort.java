package radixsort;

import java.util.Arrays;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2024/4/29 1:48 AM
 */
public class RadixSort {
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100000;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = copyArray(arr1);
            radixSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");

        int[] arr = generateRandomArray(maxSize, maxValue);
        printArray(arr);
        radixSort(arr);
        printArray(arr);
    }

public static void radixSort(int[] arr) {
    if (arr == null || arr.length < 2) {
        return;
    }
    radixSort(arr, 0, arr.length - 1, getMaxBit(arr));
}

private static int getMaxBit(int[] arr) {
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < arr.length; i++) {
        max = Math.max(max, arr[i]);
    }

    int result = 0;
    while (max != 0) {
        result++;
        max /= 10;
    }
    return result;
}

public static void radixSort(int[] arr, int L, int R, int maxBit) {
    // 以十为基数
    final int radix = 10;
    int i = 0, j = 0;
    // 初始化辅助数组,长度以边界中的元素个数来确定
    int[] help = new int[R - L + 1];
    for (int b = 1; b <= maxBit; b++) {
        // 0-9 共计10个空间
        // count[0] 当前b位是0的数字共计多少个
        // count[1] 当前b位是0和1的数字共计多少个
        // ......
        // count[i] 当前b位是0~i的数字共计多少个
        int[] count = new int[radix];
        // 统计所有数个位数从0-9中间数量
        for (i = L; i <= R; i++) {
            j = getBit(arr[i], b);
            // 对应位的数量进行统计
            count[j]++;
        }

        // 用累加和代替原数组中元素
        for (i = 1; i < radix; i++) {
            count[i] = count[i] + count[i - 1];
        }

        // 从右往左往遍历
        for (i = R; i >= L; i--) {
            j = getBit(arr[i], b);
            // arr:[021,010,111,022,011,012]
            // count[1,3,2,0,0,0,0,0,0,0] 例如：个位数为0的有1个，个位数为1的有3个，个位数为2的有2个
            // count累加和[1,4,6,6,6,6,6,6,6,6] 例如：个位数<=0 的有1个 ，个位数<=1 的有4个，个位数<=2 的有6个
            // 012取个位数2，小于等于2的数有6个，因此在help中的所占区域应该是0~5，由于从右往左遍历，012在arr中处于数组最右边，固然放在help的最右边：help[5]，同时count[2]位置进行递减，剩余5个
            // 011取个位数1，小于等于1的数有4个，因此在help中的所占区域应该是0~3，放在help[3]位置，count[1]位置进行递减，剩余3个
            // 022取个位数2，小于等于2的数有5个，因此在help中的所占区域应该是0~4，放在help[4]位置，count[2]位置进行递减，剩余4个
            // 111取个位数1，小于等于1的数有3个，因此在help中的所占区域应该是0~2，放在help[2]位置，count[1]位置进行递减，剩余2个
            // 010取个位数0，小于等于0的数有1个，因此在help中的所占区域应该是0~0，放在help[0]位置，count[0]位置进行递减，剩余0个
            // 021取个位数1，小于等于1的数有2个，因此在help中的所占区域应该是0~1，放在help[1]位置，count[1]位置进行递减，剩余1个
            // 实际当上面0统计消耗完应该不剩了，但是这里剩一个，原因就是我们根本不是靠这个每个位对应索引上的计数来统计具体数量的，我们能确定这个数应该放在help[]的具体位置就OK了
            help[count[j] - 1] = arr[i];
            // 对应位的数量进行递减
            count[j]--;
        }

        for (i = L, j = 0; i <= R; i++, j++) {
            arr[i] = help[j];
        }
    }
}

private static int getBit(int i, int b) {
    // 从整数 i 中提取了位于位置 b 的数字。位置是从右向左计数的，从 0 开始。如果 i 是 12345，b 是 3，则表达式的结果将是 3，因为第 3 位的数字是 3
    // Math.pow(10, b - 1)：计算 10 的 b - 1 次幂。
    //  - 创建一个以 10 为底的数，小数点后有 b - 1 个零 ,如果 b 是 3，则 Math.pow(10, 2) 将返回 100
    // 将 i 向右移动 b - 1 位，模拟了整数除法的效果 - 如果 i 是 12345，b 是 3
    //  -  i/(int)Math.pow(10, 2)为：123（因为 12345 / 100 = 123.45，取整数部分为 123）
    // % 10：取模运算符 % 返回两个数相除的余数。返回上一步中计算结果的个位数。123 % 10为：3
    return ((i / ((int) Math.pow(10, b - 1))) % 10);
}


    // for test
    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }

    // for test
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

    // for test
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

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
