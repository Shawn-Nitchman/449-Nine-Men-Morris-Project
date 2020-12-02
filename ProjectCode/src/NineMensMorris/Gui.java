package NineMensMorris;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/// All Structure extended from Application are static. That was why we could not use the non-static methods.
public class Gui extends Application{
    private Stage window;
    private Scene gamePage;
    private Scene menuPage;
    private Scene instructionsPage;
	private static final Game.GamePlay myGame = new Game.GamePlay();
    private final Cell[][] cell = new Cell[7][7];
    public static VBox player1, player2;
    public static Text statusText = new Text("");

    //Getter
    public static Game.GamePlay getMyGame() { return myGame; }


    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("9 Men's Morris");

        initializeMenu();
        initializeInstructionsPage();
        initializeGamePage();
        goToMenu();
    }

    // sets the window scene to menuPage
    private void goToMenu(){
        window.setScene(menuPage);
        window.show();
    }

    private void goToGamePage(RadioButton singlePlayer){
        window.setScene(gamePage);
        myGame.setSinglePlayer(singlePlayer.isSelected());
        System.out.print("Single player game:" + myGame.isSinglePlayer());
    }

    // this creates all the components on the menuPage
    private void initializeMenu(){
        VBox menuLayout = new VBox(20);
        menuLayout.setSpacing(10);
        menuLayout.setPadding(new Insets(0, 20, 10, 20));
        menuLayout.setAlignment(Pos.CENTER);

        ToggleGroup numberOfPlayers = new ToggleGroup();
        RadioButton singlePlayer = new RadioButton("Single Player");
        RadioButton twoPlayer = new RadioButton("Two Players");
        singlePlayer.setToggleGroup(numberOfPlayers);
        twoPlayer.setToggleGroup(numberOfPlayers);
        twoPlayer.setSelected(true);

        Label menuDesc = new Label("Welcome to 9 Men Morris");
        Button startGame = new Button("Start Game");
        startGame.setOnMouseClicked(e -> goToGamePage(singlePlayer));
        Button instructions = new Button("How to play");
        instructions.setOnMouseClicked(e -> window.setScene(instructionsPage));

        menuLayout.getChildren().addAll(menuDesc, singlePlayer, twoPlayer, startGame, instructions);
        menuPage = new Scene(menuLayout, 500, 500);
    }

    private void initializeInstructionsPage(){
        String instructions = "How to play 9 Men Morris:\n" +
                "Place pieces\n" +
                "Make mills\n" +
                "Remove the other player's pieces\n" +
                "Move your pieces\n";
        Label instructionsDesc = new Label(instructions);
        VBox pageLayout = new VBox(20);
        pageLayout.setAlignment(Pos.CENTER);
        pageLayout.setSpacing(10);
        Button menuReturn = new Button("Return to Menu");
        menuReturn.setOnMouseClicked(e -> window.setScene(menuPage));
        pageLayout.getChildren().addAll(instructionsDesc, menuReturn);
        instructionsPage = new Scene(pageLayout, 500, 500);
    }

    private void initializeGamePage(){
        //Layout initialization for game
        BorderPane border = new BorderPane();
        player1 = addVBox("Player1", 9);
        player2 = addVBox("Player2", 9);
        statusText.setFont(Font.font("Arial", 36));
        BorderPane.setAlignment(statusText, Pos.TOP_CENTER);

        GridPane gridpane = addGridPane();

        border.setTop(statusText);
        border.setLeft(player1);
        border.setCenter(gridpane);
        border.setRight(player2);
        gamePage = new Scene(border, 850, 650);
        myGame.updateGuiStatus();
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
            Ellipse ellipse = new Ellipse(20,20,20,20);
            ellipse.setStroke(Color.BLACK);

           if(name.equals("Player1")){
               ellipse.setFill(Style.darkRed);
           }
           else {
               ellipse.setStroke(Color.BLACK);
               ellipse.setFill(Style.darkBlue);
           }

            vbox.getChildren().add(ellipse);
        }
        return vbox;
    }


    public static void changeStatus(String status){
        statusText.setText(status);
    }

    //Method to remove displayed pieces on each player's size one the piece is placed.
    public static void removeVBoxElement(VBox playerBox) {

            Node theNode = null;
            for (Node node : playerBox.getChildren()) {
                if (node instanceof Ellipse) {
                    theNode = node;
                    break;
                }
            }
            if (theNode != null) {playerBox.getChildren().remove(theNode); }
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
