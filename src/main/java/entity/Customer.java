package entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="customer_id")
    private long id;

    @Column(name="customer_name")
    private String customer_name;

    @Column(name="customer_surname")
    private String customer_surname;

    @Column(name="date_joined")
    private Date date_joined;

    public Customer() {
    }
    public Customer(Customer customer){
        this.customer_name=customer.customer_name;
        this.customer_surname=customer.customer_surname;
        this.date_joined=customer.date_joined;
        this.id=customer.id;
    }
    public Customer(long id, String customer_name, String customer_surname, Date date_joined) {
        this.customer_name=customer_name;
        this.customer_surname=customer_surname;
        this.date_joined=date_joined;
        this.id=id;
    }

    @Override
    public String toString() {
        return "customer{" +
                "id=" + id +
                ", customer_name='" + customer_name + '\'' +
                ", customer_surname='" + customer_surname + '\'' +
                ", date_joined=" + date_joined +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_surname() {
        return customer_surname;
    }

    public void setCustomer_surname(String customer_surname) {
        this.customer_surname = customer_surname;
    }

    public Date getDate_joined() {
        return date_joined;
    }

    public void setDate_joined(Date date_joined) {
        this.date_joined = date_joined;
    }
}
