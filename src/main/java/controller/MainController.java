package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import utility.ClassFinder;

public class MainController {

    @FXML
    private ComboBox<String> cbClassName;

    @FXML
    private TableView<?> tableField;

    @FXML
    private TableColumn<?, ?> tcNo;

    @FXML
    private TableColumn<?, ?> tcField;

    @FXML
    private TableColumn<?, ?> tcValue;

    @FXML
    private ComboBox<?> cbObject;

    @FXML
    private Button btnCreateObject;

    @FXML
    private Button btnDeleteObject;

    @FXML
    private ComboBox<?> cbField;

    @FXML
    private TextField tfValue;

    @FXML
    private Button btnSetValue;

    private ObservableList<String> classObservableList;

    @FXML
    void chooseClass() {

    }

    @FXML
    void chooseField() {

    }

    @FXML
    void chooseObject() {

    }

    @FXML
    void createNewObject() {

    }

    @FXML
    void deleteObject() {

    }

    @FXML
    void setValueToField() {

    }

    public void initialize() {
        fillClassComboBox();
    }

    public void fillClassComboBox() {
        ClassFinder classFinder = new ClassFinder();
        classObservableList = FXCollections.observableArrayList(classFinder.find("model"));

        cbClassName.setItems(classObservableList);
    }

}
