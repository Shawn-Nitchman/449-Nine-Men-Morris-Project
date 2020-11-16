package NineMensMorris;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

//import javafx.scene.paint.Color;

// Cells make up the 7x7 grid on the board of playable and non-playable places
public class Cell extends Pane {
    private final boolean validSpace; // This tells us if the cell is playable or not
    private Point myPair = new Point (-99,-99); //add pair for valid space position
    //private boolean hoverHighlight;
    private boolean availableSpace; // This indicates if you can move a piece here
    public Ellipse visualPiece = new Ellipse(this.getWidth() / 3, this.getHeight() / 3,
            this.getWidth(), this.getHeight());

    public static HashMap<Point, Cell> pairToCell = new HashMap<>();

    private Point getCoords(int i, int j) {
        Point oldCoord = new Point(i, j);

        return Move.getCoordTable().get(oldCoord);
    }

    //This takes the value of x (should be 0-6) and adds it to 97
    //to get the ascii value of a-g
    public char convertIntToChar(int x) {
        x = x + 97;
        return (char) x;
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

    // Cell constructor checks if it is a validSpace and initializes drawings for the cell
    // Then, it initializes the coordinates

    public Cell(int i, int j){
        this.availableSpace = false;
        validSpace = checkValidSpace(i, j);
        initializeDrawings(i, j);
        this.setPrefSize(150, 150); // sets default cell size (refactor sometime!)
        initializeCoords(i, j, validSpace);

        //Setup the properties for the Ellipse in this Cell
        visualPiece.centerXProperty().bind(this.widthProperty().divide(2));
        visualPiece.centerYProperty().bind(this.heightProperty().divide(2));
        visualPiece.radiusXProperty().bind(this.widthProperty().divide(3));
        visualPiece.radiusYProperty().bind(this.heightProperty().divide(3));
        visualPiece.setStroke(javafx.scene.paint.Color.BLACK);
        visualPiece.setVisible(false);
        getChildren().add(visualPiece);
    }

    //This initializes the coordinates for our coordinate system
    //given the position on the 7x7 grid and whether it's a playable space
    private void initializeCoords(int i, int j, boolean validSpace) {
        if (validSpace) {
            //Assign myPair via coordTable
            this.myPair = new Point(getCoords(i, j));
            pairToCell.put(myPair, this);

            this.setOnMouseEntered(e -> hoverHighlightCell());
            this.setOnMouseExited(e -> undoHoverHighlight());
            this.setOnMouseClicked(this::handleClick);
        }
    }

    //This whole switch initializes the drawings for the board for a new game
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

    // This is called when a validSpace is clicked to handle how a piece is placed
    private void handleClick(MouseEvent e) {
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
            case Finished:
                break;
            case Draw:
                break;
            case Mill:
                //Try to Remove piece at this location
                if (qTable.get(myPair) != null && qTable.get(myPair) != currentPlayer) {
                    ArrayList<Point> freePieces = Gui.getMyGame().getFreePieces();

                    if (freePieces == null || freePieces.contains(myPair)) {
                        removeVisualPiece(this);
                        Move.removePiece(myPair);

                        if (Gui.getMyGame().getCurrentMills() == 0) {
                            theGame.switchTurn();
                        }
                    }
                }
                break;
            case MidMove:
                //Try to Move lastPiece to new Location
                if (Move.changeLocation(currentPlayer, theGame.getLastCell().myPair, this.myPair)) {
                    for (Point pair : pairToCell.keySet()) {
                        Cell tempCell = pairToCell.get(pair);
                        tempCell.undoHighlight();
                        tempCell.availableSpace = false;
                    }
                    removeVisualPiece(theGame.getLastCell());
                    colorVisualPiece(currentPlayer);
                    if (Gui.getMyGame().getCurrentMills() == 0) {
                        theGame.switchTurn();
                    }
                } else if (myPair == theGame.getLastCell().myPair){
                    theGame.gameState = Game.GameState.Moving;
                    for (Point highlitCell : Move.getMoveTable().keySet()) {
                        Cell tempCell = pairToCell.get(highlitCell);
                        tempCell.undoHighlight();
                        tempCell.availableSpace = false;
                    }
                }
                break;
            case Placing:
                //Place piece on board
                if (Move.changeLocation(currentPlayer, Game.IN_BAG, this.myPair)) {
                    colorVisualPiece(currentPlayer);
                    if (Gui.getMyGame().getCurrentMills() == 0) {
                        theGame.switchTurn();
                    }
                }
                break;
            case Moving:
                //Capture first click, change myGame.midMove to true
                if (qTable.get(myPair) != null && qTable.get(myPair).equals(currentPlayer)) {

                    boolean canMove = false;
                    for (Point checkPair : Move.getMoveTable().get(myPair)) {
                        if (Move.isOpen(checkPair)) {
                            showAvailableSpaces(checkPair);
                            canMove = true;
                        }
                    }
                    if (theGame.getCurrentPlayer().isFlying()) {
                        canMove = true;
                        for (Point pair : Move.getCoordTable().values()) {
                            if (Move.isOpen(pair)) {
                                showAvailableSpaces(pair);
                            }
                        }
                    }
                    if (canMove) {
                        theGame.setLastCell(this);
                        theGame.setMidMove(true);
                        theGame.updateGameState();
                    }
                }
                break;
        }
        switch (theGame.gameState) {
            case Finished:
                //Display Winner's Dialog Box
                theGame.setCurrentPlayer(currentPlayer);
                System.out.println(currentPlayer.getName() + ": WON!!");
                break;
            case Draw:
                //Display Draw Dialog Box
                theGame.setCurrentPlayer(currentPlayer);
                System.out.println("DRAW");
                break;
        }
    }
    
    private void hoverHighlightCell() {
    	this.setStyle("-fx-border-color:" + Style.midBlueHex
    		    + "; -fx-background-color:" + Style.lightBlueHex
    		    + "; -fx-border-width: 15; "
    		    + "-fx-border-radius: 50%;");
    }
    
    private void undoHoverHighlight() {
        if (!this.availableSpace){
            this.setStyle("-fx-background-color: " + Style.lightBlueHex
                    + "; -fx-text-fill: white;");
        }
        else{
            highlightAvailableSpace();
        }
    }

    private void highlightAvailableSpace(){
        this.setStyle("-fx-border-color:" + Style.availableMoveHex
                + "; -fx-background-color:" + Style.lightBlueHex
                + "; -fx-border-width: 15; "
                + "-fx-border-radius: 50%;");
    }

    public void showAvailableSpaces(Point currentLocation){
        Cell availableCell = pairToCell.get(currentLocation);
        if (availableCell != null) {
            availableCell.highlightAvailableSpace();
            availableCell.availableSpace = true;
        }
        // use getCellFromPair hashmap to get the cells to highlight
        // each cell returned must call highlightAvailableSpace
        // change availableSpace = true for each cell returned
    }

    private void undoHighlight() {
        if (!this.getStyle().contains("-fx-border-color:" + Style.midBlueHex
                + "; -fx-background-color:" + Style.lightBlueHex)) {

            this.setStyle("-fx-background-color: #afc1cc; -fx-text-fill: white;");
        }
    }

    public void colorVisualPiece(Player player) {
        if (player.equals(Gui.getMyGame().pl1)) {
            visualPiece.setFill(Style.darkRed);
            Gui.removeVBoxElement(Gui.player1);
        } else {
            visualPiece.setFill(Style.darkBlue);
            Gui.removeVBoxElement(Gui.player2);
        }
        visualPiece.setVisible(true);
    }

    private void removeVisualPiece(Cell theCell) {
        Node theNode = null;
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
