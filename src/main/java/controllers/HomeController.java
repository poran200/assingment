package controllers;/*
 * @created 7/12/2021
 *
 * @Author Poran chowdury
 */

import dao.ClassRepository;
import dao.StudentRepository;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Class;
import model.Student;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {


    public TextField classNameTextBox;
    public ChoiceBox<Student> studentChoiceBox;
    public Label selectclassStatus;
    public Label statusText;
    public TableView<Student> allStudentTable;
    public TableColumn<Student, Integer> studentRoleColumn;
    public TableColumn<Student, String> sudentfristNameColumn;
    public TableColumn<Student, String> studnetlastNameColumn;
    public TableColumn<Student, Class> studentClassColumn;
    public TextField studentFristNameTextBox;
    public TextField studnetLastNameTextBox;
    public ChoiceBox<Class> studentClassChoiceBox;
    public Button saveButton;
    public Button updateButton;
    public Button deletedButton;

    public Button classCreateButton;
    public Button classDeleteButton;
    public Button clearButton;
    public Button classUpdateButton;


    StudentRepository studentRepository;
    ClassRepository classRepository;
    public TableColumn<Class, Integer> roll;
    public TableColumn<Student, String> firstName;
    public TableColumn<Student, String> lastName;
    public TableView<Student> studentTable;

    // student class table view
    public TableView<Class> classTableView;
    public TableColumn<Class, Integer> classId;
    public TableColumn<Class, String> className;
    public ObservableList<Student> studentObservableList;
    public ObservableList<Class> studentClassList;

    public HomeController() {
        this.studentRepository = new StudentRepository();
        this.classRepository = new ClassRepository();
    }

    ObservableList<Class> getClasses() {
        List<Class> classes = classRepository.findAll();
        return FXCollections.observableArrayList((classes));
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roll.setCellValueFactory(new PropertyValueFactory<>("role"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        setClassTableData();
        classId.setCellValueFactory(new PropertyValueFactory<>("id"));
        className.setCellValueFactory(new PropertyValueFactory<>("name"));

        setStudnetTableData();

        classTableView.setOnMouseClicked(event -> setStudentClassView());
        studentChoiceBox.setValue(null);
        studentChoiceBox.setItems(getAllStudnets());
        studentClassChoiceBox.setItems(getClasses());

        allStudentTable.setOnMouseClicked(event -> setAllPropertyOnStudentFrom());
        updateButton.setDisable(true);
        deletedButton.setDisable(true);
        classUpdateButton.setDisable(true);
        classDeleteButton.setDisable(true);

    }

    private void setAllPropertyOnStudentFrom() {
        Student selectedItem = allStudentTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            buttonDispayUpdate();
            studentFristNameTextBox.setText(selectedItem.getFirstName());
            studnetLastNameTextBox.setText(selectedItem.getLastName());
            studentClassChoiceBox.setValue(selectedItem.getaClass());
        }

    }

    private void buttonDispayUpdate() {
        updateButton.setDisable(false);
        deletedButton.setDisable(false);
        saveButton.setDisable(true);
    }

    private void setStudnetTableData() {
        studentRoleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        sudentfristNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        studnetlastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        studentClassColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue().getaClass()));

        allStudentTable.setItems((FXCollections.observableArrayList(studentRepository.findAll())));
    }


    ObservableList<Student> getAllStudnets() {
        return FXCollections.observableArrayList(studentRepository.findAll())
                .filtered(student -> student.getaClass() == null);
    }

    private void setStudentClassView() {
        classUpdateButton.setDisable(false);
        classDeleteButton.setDisable(false);
        classCreateButton.setDisable(true);
        Class selectedClass = classTableView.getSelectionModel().getSelectedItem();
        selectclassStatus.setText(selectedClass.getName() + " Selected");
        classNameTextBox.setText(selectedClass.getName());
        studentTable.getItems().clear();
        studentTable.setItems(getStudentListOfClass(selectedClass.getId()));
    }

    public ObservableList<Student> getStudentListOfClass(int role) {
        return classRepository.findById(role).map(Class::getObservableStudentList).orElse(null);
    }


    public void createClassButton(ActionEvent actionEvent) {
        String clasName = classNameTextBox.getText();
        if (clasName != null && !clasName.isEmpty()) {
            classRepository.save(new Class(clasName));
            classNameTextBox.clear();
            setClassTableData();
        }
    }

    public void updateClassButtonActionHandler(ActionEvent actionEvent) {
        Class aClass = classTableView.getSelectionModel().getSelectedItem();
        if (aClass != null){
            String updateClassName = classNameTextBox.getText();
            classRepository.updateClassName(aClass.getId(),updateClassName);
            classNameTextBox.clear();
            setClassTableData();
        }
    }

    public void addStudentButtonActionHandeler(ActionEvent actionEvent) {
        Class aClass = classTableView.getSelectionModel().getSelectedItem();
        Student student = studentChoiceBox.getSelectionModel().getSelectedItem();
        if (aClass != null && student != null) {
            classRepository.addStudent(aClass.getId(), student.getRole());
            statusText.setText("Add student Successfully");
            studentChoiceBox.setValue(null);
            studentChoiceBox.setItems(getAllStudnets());
            setStudentClassView();
        } else {
            statusText.setText("Class and Student no selected");
            System.out.println("Class and Student no selected");
        }

    }


    public void removeStudentButtonActionHandler(ActionEvent actionEvent) {
        Class aClass = classTableView.getSelectionModel().getSelectedItem();
        Student student = studentTable.getSelectionModel().getSelectedItem();
        if (aClass != null && student != null) {
            classRepository.removeStudnet(aClass.getId(), student);
            setStudentClassView();
            statusText.setText("Student remove successfully form  " + aClass.getName());
        } else {
            statusText.setText("Class and Student no selected");
            System.out.println("Class and Student no selected");
        }
    }

    public void onStudnetSaveButtonAcitionHandler(ActionEvent actionEvent) {
        String firstName = studentFristNameTextBox.getText();
        String lastName = studnetLastNameTextBox.getText();
        Class aClass = studentClassChoiceBox.getSelectionModel().getSelectedItem();
        if (firstName != null || lastName != null) {
            studentRepository.save(new Student(firstName, lastName, aClass));
            refreshStudentTable();
            clearField();
        }
    }

    private void clearField() {
        studnetLastNameTextBox.clear();
        studentFristNameTextBox.clear();
        studentClassChoiceBox.setValue(null);
    }

    public void studentUpdateButtonActionHandeler(ActionEvent actionEvent) {
        Student selectedItem = allStudentTable.getSelectionModel().getSelectedItem();
        selectedItem.setFirstName(studentFristNameTextBox.getText());
        selectedItem.setLastName(studnetLastNameTextBox.getText());
        selectedItem.setaClass(studentClassChoiceBox.getValue());
        System.out.println("selectedItem = " + selectedItem);
        studentRepository.update(selectedItem.getRole(), selectedItem);
        clearField();
        refreshStudentTable();
        updateButtonOnCompateOparation();
    }

    private void updateButtonOnCompateOparation() {
        saveButton.setDisable(false);
        updateButton.setDisable(true);
        deletedButton.setDisable(true);
    }

    public void studnetDeleteButtonActionHandler(ActionEvent actionEvent) {
        Student selectedItem = allStudentTable.getSelectionModel().getSelectedItem();
        studentRepository.deleteById(selectedItem.getRole());
        clearField();
        refreshStudentTable();
        updateButtonOnCompateOparation();

    }

    public void onRefreshButtonActionHandler(ActionEvent actionEvent) {
        refreshStudentTable();
    }

    private void refreshStudentTable() {
        allStudentTable.getItems().clear();
        setStudnetTableData();
    }

    public void studentChoiceListOnmouseClickedActionHandel(MouseEvent mouseEvent) {
//        if (classTableView.getSelectionModel().getSelectedItem()!=null){
            System.out.println("student choice box on select");
            ObservableList<Student> allStudnets = getAllStudnets();
            allStudnets.forEach(System.out::println);
            studentChoiceBox.setItems(allStudnets);
//        }

    }

    public void classDeleteButtonActionHandle(ActionEvent actionEvent) {
        Class aClass = classTableView.getSelectionModel().getSelectedItem();
        classRepository.deleteById(aClass.getId());
        classNameTextBox.clear();
        setClassTableData();

    }

    private void setClassTableData() {
        classTableView.setItems(getClasses());
    }

    public void clearButtonOnActionHandle(ActionEvent actionEvent) {
        classDeleteButton.setDisable(true);
        classUpdateButton.setDisable(true);
        classCreateButton.setDisable(false);
        classNameTextBox.clear();
    }
}
