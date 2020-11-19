package NineMensMorris;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import javafx.scene.paint.Color;

// Cells make up the 7x7 grid on the board of playable and non-playable places
public class Cell extends Pane {
    //This maps the GUI's coordinate system with the GamePlay's coordinate system
    //Also used for finding all the playable locations
    private static HashMap<Point, Point> coordTable = null;

    //This is used to find a particular cell based on its pair attribute
    public static HashMap<Point, Cell> pairToCell = new HashMap<>();

    //Instance variables
    private Point myPair = new Point (-99,-99); //add pair for valid space position
    private boolean availableSpace = false; // This indicates if you can move a piece here
    public Ellipse visualPiece = new Ellipse(this.getWidth() / 3, this.getHeight() / 3,
            this.getWidth(), this.getHeight());

    // Cell constructor checks if it is a validSpace and initializes the attributes for this cell
    public Cell(int i, int j){
        if (coordTable == null) { InitCoordTable(); } //Only run once
        initializeDrawings(i, j);
        this.setPrefSize(150, 150); // sets default cell size (refactor sometime!)

        //If this cell is a valid playable intersection:
        if (checkValidSpace(i, j)) {
            //Assign myPair via coordTable and map this cell to that pair in pairToCell HashMap
            this.myPair = new Point(getCoords(i, j));
            pairToCell.put(myPair, this);

            //Add Event Handlers
            this.setOnMouseEntered(e -> hoverHighlightCell());
            this.setOnMouseExited(e -> undoHoverHighlight());
            this.setOnMouseClicked(this::handleClick);

            //Setup the properties for the Ellipse in this Cell
            visualPiece.centerXProperty().bind(this.widthProperty().divide(2));
            visualPiece.centerYProperty().bind(this.heightProperty().divide(2));
            visualPiece.radiusXProperty().bind(this.widthProperty().divide(3));
            visualPiece.radiusYProperty().bind(this.heightProperty().divide(3));
            visualPiece.setStroke(javafx.scene.paint.Color.BLACK);
            visualPiece.setVisible(false);
            getChildren().add(visualPiece);
        }
    }
    //This Table has the GUI grid coords as the keys and playing coords as values
    //For transforming between the two systems
    private static void InitCoordTable() {
        coordTable = new HashMap<Point, Point>();

        coordTable.put(new Point(0, 0), new Point(2, 0));
        coordTable.put(new Point(0, 3), new Point(2, 7));
        coordTable.put(new Point(0, 6), new Point(2, 6));

        coordTable.put(new Point(1, 1), new Point(1, 0));
        coordTable.put(new Point(1, 3), new Point(1, 7));
        coordTable.put(new Point(1, 5), new Point(1, 6));

        coordTable.put(new Point(2, 2), new Point(0, 0));
        coordTable.put(new Point(2, 3), new Point(0, 7));
        coordTable.put(new Point(2, 4), new Point(0, 6));

        coordTable.put(new Point(3, 0), new Point(2, 1));
        coordTable.put(new Point(3, 1), new Point(1, 1));
        coordTable.put(new Point(3, 2), new Point(0, 1));
        coordTable.put(new Point(3, 4), new Point(0, 5));
        coordTable.put(new Point(3, 5), new Point(1, 5));
        coordTable.put(new Point(3, 6), new Point(2, 5));

        coordTable.put(new Point(4, 2), new Point(0, 2));
        coordTable.put(new Point(4, 3), new Point(0, 3));
        coordTable.put(new Point(4, 4), new Point(0, 4));

        coordTable.put(new Point(5, 1), new Point(1, 2));
        coordTable.put(new Point(5, 3), new Point(1, 3));
        coordTable.put(new Point(5, 5), new Point(1, 4));

        coordTable.put(new Point(6, 0), new Point(2, 2));
        coordTable.put(new Point(6, 3), new Point(2, 3));
        coordTable.put(new Point(6, 6), new Point(2, 4));
    }

