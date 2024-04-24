package trietree;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2024/4/25 12:31 AM
 */
public class TrieTree {
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
}
