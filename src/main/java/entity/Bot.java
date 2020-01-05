package entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bot")
public class Bot implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bot_id", unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name="bot_owner_id",referencedColumnName ="customer_id" ,nullable = false)
    private Customer customer;

    @Column(name = "bot_name")
    private String name;

    @Column(name = "bot_functions")
    private String functions;

    public Bot(){}

    public Bot(Customer customer, String name, String functions) {
        this.customer = customer;
        this.name = name;
        this.functions = functions;
    }

    public Bot(Bot bot)
    {
        this.customer=bot.customer;
        this.functions=bot.functions;
        this.name=bot.name;
        this.id=bot.id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public entity.Customer getCustomer() {
        return customer;
    }

    public void setCustomer(entity.Customer customer) {
        this.customer = customer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFunctions() {
        return functions;
    }

    public void setFunctions(String functions) {
        this.functions = functions;
    }

    @Override
    public String toString() {
        return "Bot{" +
                "id=" + id +
                ", Customer_id=" + customer.getId() +
                ", name='" + name + '\'' +
                ", Functions='" + functions + '\'' +
                '}';
    }
    public Bot equalsobject(Bot b)
    {
        if(this == b)
            return this;
        else
            return new Bot();
    }
}
