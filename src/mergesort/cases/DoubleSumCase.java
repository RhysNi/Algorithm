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
 * @date : 2023/11/27 19:25
 * @CopyRight :　<a href="https://blog.csdn.net/weixin_44977377?type=blog">倪倪N</a>
 */
public class DoubleSumCase {
    public static void main(String[] args) {
        int testTime = 500_000;
        int maxSize = 100;
        int maxValue = 100;

        System.out.println("--- DoubleSumCase test start ---");


        for (int i = 0; i < testTime; i++) {
            int[] arr1 = new int[]{5, 8, 3, 7, 2, 4};

//            int[] arr1 = CommonUtils.buildRandomArray(maxSize, maxValue);
            int[] arr2 = CommonUtils.copyArray(arr1);

            int calcu = calcu(arr1);

            int calcuByMergeSort = calcuByMergeSort(arr2);

            if (calcu != calcuByMergeSort) {
                System.out.println("DoubleSumCase test error");
                printArray(arr1);
                printArray(arr2);
                return;
            }
        }
        System.out.println("--- DoubleSumCase test success ---");
    }

    private static int calcu(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        int result = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                result += (arr[j] * 2) < arr[i] ? 1 : 0;
            }
        }
        return result;
    }

    private static int calcuByMergeSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    private static int process(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }

        // 取中位数
        int M = L + ((R - L) >> 1);

        // 三阶段总数和
        return process(arr, L, M) + process(arr, M + 1, R) + merge(arr, L, M, R);
    }

    private static int merge(int[] arr, int L, int M, int R) {
        int result = 0;

        // 定义一个指针卡住右组最大的元素
        int IDX = M + 1;

        // 统计每个元素右边值乘以2仍小于当前元素值的总个数
        for (int i = L; i <= M; i++) {
            // 控制IDX指针的滑动 && 左组当前元素要比IDX所在位置前一个元素大  arr[IDX] << 1 == arr[IDX]*2
            while (IDX <= R && arr[i] > arr[IDX] << 1) {
                IDX++;
            }
            // 统计 [M,IDX-1)区间上的元素个数
            // 例如 [1,2,3,4,1,2,3,5] 拆分为两组 [1,2,3,4] [1,2,3,5]
            // 当arr[0]指向[1]得时候，IDX指向 M+1 =[1],1 > 1*2 ? 不满左组比右组大
            // IDX指针不能滑动到下一个元素，还在M+1位置上，因为最少为0 ，所以由IDX = M+1得到 0 = M+1-M-1
            result += IDX - M - 1;
        }

        // 定义左右组的指针所在位置
        int LMergeStartIdx = L;
        int RMergeStartIdx = M + 1;

        // 定义临时空间排序数组元素
        int[] tmpArr = new int[R - L + 1];
        // 定义临时数组起始索引
        int tmpArrIdx = 0;

        // 左右组都没发生越界
        while (LMergeStartIdx <= M && RMergeStartIdx <= R) {
            // 拷贝数组并移动指针
            tmpArr[tmpArrIdx++] = arr[LMergeStartIdx] < arr[RMergeStartIdx] ? arr[LMergeStartIdx++] : arr[RMergeStartIdx++];
        }

        // 右组越界，拷贝左组
        while (LMergeStartIdx <= M) {
            tmpArr[tmpArrIdx++] = arr[LMergeStartIdx++];
        }

        // 左组越界，拷贝右组
        while (RMergeStartIdx <= R) {
            tmpArr[tmpArrIdx++] = arr[RMergeStartIdx++];
        }

        // 将有序元素替换到原 arr 数组中对应位置上
        for (int i = 0; i < tmpArr.length; i++) {
            arr[L + i] = tmpArr[i];
        }

        return result;
    }
}
