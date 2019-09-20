package org.o7planning.tutorial.hibernate.anotherDatabaseQuery;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.o7planning.tutorial.hibernate.HibernateUtils;
import org.o7planning.tutorial.hibernate.entities.Employee;

public class SQLQueryies {

    public static void main(String[] args) {
        SessionFactory factory = HibernateUtils.getSessionFactory();

        Session session = factory.getCurrentSession();

        try {

/*
            SQLQuery query2 =
                    session.createSQLQuery("SELECT * FROM department ");

            query2.addEntity(Employee.class);
            query2.list();

*/
//            for (Iterator iterator ; iterator.hasNext();{
//                    Department query2 = (Department)iterator.next();
//            }
            SQLQuery query3 =
                    session.createSQLQuery("UPDATE hibernatedepartment.department SET * FROM department ");

            query3.executeUpdate();



            // String sqlNumber1 = ;

            // All the action with DB via Hibernate
            // must be located in one transaction.
            // Start Transaction.
            session.getTransaction().begin();


            // Create an HQL statement, query the object.
            // Equivalent to the SQL statement:
            // Select e.* from EMPLOYEE e order by e.EMP_NAME, e.EMP_NO
            String sql = "Select e from " + Employee.class.getName() + " e "
                    + " order by e.empName, e.empNo ";


            // Create Query object.
            Query<Employee> query = session.createQuery(sql);


            // Execute query.
            List<Employee> employees = query.getResultList();

            for (Employee emp : employees) {
                System.out.println("Emp: " + emp.getEmpNo() + " : "
                        + emp.getEmpName());
            }

            // Commit data.
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            // Rollback in case of an error occurred.
            session.getTransaction().rollback();
        }
    }

}
