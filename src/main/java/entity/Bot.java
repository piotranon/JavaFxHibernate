package entity;

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

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name="bot_function",
        joinColumns = @JoinColumn(name="bot_id"),
            inverseJoinColumns = @JoinColumn(name = "function_id")
    )
    private Set<Function> functions=new HashSet<>();

    public Bot() {
    }

    public Bot(Customer customer,String name)
    {
        this.customer=customer;
        this.name=name;
    }

    public Bot(Customer customer,String name,Set<Function> functions)
    {
        this.customer=customer;
        this.functions=functions;
        this.name=name;
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

    public Set<Function> getFunctions() {
        return functions;
    }

    public void setFunctions(Set<Function> functions) {
        this.functions = functions;
    }

    @Override
    public String toString() {
        return "Bot{" +
                "id=" + id +
                ", Customer_id=" + customer.getId() +
                ", name='" + name + '\'' +
                ", Functions='" + functions.toString() + '\'' +
                '}';
    }
}
