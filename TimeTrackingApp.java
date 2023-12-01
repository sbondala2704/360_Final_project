package asuHelloWorldJavaFX;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TimeTrackingApp extends Application {

    private Timeline timer;
    private Duration elapsedTime = Duration.ZERO;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("EffortLogger - Time Tracking");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        // Timer Label
        Label timerLabel = new Label("Elapsed Time: 00:00:00");
        grid.add(timerLabel, 0, 0, 2, 1);

        // Start Button
        Button startButton = new Button("Start");
        grid.add(startButton, 0, 1);

        // Stop Button
        Button stopButton = new Button("Stop");
        grid.add(stopButton, 1, 1);

        // Manual Entry Label
        Label manualEntryLabel = new Label("Manual Entry:");
        grid.add(manualEntryLabel, 0, 2);

        // Manual Entry TextField
        TextField manualEntryTextField = new TextField();
        grid.add(manualEntryTextField, 1, 2);

        // Submit Manual Entry Button
        Button submitManualEntryButton = new Button("Submit");
        grid.add(submitManualEntryButton, 2, 2);

        // Event handling for the Start button
        startButton.setOnAction(e -> startTimer(timerLabel));

        // Event handling for the Stop button
        stopButton.setOnAction(e -> stopTimer());

        // Event handling for the Submit Manual Entry button
        submitManualEntryButton.setOnAction(e -> handleManualEntry(manualEntryTextField.getText(), timerLabel));

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to start the timer
    private void startTimer(Label timerLabel) {
        stopTimer(); // Stop any existing timer before starting a new one
        elapsedTime = Duration.ZERO; // Reset elapsed time
        timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            elapsedTime = elapsedTime.add(Duration.seconds(1));
            timerLabel.setText("Elapsed Time: " + formatTime(elapsedTime));
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    // Method to stop the timer
    private void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    // Method to handle manual time entry
    private void handleManualEntry(String manualEntry, Label timerLabel) {
        try {
            long seconds = Long.parseLong(manualEntry);
            elapsedTime = Duration.seconds(seconds);
            timerLabel.setText("Elapsed Time: " + formatTime(elapsedTime));
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("Invalid manual entry. Please enter a valid number of seconds.");
            alert.showAndWait();
        }
    }

    // Method to format time as HH:mm:ss
    private String formatTime(Duration time) {
        long seconds = (long) time.toSeconds();
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
