package viewmodel;

import dao.DbConnectivityClass;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Animal;
import model.Person;
import service.MyLogger;
import service.UserSession;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DB_GUI_Controller implements Initializable {

    @FXML
    TextField name, species, date_of_birth, exhibit;
    @FXML
    ComboBox<String> animal_class;
    @FXML
    ImageView img_view;
    @FXML
    MenuBar menuBar;
    @FXML
    Button editRecord, deleteRecord;
    @FXML
    MenuItem editItem, deleteItem;
    @FXML
    private TableView<Animal> tv;
    @FXML
    private TableColumn<Animal, Integer> tv_id;
    @FXML
    private TableColumn<Animal, String> tv_name, tv_class, tv_species, tv_dob, tv_exhibit;
    private final DbConnectivityClass cnUtil = new DbConnectivityClass();
    private ObservableList<Animal> data;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        animal_class.setItems(FXCollections.observableArrayList("Mammal", "Bird", "Fish", "Reptile", "Amphibian", "Invertebrate"));
        cnUtil.setCurrUser(UserSession.getUserName());
        data = cnUtil.getAnimals();

        try {
            tv_id.setCellValueFactory(new PropertyValueFactory<>("Id"));
            tv_name.setCellValueFactory(new PropertyValueFactory<>("Name"));
            tv_class.setCellValueFactory(new PropertyValueFactory<>("AnimalClass"));
            tv_species.setCellValueFactory(new PropertyValueFactory<>("Species"));
            tv_dob.setCellValueFactory(new PropertyValueFactory<>("DateOfBirth"));
            tv_exhibit.setCellValueFactory(new PropertyValueFactory<>("Exhibit"));

            tv.setItems(data);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void addNewRecord() {
            if(checkNameConstraint(name.getText()) && checkNameConstraint(species.getText())  && checkDateConstraint(date_of_birth.getText()) && checkNameConstraint(exhibit.getText())) {
                cnUtil.insertAnimal(name.getText(), animal_class.getValue(), species.getText(),
                        date_of_birth.getText(), exhibit.getText());

                data.add(new Animal((data.size() + 1), name.getText(), animal_class.getValue(), species.getText(),
                        date_of_birth.getText(), exhibit.getText()));
                clearForm();
            }
    }

    @FXML
    protected void clearForm() {
        name.setText("");
        animal_class.setValue("");
        species.setText("");
        date_of_birth.setText("");
        exhibit.setText("");
    }

    @FXML
    protected void logOut(ActionEvent actionEvent) {
        UserSession.cleanUserSession();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("/css/lightTheme.css").getFile());
            Stage window = (Stage) menuBar.getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void closeApplication() {
        System.exit(0);
    }

    @FXML
    protected void displayAbout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/about.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root, 600, 500);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void editRecord() {
        if(checkNameConstraint(name.getText()) && checkNameConstraint(species.getText())  && checkDateConstraint(date_of_birth.getText()) && checkNameConstraint(exhibit.getText())) {
            Animal a = tv.getSelectionModel().getSelectedItem();
            int index = data.indexOf(a);
            Animal a2 = new Animal(index + 1, name.getText(), animal_class.getValue(), species.getText(),
                    date_of_birth.getText(), exhibit.getText());
            cnUtil.editAnimal(a.getId(), a2.getName(), a2.getSpecies(), a2.getDateOfBirth(), a2.getAnimalClass(), a2.getExhibit());
            data.remove(a);
            data.add(index, a2);
            tv.getSelectionModel().select(index);
        }
    }

    @FXML
    protected void deleteRecord() {
        Animal a = tv.getSelectionModel().getSelectedItem();
        int index = data.indexOf(a);
        cnUtil.removeAnimal(a.getId());
        data.remove(index);

        tv.getSelectionModel().select(index);
    }

    @FXML
    protected void showImage() {
        File file = (new FileChooser()).showOpenDialog(img_view.getScene().getWindow());
        if (file != null) {
            img_view.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    protected void addRecord() {
        showSomeone();
    }

    @FXML
    protected void selectedItemTV(MouseEvent mouseEvent) {
        Animal a = tv.getSelectionModel().getSelectedItem();
        name.setText(a.getName());
        species.setText(a.getSpecies());
        date_of_birth.setText(a.getDateOfBirth());
        animal_class.setValue(a.getAnimalClass());
        exhibit.setText(a.getExhibit());

        //editItem.setDisable(false); deleteItem.setDisable(false);
        //editRecord.setDisable(false); deleteRecord.setDisable(false);
    }

    public void lightTheme(ActionEvent actionEvent) {
        try {
            Scene scene = menuBar.getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.getScene().getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/css/lightTheme.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            System.out.println("light " + scene.getStylesheets());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void darkTheme(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) menuBar.getScene().getWindow();
            Scene scene = stage.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/css/darkTheme.css").toExternalForm());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showSomeone() {
        Dialog<Results> dialog = new Dialog<>();
        dialog.setTitle("New User");
        dialog.setHeaderText("Please specify…");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField textField1 = new TextField("Name");
        TextField textField2 = new TextField("Last Name");
        TextField textField3 = new TextField("Email ");
        ObservableList<Major> options =
                FXCollections.observableArrayList(Major.values());
        ComboBox<Major> comboBox = new ComboBox<>(options);
        comboBox.getSelectionModel().selectFirst();
        dialogPane.setContent(new VBox(8, textField1, textField2,textField3, comboBox));
        Platform.runLater(textField1::requestFocus);
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new Results(textField1.getText(),
                        textField2.getText(), comboBox.getValue());
            }
            return null;
        });
        Optional<Results> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((Results results) -> {
            MyLogger.makeLog(
                    results.fname + " " + results.lname + " " + results.major);
        });
    }

    private static enum Major { Business, CSC, CPIS }

    private static class Results {

        String fname;
        String lname;
        Major major;

        public Results(String name, String date, Major venue) {
            this.fname = name;
            this.lname = date;
            this.major = venue;
        }
    }

    /**
     * Checks whether the Name constraint "[a-zA-Z]{2,25}" is met.
     * @param text The text being checked.
     * @return True if the text matches the constraint, otherwise false.
     */
    public static boolean checkNameConstraint(String text) {
        String pattern = "[a-zA-Z]{2,25}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * Checks whether the Name constraint "[01][0-9]/[0123][0-9]/[0-9]{4}" is met.
     * @param text The text being checked.
     * @return True if the text matches the constraint, otherwise false.
     */
    public static boolean checkDateConstraint(String text) {
        String pattern = "[01][0-9]/[0123][0-9]/[0-9]{4}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text);
        return m.matches();
    }

}