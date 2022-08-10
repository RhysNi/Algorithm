package basic.day1;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2021/11/23 8:19 下午
 * @Description
 */
public class PrintBinary {
    public static void main(String[] args) {
        //定义两个整数
        int num1 = Integer.MIN_VALUE;
        int num2 = Integer.MAX_VALUE;
        print(num1);
        print(num2);
        System.out.println("--------------------------------");
        //左移1位
        print(num1 << 1);
        //左移2位
        print(num2 << 2);
        //或运算
        System.out.println("或运算--------------------------------");
        print(num1 | num2);
        //与运算
        System.out.println("与运算--------------------------------");
        print(num1 & num2);
        //异或运算
        print(num1 ^ num2);
        System.out.println("--------------------------------");
        //带符号右移>> 最左边用符号位补
        print(num1 >> 1);
        //不带符号右移 最左边用0补
        print(num1 >>> 1);
        System.out.println("--------------------------------");
        //取反~   ~b+1=-b
        print(-num2);
        print(~num2+1);
    }

    //二进制转换
    public static void print(int num){
        for (int i = 31; i >=0 ; i--) {
            System.out.print((num & (1<<i)) == 0?"0":"1");
        }
        System.out.println();
    }
}
