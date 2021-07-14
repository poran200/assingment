package dao;/*
 * @created 7/11/2021
 *
 * @Author Poran chowdury
 */

import model.Class;
import model.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ClassRepository implements CRUDDAO<Class, Integer> {

    Session session =null;
    Transaction transaction = null;

    @Override
    public Class save(Class aClass) {

        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(aClass);
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            closeTransaction();
        }finally {
            closeSession();
        }
        return null;
    }



    @Override
    public Optional<Class> findById(Integer id) {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
       try{
           Class result = session.find(Class.class,id);
           transaction.commit();
           return Optional.ofNullable(result);
       }catch (Exception e){
           e.printStackTrace();
           closeTransaction();
       }finally {
           closeSession();
       }
       return Optional.empty();
    }

    @Override
    public List<Class> findAll() {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
       try{
           List<Class> resultList =(List<Class>) session.createQuery("from " + Class.class.getSimpleName()).getResultList();
           transaction.commit();
           return resultList;
       }catch (Exception e){
           e.printStackTrace();
           closeTransaction();

       }finally {
           closeSession();
       }
       return Collections.emptyList();
    }

    @Override
    public Class update(Integer id, Class object) {
        return null;
    }

    public void addStudent(int classId, int studnetId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {

           Class aClass = findById(classId).get();
           Student student = session.find(Student.class, studnetId);
           aClass.addStudent(student);
           student.setaClass(aClass);
           session.update(student);
           session.update(aClass);
           transaction.commit();
       }catch (Exception e){
           e.printStackTrace();
           if (transaction !=null){
               transaction.rollback();
           }
       }finally {
          if(session != null){
              session.close();
          }
       }
    }


    public Class updateClassName(Integer id,String  className) {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        try{
            Class aClass = session.get(Class.class, id);
            aClass.setId(aClass.getId());
            aClass.setName(className);
            session.update(aClass);
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            closeTransaction();
        }finally {
            closeSession();
        }
        return null;
    }

    @Override
    public boolean deleteById(Integer id) {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        try {
            session.delete(session.get(Class.class,id));
            transaction.commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            closeTransaction();
        }finally {
            closeSession();
        }
        return false;
    }
    public void removeStudnet(Integer classId,Student student){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Class aClass = session.find(Class.class, classId);
            aClass.removeStudent(student);
            session.update(aClass);
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            if (transaction != null){
                transaction.rollback();
            }

        }finally {
           if (session != null){
               session.close();
           }
        }
    }

    private void closeTransaction() {
        if (transaction != null && transaction.isActive()){
            transaction.rollback();
        }
    }

    private void closeSession() {
        if (session != null) {
            session.close();
        }
    }
}
