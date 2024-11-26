package SquareLogic;

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // create word square object

        int[] nums = new int[1000];
        for (int i = 0; i < 1000; i++){
            if (i % 2 == 0) nums[i] = 1;
            else nums[i] = -1;
        }
        System.out.println(Arrays.toString(leet(nums)));

        System.out.println("Today's square: ");
        LetterGrid todaysSquare = new LetterGrid("alkcionitemmargi");
        System.out.println(todaysSquare.numOfWords());
        todaysSquare.allWordsPresentByLength();
        System.out.println(todaysSquare.wordsOfLength(4));

        System.out.println("\nToday's Express: ");
        LetterGrid express = new LetterGrid("rdfooewwr");
        System.out.println(express.numOfWords());
        express.allWordsPresentByLength();
        System.out.println(express.wordsOfLetterAndLength('r', 4));

        System.out.println("\nWaffle Squaredle #2");
        LetterGrid waffle2 = new LetterGrid("smoket0u0lratiou0d0ptrope");
        System.out.println(waffle2.numOfWords());
        System.out.println(waffle2.allWordsPresentByLength());

        System.out.println("\nAbandon All Hope Puzzle: ");
        LetterGrid abandonAllHope = new
                LetterGrid("xyhwitsiincelnsecndseyguirottetlaspneitxsbthcatutjoilichlcioinrotalanctyakuyrltdiulnterizexenovequsa");
        // xyhwitsiin-celnsecnds-eyguirotte-tlaspneitx-sbthcatutj-oilichlcio-inrotalanc-tyakuyrltd-iulnterize-xenovequsa

        System.out.println("\nFire Hazard Puzzle: ");
        LetterGrid fireHazard = new LetterGrid("rgerirewat" +
                "uodjlfprbe" +
                "angokeoomr" +
                "ruracrrxoe" +
                "bdncfirebw" +
                "laerifbpot" +
                "emrbdryalu" +
                "uotseglctg" +
                "zshgifales" +
                "eaberework");

        LetterGrid arborDay = new LetterGrid("sprwalnuchwmnuchaztenoleaciensdlmirmkalutawbeapotmeroecdlprrgvahuoeyweonmagownhsrocywdoiacefsresrpef");
    }

    public static String randomLetters(int size){
        String str = "";
        Random thisChar = new Random();
        // add (size * size) random letters to array
        for (int i = 0; i < size * size; i++){
                char ch = (char) thisChar.nextInt('a', 'z' + 1);
                if (ch == 'x' || ch == 'z' || ch == 'q' || ch == 'k') str = str.concat("e");
                else str = str.concat(String.valueOf(ch));
        }
        return str;
    }

    public static int[] leet(int[] nums) {
        int[] answer = new int[nums.length];
        for (int i = 0; i < nums.length; i++){
            answer[i] = 1;
            for (int j = 0; j < nums.length; j++){
                if (j != i)
                    answer[i] = answer[i] * nums[j];
            }
        }
        return answer;
    }
}
