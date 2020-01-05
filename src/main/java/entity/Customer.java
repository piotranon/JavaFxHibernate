package entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="customer")
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="customer_id", unique = true)
    private long id;

    @Column(name="customer_name")
    private String customer_name;

    @Column(name="customer_surname")
    private String customer_surname;

    @Column(name="date_joined")
    private Date date_joined;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Bot> bots=new ArrayList<Bot>();

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Channel> channels=new ArrayList<Channel>();

    public Customer() {
    }

    public Customer(Customer customer){
        this.customer_name=customer.customer_name;
        this.customer_surname=customer.customer_surname;
        this.date_joined=customer.date_joined;
        this.id=customer.id;
        this.bots = customer.bots;
    }

    public Customer(long id, String customer_name, String customer_surname, Date date_joined, List<Bot> bots) {
        this.customer_name=customer_name;
        this.customer_surname=customer_surname;
        this.date_joined=date_joined;
        this.id=id;
        this.bots = bots;
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

    public List<Bot> getBots() {
        return bots;
    }

    public void setBots(List<Bot> bots) {
        this.bots = bots;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", customer_name='" + customer_name + '\'' +
                ", customer_surname='" + customer_surname + '\'' +
                ", date_joined=" + date_joined +
                ", bots=" + bots.toString() +
                ", channels=" + channels.toString() +
                '}';
    }
}
