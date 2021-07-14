package assingment.javafx.model;/*
 * @created 7/11/2021
 *
 * @Author Poran chowdury
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(mappedBy = "aClass", orphanRemoval = false, fetch = FetchType.EAGER)
    private Set<Student> students;

    public Class() {
    }

    public Class(String name) {
        this.name = name;
    }

    public Class(Integer id) {
        this.id = id;
    }

    public ObservableList<Student> getObservableStudentList() {
        ObservableList<Student> observableArrayList = FXCollections.observableArrayList();
        observableArrayList.addAll(students);
        return observableArrayList;
    }

    public void addStudent(Student student) {
        if (this.students == null) {
            this.students = new HashSet<>();
        }
        if (!students.contains(student)) {
            students.add(student);
            student.setaClass(this);
        }

    }

    public void removeStudent(Student student) {
        this.students.remove(student);
        student.setaClass(null);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Class)) return false;

        Class aClass = (Class) o;

        if (id != aClass.id) return false;
        return Objects.equals(name, aClass.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "[" +
                "id=" + id +
                ", name='" + name + '\'' +
                ']';
    }

    @PreRemove
    public void removeAll() {
        for (Student student : this.students) {
            student.setaClass(null);
        }
    }
}
