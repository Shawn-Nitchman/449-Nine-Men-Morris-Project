package NineMensMorris;
    
import java.awt.Point;
import java.util.HashMap;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.shape.Line;


public class Gui extends Application{
	private Game.GamePlay myGame = new Game.GamePlay();

    private String currentPlayer = myGame.pl1.getName();
    private Cell[][] cell = new Cell[7][7];
    private HashMap<Point, Point> coordTable; //HashMap storing conversion information

    private void InitCoordTable(){
        coordTable = new HashMap<Point, Point>();

        coordTable.put(new Point(0, 0), new Point(2,0));
        coordTable.put(new Point(0, 3), new Point(2,1));
        coordTable.put(new Point(0,6), new Point(2,2));

        coordTable.put(new Point(1, 1), new Point(1,0));
        coordTable.put(new Point(1, 3), new Point(1,1));
        coordTable.put(new Point(1, 5), new Point(1,2));

        coordTable.put(new Point(2, 2), new Point(0,0));
        coordTable.put(new Point(2, 3), new Point(0,1));
        coordTable.put(new Point(2, 4), new Point(0,2));

        coordTable.put(new Point(3, 0), new Point(2,7));
        coordTable.put(new Point(3, 1), new Point(1,7));
        coordTable.put(new Point(3, 2), new Point(0,7));
        coordTable.put(new Point(3, 4), new Point(0,3));
        coordTable.put(new Point(3, 5), new Point(1,3));
        coordTable.put(new Point(3, 6), new Point(2,3));

        coordTable.put(new Point(4, 2), new Point(0,6));
        coordTable.put(new Point(4, 3), new Point(0,5));
        coordTable.put(new Point(4, 4), new Point(0,4));

        coordTable.put(new Point(5, 1), new Point(2,6));
        coordTable.put(new Point(5, 3), new Point(2,5));
        coordTable.put(new Point(5, 5), new Point(2,4));

        coordTable.put(new Point(6, 0), new Point(2,6));
        coordTable.put(new Point(6, 3), new Point(2,5));
        coordTable.put(new Point(6, 5), new Point(2,4));
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        InitCoordTable();

        primaryStage.setTitle("9 Men Morris");

        //Layout initialization
        BorderPane border = new BorderPane();
        VBox player1 = addVBox("Player1", 9);
        VBox player2 = addVBox("Player2", 9);
        GridPane gridpane = addGridPane();

        border.setLeft(player1);
        border.setCenter(gridpane);
        border.setRight(player2);

        Scene scene = new Scene(border, 850, 650);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    // Add to draw/visual class
    public VBox addVBox(String name, int num) {
    	//makes and returns vertical boxes for the players
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(8);

        Text title = new Text(name);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        vbox.getChildren().add(title);

        for (int i = 1; i <= num; i++){
           if(name.equals("Player1")){
               Ellipse ellipse = new Ellipse(20,20,20,20);
               ellipse.setStroke(Color.BLACK);
               ellipse.setFill(Color.WHITE);
               vbox.getChildren().add(ellipse);
           }else{
               Ellipse ellipse = new Ellipse(20,20,20,20);
               ellipse.setStroke(Color.BLACK);
               ellipse.setFill(Color.BLACK);
               vbox.getChildren().add(ellipse);
           }
        }
        return vbox;
    }

    // Creates 7x7 grid and returns it
    public GridPane addGridPane() {
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(10,10,10,10));


        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                cell[i][j] =  new Cell(i, j);
                gridpane.add(cell[i][j], i, j);
            }
        }

        return gridpane;
    }


    public class Cell extends Pane {
        private String player = " ";
        private boolean validSpace;
        private Point myPair; //add pair for valid space position

        private Point getCoords(int i, int j){
    	    Point oldCoord = new Point(i,j);
    	    return coordTable.getOrDefault(oldCoord, new Point());
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



    	//convert integer coordinates into Point coordinates
    	private Point convertCoordinates(int x, int y) {
    		if (x == 0 || x == 6 || y == 0 || y == 6) {
    			myPair.x = 2;
    		}
    		if (x == 1 || x == 5 || y == 1 || y == 5) {
    			myPair.x = 1;
    		}
    		if (x == 2 || x == 4 || y == 2 || y == 4) {
    			myPair.x = 0;
    		}
    		return null;
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

            	this.setOnMouseClicked(e -> handleClick());
            	// add on hover actions
            }
        }

        private void handleClick(){
            System.out.println("clicked");
            // if valid move, then move and change whose turn it is
            // need player, old pair & new pair for Move.move()
            if(player == " " && currentPlayer != ""){

                 if (Move.move(((currentPlayer == "R") ? myGame.pl1: myGame.pl2), Game.IN_BAG, this.myPair)) {
                    setPlayer(currentPlayer);
                    currentPlayer = (currentPlayer == "R") ? "B" : "R";
                }
            }

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
            ellipse.setStroke(Color.BLACK);

            if(player == "R"){
                ellipse.setFill(Color.RED);
                getChildren().add(ellipse);

            } else if(player == "B") {
                ellipse.setFill(Color.BLUE);
                getChildren().add(ellipse);
            }

        }
        
    }



    public static void main(String[] args) {
        launch(args);
    }

}
