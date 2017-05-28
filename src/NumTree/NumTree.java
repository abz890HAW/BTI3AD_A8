package NumTree;

/**
 * Created by marble on 5/28/17.
 */
public class NumTree {
    private NumNode root = null;

    public boolean insert(int val) {
        if(null == root) {
            root = new NumNode(val);
            return true;
        }
        else {
            return root.insert(val);
        }
    }

    public int getSum() {
        return root.getSum();
    }

    public int getSumFromTo(int left, int right) {
        int result;
        if(null == root) {
            return 0;
        }
        result = root.getSum();
        result -= root.getSumLeftOf(left);
        result -= root.getSumRightOf(right);
        return result;
    }
}
