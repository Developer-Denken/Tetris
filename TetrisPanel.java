package main;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class TetrisPanel extends JPanel {
	private static final long serialVersionUID = 3796489528603215215L;
	
	private TetrisGame game;
	private Thread gameThread;
	private Thread panelUpdateThread;
	
	public TetrisPanel() {
		panelUpdateThread = new Thread(this::GUIUpdate);
		game = new TetrisGame(panelUpdateThread);
		
		SwingUtilities.invokeLater(this::initPanel);
        
        gameThread = new Thread(game);
        gameThread.setDaemon(true);
        gameThread.start();
	}
	
	public void initPanel() {
		addKeyListener(game);
		grabFocus();
		setSize(400, 400);
		setOpaque(true);
	}
	
	public void GUIUpdate() {
		SwingUtilities.invokeLater(this::repaint);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		game.draw((Graphics2D) g);
	}
	
}
