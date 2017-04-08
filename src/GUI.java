import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by MarkTselikov on 23.03.17.
 */
public class GUI {

    private final int X_RES = 800;
    private final int Y_RES = 700;

    private boolean isStartSet = false;
    private boolean isEndSet = false;

    private Cell start;
    private Cell end;

    private Cell [][] grid;


    public GUI()
    {
        JFrame mainFrame = new JFrame();
        mainFrame.setLayout(new BorderLayout());

        // Creating control panel elements
        JPanel controlsPan = new JPanel(new GridLayout(5, 1));
        JLabel controlsLab = new JLabel("Select ...");      //  <- edit
        JRadioButton startRB = new JRadioButton("Select Start");
        JRadioButton endRB = new JRadioButton("Select End");
        JRadioButton obstRB = new JRadioButton("Create Obstacles");
        JButton startB = new JButton("Start");

        ButtonGroup controlRBGroup = new ButtonGroup();
        controlRBGroup.add(startRB);
        controlRBGroup.add(endRB);
        controlRBGroup.add(obstRB);

        // Adding control items to one panel
        controlsPan.add(controlsLab);
        controlsPan.add(startRB);
        controlsPan.add(endRB);
        controlsPan.add(obstRB);
        controlsPan.add(startB);

        // Setting up the size of the grid
        String input = JOptionPane.showInputDialog(null, "How big will be the grid?", "Grid prompt",
                JOptionPane.PLAIN_MESSAGE, null, null, null).toString();
        int gridSize = Integer.parseInt(input);
        grid = new Cell[gridSize][gridSize];

        // creating cells
        JPanel gridPan = new JPanel(new GridLayout(gridSize, gridSize));
        int counter = 0;
        for(int i = 0; i < gridSize; i++) {
            for(int j = 0; j < gridSize; j++) {

                Cell aCell = new Cell(i, j, counter);
                aCell.setBackground(Color.WHITE);
                aCell.setBorder(BorderFactory.createLineBorder(Color.black));
                gridPan.add(aCell);
                grid[i][j] = aCell;
                counter++;
            }
        }
        System.out.println("Cells created successfully \n");

        // Initializing cells
        for(int i = 0; i < gridSize; i++) {
            for(int j = 0; j < gridSize; j++) {
                grid[i][j].initAdjacentPts(grid);
                grid[i][j].addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Cell clickedCell = (Cell)e.getSource();

                        // depending on mode that is selected we modify the grid
                        if(startRB.isSelected() && !clickedCell.isObstacle()) {
                            // check if any cell was selected before
                            if(start != null) { grid [start.getxCoord()][start.getyCoord()].setBackground(Color.WHITE); }
                            start = clickedCell;
                            start.setBackground(Color.BLUE);
                            isStartSet = true;
                        }

                        else if(endRB.isSelected() && !clickedCell.isObstacle()) {
                            if(end != null) { grid [end.getxCoord()][end.getyCoord()].setBackground(Color.WHITE); }
                            end = clickedCell;
                            end.setBackground(Color.CYAN);
                            isEndSet = true;
                        }

                        else if(obstRB.isSelected()) {
                            if(clickedCell.isObstacle()) {
                                grid [clickedCell.getxCoord()][clickedCell.getyCoord()].setObstacle(false);
                                grid [clickedCell.getxCoord()][clickedCell.getyCoord()].setBackground(Color.WHITE);
                            }

                            else {
                                grid [clickedCell.getxCoord()][clickedCell.getyCoord()].setObstacle(true);
                                grid [clickedCell.getxCoord()][clickedCell.getyCoord()].setBackground(Color.DARK_GRAY);
                            }
                        }
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {}

                    @Override
                    public void mouseReleased(MouseEvent e) {}

                    @Override
                    public void mouseEntered(MouseEvent e) {}

                    @Override
                    public void mouseExited(MouseEvent e) {}
                });
            }
        }
        System.out.println("Cells initialization complete successfully \n");

        startB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isStartSet && isEndSet) {
                    AStar pathfinder = new AStar(start, end);
                    ArrayList <Cell> path = pathfinder.findPath();

                    if(!path.isEmpty()) {
                        for(Cell cell : path) {
                            grid[cell.getxCoord()][cell.getyCoord()].setBackground(Color.GREEN);
                        }
                    }
                    else { JOptionPane.showMessageDialog(null, "No path found"); }
                }
                else if(!isStartSet) { JOptionPane.showMessageDialog(null, "Select the start first"); }
                else if(!isEndSet) { JOptionPane.showMessageDialog(null, "Select the goal first"); }
            }
        });

        // Adding panels to the main frame
        mainFrame.add(controlsPan, BorderLayout.EAST);
        mainFrame.add(gridPan, BorderLayout.CENTER);

        // Configuration of the main frame
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(X_RES, Y_RES);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }
}
