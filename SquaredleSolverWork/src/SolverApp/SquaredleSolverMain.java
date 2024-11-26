package SolverApp;

import javax.swing.*;
import java.awt.*;

public class SquaredleSolverMain {
    public static void playGame() {
        JFrame frame = new JFrame("Squaredle");
        frame.setSize(1000, 725);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new SquaredleSolverGUI());

        frame.setVisible(true);
    }

    public static void main(String[] args){
        playGame();
    }
}
