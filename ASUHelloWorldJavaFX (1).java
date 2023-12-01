package asuHelloWorldJavaFX;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class ASUHelloWorldJavaFX extends Application {

    private Stage primaryStage;
    private Map<String, String> userCredentials = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("EffortLogger - Login");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        // Username Label
        Label usernameLabel = new Label("Username:");
        grid.add(usernameLabel, 0, 0);

        // Username TextField
        TextField usernameTextField = new TextField();
        grid.add(usernameTextField, 1, 0);

        // Password Label
        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 1);

        // Password PasswordField
        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 1);

        // Login Button
        Button loginButton = new Button("Login");
        grid.add(loginButton, 1, 2);
        loginButton.setOnAction(e -> {
            String username = usernameTextField.getText();
            String password = passwordField.getText();

            if (isValidLogin(username, password)) {
                // Launch the Dashboard application
                DashBoard dashboard = new DashBoard();
                try {
                    dashboard.start(new Stage()); // Start the Dashboard application in a new stage
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                showAlert("Invalid username or password.");
            }
        });

        // Sign-Up Link
        Hyperlink signUpLink = new Hyperlink("Sign Up");
        grid.add(signUpLink, 1, 3);
        signUpLink.setOnAction(event -> showSignupForm());

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean isValidLogin(String username, String password) {
        return userCredentials.containsKey(username) && userCredentials.get(username).equals(password);
    }

    private void showSignupForm() {
        Stage signupStage = new Stage();
        signupStage.setTitle("Sign Up");

        GridPane signupGrid = new GridPane();
        signupGrid.setHgap(10);
        signupGrid.setVgap(10);
        signupGrid.setPadding(new Insets(20, 20, 20, 20));

        Label emailLabel = new Label("Username:");
        TextField emailTextField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField signupPasswordField = new PasswordField();

        Button signUpButton = new Button("Sign Up");
        signUpButton.setOnAction(e -> {
            String email = emailTextField.getText();
            String password = signupPasswordField.getText();

            if (!email.isEmpty() && !password.isEmpty()) {
                userCredentials.put(email, password);
                showAlert("Signed up successfully!");
                signupStage.close();
            } else {
                showAlert("Please fill all fields.");
            }
        });

        signupGrid.add(emailLabel, 0, 0);
        signupGrid.add(emailTextField, 1, 0);
        signupGrid.add(passwordLabel, 0, 1);
        signupGrid.add(signupPasswordField, 1, 1);
        signupGrid.add(signUpButton, 1, 2);

        Scene signupScene = new Scene(signupGrid, 300, 200);
        signupStage.setScene(signupScene);
        signupStage.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
