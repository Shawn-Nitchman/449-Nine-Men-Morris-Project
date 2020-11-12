package NineMensMorris;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

import java.awt.*;

//import javafx.scene.paint.Color;

// Cells make up the 7x7 grid on the board of playable and non-playable places
public class Cell extends Pane {
    private boolean validSpace; // This tells us if the cell is playable or not
    private Point myPair = new Point (-99,-99); //add pair for valid space position
    private boolean hoverHighlight;
    private Point getCoords(int i, int j){
        Point oldCoord = new Point(i,j);
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
    private boolean checkValidSpace(int intCoordX, int y){
        char x = convertIntToChar(intCoordX);
        if (x == 'a' || x == 'g') {
            if (y == 6 || y == 3 || y == 0) {
                return true;
            }
            else {
                return false;
            }
        }
        else if (x == 'b' || x == 'f') {
            if (y == 5 || y == 3 || y == 1) {
                return true;
            }
            else {
                return false;
            }
        }
        else if (x == 'c' || x == 'e') {
            if (y == 4 || y == 3 || y == 2) {
                return true;
            }
            else {
                return false;
            }
        }
        else if (x == 'd') {
            if (y == 6 || y == 5 || y == 4 || y == 2 || y == 1 || y == 0) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    // Cell constructor checks if it is a validSpace and initializes drawings for the cell
    // Then, it initializes the coordinates
    public Cell(int i, int j){
        this.hoverHighlight = false;
        validSpace = checkValidSpace(i, j);
        initializeDrawings(i, j);     
        this.setPrefSize(150,150); // sets default cell size (refactor sometime!)
        initializeCoords(i, j, validSpace);
    }
    
    //This initializes the coordinates for our coordinate system
    //given the position on the 7x7 grid and whether it's a playable space
    private void initializeCoords(int i, int j, boolean validSpace) {
        if (validSpace == true){
            //Assign myPair via coordTable
            this.myPair = new Point(getCoords(i, j));
            this.setOnMouseClicked(e -> handleClick());
            this.setOnMouseEntered(e -> hoverHighlightCell());
            this.setOnMouseExited(e -> undoHoverHighlight());
        }
    }
    
  //This whole switch initializes the drawings for the board for a new game
   private void initializeDrawings(int i, int j) {
	   this.setStyle("-fx-background-color: #afc1cc; -fx-text-fill: white;");
	   switch(i){
       case 0:
           if(j == 1 || j == 2 || j == 4 || j == 5) {
               Line line1 = new Line();

               //Can we separate the drawing code and the gameplay code?
               line1.startXProperty().bind(this.widthProperty().divide(2));
               line1.endXProperty().bind(this.widthProperty().divide(2));
               line1.endYProperty().bind(this.heightProperty());

               getChildren().add(line1);
           }else if(j == 0){
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

               getChildren().addAll(line1,line2);
           }else if (j == 3){
               Line line1 = new Line(this.getWidth()/2,0,this.getWidth()/2,this.getHeight());
               line1.startXProperty().bind(this.widthProperty().divide(2));
               line1.endXProperty().bind(this.widthProperty().divide(2));
               line1.endYProperty().bind(this.heightProperty());

               Line line2 = new Line();
               line2.startXProperty().bind(this.widthProperty().divide(2));
               line2.startYProperty().bind(this.heightProperty().divide(2));
               line2.endXProperty().bind(this.widthProperty());
               line2.endYProperty().bind(this.heightProperty().divide(2));

               getChildren().addAll(line1,line2);
           }else {
               Line line1 = new Line(this.getWidth()/2,0,this.getWidth()/2,this.getHeight());
               line1.startXProperty().bind(this.widthProperty().divide(2));
               line1.endXProperty().bind(this.widthProperty().divide(2));
               line1.endYProperty().bind(this.heightProperty().divide(2));

               Line line2 = new Line();
               line2.startXProperty().bind(this.widthProperty().divide(2));
               line2.startYProperty().bind(this.heightProperty().divide(2));
               line2.endXProperty().bind(this.widthProperty());
               line2.endYProperty().bind(this.heightProperty().divide(2));

               getChildren().addAll(line1,line2);
           }
           break;
       case 1:
           if(j == 0 || j == 6) {
               Line line1 = new Line(0,this.getHeight()/2,this.getWidth(),this.getHeight()/2);
               line1.startYProperty().bind(this.heightProperty().divide(2));
               line1.endXProperty().bind(this.widthProperty());
               line1.endYProperty().bind(this.heightProperty().divide(2));
               getChildren().add(line1);
           }else if(j == 1){
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

               getChildren().addAll(line1,line2);
           }else if(j == 2 || j == 4){
               Line line1 = new Line();
               line1.startXProperty().bind(this.widthProperty().divide(2));
               line1.endXProperty().bind(this.widthProperty().divide(2));
               line1.endYProperty().bind(this.heightProperty());
               getChildren().add(line1);
           }else if(j == 3){
               Line line1 = new Line(this.getWidth()/2,0,this.getWidth()/2,this.getHeight());
               line1.startXProperty().bind(this.widthProperty().divide(2));
               line1.endXProperty().bind(this.widthProperty().divide(2));
               line1.endYProperty().bind(this.heightProperty());

               Line line2 = new Line();
               line2.startYProperty().bind(this.heightProperty().divide(2));
               line2.endXProperty().bind(this.widthProperty());
               line2.endYProperty().bind(this.heightProperty().divide(2));

               getChildren().addAll(line1, line2);
           }else{
               Line line1 = new Line(this.getWidth()/2,0,this.getWidth()/2,this.getHeight());
               line1.startXProperty().bind(this.widthProperty().divide(2));
               line1.endXProperty().bind(this.widthProperty().divide(2));
               line1.endYProperty().bind(this.heightProperty().divide(2));

               Line line2 = new Line();
               line2.startXProperty().bind(this.widthProperty().divide(2));
               line2.startYProperty().bind(this.heightProperty().divide(2));
               line2.endXProperty().bind(this.widthProperty());
               line2.endYProperty().bind(this.heightProperty().divide(2));

               getChildren().addAll(line1,line2);
           }

           break;

       case 2:
           if(j == 0 || j == 1 || j == 5 || j == 6){
               Line line1 = new Line(0,this.getHeight()/2,this.getWidth(),this.getHeight()/2);
               line1.startYProperty().bind(this.heightProperty().divide(2));
               line1.endXProperty().bind(this.widthProperty());
               line1.endYProperty().bind(this.heightProperty().divide(2));
               getChildren().add(line1);
           }else if(j == 2){
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

               getChildren().addAll(line1,line2);
           }else if(j == 3){
               Line line1 = new Line(this.getWidth()/2,0,this.getWidth()/2,this.getHeight());
               line1.startXProperty().bind(this.widthProperty().divide(2));
               line1.endXProperty().bind(this.widthProperty().divide(2));
               line1.endYProperty().bind(this.heightProperty());

               Line line2 = new Line();
               line2.startYProperty().bind(this.heightProperty().divide(2));
               line2.endXProperty().bind(this.widthProperty().divide(2));
               line2.endYProperty().bind(this.heightProperty().divide(2));

               getChildren().addAll(line1, line2);
           }else if(j == 4){
               Line line1 = new Line(this.getWidth()/2,0,this.getWidth()/2,this.getHeight());
               line1.startXProperty().bind(this.widthProperty().divide(2));
               line1.endXProperty().bind(this.widthProperty().divide(2));
               line1.endYProperty().bind(this.heightProperty().divide(2));

               Line line2 = new Line();
               line2.startXProperty().bind(this.widthProperty().divide(2));
               line2.startYProperty().bind(this.heightProperty().divide(2));
               line2.endXProperty().bind(this.widthProperty());
               line2.endYProperty().bind(this.heightProperty().divide(2));

               getChildren().addAll(line1,line2);
           }
           break;
       case 3:
           if(j == 0 || j == 4){
               Line line1 = new Line(0,this.getHeight()/2,this.getWidth(),this.getHeight()/2);
               line1.startYProperty().bind(this.heightProperty().divide(2));
               line1.endXProperty().bind(this.widthProperty());
               line1.endYProperty().bind(this.heightProperty().divide(2));

               Line line2 = new Line();
               line2.startXProperty().bind(this.widthProperty().divide(2));
               line2.startYProperty().bind(this.heightProperty().subtract(this.getHeight()));
               line2.endXProperty().bind(this.widthProperty().divide(2));
               line2.endYProperty().bind(this.heightProperty().divide(2));

               getChildren().addAll(line1,line2);
           }else if(j == 1 || j == 5){
               Line line1 = new Line(this.getWidth()/2,0,this.getWidth()/2,this.getHeight());
               line1.startXProperty().bind(this.widthProperty().divide(2));
               line1.endXProperty().bind(this.widthProperty().divide(2));
               line1.endYProperty().bind(this.heightProperty());

               Line line2 = new Line();
               line2.startYProperty().bind(this.heightProperty().divide(2));
               line2.endXProperty().bind(this.widthProperty());
               line2.endYProperty().bind(this.heightProperty().divide(2));

               getChildren().addAll(line1, line2);
           }else if(j == 2 || j == 6){
               Line line1 = new Line(0,this.getHeight()/2,this.getWidth(),this.getHeight()/2);
               line1.startYProperty().bind(this.heightProperty().divide(2));
               line1.endXProperty().bind(this.widthProperty());
               line1.endYProperty().bind(this.heightProperty().divide(2));

               Line line2 = new Line();
               line2.startXProperty().bind(this.widthProperty().divide(2));
               line2.endXProperty().bind(this.widthProperty().divide(2));
               line2.endYProperty().bind(this.heightProperty().divide(2));

               getChildren().addAll(line1,line2);
           }
           break;
       case 4:
           if(j == 0 || j == 1 || j == 5 || j == 6){
               Line line1 = new Line(0,this.getHeight()/2,this.getWidth(),this.getHeight()/2);
               line1.startYProperty().bind(this.heightProperty().divide(2));
               line1.endXProperty().bind(this.widthProperty());
               line1.endYProperty().bind(this.heightProperty().divide(2));
               getChildren().add(line1);
           }else if(j == 2){
               Line line1 = new Line(0,this.getHeight()/2,this.getWidth(),this.getHeight()/2);
               line1.startYProperty().bind(this.heightProperty().divide(2));
               line1.endXProperty().bind(this.widthProperty().divide(2));
               line1.endYProperty().bind(this.heightProperty().divide(2));

               Line line2 = new Line();
               line2.startXProperty().bind(this.widthProperty().divide(2));
               line2.startYProperty().bind(this.heightProperty().subtract(this.getHeight()));
               line2.endXProperty().bind(this.widthProperty().divide(2));
               line2.endYProperty().bind(this.heightProperty().divide(2));

               getChildren().addAll(line1, line2);
           }else if(j == 3){
               Line line1 = new Line(this.getWidth()/2,0,this.getWidth()/2,this.getHeight());
               line1.startXProperty().bind(this.widthProperty().divide(2));
               line1.endXProperty().bind(this.widthProperty().divide(2));
               line1.endYProperty().bind(this.heightProperty());

               Line line2 = new Line();
               line2.startXProperty().bind(this.widthProperty().divide(2));
               line2.startYProperty().bind(this.heightProperty().divide(2));
               line2.endXProperty().bind(this.widthProperty());
               line2.endYProperty().bind(this.heightProperty().divide(2));
               getChildren().addAll(line1, line2);
           }else{
               Line line1 = new Line(0,this.getHeight()/2,this.getWidth(),this.getHeight()/2);
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
           if(j == 0 || j == 6){
               Line line1 = new Line(0,this.getHeight()/2,this.getWidth(),this.getHeight()/2);
               line1.startYProperty().bind(this.heightProperty().divide(2));
               line1.endXProperty().bind(this.widthProperty());
               line1.endYProperty().bind(this.heightProperty().divide(2));
               getChildren().add(line1);
           }else if(j == 1){
               Line line1 = new Line(0,this.getHeight()/2,this.getWidth(),this.getHeight()/2);
               line1.startYProperty().bind(this.heightProperty().divide(2));
               line1.endXProperty().bind(this.widthProperty().divide(2));
               line1.endYProperty().bind(this.heightProperty().divide(2));

               Line line2 = new Line();
               line2.startXProperty().bind(this.widthProperty().divide(2));
               line2.startYProperty().bind(this.heightProperty().subtract(this.getHeight()));
               line2.endXProperty().bind(this.widthProperty().divide(2));
               line2.endYProperty().bind(this.heightProperty().divide(2));

               getChildren().addAll(line1, line2);
           }else if(j == 2 || j == 4){
               Line line1 = new Line(this.getWidth()/2,0,this.getWidth()/2,this.getHeight());
               line1.startXProperty().bind(this.widthProperty().divide(2));
               line1.endXProperty().bind(this.widthProperty().divide(2));
               line1.endYProperty().bind(this.heightProperty());
               getChildren().add(line1);
           }else if(j == 3){
               Line line1 = new Line(this.getWidth()/2,0,this.getWidth()/2,this.getHeight());
               line1.startXProperty().bind(this.widthProperty().divide(2));
               line1.endXProperty().bind(this.widthProperty().divide(2));
               line1.endYProperty().bind(this.heightProperty());

               Line line2 = new Line();
               line2.startYProperty().bind(this.heightProperty().divide(2));
               line2.endXProperty().bind(this.widthProperty());
               line2.endYProperty().bind(this.heightProperty().divide(2));

               getChildren().addAll(line1, line2);
           }else{
               Line line1 = new Line(0,this.getHeight()/2,this.getWidth(),this.getHeight()/2);
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
           if(j == 0){
               Line line1 = new Line(0,this.getHeight()/2,this.getWidth(),this.getHeight()/2);
               line1.startYProperty().bind(this.heightProperty().divide(2));
               line1.endXProperty().bind(this.widthProperty().divide(2));
               line1.endYProperty().bind(this.heightProperty().divide(2));

               Line line2 = new Line();
               line2.startXProperty().bind(this.widthProperty().divide(2));
               line2.startYProperty().bind(this.heightProperty().subtract(this.getHeight()));
               line2.endXProperty().bind(this.widthProperty().divide(2));
               line2.endYProperty().bind(this.heightProperty().divide(2));

               getChildren().addAll(line1, line2);
           }else if(j == 1 || j == 2 || j== 4 || j == 5){
               Line line1 = new Line(this.getWidth()/2,0,this.getWidth()/2,this.getHeight());
               line1.startXProperty().bind(this.widthProperty().divide(2));
               line1.endXProperty().bind(this.widthProperty().divide(2));
               line1.endYProperty().bind(this.heightProperty());
               getChildren().add(line1);
           }else if(j ==3){
               Line line1 = new Line(this.getWidth()/2,0,this.getWidth()/2,this.getHeight());
               line1.startXProperty().bind(this.widthProperty().divide(2));
               line1.endXProperty().bind(this.widthProperty().divide(2));
               line1.endYProperty().bind(this.heightProperty());

               Line line2 = new Line();
               line2.startYProperty().bind(this.heightProperty().divide(2));
               line2.endXProperty().bind(this.widthProperty().divide(2));
               line2.endYProperty().bind(this.heightProperty().divide(2));

               getChildren().addAll(line1, line2);
           }else{
               Line line1 = new Line(0,this.getHeight()/2,this.getWidth(),this.getHeight()/2);
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
    private void handleClick(){
//        System.out.println("clicked" + " " + this.myPair.toString() + " myI = " + this.myI + " myJ = " + this.myJ);
        // if valid move, then move and change whose turn it is

        /*
        Recommend putting if statements to handle each different click.
        if (click on the actual piece) { start highlighting the locations to move. }
         */
        if (Gui.getMyGame().newMills() > 0  &&
                !(Gui.getMyGame().getQuickTable().get(myPair).equals(Gui.getCurrentPlayer()))) {
            removePiece();
            Gui.getMyGame().decrementMill();
            if(Gui.getMyGame().newMills() == 0){
                Gui.setCurrentPlayer((Gui.getCurrentPlayer() == "R") ? "B" : "R");
                Gui.getMyGame().switchTurn();
            }
        }

        else if (Move.changeLocation(Gui.getMyGame().getCurrentPlayer(), Game.IN_BAG, this.myPair)) {
            placePiece(Gui.getCurrentPlayer());

            Piece thePiece = null;
            // takes in pair and gets pieces of a given player
            for (Piece piece : Gui.getMyGame().quickTable.get(myPair).getPieces()) {
                if (piece.getPair().equals(myPair)) {
                    thePiece = piece;
                }
            }
            if (thePiece != null) { //checks to see if piece is in a mill after every placement
              //  System.out.println(Gui.getMyGame().inMill(thePiece));
                if (!Gui.getMyGame().inMill(thePiece)){
                    Gui.setCurrentPlayer((Gui.getCurrentPlayer() == "R") ? "B" : "R");
                    Gui.getMyGame().switchTurn();
                }
            }
        }
    }
    
    private void hoverHighlightCell() {
        this.hoverHighlight = true;
    	this.setStyle("-fx-border-color:" + Style.midBlueHex
    		    + "; -fx-background-color:" + Style.lightBlueHex
    		    + "; -fx-border-width: 15; "
    		    + "-fx-border-radius: 50%;");
    }
    
    private void undoHoverHighlight() {
        if (this.hoverHighlight == true){
            this.setStyle("-fx-background-color: " + Style.lightBlueHex
                    + "; -fx-text-fill: white;");
            this.hoverHighlight = false;
        }
    }

    private void highlightAvailableSpace(){

    }

    private boolean canMoveHere(){
        return false;
    }

    public void placePiece(String color){
        drawPiece();
        colorVisualPiece(color);
    }

    public void drawPiece(){
        Piece.drawVisualPiece(this.getWidth()/3, this.getHeight()/3,
                this.widthProperty(), this.heightProperty());
       }

    public void colorVisualPiece(String color){
        if(color == "R"){
            Piece.visualPiece.setFill(Style.darkRed);
            getChildren().add(Piece.visualPiece);
            Gui.removeVBoxElement("R");
        } else if(color == "B") {
            Piece.visualPiece.setFill(Style.darkBlue);
            getChildren().add(Piece.visualPiece);
            Gui.removeVBoxElement("B");
        }
    }

    private void removePiece(){
        Node theNode = null;
        for (Node node : this.getChildren()) {
            if (node instanceof Ellipse) {
                theNode = node;
                break;
            }
        }
        if (theNode != null) {this.getChildren().remove(theNode); }
        Move.removePiece(myPair);
    }
}
