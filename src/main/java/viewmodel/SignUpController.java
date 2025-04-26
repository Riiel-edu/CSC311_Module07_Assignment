package viewmodel;

import dao.DbConnectivityClass;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.UserLogins;

import java.util.LinkedList;

public class SignUpController {

    @FXML
    TextField usernameField;

    @FXML
    TextField passwordField;

    boolean canCreate;

    public void createNewAccount(ActionEvent actionEvent) {

        canCreate = true;
        DbConnectivityClass dao = new DbConnectivityClass();
        dao.connectToDatabase();
        ObservableList<UserLogins> users = dao.getUserLogins();
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(!username.isEmpty() && !password.isEmpty()) {
            for (UserLogins user : users) {
                if (user.getUsername().equals(username)) {
                    canCreate = false;
                    break;
                }
            }

            if(canCreate) {
                dao.insertUserLogin(username, password);

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
                    Scene scene = new Scene(root, 900, 600);
                    scene.getStylesheets().add(getClass().getResource("/css/lightTheme.css").toExternalForm());
                    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    window.setScene(scene);
                    window.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void goBack(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("/css/lightTheme.css").toExternalForm());
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
