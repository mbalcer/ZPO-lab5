package controller;

import annotation.Named;
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
    @Named("No.")
    private TableColumn<TableFields, Integer> tcNo;

    @FXML
    @Named("Field")
    private TableColumn<TableFields, String> tcField;

    @FXML
    @Named("Value")
    private TableColumn<TableFields, Object> tcValue;

    @FXML
    private ComboBox<Object> cbObject;

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
    private ObservableList<Object> objectsList;
    private Class<?> selectedClass;

    public void initialize() {
        addDataToTableFields();
        fillClassComboBox();
        applyAnnotations();
    }

    @FXML
    void chooseClass() {
        String className = cbClassName.getSelectionModel().getSelectedItem();
        try {
            selectedClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        objectsList = FXCollections.observableArrayList();

        try {
            Object newObject = selectedClass.newInstance();
            objectsList.add(newObject);
            fillObjectsComboBox();
            cbObject.getSelectionModel().select(newObject);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Method[] methods = selectedClass.getDeclaredMethods();
        Field[] fields = selectedClass.getDeclaredFields();

        fillFieldTable(fields, methods, objectsList.get(0));
    }

    @FXML
    void chooseObject() {
        Object selectedObject = cbObject.getSelectionModel().getSelectedItem();
        Method[] methods = selectedObject.getClass().getDeclaredMethods();
        Field[] fields = selectedObject.getClass().getDeclaredFields();

        fillFieldTable(fields, methods, selectedObject);
    }

    @FXML
    void createNewObject() {
        try {
            Object newObject = selectedClass.newInstance();
            objectsList.add(newObject);
            fillObjectsComboBox();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteObject() {
        Object deleteObject = cbObject.getSelectionModel().getSelectedItem();
        objectsList.remove(deleteObject);
        fillObjectsComboBox();
    }


    @FXML
    void chooseField() {

    }

    @FXML
    void setValueToField() {

    }

    private void applyAnnotations() {
        Field[] fieldsController = this.getClass().getDeclaredFields();
        Arrays.stream(fieldsController)
                .filter(field -> field.isAnnotationPresent(Named.class))
                .filter(field -> field.getType().equals(TableColumn.class))
                .forEach(field -> {
                    Named nameAnnotation = field.getAnnotation(Named.class);
                    try {
                        TableColumn tableColumn = (TableColumn) field.get(this);
                        tableColumn.setText(nameAnnotation.value());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void fillClassComboBox() {
        ClassFinder classFinder = new ClassFinder();
        classObservableList = FXCollections.observableArrayList(classFinder.find("model"));

        cbClassName.setItems(classObservableList);
    }

    private void fillFieldTable(Field[] fields, Method[] methods, Object object) {
        fieldObservableList.clear();
        AtomicInteger id = new AtomicInteger(1);
        Arrays.stream(fields)
                .filter(field -> Arrays.stream(methods)
                        .filter(method -> method.getName().startsWith("get") || method.getName().startsWith("is"))
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

    private void fillObjectsComboBox() {
        cbObject.setItems(objectsList);
    }

    private void addDataToTableFields() {
        fieldObservableList = FXCollections.observableArrayList();
        tcNo.setCellValueFactory(new PropertyValueFactory<>("no"));
        tcField.setCellValueFactory(new PropertyValueFactory<>("field"));
        tcValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        tableField.setItems(fieldObservableList);
    }



}
