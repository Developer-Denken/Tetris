package main;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TetrisGame implements Runnable, KeyListener {
    
	private final ExecutorService service = Executors.newCachedThreadPool();
	private Piece currentPiece;
	private int fps;
	private int delay;
	private Set<Piece> board;
	private Thread updateGUI;
	
    public TetrisGame(Thread updateGUI) {
    	fps = 1;
    	delay = (int) ((double) 1000 / fps);
    	board = new HashSet<>();
    	
    	currentPiece = Piece.getRandom();
    	currentPiece.moveTo((int) (4 - currentPiece.getWidth() / 2), -5);
    	
    	this.updateGUI = updateGUI;
    }
    
    private void tick() {
    	attemptMove(0, 1);
    }
    
    public void nextPiece(int x, int y) {
    	currentPiece.translate(-x, -y);
		board.add(new Piece(currentPiece));
		
		currentPiece = Piece.getRandom();
		currentPiece.moveTo((int) (4 - currentPiece.getWidth() / 2), -5);
    }
    
    public boolean checkOverlap(Piece p1, Piece p2) {
    	Point p1Position = p1.getPosition();
    	Point p2Position = p2.getPosition();
    	
    	for(Point point1 : p1.getPoints()) for(Point point2 : p2.getPoints()) if((point1.x + p1Position.x == point2.x + p2Position.x && point1.y + p1Position.y == point2.y + p2Position.y)) return true;
    	return false;
    }
    
    public void draw(Graphics2D paint) {
    	
    	paint.setColor(currentPiece.getColor());
    	for(Point point : currentPiece.getPoints()) paint.fillRect((point.x + currentPiece.getX()) * 40, (point.y + currentPiece.getY()) * 40, 40, 40);
    	
    	for(Piece piece : board) {
    		paint.setColor(piece.getColor());
    		
    		for(Point point : piece.getPoints()) paint.fillRect((point.x + piece.getX()) * 40, (point.y + piece.getY()) * 40, 40, 40);
    	}
    }
    
    @Override
    public void run() {
        System.out.println("Game Started!");
        
        do {
        	long startTime = System.currentTimeMillis();
        	
        	tick();
        	
        	long endTime = System.currentTimeMillis();
        	long delayTime = delay - endTime + startTime;
        	
        	if(delayTime < 0) delayTime = 0;
        	
        	try {
        		
        		Thread.sleep(delayTime);
        		
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        } while(true);
    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int code = e.getKeyCode();
		
		if(code == 27) {
			System.out.println("Game Over!");
			System.exit(0);
		}
		
		if(code == 65 || code == 37) attemptMove(-1, 0);
		else if(code == 68 || code == 39) attemptMove(1, 0);
		else if(code == 83 || code == 40) attemptMove(0, 1);
		else if(code == 87 || code == 38) attemptRotate();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void attemptMove(int x, int y) {
		currentPiece.translate(x, y);
    	
    	boolean yOutOfBounds = false;
    	boolean xOutOfBounds = false;
    	for(Point point : currentPiece.getPoints()) if(point.y + currentPiece.getPosition().y > 9) yOutOfBounds = true;
    	for(Point point : currentPiece.getPoints()) if(point.x + currentPiece.getPosition().x > 9 || point.x + currentPiece.getPosition().x < 0) xOutOfBounds = true;
    	
    	if(!yOutOfBounds && !xOutOfBounds) {	
	    	for(Piece piece : board) {
	    		if(!checkOverlap(piece, currentPiece)) continue;
	    		
	    		if(x > 0) {
	    			currentPiece.translate(-x, 0);
	    			break;
	    		}
	    		
	    		nextPiece(x, y);
	    		break;
	    	}
    	} else if(!yOutOfBounds) currentPiece.translate(-x, 0);
		else nextPiece(x, y);
    	
    	service.execute(updateGUI);
	}
    
	private void attemptRotate() {
		currentPiece = currentPiece.getRotatedPiece(1);
		
		boolean yOutOfBounds = false;
    	boolean xOutOfBounds = false;
    	for(Point point : currentPiece.getPoints()) if(point.y + currentPiece.getPosition().y > 9) yOutOfBounds = true;
    	for(Point point : currentPiece.getPoints()) if(point.x + currentPiece.getPosition().x > 9 || point.x + currentPiece.getPosition().x < 0) xOutOfBounds = true;
    	
    	if(!yOutOfBounds && !xOutOfBounds) {	
	    	for(Piece piece : board) {
	    		if(!checkOverlap(piece, currentPiece)) continue;

	    		currentPiece = currentPiece.getRotatedPiece(3);
	    		break;
	    	}
    	} else currentPiece = currentPiece.getRotatedPiece(3);
    	
    	service.execute(updateGUI);
	}
}
