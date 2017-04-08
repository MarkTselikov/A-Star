import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by MarkTselikov on 23.03.17.
 */
public class Cell  extends JPanel {

    private int xCoord;
    private int yCoord;
    private int index;
    private int gScore;
    private double heuristicsScore;
    private double fScore;
    private Cell previous;

    private boolean isStart;
    private boolean isGoal;
    private boolean isObstacle;

    private ArrayList <Cell> adjacentCells;


    public Cell(int xCoord, int yCoord, int index) {

        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.index = index;
        this.gScore = 1;

        this.isStart = false;
        this.isGoal = false;
        this.isObstacle = false;

        this.previous = null;
        this.adjacentCells = new ArrayList <Cell>();
    }


    public int getxCoord() {
        return xCoord;
    }


    public int getyCoord() {
        return yCoord;
    }


    public int getIndex() { return index; }


    public int getgScore() { return gScore; }


    public void setgScore(int gScore) { this.gScore = gScore; }


    public double getHeuristicsScore() { return heuristicsScore; }


    public void setHeuristicsScore(double h) { heuristicsScore = h; }


    public double getfScore() { return fScore; }


    public void calcfScore() { fScore = gScore + heuristicsScore; }


    public Cell getPrevious() { return previous; }


    public void setPrevious(Cell previous) { this.previous = previous;}


    public boolean isStart() {
        return isStart;
    }


    public boolean isGoal() {
        return isGoal;
    }


    public boolean isObstacle() {
        return isObstacle;
    }


    public void setStart(boolean value) {
        isStart = value;
    }


    public void setGoal(boolean value) {
        isGoal = value;
    }


    public void setObstacle(boolean value) {
        isObstacle = value;
    }


    public ArrayList <Cell> getAdjacentCells() {
        return adjacentCells;
    }


    public  void initAdjacentPts(Cell [][] cells) {

        if(yCoord != 0) {
            adjacentCells.add(cells [xCoord][yCoord - 1]);
            //if(xCoord != 0) { adjacentCells.add(cells [xCoord - 1][yCoord - 1]); }
            //if(xCoord != cells.length - 1) { adjacentCells.add(cells [xCoord + 1][yCoord - 1]); }
        }
        if(xCoord != cells.length - 1) {
            adjacentCells.add(cells [xCoord + 1][yCoord]);
            //if(yCoord != 0) { adjacentCells.add(cells [xCoord + 1][yCoord - 1]); }
            //if(yCoord != cells[0].length - 1) { adjacentCells.add(cells [xCoord + 1][yCoord + 1]); }
        }
        if(yCoord != cells[0].length - 1) {
            adjacentCells.add(cells [xCoord][yCoord + 1]);
        }
        if(xCoord != 0) {
            adjacentCells.add(cells [xCoord - 1][yCoord]);
        }
    }
}
