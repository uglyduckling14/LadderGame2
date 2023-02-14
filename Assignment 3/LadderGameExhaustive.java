import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class LadderGameExhaustive extends LadderGame {
    ArrayList<ArrayList<String>> organized = new ArrayList<>();
    ArrayList<String> allWords = new ArrayList<>();
    public LadderGameExhaustive(String dictionaryFile) {
        super(dictionaryFile);
        readDictionary(dictionaryFile);
    }
    public void play(String start, String end) {
        System.out.println("Seeking Exhaustive solution for " +start + "->"+ end);
        if(!allWords.contains(end) || !allWords.contains(start)){
            System.out.println(start + "->"+ end + ": No ladder was found");
            return;
        }
        if(end.length()!=start.length()){
            System.out.println("Words are of differing lengths!");
        }
        ArrayList<String> clone = (ArrayList<String>)organized.get(start.length()-2).clone();
        Queue<WordInfo> queue = new Queue<>();
        boolean found = false;
        WordInfo solution = null;
        queue.enqueue(new WordInfo(start,0));
        int count =1;
        this.organized.get(start.length()-2).remove(start);

        while(!queue.isEmpty()&&!found){
            WordInfo current = queue.dequeue();
            var oneAway = this.oneAway(current.getWord(),true);
            //System.out.println(oneAway);
            for(var word:oneAway){
                var test = new WordInfo(word,current.getMoves()+1, current.getHistory()+" "+word);
                if(word.equals(end)){
                    found=true;
                    solution = test;
                } else{
                    queue.enqueue(test);
                    count++;
                }
            }
        }
        if(found){
            System.out.println(start+" -> "+end +" " + solution.getMoves() +" Moves ["+solution.getHistory()+"] total enqueues "+count );
        }else{
            System.out.println(start + "->"+ end + ": No ladder was found");
        }
        //System.out.println(organized.get(start.length()-2));
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

    public void listWords(int length, int howMany) {
        for(int i =0; i<howMany;i++){
            System.out.println(organized.get(length-2).get(i));
        }
    }

    /*
        Reads a list of words from a file, putting all words of the same length into the same array.
     */
    private void readDictionary(String dictionaryFile) {
        File file = new File(dictionaryFile);
        //ArrayList<String> allWords = new ArrayList<>();

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
