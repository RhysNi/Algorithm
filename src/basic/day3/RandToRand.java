package basic.day3;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2021/11/24 10:48 下午
 * @Description 验证代码
 */
public class RandToRand {
    public static void main(String[] args) {
        int times = 10000000;
        int count = 0;
        //通过改变num的值来验证Math，random函数式等概率的
        double num = 0.7;
        for (int i = 0; i < times; i++) {
            if (Math.random() < num) {
                count++;
            }
        }
        System.out.println((double) count / (double) times);
        System.out.println("---------------------------");
        //[0,1) -> [0,8)
        count = 0;
        //通过改变num2的值来验证Math，random函数式等概率的
        double num2 = 5;
        for (int i = 0; i < times; i++) {
            if (Math.random() * 8 < num2) {
                count++;
            }
        }
        System.out.println((double) count / (double) times);
        System.out.println((double) 5 / (double) 8);
        System.out.println("---------------------------");
        //[0,num3) -> [0,8]
        int num3 = 9;
        int[] counts = new int[9];
        for (int i = 0; i < times; i++) {
            int res = (int) (Math.random() * num3);
            counts[res]++;
        }
        for (int i = 0; i < num3; i++) {
            System.out.println(i + "出现了" + counts[i] + "次");
        }
        System.out.println("---------------------------");

    }
}
