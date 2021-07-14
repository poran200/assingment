package assingment.javafx.dao;/*
 * @created 7/11/2021
 *
 * @Author Poran chowdury
 */

import assingment.javafx.model.Student;
import assingment.javafx.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StudentRepository implements CRUDDAO<Student, Integer> {

    Session session = null;
    Transaction transaction = null;

    @Override
    public Student save(Student student) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(student);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            closeTransaction();
        } finally {
            closeSession();
        }
        return student;
    }

    @Override
    public Optional<Student> findById(Integer id) {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        try {
            Student query = session.find(Student.class, id);
            transaction.commit();
            return Optional.ofNullable(query);
        } catch (Exception e) {
            e.printStackTrace();
            closeTransaction();
        } finally {
            closeSession();
        }
        return Optional.empty();
    }

    @Override
    public List<Student> findAll() {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        try {
            List<Student> resultList = (List<Student>) session.createQuery("from " + Student.class.getSimpleName()).getResultList();
            transaction.commit();
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            closeTransaction();
        } finally {
            closeSession();
        }

        return Collections.emptyList();
    }

    @Override

    public Student update(Integer id, Student student) {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        try {
            Student updateStudent = session.find(Student.class, id);
            updateStudent.setRole(id);
            updateStudent.setFirstName(student.getFirstName());
            updateStudent.setLastName(student.getLastName());
            updateStudent.setaClass(student.getaClass());
            session.update(updateStudent);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            closeTransaction();
        } finally {
            closeSession();
        }
        return null;
    }

    @Override
    public boolean deleteById(Integer id) {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        try {
            session.createQuery("delete from " + Student.class.getSimpleName() + " where role=:role")
                    .setParameter("role", id).executeUpdate();
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            closeTransaction();
        } finally {
            closeSession();
        }
        return false;
    }

//    public void courseRegistration(Student student, Class aClass) {
//        try {
//            session = getSessionFactory().openSession();
//            transaction = session.beginTransaction();
//            student.addClass(aClass);
//            aClass.addStudent(student);
//            session.merge(student);
//            session.merge(aClass);
//            transaction.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            closeTransaction();
//        } finally {
//            closeSession();
//        }
//
//    }

    private void closeSession() {
        if (session != null) {
            session.close();
        }
    }

    private void closeTransaction() {
        if (transaction != null) {
            transaction.rollback();
        }
    }
}
