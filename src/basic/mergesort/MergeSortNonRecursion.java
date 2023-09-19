package basic.mergesort;

/**
 * <p>
 * <b>非递归写法-计算步长</b>
 * </p >
 *
 * @author : RhysNi
 * @version : v1.0
 * @date : 2023/9/18 20:11
 * @CopyRight :　<a href="https://blog.csdn.net/weixin_44977377?type=blog">倪倪N</a>
 */
public class MergeSortNonRecursion {
    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        // 定义一个步长，根据步长的变化控制每次参与排序的元素数量
        // 假设又数组[8，1，7，6，2]
        // step = 1 --- 则每次只有1个元素为一组参与排序，即 [8] vs [1] -- [7]vs [6] -- [2] -- 得[1,8,6,7,2]
        // step = 2 --- 则每次有2个元素为一组参与排序，即 [1,8] vs [6,7] -- [9] -- 得[1,6,7,8,2]
        // step = 4 --- 则每次有4个元素为一组参与排序，如果有凑不足4个元素为一组的就直接参与merge, 即 [1,6,7,8] vs [2] -- 得[1,2,6,7,8]
        int step = 1;

        // 最大边界为数组长度
        int endIndex = arr.length;

        // 边界判断 :
        // 有这么一种情况，假设数组最大长度是15
        // 当 step = 8 时候，左右两组得元素索引都还在边界范围内
        // 当 step = 16 时。已经比原数组长度大了。当获取arr[15]的时候就越界了，我们最大数组长度是15，对应的最大索引即为arr[14]
        // 还有一种情况 ，假设数组最大长度是16
        // 当 step = 8 时候，左右两组得元素索引都还在边界范围内
        // 当 step = 16 时, 步长和数组最大长度相等时则不用进行排序了，说明该排序的都已经排好了，右组没有多余的元素需要进行排序了。
        // 所以如果步长*2 >= 边界最大长度直接结束了
        while (step < endIndex) {
            // 左组起始索引从0开始
            int startIndexL = 0;
            while (startIndexL < endIndex) {
                // 初始化中位索引
                int midIndex = 0;
                // 确定左组边界
                if (endIndex - startIndexL >= step) {
                    // 当边界索引-左组起始索引 >= 步长时:
                    // 代表左组能够凑到步长数对应的元素个数或者刚好和步长相等,则可定中位索引为：左组起始索引+步长数-1
                    // 假设数组长度为15：endIndex=15 -- startIndexL=0 -- step=8 -- 得endIndex-startIndexL=15 -- 因此midIndex=0+8-1=arr[7]
                    // 假设数组长度为16：endIndex=16 -- startIndexL=0 -- step=16 -- 得endIndex-startIndexL=16 -- 因此midIndex=0+16-1=arr[15]
                    midIndex = startIndexL + step - 1;
                } else {
                    // 假设数组长度为15：endIndex=15 -- startIndexL=0 -- step=16 -- 得endIndex-startIndexL=15 -- 因此midIndex=0+16-1=arr[15]
                    // 但是我们数组最大长度为15，对应的最大索引应该是arr[14]，只有15个数，凑不够16个数为一组
                    // 如果使用arr[15]则会发生越界，因此我们定义midIndex为最大索引即可，有多少算多少
                    midIndex = endIndex - 1;
                }

                // 如果左组长度刚好为整个数组的长度，说明该排序的都排好了，没有右组则无需排序
                if (midIndex == endIndex - 1) {
                    break;
                }

                // 有右组的情况下先确定右组边界
                // 初始化右组边界
                int endIndexR = 0;
                // 因为刚才我们确定了midIndex的位置：在 startIndexL + step - 1位置上
                // - 假设数组长度为16 -- step=8：midIndex=在startIndexL + step - 1位置上=arr[7] -- endIndex - 1 - midIndex = 15-7=8
                //   因此刚好凑够一组与步长相等个数的元素，则右组边界定为中位索引+步长的值
                if (endIndex - 1 - midIndex >= step) {
                    endIndexR = midIndex + step;
                } else {
                    // - 假设数组长度为15 -- step=8：midIndex在startIndexL + step - 1位置上=arr[7] -- endIndex-1-midIndex = 14-7=7
                    //   因此凑不够8个元素的右组，直接以最大索引作为右组最大边界
                    endIndexR = endIndex - 1;
                }

                // 提供排序数组，左组开始索引，中位索引，和右组边界索引进行归并排序
                MergeSortMain.merge(arr, startIndexL, midIndex, endIndexR);

                // 如果上一组的右组边界刚好等于数组最大索引则无需继续排序
                // 如果仍然存在其他元素可进行排序，则需要将指针移动到下一组的左组起始索引位置,即endIndexR + 1的位置
                if (endIndexR == endIndex - 1) {
                    break;
                } else {
                    startIndexL = endIndexR + 1;
                }
            }
            // 控制步长为 2^N并且当步长 > 数组一半长度时不用继续增加步长
            // 假设数组长度15 ， endIndex / 2 = 7
            // 因此当 step=1 时候 下一次排序则需要将步长调整为 step=2...直到step=8时，此时步长超过中位索引长度，意味着本次排序完成将没有多余元素参与排序
            // 如果将步长调整为16 即step=16时，已经超过数组最大长度
            // 同理：步长==中位索引长度也是一样不需要继续调整步长
            if (step > endIndex / 2) {
                break;
            } else {
                step *= 2;
            }
        }
    }
}