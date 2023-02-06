# Assignment 3: Word Ladders Revisited

## Introduction:

I will be redoing my word ladders assignment, making it more efficient.

### AVL CLASS:

Fix Assignment 1 code.
#### public void printTree(){
    /*          43(0)
            37(1)
                31(0)
        29(2)
            23(0)
    17(3)
                13(0)
            11(1)
                7(0)
        5(2)
                3(0)
            2(1)
                1(0)
    */
    use recursion .toString() function
}
#### public E deleteMin(){
    /*The Tree after deleteMin
                43(0)
            37(1)
                31(0)
        29(2)
            23(0)
    17(3)
                13(0)
            11(1)
                7(0)
        5(2)
                3(0)
            2(1)
    */
    Removes smallest value from the tree. Makes sure it remains an AVL tree after removal.
    private AVL node findSmallest(int current, AVL node){
        if(node.key<current){
            current = node.key;
        }
        return findSmallest(current,node.left);
    }
    private AVL deleteMin(AVL node){
        travserse AVL tree;
        when node is found{
            AVLNode temp = null;
				if (temp == node.left)
					temp = node.right;
				else
					temp = node.left;

				if (temp == null)
				{
					temp = node;
					node = null;
				}
				else 
					node = temp;
        }
        //check to make sure its AVL and rotate as needed
    }
}