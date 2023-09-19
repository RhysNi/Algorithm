package basic.mergesort;

/**
 * <p>
 * <b>递归写法</b>
 * </p >
 *
 * @author : RhysNi
 * @version : v1.0
 * @date : 2023/9/18 20:10
 * @CopyRight :　<a href="https://blog.csdn.net/weixin_44977377?type=blog">倪倪N</a>
 */
public class MergeSortByRecursion {
    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // 对数从第一个元素到最后一个元素进行归并排序
        process(arr, 0, arr.length - 1);
    }

    private static void process(int[] arr, int startIndex, int endIndex) {
        // 开始到结束只存在一个数，默认有序，直接返回
        if (startIndex == endIndex) {
            return;
        }

        // 开始到结束存在多个元素的取中点，先对左边递归排序，再对右边递归排序，最终进行合并
        // 如果 数组长度为15，起始索引从0开始。对应最大索引为arr[14], 14>>1 = 1110 >> 1 = 0111 = 7
        // 如果 数组长度为16，起始索引从0开始。对应最大索引为arr[15], 15>>1 = 1111 >> 1 = 0111 = 7
        int mid = startIndex + ((endIndex - startIndex) >> 1);
        // 作用相同，但上面写法更安全，因为如果数组长度非常大，startIndex + endIndex 可能越界
        // int mid = (startIndex + endIndex) / 2;

        // 递归开始直到完全排序完成
        process(arr, startIndex, mid);
        process(arr, mid + 1, endIndex);
        MergeSortMain.merge(arr, startIndex, mid, endIndex);
    }
}
