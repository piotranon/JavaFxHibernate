package entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name="Function")
@Table(name="functions")
public class Function {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="function_id")
    private Long id;

    @NaturalId
    @Column(name="function_name")
    private String function_name;

    @Column(name="function_description")
    private String desc;

    private double price;

    @OneToMany
    private Set<Bot> bots=new HashSet<>();

    public Function() {
    }

    public Function(String name, String desc, int price, Set<Bot> bots) {
        this.function_name = name;
        this.desc = desc;
        this.price = price;
        this.bots = bots;
    }
    public Function(String name, String desc, int price) {
        this.function_name = name;
        this.desc = desc;
        this.price = price;
    }

    public Function(Function o) {
        this.function_name = o.getName();
        this.desc = o.getDesc();
        this.price = o.getPrice();
        this.bots = o.getBots();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return function_name;
    }

    public void setName(String name) {
        this.function_name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<Bot> getBots() {
        return bots;
    }

    public void setBots(Set<Bot> bots) {
        this.bots = bots;
    }

    @Override
    public String toString() {
        return "Functions{" +
                "id=" + id +
                ", name='" + function_name + '\'' +
                ", desc='" + desc + '\'' +
                ", price=" + price +
                ", bots=" + bots.getClass().getName() +
                '}';
    }
}
