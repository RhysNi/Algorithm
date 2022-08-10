package basic.day2;

import basic.CommonUtils;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2021/12/3 12:47 上午
 * @Description 两值互换
 */
public class TwoValueExchange {
    public static void main(String[] args) {
        twoValueExchange();
        twoValueExchangeForArray();
    }

    /**
     * 不用额外变量交换两个数的值
     */
    public static void twoValueExchange() {
        int a = 10;
        int b = 4;
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.println("a=" + a);
        System.out.println("b=" + b);
    }

    /**
     * 不用额外变量交换数组中两个数的值
     */
    public static void twoValueExchangeForArray() {
        int[] arr = CommonUtils.ARR;
        CommonUtils.printArray(arr);
        //交换1、3位置的值
        arr[1] = arr[1] ^ arr[3];
        arr[3] = arr[1] ^ arr[3];
        arr[1] = arr[1] ^ arr[3];
        CommonUtils.printArray(arr);
    }
}
