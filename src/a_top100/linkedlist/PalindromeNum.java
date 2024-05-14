package a_top100.linkedlist;

/**
 * <p>
 * <b>功能描述</b>
 * </p >
 *
 * @author : RhysNi
 * @version : v1.0
 * @date : 2024/5/8 16:21
 * @CopyRight :　<a href="https://blog.csdn.net/weixin_44977377?type=blog">倪倪N</a>
 */
public class PalindromeNum {
    public static void main(String[] args) {
        System.out.println(isPalindrome(18981));
    }

    public static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }

        int cur = 0;
        int number = x;
        while (number != 0) {
            //0+1 //
            cur = cur * 10 + number % 10;
            // 189 //
            number /= 10;
        }
        return cur == x;
    }
}
