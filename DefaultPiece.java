package main;

import java.util.*;
import java.awt.*;

public enum DefaultPiece {
    
    PIECE1(Color.blue, new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 0)),
    PIECE2(Color.green, new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(0, 3)),
    PIECE3(Color.red, new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1)),
    PIECE4(Color.yellow, new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1)),
    PIECE5(Color.magenta, new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(1, 2)),
    PIECE6(Color.orange, new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2)),
    PIECE7(Color.cyan, new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(1, 1));
    
    private final Set<Point> points;
    private final Color color;
    
    DefaultPiece(Color color, Point... points) {
        this.points = new HashSet<>();
        this.color = color;
        
        for(Point point : points) this.points.add(point);
    }
    
    public Set<Point> getPoints() {
        return points;
    }
    
    public Color getColor() {
    	return color;
    }
    
    public Piece getPiece() {
        return new Piece(points, color);
    }
    
    @Override
    public String toString() {
        String finalString = "";
        boolean notFirst = false;
        
        for(Point point : points) {
            if(notFirst) finalString += ", ";
            notFirst = true;
            
            finalString += "Point: {x: " + point.x + ", y: " + point.y + "}";
        }
        
        return finalString;
    }
    
}