package basic.day2;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2021/12/3 3:08 上午
 * @Description
 */
public class KM {
    public static void main(String[] args) {
        int kinds = 5;
        //设置需要生成几种数，至少两种数
        int maxValue = 100;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            //-----------------------------------
            //以下代码保证了始终满足k<m条件
            int a = (int) (Math.random() * 9 + 1);
            int b = (int) (Math.random() * 9 + 1);
            int k = Math.min(a, b);
            int m = Math.max(a, b);
            if (k == m) {
                m++;
            }
            //-----------------------------------
            int numKinds = (int) (Math.random() * kinds) + 2;
            int[] arr = randomArray(numKinds, maxValue, k, m);
            int res1 = test(arr, k);
            int res2 = findKTimesNum(arr, k, m);
            if (res1 != res2) {
                System.out.println("出错了! " + res2);
            }
        }
        System.out.println("Nice!");
    }

    /**
     * 随机生成一个符合题意的数组
     *
     * @param maxValue
     * @param k
     * @param m
     * @return
     */
    public static int[] randomArray(int numKinds, int maxValue, int k, int m) {
        //首先设置一个数往数组里塞k次
        int kNum = randomNum(maxValue);
        //由k , m ，以及生成几种数决定数组长度
        // k+(numKinds-1)*m
        int[] arr = new int[k + (numKinds - 1) * m];
        int i = 0;
        for (; i < k; i++) {
            arr[i] = kNum;
        }
        //第一种数添加完成后减少一种数
        numKinds--;
        //生成的数要不能重复，所以建立hash表来记录已经存在的数
        HashSet<Integer> set = new HashSet<>();
        set.add(kNum);
        while (numKinds != 0) {
            int currNum = 0;
            do {
                //如果hash表已经存在生成的数就重新生成;
                currNum = randomNum(maxValue);
            } while (set.contains(currNum));
            set.add(currNum);
            numKinds--;
            //填充m个
            for (int j = 0; j < m; j++) {
                //接着i的位置往后填充
                arr[i++] = currNum;
            }
        }
        return upset(arr);
    }


    /**
     * 将规律的数组打乱
     *
     * @param arr
     * @return
     */
    public static int[] upset(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int j = (int) (Math.random() * arr.length);
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
        return arr;
    }

    /**
     * 随机生成一个 -num ~ num的数
     *
     * @param range
     * @return
     */
    public static int randomNum(int range) {
        return (int) (Math.random() * range + 1) - (int) (Math.random() * range + 1);
    }


    /**
     * 寻找出现k次的数
     *
     * @param arr
     * @param m
     * @return
     */
    public static int findKTimesNum(int[] arr, int k, int m) {
        //设k=3;M=5  满足M > 1，K < M
        int[] initArr = new int[32];
        for (int num : arr) {
            for (int j = 0; j < initArr.length; j++) {
                //&1的含义：j=0 时，num=0011 ，0011 & 1 = 1 initArr[0]+=1 在initArr[0]这个位置上记录一次
                //j = 1 时，num >> 1 = 0001 ，0001 & 1 = 1 initArr[1]+=1 initArr[1]记录一次
                //j = 2 时，num >> 2 = 0000 , 0000 & 1 = 0 initArr[1]+=0 相当于initArr[2]位置不记录
                //以此类推，相当于从最低位到最高位挨个记录对应位置为1的次数
                initArr[j] += (num >> j) & 1;
            }
        }

        //new一个32位int类型变量，遍历记录完各个数出现次数的initArr数组,用每个位上的次数对K和M取模，结果为1的说明这个数在第i位上为1
        //逐位往new的变量里面设置值，最终得到一个数
        int result = 0;
        for (int i = 0; i < initArr.length; i++) {
            if (initArr[i] % m != 0) {
                //在第几位上有1就将result的第几位设置成1
                // 如：第2位上有1时 1(0001)<<2 = 0100 ；0100 | result(0000) = 0100
                //    第3位上有1时 1(0001)<<3 = 1000 ；1000 | result(0100) = 1100
                // 得到最终result为1100 (此结果为举例结果，非题解)
                result |= (1 << i);
            }
        }
        return result;
    }

    /**
     * 对数器遍历计数法 百分百正确的结果
     *
     * @param arr
     * @param k
     * @return
     */
    public static int test(int[] arr, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : arr) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }
        for (int num : map.keySet()) {
            if (map.get(num) == k) {
                return num;
            }
        }
        return -1;
    }
}