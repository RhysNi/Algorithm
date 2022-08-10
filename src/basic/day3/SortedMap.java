package basic.day3;

import java.util.TreeMap;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2021/12/6 12:58 上午
 * @Description
 */
public class SortedMap {

    public static void main(String[] args) {
        TreeMap<Integer,String> treeMap = new TreeMap<>();
        //最小key
        System.out.println(treeMap.firstKey());
        //最大key
        System.out.println(treeMap.lastKey());
        //<=4 查找离4最近的key
        System.out.println(treeMap.floorKey(4));
        //>=4 查找离4最近的key
        System.out.println(treeMap.ceilingKey(4));
    }
}
