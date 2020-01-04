import controllers.bots;
import entity.Customer;
import javafx.fxml.FXMLLoader;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.hibernateSession;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = hibernateSession.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Customer customer = new Customer();
        customer.setCustomer_name("test");
        customer.setCustomer_surname("tests32weqdsadsaewqe");


        List<Customer> Customers = hibernateSession.loadAllData(Customer.class, session);


        System.out.println("rozmiar " + Customers.size());
        for (int i = 0; i < Customers.size(); i++) {
            System.out.println(Customers.get(i).toString());
        }
        Customers.get(0).setCustomer_name("adam");
        session.update(Customers.get(0));
        session.getTransaction().commit();


//        session.delete(customer);
//        session.remove(customers.get(3));
        List<Customer> customers2 = hibernateSession.loadAllData(Customer.class, session);


        System.out.println("rozmiar " + customers2.size());
        for (int i = 0; i < customers2.size(); i++) {
            System.out.println(customers2.get(i).toString());
        }

//        rozmiarsession.save(customer);
//        System.out.println("dane:   ");
//        System.out.println((customer)session.get(customer.class,(long)1));
//        System.out.println((customer)session.get(customer.class,(long)2));

        session.getTransaction().commit();
        hibernateSession.shutdown();

    }
}
