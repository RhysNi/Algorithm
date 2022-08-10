package basic.day2;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2021/12/2 10:10 下午
 * @Description
 */
public class EvenTimesOddTimes {
    public static void main(String[] args) {
        findOneKindOddNum();
        findTwoKindsOddNum();
    }

    //一个数组中有一种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这种数？
    //利用性质三：
    // 有一批数做异或运算，同时满足交换律和结合律，得到的结果是同一个数
    // 偶数个1结果一定是0
    // 奇数个1结果一定是1
    public static void findOneKindOddNum() {
        int[] arr = {7, 4, 5, 7, 6, 7, 4, 5, 6};
        int exclusiveOr = 0;
        for (int i = 0; i < arr.length; i++) {
            exclusiveOr ^= arr[i];
        }
        // 44  55  66  777
        // 4^4=0   5^5=0   6^6=0   7^7=0  最终三个7消掉两个剩一个7直接返回
        System.out.println(exclusiveOr);
    }

    //一个数组中有两种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这两种数
    public static void findTwoKindsOddNum() {
        int[] arr = {7, 4, 5, 7, 6, 7, 5, 6};
        int exclusiveOr = 0;
        for (int i = 0; i < arr.length; i++) {
            exclusiveOr ^= arr[i];
        }
        //利用 -a = ~a+1 提取最右边的1
        int oneRight = exclusiveOr & (-exclusiveOr);
        int exclusiveOr2 = 0;
        for (int i = 0; i < arr.length; i++) {
            //4:0100   5:0101  6:0110  7:0111
            //只将最右边为1的数参与异或运算，与运算性质是只有两个位上都是1结果才为1，所以两个数与运算结果!=0的时候必然是两种数中的其中一个
            if ((arr[i] & oneRight) != 0) {
                exclusiveOr2 ^= arr[i];
            }
        }
        //既然已经得到两种数中的其中一种，那么另外一种就直接用 exclusiveOr ^ exclusiveOr2 即可得出
        System.out.println(exclusiveOr2 + " , " + (exclusiveOr ^ exclusiveOr2));
    }
}
