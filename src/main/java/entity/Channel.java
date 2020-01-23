package entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="channel")
public class Channel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="channel_id",unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name="owner_id",referencedColumnName = "customer_id",nullable = false)
    private Customer customer;

    @Column(name = "channel_name")
    private String name;

    @Column(name="channel_description")
    private String description;

    public Channel(){};

    public Channel(Channel channel)
    {
        this.customer=channel.customer;
        this.description=channel.description;
        this.name=channel.name;
        this.id=channel.id;
    }

    public Channel(Customer customer, String name, String description) {
        this.customer = customer;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "id=" + id +
                ", customerID=" + customer.getId() +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
