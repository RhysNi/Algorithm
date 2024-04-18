package heap;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * @author Rhys.Ni
 * @version 1.0
 * @date 2024/4/19 2:18 AM
 */
public class PrizeDrawTopK {
    private HashMap<Integer, Customer> customers;
    // 得奖区
    private StrengthenHeap<Customer> prizeAreaHeap;
    // 候奖区
    private StrengthenHeap<Customer> prizeWaitingAreaHeap;
    // 得奖用户数
    private final int prizeLimit;

    public PrizeDrawTopK(int prizeLimit) {
        this.customers = new HashMap<>();
        this.prizeAreaHeap = new StrengthenHeap<>(new PrizeAreaComparator());
        this.prizeWaitingAreaHeap = new StrengthenHeap<>(new PrizeWaitingAreaComparator());
        this.prizeLimit = prizeLimit;
    }

    public static void main(String[] args) {
        int maxValue = 10;
        int maxLen = 100;
        int maxK = 6;
        int testTimes = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            Data testData = randomData(maxValue, maxLen);
            int k = (int) (Math.random() * maxK) + 1;
            int[] arr = testData.arr;
            boolean[] op = testData.op;
            List<List<Integer>> ans1 = topK(arr, op, k);
            List<List<Integer>> ans2 = compare(arr, op, k);
            if (!sameAnswer(ans1, ans2)) {
                for (int j = 0; j < arr.length; j++) {
                    System.out.println(arr[j] + " , " + op[j]);
                }
                System.out.println(k);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("出错了！");
                break;
            }
        }
        System.out.println("测试结束");
    }

    public static List<List<Integer>> topK(int[] arr, boolean[] op, int k) {
        List<List<Integer>> result = new ArrayList<>();
        PrizeDrawTopK drawTopK = new PrizeDrawTopK(k);
        for (int i = 0; i < arr.length; i++) {
            drawTopK.operation(i, arr[i], op[i]);
            // 公布所有得奖用户的id
            result.add(drawTopK.getAwardWinningUser());
        }
        return result;
    }

    private List<Integer> getAwardWinningUser() {
        List<Customer> customerList = prizeAreaHeap.getAll();
        List<Integer> result = new ArrayList<>();
        for (Customer customer : customerList) {
            result.add(customer.id);
        }
        return result;
    }

    // 处理购买事件
    public void operation(int time, int id, boolean buyOrRefund) {
        // 如果某个用户购买商品数为0，但是又发生了退货事件，则认为该事件无效
        if (!buyOrRefund && !customers.containsKey(id)) {
            return;
        }

        // 没有购买记录
        if (!customers.containsKey(id)) {
            customers.put(id, new Customer(id, 0, 0));
        }
        // 看操作是购买还是退货
        Customer customer = customers.get(id);
        if (buyOrRefund) {
            customer.buy++;
        } else {
            customer.buy--;
        }

        // 没消费则从参与名单中移除
        if (customer.buy == 0) {
            customers.remove(id);
        }

        if (!prizeWaitingAreaHeap.contains(customer) && !prizeAreaHeap.contains(customer)) {
            // 新用户进来如果得奖区没满则直接进得奖区,否则进入侯奖区
            if (prizeAreaHeap.size() < prizeLimit) {
                customer.enterTime = time;
                prizeAreaHeap.push(customer);
            } else {
                customer.enterTime = time;
                prizeWaitingAreaHeap.push(customer);
            }
        } else if (prizeWaitingAreaHeap.contains(customer)) {
            // 侯奖区用户如果购货为0则从侯奖区移除,否则重新调整侯奖区
            if (customer.buy == 0) {
                prizeWaitingAreaHeap.remove(customer);
            } else {
                prizeWaitingAreaHeap.resign(customer);
            }
        } else {
            // 得奖区用户如果退货为0则从得奖区移除,否则重新调整得奖区
            if (customer.buy == 0) {
                prizeAreaHeap.remove(customer);
            } else {
                prizeAreaHeap.resign(customer);
            }
        }
        // 侯奖区 <-> 得奖区
        this.prizeAreaHeapMove(time);
    }

    private void prizeAreaHeapMove(int time) {
        // 侯奖区没有参与用户
        if (prizeWaitingAreaHeap.isEmpty()) {
            return;
        }

        // 得奖区没参与满，侯奖区用户往得奖区移动
        if (prizeAreaHeap.size() < prizeLimit) {
            Customer customer = prizeWaitingAreaHeap.pop();
            customer.enterTime = time;
            prizeAreaHeap.push(customer);
        } else {
            // 候奖区排第一的比得奖区排第一的买的多
            if (prizeWaitingAreaHeap.peek().buy > prizeAreaHeap.peek().buy) {
                // 得奖区老用户到侯奖区
                Customer oldCustomer = prizeAreaHeap.pop();
                oldCustomer.enterTime = time;
                prizeWaitingAreaHeap.push(oldCustomer);

                // 侯奖区新用户到得奖区
                Customer newCustomer = prizeWaitingAreaHeap.pop();
                newCustomer.enterTime = time;
                prizeAreaHeap.push(newCustomer);
            }
        }

    }


    private static class Customer {
        public int id;
        public int buy;
        public int enterTime;

        public Customer(int id, int buy, int enterTime) {
            this.id = id;
            this.buy = buy;
            this.enterTime = enterTime;
        }
    }

    private static class PrizeAreaComparator implements Comparator<Customer> {
        @Override
        public int compare(Customer o1, Customer o2) {
            // 买的多排前面，买的一样多则最早买的排前面
            return o1.buy != o2.buy ? (o1.buy - o2.buy) : (o1.enterTime - o2.enterTime);
        }
    }

    private static class PrizeWaitingAreaComparator implements Comparator<Customer> {
        @Override
        public int compare(Customer o1, Customer o2) {
            // 买的多排前面，买的一样多则最早买的排前面
            return o1.buy != o2.buy ? (o2.buy - o1.buy) : (o1.enterTime - o2.enterTime);
        }
    }

    public static boolean sameAnswer(List<List<Integer>> ans1, List<List<Integer>> ans2) {
        if (ans1.size() != ans2.size()) {
            return false;
        }
        for (int i = 0; i < ans1.size(); i++) {
            List<Integer> cur1 = ans1.get(i);
            List<Integer> cur2 = ans2.get(i);
            if (cur1.size() != cur2.size()) {
                return false;
            }
            cur1.sort((a, b) -> a - b);
            cur2.sort((a, b) -> a - b);
            for (int j = 0; j < cur1.size(); j++) {
                if (!cur1.get(j).equals(cur2.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static List<List<Integer>> compare(int[] arr, boolean[] op, int k) {
        HashMap<Integer, Customer> map = new HashMap<>();
        ArrayList<Customer> cands = new ArrayList<>();
        ArrayList<Customer> daddy = new ArrayList<>();
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            int id = arr[i];
            boolean buyOrRefund = op[i];
            if (!buyOrRefund && !map.containsKey(id)) {
                ans.add(getCurAns(daddy));
                continue;
            }
            // 没有发生：用户购买数为0并且又退货了
            // 用户之前购买数是0，此时买货事件
            // 用户之前购买数>0， 此时买货
            // 用户之前购买数>0, 此时退货
            if (!map.containsKey(id)) {
                map.put(id, new Customer(id, 0, 0));
            }
            // 买、卖
            Customer c = map.get(id);
            if (buyOrRefund) {
                c.buy++;
            } else {
                c.buy--;
            }
            if (c.buy == 0) {
                map.remove(id);
            }
            // c
            // 下面做
            if (!cands.contains(c) && !daddy.contains(c)) {
                if (daddy.size() < k) {
                    c.enterTime = i;
                    daddy.add(c);
                } else {
                    c.enterTime = i;
                    cands.add(c);
                }
            }
            cleanZeroBuy(cands);
            cleanZeroBuy(daddy);
            cands.sort(new PrizeWaitingAreaComparator());
            daddy.sort(new PrizeAreaComparator());
            move(cands, daddy, k, i);
            ans.add(getCurAns(daddy));
        }
        return ans;
    }

    public static Data randomData(int maxValue, int maxLen) {
        int len = (int) (Math.random() * maxLen) + 1;
        int[] arr = new int[len];
        boolean[] op = new boolean[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue);
            op[i] = Math.random() < 0.5 ? true : false;
        }
        return new Data(arr, op);
    }

    public static class Data {
        public int[] arr;
        public boolean[] op;

        public Data(int[] a, boolean[] o) {
            arr = a;
            op = o;
        }
    }

    public static void move(ArrayList<Customer> cands, ArrayList<Customer> daddy, int k, int time) {
        if (cands.isEmpty()) {
            return;
        }
        // 候选区不为空
        if (daddy.size() < k) {
            Customer c = cands.get(0);
            c.enterTime = time;
            daddy.add(c);
            cands.remove(0);
        } else { // 等奖区满了，候选区有东西
            if (cands.get(0).buy > daddy.get(0).buy) {
                Customer oldDaddy = daddy.get(0);
                daddy.remove(0);
                Customer newDaddy = cands.get(0);
                cands.remove(0);
                newDaddy.enterTime = time;
                oldDaddy.enterTime = time;
                daddy.add(newDaddy);
                cands.add(oldDaddy);
            }
        }
    }

    public static void cleanZeroBuy(ArrayList<Customer> arr) {
        List<Customer> noZero = new ArrayList<Customer>();
        for (Customer c : arr) {
            if (c.buy != 0) {
                noZero.add(c);
            }
        }
        arr.clear();
        for (Customer c : noZero) {
            arr.add(c);
        }
    }

    public static List<Integer> getCurAns(ArrayList<Customer> daddy) {
        List<Integer> ans = new ArrayList<>();
        for (Customer c : daddy) {
            ans.add(c.id);
        }
        return ans;
    }
}