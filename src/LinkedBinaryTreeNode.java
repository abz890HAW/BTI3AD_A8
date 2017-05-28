package aufgabe8.src;

/**
 * Node of a LinkedBinaryTree
 */
public class LinkedBinaryTreeNode<T extends Long> {

    /**
     * Actual node-value
     */
    private T _value;

    /**
     * Left child of node
     */
    private LinkedBinaryTreeNode<T> _leftChild;

    /**
     * Right child of node
     */
    private LinkedBinaryTreeNode<T> _rightChild;

    /**
     * Contains the sum of all smaller values of the node
     */
    private long _lowerValueSum;

    public LinkedBinaryTreeNode(T value) {
        this(value, null, null);
    }

    public LinkedBinaryTreeNode(T value, LinkedBinaryTreeNode<T> leftChild, LinkedBinaryTreeNode<T> rightChild) {
        this._value = value;
        this._leftChild = leftChild;
        this._rightChild = rightChild;
        this._lowerValueSum = 0;
    }

    public T getValue() {
        return _value;
    }

    public void setValue(T value) {
        _value = value;
    }

    public LinkedBinaryTreeNode<T> getLeftChild() {
        return _leftChild;
    }

    public void setLeftChild(LinkedBinaryTreeNode<T> leftChild) {
        _leftChild = leftChild;
    }

    public LinkedBinaryTreeNode<T> getRightChild() {
        return _rightChild;
    }

    public void setRightChild(LinkedBinaryTreeNode<T> rightChild) {
        _rightChild = rightChild;
    }

    public long getLowerValueSum() {
        return _lowerValueSum;
    }

    public void setLowerValueSum(long lowerValueSum) {
        _lowerValueSum = lowerValueSum;
    }

    @Override
    public String toString() {
        return String.format("%d(%d)", _value, _lowerValueSum);
    }

}
