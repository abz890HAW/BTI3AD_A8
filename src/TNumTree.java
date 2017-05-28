import NumTree.NumTree;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by marble on 5/10/17.
 */
public class TNumTree {
    private static final int DEPTH = 8;
    /* unique random numbers !! */
    private static final int[] VAL_ARR = {28,  4, 82, 20, 93,
                                          21, 49, 86,  0, 42,
                                          22, 14, 39, 77, 37,
                                          87, 24, 19, 27, 25,
                                          47, 30, 81, 89,  5};
    private static NumTree numTree;

    @Before
    public void init() {
        numTree = new NumTree();
    }

    @Test
    public void test() {

        /* clone array and sort for comparison */
        Integer[] sorted = new Integer[VAL_ARR.length];
        for(int i = 0; i < VAL_ARR.length; i++) {
            sorted[i] = VAL_ARR[i];
        }
        Arrays.sort(sorted);
        /* fill tree with unique random numbers */
        for(int val: VAL_ARR) {
            assertTrue(numTree.insert(val));
        }
        System.out.printf("%d\n", numTree.getSum());
        /* try to insert one number again */
        assertFalse(numTree.insert(VAL_ARR[0]));
        System.out.printf("%d\n", numTree.getSumFromTo(20, 81));
    }

}
