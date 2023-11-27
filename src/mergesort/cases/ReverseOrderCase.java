package mergesort.cases;

import basic.CommonUtils;

import static basic.CommonUtils.printArray;

/**
 * <p>
 * <b>
 * 在一数组中，若将两元素看为一组，且这组数据左边元素比右边元素大，称作逆序对，如有数组`[5，8，3，7，2，4]`：
 * <p>
 * - 以元素`[5]`为视角，则`5,3`、`[5,2]`、`[5,4]`构成逆序对
 * - 以元素`[8]`为视角，则`8,3`、`[8,7]`、`[8,2]`、`[8,4]`构成逆序对
 * - 以元素`[3]`为视角，则`3,2`构成逆序对
 * - 以元素`[7]`为视角，则`7,2`、`[7,4]`构成逆序对
 * <p>
 * 综上所述：我们只需要关注每个元素右边有多少元素比当前元素小即可，只需要注意排序的规则
 * <p>
 * - 如果是降序排，则从左往右拷贝
 * <p>
 * - 如果是升序排，则从右往左拷贝
 * - 求构成逆序对相关元素的总和
 * </b>
 * </p >
 *
 * @author : RhysNi
 * @version : v1.0
 * @date : 2023/11/24 16:57
 * @CopyRight :　<a href="https://blog.csdn.net/weixin_44977377?type=blog">倪倪N</a>
 */
public class ReverseOrderCase {
    public static void main(String[] args) {
        int testTime = 500_000;
        int maxSize = 100;
        int maxValue = 100;

        System.out.println("--- ReverseOrderCase test start ---");


        for (int i = 0; i < testTime; i++) {
//            int[] arr1 = new int[]{8, 7, 3, 4, 3, 7, 2};

            int[] arr1 = CommonUtils.buildRandomArray(maxSize, maxValue);
            int[] arr2 = CommonUtils.copyArray(arr1);

            int calcu = calcu(arr1);

            int calcuByMergeSort = calcuByMergeSort(arr2);

            if (calcu != calcuByMergeSort) {
                System.out.println("ReverseOrderCase test error");
                printArray(arr1);
                printArray(arr2);
                return;
            }
        }
        System.out.println("--- ReverseOrderCase test success ---");

    }


    private static int calcu(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        int count = 0;

        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length ; j++) {
                // 对小的元素进行累加和
                count += arr[j] < arr[i] ? 1 : 0;
            }
        }
        return count;
    }

    private static int calcuByMergeSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        int Left = 0;
        int Right = arr.length - 1;
        return process(arr, Left, Right);
    }

    private static int process(int[] arr, int startIndex, int endIndex) {
        // 开始到结束只存在一个数，不存在小和问题，不做累加操作
        if (startIndex == endIndex) {
            return 0;
        }

        // 取中位数
        int mid = startIndex + ((endIndex - startIndex) >> 1);

        // 由于三个阶段都有可能产生逆序对，因此需要将三个阶段的小和进行累加
        return process(arr, startIndex, mid) + process(arr, mid + 1, endIndex) + merge(arr, startIndex, mid, endIndex);
    }

    private static int merge(int[] arr, int startIndex, int mid, int endIndex) {

        // 定义缓存数组
        int[] tmpArr = new int[endIndex - startIndex + 1];
        // 定义缓存数组启始索引 从右往左存
        int tmpArrIdx = tmpArr.length - 1;

        // 定义左右组的指针所在位置，左组从中位往左移，右组从最大长度往中位移
        int LMergeStartIdx = mid;
        int RMergeStartIdx = endIndex;

        // 定义逆序对总数初始值
        int reverseOrderCount = 0;

        // 左右组都没发生越界
        while (LMergeStartIdx >= startIndex && RMergeStartIdx > mid) {
            // 计算从右组当前指针位置到右组起始索引有多少个元素即为存在多少组逆序对
            reverseOrderCount += arr[LMergeStartIdx] > arr[RMergeStartIdx] ? (RMergeStartIdx - mid) : 0;
            // 拷贝数组并移动指针，我们这里是升序排，所以从右往左拷贝
            tmpArr[tmpArrIdx--] = arr[LMergeStartIdx] > arr[RMergeStartIdx] ? arr[LMergeStartIdx--] : arr[RMergeStartIdx--];
        }

        // 右组越界，拷贝左组
        while (LMergeStartIdx >= startIndex) {
            tmpArr[tmpArrIdx--] = arr[LMergeStartIdx--];
        }

        // 左组越界，拷贝右组
        while (RMergeStartIdx > mid) {
            tmpArr[tmpArrIdx--] = arr[RMergeStartIdx--];
        }

        // 将有序元素替换到原 arr 数组中对应位置上
        for (int i = 0; i < tmpArr.length; i++) {
            arr[startIndex + i] = tmpArr[i];
        }

        return reverseOrderCount;
    }
}
