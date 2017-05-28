package aufgabe8.src;

/**
 * Implemenation of a binary searchtree based on an internal linked structure of nodes of Long
 */
public class LinkedBinaryTree {

    /**
     * Enumeration for supported order types
     */
    enum ORDER_TYPE {
        INORDER, PREORDER, POSTORDER
    }

    /**
     * Enables the user to select a preference for node selection while searching the nearest node
     */
    enum NODE_SEARCH_PREFERENCE {
        BIGGER, SMALLER, CLOSEST
    }

    /**
     * Node containing maximum value. Used during node search
     */
    private static final LinkedBinaryTreeNode<Long> MAX_VALUE_NODE = new LinkedBinaryTreeNode<>(Long.MAX_VALUE);

    /**
     * Node containing mininum value. Used during node search
     */
    private static final LinkedBinaryTreeNode<Long> MIN_VALUE_NODE = new LinkedBinaryTreeNode<>(Long.MIN_VALUE);

    /**
     * Root node of the LinkedBinaryTree
     */
    private LinkedBinaryTreeNode<Long> _rootNode;

    /**
     * Inserts a new node into the tree
     *
     * @param newValue Value to be inserted
     *
     * @throws NullPointerException - if newValue == null
     */
    public void insert(Long newValue) {
        // Check if root node is present
        if (_rootNode == null) {
            _rootNode = new LinkedBinaryTreeNode<>(newValue);
            return;
        }

        // Insert element at correct position
        LinkedBinaryTreeNode<Long> curNode = _rootNode;
        while (true) {
            // Filter out duplicates
            if (curNode.getValue().compareTo(newValue) == 0)
                return;

            // Check where to insert
            if (curNode.getValue().compareTo(newValue) < 0) {
                // newValue > curNodes-Value => Go right
                if (curNode.getRightChild() == null) {
                    curNode.setRightChild(new LinkedBinaryTreeNode<>(newValue));
                    return;
                }

                curNode = curNode.getRightChild();
            } else {
                // newValue < curNodes-Value => Go left
                if (curNode.getLeftChild() == null) {
                    curNode.setLeftChild(new LinkedBinaryTreeNode<>(newValue));
                    return;
                }

                curNode = curNode.getLeftChild();
            }
        }

    }

    /**
     * Wrapper for updateChildSums(LinkedBinaryTreeNode, Long)
     */
    public void updateChildSums() {
        updateChildSums(_rootNode, 0L);
    }

    /**
     * Iterates inOrder trough the tree and sums up all values that are smalle than curNode
     *
     * @param curNode Current node to process
     * @param curSum  Current sum
     *
     * @return Current sum after calculation to destruct call stack
     */
    private Long updateChildSums(LinkedBinaryTreeNode curNode, Long curSum) {
        // Cancelation condition
        if (curNode == null) {
            return 0L;
        }

        // Go down do smallest (leftest) node
        if (curNode.getLeftChild() != null) {
            curSum = updateChildSums(curNode.getLeftChild(), curSum);
        }

        // Add curNodes value to sum
        curSum += curNode.getValue();
        curNode.setLowerValueSum(curSum);
        _counterUpdateChildSum++;

        // Go down to biggest (rightest) node
        if (curNode.getRightChild() != null) {
            curSum = updateChildSums(curNode.getRightChild(), curSum);
        }

        return curSum;
    }

    /**
     * Determines the sum of node values between the given bounds
     *
     * @param lowerBound Lower bound
     * @param upperBound Upper bound
     *
     * @return Sum of the node values in between these bounds
     */
    public Long limitSum(long lowerBound, long upperBound) {
        // Preconditions
        if (lowerBound > upperBound)
            throw new IllegalArgumentException();

        // Find nodes
        LinkedBinaryTreeNode upperNode = findClosestBiggerNode(upperBound);
        LinkedBinaryTreeNode lowerNode = findClosestSmallerNode(lowerBound);

        // Assure that nodes got found otherwise select smallest/biggest node
        if (upperNode == MIN_VALUE_NODE) {
            upperNode = getBiggestNode();
            upperBound = upperNode.getValue();
            _counterLimitSum++;
        }
        if (lowerNode == MAX_VALUE_NODE) {
            lowerNode = getSmallestNode();
            lowerBound = lowerNode.getValue();
            _counterLimitSum++;
        }

        // Calculate offset for cases where nodes match directly
        long offset = 0;
        if (lowerNode.getValue() == lowerBound)
            offset += lowerNode.getValue();
        if (upperNode.getValue() > upperBound)
            offset -= upperNode.getValue();
        _counterLimitSum+=2;

        // Calculate difference
        _counterLimitSum++;
        return findClosestBiggerNode(upperBound).getLowerValueSum() - findClosestSmallerNode(lowerBound).getLowerValueSum() + offset;
    }

    /**
     * Temporary node used during finding nodes recursively
     */
    private LinkedBinaryTreeNode _tmpNode;

    /**
     * Finds the closest node that has to be smaller than value
     *
     * @param value Value to look for
     *
     * @return Node matching the described condition
     */
    private LinkedBinaryTreeNode findClosestSmallerNode(long value) {
        _tmpNode = MAX_VALUE_NODE;
        findClosestNode(_rootNode, value, NODE_SEARCH_PREFERENCE.SMALLER);

        return _tmpNode;
    }

