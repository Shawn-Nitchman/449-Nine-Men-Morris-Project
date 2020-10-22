package NineMensMorris;

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

/// All Structure extended from Application are static. That was why we could not use the non-static methods.
public class Gui extends Application{
	private static Game.GamePlay myGame = new Game.GamePlay();
    private static String currentPlayer = "R";
    private Cell[][] cell = new Cell[7][7];
    public static VBox player1, player2;
    public static String getCurrentPlayer() { return currentPlayer; }
    public static Game.GamePlay getMyGame() { return myGame; }

    public static void setCurrentPlayer(String p) { currentPlayer = p; }

    @Override
    public void start(Stage primaryStage) throws Exception{

        // In progress on making it change color each time.
        primaryStage.setTitle("9 Men Morris");

        //Layout initialization
        BorderPane border = new BorderPane();
        player1 = addVBox("Player1", 9);
        player2 = addVBox("Player2", 9);
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
               ellipse.setFill(Style.darkRed);
               vbox.getChildren().add(ellipse);
           }else{
               Ellipse ellipse = new Ellipse(20,20,20,20);
               ellipse.setStroke(Color.BLACK);
               ellipse.setFill(Style.darkBlue);
               vbox.getChildren().add(ellipse);
           }
        }
        return vbox;
    }

    //Method to remove displayed pieces on each player's size one the piece is placed.
    public static void removeVBoxElement(String id) {

        if (id == "R") {
            ///Some how index zero is the actual player's name "Player1/Player2" Please solve this.
            player1.getChildren().remove(1);
        }
        if (id == "B") {
            player2.getChildren().remove(1);
        }
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

    public static void main(String[] args) {
        launch(args);
    }
}