    //This whole switch initializes the drawings for the visual board for a new game
    private void initializeDrawings(int i, int j) {
        this.setStyle("-fx-background-color: #afc1cc; -fx-text-fill: white;");
        switch (i) {
            case 0:
                if (j == 1 || j == 2 || j == 4 || j == 5) {
                    Line line1 = new Line();

                    //Can we separate the drawing code and the gameplay code?
                    line1.startXProperty().bind(this.widthProperty().divide(2));
                    line1.endXProperty().bind(this.widthProperty().divide(2));
                    line1.endYProperty().bind(this.heightProperty());

                    getChildren().add(line1);
                } else if (j == 0) {
                    Line line1 = new Line();
                    line1.startXProperty().bind(this.widthProperty().divide(2));
                    line1.startYProperty().bind(this.heightProperty().subtract(this.getHeight()));
                    line1.endXProperty().bind(this.widthProperty().divide(2));
                    line1.endYProperty().bind(this.heightProperty().divide(2));

                    Line line2 = new Line();
                    line2.startXProperty().bind(this.widthProperty().divide(2));
                    line2.startYProperty().bind(this.heightProperty().divide(2));
                    line2.endXProperty().bind(this.widthProperty());
                    line2.endYProperty().bind(this.heightProperty().divide(2));

                    getChildren().addAll(line1, line2);
                } else if (j == 3) {
                    Line line1 = new Line(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
                    line1.startXProperty().bind(this.widthProperty().divide(2));
                    line1.endXProperty().bind(this.widthProperty().divide(2));
                    line1.endYProperty().bind(this.heightProperty());

                    Line line2 = new Line();
                    line2.startXProperty().bind(this.widthProperty().divide(2));
                    line2.startYProperty().bind(this.heightProperty().divide(2));
                    line2.endXProperty().bind(this.widthProperty());
                    line2.endYProperty().bind(this.heightProperty().divide(2));

                    getChildren().addAll(line1, line2);
                } else {
                    Line line1 = new Line(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
                    line1.startXProperty().bind(this.widthProperty().divide(2));
                    line1.endXProperty().bind(this.widthProperty().divide(2));
                    line1.endYProperty().bind(this.heightProperty().divide(2));

                    Line line2 = new Line();
                    line2.startXProperty().bind(this.widthProperty().divide(2));
                    line2.startYProperty().bind(this.heightProperty().divide(2));
                    line2.endXProperty().bind(this.widthProperty());
                    line2.endYProperty().bind(this.heightProperty().divide(2));

                    getChildren().addAll(line1, line2);
                }
                break;
            case 1:
                if (j == 0 || j == 6) {
                    Line line1 = new Line(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
                    line1.startYProperty().bind(this.heightProperty().divide(2));
                    line1.endXProperty().bind(this.widthProperty());
                    line1.endYProperty().bind(this.heightProperty().divide(2));
                    getChildren().add(line1);
                } else if (j == 1) {
                    Line line1 = new Line();
                    line1.startXProperty().bind(this.widthProperty().divide(2));
                    line1.startYProperty().bind(this.heightProperty().subtract(this.getHeight()));
                    line1.endXProperty().bind(this.widthProperty().divide(2));
                    line1.endYProperty().bind(this.heightProperty().divide(2));

                    Line line2 = new Line();
                    line2.startXProperty().bind(this.widthProperty().divide(2));
                    line2.startYProperty().bind(this.heightProperty().divide(2));
                    line2.endXProperty().bind(this.widthProperty());
                    line2.endYProperty().bind(this.heightProperty().divide(2));

                    getChildren().addAll(line1, line2);
                } else if (j == 2 || j == 4) {
                    Line line1 = new Line();
                    line1.startXProperty().bind(this.widthProperty().divide(2));
                    line1.endXProperty().bind(this.widthProperty().divide(2));
                    line1.endYProperty().bind(this.heightProperty());
                    getChildren().add(line1);
                } else if (j == 3) {
                    Line line1 = new Line(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
                    line1.startXProperty().bind(this.widthProperty().divide(2));
                    line1.endXProperty().bind(this.widthProperty().divide(2));
                    line1.endYProperty().bind(this.heightProperty());

                    Line line2 = new Line();
                    line2.startYProperty().bind(this.heightProperty().divide(2));
                    line2.endXProperty().bind(this.widthProperty());
                    line2.endYProperty().bind(this.heightProperty().divide(2));

                    getChildren().addAll(line1, line2);
                } else {
                    Line line1 = new Line(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
                    line1.startXProperty().bind(this.widthProperty().divide(2));
                    line1.endXProperty().bind(this.widthProperty().divide(2));
                    line1.endYProperty().bind(this.heightProperty().divide(2));

                    Line line2 = new Line();
                    line2.startXProperty().bind(this.widthProperty().divide(2));
                    line2.startYProperty().bind(this.heightProperty().divide(2));
                    line2.endXProperty().bind(this.widthProperty());
                    line2.endYProperty().bind(this.heightProperty().divide(2));

                    getChildren().addAll(line1, line2);
                }

                break;

            case 2:
                if (j == 0 || j == 1 || j == 5 || j == 6) {
                    Line line1 = new Line(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
                    line1.startYProperty().bind(this.heightProperty().divide(2));
                    line1.endXProperty().bind(this.widthProperty());
                    line1.endYProperty().bind(this.heightProperty().divide(2));
                    getChildren().add(line1);
                } else if (j == 2) {
                    Line line1 = new Line();
                    line1.startXProperty().bind(this.widthProperty().divide(2));
                    line1.startYProperty().bind(this.heightProperty().subtract(this.getHeight()));
                    line1.endXProperty().bind(this.widthProperty().divide(2));
                    line1.endYProperty().bind(this.heightProperty().divide(2));

                    Line line2 = new Line();
                    line2.startXProperty().bind(this.widthProperty().divide(2));
                    line2.startYProperty().bind(this.heightProperty().divide(2));
                    line2.endXProperty().bind(this.widthProperty());
                    line2.endYProperty().bind(this.heightProperty().divide(2));

                    getChildren().addAll(line1, line2);
                } else if (j == 3) {
                    Line line1 = new Line(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
                    line1.startXProperty().bind(this.widthProperty().divide(2));
                    line1.endXProperty().bind(this.widthProperty().divide(2));
                    line1.endYProperty().bind(this.heightProperty());

                    Line line2 = new Line();
                    line2.startYProperty().bind(this.heightProperty().divide(2));
                    line2.endXProperty().bind(this.widthProperty().divide(2));
                    line2.endYProperty().bind(this.heightProperty().divide(2));

                    getChildren().addAll(line1, line2);
                } else if (j == 4) {
                    Line line1 = new Line(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
                    line1.startXProperty().bind(this.widthProperty().divide(2));
                    line1.endXProperty().bind(this.widthProperty().divide(2));
                    line1.endYProperty().bind(this.heightProperty().divide(2));

                    Line line2 = new Line();
                    line2.startXProperty().bind(this.widthProperty().divide(2));
                    line2.startYProperty().bind(this.heightProperty().divide(2));
                    line2.endXProperty().bind(this.widthProperty());
                    line2.endYProperty().bind(this.heightProperty().divide(2));

                    getChildren().addAll(line1, line2);
                }
                break;
            case 3:
                if (j == 0 || j == 4) {
                    Line line1 = new Line(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
                    line1.startYProperty().bind(this.heightProperty().divide(2));
                    line1.endXProperty().bind(this.widthProperty());
                    line1.endYProperty().bind(this.heightProperty().divide(2));

                    Line line2 = new Line();
                    line2.startXProperty().bind(this.widthProperty().divide(2));
                    line2.startYProperty().bind(this.heightProperty().subtract(this.getHeight()));
                    line2.endXProperty().bind(this.widthProperty().divide(2));
                    line2.endYProperty().bind(this.heightProperty().divide(2));

                    getChildren().addAll(line1, line2);
                } else if (j == 1 || j == 5) {
                    Line line1 = new Line(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
                    line1.startXProperty().bind(this.widthProperty().divide(2));
                    line1.endXProperty().bind(this.widthProperty().divide(2));
                    line1.endYProperty().bind(this.heightProperty());

                    Line line2 = new Line();
                    line2.startYProperty().bind(this.heightProperty().divide(2));
                    line2.endXProperty().bind(this.widthProperty());
                    line2.endYProperty().bind(this.heightProperty().divide(2));

                    getChildren().addAll(line1, line2);
                } else if (j == 2 || j == 6) {
                    Line line1 = new Line(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
                    line1.startYProperty().bind(this.heightProperty().divide(2));
                    line1.endXProperty().bind(this.widthProperty());
                    line1.endYProperty().bind(this.heightProperty().divide(2));

                    Line line2 = new Line();
                    line2.startXProperty().bind(this.widthProperty().divide(2));
                    line2.endXProperty().bind(this.widthProperty().divide(2));
                    line2.endYProperty().bind(this.heightProperty().divide(2));

                    getChildren().addAll(line1, line2);
                }
                break;
            case 4:
                if (j == 0 || j == 1 || j == 5 || j == 6) {
                    Line line1 = new Line(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
                    line1.startYProperty().bind(this.heightProperty().divide(2));
                    line1.endXProperty().bind(this.widthProperty());
                    line1.endYProperty().bind(this.heightProperty().divide(2));
                    getChildren().add(line1);
                } else if (j == 2) {
                    Line line1 = new Line(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
                    line1.startYProperty().bind(this.heightProperty().divide(2));
                    line1.endXProperty().bind(this.widthProperty().divide(2));
                    line1.endYProperty().bind(this.heightProperty().divide(2));

                    Line line2 = new Line();
                    line2.startXProperty().bind(this.widthProperty().divide(2));
                    line2.startYProperty().bind(this.heightProperty().subtract(this.getHeight()));
                    line2.endXProperty().bind(this.widthProperty().divide(2));
                    line2.endYProperty().bind(this.heightProperty().divide(2));

                    getChildren().addAll(line1, line2);
                } else if (j == 3) {
                    Line line1 = new Line(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
                    line1.startXProperty().bind(this.widthProperty().divide(2));
                    line1.endXProperty().bind(this.widthProperty().divide(2));
                    line1.endYProperty().bind(this.heightProperty());

                    Line line2 = new Line();
                    line2.startXProperty().bind(this.widthProperty().divide(2));
                    line2.startYProperty().bind(this.heightProperty().divide(2));
                    line2.endXProperty().bind(this.widthProperty());
                    line2.endYProperty().bind(this.heightProperty().divide(2));
                    getChildren().addAll(line1, line2);
                } else {
                    Line line1 = new Line(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
                    line1.startYProperty().bind(this.heightProperty().divide(2));
                    line1.endXProperty().bind(this.widthProperty().divide(2));
                    line1.endYProperty().bind(this.heightProperty().divide(2));

                    Line line2 = new Line();
                    line2.startXProperty().bind(this.widthProperty().divide(2));
                    line2.endXProperty().bind(this.widthProperty().divide(2));
                    line2.endYProperty().bind(this.heightProperty().divide(2));

                    getChildren().addAll(line1, line2);
                }
                break;
            case 5:
                if (j == 0 || j == 6) {
                    Line line1 = new Line(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
                    line1.startYProperty().bind(this.heightProperty().divide(2));
                    line1.endXProperty().bind(this.widthProperty());
                    line1.endYProperty().bind(this.heightProperty().divide(2));
                    getChildren().add(line1);
                } else if (j == 1) {
                    Line line1 = new Line(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
                    line1.startYProperty().bind(this.heightProperty().divide(2));
                    line1.endXProperty().bind(this.widthProperty().divide(2));
                    line1.endYProperty().bind(this.heightProperty().divide(2));

                    Line line2 = new Line();
                    line2.startXProperty().bind(this.widthProperty().divide(2));
                    line2.startYProperty().bind(this.heightProperty().subtract(this.getHeight()));
                    line2.endXProperty().bind(this.widthProperty().divide(2));
                    line2.endYProperty().bind(this.heightProperty().divide(2));

                    getChildren().addAll(line1, line2);
                } else if (j == 2 || j == 4) {
                    Line line1 = new Line(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
                    line1.startXProperty().bind(this.widthProperty().divide(2));
                    line1.endXProperty().bind(this.widthProperty().divide(2));
                    line1.endYProperty().bind(this.heightProperty());
                    getChildren().add(line1);
                } else if (j == 3) {
                    Line line1 = new Line(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
                    line1.startXProperty().bind(this.widthProperty().divide(2));
                    line1.endXProperty().bind(this.widthProperty().divide(2));
                    line1.endYProperty().bind(this.heightProperty());

                    Line line2 = new Line();
                    line2.startYProperty().bind(this.heightProperty().divide(2));
                    line2.endXProperty().bind(this.widthProperty());
                    line2.endYProperty().bind(this.heightProperty().divide(2));

                    getChildren().addAll(line1, line2);
                } else {
                    Line line1 = new Line(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
                    line1.startYProperty().bind(this.heightProperty().divide(2));
                    line1.endXProperty().bind(this.widthProperty().divide(2));
                    line1.endYProperty().bind(this.heightProperty().divide(2));

                    Line line2 = new Line();
                    line2.startXProperty().bind(this.widthProperty().divide(2));
                    line2.endXProperty().bind(this.widthProperty().divide(2));
                    line2.endYProperty().bind(this.heightProperty().divide(2));

                    getChildren().addAll(line1, line2);
                }
                break;
            case 6:
                if (j == 0) {
                    Line line1 = new Line(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
                    line1.startYProperty().bind(this.heightProperty().divide(2));
                    line1.endXProperty().bind(this.widthProperty().divide(2));
                    line1.endYProperty().bind(this.heightProperty().divide(2));

                    Line line2 = new Line();
                    line2.startXProperty().bind(this.widthProperty().divide(2));
                    line2.startYProperty().bind(this.heightProperty().subtract(this.getHeight()));
                    line2.endXProperty().bind(this.widthProperty().divide(2));
                    line2.endYProperty().bind(this.heightProperty().divide(2));

                    getChildren().addAll(line1, line2);
                } else if (j == 1 || j == 2 || j == 4 || j == 5) {
                    Line line1 = new Line(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
                    line1.startXProperty().bind(this.widthProperty().divide(2));
                    line1.endXProperty().bind(this.widthProperty().divide(2));
                    line1.endYProperty().bind(this.heightProperty());
                    getChildren().add(line1);
                } else if (j == 3) {
                    Line line1 = new Line(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());
                    line1.startXProperty().bind(this.widthProperty().divide(2));
                    line1.endXProperty().bind(this.widthProperty().divide(2));
                    line1.endYProperty().bind(this.heightProperty());

                    Line line2 = new Line();
                    line2.startYProperty().bind(this.heightProperty().divide(2));
                    line2.endXProperty().bind(this.widthProperty().divide(2));
                    line2.endYProperty().bind(this.heightProperty().divide(2));

                    getChildren().addAll(line1, line2);
                } else {
                    Line line1 = new Line(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
                    line1.startYProperty().bind(this.heightProperty().divide(2));
                    line1.endXProperty().bind(this.widthProperty().divide(2));
                    line1.endYProperty().bind(this.heightProperty().divide(2));

                    Line line2 = new Line();
                    line2.startXProperty().bind(this.widthProperty().divide(2));
                    line2.endXProperty().bind(this.widthProperty().divide(2));
                    line2.endYProperty().bind(this.heightProperty().divide(2));

                    getChildren().addAll(line1, line2);
                }
                break;
        }
    }

    //This uses the converted char value to determine whether it is a playable
    //spot on the 7x7 grid. it returns true if it is and false if it isn't.
    private boolean checkValidSpace(int intCoordX, int y) {
        char x = convertIntToChar(intCoordX);
        if (x == 'a' || x == 'g') {
            if (y == 6 || y == 3 || y == 0) {
                return true;
            } else {
                return false;
            }
        } else if (x == 'b' || x == 'f') {
            if (y == 5 || y == 3 || y == 1) {
                return true;
            } else {
                return false;
            }
        } else if (x == 'c' || x == 'e') {
            if (y == 4 || y == 3 || y == 2) {
                return true;
            } else {
                return false;
            }
        } else if (x == 'd') {
            if (y == 6 || y == 5 || y == 4 || y == 2 || y == 1 || y == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //Helper function for checkValidSpace
    private char convertIntToChar(int x) {
        //Take the value of x (should be 0-6) and add it to 97
        //to get the ascii value of a-g
        x = x + 97;
        return (char) x;
    }

    //Finds the playing coordinate from the GUI coordinate through the CoordTable in the Move Class
    private Point getCoords(int i, int j) {
        Point oldCoord = new Point(i, j);
        return coordTable.get(oldCoord);
    }

    // This is called when a validSpace is clicked to handle how a piece is placed
    private void handleClick(MouseEvent e) {
        //Local variables for the GamePlay class, the current player and the quickTable
        Game.GamePlay theGame = Gui.getMyGame();
        Player currentPlayer = theGame.getCurrentPlayer();
        HashMap<Point, Player> qTable = theGame.getQuickTable();


        /*
        //FIXME: Event Experimentation
        System.out.println("getEventType : " + e.getEventType().toString() + "\n" +
                "getSource : " + e.getSource().toString() + "\n" +
                "getTarget : " + e.getTarget().toString() + "\n" +
                "toString : " + e.toString() + "\n");
        */

        switch (theGame.gameState) {
            case Mill: //Try to Remove piece at this location
                //If there is a piece on this cell AND it is of the other player
                if (qTable.get(myPair) != null && qTable.get(myPair) != currentPlayer) {

                    //Local variable for freePieces Array
                    ArrayList<Point> freePieces = Gui.getMyGame().getFreePieces();

                    //If freePieces equals null, all opponents pieces are in mills, any piece can be taken
                    //If the piece clicked is in the freePieces Array, is it not in a mill, and can be taken
                    if (freePieces == null) {
                        removeVisualPiece(this);
                        Move.removePiece(myPair);
                       Cell.undoHighlights();
                    }
                    else if (freePieces.contains(myPair)) {
                        removeVisualPiece(this);
                        Move.removePiece(myPair);
                        Cell.undoHighlights();
                    }

                }
                break;
            case MidMove: //Try to Move lastPiece to new Location
                if (Move.changeLocation(currentPlayer, theGame.getLastCell().myPair, this.myPair)) {

                    //Tell GUI to 'move' visual piece
                    removeVisualPiece(theGame.getLastCell());
                    colorVisualPiece(currentPlayer);

                    //Undo all highlights, as we have completed moving the piece
                    Cell.undoHighlights();
                    if (theGame.unresolvedMills()) {
                        Cell.hightlightMills(theGame.getFreePieces());
                    }
                //If you click on the same piece that you selected to move, you will unselect that piece
                } else if (myPair == theGame.getLastCell().myPair){

                    //Reset GameState flag from midMove to Moving
                    theGame.gameState = Game.GameState.Moving;

                    //Undo highlights from all cells
                    Cell.undoHighlights();
                }
                break;
            case Placing: //Place piece on board
                if (Move.changeLocation(currentPlayer, Game.IN_BAG, this.myPair)) {

                    //Tell GUI to place visual piece
                    colorVisualPiece(currentPlayer);
                    if (theGame.unresolvedMills()) {
                        Cell.hightlightMills(theGame.getFreePieces());
                    }
                }
                break;
            case Moving: //Capture first click, change myGame.midMove to true
                //If a cell that was clicked has a piece on it of the current player...
                if (qTable.get(myPair) != null && qTable.get(myPair).equals(currentPlayer)) {

                    //Make sure that piece has at least one legal move, using moveTable <Point, Point[Available Locations]>
                    boolean canMove = false;
                    for (Point checkPair : Move.getMoveTable().get(myPair)) {
                        if (Move.isOpen(checkPair)) {
                            showAvailableSpaces(checkPair); //Highlight as an available place to move
                            canMove = true;
                        }
                    }
                    //If player is flying, then all open cells are available to move to and should be highlighted
                    if (theGame.getCurrentPlayer().isFlying()) {
                        canMove = true;
                        for (Point pair : coordTable.values()) {
                            if (Move.isOpen(pair)) {
                                showAvailableSpaces(pair);
                            }
                        }
                    }

                    //If we clicked on a movable piece, capture the cell we are moving from (this),
                    //Set midMove flag to true, because the next click should attempt to move piece to new location
                    //and Update the GameState
                    if (canMove) {
                        theGame.setLastCell(this);
                        theGame.setMidMove(true);
                        theGame.updateGameState();
                    }
                }
                break;
        }
    }

    public static void hightlightMills(ArrayList<Point> freePieces) {
        if (freePieces == null) {
            for (Map.Entry<Point, Player> pair : Gui.getMyGame().getQuickTable().entrySet()) {
                if (!(pair.getValue() == Gui.getMyGame().getCurrentPlayer())) {
                    showAvailableSpaces(pair.getKey());
                }
            }
        }
        else {
            for (Point pair : freePieces) {
                showAvailableSpaces(pair);
            }
        }
    }
    //Change style of cell to look 'highlighted' when hovered over
    private void hoverHighlightCell() {
    	this.setStyle("-fx-border-color:" + Style.midBlueHex
    		    + "; -fx-background-color:" + Style.lightBlueHex
    		    + "; -fx-border-width: 15; "
    		    + "-fx-border-radius: 50%;");
    }

    //Change style of cell to remove 'highlighted' look when un-hovered, unless it is already 'highlighted' as a movable place
    private void undoHoverHighlight() {
        //If this cell has been flagged as an available place to move, then redo that highlight
        if (this.availableSpace){
            highlightAvailableSpace();
        }
        else{ //Otherwise remove highlight from cell
            this.setStyle("-fx-background-color: " + Style.lightBlueHex
                    + "; -fx-text-fill: white;");
        }
    }

    //Change style of other cell to look 'highlighted' and mark as available
    public static void showAvailableSpaces(Point movableToLocation){
        Cell availableCell = pairToCell.get(movableToLocation);
        if (availableCell != null) {
            availableCell.highlightAvailableSpace();
        }
    }

    //Helper function for showAvailableSpaces
    private void highlightAvailableSpace(){
        this.availableSpace = true;
        this.setStyle("-fx-border-color:" + Style.availableMoveHex
                + "; -fx-background-color:" + Style.lightBlueHex
                + "; -fx-border-width: 15; "
                + "-fx-border-radius: 50%;");
    }

    //Change style of this cell to undo 'highlight' for being an available space to move to
    public static void undoHighlights() {
        for (Cell cell : pairToCell.values()) {
            cell.availableSpace = false;
            if (!cell.getStyle().contains("-fx-border-color:" + Style.midBlueHex
                    + "; -fx-background-color:" + Style.lightBlueHex)) {

                cell.setStyle("-fx-background-color: #afc1cc; -fx-text-fill: white;");
            }
        }
    }

    //Change style of Ellipse in cell to a player color, show the Ellipse and remove one Ellipse from players 'bag'
    public void colorVisualPiece(Player player) {
        //Player 1's pieces are currently darkRed, while Player 2's piece are darkBlue (looks gray to me)
        if (player.equals(Gui.getMyGame().pl1)) {
            visualPiece.setFill(Style.darkRed);
            Gui.removeVBoxElement(Gui.player1);

        } else {
            visualPiece.setFill(Style.darkBlue);
            Gui.removeVBoxElement(Gui.player2);
        }
        visualPiece.setVisible(true);
    }

    //Change the style of Ellipse in a cell to be invisible, thereby visually removing the piece from the GUI board
    private static void removeVisualPiece(Cell theCell) {
        Node theNode = null;

        //For the elements in the cell, find the Ellipse
        for (Node node : theCell.getChildren()) {
            if (node instanceof Ellipse) {
                theNode = node;
                break;
            }
        }
        if (theNode != null) {
            theNode.setVisible(false);
        }
    }
}
