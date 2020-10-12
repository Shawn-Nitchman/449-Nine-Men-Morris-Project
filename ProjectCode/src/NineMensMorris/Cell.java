package NineMensMorris;

import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;

import java.awt.*;

public class Cell extends Pane {
    private String player = " ";
    private boolean validSpace;
    private Point myPair = new Point (-99,-99); //add pair for valid space position
    private int myI, myJ;

    private Point getCoords(int i, int j){
        Point oldCoord = new Point(i,j);
        return Move.getCoordTable().get(oldCoord);
    }
    //This takes the value of x (should be 1-7) and adds it to 96
    //to get the ascii value of a-g
    private char convertIntToChar(int x) {
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

    public Cell(int i, int j){
        //setStyle("-fx-border-color : black");
        validSpace = checkValidSpace(i, j);

        //This whole switch initializes the drawings for the board for a new game
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

        this.setPrefSize(150,150); // sets default cell size (refactor sometime!)

        //This checks if the cell is a playable space on the board
        if (validSpace == true){
            //Assign myPair via coordTable
            this.myPair = new Point(getCoords(i, j));
            this.myI = i; this.myJ = j;
            this.setOnMouseClicked(e -> handleClick());
            // add on hover actions
        }
    }

    private void handleClick(){
        System.out.println("clicked" + " " + this.myPair.toString() + " myI = " + this.myI + " myJ = " + this.myJ);
        // if valid move, then move and change whose turn it is
        // need player, old pair & new pair for Move.move()
        //if(player == " " && currentPlayer != ""){

        if (Move.move(((Gui.getCurrentPlayer() == "R") ? Gui.getMyGame().pl1: Gui.getMyGame().pl2), Game.IN_BAG, this.myPair)) {
            setPlayer(Gui.getCurrentPlayer());
            Gui.setCurrentPlayer((Gui.getCurrentPlayer() == "R") ? "B" : "R");
        }
        //}

    }

    public String getPlayer(){
        return player;
    }

    public void setPlayer(String c){
        player = c;

        Ellipse ellipse = new Ellipse(this.getWidth()/3,this.getHeight()/3, this.getWidth()/3,this.getHeight()/3);
        ellipse.centerXProperty().bind(this.widthProperty().divide(2));
        ellipse.centerYProperty().bind(this.heightProperty().divide(2));
        ellipse.radiusXProperty().bind(this.widthProperty().divide(3));
        ellipse.radiusYProperty().bind(this.heightProperty().divide(3));
        ellipse.setStroke(javafx.scene.paint.Color.BLACK);

        if(player == "R"){
                /*
                for (Node object : player1.getChildrenUnmodifiable()) {
                    if (object.getClass().isInstance(ellipse)) {
                        player1.getChildren().remove(object);
                    }
                }
                */

            ellipse.setFill(javafx.scene.paint.Color.RED);
            getChildren().add(ellipse);

        } else if(player == "B") {
            ellipse.setFill(Color.BLUE);
            getChildren().add(ellipse);
        }

    }

}
