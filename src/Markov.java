import java.io.File;
import java.io.IOException;
import java.util.*;

public class Markov {

    /**
     * Author: Shai Baruch
     * Date: 3/18/2023
     * Description: A program that reads files and stores them in forms of keys and values.
     * each word is followed by another and so, the program use these words to generate a new text.
     * The program will make use of the words based on the punctuation.
     */
    private static final String ENDS_IN_PUNCTUATION = "__$";
    private static final String PUNCTUATION_MARKS = ".!?$";
    private String prevWord;
private HashMap<String, ArrayList<String>> words;

    public Markov(){

        words = new HashMap<>(){{
            put(ENDS_IN_PUNCTUATION,new ArrayList<>());
        }};
        prevWord = ENDS_IN_PUNCTUATION;
    }



public static boolean endsWithPunctuation(String word) { // boolean method checks for punctuation after each word.
    try {
        char ch = word.charAt(word.length() - 1);
        for (int i = 0; i < PUNCTUATION_MARKS.length(); i++) {
            if (ch == PUNCTUATION_MARKS.charAt(i)) { //comparing chars to check for punctuation marks.
                return true;

            }
        }
    } catch (Exception e){
        System.out.println("Exception detected: "+e);
    }
    return false;
}
public void addFromFile(String filename){
    try {
        File f = new File(filename);
        Scanner scan = new Scanner(f);
        while(scan.hasNextLine()) {

            addLine(scan.nextLine());
            }
    }catch (IOException e){
        System.out.println("Exception detected: "+ e);
        }
}
void addLine(String line ){ // check for empty lines, spaces...
        if(null == line || line.isEmpty() || line.isBlank())
            return;

    for(String s : line.split(" ")){
        if(s.isBlank()||s.isEmpty()) { // making sure there's no pulling \t,\n," "
            continue;
        }
    addWord(s);
    }
}

String randomWord(String randKey){

    ArrayList<String> values = words.get(randKey);
    Random rand = new Random();
    return values.get(rand.nextInt(values.size()));
}

public String toString() {
return words.toString();
}

     HashMap<String, ArrayList<String>> getWords() {
        return words;
    }

    void addWord(String currentWord){
        if (endsWithPunctuation(prevWord)) {
            words.get(ENDS_IN_PUNCTUATION).add(currentWord);
        }else {
            ArrayList<String> values = words.get(prevWord);
            if (null == values) {
                values = new ArrayList<>();
                words.put(prevWord, values);
            }

            values.add(currentWord);
        }
        prevWord = currentWord; // making sure prevWord is always changing.
    }

    public String getSentence(){
        StringBuilder str = new StringBuilder();

        String currentWord = randomWord(ENDS_IN_PUNCTUATION);
        while(!endsWithPunctuation(currentWord)){ // Building each sentence up to punctuation.
            str.append(currentWord).append(" ");
            currentWord = randomWord(currentWord);
        }
        str.append(currentWord); // adding currentWord only.
        return str.toString();
    }

}