    /**
     * Finding the closest node that has to be bigger than value
     *
     * @param value Value to look for
     *
     * @return Node matching the described condition
     */
    private LinkedBinaryTreeNode findClosestBiggerNode(long value) {
        _tmpNode = MIN_VALUE_NODE;
        findClosestNode(_rootNode, value, NODE_SEARCH_PREFERENCE.BIGGER);

        return _tmpNode;
    }

    /**
     * Recursive search that finds a node closest to the value with respect to the given preference
     *
     * @param curNode    Node to process
     * @param value      Value to look for
     * @param preference Precerence for node selection according to NODE_SEARCH_PRERENCE enum
     *
     * @return Matching node
     */
    private LinkedBinaryTreeNode<Long> findClosestNode(LinkedBinaryTreeNode curNode, long value, NODE_SEARCH_PREFERENCE preference) {
        // Cancelation condition for not exactly matching nodes
        if (curNode == null)
            return null;

        // Check if node matches directly
        if (curNode.getValue() == value) {
            _tmpNode = curNode;
        }
        _counterLimitSum++;

        // Check if a node is "closer" to the value than the last found node
        if (Math.abs(curNode.getValue() - value) < Math.abs(_tmpNode.getValue() - value)) {
            // Check if found node matches the selected preference
            if (preference == NODE_SEARCH_PREFERENCE.SMALLER) {
                if (curNode.getValue() < value)
                    _tmpNode = curNode;
            } else if (preference == NODE_SEARCH_PREFERENCE.BIGGER) {
                if (curNode.getValue() > value)
                    _tmpNode = curNode;
            } else if (preference == NODE_SEARCH_PREFERENCE.CLOSEST) {
                _tmpNode = curNode;
            }
            _counterLimitSum++;
        }

        // Process down in the tree
        if (curNode.getValue() < value)
            return findClosestNode(curNode.getRightChild(), value, preference);

        if (curNode.getValue() > value)
            return findClosestNode(curNode.getLeftChild(), value, preference);

        return curNode;
    }

    /**
     * Finds the smallest value of the tree
     *
     * @return Node containing smallest tree-wide value
     */
    private LinkedBinaryTreeNode getSmallestNode() {
        LinkedBinaryTreeNode result = _rootNode;

        while (result.getLeftChild() != null) {
            result = result.getLeftChild();
        }

        return result;
    }

    /**
     * Finds the biggest value of the tree
     *
     * @return Node containing biggest tree-wide value
     */
    private LinkedBinaryTreeNode getBiggestNode() {
        LinkedBinaryTreeNode result = _rootNode;

        while (result.getRightChild() != null) {
            result = result.getRightChild();
        }

        return result;
    }

    /**
     * Returns a string representation of the tree accorting to sortType
     *
     * @param sortType Desired sorting method
     *
     * @return String repsentation of the tree
     */
    public String toString(ORDER_TYPE sortType) {
        switch (sortType) {
            case INORDER:
                return "INORDER: [" + toStringInorder(_rootNode) + "]";
            case PREORDER:
                return "PREORDER: [" + toStringPreorder(_rootNode) + "]";
            case POSTORDER:
                return "POSTORDER: [" + toStringPostorder(_rootNode) + "]";
        }

        return null;
    }

    /**
     * Generates String inorder-represenataion of tree starting at given node
     *
     * @param curNode Node to start from
     *
     * @return Inorder-representation of tree
     */
    private String toStringInorder(LinkedBinaryTreeNode curNode) {
        if (curNode == null)
            return "";

        return appendSep(toStringInorder(curNode.getLeftChild())) + curNode + prependSep(toStringInorder(curNode.getRightChild()));
    }

    /**
     * Generates String preorder-represenataion of tree starting at given node
     *
     * @param curNode Node to start from
     *
     * @return Preorder-representation of tree
     */
    private String toStringPreorder(LinkedBinaryTreeNode curNode) {
        if (curNode == null)
            return "";

        return curNode + prependSep(toStringInorder(curNode.getLeftChild())) + prependSep(toStringInorder(curNode.getRightChild()));
    }

    /**
     * Generates String postorder-represenataion of tree starting at given node
     *
     * @param curNode Node to start from
     *
     * @return Postorder-representation of tree
     */
    private String toStringPostorder(LinkedBinaryTreeNode curNode) {
        if (curNode == null)
            return "";

        return appendSep(toStringInorder(curNode.getLeftChild())) + appendSep(toStringInorder(curNode.getRightChild())) + curNode;
    }

    /**
     * Appends a seperator to input if input is not empty
     *
     * @param input String to append a seperator to
     *
     * @return String with seperator or empty string
     */
    private String appendSep(String input) {
        if (!input.isEmpty()) {
            return input + ",";
        }

        return "";
    }

    /**
     * Prepends a seperator to input if input is not empty
     *
     * @param input String to prepend a seperator to
     *
     * @return String with seperator or empty string
     */
    private String prependSep(String input) {
        if (!input.isEmpty()) {
            return "," + input;
        }

        return "";
    }

    /*********************
     * Benchmark related *
     ********************/

    /**
     * Counts all done comparisons
     */
    protected long _counterUpdateChildSum = 0;

    /**
     * Counts all done data movements
     */
    protected long _counterLimitSum = 0;

    public long getCounterUpdateChildSum() {
        return _counterUpdateChildSum;
    }

    public long getCounterLimitSum() {
        return _counterLimitSum;
    }

    public void resetCounter() {
        _counterUpdateChildSum = 0;
        _counterLimitSum = 0;
    }

}
