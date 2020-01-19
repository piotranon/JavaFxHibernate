import controllers.bots;
import entity.Bot;
import entity.Channel;
import entity.Customer;
import entity.Function;
import javafx.fxml.FXMLLoader;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.hibernateSession;

import java.util.ArrayList;
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
        List<Channel> channels =hibernateSession.loadAllData(Channel.class,session);
        System.out.println("??????????????CHANNELS");

        System.out.println(channels.toString());

        channels.get(0).setName("adam223");

        System.out.println("transaction: "+hibernateSession.getSessionFactory().getCurrentSession().getTransaction().getStatus());

        System.out.println(channels.toString());

        System.out.println("rozmiar " + Customers.size());
        for (int i = 0; i < Customers.size(); i++) {
            System.out.println(Customers.get(i).toString());
        }
        Customers.get(0).setCustomer_name("adam");
        session.update(Customers.get(0));
        List<Bot> bots=new ArrayList<Bot>(Customers.get(0).getBots());

        session.update(bots);
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
