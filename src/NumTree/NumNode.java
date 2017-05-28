package NumTree;

/**
 * Created by marble on 5/28/17.
 */
public class NumNode {
    private int val;
    private int sum;
    private NumNode left=null, right=null;

    NumNode(int val){
        this.val = val;
        this.sum = val;
    }

    int getSum() {
        return sum;
    }

    public boolean insert(int val) {
        if(this.val == val) {
            return false;
        }
        else if(this.val > val) {
            if(null == left) {
                left = new NumNode(val);
                sum += val;
                return true;
            }
            else {
                if(left.insert(val)) {
                    sum += val;
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        else {
            if(null == right) {
                right = new NumNode(val);
                sum += val;
                return true;
            }
            else {
                if(right.insert(val)) {
                    sum += val;
                    return true;
                }
                else {
                    return false;
                }
            }
        }
    }

    int getSumLeftOf(int val) {
        if(this.val >= val) {
            if(null != left) {
                return left.getSumLeftOf(val);
            }
            else {
                return 0;
            }
        }
        else {
            int result = this.val;
            if(null != left) {
                result += left.getSum();
            }
            if(null != right) {
                result += right.getSumLeftOf(val);
            }
            return result;
        }
    }

    int getSumRightOf(int val) {
        if(this.val <= val) {
            if(null != right) {
                return right.getSumRightOf(val);
            }
            else {
                return 0;
            }
        }
        else {
            int result = this.val;
            if(null != right) {
                result += right.getSum();
            }
            if(null != left) {
                result += left.getSumRightOf(val);
            }
            return result;
        }
    }
}
