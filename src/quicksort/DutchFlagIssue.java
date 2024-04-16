package quicksort;

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

import static basic.CommonUtils.swap;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2024/4/17 3:43 AM
 */
public class DutchFlagIssue {

    public void quicksort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    private void process(int[] arr, int L, int R) {
        if (L < R) {
            //通过随机数将一个随机位置的元素和R位置的划分值交换，防止分区数据严重倾斜导致复杂度偏高
            swap(arr, (int) (L + Math.random() * (R - L + 1)), R);
            int[] dutchFlagArea = dutchFlag(arr, L, R);
            //左区处理,L...【等于区域左边界】
            process(arr, L, dutchFlagArea[0]);
            //右区处理，【等于区域右边界】...最后一位元素
            process(arr, dutchFlagArea[1] + 1, R);
        }
    }


    public static int[] dutchFlag(int[] arr, int L, int R) {
        if (L > R) {
            return new int[]{-1, -1};
        }

        if (L == R) {
            return new int[]{L, R};
        }
        //定义【小于区域】右边界
        int less = L - 1;
        //定于【大于区域】左边界，因为要把数组最后一个数摘出来做划分值，所以直接以最后一个元素位置作为边界
        int more = R;
        // 定义当前指针位置
        int currIdx = L;
        // 当前指针位置不能撞上【大于区域】左边界
        while (currIdx < more) {
            if (arr[currIdx] == arr[R]) {
                // 当前数=目标数，当前数的指针跳到下一个数上
                currIdx++;
            } else if (arr[currIdx] < arr[R]) {
                //当前数<目标数，当前数和【小于区域】的下一个数交换，【小于区域】向右覆盖一个位置，当前数指针跳下一个位置
                swap(arr, currIdx++, ++less);
            } else {
                //当前数>目标数，当前数和【大于区域】指针的前一个数交换，【大于区域】向左覆盖一个位置，当前数指针停在原地
                swap(arr, currIdx, --more);
            }
        }
        // 将最后一个元素和【大于区域】第一个元素交换位置
        // 此时 <R =R >R 就已经完成了
        swap(arr, more, R);

        // 返回=R的起始位置和结束位置
        return new int[]{less + 1, more};
    }
}
