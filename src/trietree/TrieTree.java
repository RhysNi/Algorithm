package trietree;

import java.util.HashMap;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2024/4/25 12:31 AM
 */
public class TrieTree {
    public static void main(String[] args) {
        int arrLen = 100;
        int strLen = 20;
        int testTimes = 100000;
        for (int i = 0; i < testTimes; i++) {
            String[] arr = generateRandomStringArray(arrLen, strLen);
            Trie trie1 = new Trie();
            Trie2 trie2 = new Trie2();
            Right right = new Right();
            for (int j = 0; j < arr.length; j++) {
                double decide = Math.random();
                if (decide < 0.25) {
                    trie1.insert(arr[j]);
                    trie2.insert(arr[j]);
                    right.insert(arr[j]);
                } else if (decide < 0.5) {
                    trie1.delete(arr[j]);
                    trie2.delete(arr[j]);
                    right.delete(arr[j]);
                } else if (decide < 0.75) {
                    int ans1 = trie1.search(arr[j]);
                    int ans2 = trie2.search(arr[j]);
                    int ans3 = right.search(arr[j]);
                    if (ans1 != ans2 || ans2 != ans3) {
                        System.out.println("Oops!");
                    }
                } else {
                    int ans1 = trie1.prefixNum(arr[j]);
                    int ans2 = trie2.prefixNumber(arr[j]);
                    int ans3 = right.prefixNumber(arr[j]);
                    if (ans1 != ans2 || ans2 != ans3) {
                        System.out.println("Oops!");
                    }
                }
            }
        }
        System.out.println("finish!");
    }

    static class Node {
        public int pass;  //途径次数
        public int end;  //作为结尾的次数
        public Node[] nexts;  //后续节点的分支

        public Node() {
            // 初始化
            pass = 0;
            end = 0;
            // 以26字母为根路径创建节点
            nexts = new Node[26];
        }
    }

    /*
     * 树结构
     */
    static class Trie {
        private Node root;

        public void insert(String word) {
            if (word == null) {
                return;
            }

            char[] charArray = word.toCharArray();
            // 从根节点开始
            Node node = root;
            // 每存在一个节点就累加一次途径次数
            node.pass++;

            // 初始化路线
            int path = 0;
            for (int i = 0; i < charArray.length; i++) {
                // 计算当前字符的ASCII - 'a'的ASCII码值得到与a的距离，决定走哪一路
                path = charArray[i] - 'a';
                // 每加入一个没加过的字符就建一个节点来存
                if (node.nexts[path] == null) {
                    node.nexts[path] = new Node();
                }
                node = node.nexts[path];
                // 每寸一个节点就记一次途径次数
                node.pass++;
            }
            // 每到最后一个节点设置一次end次数
            node.end++;
        }

        /*
         * 删除树中匹配的词，每次只删一次
         */
        public void delete(String word) {
            if (this.search(word) != 0) {
                char[] charArray = word.toCharArray();
                // 从根节点开始往下遍历，每找到一个节点就将对应节点的途径数减一
                Node node = root;
                node.pass--;

                // 操作没条路线的节点
                int path = 0;
                for (int i = 0; i < charArray.length; i++) {
                    path = charArray[i] - 'a';
                    // 一旦找到pass为0的说明没有途径过，直接将后续节点设置为null，等待自动GC掉
                    if (--node.nexts[path].pass == 0) {
                        node.nexts[path] = null;
                        return;
                    }
                    node = node.nexts[path];
                }
                // 每到最后一个节点即跟随减一
                node.end--;
            }
        }

        /*
         * 查询word之前加入过几次
         */
        public int search(String word) {
            if (word == null) {
                return 0;
            }

            char[] charArray = word.toCharArray();
            // 从根节点开始找
            Node node = root;
            int index = 0;
            for (int i = 0; i < charArray.length; i++) {
                index = charArray[i] - 'a';
                // 如果某个节点的下一个节点是指向null的则直接返回0
                if (node.nexts[index] == null) {
                    return 0;
                }
                // Node有内容的则对每个节点的end进行统计
                node = node.nexts[index];
            }
            // end 为1就代表这个单词完整结束，最终的end值就代表这个词一共出现过几次
            return node.end;
        }

