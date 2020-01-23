package entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bot")
public class Bot implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bot_id", unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name="bot_owner_id",referencedColumnName ="customer_id" ,nullable = false)
    private Customer customer;

    @Column(name = "bot_name")
    private String name;

    @ManyToOne
    @JoinColumn(name="function_id",referencedColumnName ="function_id", nullable = false)
    private Function function;

    public Bot() {
    }

    public Bot(Customer customer,String name)
    {
        this.customer=customer;
        this.name=name;
    }

    public Bot(Customer customer,String name,Function function)
    {
        this.customer=customer;
        this.function=function;
        this.name=name;
    }

    public Bot(Bot bot)
    {
        this.customer=bot.customer;
        this.function=bot.function;
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

    public Function getFunctions() {
        return function;
    }

    public void setFunctions(Function function) {
        this.function = function;
    }

    @Override
    public String toString() {
        return "Bot{" +
                "id=" + id +
                ", Customer_id=" + customer.getId() +
                ", name='" + name + '\'' +
                ", Functions='" + function.toString() + '\'' +
                '}';
    }
}
