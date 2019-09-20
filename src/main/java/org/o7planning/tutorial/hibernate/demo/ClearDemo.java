package org.o7planning.tutorial.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.o7planning.tutorial.hibernate.DataUtils;
import org.o7planning.tutorial.hibernate.HibernateUtils;
import org.o7planning.tutorial.hibernate.entities.Department;
import org.o7planning.tutorial.hibernate.entities.Employee;

public class ClearDemo {


    public static void main(String[] args) {
        SessionFactory factory = HibernateUtils.getSessionFactory();

        Session session = factory.getCurrentSession();
        Employee emp = null;
        Department dept = null;
        try {
            session.getTransaction().begin();

            // It is an object has Persistent status.
            emp = DataUtils.findEmployee(session, "E7499");
            dept = DataUtils.findDepartment(session, "D10");


            // clear() evicts all the objects in the session.
            session.clear();


            // Now 'emp' & 'dept' has Detached status
            // ==> false
            System.out.println("- emp Persistent? " + session.contains(emp));
            System.out.println("- dept Persistent? " + session.contains(dept));

            // All change on the 'emp' will not update
            // if not reatach 'emp' to session
            emp.setEmpNo("NEW");

            dept = DataUtils.findDepartment(session, "D20");
            System.out.println("Dept Name = "+ dept.getDeptName());

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }
}