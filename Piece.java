package main;

import java.awt.*;
import java.util.*;

public class Piece {
    
    private Set<Point> points;
    private Point position;
    private Color color;
    
    public Piece(Set<Point> points, Color color) {
        this.points = new HashSet<>(points);
        position = new Point(0, 0);
        this.color = color;
    }
    
    public Piece(Set<Point> points, Color color, Point position) {
        this.points = new HashSet<>(points);
        this.position = new Point(position);
        this.color = color;
    }
    
    public Piece(DefaultPiece piece) {
    	points = new HashSet<>(piece.getPoints());
    	position = new Point(0, 0);
    	color = piece.getColor();
    }
    
    public Piece(Piece piece) {
    	points = new HashSet<>(piece.getPoints());
    	position = new Point(piece.getPosition());
    	color = piece.getColor();
    }
    
    public Color getColor() {
    	return color;
    }
    
    public Set<Point> getPoints() {
        return points;
    }
    
    public Point getPosition() {
    	return position;
    }
    
    @Override
    public String toString() {
        String finalString = "Position: {x: " + position.x + ", y: " + position.y + "}";
        
        for(Point point : points) finalString += ", Point: {x: " + point.x + ", y: " + point.y + "}";
        
        return finalString;
    }
    
    public Piece getRotatedPiece(int rotation) {
        Set<Point> points = new HashSet<>();
        
        for(Point point : this.points) points.add(new Point(-point.y, point.x));
        
        if(rotation > 1) return new Piece(points, color, position).getRotatedPiece(rotation - 1);
        
        return new Piece(points, color, position);
    }
    
    public static Piece getRandom() {
    	return DefaultPiece.valueOf("PIECE" + (int) (Math.random() * 7 + 1)).getPiece();
    }
    
    public void moveTo(int x, int y) {
    	position = new Point(x, y);
    }
    
    public void translate(int x, int y) {
    	position.x += x;
    	position.y += y;
    }
    
    public int getX() {
    	return position.x;
    }
    
    public int getY() {
    	return position.y;
    }
    
    public int getWidth() {
    	int highest = 0;
    	int lowest = 0;
    	boolean isFirst = true;
    	
    	for(Point point : points) {
    		if(isFirst) {
    			highest = point.x;
    			lowest = point.x;
    			
    			isFirst = false;
    			continue;
    		}
    		
    		highest = Math.max(highest, point.x);
    		lowest = Math.min(lowest, point.x);
    	}
    	
    	return highest - lowest;
    }
    
}