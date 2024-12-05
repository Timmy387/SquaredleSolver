package SquareLogic;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;


public class LetterGrid {

    // instance variables
    private final ArrayList<Node> letterList;
    private final int size;
    private final ArrayList<String> presentWords;

    private static class Node {
        private final char letter;
        private final int row, column;
        private boolean checked;
        public Node(char letter, int row, int column) {
            this.letter = letter;
            this.row = row;
            this.column = column;
            checked = false;
        }
    }

    /**
     * constructor
     *
     * @param str letters to put in grid
     */
    public LetterGrid(String str) {
        size = (int) Math.sqrt(str.length());
        letterList = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            // / 4 goes 0000,1111,2222,3333 - % 4 goes 0123,0123,0123,0123 (for 4x4 grid)
            letterList.add(new Node(str.charAt(i), i / size, i % size));
        }
        presentWords = new ArrayList<>();
        this.loadAllWords();
    }


    /**
     * checks whether a word can be made using the given grid
     *
     * @param word word to be checked for
     * @return true if word is make-able
     */
    public boolean isWordPresent(String word){
        for (Node currentNode: letterList){
            if (word.charAt(0) == currentNode.letter){
                currentNode.checked = true;
                if (isWordPresent(word.substring(1), currentNode)){
                    currentNode.checked = false;
                    return true;
                }
                currentNode.checked = false;
            }
        }
        return false;
    }

    /**
     * starting from a given node, determine if a word can be made
     *
     * @param word word to be made
     * @param start  starting node
     * @return true if word can be made
     */
    private boolean isWordPresent(String word, Node start) {

        boolean flag = false;
        int i = start.row;
        int iEnd = start.row;
        if (start.row > 0) i--;
        if (start.row < size - 1) iEnd++;

        for (; i <= iEnd; i++){
            int j = start.column;
            int jEnd = start.column;
            if (start.column > 0) j--;
            if (start.column < size - 1) jEnd++;

            for (; j <= jEnd; j++){
                if (i != start.row || j != start.column){
                    Node currentNode = letterList.get(i * size + j);
                    if (currentNode.letter == word.charAt(0) && !currentNode.checked){
                        if (word.length() == 1){
                            return true;
                        }

                        currentNode.checked = true;
                        if (isWordPresent(word.substring(1), currentNode)) {
                            currentNode.checked = false;
                            return true;
                        }
                        currentNode.checked = false;
                    }
                }
            }
        }
        return flag;
    }

    /**
     * getter for words in puzzle
     *
     * @return List of all make-able words
     */
    public ArrayList<String> allWordsPresentArr() {
        return this.presentWords;
    }

    /**
     * loads all make-able english words in square into presentWord list
     */
    private void loadAllWords() {
        ArrayList<String> wordList = new ArrayList<>();
        try {
            InputStream is = getClass().getResourceAsStream("/resources/NWL2020AndBigWords.txt");
            if (is == null) {
                System.out.println("Didn't load .txt file");
                return;
            }
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            while (br.ready()) wordList.add(br.readLine());
            br.close();
            isr.close();
            is.close();
        } catch (IOException e) {
            System.out.println("Failed to read .txt file!");
        }
        for (String str : wordList) {
            if (this.isWordPresent(str)) {
                presentWords.add(str);
            }
        }
    }

    /**
     * formats a list of words from this puzzle
     *
     * @param arr list to be formatted
     * @return list formatted into a readable string
     */
    public static String wordListFormattedForConsole(ArrayList<String> arr) {
        String str = "";
        int delay = 5;

        // find all the letters that a word begins with in the puzzle
        ArrayList<Character> firstLetters = new ArrayList<>();
        for (String word : arr) {
            if (!firstLetters.contains(word.charAt(0)))
                firstLetters.add(word.charAt(0));
        }
        Collections.sort(firstLetters);

        int lineLength = 0;

        // print out all words of each letter in their own groups
        for (Character character : firstLetters) {
            str = str.concat((char) (character - 'a' + 'A') + " words: ");
            for (String word : arr) {
                if (character.equals(word.charAt(0))) {
                    str = str.concat(word);
                    str = str.concat(", ");
                    lineLength += word.length();
                    lineLength += 2;
                    if (lineLength % 160 < 20 && delay == 0) {
                        str = str.concat("\n");
                        delay = 5;
                    }
                    if (delay > 0) delay--;
                }
            }
            str = str.substring(0, str.length() - 2);
            str = str.concat("\n");
            delay = 5;
            lineLength = 0;
        }
        if (str.isEmpty()) return "There are no words in this list.";
        return str;
    }

    /**
     * formats wordlist for app use
     *
     * @param arr wordlist to format
     * @return formatted list
     */
    public static String formattedByLetter(ArrayList<String> arr) {
        String str = "";

        // find all the distinct first letters in the puzzle
        ArrayList<Character> firstLetters = new ArrayList<>();
        for (String word : arr) {
            if (!firstLetters.contains(word.charAt(0)))
                firstLetters.add(word.charAt(0));
        }
        Collections.sort(firstLetters);

        // ArrayList of array lists of words starting with each letter
        ArrayList<ArrayList<String>> difLetWords = new ArrayList<>();
        for (Character character : firstLetters) {
            ArrayList<String> letterArr = new ArrayList<>();
            letterArr.add((char) (character - 'a' + 'A') + " words:");
            difLetWords.add(letterArr);
        }

        // fill up ArrayLists with correct words
        for (String word : arr) {
            for (ArrayList<String> arrLet : difLetWords) {
                if ((char) (arrLet.getFirst().charAt(0) - 'A' + 'a') == word.charAt(0))
                    arrLet.add(word);
            }
        }

        // fill up stringBuilder with words from arrays
        for (ArrayList<String> arrLet : difLetWords) {
            StringBuilder add = new
                    StringBuilder(arrLet.toString().substring(1, arrLet.toString().length() - 1) + "\n");
            add.delete(8, 9);
            str = str.concat(add.toString());
        }
        return str;
    }

    /**
     * sorts and formats any wordlist by length
     *
     * @param arr list to be sorted
     * @return sorted list as a string
     */
    public static String formattedByLength(ArrayList<String> arr) {
        // determine each word length
        ArrayList<Integer> lengths = new ArrayList<>();
        for (String word : arr) {
            if (!lengths.contains(word.length()))
                lengths.add(word.length());
        }
        Collections.sort(lengths);

        // add 'x letters:' to each arraylist
        ArrayList<ArrayList<String>> difLenWords = new ArrayList<>();
        for (Integer i : lengths) {
            ArrayList<String> temp = new ArrayList<>();
            temp.add(i + " letters:");
            difLenWords.add(temp);
        }

        // sort words into each arraylist
        for (String word : arr) {
            for (ArrayList<String> lens : difLenWords) {
                // find number of current arraylist
                int currentLen;
                // if current length is greater than 9
                if (lens.getFirst().charAt(1) != ' ')
                    currentLen = Integer.parseInt(lens.getFirst().substring(0, 2));
                    // <= 9
                else
                    currentLen = Integer.parseInt(lens.getFirst().substring(0, 1));
                // when word length is equal to current arrays length, add word to array
                if (word.length() == currentLen)
                    lens.add(word);
            }
        }

        // add string version of each arraylist to string
        String str = "";
        for (ArrayList<String> lens : difLenWords) {
            // generate a stringBuilder with each word as well as the 'x letters:' part and add it
            // to final string
            StringBuilder formedList = new
                    StringBuilder(lens.toString().substring(1, lens.toString().length() - 1) + "\n");
            // delete the comma after the first index, so it doesn't say "x letters:,"
            for (int i = 0; i < formedList.length(); i++) {
                if (formedList.charAt(i) == ',') {
                    formedList.delete(i, i + 1);
                    break;
                }
            }
            str = str.concat(formedList.toString());
        }

        return str;
    }


    /**
     * displays all words in given puzzle sorted by length
     *
     * @return string of all words in puzzle
     */
    public String allWordsPresentByLength() {
        return "Here is a list of every word in this puzzle: \n"
                + formattedByLength(this.allWordsPresentArr());
    }

    /**
     * displays all words in puzzle sorted by letter
     *
     * @return string of all words in puzzle
     */
    public String allWordsPresentByLetter() {
        return "Here is a list of every word in this puzzle: \n"
                + formattedByLetter(this.allWordsPresentArr());
    }

    /**
     * finds number of english words in square
     *
     * @return total of english words
     */
    public String numOfWords() {
        return "This puzzle has " + allWordsPresentArr().size() + " total words to be found.";
    }

    /**
     * finds all words in puzzle of given length
     *
     * @param length given length
     * @return list of all words in puzzle of said length
     */
    public String wordsOfLength(int length) {
        ArrayList<String> arr = new ArrayList<>();
        String str = "";
        for (String word : this.allWordsPresentArr()) {
            if (word.length() == length) {
                arr.add(word);
                str = str.concat(word);
                str = str.concat(", ");
            }
        }
        if (str.isEmpty()) return this.longestWordLength();
        if (arr.size() == 1) return "The only word in this puzzle with "
                + length + " letters is: " + arr.getFirst();
        return "The words in this puzzle with " + length + " letters are: \n"
                + formattedByLetter(arr);
    }

    /**
     * finds number of words of given length
     *
     * @param length given length
     * @return int value of words of given length
     */
    public String numWordsOfLength(int length) {
        int count = 0;
        for (String word : this.allWordsPresentArr()) {
            if (word.length() == length) count++;
        }
        if (count == 1) return "There is 1 word in this puzzle with " + length + " letters.";
        return "There are " + count + " words in this puzzle with " + length + " letters.";
    }

    /**
     * finds longest word in given puzzle
     *
     * @return int value of longest word
     */
    public String longestWordLength() {
        int longest = 0;
        for (String word : this.allWordsPresentArr()) {
            if (word.length() > longest) longest = word.length();
        }
        return "The longest words in this puzzle are " + longest + " letters long.";
    }

    /**
     * finds all words starting with a given letter
     *
     * @param letter letter to look for
     * @return string containing all words starting with given letter
     */
    public String wordsOfLetter(char letter) {
        ArrayList<String> arr = new ArrayList<>();
        for (String word : this.allWordsPresentArr()) {
            if (word.charAt(0) == letter)
                arr.add(word);
        }
        if (arr.size() == 1)
            return "The only word in this puzzle starting with " + (char) (letter - 'a' + 'A')
                    + " is: " + arr.getFirst();
        return "The words starting with " + (char) (letter - 'a' + 'A') + " are: \n"
                + formattedByLength(arr);
    }

    /**
     * finds number of words that start with a given letter
     *
     * @param letter given letter
     * @return string with total number of words
     */
    public String numWordsOfLetter(char letter) {
        int count = 0;
        for (String word : this.allWordsPresentArr()) {
            if (word.charAt(0) == letter) count++;
        }
        if (count == 1) return "There is 1 word in this puzzle starting with "
                + (char) (letter - 'a' + 'A') + ".";
        return "There are " + count + " words starting with "
                + (char) (letter - 'a' + 'A') + ".";
    }

    /**
     * finds all words starting with a given letter that are of a given length
     *
     * @param letter given letter
     * @param length given length
     * @return list of words that match letter and length
     */
    public String wordsOfLetterAndLength(char letter, int length) {
        ArrayList<String> arr = new ArrayList<>();
        for (String word : this.allWordsPresentArr()) {
            if (word.charAt(0) == letter && word.length() == length) arr.add(word);
        }
        return formattedByLetter(arr);
    }

    /**
     * creates a new square that's been rotated 90° to the right
     *
     * @return rotated letter square
     */
    public LetterGrid turnBoard() {
        String str = "";
        for (int i = 1; i <= size; i++) {
            for (int j = size; j > 0; j--) {
                for (Node letter : letterList) {
                    if (letter.column == i && letter.row == j)
                        str = str.concat(String.valueOf(letter.letter));
                }
            }
        }
        return new LetterGrid(str);
    }
}
