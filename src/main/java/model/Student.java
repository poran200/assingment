package model;/*
 * @created 7/11/2021
 *
 * @Author Poran chowdury
 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int role;
    private String firstName;
    private String lastName;
    @ManyToOne
    private Class aClass;
    public Student() {
    }



    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(int role, String firstName, String lastName) {
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(String firstName, String lastName, Class aClass) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.aClass = aClass;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;

        Student student = (Student) o;

        if (role != student.role) return false;
        if (!Objects.equals(firstName, student.firstName)) return false;
        return Objects.equals(lastName, student.lastName);
    }

    @Override
    public int hashCode() {
        int result = role;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "[" +
                "Role=" + role +
                ", First Name='" + firstName + '\'' +
                ", Last Name='" + lastName + '\'' +
                ", Class='" + aClass + '\'' +
                ']';
    }


}
