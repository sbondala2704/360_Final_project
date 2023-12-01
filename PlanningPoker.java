package asuHelloWorldJavaFX;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import java.util.*;

public class PlanningPoker extends Application {

    private static final String TITLE = "Planning Poker";
    private Stage primaryStage;
    private Label titleLabel;
    private List<String> userStories; // User stories data
    private Map<String, String> assignedValues = new HashMap<>(); // Assigned card values for user stories

    // Constructor to pass user stories data
    public PlanningPoker(List<String> userStories) {
        this.userStories = userStories;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setScene(createMainScene());
        primaryStage.setTitle(TITLE);
        primaryStage.show();
    }

    private Scene createMainScene() {
        BorderPane borderPane = new BorderPane();

        titleLabel = new Label();
        titleLabel.setStyle("-fx-font-size: 24px;");
        animateTitle(titleLabel, TITLE);

        Button startSessionButton = new Button("Start Session");
        startSessionButton.setOnAction(event -> openPlanningSession());

        Button backButton = new Button("Back to Dashboard");
        backButton.setOnAction(event -> primaryStage.close());

        HBox buttonBar = new HBox(10, startSessionButton, backButton);
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.setPadding(new Insets(15, 12, 15, 12));

        VBox topLayout = new VBox(10, titleLabel, buttonBar);
        topLayout.setAlignment(Pos.CENTER);
        topLayout.setPadding(new Insets(15, 12, 15, 12));

        borderPane.setTop(topLayout);

        return new Scene(borderPane, 600, 400);
    }

    private void openPlanningSession() {
        if (userStories.isEmpty()) {
            // Display a message when no user stories are entered
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No User Stories Found");
            alert.setContentText("Please enter User Stories in the Options -> User Stories menu before continuing.");
            alert.showAndWait();
            return;
        }

        ComboBox<String> userStoriesDropdown = new ComboBox<>(FXCollections.observableArrayList(userStories));
        userStoriesDropdown.setPromptText("Select a User Story");

        GridPane cardGrid = new GridPane();
        cardGrid.setAlignment(Pos.CENTER);
        cardGrid.setHgap(10);
        cardGrid.setVgap(10);
        cardGrid.setPadding(new Insets(25, 25, 25, 25));

        String[] cardValues = {"0", "1", "2", "3", "5", "8", "13", "20", "40", "100"};
        for (int i = 0; i < cardValues.length; i++) {
            Button card = new Button(cardValues[i]);
            card.setPrefSize(60, 90);
            int columnIndex = i % 6;
            int rowIndex = i / 6;
            cardGrid.add(card, columnIndex, rowIndex);

            // Handle card assignment
            card.setOnAction(event -> {
                String selectedUserStory = userStoriesDropdown.getValue();
                String selectedCardValue = ((Button) event.getSource()).getText();
                if (selectedUserStory != null && !selectedUserStory.isEmpty()) {
                    assignedValues.put(selectedUserStory, selectedCardValue);
                    System.out.println("Assigned " + selectedCardValue + " to " + selectedUserStory);
                }
            });
        }

        Button assignButton = new Button("Assign");
        assignButton.setOnAction(event -> openAssignedValuesWindow());

        Button backButton = new Button("Back to Dashboard");
        backButton.setOnAction(event -> {
            primaryStage.setScene(createMainScene());
            titleLabel.setText(TITLE);
        });

        VBox planningSessionLayout = new VBox(10, userStoriesDropdown, cardGrid, assignButton, backButton);
        planningSessionLayout.setAlignment(Pos.CENTER);
        planningSessionLayout.setPadding(new Insets(15));

        Scene planningSessionScene = new Scene(planningSessionLayout, 600, 400);
        primaryStage.setScene(planningSessionScene);
        titleLabel.setText("Planning Session");
    }

    private void animateTitle(Label label, String text) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.2), event -> {
            String currentText = label.getText();
            label.setText(currentText.length() < text.length() ? text.substring(0, currentText.length() + 1) : "");
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void openAssignedValuesWindow() {
        Stage assignedValuesStage = new Stage();
        assignedValuesStage.setTitle("Assigned Values");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        if (assignedValues.isEmpty()) {
            layout.getChildren().add(new Label("No values assigned yet."));
        } else {
            for (Map.Entry<String, String> entry : assignedValues.entrySet()) {
                Label userStoryLabel = new Label(entry.getKey() + ": " + entry.getValue());
                layout.getChildren().add(userStoryLabel);
            }
        }

        Button startNewRoundButton = new Button("Start New Round");
        startNewRoundButton.setOnAction(event -> {
            // Save assigned values to reports (you can implement this part)
            assignedValues.clear(); // Clear assigned values for a new round
            openPlanningSession(); // Start a new planning session
            assignedValuesStage.close();
        });

        Button endSessionButton = new Button("End Session");
        endSessionButton.setOnAction(event -> assignedValuesStage.close());

        layout.getChildren().addAll(startNewRoundButton, endSessionButton);

        Scene scene = new Scene(layout, 400, 300);
        assignedValuesStage.setScene(scene);
        assignedValuesStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
