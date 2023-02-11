import java.lang.*;

import static java.lang.Math.max;

/**
 * Implements an AVL tree.
 * Note that all "matching" is based on the compareTo method.
 * Based on code provided by Mark Allen Weiss (CS 2420 book author)
 */
public class AVLTree<E extends Comparable<? super E>> {
    /**
     * Construct the tree.
     */
    public AVLTree() {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     *
     * @param value the item to insert.
     */
    public void insert(E value) {
        root = insert(value, root);
    }

    public E deleteMin() {
        // TODO: Write some good stuff here

        // Note: I only put this code here to have it compile.
        // This will NOT work if root is null.  You should return
        // the actual min value found.

        E smallest = findSmallest(root);
        //System.out.println(smallest+" "+root.value);
        deleteMin(smallest,root);
        return smallest;
    }
    private AvlNode deleteMin(E node,AvlNode current) {
        if (current == null) {
            return null;
        }
        //System.out.println(root.value);
        //root= balance(root);
        if(node.compareTo(current.value) < 0){
            //balance(root);
            //System.out.println(root.value+"left");
            current.left = deleteMin(node,current.left);
            //root=balance(root);
        }else if(node.compareTo(current.value) > 0){
            //balance(root);
            //System.out.println(root.value+"right");
            current.right = deleteMin(node,current.right);
            //root = balance(root);
        }else{
            if(current.left==null ||current.right==null){//one or zero child
                AvlNode temp = null;
                if(temp == current.left){
                    temp = current.right;
                }else{
                    temp = current.left;
                }
                if(temp == null){
                    temp = current;
                    current = null;
                }else{
                    current = temp;
                }
                //root=balance(root);
            }
        }
        return balance(current);

    }
    private E findSmallest(AvlNode node){
        while(node.left!=null){
            node=node.left;
        }
        return node.value;
    }

    /**
     * Find the largest item in the tree.
     *
     * @return the largest item of null if empty.
     */
    public E findMax() {
        if (isEmpty()) {
            throw new RuntimeException();
        }

        return findMax(root).value;
    }

    /**
     * Find an item in the tree.
     *
     * @param value the item to search for.
     * @return true if x is found.
     */
    public boolean contains(E value) {
        return contains(value, root);
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty() {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree(String label) {
        System.out.println(label);
        System.out.println(printTree("",root,0));
    }
    private String printTree(String tree, AvlNode node, int indent){
        if(node==null){
            return "";
        }
        tree = printTree(tree,node.right,indent+=1);
        String indentTree="";
        for(int i = 0; i<indent; i++){
            indentTree += "\t";
        }
        tree = tree + indentTree+node.value+"("+node.height+")"+"\n";
        return tree + printTree(tree, node.left,indent);

    }

    private static final int ALLOWED_IMBALANCE = 1;

    // Assume t is either balanced or within one of being balanced
    private AvlNode balance(AvlNode node) {
        if (node == null) {
            return null;
        }
        //System.out.println(node.value);
        if (height(node.left) - height(node.right) > ALLOWED_IMBALANCE) {
            if (height(node.left.left) >= height(node.left.right)) {
                node = rightRotation(node);
            } else {
                node = doubleRightRotation(node);
            }
        } else if (height(node.right) - height(node.left) > ALLOWED_IMBALANCE) {
            if (height(node.right.right) >= height(node.right.left)) {
                node = leftRotation(node);
            } else {
                node = doubleLeftRotation(node);
            }
        }

        node.height = max(height(node.left), height(node.right)) + 1;
        return node;
    }

    /**
     * Internal method to insert into a subtree.  Duplicates are allowed
     *
     * @param value the item to insert.
     * @param node  the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode insert(E value, AvlNode node) {
        if (node == null) {
            return new AvlNode(value, null, null);
        }

        int compareResult = value.compareTo(node.value);

        if (compareResult < 0) {
            node.left = insert(value, node.left);
        } else {
            node.right = insert(value, node.right);
        }

        return balance(node);
    }

    /**
     * Internal method to find the largest item in a subtree.
     *
     * @param node the node that roots the tree.
     * @return node containing the largest item.
     */
    private AvlNode findMax(AvlNode node) {
        if (node == null) {
            return null;
        }

        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    /**
     * Internal method to find an item in a subtree.
     *
     * @param value is item to search for.
     * @param node  the node that roots the tree.
     * @return true if x is found in subtree.
     */
    private boolean contains(E value, AvlNode node) {
        while (node != null) {
            int compareResult = value.compareTo(node.value);

            if (compareResult < 0) {
                node = node.left;
            } else if (compareResult > 0) {
                node = node.right;
            } else {
                return true;    // Match
            }
        }

        return false;   // No match
    }

    /**
     * Return the height of node t, or -1, if null.
     */
    private int height(AvlNode node) {
        if (node == null) {
            return -1;
        }

        return node.height;
    }

    /**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     */
    private AvlNode rightRotation(AvlNode node) {
        AvlNode theLeft = node.left;
        node.left = theLeft.right;
        theLeft.right = node;
        node.height = max(height(node.left), height(node.right)) + 1;
        theLeft.height = max(height(theLeft.left), node.height) + 1;
        return theLeft;
    }

    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     */
    private AvlNode leftRotation(AvlNode node) {
        AvlNode theRight = node.right;
        node.right = theRight.left;
        theRight.left = node;
        node.height = max(height(node.left), height(node.right)) + 1;
        theRight.height = max(height(theRight.right), node.height) + 1;
        return theRight;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     */
    private AvlNode doubleRightRotation(AvlNode node) {
        node.left = leftRotation(node.left);
        return rightRotation(node);
    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child.
     * For AVL trees, this is a double rotation for case 3.
     * Update heights, then return new root.
     */
    private AvlNode doubleLeftRotation(AvlNode node) {
        node.right = rightRotation(node.right);
        return leftRotation(node);
    }

    private class AvlNode {
        AvlNode(E value, AvlNode left, AvlNode right) {
            this.value = value;
            this.left = left;
            this.right = right;
            height = 0;
        }

        E value;      // The data in the node
        AvlNode left;         // Left child
        AvlNode right;        // Right child
        int height;       // Height
    }

    /**
     * The tree root.
     */
    private AvlNode root;
}
