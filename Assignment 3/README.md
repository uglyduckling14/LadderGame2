# Assignment 3: Word Ladders Revisited

## Introduction:

I will be redoing my word ladders assignment, making it more efficient. I will determine the "best" word ladder by 
calculating a priority value and assigning it to the word.

Then I will use that priority value and store it in an AVL tree. The word with the "highest" priority value will then be 
selected as the new word ladder candidate.

### AVL CLASS:

Fix Assignment 1 code.
#### public void printTree(String label){
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
    System.out.println(label);
    String printed = printTree(String tree,AvlNode node, int height, int indent);
    System.out.println(printed);
}
#### private String printTree(String tree, AvlNode node, int height, int indent){
    if(node==null){
        return "";
    }
    tree = printTree(tree,node.right,node.right.height,indent+=2);
    String indentTree="";
    for(int i = 0; i<indent; i++){
        indentTree += " ";
    }
    indent+=2;
    tree = tree + indentTree+node.key+"("+height+")"+"\n";
    return tree + printTree(tree, node.left, node.left.height,indent);

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
        balance(AVL node);
    }
}
#### private int getBalance(AVL node){
    if(node == null){
        return 0;
    }
    return height(node.left)-height(node.right);
}

### LADDERGAME ABSTRACT CLASS:
Convert everything to abstract methods.

### LADDERGAMEEXHAUSTIVE CLASS:

Copy Assignment 1 code.
### WORDINFOPRIORITY CLASS:
Priority Calculation:
* NOTE: The lower the expected total work, the higher the priority
current ladder.length() + dif(current,end)

private WordInfoPriority priority = new WordInfoPriority;

### LADDERGAMEPRIORITY CLASS: