package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class Main extends Application{

    private char currentPlayer = 'w';
    private Cell[][] cell = new Cell[7][7];

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("9 Men Morris");

        BorderPane border = new BorderPane();
        VBox player1 = addVBox("Player1");
        VBox player2 = addVBox("Player2");
        GridPane gridpane = addGridPane();

        border.setLeft(player1);
        border.setCenter(gridpane);
        border.setRight(player2);

        Scene scene = new Scene(border, 750, 750);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public VBox addVBox(String name) {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(8);

        Text title = new Text(name);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        vbox.getChildren().add(title);

        for (int i = 1; i <= 9; i++){
            Text piece = new Text("Piece " + i);
            VBox.setMargin(piece, new Insets(0,0,0,8));
            vbox.getChildren().add(piece);
        }
        return vbox;
    }

    public GridPane addGridPane() {
        GridPane gridpane = new GridPane();

        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                cell[i][j] =  new Cell();
                gridpane.add(cell[i][j], j , i);
            }

        }

        return gridpane;
    }

    public class Cell extends Pane {
        private char player = ' ';

        public Cell(){
            setStyle("-fx-border-color : black");
            this.setPrefSize(150,150);
            this.setOnMouseClicked(e -> handleClick());
        }

        private void handleClick(){
            System.out.println("clicked");
            if(player == ' ' && currentPlayer != ' '){
                setPlayer(currentPlayer);
                currentPlayer = (currentPlayer == 'W') ? 'B' : 'W';
            }
        }

        public char getPlayer(){
            return player;
        }

        public void setPlayer(char c){
            player = c;

            if(player == 'W'){
                Text poop = new Text("White");
                getChildren().add(poop);

            } else if(player == 'B'){
                Text pee = new Text("Black");
                getChildren().add(pee);
            }

        }
    }



    public static void main(String[] args) {
        launch(args);
    }

}
