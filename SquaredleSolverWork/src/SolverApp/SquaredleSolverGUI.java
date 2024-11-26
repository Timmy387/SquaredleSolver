package SolverApp;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import SquareLogic.*;

public class SquaredleSolverGUI extends JPanel implements ActionListener, KeyListener {
    JTextArea generalInput;
    JTextArea output;
    private String lettersForSquare;
    private final SolvePanel wordLengthPanel, startLetterPanel;
    private final GeneralInput gen;
    private final JButton instruct;
    protected LetterGrid square;
    public SquaredleSolverGUI(){

        // title definition
        JLabel header = new JLabel("Squaredle Solver");
        header.setFont(new Font("Times New Roman", Font.BOLD, 30));
        header.setBackground(Color.DARK_GRAY);
        header.setForeground(Color.white);
        header.setOpaque(true);
        header.setBorder(new BevelBorder(BevelBorder.RAISED));

        // instruct button
        instruct = new JButton("Instructions");
        instruct.addActionListener(this);

//        Scanner scan = new Scanner(System.in);
//        System.out.println("Enter the puzzle dimension: ");
//        int puzSize = scan.nextInt();
//        JTextArea input = new JTextArea(puzSize, puzSize);
        // general input area
        generalInput = new JTextArea(1, 55);
        generalInput.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        generalInput.addKeyListener(this);

        // output box formatting
        output = new JTextArea();
        output.setLineWrap(true);
        output.setWrapStyleWord(true);
        output.setEditable(false);
        output.setSelectionColor(Color.gray);
        output.setSelectedTextColor(Color.white);
        output.setForeground(Color.white);
        output.setBackground(Color.darkGray);
        JScrollPane scroll = new JScrollPane(output);
        Dimension dim = new Dimension(900, 530);
        scroll.setPreferredSize(dim);
        this.writeInstructions();

        // create a generalInput obj
        gen = new GeneralInput(generalInput, output);

        // define a solve panel for word lengths
        wordLengthPanel =
                new SolvePanel(2, output,
                        "Find words with this length: ",
                        "Find!", 1);

        // define a solve panel for starting letters
        startLetterPanel =
                new SolvePanel(2, output,
                        "Find words with this letter: ",
                        "Find!", 2);
        // add each component
        add(header);
        add(instruct);
        add(gen);
        add(wordLengthPanel);
        add(startLetterPanel);
        add(scroll);
    }

    /**
     * when the 'instructions' button is pressed, call
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(instruct.getActionCommand()))
            writeInstructions();
    }

    /**
     * prints instructions on using the solver to the output box
     */
    public void writeInstructions(){
        output.setText("""
                How to use solver:
                
                Type each letter in your square into the text box labeled "Solve
                full puzzle," in order from left to right then from top to bottom.
                
                To find words of a given length, type any integer value four or greater
                into the box labeled "Find words of this length."
                
                To find words starting with a given letter, type one letter
                at a time into the box labeled "Find words with this letter."
                
                The Solve button displays every word in the puzzle sorted by length;
                the sort by letter button sorts them by letter. (duh!)
                
                Click the respective Find buttons to find the specific words you are
                looking for.
                
                The words you searched for will appear in this box.""");
        Font f = new Font("Times New Roman", Font.PLAIN, 24);
        output.setFont(f);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
//        lettersForSquare = this.generalInput.getText();
//        if (e.getKeyCode() == '\b') output.setText("");
//        else if (Math.sqrt(lettersForSquare.length() + 1) % 1 == 0 && lettersForSquare.length() >= 3){
//            output.setFont(new Font("Times New Roman", Font.PLAIN, 30));
//            output.setText("Calculating...");
//        }
//        else output.setText("");
    }

    /**
     * Invoked when a key has been released.
     * When a square number of letters has been entered, begin creating and solving square right away
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        lettersForSquare = this.generalInput.getText();
        if (Math.sqrt(lettersForSquare.length()) % 1 == 0 && lettersForSquare.length() > 3){
//            output.setFont(new Font("Times New Roman", Font.PLAIN, 30));
            setSquares();
//            output.setText("Done calculating, press any button to solve! " +
//                    "\nOr keep typing for a bigger puzzle! ");
        }
    }

    /**
     * sets the squares of each solve panel to the main square
     */
    public void setSquares(){
        square = new LetterGrid(this.lettersForSquare);
        gen.setSquare(square);
        wordLengthPanel.setSquare(square);
        startLetterPanel.setSquare(square);
    }
}
