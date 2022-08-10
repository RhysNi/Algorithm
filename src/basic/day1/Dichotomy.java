package basic.day1;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2021/11/25 1:00 上午
 * @Description
 */
public class Dichotomy {
    public static void main(String[] args) {

    }

    public static boolean find(int[] arr, int num) {
        if (arr == null || arr.length == 0) {
            return false;
        }
        int L = 0;
        int R = arr.length - 1;
        while (L <= R) {
            int mid = (L + R) / 2;
            if (arr[mid] == num) {
                return true;
            } else if (arr[mid] < num) {
                L = mid + 1;
            } else {
                R = mid - 1;
            }
        }
        return false;
    }
}
