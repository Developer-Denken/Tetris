package main;

import java.awt.*;
import javax.swing.*;

public class TetrisFrame extends JFrame {
	private static final long serialVersionUID = 6051068036091148694L;
    
    public TetrisFrame() {
        setUndecorated(true);
        setSize(400, 400);
        getContentPane().setBackground(Color.black);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        add(new TetrisPanel());
    }
    
}