        /*
         * 统计所有字符串中以某个字符串作为前缀的总数
         */
        public int prefixNum(String prefix) {
            if (prefix == null) {
                return 0;
            }

            char[] charArray = prefix.toCharArray();
            // 从根节点开始遍历
            Node node = root;
            int index = 0;
            for (int i = 0; i < charArray.length; i++) {
                index = charArray[i] - 'a';
                if (node.nexts[index] == null) {
                    return 0;
                }
                node = node.nexts[index];
            }
            // 统计这个前缀一共被途径了多少次，代表以这个字符串做过多少次前缀
            return node.pass;
        }


        public Trie() {
            this.root = new Node();
        }

    }

    public static class Node2 {
        public int pass;
        public int end;
        public HashMap<Integer, Node2> nexts;

        public Node2() {
            pass = 0;
            end = 0;
            nexts = new HashMap<>();
        }
    }

    public static class Trie2 {
        private Node2 root;

        public Trie2() {
            root = new Node2();
        }

        public void insert(String word) {
            if (word == null) {
                return;
            }
            char[] chs = word.toCharArray();
            Node2 node = root;
            node.pass++;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = (int) chs[i];
                if (!node.nexts.containsKey(index)) {
                    node.nexts.put(index, new Node2());
                }
                node = node.nexts.get(index);
                node.pass++;
            }
            node.end++;
        }

        public void delete(String word) {
            if (search(word) != 0) {
                char[] chs = word.toCharArray();
                Node2 node = root;
                node.pass--;
                int index = 0;
                for (int i = 0; i < chs.length; i++) {
                    index = (int) chs[i];
                    if (--node.nexts.get(index).pass == 0) {
                        node.nexts.remove(index);
                        return;
                    }
                    node = node.nexts.get(index);
                }
                node.end--;
            }
        }

        // word这个单词之前加入过几次
        public int search(String word) {
            if (word == null) {
                return 0;
            }
            char[] chs = word.toCharArray();
            Node2 node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = (int) chs[i];
                if (!node.nexts.containsKey(index)) {
                    return 0;
                }
                node = node.nexts.get(index);
            }
            return node.end;
        }

        // 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }
            char[] chs = pre.toCharArray();
            Node2 node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = (int) chs[i];
                if (!node.nexts.containsKey(index)) {
                    return 0;
                }
                node = node.nexts.get(index);
            }
            return node.pass;
        }
    }

    public static class Right {

        private HashMap<String, Integer> box;

        public Right() {
            box = new HashMap<>();
        }

        public void insert(String word) {
            if (!box.containsKey(word)) {
                box.put(word, 1);
            } else {
                box.put(word, box.get(word) + 1);
            }
        }

        public void delete(String word) {
            if (box.containsKey(word)) {
                if (box.get(word) == 1) {
                    box.remove(word);
                } else {
                    box.put(word, box.get(word) - 1);
                }
            }
        }

        public int search(String word) {
            if (!box.containsKey(word)) {
                return 0;
            } else {
                return box.get(word);
            }
        }

        public int prefixNumber(String pre) {
            int count = 0;
            for (String cur : box.keySet()) {
                if (cur.startsWith(pre)) {
                    count += box.get(cur);
                }
            }
            return count;
        }
    }

    // for test
    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 6);
            ans[i] = (char) (97 + value);
        }
        return String.valueOf(ans);
    }

    // for test
    public static String[] generateRandomStringArray(int arrLen, int strLen) {
        String[] ans = new String[(int) (Math.random() * arrLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLen);
        }
        return ans;
    }
}
