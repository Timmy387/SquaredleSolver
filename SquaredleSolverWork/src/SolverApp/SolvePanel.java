package SolverApp;

import SquareLogic.LetterGrid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SolvePanel extends JPanel implements ActionListener{
    private final JTextArea input;
    private final JTextArea output;
    private final int action;
    private LetterGrid square;
    public SolvePanel(int columns, JTextArea output,
                      String label, String button, int action){
        this.output = output;
        this.action = action;
        this.input = new JTextArea(1, columns);
        this.input.setBackground(Color.LIGHT_GRAY);
        JLabel label1 = new JLabel(label);
        label1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        JButton button1 = new JButton(button);
        button1.addActionListener(this);

        add(label1);
        add(input);
        add(button1);
    }

    /**
     * sets square of this solve panel object
     * @param square square to be added
     */
    public void setSquare(LetterGrid square){
        this.square = square;
    }

    /**
     * when solve button is pressed, determines which button it was and calls respective solve method
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (square == null) {
            output.setFont(new Font("Times New Roman", Font.PLAIN, 25));
            output.setText("Please enter a properly formatted square above!");
        }
        else {
            switch (action){
                case 1: {
                    this.wordsOfLen();
                    break;
                }
                case 2:{
                    this.wordsWithLetter();
                    break;
                }
                default:{
                    this.wordsOfLenAndLetter();
                }
            }
        }

    }

    // actions to perform on button presses

    /**
     * prints all words of a given length to the output box
     */
    public void wordsOfLen(){
        int length = 0;
        try {
            length = Integer.parseInt(input.getText());
        } catch (NumberFormatException ignored) {

        }
        if (length < 4) {
            output.setFont(new Font("Times New Roman", Font.PLAIN, 25));
            output.setText("Please only enter a positive integer 4 or greater!");
        }
        else {
            output.setFont(new Font("Times New Roman", Font.PLAIN, 18));
            String stuff = square.numWordsOfLength(length);
            stuff = stuff.concat("\n");
            stuff = stuff.concat(square.wordsOfLength(length));
            output.setText(stuff);
        }
    }

    /**
     * prints all words beginning with a given letter to output
     */
    public void wordsWithLetter(){
        // find letter entered
        String typed = input.getText().toLowerCase();
        if (input.getText().length() == 1 && ((typed.charAt(0) >= 'a' && typed.charAt(0) <= 'z'))){
            output.setFont(new Font("Times New Roman", Font.PLAIN, 18));
            char letter = typed.charAt(0);
            String stuff = square.numWordsOfLetter(letter);
            stuff = stuff.concat("\n" + square.wordsOfLetter(letter));
            output.setText(stuff);
        }
        else {
            output.setFont(new Font("Times New Roman", Font.PLAIN, 25));
            output.setText("Please check for exactly one letter at a time!");
        }
    }
    public void wordsOfLenAndLetter(){

    }
}
