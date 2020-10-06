    package sample;

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


public class Main extends Application{


    private char currentPlayer = 'W';
    private Cell[][] cell = new Cell[7][7];

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("9 Men Morris");

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

    public VBox addVBox(String name, int num) {
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
        private char player = ' ';

        public Cell(int i, int j){
            //setStyle("-fx-border-color : black");

            switch(i){
                case 0:
                    if(j == 1 || j == 2 || j == 4 || j == 5) {
                        Line line1 = new Line();

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

            this.setPrefSize(150,150);
            this.setOnMouseClicked(e -> handleClick());
        }

        private void handleClick(){
            System.out.println("clicked");
            // bool isvalid
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

                Ellipse ellipse = new Ellipse(this.getWidth()/3,this.getHeight()/3, this.getWidth()/3,this.getHeight()/3);
                ellipse.centerXProperty().bind(this.widthProperty().divide(2));
                ellipse.centerYProperty().bind(this.heightProperty().divide(2));
                ellipse.radiusXProperty().bind(this.widthProperty().divide(3));
                ellipse.radiusYProperty().bind(this.heightProperty().divide(3));
                ellipse.setStroke(Color.BLACK);
                ellipse.setFill(Color.WHITE);

                getChildren().add(ellipse);

            } else if(player == 'B') {
                Ellipse ellipse = new Ellipse(this.getWidth() / 3, this.getHeight() / 3, this.getWidth() / 3, this.getHeight() / 3);
                ellipse.centerXProperty().bind(this.widthProperty().divide(2));
                ellipse.centerYProperty().bind(this.heightProperty().divide(2));
                ellipse.radiusXProperty().bind(this.widthProperty().divide(3));
                ellipse.radiusYProperty().bind(this.heightProperty().divide(3));
                ellipse.setStroke(Color.BLACK);
                ellipse.setFill(Color.BLACK);

                getChildren().add(ellipse);
            }

        }
        
    }



    public static void main(String[] args) {
        launch(args);
    }

}
