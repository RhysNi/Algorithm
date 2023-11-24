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
            int[] arr1 = CommonUtils.buildRandomArray(maxSize, maxValue);
            int[] arr2 = CommonUtils.copyArray(arr1);

            if (calcu(arr1) != calcuByMergeSort(arr2)) {
                System.out.println("ReverseOrderCase test error");
                printArray(arr1);
                printArray(arr2);
            }
        }
        System.out.println("--- ReverseOrderCase test success ---");

    }


    private static int calcu(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < i; j++) {
            }
        }
        return 0;
    }

    private static int calcuByMergeSort(int[] arr) {
        return 0;
    }
}
