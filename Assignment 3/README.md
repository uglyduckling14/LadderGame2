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
### WORDINFOPRIORITY CLASS implements Comparable:
Priority Calculation:
* NOTE: The lower the expected total work, the higher the priority
current ladder.length() - dif(current,end)

##### private WordInfoPriority priority = new WordInfoPriority;
private int estimatedWork= moves+ diff(test, word);
##### public WordInfoPriority(String word, int moves, int estimatedWork){
    //this.word = word;
    //this.moves = moves;
    //this.estimatedWork = estimatedWork;
}
#### public WordInfoPriority(String word, int moves, int estimatedWork, String history){
    //this.word = word;
    //this.moves = moves;
    //this.estimatedWork;
    //this.history = history;
}
#### public int compareTo(E word){
    if( priority.estimatedWork vs word.estimatedWork);
}
#### private int diff(String test, String word);
### LADDERGAMEPRIORITY CLASS:
    public void play(String start, String end){
        if(!allWords.contains(end) || !allWords.contains(start)){//checks to make sure start and end are not the same.
            System.out.println(start + "->"+ end + ": No ladder was found");
            return;
        }
        if(end.length()!=start.length()){
            System.out.println("Words are of differing lengths!");
        }
        ArrayList<String> clone = (ArrayList<String>)organized.get(start.length()-2).clone();//keeps an copy of the array in case of deletion
        AVLTree<WordInfoPriority> node = new AVLTree<>();
        boolean found = false;
        WordInfoPriority solution = null;
        node.insert(new WordInfo(start,0));//insert start
        int count =1;
        this.organized.get(start.length()-2).remove(start);
        while(!node.isEmpty()&&!found){
            WordInfoPriority current = node.deleteMin();//finds the highest priority solution.
            var oneAway = this.oneAway(current.getWord(),true);//list of words one away from highest priority solution
            for(var word:oneAway){//adds each word to avl tree
                var test = new WordInfoPriority(word,current.getMoves()+1, current.getHistory()+" "+word, word.length-moves);
                if(word.equals(end)){
                    found = true;
                    solution = current;
                }else{
                    node.insert(test);
                    count++;
                }
            }
        }
        if(found){
            System.out.println(start+" -> "+end +" " + solution.getMoves() +" Moves ["+solution.getHistory()+"] total enqueues "+count );
        }else{
            System.out.println(start + "->"+ end + ": No ladder was found");
        }
        this.organized.set(start.length()-2,clone);
    }
#### public int dif
#### public ArrayList oneAway

### Major Changes:
* Made sure that for duplicate values I had taken into account that they may have duplicate children.
* returned balance(root) in deleteMin() instead of balance(current)
* Created findSmallestNode() method

### Future Edits:
There is a lot of redundant code, especially in the AVLTree deleteMin() method, but it works and this assignment is due in
4 hours.