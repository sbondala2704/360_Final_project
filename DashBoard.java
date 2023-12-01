package asuHelloWorldJavaFX;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import java.util.ArrayList;
import java.util.List;

public class DashBoard extends Application {

    private static final String WELCOME_MESSAGE = "Welcome!";
    private Stage primaryStage;
    private List<String> userStories = new ArrayList<>();
    private ComboBox<String> userStoriesComboBox = new ComboBox<>(); // Moved the ComboBox here

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setScene(createMainScene());
        primaryStage.setTitle("EffortLogger Dashboard");
        primaryStage.show();
    }

    private Scene createMainScene() {
        MenuButton menuButton = new MenuButton("Options");
        MenuItem userStoriesItem = new MenuItem("User Stories");
        MenuItem uploadDataItem = new MenuItem("Upload Data");
        menuButton.getItems().addAll(userStoriesItem, uploadDataItem);

        userStoriesItem.setOnAction(event -> openUserStoriesWindow());
        uploadDataItem.setOnAction(event -> openFileChooser());

        HBox topBarLeft = new HBox(menuButton);
        topBarLeft.setAlignment(Pos.TOP_LEFT);
        topBarLeft.setPadding(new Insets(10));

        Label welcomeLabel = new Label("");
        welcomeLabel.setStyle("-fx-font-size: 30;");
        animateWelcomeMessage(welcomeLabel);

        HBox topBarCenter = new HBox(welcomeLabel);
        topBarCenter.setAlignment(Pos.TOP_CENTER);
        topBarCenter.setPadding(new Insets(20));

        Button timeTrackerButton = new Button("Time Tracker");
        timeTrackerButton.setOnAction(event -> launchTimeTracker());
        Button projectsButton = new Button("Projects");
        projectsButton.setOnAction(event -> openProjectsWindow());
        Button reportsButton = new Button("Reports");
        reportsButton.setOnAction(event -> openReportsWindow());
        Button planningPokerButton = new Button("Planning Poker");
        planningPokerButton.setOnAction(event -> launchPlanningPoker());

        VBox centerBox = new VBox(10, timeTrackerButton, projectsButton, reportsButton, planningPokerButton);
        centerBox.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(topBarLeft);
        borderPane.setTop(topBarCenter);
        borderPane.setCenter(centerBox);

        return new Scene(borderPane, 800, 500);
    }

    private void animateWelcomeMessage(Label label) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            String text = label.getText();
            if (text.length() < WELCOME_MESSAGE.length()) {
                label.setText(WELCOME_MESSAGE.substring(0, text.length() + 1));
            }
        }));
        timeline.setCycleCount(WELCOME_MESSAGE.length());
        timeline.play();
    }

    private void openUserStoriesWindow() {
        Stage userStoriesStage = new Stage();
        userStoriesStage.setTitle("User Stories");

        userStoriesComboBox.setPromptText("Select a User Story"); // Set prompt text here

        ListView<String> userStoriesListView = new ListView<>();
        userStoriesListView.getItems().addAll(userStories);

        if (userStories.isEmpty()) {
            userStoriesListView.setPlaceholder(new Label("No User Stories available"));
        }

        Button addUserStoryButton = new Button("Add User Story");
        addUserStoryButton.setOnAction(e -> openAddUserStoryWindow(userStoriesStage, userStoriesListView));

        VBox layout = new VBox(10, userStoriesComboBox, userStoriesListView, addUserStoryButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 400, 300);
        userStoriesStage.setScene(scene);
        userStoriesStage.show();
    }

    private void openAddUserStoryWindow(Stage parentStage, ListView<String> userStoriesListView) {
        Stage addUserStoryStage = new Stage();
        addUserStoryStage.setTitle("Add User Story");

        Label nameLabel = new Label("Name:");
        TextField nameTextField = new TextField();

        Label storyLabel = new Label("Enter User Story:");
        TextArea userStoryTextArea = new TextArea();

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            String name = nameTextField.getText();
            String story = userStoryTextArea.getText();
            if (!name.isEmpty() && !story.isEmpty()) {
                userStories.add(name + ": " + story);
                userStoriesListView.getItems().setAll(userStories);
                userStoriesComboBox.getItems().setAll(userStories); // Update the ComboBox
                addUserStoryStage.close();
            }
        });

        VBox layout = new VBox(10, nameLabel, nameTextField, storyLabel, userStoryTextArea, saveButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 300, 400);
        addUserStoryStage.setScene(scene);
        addUserStoryStage.show();
    }

    private void openFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload Data");
        fileChooser.showOpenDialog(primaryStage);
    }

    private void launchTimeTracker() {
        try {
            Application timeTrackerApp = new TimeTrackingApp();
            Stage stage = new Stage();
            timeTrackerApp.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openProjectsWindow() {
        Stage newWindow = new Stage();
        newWindow.setTitle("Projects");

        Label noProjectsLabel = new Label("No projects available");
        noProjectsLabel.setStyle("-fx-font-size: 16px;");

        Button addProjectButton = new Button("Add Project");
        addProjectButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Project File");
            fileChooser.showOpenDialog(newWindow);
        });

        Button backButton = new Button("Back to Dashboard");
        backButton.setOnAction(e -> newWindow.close());

        VBox layout = new VBox(10, noProjectsLabel, addProjectButton, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene secondScene = new Scene(layout, 300, 200);

        newWindow.setScene(secondScene);
        newWindow.show();
    }

    private void openReportsWindow() {
        Stage newWindow = new Stage();
        newWindow.setTitle("Reports");

        Label noReportsLabel = new Label("No reports available currently");
        noReportsLabel.setStyle("-fx-font-size: 16px;");

        Button addReportButton = new Button("Add Report");
        addReportButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Report File");
            fileChooser.showOpenDialog(newWindow);
        });

        Button backButton = new Button("Back to Dashboard");
        backButton.setOnAction(e -> newWindow.close());

        VBox layout = new VBox(10, noReportsLabel, addReportButton, backButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene secondScene = new Scene(layout, 300, 200);

        newWindow.setScene(secondScene);
        newWindow.show();
    }

    private void launchPlanningPoker() {
        try {
            Application pokerApp = new PlanningPoker(userStories); // Pass user stories to PlanningPoker
            Stage stage = new Stage();
            pokerApp.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
