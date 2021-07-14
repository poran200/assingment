import dao.ClassRepository;
import dao.StudentRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Class;
import model.Student;

import java.util.Optional;

public class Main  extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        StudentRepository repository = new StudentRepository();
        ClassRepository classRepository = new ClassRepository();
        classRepository.save(new Class("Class Seven"));
        classRepository.save(new Class("Class Eight"));
        classRepository.save(new Class("Class Nine"));
        classRepository.save(new Class("Class Ten"));
        repository.save(new Student("Nabil","Choudury"));
        repository.save(new Student("Shah","Jalal"));
        repository.save(new Student("Najmul","Hasan"));
        repository.save(new Student("Zawad","Zamil"));
//        classRepository.addStudent(1,1);


        Parent root = FXMLLoader.load(getClass().getResource("view/Home.fxml"));
        primaryStage.setTitle("Demo Application");
        primaryStage.setScene(new Scene(root, 1200, 600));
        primaryStage.show();
//        StudentRepository repository = new StudentRepository();
//        Optional<Student> optional = repository.findById(1);
//        optional.ifPresent(student -> student.getClasses().forEach(System.out::println));
    }
}
