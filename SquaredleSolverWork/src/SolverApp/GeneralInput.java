package SolverApp;

import SquareLogic.LetterGrid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GeneralInput extends JPanel implements ActionListener{
    private final JTextArea input;
    private final JTextArea output;
    private LetterGrid square;
    private final JButton button1;
    public GeneralInput(JTextArea input, JTextArea output){
        this.output = output;
        this.input = input;
        this.input.setBackground(Color.LIGHT_GRAY);
        JLabel label1 = new JLabel("Solve full puzzle: ");
        label1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        button1 = new JButton("Solve!");
        button1.addActionListener(this);
        JButton button2 = new JButton("Sort by letter");
        button2.addActionListener(this);

        add(label1);
        add(input);
        add(button1);
        add(button2);
    }

    /**
     * action listener sets the command for pressing the general input button (solve full puzzle)
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String typed = input.getText().toLowerCase();
        if (Math.sqrt(typed.length()) % 1 != 0 && !typed.isEmpty()) {
            output.setFont(new Font("Times New Roman", Font.PLAIN, 25));
            output.setText("Please ensure this is a perfect square! (4, 9, 16, 25, 36 letters etc.)" +
                    "\nYour entry has " + typed.length() + " letters, which is not a perfect square. ");
        }
        else if (this.square == null || typed.length() < 4) {
            output.setFont(new Font("Times New Roman", Font.PLAIN, 25));
            output.setText("Please enter a square with at least four letters to solve!");
        }
        else {
            output.setFont(new Font("Times New Roman", Font.PLAIN, 17));
            String stuff = square.numOfWords();
            stuff = stuff.concat("\n");
            if (e.getActionCommand().equals(button1.getActionCommand()))
                stuff = stuff.concat(square.allWordsPresentByLength());
            else stuff = stuff.concat(square.allWordsPresentByLetter());
            output.setText(stuff);
        }
    }

    /**
     * sets the square of this general input object
     * @param square square to be added
     */
    public void setSquare(LetterGrid square){
        this.square = square;
    }
}
