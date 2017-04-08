import java.util.ArrayList;

/**
 * Created by MarkTselikov on 24.03.17.
 */
public class AStar {

    private boolean solutionExists;

    private Cell start;
    private Cell goal;

    private ArrayList<Cell> openSet;
    private ArrayList <Cell> closedSet;


    public AStar(Cell start, Cell goal) {

        this.start = start;
        this.goal = goal;
        this.solutionExists = false;

        openSet = new ArrayList<Cell>();
        closedSet = new ArrayList<Cell>();

        openSet.add(start);
    }


    public ArrayList <Cell> findPath() {

        Cell current = start;
        current.setgScore(0);
        current.setHeuristicsScore(rawDistance(current, goal));
        current.calcfScore();

        // ===== this part might be changed to show each step at gui ======
        while(!openSet.isEmpty()) {

            if(current == goal) { solutionExists = true; }

            Cell[] neighbors = current.getAdjacentCells().toArray(new Cell[0]);
            for(Cell neighbor : neighbors) {
                if(!closedSet.contains(neighbor) && !neighbor.isObstacle()) {

                    neighbor.setHeuristicsScore(rawDistance(neighbor, goal));
                    neighbor.setPrevious(current);

                    int gTemp = current.getgScore() + neighbor.getgScore();
                    if(gTemp < neighbor.getgScore()) { neighbor.setgScore(gTemp); }

                    neighbor.calcfScore();
                    if(!openSet.contains(neighbor)) { openSet.add(neighbor); }

                }
            }

            // vvv | Testing
            for(Cell cell : neighbors) { System.out.println("Neighbors' " + cell.getIndex() + " F Score: " + cell.getfScore()); }
            System.out.println("Open Set: ");
            for(Cell cell : openSet) { System.out.println(cell.getxCoord() + " " + cell.getyCoord()); }
            // ^^^

            Cell bestCandidate = openSet.get(0);
            for(Cell cell : openSet) {
                if(cell.getfScore() < bestCandidate.getfScore()) {
                    bestCandidate = cell;
                }
            }

            closedSet.add(bestCandidate);
            // this is probably the worst solution I could have came up with
            for(int i = 0; i < openSet.size(); i++) {
                if(openSet.get(i) == bestCandidate) { openSet.remove(i); }
            }

            current = bestCandidate;
            // vvv | Testing
            System.out.println("Finished a circle with " + current.getIndex());
            System.out.println("======================");
            // ^^^
        }

        // If nothing is found an empty array list is returned
        if(solutionExists) {
            ArrayList <Cell> path = new ArrayList<Cell>();
            Cell tempCell = goal.getPrevious();
            while (tempCell != start) {
                path.add(tempCell);
                tempCell = tempCell.getPrevious();
            }
            return path;
        }
        else {
            System.out.println("Could not find a path ");
            return new ArrayList<>();
        }
    }


    /**
     * Method that calculates raw distance between two cells
     * @param a The first cell
     * @param b The second cell
     * @return The raw distance
     */
    public double rawDistance(Cell a, Cell b) {

        double xDist = Math.abs(b.getxCoord() - a.getxCoord());
        double yDist = Math.abs(b.getyCoord() - a.getyCoord());

        return Math.sqrt( Math.pow(xDist, 2) + Math.pow(yDist, 2) );
    }


    public ArrayList <Cell> getClosedSet() {
        return closedSet;
    }

}
