package org.o7planning.tutorial.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.o7planning.tutorial.hibernate.DataUtils;
import org.o7planning.tutorial.hibernate.HibernateUtils;
import org.o7planning.tutorial.hibernate.entities.Department;
import org.o7planning.tutorial.hibernate.entities.Employee;

import java.util.Date;

public class PersistentDemo {
    public static void main(String[] args) {

        SessionFactory factory = HibernateUtils.getSessionFactory();
        Session session = factory.getCurrentSession();
        Department department = null;
        Employee emp = null;

        try {
            session.getTransaction().begin();

            Long maxEmpId = DataUtils.getMaxEmpId(session);
            Long empId = maxEmpId + 1;

            // Get Persistent object.
            department = DataUtils.findDepartment(session, "D10");
            // Create transient object
            emp = new Employee();
            emp.setEmpId(empId);
            emp.setEmpNo("E" + empId);
            emp.setEmpName("Name " + empId);
            emp.setJob("Coder");
            emp.setSalary(1000f);
            emp.setManager(null);
            emp.setHideDate(new Date());
            emp.setDepartment(department);

            // Using persist(..)
            // Now 'emp' is managed by Hibernate.
            // it has Persistent status.
            // No action at this time with DB.
            session.persist(emp);

            // At this step the data is pushed to the DB.
            // Execute Insert statement.
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }

        // After the session is closed (commit, rollback, close)
        // Objects 'emp', 'dept' became the Detached objects.
        // It is no longer in the control of the session.
        System.out.println("Emp No: " + emp.getEmpNo());

    }
}
