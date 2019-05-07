package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import table.TableFields;
import utility.ClassFinder;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class MainController {

    @FXML
    private ComboBox<String> cbClassName;

    @FXML
    private TableView<TableFields> tableField;

    @FXML
    private TableColumn<TableFields, Integer> tcNo;

    @FXML
    private TableColumn<TableFields, String> tcField;

    @FXML
    private TableColumn<TableFields, Object> tcValue;

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
    private ObservableList<TableFields> fieldObservableList;

    private Object object;

    @FXML
    void chooseClass() {
        String className = cbClassName.getSelectionModel().getSelectedItem();
        Class<?> newClass = null;
        try {
            newClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            object = newClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Method[] methods = newClass.getDeclaredMethods();
        Field[] fields = newClass.getDeclaredFields();

        fillFieldTable(fields, methods);
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
        addDataToTableFields();
        fillClassComboBox();
    }

    private void fillClassComboBox() {
        ClassFinder classFinder = new ClassFinder();
        classObservableList = FXCollections.observableArrayList(classFinder.find("model"));

        cbClassName.setItems(classObservableList);
    }

    private void fillFieldTable(Field[] fields, Method[] methods) {
        fieldObservableList.clear();
        AtomicInteger id = new AtomicInteger(1);
        Arrays.stream(fields)
                .filter(field -> Arrays.stream(methods)
                        .filter(method -> method.getName().startsWith("get"))
                        .anyMatch(method -> method.getName().toLowerCase().endsWith(field.getName().toLowerCase())))
                .forEach(field -> {
                    field.setAccessible(true);
                    Object fieldValue = null;
                    try {
                        fieldValue = field.get(object);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    fieldObservableList.add(new TableFields(id.getAndIncrement(), field.getName(), fieldValue));
                });
        tableField.setItems(fieldObservableList);
    }

    private void addDataToTableFields() {
        fieldObservableList = FXCollections.observableArrayList();
        tcNo.setCellValueFactory(new PropertyValueFactory<>("no"));
        tcField.setCellValueFactory(new PropertyValueFactory<>("field"));
        tcValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        tableField.setItems(fieldObservableList);
    }

}
