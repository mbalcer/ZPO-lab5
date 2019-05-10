package controller;

import annotation.Named;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import table.TableFields;
import utility.CastValue;
import utility.ClassFinder;
import utility.InfoDialog;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
    private ComboBox<Field> cbField;

    @FXML
    private TextField tfValue;

    private ObservableList<String> classObservableList;
    private ObservableList<TableFields> fieldObservableList;
    private ObservableList<Field> fieldsInComboBox;
    private ObservableList<Object> objectsList;
    private Class<?> selectedClass;
    private InfoDialog info;

    public void initialize() {
        addDataToTableFields();
        fieldsInComboBox = FXCollections.observableArrayList();
        objectsList = FXCollections.observableArrayList();
        info = new InfoDialog();
        fillClassComboBox();
        applyAnnotations();
    }

    @FXML
    void chooseClass() {
        String className = cbClassName.getSelectionModel().getSelectedItem();
        try {
            selectedClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            info.showAlert("Error", "There isn't class with the given name");
            return;
        }

        objectsList.clear();
        createNewObject();
        cbObject.getSelectionModel().select(objectsList.get(0));

        Method[] methods = selectedClass.getDeclaredMethods();
        Field[] fields = selectedClass.getDeclaredFields();

        fillFieldTable(fields, methods, objectsList.get(0));
        fillFieldsComboBox(fields);
    }

    @FXML
    void chooseObject() {
        Object selectedObject = cbObject.getSelectionModel().getSelectedItem();
        if (selectedObject != null) {
            Method[] methods = selectedObject.getClass().getDeclaredMethods();
            Field[] fields = selectedObject.getClass().getDeclaredFields();

            fillFieldTable(fields, methods, selectedObject);
        }
    }

    @FXML
    void createNewObject() {
        try {
            Object newObject = selectedClass.newInstance();
            objectsList.add(newObject);
            fillObjectsComboBox();
        } catch (ReflectiveOperationException e) {
            info.showAlert("Error", "Error when creating a class instance");
            return;
        } catch (NullPointerException e) {
            info.showAlert("Info", "First choose class");
            return;
        }
    }

    @FXML
    void deleteObject() {
        Object deleteObject = cbObject.getSelectionModel().getSelectedItem();
        objectsList.remove(deleteObject);
        fillObjectsComboBox();
    }

    @FXML
    void setValueToField() {
        Field selectedField = cbField.getSelectionModel().getSelectedItem();
        Object selectedObject = cbObject.getSelectionModel().getSelectedItem();
        String nameMethod = null;
        try {
            nameMethod = "set" + selectedField.getName().substring(0, 1).toUpperCase() + selectedField.getName().substring(1);
        } catch (NullPointerException e) {
            info.showAlert("Info", "First enter the name of the class");
            return;
        }

        Method method = null;
        try {
            method = selectedObject.getClass().getDeclaredMethod(nameMethod, selectedField.getType());
        } catch (NoSuchMethodException e) {
            info.showAlert("Error", "The method entered doesn't exist");
            return;
        } catch (NullPointerException e) {
            info.showAlert("Error", "First choose object");
            return;
        }

        Class<?> parameterType = method.getParameterTypes()[0];
        Object valueFieldAfterParse = new CastValue().cast(parameterType, tfValue.getText());

        if (valueFieldAfterParse == null)
            return;

        try {
            method.invoke(selectedObject, valueFieldAfterParse);
        } catch (ReflectiveOperationException e) {
            info.showAlert("Error", "Error when invoking the method");
        }


        Method[] methods = selectedClass.getDeclaredMethods();
        Field[] fields = selectedClass.getDeclaredFields();

        fillFieldTable(fields, methods, selectedObject);
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

    private void fillFieldsComboBox(Field[] fields) {
        fieldsInComboBox.clear();
        Arrays.stream(fields)
                .forEach(field -> {
                    field.setAccessible(true);
                    fieldsInComboBox.add(field);
                });

        cbField.setItems(fieldsInComboBox);
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
