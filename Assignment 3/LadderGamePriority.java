import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class LadderGamePriority extends LadderGame{
    ArrayList<ArrayList<String>> organized = new ArrayList<>();
    ArrayList<String> allWords = new ArrayList<>();

    public LadderGamePriority(String dictionaryFile) {
        super(dictionaryFile);
        readDictionary(dictionaryFile);
    }

    public void play(String start, String end){
        System.out.println("Seeking A* solution for "+start+" -> "+end);
        if(!allWords.contains(end) || !allWords.contains(start)){//checks to make sure start and end are not the same.
            System.out.println(start + "->"+ end + ": No ladder was found");
            return;
        }
        if(end.length()!=start.length()){
            System.out.println("Words are of differing lengths!");
        }
        ArrayList<String> clone = (ArrayList<String>)organized.get(start.length()-2).clone();//keeps a copy of the array in case of deletion
        AVLTree<WordInfoPriority> node = new AVLTree<>();
        boolean found = false;
        WordInfoPriority solution= new WordInfoPriority(start, 0, start.length()-diff(start,end),start);
        node.insert(solution);//insert start
        solution=null;
        //node.printTree("test");
        //System.out.println(node);
        int count =1;
        this.organized.get(start.length()-2).remove(start);
        while(!node.isEmpty()&&!found){
            //node.printTree("test");
            WordInfoPriority current = node.deleteMin();//finds the highest priority solution.
            var oneAway = this.oneAway(current.getWord(),true);//list of words one away from highest priority solution
            for(var word:oneAway){//adds each word to avl tree
                var test = new WordInfoPriority(word,current.getMoves()+1,word.length()-diff(end,word), current.getHistory()+" "+word);
                if(word.equals(end)){
                    found = true;
                    solution = test;
                }else{
                    node.insert(test);
                    count++;
                }
            }
        }
        if(found){
            System.out.println(start+" -> "+end +" " + solution.getMoves() +" Moves ["+solution.getHistory()+"] total enqueues "+(count+1) );
        }else{
            System.out.println(start + "->"+ end + ": No ladder was found");
        }
        this.organized.set(start.length()-2,clone);
    }
    public ArrayList<String> oneAway(String word, boolean withRemoval) {
        //allWords.remove(word);
        ArrayList<String> words = new ArrayList<>();
        for(String possible : organized.get(word.length()-2)){
            //System.out.println("1");
            if(diff(word,possible)==1){
                words.add(possible);
            }
        }
        if(withRemoval){
            organized.get(word.length()-2).removeAll(words);
        }
        return words;
    }
    private int diff(String word, String test){
        if (word.length() != test.length()) {
            return -1;
        }
        int diff = 0;
        //System.out.println(diff);
        for(int i =0; i<word.length(); i++){
            if(word.charAt(i)!=test.charAt(i)){
                diff++;
                //System.out.println(diff);
            }
        }
        return diff;
    }
    private void readDictionary(String dictionaryFile) {
        File file = new File(dictionaryFile);
        //ArrayList<String> allWord = new ArrayList<>();

        //
        // Track the longest word, because that tells us how big to make the array.
        int longestWord = 0;
        try (Scanner input = new Scanner(file)) {
            //
            // Start by reading all the words into memory.
            while (input.hasNextLine()) {
                String word = input.nextLine().toLowerCase();
                allWords.add(word);
                longestWord = Math.max(longestWord, word.length());
            }
            for(int i = 0; i< longestWord; i++){
                ArrayList<String> row = new ArrayList<>();
                for(String item: allWords){
                    if(item.length()-2==i){
                        row.add(item);
                    }
                }
                organized.add(row);
            }

        }
        catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the dictionary: " + ex);
        }
    }
}

